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
    
    private Nurse currentUser;
    
    //private VMClass viewModel;
    
    public void setUser(Nurse nurse) {
    	this.currentUser = nurse;
		this.currentUserLabel.setVisible(true);
		this.currentUserLabel.setText(currentUser.getUsername() + ", " + currentUser.getFirstName() + " " + currentUser.getLastName() + ", " + currentUser.getId());
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
    		String fname = this.fname.textProperty().getValue();
    		String lname = this.lname.textProperty().getValue();
    		String city = this.city.textProperty().getValue();
    		String address = this.address.textProperty().getValue();
    		String zip = this.zipcode.textProperty().getValue();
    		String phoneNumber = this.phoneNumber.textProperty().getValue();
    		String gender = this.gender.getValue();
    		System.out.println(gender);
    		String state = this.state.getValue().getAbbreviation();
    		LocalDate dateOfBirth = this.dateOfBirth.getValue();
    		
    		Patient patient = new Patient(fname, lname, city, address, zip, phoneNumber, gender, state, dateOfBirth);
    		DBRegisterPatient patientRegister = new DBRegisterPatient();
    		patientRegister.registerPatient(patient);
    	} catch(Exception e) {
    		
    	}
    	
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
        
        this.gender.setItems(FXCollections.observableArrayList(
        	    "Female",
        	    "Male"));
        this.state.getItems().addAll(USStates.values());
        this.currentUserLabel.setVisible(false);
    }
}
