package kuukausibudjetti.domain;

import java.util.List;
import kuukausibudjetti.dao.PersonDao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jonne
 */
public class PersonService {
    private final PersonDao personDao;
    
    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }
    
    public boolean addPerson(String name) {
        return this.personDao.create(name);
    }
    
    public List<Person> getPersons() {
        return this.personDao.getAll();
    }
}
