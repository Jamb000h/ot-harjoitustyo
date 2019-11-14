/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.domain;

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
}
