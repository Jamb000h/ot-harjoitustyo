package kuukausibudjetti.domain;

import java.sql.SQLException;
import java.util.ArrayList;
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
    
    /**
     * Add a new person to database
     * @param name name of the new person
     * @return true if person was successfully added, otherwise false
     */
    public boolean addPerson(String name) {
        try {
            this.personDao.create(name);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Get all persons
     * @return list of all persons
     */
    public List<Person> getPersons() {
        return this.personDao.getAll();
    }
    
    /**
     * Refetch all persons from database and return them
     * @return list of all persons, updated from database
     */
    public List<Person> refetchPersons() {
        List<Person> persons;
        try {
            persons = this.personDao.fetchAll();
        } catch (SQLException e) {
            System.out.println("Error getting persons!");
            persons = new ArrayList<>();
        }
        
        return persons;
    }
    
    /**
     * Remove person and all related entries from database
     * @param id id of person to remove
     * @return true if deletion successful, otherwise false
     */
    public boolean removePerson(long id) {
        return this.personDao.delete(id);
    }
}
