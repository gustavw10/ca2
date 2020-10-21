/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Gustav
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person"),
@NamedQuery(name = "Person.getAll", query = "SELECT p FROM Person p")}) 
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    
    @ManyToMany(mappedBy = "persons", cascade = CascadeType.PERSIST)
    private List<Hobby> hobbies;

    @OneToMany(mappedBy="person", cascade = { CascadeType.PERSIST })
    private List<Phone> phones;
    
    @ManyToOne(cascade = { CascadeType.PERSIST })
    private Address address;

    public Person() {
    } 
    
    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        phones = new ArrayList<>();
        hobbies = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }
    
    public void addPhone(Phone phone) {
        if (phone != null){
            phones.add(phone);
        }
    }
    
    public void setAddress(Address address) {
        if (address != null){
            this.address = address;
            address.addPerson(this);
        }
    }
    
    public void addHobby(Hobby hobby){
        if(hobby != null){
            this.hobbies.add(hobby);
            hobby.getPersons().add(this);
        }
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


    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> persons) {
        this.phones = persons;
    }

    public Address getAddress() {
        return address;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    

}
