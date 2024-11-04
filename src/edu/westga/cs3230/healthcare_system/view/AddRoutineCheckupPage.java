package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.time.LocalDateTime;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.dal.RoutineCheckupDAL;
import edu.westga.cs3230.healthcare_system.model.RoutineCheckup;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddRoutineCheckupPage {

    @FXML private Label currentUserLabel;
    @FXML
    private TextField patientHeightField;
    @FXML
    private TextField patientWeightField;
    @FXML
    private TextField systolicBPField;
    @FXML
    private TextField diastolicBPField;
    @FXML
    private TextField bodyTemperatureField;
    @FXML
    private TextField pulseField;
    @FXML
    private TextField symptomsField;
    @FXML
    private TextArea initialDiagnosisField;
    
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
    private void handleSubmit() {
        try {
            double patientHeight = Double.parseDouble(this.patientHeightField.getText());
            double patientWeight = Double.parseDouble(this.patientWeightField.getText());
            int systolicBP = Integer.parseInt(this.systolicBPField.getText());
            int diastolicBP = Integer.parseInt(this.diastolicBPField.getText());
            int bodyTemperature = Integer.parseInt(this.bodyTemperatureField.getText());
            int pulse = Integer.parseInt(this.pulseField.getText());
            String symptoms = this.symptomsField.getText();
            String initialDiagnosis = this.initialDiagnosisField.getText();

            RoutineCheckup checkup = new RoutineCheckup(this.appointmentTime, this.doctorId, UserLogin.getSessionUser().getId(),
                    patientHeight, patientWeight, systolicBP, diastolicBP,
                    bodyTemperature, pulse, symptoms, initialDiagnosis);
            
            this.routinCheckupDB.addRoutineCheckup(checkup);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Checkup Submitted");
            alert.setHeaderText(null);
            alert.setContentText("Routine Checkup submitted successfully");
            alert.showAndWait();

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