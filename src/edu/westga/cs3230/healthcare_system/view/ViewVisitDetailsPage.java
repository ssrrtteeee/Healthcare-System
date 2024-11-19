package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.dal.DoctorDAL;
import edu.westga.cs3230.healthcare_system.dal.NurseDAL;
import edu.westga.cs3230.healthcare_system.dal.RoutineCheckupDAL;
import edu.westga.cs3230.healthcare_system.model.Doctor;
import edu.westga.cs3230.healthcare_system.model.Nurse;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.model.RoutineCheckup;
import edu.westga.cs3230.healthcare_system.model.Test;
import edu.westga.cs3230.healthcare_system.model.TestResults;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;
import edu.westga.cs3230.healthcare_system.view_model.ViewVisitDetailsPageViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Creates the view visit details page.
 * @author Stefan
 * @version Fall 2024
 *
 */
public class ViewVisitDetailsPage {

	@FXML private Label currentUserLabel;
    @FXML private Label currentPatientLabel;
    @FXML private Label currentDoctorLabel;
    @FXML private Label currentNurseLabel;
    
    @FXML private TextField patientHeight;
    @FXML private TextField patientWeight;
    @FXML private TextField systolicBP;
    @FXML private TextField diastolicBP;
    @FXML private TextField bodyTemperature;
    @FXML private TextField pulse;
    @FXML private TextArea symptoms;
    @FXML private TextArea initialDiagnosis;
    @FXML private TextArea finalDiagnosis;

    @FXML private ListView<Test> orderedTests;
    @FXML private ListView<TestResults> performedTests;
	
    @FXML private TextArea orderedTestsDetails;
    @FXML private TextArea performedTestsResults;
    
    private LocalDateTime appointmentTime;
    private int doctorId;
    private Patient patient;
    
    private NurseDAL nurseDB;
    private RoutineCheckupDAL routineCheckupDB;
    private DoctorDAL doctorDB;
    private boolean setDoctorId = false;
    private boolean setAppointmentTime = false;
    
    private ViewVisitDetailsPageViewModel viewModel;
    
    @FXML
    void initialize() {
    	this.viewModel = new ViewVisitDetailsPageViewModel();
        this.currentUserLabel.setText(UserLogin.getUserlabel());
        this.doctorDB = new DoctorDAL();
        this.routineCheckupDB = new RoutineCheckupDAL();
        this.nurseDB = new NurseDAL();
	    this.bindElements();
	    this.setupCellFactories();
    }
    
    private void setupCellFactories() {
        this.orderedTests.setCellFactory(new Callback<ListView<Test>, ListCell<Test>>() {
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
        this.performedTests.setCellFactory(new Callback<ListView<TestResults>, ListCell<TestResults>>() {
			@Override
			public ListCell<TestResults> call(ListView<TestResults> appointmentsListView) {
				return new ListCell<TestResults>() {
					@Override
					protected void updateItem(TestResults item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText("");
						} else {
							setText(item.getTestName());
						}
					}
				};
			}
		});
    }
    
    private void bindElements() {
        this.orderedTests.setItems(this.viewModel.getOrderedTestsProperty());
        this.performedTests.setItems(this.viewModel.getPerformedTestsProperty());
    	
        this.orderedTestsDetails.textProperty().bind(this.viewModel.getOrderedTestDescriptionProperty());
        this.performedTestsResults.textProperty().bind(this.viewModel.getPerformedTestDescriptionProperty());
        this.viewModel.getSelectedOrderedTestsProperty().bind(this.orderedTests.getSelectionModel().selectedItemProperty());
        this.viewModel.getSelectedPerformedTestsProperty().bind(this.performedTests.getSelectionModel().selectedItemProperty());
        this.viewModel.createSelectedElementBindings();
    }
    
    private void loadCheckupDetails() {
    	if (!this.setDoctorId || !this.setAppointmentTime)  {
    		return;
    	}
    	RoutineCheckup checkup = this.routineCheckupDB.getRoutineCheckup(this.appointmentTime, this.doctorId);
    	if (checkup != null) {
    		this.viewModel.loadAvailableTests(this.appointmentTime, this.doctorId);
    		Nurse nurse = this.nurseDB.getNurse(checkup.getRecordingNurseId());
        	this.currentNurseLabel.setText("Recording nurse Name: " + nurse.getFirstName() + " " + nurse.getLastName());
        	 this.patientHeight.setText(String.valueOf(checkup.getPatientHeight()));
        	 this.patientWeight.setText(String.valueOf(checkup.getPatientWeight()));
        	 this.systolicBP.setText(String.valueOf(checkup.getSystolicBP()));
        	 this.diastolicBP.setText(String.valueOf(checkup.getDiastolicBP()));
        	 this.bodyTemperature.setText(String.valueOf(checkup.getBodyTemperature()));
        	 this.pulse.setText(String.valueOf(checkup.getPulse()));
        	 this.symptoms.setText(checkup.getSymptoms());
        	 this.initialDiagnosis.setText(checkup.getInitialDiagnosis());
        	 this.finalDiagnosis.setText(checkup.getFinalDiagnosis());
    	}
    }
    
    /**
     * Sets the appointment date and time to the specified value.
     * 
     * @precondition time != null
     * @postcondition true
     * @param time the time
     */
    public void setAppointmentTime(LocalDateTime time) {
    	if (time == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_APMT_TIME);
    	}
    	this.appointmentTime = time;
    	this.setAppointmentTime = true;
    	this.loadCheckupDetails();
    }
    
    /**
     * Sets the doctor to the one with the specified ID
     * 
     * @precondition none
	 * @postcondition none
     * @param doctorId the ID
     */
    public void setDoctor(int doctorId) {
    	this.doctorId = doctorId;
    	Doctor doctor = this.doctorDB.retrieveDoctor(doctorId);
    	this.currentDoctorLabel.setText("Doctor Name: " + doctor.getFirstName() + " " + doctor.getLastName());
    	this.setDoctorId = true;
    	this.loadCheckupDetails();
    }
    
    /**
     * Sets the patient to the specified value
     * 
     * @precondition patient != null
	 * @postcondition true
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
}
