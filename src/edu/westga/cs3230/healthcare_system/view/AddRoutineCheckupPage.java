package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.PatternSyntaxException;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.dal.RoutineCheckupDAL;
import edu.westga.cs3230.healthcare_system.model.RoutineCheckup;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
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

public class AddRoutineCheckupPage {

    @FXML private Label currentUserLabel;
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
    @FXML private Label initialDiagnosisWarning;
    
    private LocalDateTime appointmentTime;
    private int doctorId;
    private RoutineCheckupDAL routinCheckupDB;
    
    @FXML
    void initialize() {
        this.currentUserLabel.setText(UserLogin.getUserlabel());
    }
    
    public void setAppointmentTime(LocalDateTime time) {
    	this.appointmentTime = time;
    }
    
    public void setDoctor(int doctorId) {
    	this.doctorId = doctorId;
    }
    
    public AddRoutineCheckupPage() {
    	this.routinCheckupDB = new RoutineCheckupDAL();
    }
    
    @FXML
    void handleSubmit() {
    	boolean hasError = false;
        try {
        	Double patientHeight = (double) 0;
        	try {
        		patientHeight = Double.parseDouble(this.patientHeight.getText());
        		if (this.patientHeight.getText().length() > 4 || this.patientHeight.getText().isBlank() || this.patientHeight.getText().split(".")[1].length() > 2) {
        			throw new IllegalArgumentException();
        		}
        		this.heightWarning.setText("");
        	} catch (PatternSyntaxException ex) {
        		this.heightWarning.setText("");
        	} catch (Exception ex) {
        		this.heightWarning.setText("Must be 1 digit and less than 5, with not more than 2 decimals.");
        		hasError = true;
        	}
        	
        	Double patientWeight = (double) 0;
        	try {
        		patientWeight = Double.parseDouble(this.patientWeight.getText());
        		if (this.patientWeight.getText().length() > 4 || this.patientWeight.getText().isBlank() || this.patientWeight.getText().split(".")[1].length() > 2) {
        			throw new IllegalArgumentException();
        		}
        		this.weightWarning.setText("");
        	} catch (PatternSyntaxException ex) {
        		this.weightWarning.setText("");
        	} catch (Exception ex) {
        		this.weightWarning.setText("Weight must be at least 1 digit and less than 5, with not more than 2 decimals.");
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
    		if (initialDiagnosis.isBlank()) {
    			hasError = true;
    			this.initialDiagnosisWarning.setText("Initial diagnosis should not be empty.");
    		} else {
    			this.initialDiagnosisWarning.setText("");
    		}

            if (!hasError) {
	            RoutineCheckup checkup = new RoutineCheckup(this.appointmentTime, this.doctorId, UserLogin.getSessionUser().getId(),
	            		patientHeight, patientWeight, systolicBP, diastolicBP,
	                    bodyTemperature, pulse, symptoms, initialDiagnosis);
	            
	            this.routinCheckupDB.addRoutineCheckup(checkup);
	
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
    	loader.setLocation(Main.class.getResource(Main.HOME_PAGE));
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
    	    	
    	addTodoStage.show();
    	
    	Stage stage = (Stage) this.currentUserLabel.getScene().getWindow();
    	stage.close();
    }
}