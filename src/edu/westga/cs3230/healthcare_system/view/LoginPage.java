package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.view_model.LoginPageViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** Code behind for the Login Page.
 * @author Stefan
 * @version 12/1/2022
 */
public class LoginPage {
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Label errorMessageLabel;
    @FXML private Button login;
    
    private LoginPageViewModel loginViewModel;

    @FXML
    void login(ActionEvent event) throws IOException {
    	try {
    		if (this.loginViewModel.login(this.username.textProperty().getValue(), this.password.textProperty().getValue())) {
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
            	homePage.setUser(this.loginViewModel.getUserDetails(this.username.textProperty().getValue(), this.password.textProperty().getValue()));
            	
            	addTodoStage.show();
            	
            	Stage stage = (Stage) this.username.getScene().getWindow();
            	stage.close();
    		}
    	} catch (IllegalArgumentException exception) {
    		this.errorMessageLabel.setVisible(true);
    		this.errorMessageLabel.setText(exception.getMessage());
    	}
    }
    
    @FXML
    void initialize() {
        assert this.username != null : "fx:id=\"username\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert this.password != null : "fx:id=\"password\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert this.errorMessageLabel != null : "fx:id=\"errorMessageLabel\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert this.login != null : "fx:id=\"login\" was not injected: check your FXML file 'LoginPage.fxml'.";
        this.errorMessageLabel.setVisible(false);
        try {
			this.loginViewModel = new LoginPageViewModel();
		} catch (IOException exception) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("File Misloaded");
			alert.setHeaderText("File Misloaded");
			alert.setContentText("File with user information misloaded");

			alert.showAndWait();
		}
    }
}
