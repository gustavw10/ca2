package facades;

import dto.CityInfoDTO;
import dto.CityInfosDTO;
import dto.HobbyDTO;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
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

    @Override
    public PersonDTO addPerson(PersonDTO p) throws MissingInputException {
        //String firstName, String lastName, String email, String phone, String street, String zipCode, String city
        if ((p.getFirstName().length() == 0 || p.getLastName().length() == 0)) {
            throw new MissingInputException("Missing first and/or lastname");
        }
        EntityManager em = getEntityManager();
        Person person = new Person(p.getFirstName(), p.getLastName(), p.getEmail());
        Address address = new Address(p.getStreet());
        person.setPhones(p.getPhones()); //DEN HER MANGLER
        try {
              CityInfo city = em.find(CityInfo.class, p.getZip());
              if(city != null){
                  address.setCityInfo(city);
                  person.setAddress(address);
              }
//            em.getTransaction().begin();
//            Query query = em.createQuery("SELECT a FROM Address a WHERE a.street = :street AND a.zip = :zip AND a.city = :city");
//            query.setParameter("street", street);
//            query.setParameter("zip", zipCode);
//            query.setParameter("city", city);
//            List<Address> addresses = query.getResultList();
//            if (addresses.size() > 0) {
//                person.setAddress(addresses.get(0));
//            } else {
//                person.setAddress(new Address(street));
//            }
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    @Override
    public PersonDTO deletePerson(long id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);

        if (person == null) {
            throw new PersonNotFoundException(String.format("Person with id: (%d) not found", id));
        } else {
            try {
                List<Phone> phones = person.getPhones();

                em.getTransaction().begin();
                for (int i = 0; i < phones.size(); i++) {
                    em.remove(phones.get(i));
                }
                em.remove(person);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new PersonDTO(person);
        }
    }

    @Override
    public PersonDTO getPerson(long id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();

        try {
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new PersonNotFoundException(String.format("Person with id: (%d) not found.", id));
            } else {
                return new PersonDTO(person);
            }
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException, MissingInputException {
        if ((p.getFirstName().length() == 0 || p.getLastName().length() == 0)) {
            throw new MissingInputException("Missing first and/or lastname");
        }
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Person person = em.find(Person.class, p.getId());
            if (person == null) {
                throw new PersonNotFoundException(String.format("Person with id: %d not found", p.getId()));
            } else {

                person.setFirstName(p.getFirstName());
                person.setLastName(p.getLastName());
                person.setEmail(p.getEmail());
            }

            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    @Override
    public long getPersonCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM Person r").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }
    }

    @Override
    public long getAddressesCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM Address r").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }
    }

    @Override
    public long getPhonesCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM Phone r").getSingleResult();
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
        Hobby hob1 = new Hobby("1hobbynameONE", "linkONE", "categoryONE", "typeONE");
        Hobby hob2 = new Hobby("1hobbynameTWO", "linkTWO", "categoryTWO", "typeTWO");

        person1.addHobby(hob1);
        person1.addHobby(hob2);
        person2.addHobby(hob1);

        System.out.println(person1.getHobbies().get(0).getName());
        System.out.println(person2.getHobbies().get(0).getName());
        
        Query query = em.createQuery("SELECT h from Hobby h WHERE h.name = :name");
        query.setParameter("name", "1hobbynameONE");
        List<Hobby> result = query.getResultList();
        
        for (Hobby ps: result) {
            System.out.println("Navn: " + ps.getName()+ ", " + ps.getCategory()+ ", " + ps.getType());
        }
        
//        Query q1 = em.createQuery(
//                "SELECT p " +
//                        "FROM Person p " +
//                        "JOIN Hobby phobby " +
//                        "ON p.id = phobby.persons_ID " +
//                        "JOIN Hobby hobby " +
//                        "ON hobby.name = phobby.hobbies_NAME " +
//                        "WHERE Hobby.name = :name");
//        q1.setParameter("name", "1hobbynameONE");
//        System.out.println(new PersonsDTO(q1.getResultList()));
//        try {
//            em.getTransaction().begin();
//
////            Query query = em.createQuery("SELECT a FROM Phone a WHERE a.person_id = :id");
////            query.setParameter("id", person1.getId());
//            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
//            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
//            em.persist(person1);
//            em.persist(person2);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {
            return new PersonsDTO(em.createNamedQuery("Person.getAll").getResultList());
        } finally {
            em.close();
        }
    }

    public CityInfosDTO getAllZip() {
        EntityManager em = emf.createEntityManager();
        try {
            return new CityInfosDTO(em.createNamedQuery("CityInfo.getAll").getResultList());
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO getPersonByHobby(String hobby) {
        EntityManager em = getEntityManager();

        try {
            Person person = em.find(Person.class, hobby);
                return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

}
