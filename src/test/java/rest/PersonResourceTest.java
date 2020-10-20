package rest;

import rest.ApplicationConfig;
import entities.Person;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1,p2,p3,p4,p5;
    
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
//        EntityManager em = emf.createEntityManager();
//        Date date = new Date();
//        c1 = new Cars(1997, "Ford", "E350", 3000, "test", date);
//        c2 = new Cars(1999, "Chevy", "Venture", 4900, "test1", date);
//        c3 = new Cars(2000, "Chevy", "Venture", 5000, "test3", date);
//        c4 = new Cars(1996, "Jeep", "Grand Cherokee", 4799, "test4", date);
//        c5 = new Cars(2005, "Volvo", "v70", 44799, "test5", date);
//        try {
//            em.getTransaction().begin();
//            em.createNamedQuery("Cars.deleteAllRows").executeUpdate();
//            em.persist(c1);
//            em.persist(c2);
//            em.persist(c3);
//            em.persist(c4);
//            em.persist(c5);
//            em.getTransaction().commit();
//        } finally { 
//            em.close();
//        }
    }
    
//    @Test
//    public void testServerIsUp() {
//        System.out.println("Testing is server UP");
//        given().when().get("/cars").then().statusCode(200);
//    }
    
    @Test
    public void testCount() {
  
    }
    @Test
    public void testGetAll() {

                
    }
    
}