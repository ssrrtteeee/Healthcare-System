package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.dal.DoctorDAL;
import edu.westga.cs3230.healthcare_system.model.Doctor;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.model.Test;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;
import edu.westga.cs3230.healthcare_system.view_model.OrderTestsPageViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * The code for the order tests page.
 * @author Stefan
 * @version Fall 2024
 *
 */
public class OrderTestsPage {
    @FXML private Button backButton;
    @FXML private Button addTestButton;
    @FXML private Button confirmButton;
    @FXML private Button removeTestButton;
    
    @FXML private Label currentUserLabel;
    @FXML private Label currentPatientLabel;
    @FXML private Label currentDoctorLabel;
    @FXML private Label orderTestsLabel;
    @FXML private Label initialDiagnosisWarning;
    
    @FXML private ListView<Test> availableTests;
    @FXML private ListView<Test> selectedTests;
    
    @FXML private TextArea initialDiagnosis;
    @FXML private TextArea availableTestsDescription;
    @FXML private TextArea selectedTestsDescription;
    
    private Patient patient;
    private int doctorId;
    private LocalDateTime appointmentTime;
    private OrderTestsPageViewModel viewModel;
    
    private DoctorDAL doctorDB;

    /**
     * Instantiates a new OrdxerTestsPage.
     */
    public OrderTestsPage() {
        this.viewModel = new OrderTestsPageViewModel();
    }

    @FXML
    void initialize() {
    	this.doctorDB = new DoctorDAL();
   		this.bindElements();
        this.currentUserLabel.setText(UserLogin.getUserlabel());
        this.setupCellFactories();
    }
    
    /**
     * Sets the doctor label with the name of the doctor with the given id
     * 
     * @precondition none
	 * @postcondition none
     * @param doctorId the ID for the doctor
     */
    public void setDoctor(int doctorId) {
    	this.doctorId = doctorId;
    	Doctor doctor = this.doctorDB.retrieveDoctor(doctorId);
    	this.currentDoctorLabel.setText("Doctor Name: " + doctor.getFirstName() + " " + doctor.getLastName());
    }
    
    /**
     * Sets the patient to the specified value
     * 
     * @precondition patient != null
	 * @postcondition none
     * @param patient the patient
     */
    public void setPatient(Patient patient) {
    	if (patient == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_PATIENT);
    	}
    	this.patient = patient;
    	this.currentPatientLabel.setText(
    			"Patient Name: " + patient.getFirstName() + " " + patient.getLastName() + System.lineSeparator()
    			+ "Patient Date of Birth: " + DateTimeFormatter.ofPattern("MM-dd-yyyy").format(patient.getDateOfBirth()));
    }
    
    /**
     * Sets the appointment time.
     * @param appointmentTime the appointment time for this visit
     */
    public void setAppointmentTime(LocalDateTime appointmentTime) {
    	this.appointmentTime = appointmentTime;
    }

    private void bindElements() {
        this.availableTests.setItems(this.viewModel.getAvailableTestsProperty());
        this.selectedTests.setItems(this.viewModel.getSelectedTestsProperty());
        
        this.initialDiagnosis.textProperty().bindBidirectional(this.viewModel.getInitialDiagnosisProperty());
        this.availableTestsDescription.textProperty().bind(this.viewModel.getAvailableTestDescriptionProperty());
        this.selectedTestsDescription.textProperty().bind(this.viewModel.getSelectedTestDescriptionProperty());
        this.viewModel.getSelectedAvailableTestsProperty().bind(this.availableTests.getSelectionModel().selectedItemProperty());
        this.viewModel.getSelectedSelectedTestsProperty().bind(this.selectedTests.getSelectionModel().selectedItemProperty());
        this.viewModel.createSelectedElementBindings();
    }
    
    private void setupCellFactories() {
        this.availableTests.setCellFactory(new Callback<ListView<Test>, ListCell<Test>>() {
			@Override
			public ListCell<Test> call(ListView<Test> appointmentsListView) {
				return new ListCell<Test>() {
					@Override
					protected void updateItem(Test item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText("");
						} else {
							setText(item.getName());
						}
					}
				};
			}
		});
        this.selectedTests.setCellFactory(new Callback<ListView<Test>, ListCell<Test>>() {
			@Override
			public ListCell<Test> call(ListView<Test> appointmentsListView) {
				return new ListCell<Test>() {
					@Override
					protected void updateItem(Test item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText("");
						} else {
							setText(item.getName());
						}
					}
				};
			}
		});
    }
    
    @FXML
    private void goBack() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(Main.VIEW_PATIENT_INFO_PAGE));
    	try {
			loader.load();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    	Parent parent = loader.getRoot();
    	Scene scene = new Scene(parent);
    	Stage addTodoStage = new Stage();
    	addTodoStage.setTitle(Main.TITLE);
    	addTodoStage.setScene(scene);
    	addTodoStage.initModality(Modality.APPLICATION_MODAL);
    	
    	ViewPatientInfoPage page = loader.getController();
    	page.setPatient(this.patient);
    	    	
    	addTodoStage.show();
    	
    	Stage stage = (Stage) this.currentUserLabel.getScene().getWindow();
    	stage.close();
    }

    @FXML
    private void addTest() {
        this.viewModel.addTestToSelected();
    }

    @FXML
    private void removeTest() {
    	this.viewModel.removeTestFromSelected();
    }

    @FXML
    private void confirmTests() {
        String initialDiagnosis = this.initialDiagnosis.getText();
		if (initialDiagnosis.isBlank()) {
			this.initialDiagnosisWarning.setText("Initial diagnosis should not be empty.");
			return;
		} else {
			this.initialDiagnosisWarning.setText("");
		}
		
		Alert alert = new Alert(AlertType.ERROR);
    	if (this.viewModel.confirmTests(this.appointmentTime, this.doctorId)) {
    		alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Success");
    		alert.setHeaderText("Successfully ordered tests.");
    		this.goBack();
    	} else {
    		alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Could not order tests.");
    	}
    	alert.showAndWait();
        
    }
}
