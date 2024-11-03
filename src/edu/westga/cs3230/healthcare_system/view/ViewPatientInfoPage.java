package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import edu.westga.cs3230.healthcare_system.view_model.ViewPatientInfoPageViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The codebehind for the edit patient info page.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class ViewPatientInfoPage {
    @FXML private Label currentUserLabel;
    @FXML private TextField fname;
    @FXML private TextField lname;
    @FXML private DatePicker dateOfBirth;
    @FXML private TextField gender;
    @FXML private TextField city;
    @FXML private TextField state;
	@FXML private TextField address;
    @FXML private TextField zipcode;
    @FXML private TextField phoneNumber;
    @FXML private CheckBox isActive;
    
    private ViewPatientInfoPageViewModel viewmodel;
    
    /**
     * Instantiates a new ViewPatientInfoPage
     */
    public ViewPatientInfoPage() {
    	this.viewmodel = new ViewPatientInfoPageViewModel();
    }

    @FXML
    void initialize() {
        this.bindElements();
        this.currentUserLabel.setText(UserLogin.getUserlabel());
        this.isActive.disableProperty().set(true);
    }
	
    /**
     * Sets the patient to view.
     * 
     * @precondition patient != null
     * @postcondition true
     * @param patient the patient
     */
	public void setPatient(Patient patient) {
		this.viewmodel.setPatient(patient);
	}
    
	private void bindElements() {
		this.fname.textProperty().bindBidirectional(this.viewmodel.getFnameProperty());
		this.lname.textProperty().bindBidirectional(this.viewmodel.getLnameProperty());
		this.city.textProperty().bindBidirectional(this.viewmodel.getCityProperty());
		this.address.textProperty().bindBidirectional(this.viewmodel.getAddressProperty());
		this.zipcode.textProperty().bindBidirectional(this.viewmodel.getZipcodeProperty());
		this.phoneNumber.textProperty().bindBidirectional(this.viewmodel.getPhoneNumProperty());
		this.gender.textProperty().bindBidirectional(this.viewmodel.getGenderProperty());
		this.state.textProperty().bindBidirectional(this.viewmodel.getStateProperty());
		this.dateOfBirth.valueProperty().bindBidirectional(this.viewmodel.getDobProperty());
		this.isActive.selectedProperty().bindBidirectional(this.viewmodel.getIsActiveProperty());
	}

	@FXML
    void backToHomePage() {
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
