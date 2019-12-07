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
    
    /**
     * Add new common entry to database
     * @param sum entry as euros without cents
     * @param type type of entry
     * @param desc description for entry
     * @return true if entry is saved successfully, otherwise false
     */
    public boolean addEntry(Integer sum, EntryType type, String desc) {
        try {
            Entry newEntry = this.entryDao.create(sum, type, desc);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Add new entry for person to database
     * @param sum entry as euros without cents
     * @param type type of entry
     * @param desc description for entry
     * @param p person to add the entry to
     * @return true if entry is saved successfully, otherwise false
     */
    public boolean addEntryForPerson(Integer sum, EntryType type, String desc, Person p) {
        try {
            Entry newEntry = this.entryDao.createForPerson(sum, type, desc, p);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Get a list of all common
     * @return list of all common entries
     */
    public List<Entry> getAllCommonEntries() {
        return this.entryDao.getAll()
                .stream().filter(entry -> entry.getPersonId() == -1)
                .collect(Collectors.toList());
    }
    
    /**
     * Get a list of all common incomes
     * @return list of all common income entries
     */
    public List<Entry> getAllCommonIncomes() {
        return this.entryDao.getAll()
                .stream().filter(entry -> entry.getPersonId() == -1 && entry.getType() == EntryType.INCOME)
                .collect(Collectors.toList());
    }
    
    /**
     * Get a list of all common expenditures
     * @return list of all common expenditure entries
     */
    public List<Entry> getAllCommonExpenditures() {
        return this.entryDao.getAll()
                .stream().filter(entry -> entry.getPersonId() == -1 && entry.getType() == EntryType.EXPENDITURE)
                .collect(Collectors.toList());
    }
    
    /**
     * Get a list of all incomes
     * @return list of all saved incomes
     */
    public List<Entry> getAllIncomes() {
        return this.entryDao.getAll()
                .stream().filter(entry -> entry.getType() == EntryType.INCOME)
                .collect(Collectors.toList());
    }
    
    /** 
     * Get a list of all expenditures
     * @return list of all saved expenditures
     */
    public List<Entry> getAllExpenditures() {
        return this.entryDao.getAll()
                .stream().filter(entry -> entry.getType() == EntryType.EXPENDITURE)
                .collect(Collectors.toList());
    }
    
    /** 
     * Get a list of all entries for a person
     * @param p person to fetch entries for
     * @return all entries for specified person
     */
    public List<Entry> getAllEntriesForPerson(Person p) {
        return this.entryDao.getAll()
                .stream().filter(entry -> entry.getPersonId() == p.getId())
                .collect(Collectors.toList());
    }
    
    /** 
     * Get a list of all incomes for a person
     * @param p person to fetch income entries for
     * @return income entries for specified person
     */
    public List<Entry> getAllIncomesForPerson(Person p) {
        return this.entryDao.getAll()
                .stream().filter(entry -> entry.getPersonId() == p.getId() && entry.getType() == EntryType.INCOME)
                .collect(Collectors.toList());
    }
    
    /** 
     * Get a list of all expenditures for a person
     * @param p person to fetch expenditure entries for
     * @return expenditure entries for specified person
     */
    public List<Entry> getAllExpendituresForPerson(Person p) {
        return this.entryDao.getAll()
                .stream().filter(entry -> entry.getPersonId() == p.getId() && entry.getType() == EntryType.EXPENDITURE)
                .collect(Collectors.toList());
    }
    
    /**
     * Remove an entry from database
     * @param id if of entry to remove
     * @return true if deletion successful, otherwise false
     */
    public boolean removeEntry(long id) {
        return this.entryDao.delete(id);
    }
    
    /**
     * Refetch all entries from database
     * @return true if refetch was successful, otherwise false
     */
    public boolean refetchEntries() {
        try {
            this.entryDao.fetchAll();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
