/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jonne
 */
public class SQLiteDatabase {
    private Connection connection;

    public SQLiteDatabase(String name) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        createTables();
    }
    
    private void createTables() {
        try {
            createPersonTable();
        } catch (SQLException e) {
            System.out.println("Either the table PERSON exists or it couldn't be created.");
        }
    }
    
    private void createPersonTable() throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "CREATE TABLE PERSON "
                + "(ID             INTEGER PRIMARY KEY      NOT NULL,"
                + " NAME           TEXT                     NOT NULL)";
        stmt.executeUpdate(sql);
        stmt.close();

    }
    
    public Connection getConnection() {
        return this.connection;
    }
}
