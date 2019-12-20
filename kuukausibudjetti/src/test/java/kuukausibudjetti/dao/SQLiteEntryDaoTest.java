/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.dao;

import kuukausibudjetti.db.SQLiteDatabase;
import kuukausibudjetti.domain.Entry;
import kuukausibudjetti.domain.EntryType;
import kuukausibudjetti.domain.Person;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jonne
 */
public class SQLiteEntryDaoTest {
    private EntryDao dao;
    private PersonDao personDao;
    
    @Before
    public void setUp() throws Exception {
        SQLiteDatabase db = new SQLiteDatabase(":memory:");
        this.dao = new SQLiteEntryDao(db);
        this.personDao = new SQLitePersonDao(db);
    }
    
    @Test
    public void initiallyZeroPersons() {
        assertTrue(this.dao.getAll().size() == 0);
    }
    
    @Test
    public void commonEntryAddedProperly() {
        int startSize = this.dao.getAll().size();
        try {
            Entry e = this.dao.create(0, EntryType.INCOME, "Description for test!?=89");
            assertTrue(this.dao.getAll().size() == startSize + 1);
            assertEquals("Description for test!?=89", this.dao.getAll().get(this.dao.getAll().size() - 1).getDesc());
        } catch (Exception e) {
            
        }
    }
    
    @Test
    public void personEntryAddedProperly() {
        int startSize = this.dao.getAll().size();
        try {
            Person p = this.personDao.create("Jonne");
            Entry e = this.dao.createForPerson(500, EntryType.INCOME, "Whoa I am an entry", p);
            assertTrue(this.dao.getAll().size() == startSize + 1);
            assertEquals("Whoa I am an entry", this.dao.getAll().get(this.dao.getAll().size() - 1).getDesc());
            assertEquals(p.getId(), e.getPersonId());
        } catch (Exception e) {
            
        }
    }
    
    @Test
    public void entryDeletedProperly() {
        int startSize = this.dao.getAll().size();
        try {
            Person p = this.personDao.create("Jonne");
            Entry e = this.dao.createForPerson(500, EntryType.INCOME, "Whoa I am an entry", p);
            assertTrue(this.dao.getAll().size() == startSize + 1);
            this.dao.delete(e.getId());
            assertTrue(this.dao.getAll().size() == startSize);
        } catch (Exception e) {
            
        }
    }
}
