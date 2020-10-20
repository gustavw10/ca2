package dto;

import entities.Address;

public class AddressDTO {

    private String street;
    
    public AddressDTO(Address a){
        this.street = a.getStreet();
    }

    public AddressDTO() {
    }

    public AddressDTO(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    

}
