package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CityInfosDTO;
import dto.PersonDTO;
import dto.PersonsDTO;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }
  

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons() {
        PersonsDTO persons = FACADE.getAllPersons();
        return GSON.toJson(persons);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonById(@PathParam("id") long id) throws PersonNotFoundException {
        return GSON.toJson(FACADE.getPerson(id));
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addPerson(String person) throws MissingInputException  {
        PersonDTO pers = GSON.fromJson(person, PersonDTO.class);
        System.out.println(pers.getFirstName());
        PersonDTO returnPerson = FACADE.addPerson(pers);
        return GSON.toJson(returnPerson);
    }
    
    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updatePerson(@PathParam("id") long id,  String person) throws Exception {
        PersonDTO pDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO pNew = FACADE.updatePerson(pDTO);
        return GSON.toJson(pNew);
    }
    
    public static void main(String[] args) throws MissingInputException {
        PersonResource res = new PersonResource();
        res.addPerson("{\n" +
"   \n" +
"     \"firstName\": \"Eko4n\",\n" +
"      \"lastName\": \"Eko4name\",\n" +
"      \"phone\": \"700\",\n" +
"      \"email\": \"ekoEEEEENTEST@email.com\",\n" +
"      \"street\": \"ekotest\",\n" +
"      \"zip\": \"900\",\n" +
"      \"city\": \"ekocity\"\n" +
"}");
    }
 
}
