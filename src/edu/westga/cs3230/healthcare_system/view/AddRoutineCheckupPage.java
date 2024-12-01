package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.dal.DoctorDAL;
import edu.westga.cs3230.healthcare_system.dal.RoutineCheckupDAL;
import edu.westga.cs3230.healthcare_system.model.Doctor;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.model.RoutineCheckup;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The CodeBehind for the AddRoutineCheckup page
 * 
 * @author Stefan
 * @version Spring 2024
 */
public class AddRoutineCheckupPage extends CommonFunctionality {

    @FXML private Label currentUserLabel;
    @FXML private Label currentPatientLabel;
    @FXML private Label currentDoctorLabel;
    
    @FXML private TextField patientHeight;
    @FXML private TextField patientWeight;
    @FXML private TextField systolicBP;
    @FXML private TextField diastolicBP;
    @FXML private TextField bodyTemperature;
    @FXML private TextField pulse;
    @FXML private TextArea symptoms;
    @FXML private TextArea initialDiagnosis;
    
    @FXML private Text heightWarning;
    @FXML private Text weightWarning;
    @FXML private Label systolicBPWarning;
    @FXML private Label diastolicBPWarning;
    @FXML private Label bodyTemperatureWarning;
    @FXML private Label pulseWarning;
    @FXML private Label symptomsWarning;
    
    private LocalDateTime appointmentTime;
    private int doctorId;
    private RoutineCheckupDAL routineCheckupDB;
    private Patient patient;
    
    private DoctorDAL doctorDB;
    
    @FXML
    void initialize() {
        this.currentUserLabel.setText(UserLogin.getUserlabel());
        this.doctorDB = new DoctorDAL();
        this.routineCheckupDB = new RoutineCheckupDAL();
        this.initCommon();
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
    void handleSubmit() {
    	boolean hasError = false;
        try {
        	Double patientHeight = (double) 0;
        	boolean heightFieldHasError = false;
        	try {
        		String patientHeightString = this.patientHeight.getText();
        		patientHeight = Double.parseDouble(patientHeightString);
        		if (patientHeightString.isBlank()) {
        			heightFieldHasError = true;
        		}
        		if (patientHeightString.contains(".")) {
        			String[] splitDecimal = patientHeightString.split("\\.");
        			if (splitDecimal[1] != null && ((splitDecimal[0].length() + splitDecimal[1].length()) > 4 || splitDecimal[1].length() > 2)) {
        				heightFieldHasError = true;
        			}
        		} else {
        			if (patientHeightString.length() > 3) {
        				heightFieldHasError = true;
        			}
        		}
        	} catch (Exception ex) {
        		heightFieldHasError = true;
        	}
        	if (!heightFieldHasError) {
        		this.heightWarning.setText("");
        	} else {
				this.heightWarning.setText("Must be 1 digit and less than 4, with not more than 2 decimals.");
        		hasError = true;
        	}
        	
        	Double patientWeight = (double) 0;
        	boolean weightFieldHasError = false;
        	try {
        		String patientWeightString = this.patientWeight.getText();
        		patientWeight = Double.parseDouble(patientWeightString);
        		if (patientWeightString.isBlank()) {
        			weightFieldHasError = true;
        		}
        		if (patientWeightString.contains(".")) {
        			String[] splitDecimal = patientWeightString.split("\\.");
        			if ((splitDecimal[0].length() + splitDecimal[1].length()) > 4 || splitDecimal[1].length() > 2) {
        				weightFieldHasError = true;
        			}
        		} else {
        			if (patientWeightString.length() > 3) {
        				weightFieldHasError = true;
        			}
        		}
        	} catch (Exception ex) {
        		weightFieldHasError = true;
        	}
        	if (!weightFieldHasError) {
        		this.weightWarning.setText("");
        	} else {
				this.weightWarning.setText("Must be 1 digit and less than 4, with not more than 2 decimals.");
        		hasError = true;
        	}
        	
        	int systolicBP = 0;
        	try {
        		systolicBP = Integer.parseInt(this.systolicBP.getText());
        		if (this.systolicBP.getText().isBlank()) {
        			throw new IllegalArgumentException();
        		}
        		this.systolicBPWarning.setText("");
        	} catch (Exception ex) {
        		hasError = true;
        		this.systolicBPWarning.setText("Systolic BP should be at least 1 digit");
        	}
        	
        	int diastolicBP = 0;
        	try {
        		diastolicBP = Integer.parseInt(this.diastolicBP.getText());
        		if (this.diastolicBP.getText().isBlank()) {
        			throw new IllegalArgumentException();
        		}
        		this.diastolicBPWarning.setText("");
        	} catch (Exception ex) {
        		hasError = true;
        		this.diastolicBPWarning.setText("Diastolic BP should be at least 1 digit ");
        	}
            
        	int bodyTemperature = 0;
        	try {
        		bodyTemperature = Integer.parseInt(this.bodyTemperature.getText());
        		if (this.bodyTemperature.getText().isBlank()) {
        			throw new IllegalArgumentException();
        		}
        		this.bodyTemperatureWarning.setText("");
        	} catch (Exception ex) {
        		hasError = true;
        		this.bodyTemperatureWarning.setText("Body Temperature should be at least 1 digit ");
        	}
            
        	int pulse = 0;
        	try {
        		pulse = Integer.parseInt(this.pulse.getText());
        		if (this.pulse.getText().isBlank()) {
        			throw new IllegalArgumentException();
        		}
        		this.pulseWarning.setText("");
        	} catch (Exception ex) {
        		hasError = true;
        		this.pulseWarning.setText("Pulse should be at least 1 digit");
        	}
            
            String symptoms = this.symptoms.getText();
    		if (symptoms.isBlank()) {
    			hasError = true;
    			this.symptomsWarning.setText("Symptoms should not be empty.");
    		} else {
    			this.symptomsWarning.setText("");
    		}
    		
            String initialDiagnosis = this.initialDiagnosis.getText();

            if (!hasError) {
	            RoutineCheckup checkup = new RoutineCheckup(this.appointmentTime, this.doctorId, UserLogin.getSessionUser().getId(),
	            		patientHeight, patientWeight, systolicBP, diastolicBP,
	                    bodyTemperature, pulse, symptoms, initialDiagnosis);
	            
	            this.routineCheckupDB.addRoutineCheckup(checkup);
	            this.goBack();
	
	            Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Checkup Submitted");
	            alert.setHeaderText(null);
	            alert.setContentText("Routine Checkup submitted successfully");
	            alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid input values.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    private void goBack() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(Main.VIEW_PATIENT_INFO_PAGE));
    	try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
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