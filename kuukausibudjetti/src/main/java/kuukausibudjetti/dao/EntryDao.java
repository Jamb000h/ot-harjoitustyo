/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.dao;

import java.util.List;
import kuukausibudjetti.domain.Entry;
import kuukausibudjetti.domain.EntryType;
import kuukausibudjetti.domain.Person;

/**
 *
 * @author Jonne
 */
public interface EntryDao {
    Boolean create(Integer sum, EntryType type, String desc);
    Boolean createForPerson(Integer sum, EntryType type, String desc, Person p);
    Boolean delete(long id);
    List<Entry> getAll();
    List<Entry> getAllExpenditures();
    List<Entry> getAllIncomes();
}
