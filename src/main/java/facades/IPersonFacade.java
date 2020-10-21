package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;

public interface IPersonFacade {

    public PersonDTO addPerson(
            String firstName,
            String lastName,
            String email,
            String phone,
            String street,
            String zipCode,
            String city)
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
}
