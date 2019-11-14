/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.dao;

import java.util.List;
import kuukausibudjetti.domain.Person;

/**
 *
 * @author Jonne
 */
public interface PersonDao {
    Boolean create(String name);
    Boolean delete(long id);
    List<Person> getAll();
}
