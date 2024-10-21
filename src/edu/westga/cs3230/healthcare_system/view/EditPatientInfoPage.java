package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.model.Nurse;
import edu.westga.cs3230.healthcare_system.model.USStates;
import edu.westga.cs3230.healthcare_system.view_model.EditPatientInfoPageViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The codebehind for the edit patient info page.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class EditPatientInfoPage {
    @FXML
    private Label currentUserLabel;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private Spinner<Integer> patientIdSpinner;
    
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField city;
    @FXML
    private TextField address;
    @FXML
    private TextField zipcode;
    @FXML
    private TextField phoneNumber;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private ComboBox<USStates> state;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private CheckBox isActive;
    @FXML
    private Button confirm;
    
    private EditPatientInfoPageViewModel viewmodel;
    private Nurse currentUser;
    
    /**
     * Instantiates a new EditPatientInfoPage
     */
    public EditPatientInfoPage() {
    	this.viewmodel = new EditPatientInfoPageViewModel();
    }
    
	@FXML
    void initialize() {
        this.patientIdSpinner.setValueFactory(new IntegerSpinnerValueFactory(0, 999999999, 0, 1));
        this.bindElements();
    }
	
	private void bindElements() {
		this.patientIdSpinner.getValueFactory().valueProperty().bindBidirectional(this.viewmodel.getIdProperty());
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
		
		this.fname.disableProperty().bind(this.viewmodel.getControlsActiveProperty());
		this.lname.disableProperty().bind(this.viewmodel.getControlsActiveProperty());
		this.city.disableProperty().bind(this.viewmodel.getControlsActiveProperty());
		this.address.disableProperty().bind(this.viewmodel.getControlsActiveProperty());
		this.zipcode.disableProperty().bind(this.viewmodel.getControlsActiveProperty());
		this.phoneNumber.disableProperty().bind(this.viewmodel.getControlsActiveProperty());
		this.gender.disableProperty().bind(this.viewmodel.getControlsActiveProperty());
		this.state.disableProperty().bind(this.viewmodel.getControlsActiveProperty());
		this.dateOfBirth.disableProperty().bind(this.viewmodel.getControlsActiveProperty());
		this.confirm.disableProperty().bind(this.viewmodel.getControlsActiveProperty());
		this.isActive.disableProperty().bind(this.viewmodel.getControlsActiveProperty());
	}

	@FXML
    void backToHomePage(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(Main.HOME_PAGE));
    	loader.load();
    	Parent parent = loader.getRoot();
    	Scene scene = new Scene(parent);
    	Stage addTodoStage = new Stage();
    	addTodoStage.setTitle(Main.TITLE);
    	addTodoStage.setScene(scene);
    	addTodoStage.initModality(Modality.APPLICATION_MODAL);
    	
    	HomePage homePage = loader.getController();
    	homePage.setUser(this.currentUser);
    	
    	addTodoStage.show();
    	
    	Stage stage = (Stage) this.currentUserLabel.getScene().getWindow();
    	stage.close();
    }
	
	/**
     * Sets the user.
     * 
     * @precondition user != null
     * @postcondition true
     * @param user the user
     */
    public void setUser(Nurse user) {
    	if (user == null) {
    		throw new IllegalArgumentException("User cannot be null.");
    	}
    	this.currentUser = user;
		this.currentUserLabel.setVisible(true);
		this.currentUserLabel.setText(HomePage.getUserlabel(user));
    }
    
    @FXML
    void confirmChanges() {
    	this.viewmodel.onConfirm();
    }
}
