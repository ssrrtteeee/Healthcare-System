package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.model.Doctor;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import edu.westga.cs3230.healthcare_system.view_model.CreateAppointmentPageViewModel;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * The codebehind for the create appointment page.
 * 
 * @author Stefan
 * @version Fall 2024
 */
public class CreateAppointmentPage {
    @FXML private Button createAppointment;
    @FXML private Button selectDoctor;
    @FXML private DatePicker appointmentDate;
    @FXML private ComboBox<LocalTime> appointmentTime;
    @FXML private TextArea appointmentReason;
    @FXML private Label doctorName;
    
    @FXML private Label doctorErrMsg;
    @FXML private Label appointmentDateErrMsg;
    @FXML private Label appointmentTimeErrMsg;
    @FXML private Label appointmentReasonErrMsg;
	
    @FXML private Label currentUserLabel;
    @FXML private Label patientInfoLabel;
    
    private CreateAppointmentPageViewModel viewmodel;
        
    /**
     * Instantiates a new CreateAppointmentPage
     */
    public CreateAppointmentPage() {
    	this.viewmodel = new CreateAppointmentPageViewModel();
    }

    @FXML
    void initialize() {
        this.currentUserLabel.setText(UserLogin.getUserlabel());
        this.appointmentTime.setCellFactory(new Callback<ListView<LocalTime>, ListCell<LocalTime>>() {
			@Override
			public ListCell<LocalTime> call(ListView<LocalTime> timeSlotsListView) {
				return new ListCell<LocalTime>() {
					@Override
					protected void updateItem(LocalTime item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText("No available time slots.");
						} else {
							setText(DateTimeFormatter.ofPattern("hh:mm a").format(item));
						}
					}
				};
			};
		});
		this.appointmentTime.setButtonCell(new ListCell<LocalTime>() {
			@Override
			protected void updateItem(LocalTime item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText("Please select a time.");
				} else {
					setText(DateTimeFormatter.ofPattern("hh:mm a").format(item));
				}
			}
		});
    }
    
    @FXML
    void selectDoctor() {
		Dialog<Boolean> dialog = new Dialog<Boolean>();
		dialog.setTitle("Search Doctors");
	    dialog.setHeaderText("Search by.");
	    dialog.setResizable(false);

	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    
	    Button nameButton = new Button("Name");
	    Button specialtyButton = new Button("Specialty");
	    nameButton.setPrefSize(80, 30);
	    specialtyButton.setPrefSize(80, 30);
	    grid.addRow(1, nameButton, specialtyButton);
	    
	    dialog.getDialogPane().setContent(grid);
		
	    nameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.setResult(true);
			}
		});
	    specialtyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.setResult(false);
			}
		});
	    
		Optional<Boolean> result = dialog.showAndWait();
		if (result.isPresent()) {
			if (result.get()) {
		    	this.showGetDoctorByNameDialog();
			} else {
				this.showGetDoctorBySpecialtyDialog();
			}
		}
    }
    
    private void showGetDoctorByNameDialog() {
		Dialog<Boolean> dialog = new Dialog<Boolean>();
	    dialog.setTitle("Select Doctor");
	    dialog.setHeaderText("Enter the doctor's info below.");
	    dialog.setResizable(false);

	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));
	    
	    Label fnameLabel = new Label("First name: ");
	    Label lnameLabel = new Label("Last name: ");
	    Label errorLabel = new Label();
	    errorLabel.setTextFill(Color.RED);
	    GridPane.setColumnSpan(errorLabel, 2);
	    
	    TextField fnameTextField = new TextField();
	    TextField lnameTextField = new TextField();
	    fnameTextField.setPrefSize(200, 30);
	    lnameTextField.setPrefSize(200, 30);
	    
	    Button confirmButton = new Button("Confirm");
	    Button cancelButton = new Button("Cancel");
	    confirmButton.setPrefSize(80, 30);
	    cancelButton.setPrefSize(80, 30);
	    
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
	    
	    grid.addRow(1, fnameLabel, fnameTextField);
	    grid.addRow(2, lnameLabel, lnameTextField);
	    grid.addRow(3, confirmButton, cancelButton);
	    grid.addRow(4, errorLabel);
	    
	    dialog.getDialogPane().setContent(grid);

	    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (fnameTextField.textProperty().get().isBlank() || lnameTextField.textProperty().get().isBlank()) {
					errorLabel.textProperty().set("Please fill in all data before proceeding.");
				} else {
					Doctor doctor = CreateAppointmentPage.this.viewmodel.getDoctor(fnameTextField.textProperty().get(), lnameTextField.textProperty().get());
					
					if (doctor == null) {
						errorLabel.textProperty().set("Could not find doctor. Please try again.");
					} else {
						CreateAppointmentPage.this.doctorName.setText("Doctor: " + doctor.getFirstName() + " " + doctor.getLastName());
						CreateAppointmentPage.this.setTimes();
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

	private void showGetDoctorBySpecialtyDialog() {
		Dialog<Boolean> dialog = new Dialog<Boolean>();
	    dialog.setTitle("Select Doctor");
	    dialog.setHeaderText("Please select a specialty below.");
	    dialog.setResizable(false);

	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));
	    
	    Label errorLabel = new Label();
	    errorLabel.setTextFill(Color.RED);
	    
	    ComboBox<String> specialtyComboBox = new ComboBox<String>();
	    ListView<Doctor> doctorsListView = new ListView<Doctor>();

	    doctorsListView.setCellFactory(new Callback<ListView<Doctor>, ListCell<Doctor>>() {
			@Override
			public ListCell<Doctor> call(ListView<Doctor> appointmentsListView) {
				return new ListCell<Doctor>() {
					@Override
					protected void updateItem(Doctor item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText("");
						} else {
							setText(item.getFirstName() + " " + item.getLastName());
						}
					}
				};
			};
		});
	    
	    specialtyComboBox.itemsProperty().set(FXCollections.observableArrayList(this.viewmodel.getSpecialties()));
	    
	    Button confirmButton = new Button("Confirm");
	    Button cancelButton = new Button("Cancel");
	    confirmButton.disableProperty().set(true);
	    confirmButton.setPrefSize(80, 30);
	    cancelButton.setPrefSize(80, 30);

	    GridPane.setColumnSpan(doctorsListView, 2);
	    GridPane.setColumnSpan(errorLabel, 2);

	    grid.addRow(1, specialtyComboBox);
	    grid.addRow(2, doctorsListView);
	    grid.addRow(3, confirmButton, cancelButton);
	    grid.addRow(4, errorLabel);
	    
	    dialog.getDialogPane().setContent(grid);

	    specialtyComboBox.getSelectionModel().selectedItemProperty().addListener((unused, oldVal, newVal) -> {
	    	if (newVal != null) {
	    		doctorsListView.setItems(FXCollections.observableArrayList(this.viewmodel.getDoctorsBySpecialty(newVal)));
	    	}
	    });
	    doctorsListView.getSelectionModel().selectedItemProperty().addListener((unused, oldVal, newVal) -> {
	    	confirmButton.disableProperty().set(newVal == null);
	    });
	    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Doctor doctor = doctorsListView.getSelectionModel().getSelectedItem();
				CreateAppointmentPage.this.viewmodel.setDoctor(doctor);
				CreateAppointmentPage.this.doctorName.setText("Doctor: " + doctor.getFirstName() + " " + doctor.getLastName());
				CreateAppointmentPage.this.setTimes();
				Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
				stage.close();
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
	
    @FXML
    void dateSelected() {
    	this.setTimes();
    }
    
    private void setTimes() {
    	boolean validDocAndDate = this.hasValidDoctorAndDate();
    	if (validDocAndDate) {
    		Collection<LocalTime> times = this.viewmodel.getOpenTimes(this.appointmentDate.getValue());
    		this.appointmentTime.getItems().clear();
    		if (times != null) {
    			times = times.stream().sorted().toList();
    			this.appointmentTime.getItems().addAll(times);
    		}
    		this.appointmentTime.disableProperty().set(!validDocAndDate);
    	}
    }
    
    private boolean hasValidDoctorAndDate() {
		return this.appointmentDate.getValue() != null
				&& this.viewmodel.getDoctor() != null
				&& !this.appointmentDate.getValue().isBefore(LocalDate.now());
    }
    
    @FXML
    void createNewAppointment() {
    	boolean hasErrors = false;
    	
		LocalDate appointmentDate = null;
		try {
			appointmentDate = this.appointmentDate.getValue();
    		if (appointmentDate == null) {
    			this.appointmentDateErrMsg.setText("Appointment date must be selected.");
    			hasErrors = true;
    		} else {
    			this.appointmentDateErrMsg.setText("");
    		}
		} catch (Exception ex) {
			this.appointmentDateErrMsg.setText("Invalid value given for appointment date.");
			hasErrors = true;
		}
    	
    	LocalTime appointmentTime = this.appointmentTime.getValue();
		if (appointmentTime == null) {
			this.appointmentTimeErrMsg.setText("Time must be selected.");
			hasErrors = true;
		} else {
			this.appointmentTimeErrMsg.setText("");
		}
		
		Doctor doctor = this.viewmodel.getDoctor();
		if (doctor == null) {
			this.doctorErrMsg.setText("Doctor must be selected.");
			hasErrors = true;
		} else {
			this.doctorErrMsg.setText("");
		}
		
		String appointmentReason = this.appointmentReason.textProperty().getValue();
		if (appointmentReason.isBlank()) {
			this.appointmentReasonErrMsg.setText("Reason for appointment should not be blank.");
			hasErrors = true;
		} else {
			this.appointmentReasonErrMsg.setText("");
		}
		
		if (!hasErrors) {
			Alert dialog;
			if (this.viewmodel.createAppointment(doctor, appointmentDate, appointmentTime, appointmentReason)) {
	    		dialog = new Alert(Alert.AlertType.INFORMATION);
	    		dialog.setTitle("Success");
	    		dialog.setHeaderText("Updated patient successfully.");
	    		this.backToViewPatient();
			} else {
	    		dialog = new Alert(Alert.AlertType.ERROR);
	    		dialog.setTitle("Error");
	    		dialog.setHeaderText("Could not create appointment.");
			}
			dialog.showAndWait();
		}
    }
	
	@FXML
    void backToViewPatient() {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(Main.VIEW_PATIENT_INFO_PAGE));
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
    	
    	ViewPatientInfoPage page = loader.getController();
    	page.setPatient(this.viewmodel.getPatient());
    	    	
    	addTodoStage.show();
    	
    	Stage stage = (Stage) this.currentUserLabel.getScene().getWindow();
    	stage.close();
    }
    
	/**
	 * Sets the patient.
	 * @param patient the patient
	 */
    public void setPatient(Patient patient) {
    	this.viewmodel.setPatient(patient);
    	this.patientInfoLabel.setText(patient.getFirstName() + " " + patient.getLastName() + ", "
    			+ DateTimeFormatter.ofPattern("MM-dd-yyyy").format(patient.getDateOfBirth()));
    }
}
