import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javafx.util.Callback;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import java.io.*;
/**
 * @author Nate Dunlap
 */
public class Main extends Application implements Serializable {

    Stage window; 
    TableView<Event> table;    
    TextField amountInput;
    TextField timeInput;
    TextField timeInputEnd;
    TextField dateInput;
    TextField descInput;
    BorderPane layout;
    private  double amount = 0;
    private String type = "";
    private String date = "";
    private String time = "";
    private String desc = "";
    private int totalEvents = 0;
    private int totalDrinkEvents = 0;
    private int totalExerciseEvents = 0;
    private int totalSleepEvents = 0;

    public static void main(String[] args) {
        launch(args);
    }

    //Builds window
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Event Tracker");
        
        Text totalEventsText = new Text("Events logged: " + totalEvents);
        totalEventsText.setFont(Font.font("Sans serif", 17));
        
        Text totalDrinkEventsText = new Text("Drink Events: " + totalDrinkEvents);
        totalDrinkEventsText.setFont(Font.font("Sans serif", 13));
        
        Text totalExerciseEventsText = new Text("Exercise Events: " + totalExerciseEvents);
        totalExerciseEventsText.setFont(Font.font("Sans serif", 13));
        
        Text totalSleepEventsText = new Text("Sleep Events: " + totalSleepEvents);
        totalSleepEventsText.setFont(Font.font("Sans serif", 13));
        
        //Menu Items
        Menu fileMenu = new Menu("_File");
        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(e -> clearTable(totalEventsText));
        fileMenu.getItems().add(newFile);
 
        MenuItem exportFile = new MenuItem("Export...");
        exportFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e)  {
              try {
                writeExcel();
              }
              catch (Exception ex) {
            ex.printStackTrace();
        }
    }          
});		fileMenu.getItems().add(exportFile);
        fileMenu.getItems().add(new MenuItem("Import..."));
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Exit"));
        
        //Edit Menu
        Menu editMenu = new Menu("_Edit");
        editMenu.getItems().add(new MenuItem());
        
       //Menu Bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu);
        
        layout = new BorderPane();
        layout.setTop(menuBar);
        
        Callback<TableColumn, TableCell> cellFactory =
             new Callback<TableColumn, TableCell>() {
                 public TableCell call(TableColumn p) {
                    return new EditingCell();
                 }
             };
        
             ////Table columns
             //Event Column
        TableColumn<Event, String> eventColumn = new TableColumn<>("Event");
        eventColumn.setMinWidth(200);
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        eventColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        eventColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<Event, String>>() {
                @Override
                public void handle(CellEditEvent<Event, String> t) {
                    ((Event) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setType(t.getNewValue());
                }
             }
        );

        //Amount Column
        TableColumn<Event, Double> amountColumn = new TableColumn<>("Amount (Fl OZ)");
        amountColumn.setMinWidth(150);
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        amountColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<Event, Double>>() {
                @Override
                public void handle(CellEditEvent<Event, Double> t) {
                    ((Event) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setAmount(t.getNewValue());
                }
             }
        );
        
        //Time Column
        TableColumn<Event, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setMinWidth(150);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        timeColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<Event, String>>() {
                @Override
                public void handle(CellEditEvent<Event, String> t) {
                    ((Event) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setTime(t.getNewValue());
                }
             }
        );
        
        //Date Column
        TableColumn<Event, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(100);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<Event, String>>() {
                @Override
                public void handle(CellEditEvent<Event, String> t) {
                    ((Event) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setDate(t.getNewValue());
                }
             }
        );
        
        //Description Column
        TableColumn<Event, String> descColumn = new TableColumn<>("Description");
        descColumn.setMinWidth(320);
        descColumn.setCellValueFactory(new PropertyValueFactory<>("desc"));
        descColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<Event, String>>() {
                @Override
                public void handle(CellEditEvent<Event, String> t) {
                    ((Event) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setDesc(t.getNewValue());
                }
             }
        );
        
        //Invalid input alerts
        
        Alert timeAlert = new Alert(AlertType.ERROR);
        timeAlert.setTitle("Invalid Input");
        timeAlert.setHeaderText("Error");
        timeAlert.setContentText("Please utilize format HH:MM AM/PM");
        
        Alert dateAlert = new Alert(AlertType.ERROR);
        dateAlert.setTitle("Invalid Input");
        dateAlert.setHeaderText("Error");
        dateAlert.setContentText("Please utilize format MM/DD/YYYY");

        //Amount input
        amountInput = new TextField();
        amountInput.setPromptText("Enter Amount");
        amountInput.setMinWidth(100);
        amountInput.setDisable(true);
        amountInput.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { 
                    if (!amountInput.getText().matches("[0-9](\\.[0-9]{1,2}){0,1}|10(\\.0{1,2}){0,1}")) {
                        
                    	amountInput.setText("");
                    	
                    }
                }
            });
        
        //Time input
        timeInput = new TextField();
        timeInput.setPromptText("Enter Start Time");
        timeInput.setMinWidth(100);
        timeInput.setDisable(true);
        timeInput.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { 
                    if (!timeInput.getText().matches("[0-1][0-9]:[0-5][0-9] AM") && !timeInput.getText().matches("[0-1][0-9]:[0-5][0-9] PM") && !timeInput.getText().matches("")) { 
                    	timeInput.setText("");
                    	timeAlert.showAndWait();
                    }
                }
            });
        
        //End time input
        timeInputEnd = new TextField();
        timeInputEnd.setPromptText("Enter End Time");
        timeInputEnd.setMinWidth(100);
        timeInputEnd.setDisable(true);
        timeInputEnd.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { 
            	if (!timeInputEnd.getText().matches("[0-1][0-9]:[0-5][0-9] AM") && !timeInputEnd.getText().matches("[0-1][0-9]:[0-5][0-9] PM") && !timeInputEnd.getText().matches("")) {
                        
                    	timeInputEnd.setText("");
                    	timeAlert.showAndWait();
                    }
                }
            });
        
        //Date input
        dateInput = new TextField();
        dateInput.setPromptText("Enter Date MM/DD/YYYY");
        dateInput.setMinWidth(100);
        dateInput.setDisable(true);
        dateInput.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { 
                    if (!dateInput.getText().matches("[0-1][0-9]/[0-3][0-9]/[0-2][0-9][0-9][0-9]") && !dateInput.getText().matches("")) { 
                    	dateInput.setText("");
                    	dateAlert.showAndWait();
                    }
                }
            });
        
        //Description input
        descInput = new TextField();
        descInput.setPromptText("Enter a description");
        descInput.setMinWidth(625);
        descInput.setDisable(true);
        
        
        //Buttons
        Button addButton = new Button("Add Event");
        addButton.setOnAction(e -> addButtonClicked(totalEventsText));
        addButton.setDisable(true);
       
        Button deleteButton = new Button("Delete Event");
        deleteButton.setOnAction(e -> deleteButtonClicked(totalEventsText, addButton));
        
        Button createDrink = new Button("Drink Event");
        createDrink.setMinSize(100, 100);
        createDrink.setOnAction(e -> drinkButtonClicked(addButton));
        
        Button createSleep = new Button("Sleep Event");
        createSleep.setMinSize(100, 100);
        createSleep.setOnAction(e -> sleepButtonClicked(addButton));
        
        Button createExercise = new Button("Exercise Event");
        createExercise.setMinSize(100, 100);
        createExercise.setOnAction(e -> exerciseButtonClicked(addButton));
        
        Button refreshButton = new Button("Refresh");
        refreshButton.setMinSize(165, 10);
        refreshButton.setOnAction(e -> refreshButtonClicked(totalEventsText, totalDrinkEventsText, totalExerciseEventsText, totalSleepEventsText));

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(20);
        hBox.getChildren().addAll(createDrink, createExercise, createSleep, totalEventsText, totalDrinkEventsText, totalExerciseEventsText, totalSleepEventsText);
        
        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(10,10,10,10));
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(amountInput, timeInput, timeInputEnd, dateInput, addButton, deleteButton);
        
        HBox hBox3 = new HBox();
        hBox3.setPadding(new Insets(10,10,10,10));
        hBox3.setSpacing(10);
        hBox3.getChildren().addAll(descInput, refreshButton);

        table = new TableView<>();
        table.getColumns().addAll(eventColumn, amountColumn, timeColumn, dateColumn, descColumn);
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(layout, table, hBox, hBox2, hBox3);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
        table.setEditable(true);
        
    }
    
    //Add button clicked
    public void addButtonClicked(Text totalEventsText){
    	Event newEvent = new DrinkEvent();
    	newEvent.setType(type);
    	System.out.println(newEvent.getType());
    	if (amountInput.getText().isEmpty()) {
    		amount = 0;
    	} else {
    		amount = Double.parseDouble(amountInput.getText());
    	}
    	if (newEvent.getType() == "Drink") {
    		totalDrinkEvents += 1;
    		newEvent.setTime(timeInput.getText());
    	} 
    	if (newEvent.getType() == "Exercise"){
    		totalExerciseEvents += 1;
    		String startTime = timeInput.getText();
    		String endTime = timeInputEnd.getText();
    		String timeFrame = startTime + " - " + endTime;
    	    newEvent.setTime(timeFrame);
    	} 
    	if (newEvent.getType() == "Sleep") {
    		totalSleepEvents += 1;
    		String startTime = timeInput.getText();
    		String endTime = timeInputEnd.getText();
    		String timeFrame = startTime + " - " + endTime;
    	    newEvent.setTime(timeFrame);
    				
    	}
        newEvent.setAmount(amount);
        newEvent.setDate(dateInput.getText());
        newEvent.setDesc(descInput.getText());
        table.getItems().add(newEvent);
        totalEvents += 1;
    }
    
    //Clears text fields
    
    public void update(Button addButton) {
    	addButton.setDisable(false);
    	amountInput.clear();
        timeInput.clear();
        timeInputEnd.clear();
        descInput.clear();
        amountInput.setDisable(true);
    	timeInput.setDisable(true);
    	timeInputEnd.setDisable(true);
    	dateInput.setDisable(true);
    	descInput.setDisable(true);
    }
    
    //Drink Button Clicked
    public void drinkButtonClicked(Button addButton) {
    	update(addButton);
    	type = "Drink";
    	amountInput.setDisable(false);
    	timeInput.setDisable(false);
    	dateInput.setDisable(false);
    	descInput.setDisable(false);
    }
    
  //Exercise Button Clicked
    public void exerciseButtonClicked(Button addButton) {
    	update(addButton);
    	type = "Exercise";
    	timeInput.setDisable(false);
    	timeInputEnd.setDisable(false);
    	dateInput.setDisable(false);
    	descInput.setDisable(false);
    }
    
  //Sleep Button Clicked
    public void sleepButtonClicked(Button addButton) {
    	update(addButton);
    	type = "Sleep";
    	timeInput.setDisable(false);
    	timeInputEnd.setDisable(false);
    	dateInput.setDisable(false);
    	descInput.setDisable(false);
    }

    //Delete button clicked
    public void deleteButtonClicked(Text totalEventsText, Button addButton){
    	update(addButton);
    	addButton.setDisable(true);
        ObservableList<Event> EventSelected, allEvents;
        allEvents = table.getItems();
        EventSelected = table.getSelectionModel().getSelectedItems();
        EventSelected.forEach(allEvents::remove);
        if (totalEvents > 0) {
	        totalEvents -= 1;
        }
    }
    
  //Refresh Button Clicked (Updates Event counters)
    public void refreshButtonClicked(Text totalEventsText, Text totalDrinkEventsText, Text totalExerciseEventsText, Text totalSleepEventsText) {
    	totalEventsText.setText("Events logged: " + totalEvents);
    	totalDrinkEventsText.setText("Drink Events: " + totalDrinkEvents);
    	totalExerciseEventsText.setText("Exercise Events: " + totalExerciseEvents);
    	totalSleepEventsText.setText("Sleep Events: " + totalSleepEvents);
    }
    
    //Clears list. Starts a new one
    public void clearTable(Text totalEventsText) {
    	table.getItems().clear();
    	totalEvents = 0;
    	totalDrinkEvents = 0;
    	totalExerciseEvents = 0;
    	totalSleepEvents = 0;
    }
    public ObservableList<Event> data = FXCollections.observableArrayList();
    
    //Persistence method (WIP)
    
    public void writeExcel() throws Exception {
        Writer writer = null;
        try {
            File file = new File("EventList.csv");
            writer = new BufferedWriter(new FileWriter(file));
            for (Event outEvent : data) {

                String text = outEvent.getType() + "," + outEvent.getAmount() + "," + outEvent.getTime() + "\n";
                System.out.println("printed");
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {

            writer.flush();
             writer.close();
        } 
    }
}
   
   