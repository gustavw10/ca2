package facades;

import dto.PersonDTO;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;

public interface IPersonFacade {
    

    public PersonDTO addPerson()
            throws MissingInputException;

    public PersonDTO deletePerson(
            int id)
            throws PersonNotFoundException;

    public PersonDTO getPerson(
            int id)
            throws PersonNotFoundException;

    public PersonDTO editPerson(
            PersonDTO p)
            throws PersonNotFoundException, MissingInputException;
}
