package dto;

import entities.Phone;

public class PhoneDTO {

    private String number;

    public PhoneDTO(Phone p){
        this.number = p.getNumber();
    }

    public PhoneDTO() {
    }

    public PhoneDTO(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    
    
}
