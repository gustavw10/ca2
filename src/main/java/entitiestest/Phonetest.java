/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiestest;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Gustav
 */
//@Entity
//public class Phone implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String number;
//    
//    @ManyToOne(cascade = { CascadeType.PERSIST })
//    private Person person;
//
//    public Phone(String number) {
//        this.number = number;
//    }
//    
//    public Phone(){
//        
//    }
//    
//    public void setPerson(Person person) {
//        System.out.println(person.getFirstName());
//        if (person != null){
//            System.out.println("inside1");
//            this.person = person;
//            
//            System.out.println("inside2");
//            
//            System.out.println(this);
//            
//            person.addPhone(this);
//            
//        } else {
//            this.person = null;
//        }
//    }
//    
//    
//}
