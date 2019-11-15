package kuukausibudjetti.domain;

import java.sql.SQLException;
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
        try {
            Person newPerson = this.personDao.create(name);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<Person> getPersons() {
        return this.personDao.getAll();
    }
    
    public List<Person> refetchPersons() {
        return this.personDao.fetchAll();
    }
    
    public boolean removePerson(long id) {
        return this.personDao.delete(id);
    }
}
