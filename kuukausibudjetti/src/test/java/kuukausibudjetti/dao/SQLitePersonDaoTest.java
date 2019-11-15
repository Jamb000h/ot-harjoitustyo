/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.dao;

import kuukausibudjetti.db.SQLiteDatabase;
import kuukausibudjetti.domain.Person;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jonne
 */
public class SQLitePersonDaoTest {
    
    private PersonDao dao;
    
    @Before
    public void setUp() throws Exception {
        SQLiteDatabase db = new SQLiteDatabase(":memory:");
        this.dao = new SQLitePersonDao(db);
    }
    
    @Test
    public void initiallyZeroPersons() {
        assertTrue(this.dao.getAll().size() == 0);
    }
    
    @Test
    public void personsAddedProperly() {
        int startSize = this.dao.getAll().size();
        try {
            Person p = this.dao.create("Jonne");
            assertTrue(this.dao.getAll().size() == startSize + 1);
            assertEquals("Jonne", this.dao.getAll().get(this.dao.getAll().size() - 1).getName());
        } catch (Exception e) {
            
        }
    }
    
    @Test
    public void personsDeletedProperly() {
        int startSize = this.dao.getAll().size();
        try {
            Person p = this.dao.create("Jonne");
            assertTrue(this.dao.getAll().size() == startSize + 1);
            assertEquals("Jonne", this.dao.getAll().get(this.dao.getAll().size() - 1).getName());
            
            this.dao.delete(this.dao.getAll().get(this.dao.getAll().size() - 1).getId());
            assertTrue(this.dao.getAll().size() == startSize);
        } catch (Exception e) {
            
        }
    }
    
    
}
