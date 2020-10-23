package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import exceptions.PersonNotFoundException;
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

    @AfterEach
    public void tearDown() {

    }

    
    @Test
    public void testPersonCount() {
        assertEquals(2, facade.getPersonCount());
    }
      

    
    
   
    

}
