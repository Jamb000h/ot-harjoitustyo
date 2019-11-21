/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.dao;

import java.sql.SQLException;
import java.util.List;
import kuukausibudjetti.domain.Entry;
import kuukausibudjetti.domain.EntryType;
import kuukausibudjetti.domain.Person;

/**
 *
 * @author Jonne
 */
public interface EntryDao {
    Entry create(Integer sum, EntryType type, String desc) throws SQLException;
    Entry createForPerson(Integer sum, EntryType type, String desc, Person p) throws SQLException;
    Boolean delete(long id);
    List<Entry> getAll();
    List<Entry> fetchAll() throws SQLException;
}
