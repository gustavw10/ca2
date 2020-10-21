package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {
    private static Person p1,p2,p3;
    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
    EntityManager em = emf.createEntityManager();
    
     p1 = new Person("eko", "ekolastname", "eko@mail.com");
     p2 = new Person("eko1", "ekolastname1", "eko1@mail.com");
     p3 = new Person("eko2", "ekolastname2", "eko2@mail.com");
    Phone ph1 = new Phone("12345678");
    Phone ph2 = new Phone("23456789");
    Phone ph3 = new Phone("34567890");
    Hobby h1 = new Hobby("skiløb", "wikilink", "category", "type");
    Hobby h2 = new Hobby("tennis", "wikilink1", "category1", "type1");
    Hobby h3 = new Hobby("fodbold", "wikilink2", "category2", "type2");
    Address a1 = new Address("ekostreet");
    Address a2 = new Address("ekostreet2");
    Address a3 = new Address("ekostreet3");
    CityInfo c1 = new CityInfo("1234", "Lyngby");
    CityInfo c2 = new CityInfo("4321", "Gentofte");
    CityInfo c3 = new CityInfo("2345", "København");
    
    ph1.setPerson(p1);
    ph2.setPerson(p2);
    ph3.setPerson(p3);
    
    a1.setCityInfo(c3);
    a2.setCityInfo(c2);
    a3.setCityInfo(c1);
    
    p1.setAddress(a1);
    p2.setAddress(a2);
    p3.setAddress(a3);
    
    p1.addHobby(h3);
    p2.addHobby(h2);
    p3.addHobby(h1);
    
    try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Phone").executeUpdate();
            em.createQuery("DELETE from Person").executeUpdate();
            em.createQuery("DELETE from Address").executeUpdate();
            em.createQuery("DELETE from CityInfo").executeUpdate();    
            em.createQuery("DELETE from Hobby").executeUpdate();
            
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {

    }

    @Disabled
    @Test
    public void testPersonCount() {
        assertEquals(3, facade.getPersonCount());
    }
    @Disabled  
    @Test
    public void testAllPersons() {

//        PersonDTO p1DTO = new PersonDTO(p1);
//        PersonDTO p2DTO = new PersonDTO(p2);
//        PersonDTO p3DTO = new PersonDTO(p3);
//        assertEquals(facade.getAllPersons(), containsInAnyOrder(p1DTO, p2DTO, p3DTO));
        
        
        
        
        
        
//        ArrayList<Person> people = new ArrayList<>();
//        people.add(p1);
//        people.add(p2);
//        people.add(p3);
//        
//        assertEquals(facade.getAllPersons(), Matchers.containsInAnyOrder(people));
    }
    
    
    
    

}
