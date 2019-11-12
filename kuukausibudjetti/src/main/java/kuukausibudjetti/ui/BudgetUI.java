/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.ui;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import javafx.application.Application;
import javafx.stage.Stage;
import kuukausibudjetti.dao.PersonDao;
import kuukausibudjetti.dao.SQLitePersonDao;
import kuukausibudjetti.db.SQLiteDatabase;
import kuukausibudjetti.domain.Person;
import kuukausibudjetti.domain.PersonService;

/**
 *
 * @author Jonne
 */
public class BudgetUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));
        
        String dbFile = properties.getProperty("dbFile");
        SQLiteDatabase db = new SQLiteDatabase(dbFile);
            
        PersonDao personDao = new SQLitePersonDao(db);
        PersonService personService = new PersonService(personDao);
        
        personService.addPerson("Jonne");
        List<Person> persons = personService.getPersons();
        persons.forEach((p) -> {
            System.out.println(p.toString());
        });
        
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.show();
    }
}
