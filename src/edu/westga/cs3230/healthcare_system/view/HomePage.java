package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.ResourceBundle;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.dal.PatientDAL;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** Code behind for the Login Page.
 * @author Stefan
 * @version Fall 2024
 */

public class HomePage {
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    
    @FXML private Label currentUserLabel;
    @FXML private Button logout;
    
    @FXML
    void initialize() {
        assert this.currentUserLabel != null : "fx:id=\"currentUserLabel\" was not injected: check your FXML file 'AddRX.fxml'.";
        assert this.logout != null : "fx:id=\"logout\" was not injected: check your FXML file 'AddRX.fxml'.";
        
		this.currentUserLabel.setVisible(true);
		this.currentUserLabel.setText(UserLogin.getUserlabel());

    }
    
    @FXML
    void loadNewPage(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(Main.ADD_ROUTINE_CHECKUP_PAGE));
    	loader.load();
    	Parent parent = loader.getRoot();
    	Scene scene = new Scene(parent);
    	Stage addTodoStage = new Stage();
    	addTodoStage.setTitle(Main.TITLE);
    	addTodoStage.setScene(scene);
    	addTodoStage.initModality(Modality.APPLICATION_MODAL);
    	
    	AddRoutineCheckupPage page = loader.getController();
    	page.setDoctor(1);
    	page.setAppointmentTime(LocalDate.of(2024, Month.NOVEMBER, 12).atTime(0, 40));
    	    	
    	addTodoStage.show();
    	
    	Stage stage = (Stage) this.currentUserLabel.getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    void logout(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(Main.LOGIN_PAGE_FXML));
    	loader.load();
    	Parent parent = loader.getRoot();
    	Scene scene = new Scene(parent);
    	Stage addTodoStage = new Stage();
    	addTodoStage.setTitle(Main.TITLE);
    	addTodoStage.setScene(scene);
    	addTodoStage.initModality(Modality.APPLICATION_MODAL);
    	
    	addTodoStage.show();
    	
    	Stage stage = (Stage) this.currentUserLabel.getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    void registerPatient(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(Main.REGISTER_PATIENT_PAGE));
    	loader.load();
    	Parent parent = loader.getRoot();
    	Scene scene = new Scene(parent);
    	Stage addTodoStage = new Stage();
    	addTodoStage.setTitle(Main.TITLE);
    	addTodoStage.setScene(scene);
    	addTodoStage.initModality(Modality.APPLICATION_MODAL);
    	    	
    	addTodoStage.show();
    	
    	Stage stage = (Stage) this.currentUserLabel.getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    void viewPatientInfo(ActionEvent event) throws IOException {
    	Patient patient = this.showGetPatientDialog();
    	if (patient != null) {
        	FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(Main.class.getResource(Main.VIEW_PATIENT_INFO_PAGE));
        	loader.load();
        	ViewPatientInfoPage page = loader.getController();
        	page.setPatient(patient);
        	Parent parent = loader.getRoot();
        	Scene scene = new Scene(parent);
        	Stage addTodoStage = new Stage();
        	addTodoStage.setTitle(Main.TITLE);
        	addTodoStage.setScene(scene);
        	addTodoStage.initModality(Modality.APPLICATION_MODAL);
        	        	
        	addTodoStage.show();
        	
        	Stage stage = (Stage) this.currentUserLabel.getScene().getWindow();
        	stage.close();
    	}
    }
    
//    @FXML
//    void createAppointment(ActionEvent event) throws IOException {
//    	FXMLLoader loader = new FXMLLoader();
//    	loader.setLocation(Main.class.getResource(Main.CREATE_APPOINTMENT_PAGE));
//    	loader.load();
//    	Parent parent = loader.getRoot();
//    	Scene scene = new Scene(parent);
//    	Stage addTodoStage = new Stage();
//    	addTodoStage.setTitle(Main.TITLE);
//    	addTodoStage.setScene(scene);
//    	addTodoStage.initModality(Modality.APPLICATION_MODAL);
//    	
//    	CreateAppointmentPage createAppointmentPage = loader.getController();
//    	createAppointmentPage.setPatient(1);
//    	
//    	addTodoStage.show();
//    	
//    	Stage stage = (Stage) this.currentUserLabel.getScene().getWindow();
//    	stage.close();
//    }

    @FXML
    void editPatient(ActionEvent event) throws IOException {
    	Patient patient = this.showGetPatientDialog();
    	if (patient != null) {
        	FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(Main.class.getResource(Main.EDIT_PATIENT_INFO_PAGE));
        	loader.load();
        	EditPatientInfoPage page = loader.getController();
        	page.setPatient(patient);
        	Parent parent = loader.getRoot();
        	Scene scene = new Scene(parent);
        	Stage addTodoStage = new Stage();
        	addTodoStage.setTitle(Main.TITLE);
        	addTodoStage.setScene(scene);
        	addTodoStage.initModality(Modality.APPLICATION_MODAL);
        	
        	addTodoStage.show();
        	
        	Stage stage = (Stage) this.currentUserLabel.getScene().getWindow();
        	stage.close();
    	}
    }
    
    private Patient showGetPatientDialog() {
		Dialog<Patient> dialog = new Dialog<Patient>();
	    dialog.setTitle("Select patient");
	    dialog.setHeaderText("Enter your desired patient's info below.");
	    dialog.setResizable(false);

	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));
	    
	    Label fnameLabel = new Label("First name: ");
	    Label lnameLabel = new Label("Last name: ");
	    Label dobLabel = new Label("Date of birth: ");
	    Label errorLabel = new Label();
	    errorLabel.setTextFill(Color.RED);
	    GridPane.setColumnSpan(errorLabel, 2);
	    
	    TextField fnameTextField = new TextField();
	    TextField lnameTextField = new TextField();
	    DatePicker dobDatePicker = new DatePicker();
	    fnameTextField.setPrefSize(200, 30);
	    lnameTextField.setPrefSize(200, 30);
	    dobDatePicker.setPrefSize(200, 30);
	    
	    Button confirmButton = new Button("Confirm");
	    Button cancelButton = new Button("Cancel");
	    confirmButton.setPrefSize(80, 30);
	    cancelButton.setPrefSize(80, 30);
	    
	    grid.addRow(1, fnameLabel, fnameTextField);
	    grid.addRow(2, lnameLabel, lnameTextField);
	    grid.addRow(3, dobLabel, dobDatePicker);
	    grid.addRow(4, confirmButton, cancelButton);
	    grid.addRow(5, errorLabel);
	    
	    dialog.getDialogPane().setContent(grid);
	    
	    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Patient result = null;
				
				if (dobDatePicker.valueProperty().get() == null) {
				    PatientDAL db = new PatientDAL();
					Collection<Patient> patients = db.retrievePatients(fnameTextField.textProperty().get(), lnameTextField.textProperty().get());
					result = patients.isEmpty() ? null : HomePage.this.showPatientsList(patients);
				} else {
					PatientDAL db = new PatientDAL(); 
					if (fnameTextField.textProperty().get().isBlank() || lnameTextField.textProperty().get().isBlank()) {
						Collection<Patient> patients = db.retrievePatients(dobDatePicker.valueProperty().get());
						result = patients.isEmpty() ? null : HomePage.this.showPatientsList(patients);
					} else {
						result = db.retrievePatient(fnameTextField.textProperty().get(), lnameTextField.textProperty().get(), dobDatePicker.valueProperty().get());
					}
				}
				
				if (result == null) {
					errorLabel.textProperty().set("Could not find patient data. Please try again.");
				} else {
					dialog.setResult(result);
				}
			}
		});
	    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
				stage.close();
				dialog.resultProperty().set(null);
			}
		});
	    
	    return dialog.showAndWait().orElse(null);
	}

	private Patient showPatientsList(Collection<Patient> patients) {
		Dialog<Patient> dialog = new Dialog<Patient>();
	    dialog.setTitle("Select patient");
	    dialog.setResizable(false);

	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));
	    
	    ListView<Patient> patientsListView = new ListView<Patient>(FXCollections.observableArrayList(patients));
	    Button confirmButton = new Button("Confirm");
	    Button cancelButton = new Button("Cancel");
	    confirmButton.setPrefSize(80, 30);
	    cancelButton.setPrefSize(80, 30);
	    GridPane.setColumnSpan(patientsListView, 2);
	    patientsListView.setCellFactory(cell -> {
            return new ListCell<Patient>() {
                @Override
                protected void updateItem(Patient item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getFirstName() + " " + item.getLastName() + ", " + item.getDateOfBirth().toString());
                    }
                }
            };
        });	    confirmButton.disableProperty().set(true);
	    
	    grid.addRow(1, patientsListView);
	    grid.addRow(2, confirmButton, cancelButton);
	    
	    dialog.getDialogPane().setContent(grid);
	    
	    patientsListView.getSelectionModel().selectedItemProperty().addListener((unused, oldVal, newVal) -> {
	    	if (newVal != null) {
	    		confirmButton.disableProperty().set(false);
	    	}
	    });
	    
	    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.setResult(patientsListView.getSelectionModel().getSelectedItem());
			}
		});
	    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
				stage.close();
				dialog.resultProperty().set(null);
			}
		});
	    
	    return dialog.showAndWait().orElse(null);
	}
}
