package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.dal.RoutineCheckupDAL;
import edu.westga.cs3230.healthcare_system.model.Appointment;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.model.RoutineCheckup;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import edu.westga.cs3230.healthcare_system.view_model.ViewPatientInfoPageViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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
public class ViewPatientInfoPage extends CommonFunctionality {
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
    @FXML private Button viewAppointmentButton;
    @FXML private Button addRoutineCheckupButton;
    @FXML private Button orderTestsButton;
    @FXML private Button viewVisitDetailsButton;
    
    private RoutineCheckupDAL routineCheckupDB;
    private ViewPatientInfoPageViewModel viewmodel;
    
    /**
     * Instantiates a new ViewPatientInfoPage
     */
    public ViewPatientInfoPage() {
    	this.viewmodel = new ViewPatientInfoPageViewModel();
    }

    @FXML
    void initialize() {
    	this.routineCheckupDB = new RoutineCheckupDAL();
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
        this.initCommon();
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
			this.viewAppointmentButton.disableProperty().set(newVal == null);
			
			RoutineCheckup visit = this.routineCheckupDB.getRoutineCheckup(newVal.getValue().getAppointmentTime(), newVal.getValue().getDoctorId());
			
			this.addRoutineCheckupButton.disableProperty().set(visit != null);
			this.viewVisitDetailsButton.disableProperty().set(visit == null);
			
			if (visit == null) {
				this.orderTestsButton.disableProperty().set(true);
			} else {
				Boolean hasFinalDiagnosis = visit.getFinalDiagnosis() != null && !visit.getFinalDiagnosis().isBlank();
				this.orderTestsButton.disableProperty().set(hasFinalDiagnosis);
			}
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
	void viewApppointment() {
		Appointment currAppt = this.viewmodel.getSelectedAppointmentProperty().get().getValue();
		
		Dialog<Boolean> dialog = new Dialog<Boolean>();
	    dialog.setTitle("View appointment");
	    dialog.setResizable(false);

	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));

	    TextField apptTime = new TextField(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a").format(currAppt.getAppointmentTime()));
	    TextField patientName = new TextField(this.viewmodel.getPatient().getFirstName() + " " + this.viewmodel.getPatient().getLastName());
	    TextField doctorName = new TextField(this.viewmodel.getSelectedAppointmentProperty().get().getKey());
	    TextArea reason = new TextArea(currAppt.getReason());
	    
	    apptTime.editableProperty().set(false);
	    patientName.editableProperty().set(false);
	    doctorName.editableProperty().set(false);
	    reason.editableProperty().set(false);
	    
	    GridPane.setColumnSpan(reason, 2);

	    Button cancelButton = new Button("Close");
	    cancelButton.setPrefSize(80, 30);
	    
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
	    
	    grid.addRow(1, new Label("Time: "), apptTime);
	    grid.addRow(2, new Label("Patient name: "), patientName);
	    grid.addRow(3, new Label("Doctor name: "), doctorName);
	    grid.addRow(4, new Label("Reason:"));
	    grid.addRow(5, reason);
	    grid.addRow(6, cancelButton);
		
	    dialog.getDialogPane().setContent(grid);

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
	
	@FXML
	void orderTests() throws IOException {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(Main.ORDER_TESTS_PAGE));
    	loader.load();
    	
    	OrderTestsPage page = loader.getController();
    	page.setDoctor(this.appointments.getSelectionModel().getSelectedItem().getValue().getDoctorId());
    	page.setPatient(this.viewmodel.getPatient());
    	page.setAppointmentTime(this.appointments.getSelectionModel().getSelectedItem().getValue().getAppointmentTime());
    	
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
	void viewVisitDetails() throws IOException {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(Main.VIEW_VISIT_DETAILS_PAGE));
    	loader.load();
    	
    	ViewVisitDetailsPage page = loader.getController();
    	page.setDoctor(this.appointments.getSelectionModel().getSelectedItem().getValue().getDoctorId());
    	page.setPatient(this.viewmodel.getPatient());
    	page.setAppointmentTime(this.appointments.getSelectionModel().getSelectedItem().getValue().getAppointmentTime());
    	
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
