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
    
    public SQLiteEntryDao(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public Boolean create(Integer sum, EntryType type, String desc) {
        try {
            Connection connection = this.db.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO ENTRY (SUM, TYPE, DESCRIPTION) VALUES (?, ?, ?);");
            stmt.setInt(1, sum);
            stmt.setInt(2, type.getId());
            stmt.setString(3, desc);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Boolean createForPerson(Integer sum, EntryType type, String desc, Person p) {
        try {
            Connection connection = this.db.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO ENTRY (SUM, TYPE, DESCRIPTION, PERSONID) VALUES (?, ?, ?, ?);");
            stmt.setInt(1, sum);
            stmt.setInt(2, type.getId());
            stmt.setString(3, desc);
            stmt.setLong(4, p.getId());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Boolean delete(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Entry> getAll() {
        ArrayList<Entry> entries = new ArrayList<>();
        try {
            Connection connection = this.db.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ENTRY");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("ID");
                Integer sum = resultSet.getInt("SUM");
                Integer type = resultSet.getInt("TYPE");
                String desc = resultSet.getString("DESCRIPTION");
                
                if(type == EntryType.EXPENDITURE.getId()) {
                    entries.add(new Entry(id, sum, EntryType.EXPENDITURE, desc));
                    continue;
                }
                
                if(type == EntryType.INCOME.getId()) {
                    entries.add(new Entry(id, sum, EntryType.INCOME, desc));
                    continue;
                }
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error getting persons");
        }
        
        return entries;
    }
    
    @Override
    public List<Entry> getAllExpenditures() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Entry> getAllIncomes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
