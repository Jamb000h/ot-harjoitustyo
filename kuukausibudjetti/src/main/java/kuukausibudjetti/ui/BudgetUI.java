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
import javafx.scene.control.ListView;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    private ListView personList;
    private ListView incomeList;
    private ListView expenditureList;
    private Person selectedPerson;
    private Label incomeBoxLabel;
    private Label expenditureBoxLabel;
    private Label entryBoxLabel;
    private Text totalIncome;
    private Text totalExpenditure;
    private Text totalDifference;
 
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
        
        this.totalIncome = new Text();
        this.totalIncome.setFont(Font.font ("Arial", 20));
        this.totalExpenditure = new Text();
        this.totalExpenditure.setFont(Font.font ("Arial", 20));
        this.totalDifference = new Text();
        this.totalDifference.setFont(Font.font ("Arial", 20));
        
        createLists();
        refreshPersonList();
        refreshEntryLists();
        refreshTotals();
    }
    
    private Node createPersonListItem(Person p) {
        HBox box = new HBox();
        box.setMaxWidth(200);
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

        Button removePersonButton = createButton("poista");
        removePersonButton.setOnAction(e -> {
            this.personService.removePerson(p.getId());
            if(this.selectedPerson != null && this.selectedPerson.getId() == p.getId()) {
                this.selectedPerson = null;
                refreshBoxLabels();
            }
            this.entryService.refetchEntries();
            refreshEntryLists();
            refreshPersonList();
            refreshTotals();
        });
        
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        box.getChildren().addAll(personNameLabel, region1, removePersonButton);
        return box;
    }
    
    private Node createCommonListItem() {
        HBox box = new HBox();
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
    
    private void refreshTotals() {
        Integer incomeTotal = this.entryService.getAllIncomes().stream().mapToInt(e -> e.getSum()).sum();
        Integer expenditureTotal = this.entryService.getAllExpenditures().stream().mapToInt(e -> e.getSum()).sum();
        Integer totalDifference = incomeTotal - expenditureTotal;
        this.totalIncome.setText(incomeTotal.toString() + "€");
        this.totalExpenditure.setText(expenditureTotal.toString() + "€");
        this.totalDifference.setText(totalDifference.toString() + "€");
    }
    
    private void refreshPersonList() {
        this.personList.getItems().clear();
        personList.getItems().add(createCommonListItem());
        List<Person> persons = this.personService.refetchPersons();
        persons.forEach(person ->{
            personList.getItems().add(createPersonListItem(person));
        });
        this.selectedPerson = null;
    }
    
    private Node createEntryListItem(Entry e) {
        HBox listItem = createListItem();
        Label entryDescLabel = new Label(e.getDesc());
        Label entrySumLabel = new Label(e.getSum().toString() + "€");
        entrySumLabel.setPadding(new Insets(0, 10, 0, 10));

        Button removeEntryButton = createButton("poista");
        removeEntryButton.setOnAction(event -> {
            if (this.entryService.removeEntry(e.getId())) {
                refreshEntryLists();
                refreshTotals();
            }
        });

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        
        listItem.getChildren().addAll(entryDescLabel, region1, entrySumLabel, removeEntryButton);
        return listItem;
    }
    
    private ListView createListWrapper() {
        ListView wrapper = new ListView();
        wrapper.setPadding(new Insets(10));
        return wrapper;
    }
    
    private void createLists() {
        this.incomeList = createListWrapper();
        this.expenditureList = createListWrapper();
        this.personList = createListWrapper();
    }
    
    private HBox createListItem() {
        HBox listItem = new HBox();
        listItem.setAlignment(Pos.CENTER_LEFT);
        return listItem;
    }
    
    private void refreshEntryLists() {
        this.incomeList.getItems().clear();
        this.expenditureList.getItems().clear();
        List<Entry> entries = new ArrayList<>();
        if (this.selectedPerson == null) {
            entries = this.entryService.getAllCommonEntries(); 
        } else {
            entries = this.entryService.getAllEntriesForPerson(this.selectedPerson);
        }
        entries.forEach(entry ->{
            if(entry.getType() == EntryType.INCOME) {
                this.incomeList.getItems().add(createEntryListItem(entry));
            } else {
                this.expenditureList.getItems().add(createEntryListItem(entry));
            }
        });
    }
    
    private Button createButton(String text) {
        Button button = new Button(text);
        button.setCursor(Cursor.HAND);
        button.setPadding(new Insets(5));
        button.setMinWidth(50);
        button.setAlignment(Pos.CENTER);
        return button;
    }
    
    private HBox createAddPersonInput() {
        HBox wrapper = new HBox();
        wrapper.setPadding(new Insets(10, 0, 0, 0));
        wrapper.setAlignment(Pos.CENTER);
        TextField addPersonInput = new TextField();
        addPersonInput.setPromptText("Nimi");
        Button addPersonButton = createButton("Lisää henkilö");
        addPersonButton.setOnAction(e -> {
            String name = addPersonInput.getText();
            this.personService.addPerson(name);
            addPersonInput.setText("");
            refreshPersonList();
            refreshEntryLists();
        });
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        wrapper.getChildren().addAll(addPersonInput, region1, addPersonButton);
        return wrapper;
    }
    
    private HBox createAddEntryInput(EntryType entryType) {
        HBox wrapper = new HBox();
        wrapper.setPadding(new Insets(10, 0, 0, 0));
        TextField addEntrySum = new TextField();
        addEntrySum.setPromptText("Summa");
        TextField addEntryDesc = new TextField();
        addEntryDesc.setMinWidth(260);
        if (entryType == EntryType.INCOME) {
            addEntryDesc.setPromptText("Selite tulolle");
        } else {
            addEntryDesc.setPromptText("Selite menolle");
        }
        Button addEntryButton = createButton("Lisää");

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
            refreshTotals();
        });
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        wrapper.getChildren().addAll(addEntryDesc, region1, addEntrySum, region2, addEntryButton);
        return wrapper;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox personBox = new VBox();
        personBox.setAlignment(Pos.TOP_CENTER);
        personBox.setPadding(new Insets(10)); 
        Label personBoxLabel = new Label("HENKILÖT");
        HBox addPersonInput = createAddPersonInput();        
        personBox.getChildren().addAll(personBoxLabel, this.personList, addPersonInput);
        
        VBox incomeBox = new VBox();
        incomeBox.setMinWidth(500);
        incomeBox.setAlignment(Pos.TOP_CENTER);
        incomeBox.setPadding(new Insets(10));
        HBox addIncomeInput = createAddEntryInput(EntryType.INCOME);
        incomeBox.getChildren().addAll(this.incomeBoxLabel, this.incomeList, addIncomeInput);
        
        VBox expenditureBox = new VBox();
        expenditureBox.setMinWidth(500);
        expenditureBox.setAlignment(Pos.TOP_CENTER);
        expenditureBox.setPadding(new Insets(10));
        HBox addExpenditureInput = createAddEntryInput(EntryType.EXPENDITURE);
        expenditureBox.getChildren().addAll(this.expenditureBoxLabel, this.expenditureList, addExpenditureInput);
        
        VBox entryListWrapper = new VBox();
        entryListWrapper.getChildren().addAll(this.entryBoxLabel, incomeBox, expenditureBox);
        
        HBox totalCalculationWrapper = new HBox();
        totalCalculationWrapper.setAlignment(Pos.CENTER);
        totalCalculationWrapper.setMaxWidth(400);
        HBox incomeWrapper = new HBox();
        incomeWrapper.getChildren().addAll(new Label("Tulot yhteensä: "), this.totalIncome);
        HBox expenditureWrapper = new HBox();
        expenditureWrapper.getChildren().addAll(new Label("Menot yhteensä: "), this.totalExpenditure);
        HBox differenceWrapper = new HBox();
        differenceWrapper.getChildren().addAll(new Label("Budjetissa löysää: "), this.totalDifference);
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        totalCalculationWrapper.getChildren().addAll(incomeWrapper, region1, expenditureWrapper, region2, differenceWrapper);
        
        HBox personEntryWrapper = new HBox();
        personEntryWrapper.getChildren().addAll(personBox, entryListWrapper);
        
        VBox wrapper = new VBox();
        wrapper.setAlignment(Pos.TOP_CENTER);
        wrapper.getChildren().addAll(personEntryWrapper, totalCalculationWrapper);
        
        Scene budgetScene = new Scene(wrapper, 1200, 800);
        primaryStage.setScene(budgetScene);
        primaryStage.show();
    }
}
