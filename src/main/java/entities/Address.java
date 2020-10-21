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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Gustav
 */

@Entity
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    
    @ManyToOne(cascade = { CascadeType.PERSIST })
    private CityInfo cityinfo;
    
    @OneToMany(mappedBy="address")
    private List<Person> persons;

    public Address(String street) {
        this.street = street;
        persons = new ArrayList<>();
    }
    
    public Address(){
    }

    public Address(String street, CityInfo cityinfo, List<Person> persons) {
        this.street = street;
        this.cityinfo = cityinfo;
        this.persons = persons;
    }
    
    public void setCityInfo(CityInfo info) {
        if (info != null){
            this.cityinfo = info;
            info.addAddress(this);
        }
    }
    
    public void addPerson(Person person) {
        if (person != null){
            persons.add(person);
        }
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public CityInfo getCityinfo() {
        return cityinfo;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCityinfo(CityInfo cityinfo) {
        this.cityinfo = cityinfo;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
    
    
    
}
