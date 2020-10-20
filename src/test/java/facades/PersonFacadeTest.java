package facades;

import entities.Person;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {
    private static Person p1,p2,p3,p4,p5;
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
    
//        EntityManager em = emf.createEntityManager();
//        Date date = new Date();
//        c1 = new Cars(1997, "Ford", "E350", 3000, "test", date);
//        c2 = new Cars(1999, "Chevy", "Venture", 4900, "test1", date);
//        c3 = new Cars(2000, "Chevy", "Venture", 5000, "test3", date);
//        c4 = new Cars(1996, "Jeep", "Grand Cherokee", 4799, "test4", date);
//        c5 = new Cars(2005, "Volvo", "v70", 44799, "test5", date);
//                try {
//            em.getTransaction().begin();
//            em.createQuery("DELETE from Cars").executeUpdate();
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

    @AfterEach
    public void tearDown() {

    }

   
//    @Test
//    public void testCarCount() {
//        assertEquals(5, facade.getPersonCount());
//    }
    
    @Test
    public void testAllCars() {
      //  assertEquals(members, facade.getAllNames());
    }
    
    
    
    

}
