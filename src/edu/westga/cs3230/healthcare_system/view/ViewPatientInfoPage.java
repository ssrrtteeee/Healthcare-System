package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.model.Appointment;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import edu.westga.cs3230.healthcare_system.view_model.ViewPatientInfoPageViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

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
    @FXML private ListView<Pair<String, Appointment>> appointments;
    @FXML private Button editAppointmentButton;
    @FXML private Button addRoutineCheckupButton;
    
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
        
        this.appointments.setCellFactory(new Callback<ListView<Pair<String, Appointment>>, ListCell<Pair<String, Appointment>>>() {
			@Override
			public ListCell<Pair<String, Appointment>> call(ListView<Pair<String, Appointment>> appointmentsListView) {
				return new ListCell<Pair<String, Appointment>>() {
					@Override
					protected void updateItem(Pair<String, Appointment> item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText("");
						} else {
							setText(item.getKey() + ", " + DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a").format(item.getValue().getAppointmentTime()));
						}
					}
				};
			};
		});
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
		this.appointments.itemsProperty().bindBidirectional(this.viewmodel.getAppointmentsProperty());
		this.viewmodel.getSelectedAppointmentProperty().bind(this.appointments.getSelectionModel().selectedItemProperty());
		
		this.viewmodel.getSelectedAppointmentProperty().addListener((unused, oldVal, newVal) -> {
			this.editAppointmentButton.disableProperty().set(newVal == null || newVal.getValue().getAppointmentTime().isBefore(LocalDateTime.now()));
			this.addRoutineCheckupButton.disableProperty().set(newVal == null);
		});
	}
	
	@FXML
    void scheduleAppointment() {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(Main.CREATE_APPOINTMENT_PAGE));
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
    	
    	CreateAppointmentPage page = loader.getController();
    	page.setPatient(this.viewmodel.getPatient());
    	    	
    	addTodoStage.show();
    	
    	Stage stage = (Stage) this.currentUserLabel.getScene().getWindow();
    	stage.close();
    }
	
	@FXML
	void addRoutineCheckup() {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(Main.ADD_ROUTINE_CHECKUP_PAGE));
    	try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	AddRoutineCheckupPage page = loader.getController();
    	page.setDoctor(this.appointments.getSelectionModel().getSelectedItem().getValue().getDoctorId());
    	page.setAppointmentTime(this.appointments.getSelectionModel().getSelectedItem().getValue().getAppointmentTime());
    	page.setPatient(this.viewmodel.getPatient());
    	
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
	void editAppointment() throws IOException {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(EditAppointmentPage.LOCATION));
    	loader.load();
    	
    	EditAppointmentPage page = loader.getController();
    	page.setPatient(this.viewmodel.getPatient());
    	page.setAppointment(this.appointments.getSelectionModel().getSelectedItem().getValue());
    	
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
