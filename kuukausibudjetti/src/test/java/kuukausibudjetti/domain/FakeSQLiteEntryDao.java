/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kuukausibudjetti.dao.EntryDao;

/**
 *
 * @author Jonne
 */
public class FakeSQLiteEntryDao implements EntryDao {
    
    private List<Entry> entries;
    
    public FakeSQLiteEntryDao() {
        this.entries = new ArrayList<>();
    }

    @Override
    public Entry create(Integer sum, EntryType type, String desc) throws SQLException {
        Entry e = new Entry(this.entries.size() + 1, sum, type, desc);
        this.entries.add(e);
        return e;
    }

    @Override
    public Entry createForPerson(Integer sum, EntryType type, String desc, Person p) throws SQLException {
        Entry e = new Entry(this.entries.size() + 1, sum, type, desc, p.getId());
        this.entries.add(e);
        return e;
    }

    @Override
    public Boolean delete(long id) {
        this.entries.removeIf(entry -> entry.getId() == id);
        return true;
    }

    @Override
    public List<Entry> getAll() {
        return this.entries;
    }

    @Override
    public List<Entry> fetchAll() throws SQLException {
        return this.entries;
    }
    
}
