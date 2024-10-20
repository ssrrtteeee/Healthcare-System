package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.dal.DBRegisterPatient;
import edu.westga.cs3230.healthcare_system.model.Nurse;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.model.USStates;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** Code behind for the Login Page.
 * @author Stefan
 * @version Fall 2024
 */
public class RegisterPatient {
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    
    @FXML private Label currentUserLabel;
    @FXML private Button logout;
    @FXML private Button back;
    
    //Form Data
    @FXML private TextField fname;
    @FXML private TextField lname;
    @FXML private TextField city;
    @FXML private TextField address;
    @FXML private TextField zipcode;
    @FXML private TextField phoneNumber;
    @FXML private ComboBox<String> gender;
    @FXML private ComboBox<USStates> state;
    @FXML private DatePicker dateOfBirth;
    @FXML private Label zipcodeErrorLabel;
    @FXML private Label phoneNumberErrorLabel;
    @FXML private Label fNameErrorLabel;
    @FXML private Label lNameErrorLabel;
    @FXML private Label cityErrorLabel;
    @FXML private Label addressErrorLabel;
    @FXML private Label genderErrorLabel;
    @FXML private Label stateErrorLabel;
    @FXML private Label dobErrorLabel;
    
    private Nurse currentUser;
    
    //private VMClass viewModel;
    
    /**
     * Sets the current session user.
     * @param nurse the current session user
     */
    public void setUser(Nurse nurse) {
    	this.currentUser = nurse;
		this.currentUserLabel.setVisible(true);
		this.currentUserLabel.setText(this.currentUser.getUsername() + ", " + this.currentUser.getFirstName() + " " + this.currentUser.getLastName() + ", " + this.currentUser.getId());
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
    	try {
    		boolean zipError = false;
    		boolean phoneError = false;
    		boolean firstNameError = false;
    		boolean lastNameError = false;
    		boolean cityError = false;
    		boolean addressError = false;
    		boolean genderError = false;
    		boolean stateError = false;
    		boolean dateOfBirthError = false;
    		
    		String fname = this.fname.textProperty().getValue();
    		if (fname.isBlank()) {
    			firstNameError = true;
    			this.fNameErrorLabel.setText("First name should not be blank.");
    		}
    		String lname = this.lname.textProperty().getValue();
    		if (lname.isBlank()) {
    			lastNameError = true;
    			this.lNameErrorLabel.setText("Last name should not be blank.");
    		}
    		String city = this.city.textProperty().getValue();
    		if (city.isBlank()) {
    			cityError = true;
    			this.cityErrorLabel.setText("City should not be blank.");
    		}
    		String address = this.address.textProperty().getValue();
    		if (address.isBlank()) {
    			addressError = true;
    			this.addressErrorLabel.setText("Address should not be blank.");
    		}
    		String zip = this.zipcode.textProperty().getValue();
    		try {
    			if (zip.length() != 5) {
    				zipError = true;
    				this.zipcodeErrorLabel.setText("Zip code should be 5 digits.");
    			}
    		} catch (NumberFormatException ex) {
    			zipError = true;
    			this.zipcodeErrorLabel.setText("Zip code should only include numbers.");
    		}
    		
    		String phoneNumber = this.phoneNumber.textProperty().getValue();
    		try {
    			if (phoneNumber.length() != 10) {
    				phoneError = true;
    				this.phoneNumberErrorLabel.setText("Phone number should be 10 digits.");
    			}
    		} catch (NumberFormatException ex) {
    			phoneError = true;
    			this.phoneNumberErrorLabel.setText("Phone number should only include numbers.");
    		}
    		String gender = this.gender.getValue();
    		if (gender == null || gender.isBlank()) {
    			genderError = true;
    			this.genderErrorLabel.setText("Gender must be selected.");
    		}
    		String state = this.state.getValue() != null ? this.state.getValue().getAbbreviation() : null;
    		if (state == null || state.isBlank()) {
    			stateError = true;
    			this.stateErrorLabel.setText("State must be selected.");
    		}
    		LocalDate dateOfBirth = null;
    		try {
    			dateOfBirth = this.dateOfBirth.getValue();
        		if (dateOfBirth == null) {
        			dateOfBirthError = true;
        			this.dobErrorLabel.setText("Date of Birth must be selected.");
        		}
    		} catch (Exception ex) {
    			dateOfBirthError = true;
    			this.dobErrorLabel.setText("Invalid value given for date of birth.");
    		}

    		this.zipcodeErrorLabel.setVisible(zipError);
			this.phoneNumberErrorLabel.setVisible(phoneError);
			this.fNameErrorLabel.setVisible(firstNameError);
			this.lNameErrorLabel.setVisible(lastNameError);
			this.cityErrorLabel.setVisible(cityError);
			this.addressErrorLabel.setVisible(addressError);
			this.genderErrorLabel.setVisible(genderError);
			this.stateErrorLabel.setVisible(stateError);
			this.dobErrorLabel.setVisible(dateOfBirthError);
			
    		if (!zipError && !phoneError && !firstNameError && !lastNameError && !cityError && !addressError && !genderError && !stateError && !dateOfBirthError) {
        		Patient patient = new Patient(fname, lname, city, address, zip, phoneNumber, gender, state, dateOfBirth, true);
        		DBRegisterPatient patientRegister = new DBRegisterPatient();
        		patientRegister.registerPatient(patient);
        		
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
    	} catch (Exception e) {
    		
    	}
    }
    
    @FXML
    void initialize() {
    	assert this.fname != null : "fx:id=\"fname\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.lname != null : "fx:id=\"lname\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.city != null : "fx:id=\"city\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.address != null : "fx:id=\"address\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.zipcode != null : "fx:id=\"zipcode\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.phoneNumber != null : "fx:id=\"phoneNumber\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.gender != null : "fx:id=\"gender\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.state != null : "fx:id=\"state\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.dateOfBirth != null : "fx:id=\"dateOfBirth\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.logout != null : "fx:id=\"logout\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.back != null : "fx:id=\"back\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.currentUserLabel != null : "fx:id=\"currentUserLabel\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.zipcodeErrorLabel != null : "fx:id=\"zipcodeErrorLabel\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.phoneNumberErrorLabel != null : "fx:id=\"phoneNumberErrorLabel\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.fNameErrorLabel != null : "fx:id=\"fNameErrorLabel\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.lNameErrorLabel != null : "fx:id=\"lNameErrorLabel\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.cityErrorLabel != null : "fx:id=\"cityErrorLabel\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.addressErrorLabel != null : "fx:id=\"addressErrorLabel\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.genderErrorLabel != null : "fx:id=\"genderErrorLabel\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.stateErrorLabel != null : "fx:id=\"stateErrorLabel\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        assert this.dobErrorLabel != null : "fx:id=\"dobErrorLabel\" was not injected: check your FXML file 'RegisterPatient.fxml'.";
        
        this.currentUserLabel.setVisible(false);
        this.zipcodeErrorLabel.setVisible(false);
        this.phoneNumberErrorLabel.setVisible(false);
        this.fNameErrorLabel.setVisible(false);
        this.lNameErrorLabel.setVisible(false);
        this.cityErrorLabel.setVisible(false);
        this.addressErrorLabel.setVisible(false);
        this.genderErrorLabel.setVisible(false);
        this.stateErrorLabel.setVisible(false);
        this.dobErrorLabel.setVisible(false);
        
        this.gender.setItems(FXCollections.observableArrayList(
        	    "Female",
        	    "Male"));
        this.state.getItems().addAll(USStates.values());

    }
}
