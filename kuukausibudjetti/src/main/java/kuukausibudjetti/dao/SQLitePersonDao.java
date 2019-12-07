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
import kuukausibudjetti.domain.Person;

/**
 *
 * @author Jonne
 */
public class SQLitePersonDao implements PersonDao {
    
    private final SQLiteDatabase db;
    private List<Person> persons;
    
    public SQLitePersonDao(SQLiteDatabase db) {
        this.db = db;
        this.persons = new ArrayList<>();
        try {
            fetchAll();
        } catch (SQLException e) {
            System.out.println("Error fetchin persons!");
        }
    }

    /**
     * Save a new person to database and return it
     * @param name name of person to create
     * @return created person
     * @throws SQLException SQL exception if failure
     */
    @Override
    public Person create(String name) throws SQLException {
        try {
            Connection connection = this.db.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO PERSON (NAME) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            long id = stmt.executeUpdate();
            stmt.close();
            Person p = new Person(id, name);
            this.persons.add(p);
            return p;
        } catch (SQLException e) {
            throw(e);
        }
    }

    /**
     * Get all persons
     * @return list of all persons
     */
    @Override
    public List<Person> getAll() {
        return this.persons;
    }

    /**
     * Delete a person
     * @param id id of person to remove
     * @return true if deletion successful, otherwise false
     */
    @Override
    public Boolean delete(long id) {
        try {
            Connection connection = this.db.getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM PERSON WHERE ID = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            stmt.close();
            this.persons.removeIf(person -> person.getId() == id);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Fetch all persons from database and return them
     * @return a list of all persons from database
     * @throws SQLException SQL exception if failure
     */
    @Override
    public List<Person> fetchAll() throws SQLException {
        ArrayList<Person> personList = new ArrayList<>();
        Connection connection = this.db.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PERSON");
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            long id = resultSet.getLong("ID");
            String name = resultSet.getString("NAME");

            Person p = new Person(id, name);
            personList.add(p);
        }
        stmt.close();
        this.persons = personList;
        return this.persons;
    }
    
}
