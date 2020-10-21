package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;


@Entity
@NamedQueries({
@NamedQuery(name = "Phone.deleteAllRows", query = "DELETE from Phone")}) 
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    
    @ManyToOne(cascade = { CascadeType.PERSIST })
    private Person person;

    public Phone(String number) {
        this.number = number;
    }
    
    public Phone() {
    }
    
    public void setPerson(Person person) {
        if (person != null){
            this.person = person;
            person.addPhone(this);
        }
    }

    public Person getPerson() {
        return person;
    }

    public String getNumber() {
        return number;
    }
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
   
}
