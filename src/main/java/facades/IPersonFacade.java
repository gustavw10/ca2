package facades;

import dto.CityInfosDTO;
import dto.PersonDTO;
import dto.PersonsDTO;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.List;

public interface IPersonFacade {

    public PersonDTO addPerson(PersonDTO p)
            throws MissingInputException;

    public PersonDTO deletePerson(
            long id)
            throws PersonNotFoundException;

    public PersonDTO getPerson(
            long id)
            throws PersonNotFoundException;

    public PersonDTO editPerson(
            PersonDTO p)
            throws PersonNotFoundException, MissingInputException;

    public long getPersonCount();

    public long getAddressesCount();

    public long getPhonesCount();
    
    public List<PersonDTO> getHobbyPersonCount(String hobby);

    public PersonsDTO getAllPersons();
    
    public CityInfosDTO getAllZip();
    
    public List<PersonDTO> getAllByHobby(String hobby);
}
