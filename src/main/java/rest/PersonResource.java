package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CityInfosDTO;
import dto.HobbyToPersonDTO;
import dto.PersonDTO;
import dto.PersonsDTO;
import dto.PhoneDTO;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
        PersonDTO returnPerson = FACADE.addPerson(pers);
        return GSON.toJson(returnPerson);
    }
    
    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updatePerson(@PathParam("id") long id,  String person) throws Exception {
        PersonDTO pDTO = GSON.fromJson(person, PersonDTO.class);
        pDTO.setId(id);
        PersonDTO pNew = FACADE.updatePerson(pDTO);
        return GSON.toJson(pNew);
    }
    
    @PUT
    @Path("addHobby/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addHobby(@PathParam("id") long id,  String hobbydto) throws Exception {
        
        System.out.println(hobbydto);
        //HobbyToPersonDTO pDTO = GSON.fromJson(hobbydto, HobbyToPersonDTO.class);
        
        HobbyToPersonDTO pNew = FACADE.addHobbyToPerson(hobbydto, id);
        return GSON.toJson("");
    }
    
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deletePerson(@PathParam("id") long id) throws PersonNotFoundException {
        PersonDTO pDeleted = FACADE.deletePerson(id);
        return GSON.toJson(pDeleted);
    }
    
    public static void main(String[] args) throws MissingInputException, Exception {
        PersonResource res = new PersonResource();
//        res.addPerson("{\n" +
//"   \n" +
//"     \"firstName\": \"EKOCAMEN\",\n" +
//"      \"lastName\": \"Eko4name\",\n" +
//"      \"phone\": \"3500, 200003\",\n" +
//"      \"email\": \"ekoEEEEENTEST@email.com\",\n" +
//"      \"street\": \"addr\",\n" +
//"      \"zip\": \"0999\",\n" +
//"      \"city\": \"ekocity\"\n" +
//"}");
        
//        res.addHobby(3, "{\n" +
//"   \n" +
//     
//"      \"hobbies\": \"Airsoft, Akrobatik\",\n" +
//"}");

//            res.addHobby(15, "Animation");
//        
//        res.updatePerson(40, "{\n" +
//"   \n" +
//"     \"firstName\": \"once again\",\n" +
//"      \"lastName\": \"Ekeadawo4name\",\n" +
//"      \"phone\": \"500\",\n" +
//"      \"email\": \"ekoEEEEENTEST@email.com\",\n" +
//"      \"street\": \"I AM TEST WITH POSTMAN\",\n" +
//"      \"zip\": \"900\",\n" +
//"      \"city\": \"ekocity\"\n" +
//"}");
        
//        res.deletePerson(34);
        
    }
 
}
