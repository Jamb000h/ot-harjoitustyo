/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jonne
 */
public class EntryServiceTest {
    private EntryService entryService;
    
    @Before
    public void setUp() {
        entryService = new EntryService(new FakeSQLiteEntryDao());
    }
    
    @Test
    public void creatingWithoutPersonWorks() {
        int initialSize = this.entryService.getAllIncomes().size() + this.entryService.getAllExpenditures().size();
        assertTrue(this.entryService.addEntry(50, EntryType.INCOME, "First"));
        int afterSize = this.entryService.getAllIncomes().size() + this.entryService.getAllExpenditures().size();
        assertEquals(initialSize + 1, afterSize);
    }
    
    @Test
    public void creatingWithPersonWorks() {
        int initialSize = this.entryService.getAllIncomes().size() + this.entryService.getAllExpenditures().size();
        assertTrue(this.entryService.addEntryForPerson(50, EntryType.INCOME, "First", new Person(1, "Jonne")));
        int afterSize = this.entryService.getAllIncomes().size() + this.entryService.getAllExpenditures().size();
        assertEquals(initialSize + 1, afterSize);
    }
    
    @Test
    public void gettingAllIncomesWorks() {
        int initialSize = this.entryService.getAllIncomes().size();
        assertTrue(this.entryService.addEntryForPerson(50, EntryType.INCOME, "First", new Person(1, "Jonne")));
        assertTrue(this.entryService.addEntry(50, EntryType.INCOME, "Second"));
        assertTrue(this.entryService.addEntry(50, EntryType.EXPENDITURE, "Third but I don't count"));
        int afterSize = this.entryService.getAllIncomes().size();
        assertEquals(initialSize + 2, afterSize);
    }
    
    @Test
    public void gettingAllExpendituresWorks() {
        int initialSize = this.entryService.getAllExpenditures().size();
        assertTrue(this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "First", new Person(1, "Jonne")));
        assertTrue(this.entryService.addEntryForPerson(50, EntryType.INCOME, "First ignored", new Person(2, "Jonne")));
        assertTrue(this.entryService.addEntry(50, EntryType.INCOME, "Second ignored"));
        assertTrue(this.entryService.addEntry(50, EntryType.EXPENDITURE, "Second proper!"));
        int afterSize = this.entryService.getAllExpenditures().size();
        assertEquals(initialSize + 2, afterSize);
    }
    
    @Test
    public void gettingAllCommonEntriesWorks() {
        int initialSize = this.entryService.getAllCommonEntries().size();
        assertTrue(this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "First ignored", new Person(1, "Jonne")));
        assertTrue(this.entryService.addEntry(50, EntryType.EXPENDITURE, "First that counts"));
        assertTrue(this.entryService.addEntry(50, EntryType.INCOME, "Second that counts"));
        assertTrue(this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "Second ignored", new Person(2, "Jonne")));
        int afterSize = this.entryService.getAllCommonEntries().size();
        assertEquals(initialSize + 2, afterSize);
    }
    
    @Test
    public void gettingAllEntriesForPersonWorks() {
        Person p = new Person(1, "Jonne");
        int initialSize = this.entryService.getAllEntriesForPerson(p).size();
        assertTrue(this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "First proper", p));
        assertTrue(this.entryService.addEntry(50, EntryType.EXPENDITURE, "First that is ignored"));
        assertTrue(this.entryService.addEntry(50, EntryType.INCOME, "Second that is ignored"));
        assertTrue(this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "Third ignored", new Person(2, "Jonne")));
        int afterSize = this.entryService.getAllEntriesForPerson(p).size();
        assertEquals(initialSize + 1, afterSize);
    }
}
