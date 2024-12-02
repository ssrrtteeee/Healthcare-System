package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.model.USStates;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import edu.westga.cs3230.healthcare_system.view_model.EditPatientInfoPageViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The codebehind for the edit patient info page.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class EditPatientInfoPage extends CommonFunctionality {
	@FXML private TextField address;
    @FXML private Label addressErrMsg;
    @FXML private TextField city;
    @FXML private Label cityErrMsg;
    @FXML private Button confirm;
    @FXML private Label currentUserLabel;
    @FXML private DatePicker dateOfBirth;
    @FXML private Label dobErrMsg;
    @FXML private TextField fname;
    @FXML private Label fnameErrMsg;
    @FXML private ComboBox<String> gender;
    @FXML private CheckBox isActive;
    @FXML private TextField lname;
    @FXML private Label lnameErrMsg;
    @FXML private Label phoneNumErrMsg;
    @FXML private TextField phoneNumber;
    @FXML private ComboBox<USStates> state;
    @FXML private TextField zipcode;
    @FXML private Label zipcodeErrMsg;
    
    private EditPatientInfoPageViewModel viewmodel;
        
    /**
     * Instantiates a new EditPatientInfoPage
     * 
     * @precondition true
	 * @postcondition true
     */
    public EditPatientInfoPage() {
    	this.viewmodel = new EditPatientInfoPageViewModel();
    }

    @FXML
    void initialize() {
        this.bindElements();
        this.currentUserLabel.setText(UserLogin.getUserlabel());
        this.initCommon();
    }
	
    /**
     * Sets the patient to edit.
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
		this.gender.itemsProperty().bindBidirectional(this.viewmodel.getGendersListProperty());
		this.gender.valueProperty().bindBidirectional(this.viewmodel.getGenderProperty());
		this.state.itemsProperty().bindBidirectional(this.viewmodel.getStatesProperty());
		this.state.valueProperty().bindBidirectional(this.viewmodel.getStateProperty());
		this.dateOfBirth.valueProperty().bindBidirectional(this.viewmodel.getDobProperty());
		this.isActive.selectedProperty().bindBidirectional(this.viewmodel.getIsActiveProperty());
		
		this.fnameErrMsg.textProperty().bindBidirectional(this.viewmodel.getFnameErrorMsgProperty());
		this.lnameErrMsg.textProperty().bindBidirectional(this.viewmodel.getLnameErrorMsgProperty());
		this.dobErrMsg.textProperty().bindBidirectional(this.viewmodel.getDobErrorMsgProperty());
		this.cityErrMsg.textProperty().bindBidirectional(this.viewmodel.getCityErrorMsgProperty());
		this.addressErrMsg.textProperty().bindBidirectional(this.viewmodel.getAddressErrorMsgProperty());
		this.zipcodeErrMsg.textProperty().bindBidirectional(this.viewmodel.getZipcodeErrorMsgProperty());
		this.phoneNumErrMsg.textProperty().bindBidirectional(this.viewmodel.getPhoneNumErrorMsgProperty());
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

    @FXML
    void confirmChanges() {
    	Alert dialog;
    	if (!this.viewmodel.onConfirm()) {
    		dialog = new Alert(Alert.AlertType.ERROR);
    		dialog.setTitle("Error");
    		dialog.setHeaderText("Could not update patient.");
    		
    	} else {
    		dialog = new Alert(Alert.AlertType.INFORMATION);
    		dialog.setTitle("Success");
    		dialog.setHeaderText("Updated patient successfully.");
    	}
    	dialog.showAndWait();
    }
}
