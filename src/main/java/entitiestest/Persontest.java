/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiestest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Gustav
 */
//@Entity
//public class Person implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String firstName;
//    private String lastName;
//    private String email;
//    
//    @OneToMany(mappedBy="person")
//    private List<Phone> phones;
//    
//    public Person(String firstName, String lastName, String email) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//    }
//    
//    public Person(){
//        
//    }
//    
//    public void addPhone(Phone phone) {
//        System.out.println("INSIDE PHONE");
//        if (phone != null){
//            System.out.println("INSIDE PHONE 2");
//            System.out.println(phone);
//            phones.add(phone);
//        }
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//    
//    

