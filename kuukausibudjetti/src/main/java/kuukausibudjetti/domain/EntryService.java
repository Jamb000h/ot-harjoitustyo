/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kuukausibudjetti.dao.EntryDao;

/**
 *
 * @author Jonne
 */
public class EntryService {
    private final EntryDao entryDao;
    
    public EntryService(EntryDao entryDao) {
        this.entryDao = entryDao;
    }
    
    public boolean addEntry(Integer sum, EntryType type, String desc) {
        try {
            Entry newEntry = this.entryDao.create(sum, type, desc);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean addEntryForPerson(Integer sum, EntryType type, String desc, Person p) {
        try {
            Entry newEntry = this.entryDao.createForPerson(sum, type, desc, p);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<Entry> getAllEntriesForPerson(Person p) {
        System.out.println(this.entryDao.getAll().size());
        this.entryDao.getAll().forEach(e -> System.out.println(e.getPersonId()));
        return this.entryDao.getAll()
                .stream().filter(entry -> entry.getPersonId() == p.getId())
                .collect(Collectors.toList());
    }
    
    public List<Entry> getAllIncomesForPerson(Person p) {
        return this.entryDao.getAll()
                .stream().filter(entry -> entry.getPersonId() == p.getId() && entry.getType() == EntryType.INCOME)
                .collect(Collectors.toList());
    }
    
    public List<Entry> getAllExpendituresForPerson(Person p) {
        return this.entryDao.getAll()
                .stream().filter(entry -> entry.getPersonId() == p.getId() && entry.getType() == EntryType.EXPENDITURE)
                .collect(Collectors.toList());
    }
    
    public boolean removeEntry(long id) {
        return this.entryDao.delete(id);
    }
}
