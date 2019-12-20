/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.domain;

import static org.junit.Assert.assertEquals;
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
        this.entryService.addEntry(50, EntryType.INCOME, "First");
        int afterSize = this.entryService.getAllIncomes().size() + this.entryService.getAllExpenditures().size();
        assertEquals(initialSize + 1, afterSize);
    }
    
    @Test
    public void creatingWithPersonWorks() {
        int initialSize = this.entryService.getAllIncomes().size() + this.entryService.getAllExpenditures().size();
        this.entryService.addEntryForPerson(50, EntryType.INCOME, "First", new Person(1, "Jonne"));
        int afterSize = this.entryService.getAllIncomes().size() + this.entryService.getAllExpenditures().size();
        assertEquals(initialSize + 1, afterSize);
    }
    
    @Test
    public void gettingAllIncomesWorks() {
        int initialSize = this.entryService.getAllIncomes().size();
        this.entryService.addEntryForPerson(50, EntryType.INCOME, "First", new Person(1, "Jonne"));
        this.entryService.addEntry(50, EntryType.INCOME, "Second");
        this.entryService.addEntry(50, EntryType.EXPENDITURE, "Third but I don't count");
        int afterSize = this.entryService.getAllIncomes().size();
        assertEquals(initialSize + 2, afterSize);
    }
    
    @Test
    public void gettingAllExpendituresWorks() {
        int initialSize = this.entryService.getAllExpenditures().size();
        this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "First", new Person(1, "Jonne"));
        this.entryService.addEntryForPerson(50, EntryType.INCOME, "First ignored", new Person(2, "Jonne"));
        this.entryService.addEntry(50, EntryType.INCOME, "Second ignored");
        this.entryService.addEntry(50, EntryType.EXPENDITURE, "Second proper!");
        int afterSize = this.entryService.getAllExpenditures().size();
        assertEquals(initialSize + 2, afterSize);
    }
    
    @Test
    public void gettingAllCommonEntriesWorks() {
        int initialSize = this.entryService.getAllCommonEntries().size();
        this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "First ignored", new Person(1, "Jonne"));
        this.entryService.addEntry(50, EntryType.EXPENDITURE, "First that counts");
        this.entryService.addEntry(50, EntryType.INCOME, "Second that counts");
        this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "Second ignored", new Person(2, "Jonne"));
        int afterSize = this.entryService.getAllCommonEntries().size();
        assertEquals(initialSize + 2, afterSize);
    }
    
    @Test
    public void gettingAllCommonIncomesWorks() {
        int initialSize = this.entryService.getAllCommonIncomes().size();
        this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "First ignored", new Person(1, "Jonne"));
        this.entryService.addEntry(50, EntryType.EXPENDITURE, "Still nothing");
        this.entryService.addEntry(50, EntryType.INCOME, "First that counts");
        this.entryService.addEntryForPerson(50, EntryType.INCOME, "Third ignored", new Person(2, "Jonne"));
        int afterSize = this.entryService.getAllCommonIncomes().size();
        assertEquals(initialSize + 1, afterSize);
    }
    
    @Test
    public void gettingAllCommonExpendituresWorks() {
        int initialSize = this.entryService.getAllCommonExpenditures().size();
        this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "First ignored", new Person(1, "Jonne"));
        this.entryService.addEntry(50, EntryType.EXPENDITURE, "ME! ME!");
        this.entryService.addEntry(50, EntryType.INCOME, "IGNORED.");
        this.entryService.addEntryForPerson(50, EntryType.INCOME, "Third ignored", new Person(2, "Jonne"));
        int afterSize = this.entryService.getAllCommonExpenditures().size();
        assertEquals(initialSize + 1, afterSize);
    }
    
    @Test
    public void gettingAllEntriesForPersonWorks() {
        Person p = new Person(1, "Jonne");
        int initialSize = this.entryService.getAllEntriesForPerson(p).size();
        this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "First proper", p);
        this.entryService.addEntry(50, EntryType.EXPENDITURE, "First that is ignored");
        this.entryService.addEntry(50, EntryType.INCOME, "Second that is ignored");
        this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "Third ignored", new Person(2, "Jonne"));
        int afterSize = this.entryService.getAllEntriesForPerson(p).size();
        assertEquals(initialSize + 1, afterSize);
    }
    
    @Test
    public void gettingAllIncomesForPersonWorks() {
        Person p = new Person(1, "Jonne");
        int initialSize = this.entryService.getAllIncomesForPerson(p).size();
        this.entryService.addEntryForPerson(50, EntryType.INCOME, "First proper", p);
        this.entryService.addEntry(50, EntryType.EXPENDITURE, "First that is ignored");
        this.entryService.addEntry(50, EntryType.INCOME, "Second that is ignored");
        this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "Third ignored", new Person(2, "Jonne"));
        int afterSize = this.entryService.getAllIncomesForPerson(p).size();
        assertEquals(initialSize + 1, afterSize);
    }
    
    @Test
    public void gettingAllExpendituresForPersonWorks() {
        Person p = new Person(1, "Jonne");
        int initialSize = this.entryService.getAllExpendituresForPerson(p).size();
        this.entryService.addEntryForPerson(50, EntryType.INCOME, "Nope.", p);
        this.entryService.addEntry(50, EntryType.EXPENDITURE, "Second that is ignored");
        this.entryService.addEntry(50, EntryType.INCOME, "Third that is ignored");
        this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "ME! ME!", p);
        this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "fourth ignored", new Person(2, "Jonne"));
        int afterSize = this.entryService.getAllExpendituresForPerson(p).size();
        assertEquals(initialSize + 1, afterSize);
    }
    
    @Test
    public void deletionWorksForCommonEntries() {
        int initialSize = this.entryService.getAllCommonEntries().size();
        this.entryService.addEntry(50, EntryType.EXPENDITURE, "Second that is ignored");
        this.entryService.addEntry(50, EntryType.INCOME, "Third that is ignored");
        int midSize = this.entryService.getAllCommonEntries().size();
        assertEquals(initialSize + 2, midSize);
        this.entryService.removeEntry(1);
        int afterSize = this.entryService.getAllCommonEntries().size();
        assertEquals(midSize - 1, afterSize);
        assertEquals(initialSize + 1, afterSize);
    }
    
    @Test
    public void deletionWorksForPersonEntries() {
        Person p = new Person(1, "Jonne");
        int initialSize = this.entryService.getAllEntriesForPerson(p).size();
        this.entryService.addEntry(50, EntryType.EXPENDITURE, "First that is ignored");
        this.entryService.addEntry(50, EntryType.INCOME, "Second that is ignored");
        this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "ME! ME!", p);
        this.entryService.addEntryForPerson(50, EntryType.EXPENDITURE, "ME TOO", p);
        int midSize = this.entryService.getAllEntriesForPerson(p).size();
        assertEquals(initialSize + 2, midSize);
        this.entryService.removeEntry(3);
        int afterSize = this.entryService.getAllEntriesForPerson(p).size();
        assertEquals(midSize - 1, afterSize);
        assertEquals(initialSize + 1, afterSize);
    }
}
