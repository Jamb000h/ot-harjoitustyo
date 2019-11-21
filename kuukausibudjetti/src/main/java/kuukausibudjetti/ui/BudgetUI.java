/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kuukausibudjetti.ui;

import java.io.FileInputStream;
import java.util.ArrayList;
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
import kuukausibudjetti.domain.Entry;
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
    private VBox expenditureList;
    private Person selectedPerson;
    private Label incomeBoxLabel;
    private Label expenditureBoxLabel;
    private Label entryBoxLabel;
 
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
        
        EntryDao entryDao = new SQLiteEntryDao(db);
        this.entryService = new EntryService(entryDao);
        
        this.entryBoxLabel = new Label("YHTEISET");
        this.expenditureBoxLabel = new Label("MENOT");
        this.incomeBoxLabel = new Label("TULOT");
        
        setStyles();
        createLists();
        refreshPersonList();
        refreshEntryLists();
    }
    
    private Node createPersonListItem(Person p) {
        HBox box = new HBox(10);
        box.setMaxWidth(250);
        box.setMinWidth(250);
        box.setAlignment(Pos.CENTER_LEFT);
        
        Label personNameLabel = new Label(p.getName());
        personNameLabel.setPadding(new Insets(5));
        personNameLabel.setMinWidth(170);
        personNameLabel.setCursor(Cursor.HAND);
        personNameLabel.setOnMouseClicked(e -> {
            this.selectedPerson = p;
            refreshEntryLists();
            refreshBoxLabels();
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
            if(this.selectedPerson.getId() == p.getId()) {
                this.selectedPerson = null;
                refreshEntryLists();
                refreshBoxLabels();
            }
            refreshPersonList();
        });
        
        Pane columnSpacer = new Pane();
        columnSpacer.setMinSize(10, 1);
        columnSpacer.setMaxSize(10, 1);
        box.getChildren().addAll(personNameLabel, columnSpacer, removePersonButton);
        return box;
    }
    
    private Node createCommonListItem() {
        HBox box = new HBox(10);
        box.setMaxWidth(250);
        box.setMinWidth(250);
        box.setAlignment(Pos.CENTER_LEFT);
        
        Label label = new Label("Yhteiset");
        label.setPadding(new Insets(5));
        label.setMinWidth(170);
        label.setCursor(Cursor.HAND);
        label.setOnMouseClicked(e -> {
            this.selectedPerson = null;
            refreshEntryLists();
            refreshBoxLabels();
        });
        
        label.setOnMouseEntered(e -> {
            label.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        });
        
        label.setOnMouseExited(e -> {
            label.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        });
        
        Pane columnSpacer = new Pane();
        columnSpacer.setMinSize(10, 1);
        columnSpacer.setMaxSize(10, 1);
        box.getChildren().addAll(label);
        return box;
    }
    
    private void refreshBoxLabels() {
        if(this.selectedPerson == null) {
            this.incomeBoxLabel.setText("TULOT");
            this.expenditureBoxLabel.setText("MENOT");
            this.entryBoxLabel.setText("YHTEISET");
            return;
        }
        
            this.incomeBoxLabel.setText("TULOT - " + this.selectedPerson.getName());
            this.expenditureBoxLabel.setText("MENOT - " + this.selectedPerson.getName());
            this.entryBoxLabel.setText(this.selectedPerson.getName());
    }
    
    private void refreshPersonList() {
        this.personList.getChildren().clear();
        personList.getChildren().add(createCommonListItem());
        List<Person> persons = this.personService.refetchPersons();
        persons.forEach(person ->{
            personList.getChildren().add(createPersonListItem(person));
        });
        this.selectedPerson = null;
    }
    
    private Node createEntryListItem(Entry e) {
        HBox listItem = createListItem();
        Label entryLabel = createListItemLabel(e.getDesc() + e.getSum().toString());

        Button removeEntryButton = new Button("poista");
        removeEntryButton.setMinWidth(50);
        removeEntryButton.setOnAction(event -> {
            this.entryService.removeEntry(e.getId());
            refreshEntryLists();
        });

        listItem.getChildren().addAll(entryLabel, removeEntryButton);
        return listItem;
    }
    
    private VBox createListWrapper() {
        VBox wrapper = new VBox();
        wrapper.setPadding(new Insets(10));
        wrapper.setAlignment(Pos.CENTER_LEFT);
        return wrapper;
    }
    
    private void createLists() {
        this.incomeList = createListWrapper();
        this.expenditureList = createListWrapper();
        this.personList = createListWrapper();
    }
    
    private void setStyles() {
        Region colSpacer = new Region();
        colSpacer.setMinSize(1, 10);
        HBox.setHgrow(colSpacer, Priority.ALWAYS);
        
        Region rowSpacer = new Region();
        rowSpacer.setMinSize(1, 10);
        VBox.setVgrow(rowSpacer, Priority.ALWAYS);
    }
    
    private HBox createListItem() {
        HBox listItem = new HBox(10);
        listItem.setMaxWidth(450);
        listItem.setMinWidth(450);
        listItem.setAlignment(Pos.CENTER_LEFT);
        return listItem;
    }
    
    private Label createListItemLabel(String labelText) {
        Label label = new Label(labelText);
        label.setPadding(new Insets(5));
        label.setMinWidth(150);
        return label;
    }
    
    private void refreshEntryLists() {
        this.incomeList.getChildren().clear();
        this.expenditureList.getChildren().clear();
        List<Entry> entries = new ArrayList<>();
        if (this.selectedPerson == null) {
            entries = this.entryService.getAllCommonEntries(); 
        } else {
            entries = this.entryService.getAllEntriesForPerson(this.selectedPerson);
        }
        entries.forEach(entry ->{
            if(entry.getType() == EntryType.INCOME) {
                this.incomeList.getChildren().add(createEntryListItem(entry));
            } else {
                this.expenditureList.getChildren().add(createEntryListItem(entry));
            }
        });
    }
    
    private HBox createAddPersonInput() {
        HBox wrapper = new HBox();
        TextField addPersonInput = new TextField();
        
        Button addPersonButton = new Button("Lisää henkilö");
        addPersonButton.setPadding(new Insets(10));
        addPersonButton.setOnAction(e -> {
            String name = addPersonInput.getText();
            this.personService.addPerson(name);
            addPersonInput.setText("");
            refreshPersonList();
            refreshEntryLists();
        });
        wrapper.getChildren().addAll(addPersonInput, addPersonButton);
        return wrapper;
    }
    
    private HBox createAddEntryInput(EntryType entryType) {
        HBox wrapper = new HBox();
        Label sumLabel = new Label("Summa");
        Label descLabel = new Label("Selite");
        TextField addEntrySum = new TextField();
        TextField addEntryDesc = new TextField();
        Button addEntryButton = new Button("Lisää");

        addEntryButton.setPadding(new Insets(10));
        addEntryButton.setOnAction(e -> {
            Integer sum = 0;
            try {
                sum = Integer.parseInt(addEntrySum.getText());
            } catch (Exception ex) {
                return;
            }
            if (this.selectedPerson == null) {
                this.entryService.addEntry(sum, entryType, addEntryDesc.getText());
            } else {
                this.entryService.addEntryForPerson(sum, entryType, addEntryDesc.getText(), this.selectedPerson);
            }
            addEntrySum.setText("");
            addEntryDesc.setText("");
            refreshEntryLists();
        });
        wrapper.getChildren().addAll(sumLabel, addEntrySum, descLabel, addEntryDesc, addEntryButton);
        return wrapper;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox personBox = new VBox();
        personBox.setAlignment(Pos.CENTER);
        personBox.setPadding(new Insets(10)); 
        ScrollPane personListPane = new ScrollPane();
        personListPane.setContent(this.personList);
        Label personBoxLabel = new Label("HENKILÖT");
        HBox addPersonInput = createAddPersonInput();        
        personBox.getChildren().addAll(personBoxLabel, personListPane, addPersonInput);
        
        VBox incomeBox = createListWrapper();
        ScrollPane incomePane = new ScrollPane();
        incomePane.setContent(this.incomeList);
        HBox addIncomeInput = createAddEntryInput(EntryType.INCOME);
        incomeBox.getChildren().addAll(this.incomeBoxLabel, incomePane, addIncomeInput);
        
        VBox expenditureBox = createListWrapper();
        ScrollPane expenditurePane = new ScrollPane();
        expenditurePane.setContent(this.expenditureList);
        HBox addExpenditureInput = createAddEntryInput(EntryType.EXPENDITURE);
        expenditureBox.getChildren().addAll(this.expenditureBoxLabel, expenditurePane, addExpenditureInput);
        
        VBox entryListWrapper = new VBox();
        entryListWrapper.getChildren().addAll(this.entryBoxLabel, incomeBox, expenditureBox);
        
        HBox wrapper = new HBox();
        wrapper.getChildren().addAll(personBox, entryListWrapper);
        
        Scene budgetScene = new Scene(wrapper, 800, 600);
        primaryStage.setScene(budgetScene);
        primaryStage.show();
    }
}
