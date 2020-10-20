package facades;

import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class FacadeExample {

    private static FacadeExample instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private FacadeExample() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static FacadeExample getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FacadeExample();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getRenameMeCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM RenameMe r").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }

    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        System.out.println("testing...");
        Person person1 = new Person("eko", "ekolastname", "eko@mail.com");
        Person person2 = new Person("eko2", "ekolastname2", "eko2@mail.com");

        Phone phone1 = new Phone("2000000");
        Phone phone2 = new Phone("3000000");

        phone1.setPerson(person1);
        phone2.setPerson(person1);

        System.out.println(person1.getPhones().get(0).getNumber());
        System.out.println(person1.getPhones().get(1).getNumber());
        System.out.println(person1.getPhones().get(0).getPerson().getFirstName());
        System.out.println(person1.getPhones().size());

        //
        CityInfo info = new CityInfo("zip", "City");
        Address address = new Address("ekostreet");

        address.setCityInfo(info);

        System.out.println(address.getCityinfo().getAddresses().get(0).getStreet());

        //
        person1.setAddress(address);
        person2.setAddress(address);
        System.out.println(person1.getAddress().getCityinfo().getAddresses().get(0).getCityinfo().getZipCode());
        for (int i = 0; i < address.getPersons().size(); i++) {
            System.out.println(address.getPersons().get(i).getFirstName());
        }

        //
        Hobby hob1 = new Hobby("hobbynameONE", "linkONE", "categoryONE", "typeONE");
        Hobby hob2 = new Hobby("hobbynameTWO", "linkTWO", "categoryTWO", "typeTWO");

        person1.addHobby(hob1);
        person1.addHobby(hob2);
        person2.addHobby(hob1);

        System.out.println(person1.getHobbies().get(0).getName());
        System.out.println(person2.getHobbies().get(0).getName());

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(person1);
            em.persist(person2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
