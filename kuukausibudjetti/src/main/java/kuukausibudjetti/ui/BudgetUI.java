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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kuukausibudjetti.dao.EntryDao;
import kuukausibudjetti.dao.PersonDao;
import kuukausibudjetti.dao.SQLiteEntryDao;
import kuukausibudjetti.dao.SQLitePersonDao;
import kuukausibudjetti.db.SQLiteDatabase;
import kuukausibudjetti.domain.EntryService;
import kuukausibudjetti.domain.EntryType;
import kuukausibudjetti.domain.Person;
import kuukausibudjetti.domain.PersonService;

/**
 *
 * @author Jonne
 */
public class BudgetUI extends Application {
    
    private PersonService personService;
    private EntryService entryService;
    private VBox personList;
    private VBox incomeList;
    private Person selectedPerson;
 
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
        this.personService = new PersonService(personDao);
        this.selectedPerson = null;
        createPersonList();
        refreshPersonList();
        
        EntryDao entryDao = new SQLiteEntryDao(db);
        this.entryService = new EntryService(entryDao);
        
    }
    
    private Node createPersonListItem(Person p) {
        HBox box = new HBox(10);
        box.setMaxWidth(250);
        box.setMinWidth(250);
        box.setAlignment(Pos.CENTER_LEFT);
        
        Region rowSpacer = new Region();
        rowSpacer.setMinSize(1, 10);
        HBox.setHgrow(rowSpacer, Priority.ALWAYS);
        
        Label personNameLabel = new Label(p.getName());
        personNameLabel.setPadding(new Insets(5));
        personNameLabel.setMinWidth(170);
        personNameLabel.setCursor(Cursor.HAND);
        personNameLabel.setOnMouseClicked(e -> {
            this.selectedPerson = p;
        });
        
        personNameLabel.setOnMouseEntered(e -> {
            personNameLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        });
        
        personNameLabel.setOnMouseExited(e -> {
            personNameLabel.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        });

        Button removePersonButton = new Button("poista");
        removePersonButton.setMinWidth(50);
        removePersonButton.setOnAction(e -> {
            this.personService.removePerson(p.getId());
            refreshPersonList();
        });
        
        Pane columnSpacer = new Pane();
        columnSpacer.setMinSize(10, 1);
        columnSpacer.setMaxSize(10, 1);
        box.getChildren().addAll(personNameLabel, columnSpacer, removePersonButton);
        return box;
    }
    
    private void createPersonList() {
        VBox list = new VBox(10);
        list.setPadding(new Insets(10)); 

        this.personList = list;
    }
    
    private void refreshPersonList() {
        this.personList.getChildren().clear();
        List<Person> persons = this.personService.getPersons();
        persons.forEach(person ->{
            personList.getChildren().add(createPersonListItem(person));
        });
        this.selectedPerson = null;
    }

    @Override
    public void start(Stage primaryStage) {
        Pane wrapper = new Pane();
        
        VBox personBox = new VBox();
        personBox.setAlignment(Pos.CENTER);
        personBox.setPadding(new Insets(10)); 
        ScrollPane personListPane = new ScrollPane();
        personListPane.setContent(this.personList);
        Label personBoxLabel = new Label("HENKILÖT");

        TextField addPersonInput = new TextField();
        
        Button addPersonButton = new Button("Lisää henkilö");
        addPersonButton.setPadding(new Insets(10));
        addPersonButton.setOnAction(e -> {
            String name = addPersonInput.getText();
            this.personService.addPerson(name);
            refreshPersonList();
        });
        
        TextField addEntrySum = new TextField();
        TextField addEntryDesc = new TextField();
        Button addEntryButton = new Button("Lisää tulo");

        addEntryButton.setPadding(new Insets(10));
        addEntryButton.setOnAction(e -> {
            Integer sum = 0;
            try {
                sum = Integer.parseInt(addEntrySum.getText());
            } catch (Exception ex) {
                return;
            }
            this.entryService.addEntry(sum, EntryType.INCOME, addEntryDesc.getText());
        });
                
        personBox.getChildren().addAll(personBoxLabel, personListPane, addPersonInput, addPersonButton);
        
        VBox incomeBox = new VBox();
        incomeBox.setAlignment(Pos.CENTER_LEFT);
        incomeBox.setPadding(new Insets(10)); 
        ScrollPane incomePane = new ScrollPane();
        incomePane.setContent(this.incomeList);
        
        wrapper.getChildren().addAll(personBox);
        Scene budgetScene = new Scene(wrapper, 800, 600);
        primaryStage.setScene(budgetScene);
        primaryStage.show();
    }
}
