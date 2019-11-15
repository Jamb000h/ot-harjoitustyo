/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.domain;

import java.util.List;
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
        return this.entryDao.create(sum, type, desc);
    }
    
    public boolean addEntryForPerson(Integer sum, EntryType type, String desc, Person p) {
        return this.entryDao.createForPerson(sum, type, desc, p);
    }
    
    public List<Entry> getAllEntriesForPerson(Person p) {
        return this.entryDao.getAllForPerson(p);
    }
    
    public boolean removeEntry(long id) {
        return this.entryDao.delete(id);
    }
}
