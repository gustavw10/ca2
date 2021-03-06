package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CityInfosDTO;
import dto.PersonDTO;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

@Path("cityinfo")
public class CityInfoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World - CityInfo\"}";
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllZipCodes() {
       List<String> cities = FACADE.getAllZip();
       return GSON.toJson(cities);
    }

    @GET
    @Path("{zipcode}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getByZip(@PathParam("zipcode") String zipcode) {
        List<PersonDTO> p = FACADE.getAllByZip(zipcode);
        return GSON.toJson(p);
    }

    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_JSON})
    public String getZipcodeCount() {
        long count = FACADE.getZipcodesCount();
        return "{\"count\":" + count + "}";
    }
}
