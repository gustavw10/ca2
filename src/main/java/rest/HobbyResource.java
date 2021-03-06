/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;

import exceptions.PersonNotFoundException;

import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author David
 */
@Path("hobby")
public class HobbyResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }


    @GET
    @Path("{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getByHobby(@PathParam("hobby") String hobby) {
        List<PersonDTO> p = FACADE.getAllByHobby(hobby);
        return GSON.toJson(p);
    }

//    @GET
//    @Path("{hobby}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getByHobby(@PathParam("hobby") String hobby) {
//        return GSON.toJson(FACADE.getPersonByHobby(hobby));
//    }
    
    @Path("count/{hobby}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getHobbyPersonCount(@PathParam("hobby") String hobby) {
        List<PersonDTO> list = FACADE.getAllByHobby(hobby);
        int count = list.size();
        return "{\"count\":" + count + "}";
    }
    

}
