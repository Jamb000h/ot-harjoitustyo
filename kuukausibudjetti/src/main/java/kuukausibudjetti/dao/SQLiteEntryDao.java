/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import kuukausibudjetti.db.SQLiteDatabase;
import kuukausibudjetti.domain.Entry;
import kuukausibudjetti.domain.EntryType;
import kuukausibudjetti.domain.Person;

/**
 *
 * @author Jonne
 */
public class SQLiteEntryDao implements EntryDao {
    
    private final SQLiteDatabase db;
    private List<Entry> entries;
    
    public SQLiteEntryDao(SQLiteDatabase db) {
        this.db = db;
        this.entries = new ArrayList<>();
        try {
            fetchAll();
        } catch (SQLException e) {
            System.out.println("Error getting entries!");
        }
    }

    @Override
    public Entry create(Integer sum, EntryType type, String desc) throws SQLException {
        try {
            Connection connection = this.db.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO ENTRY (SUM, TYPE, DESCRIPTION) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, sum);
            stmt.setInt(2, type.getId());
            stmt.setString(3, desc);
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            long newId = (long) generatedKeys.getLong(1);
            stmt.close();
            Entry e = new Entry(newId, sum, type, desc);
            this.entries.add(e);
            return e;
        } catch (SQLException e) {
            throw(e);
        }
    }

    @Override
    public Entry createForPerson(Integer sum, EntryType type, String desc, Person p) throws SQLException {
        Connection connection = this.db.getConnection();
        PreparedStatement stmt = 
                connection.prepareStatement(
                        "INSERT INTO ENTRY (SUM, TYPE, DESCRIPTION, PERSONID) VALUES (?, ?, ?, ?);", 
                        Statement.RETURN_GENERATED_KEYS
        );
        stmt.setInt(1, sum);
        stmt.setInt(2, type.getId());
        stmt.setString(3, desc);
        stmt.setLong(4, p.getId());
        stmt.executeUpdate();
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        long newId = (long) generatedKeys.getLong(1);
        stmt.close();
        Entry e = new Entry(newId, sum, type, desc, p.getId());
        this.entries.add(e);
        return e;
    }

    @Override
    public Boolean delete(long id) {
        try {
            Connection connection = this.db.getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM ENTRY WHERE ID = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            stmt.close();
            this.entries.removeIf(entry -> entry.getId() == id);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Entry> getAll() {
        return this.entries;
    }
    
    public List<Entry> fetchAll() throws SQLException {
        ArrayList<Entry> entryList = new ArrayList<>();
        Connection connection = this.db.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ENTRY");
        ResultSet resultSet = stmt.executeQuery();
        
        while (resultSet.next()) {
            long id = resultSet.getLong("ID");
            Integer sum = resultSet.getInt("SUM");
            EntryType type = getEntryTypeById(resultSet.getInt("TYPE"));
            String desc = resultSet.getString("DESCRIPTION");
            long personId = -1;
            if (resultSet.getLong("PERSONID") != 0) {
                personId = resultSet.getLong("PERSONID");
            }
            entryList.add(new Entry(id, sum, type, desc, personId));
        }
        
        stmt.close();
        this.entries = entryList;
        return this.entries;
    }
    
    private EntryType getEntryTypeById(int typeId) {
        if (typeId == EntryType.EXPENDITURE.getId()) {
            return EntryType.EXPENDITURE;
        }
        
        return EntryType.INCOME;
    }
}
