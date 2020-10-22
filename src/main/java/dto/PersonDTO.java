package dto;

import entities.Person;
import entities.Phone;
import java.util.ArrayList;
import java.util.List;

public class PersonDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String street;
    private String city;
    private String zip;
    private List<Phone> phones;

    public PersonDTO(Person p) {
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.email = p.getEmail();
        this.id = p.getId();
    }

    public PersonDTO() {
    }

    public PersonDTO(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
    
    public List<PersonDTO>toDTO(List<Person>persons){
        List<PersonDTO>dtoes = new ArrayList();
            for(Person p: persons){
                dtoes.add(new PersonDTO(p));
            }
            return dtoes;
    }
}
