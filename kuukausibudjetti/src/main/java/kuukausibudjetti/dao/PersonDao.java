/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.dao;

import java.sql.SQLException;
import java.util.List;
import kuukausibudjetti.domain.Person;

/**
 *
 * @author Jonne
 */
public interface PersonDao {
    Person create(String name) throws SQLException;
    Boolean delete(long id);
    List<Person> getAll();
    List<Person> fetchAll();
}
