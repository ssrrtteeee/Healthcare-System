package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.model.Nurse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** Code behind for the Login Page.
 * @author Stefan
 * @version Fall 2024
 */
public class HomePage {
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    
    @FXML private Label currentUserLabel;
    @FXML private Button logout;
    
    private Nurse currentUser;
    
    //private VMClass viewModel;
    
    public void setUser(Nurse nurse) {
    	this.currentUser = nurse;
		this.currentUserLabel.setVisible(true);
		this.currentUserLabel.setText(currentUser.getUsername() + ", " + currentUser.getFirstName() + " " + currentUser.getLastName() + ", " + currentUser.getId());
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
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(Main.REGISTER_PATIENT_PAGE));
    	loader.load();
    	Parent parent = loader.getRoot();
    	Scene scene = new Scene(parent);
    	Stage addTodoStage = new Stage();
    	addTodoStage.setTitle(Main.TITLE);
    	addTodoStage.setScene(scene);
    	addTodoStage.initModality(Modality.APPLICATION_MODAL);
    	
    	RegisterPatient registerPatientPage = loader.getController();
    	registerPatientPage.setUser(this.currentUser);
    	
    	addTodoStage.show();
    	
    	Stage stage = (Stage) this.currentUserLabel.getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    void editPatient(ActionEvent event) throws IOException {
    }
    
    @FXML
    void initialize() {
        assert this.currentUserLabel != null : "fx:id=\"currentUserLabel\" was not injected: check your FXML file 'AddRX.fxml'.";
        assert this.logout != null : "fx:id=\"logout\" was not injected: check your FXML file 'AddRX.fxml'.";
        this.currentUserLabel.setVisible(false);
    }
}
