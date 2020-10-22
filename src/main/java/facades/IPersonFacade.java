package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;

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

    public PersonsDTO getAllPersons();
    
    public PersonDTO getPersonByHobby (String hobby);
}
