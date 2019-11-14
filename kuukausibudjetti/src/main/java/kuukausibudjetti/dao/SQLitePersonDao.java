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
import kuukausibudjetti.domain.Person;

/**
 *
 * @author Jonne
 */
public class SQLitePersonDao implements PersonDao {
    
    private final SQLiteDatabase db;
    
    public SQLitePersonDao(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public Boolean create(String name) {
        try {
            Connection connection = this.db.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO PERSON (NAME) VALUES (?);");
            stmt.setString(1, name);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Person> getAll() {
        ArrayList<Person> persons = new ArrayList<>();
        try {
            Connection connection = this.db.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PERSON");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");

                Person p = new Person(id, name);
                persons.add(p);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error getting persons");
        }
        
        return persons;
    }

    @Override
    public Boolean delete(long id) {
        try {
            Connection connection = this.db.getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM PERSON WHERE ID = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
}
