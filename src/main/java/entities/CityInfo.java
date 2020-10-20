/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Gustav
 */
@Entity
public class CityInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 4)
    private String zipCode;
    @Column(length = 35)
    private String city;
    
    @OneToMany(mappedBy="cityinfo")
    private List<Address> addresses;
    

    public CityInfo(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
        addresses = new ArrayList<>();
    }

    public CityInfo() {
    }
    
    public void addAddress(Address address) {
        if (address != null){
            addresses.add(address);
        }
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    
}
