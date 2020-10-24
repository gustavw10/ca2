package facades;

import dto.CityInfoDTO;
import dto.CityInfosDTO;
import dto.HobbyDTO;
import dto.HobbyToPersonDTO;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM Person r").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }

    }
    
    @Override
    public PersonDTO addPerson(PersonDTO p) throws MissingInputException {
        if ((p.getFirstName().length() == 0 || p.getLastName().length() == 0)) {
            throw new MissingInputException("Missing first and/or lastname");
        }
        
        EntityManager em = getEntityManager();
        Person person = new Person(p.getFirstName(), p.getLastName(), p.getEmail());
        
        try {
            em.getTransaction().begin();
            
            String[] listOfPhones = p.getPhones().split(",");
            for(int i = 0; i < listOfPhones.length; i++){
                Phone ph = new Phone(listOfPhones[i].trim());
                ph.setPerson(person);
            }
            
            Query query = em.createQuery("SELECT a FROM Address a WHERE a.street = :street AND a.cityinfo.zipCode = :CITYINFO_ZIPCODE");
            query.setParameter("street", p.getStreet());
            query.setParameter("CITYINFO_ZIPCODE", p.getZip());
            List<Address> addresses = query.getResultList();
            
            if (addresses.size() > 0) {
                person.setAddress(addresses.get(0));
            } else {
                Address address = new Address(p.getStreet());
                CityInfo city = em.find(CityInfo.class, p.getZip());
              if(city != null){
                  address.setCityInfo(city);
                }
                person.setAddress(address);
          }
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }
    
    
    @Override
    public PersonDTO updatePerson(PersonDTO p) throws PersonNotFoundException, MissingInputException {
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
                person.getAddress().setStreet(p.getStreet());
                person.getAddress().getCityinfo().setCity(p.getCity());
//              person.getAddress().getCityinfo().setZipCode(p.getZip());
               
                List<Phone> phones = person.getPhones();
 
                for (int i = 0; i < phones.size(); i++) {
                    em.remove(phones.get(i));
                  }
                
                Query query = em.createQuery("DELETE FROM Phone WHERE person_id = :id");
                query.setParameter("id", p.getId());
                query.executeUpdate();
                
                String[] listOfPhones = p.getPhones().split(",");
                for(int i = 0; i < listOfPhones.length; i++){
                Phone ph = new Phone(listOfPhones[i].trim());
                ph.setPerson(person);
              }
            }

            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }
    
    @Override
    public HobbyToPersonDTO addHobbyToPerson(String h, long id) throws MissingInputException, PersonNotFoundException{
        if ((h == null)) {
            throw new MissingInputException("Missing id for hobby or input");
        }
         EntityManager em = emf.createEntityManager();
        try {
            Person person = em.find(Person.class, id);
            
            if (h != null) {
                String[] hSplit = h.split(",");
                
                for (int i = 0; i < hSplit.length; i++) {
                    
                    TypedQuery<Hobby> hobbyQuery = em.createQuery("SELECT h FROM Hobby h WHERE h.name = :hobbyNames", Hobby.class);
                    hobbyQuery.setParameter("hobbyNames", hSplit[i].trim().toLowerCase());
                    Hobby hobby = hobbyQuery.getSingleResult();
                    
                    em.getTransaction().begin();
                    person.addHobby(hobby);
                    em.getTransaction().commit();
                }
            }
            
        } finally {
            em.close();
        }
        return null;
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
                System.out.println(phones.size());
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
    public long getZipcodesCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM CityInfo r").getSingleResult();
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

//        System.out.println("testing...");
//        Person person1 = new Person("TESTFORCITY", "TESTFORCITY", "eko@mail.com");
//        Person person2 = new Person("eko2", "ekolastname2", "eko2@mail.com");
//
//        Phone phone1 = new Phone("2000000");
//        Phone phone2 = new Phone("3000000");
//
//        phone1.setPerson(person1);
//        phone2.setPerson(person1);
//
////        System.out.println(person1.getPhones().get(0).getNumber());
////        System.out.println(person1.getPhones().get(1).getNumber());
////        System.out.println(person1.getPhones().get(0).getPerson().getFirstName());
////        System.out.println(person1.getPhones().size());
//
//        //
//        CityInfo info = new CityInfo("0036", "aabty");
//        CityInfo info2 = new CityInfo("0037", "aabty2");
//        Address address = new Address("TEST");
//        Address address2 = new Address("TEST");
//        
//        CityInfo city = em.find(CityInfo.class, "900");
//        
//        address.setCityInfo(info);
//        address2.setCityInfo(info2);
//        System.out.println(address.getCityinfo().getZipCode());
//
//        person1.setAddress(address);
//        person2.setAddress(address2);
//        
//         try {
//            em.getTransaction().begin();
//            em.persist(address);
//            em.persist(person1);
//            em.persist(person2);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//       
//        //
//        
////        System.out.println(person1.getAddress().getCityinfo().getAddresses().get(0).getCityinfo().getZipCode());
////        for (int i = 0; i < address.getPersons().size(); i++) {
////            System.out.println(address.getPersons().get(i).getFirstName());
////        }
////
////        
////        Hobby hob1 = new Hobby("1hobbynameONE", "linkONE", "categoryONE", "typeONE");
////        Hobby hob2 = new Hobby("1hobbynameTWO", "linkTWO", "categoryTWO", "typeTWO");
////
////        person1.addHobby(hob1);
////        person1.addHobby(hob2);
////        person2.addHobby(hob1);
////
////        System.out.println(person1.getHobbies().get(0).getName());
////        System.out.println(person2.getHobbies().get(0).getName());
////
////        Query query = em.createQuery("SELECT h from Hobby h WHERE h.name = :name");
////        query.setParameter("name", "1hobbynameONE");
////        List<Hobby> result = query.getResultList();
////
////        for (Hobby ps : result) {
////            System.out.println("Navn: " + ps.getName() + ", " + ps.getCategory() + ", " + ps.getType());
////        }
//
//////        Query q1 = em.createQuery(
//////                "SELECT p "
//////                + "FROM Person p "
//////                + "JOIN Hobby phobby "
//////                + "ON p.id = phobby.persons_ID "
//////                + "JOIN Hobby hobby "
//////                + "ON hobby.name = phobby.hobbies_NAME "
//////                + "WHERE hobby.name = :name");
//////        q1.setParameter("name", "1hobbynameONE");
//////        System.out.println(new PersonsDTO(q1.getResultList()));
//
//        try {
//            em.getTransaction().begin();
//
////            Query query = em.createQuery("SELECT a FROM Phone a WHERE a.person_id = :id");
////            query.setParameter("id", person1.getId());
//           // em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
//           // em.createNamedQuery("Person.deleteAllRows").executeUpdate();
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

    
    public List<String> getAllZip() {
        EntityManager em = emf.createEntityManager();
        TypedQuery getZips = em.createQuery("SELECT z.zipCode, z.city FROM CityInfo z", CityInfo.class);
        List<String> zipCodes = getZips.getResultList();
            return zipCodes;
    }
    
    public List<PersonDTO> getAllByZip(String zipCode) {
        EntityManager em = emf.createEntityManager();
        List<PersonDTO> listDTO;
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p INNER JOIN p.address.cityinfo z WHERE z.zipCode='" + zipCode + "'", Person.class);
            
            List<Person> p = query.getResultList();
            
            PersonDTO pdto = new PersonDTO();
            
            listDTO = pdto.toDTO(p);
            System.out.println(pdto);
            System.out.println(p);
        } finally {
            em.close();
        }
        return listDTO;
    }

    @Override
    public List<PersonDTO> getAllByHobby(String hobby) {
        EntityManager em = emf.createEntityManager();
        List<PersonDTO> listDTO;
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p INNER JOIN p.hobbies h WHERE h.name='" + hobby + "'", Person.class);
            
            List<Person> p = query.getResultList();
            
            PersonDTO pdto = new PersonDTO();
            
            listDTO = pdto.toDTO(p);
        } finally {
            em.close();
        }
        return listDTO;
    }
}
