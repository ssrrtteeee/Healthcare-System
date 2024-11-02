package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.model.Nurse;
import edu.westga.cs3230.healthcare_system.model.USStates;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import edu.westga.cs3230.healthcare_system.view_model.EditPatientInfoPageViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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
    private TextField address;
    @FXML
    private Label addressErrMsg;
    @FXML
    private TextField city;
    @FXML
    private Label cityErrMsg;
    @FXML
    private Button confirm;
    @FXML
    private Label currentUserLabel;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private Label dobErrMsg;
    @FXML
    private TextField fname;
    @FXML
    private Label fnameErrMsg;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private CheckBox isActive;
    @FXML
    private TextField lname;
    @FXML
    private Label lnameErrMsg;
    @FXML
    private Label phoneNumErrMsg;
    @FXML
    private TextField phoneNumber;
    @FXML
    private ComboBox<USStates> state;
    @FXML
    private TextField zipcode;
    @FXML
    private Label zipcodeErrMsg;
    
    
    private EditPatientInfoPageViewModel viewmodel;
        
    /**
     * Instantiates a new EditPatientInfoPage
     */
    public EditPatientInfoPage() {
    	this.viewmodel = new EditPatientInfoPageViewModel();
    }

    @FXML
    void initialize() {
        this.bindElements();
        this.currentUserLabel.setText(UserLogin.getUserlabel());
        this.showGetPatientDialog();
    }
	
	private void showGetPatientDialog() {
		Dialog<Boolean> dialog = new Dialog<Boolean>();
	    dialog.setTitle("Select patient");
	    dialog.setHeaderText("Enter your desired patient's info below.");
	    dialog.setResizable(false);

	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));
	    
	    Label fnameLabel = new Label("First name: ");
	    Label lnameLabel = new Label("Last name: ");
	    Label dobLabel = new Label("Date of birth: ");
	    Label errorLabel = new Label();
	    errorLabel.setTextFill(Color.RED);
	    GridPane.setColumnSpan(errorLabel, 2);
	    
	    TextField fnameTextField = new TextField();
	    TextField lnameTextField = new TextField();
	    DatePicker dobDatePicker = new DatePicker();
	    fnameTextField.setPrefSize(200, 30);
	    lnameTextField.setPrefSize(200, 30);
	    dobDatePicker.setPrefSize(200, 30);
	    
	    Button confirmButton = new Button("Confirm");
	    Button cancelButton = new Button("Cancel");
	    confirmButton.setPrefSize(80, 30);
	    cancelButton.setPrefSize(80, 30);
	    
	    grid.addRow(1, fnameLabel, fnameTextField);
	    grid.addRow(2, lnameLabel, lnameTextField);
	    grid.addRow(3, dobLabel, dobDatePicker);
	    grid.addRow(4, confirmButton, cancelButton);
	    grid.addRow(5, errorLabel);
	    
	    dialog.getDialogPane().setContent(grid);

	    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (fnameTextField.textProperty().get() == null
						|| lnameTextField.textProperty().get() == null
						|| dobDatePicker.valueProperty().get() == null
				) {
					errorLabel.textProperty().set("Please fill in all data before proceeding.");
				} else {
					EditPatientInfoPage.this.viewmodel.getPatient(fnameTextField.textProperty().get(), lnameTextField.textProperty().get(), dobDatePicker.valueProperty().get());
					
					if (EditPatientInfoPage.this.viewmodel.getControlsActiveProperty().get()) {
						errorLabel.textProperty().set("Could not find patient data. Please try again.");
					} else {
						Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
						stage.close();
					}
				}
			}
		});
		
	    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
				stage.close();
				dialog.resultProperty().set(true);
			}
		});
	    
	    dialog.showAndWait();
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
    	
	    this.showGetPatientDialog();
    	
		this.currentUserLabel.setVisible(true);
		this.currentUserLabel.setText(UserLogin.getUserlabel());
    }
    
    @FXML
    void selectNewPatient() {
    	this.showGetPatientDialog();
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
