package rest;

import dto.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import rest.ApplicationConfig;
import entities.Person;
import entities.Phone;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1,p2;
    
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {

        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void closeTestServer(){

         EMF_Creator.endREST_TestWithDB();
         httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
 EntityManager em = emf.createEntityManager();


        p1 = new Person("eko", "ekolastname", "eko@mail.com");
        p2 = new Person("eko2", "ekolastname2", "eko2@mail.com");

        Phone phone1 = new Phone("2000000");
        Phone phone2 = new Phone("3000000");

        phone1.setPerson(p1);
        phone2.setPerson(p1);

        CityInfo info = new CityInfo("zip", "City");
        CityInfo info2 = new CityInfo("zip2", "City2");
        Address address = new Address("ekostreet");
        Address address2 = new Address("ekostreet2");

        address.setCityInfo(info);
        address2.setCityInfo(info2);



        p1.setAddress(address);
        p2.setAddress(address2);

        Hobby hob1 = new Hobby("1hobbynameONE", "linkONE", "categoryONE", "typeONE");
        Hobby hob2 = new Hobby("1hobbynameTWO", "linkTWO", "categoryTWO", "typeTWO");

        p1.addHobby(hob1);
        p1.addHobby(hob2);
        p2.addHobby(hob1);


        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Phone").executeUpdate();
            em.createQuery("DELETE from Person").executeUpdate();
            em.createQuery("DELETE from Address").executeUpdate();
            em.createQuery("DELETE from CityInfo").executeUpdate();    
            em.createQuery("DELETE from Hobby").executeUpdate();

            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    

   @Disabled
   @Test
    public void testGetAll2() {
                List<PersonDTO> personsDTOs;
        
        personsDTOs = given()
                .contentType("application/json")
                .when()
                .get("/person/all")
                .then()
                .assertThat()
                .extract().body().jsonPath().getList("all", PersonDTO.class);
        
        PersonDTO p1DTO = new PersonDTO(p1);
        PersonDTO p2DTO = new PersonDTO(p2);

        
        assertThat(personsDTOs.size(), is(2));
                
    }
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }
    
    @Disabled
    @Test
    public void testPersonGetAll() {
        given()
                .contentType("application/json")
                .get("/person/all")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", is(2))
                .and()
                .body("id", hasItems("1","2"));
                
    }
    
    @Test
    public void testPersonCount() {
        given()
        
        .contentType("application/json")
        .get("/person/count").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("count", equalTo(2)); 
    }
    
    @Test
    public void testZipcodeCount() {
        given()
                .contentType("application/json")
                .get("/cityinfo/count")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(2)); // har kun 2 zipcodes i test db
    }
    
    @Disabled
    @Test
    public void testGetPersonById() {
        given()
                .contentType("application/json")
                .get("/person/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("firstName", equalTo(p1.getFirstName()))
                .and()
                .body("lastName", equalTo(p1.getLastName()));
                
    }
    @Disabled
    @Test
    public void testGetAllZip() {
        given()
                .contentType("application/json")
                .get("/cityinfo/all")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", is(1))
                .and()
                .body("zipCode", hasItems("zip", "zip2"));
                

               
    }
    
//    @Test
//    public void testGetAllZip(){
//        given()
//                .contentType("application/json")
//                .get("/cityinfo/all")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("size()", is(2));
//    }
    
}