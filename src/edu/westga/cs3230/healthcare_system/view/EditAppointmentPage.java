package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.model.Appointment;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;
import edu.westga.cs3230.healthcare_system.view_model.EditAppointmentPageViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
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
 * @author Jacob
 * @version Fall 2024
 */
public class EditAppointmentPage {
	public static final String LOCATION = "view/EditAppointment.fxml";
	
	@FXML private Label oldDoctor;
	@FXML private Label oldTime;
	@FXML private TextArea oldReason;
	
    @FXML private Button confirm;
    @FXML private Label newDoctor;
    @FXML private DatePicker newDate;
    @FXML private ComboBox<LocalTime> newTime;
    @FXML private TextArea newReason;
    
    @FXML private Label timeErrMsg;
    @FXML private Label reasonErrMsg;
    @FXML private Label appointmentDateErrMsg;
    @FXML private Label appointmentTimeErrMsg;
    @FXML private Label appointmentReasonErrMsg;
    
    @FXML private Label currentUserLabel;
    @FXML private Label patientInfoLabel;
    
    private EditAppointmentPageViewModel viewmodel;
        
    /**
     * Instantiates a new CreateAppointmentPage
     */
    public EditAppointmentPage() {
    	this.viewmodel = new EditAppointmentPageViewModel();
    }

    @FXML
    void initialize() {
   		this.bindElements();
        this.currentUserLabel.setText(UserLogin.getUserlabel());
         
        this.setupCells();
    }

	private void setupCells() {
		this.newDate.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate today = LocalDate.now();
                        setDisable(empty || item.compareTo(today) < 0);
                    }
                };
            }
        });
        this.newTime.setCellFactory(new Callback<ListView<LocalTime>, ListCell<LocalTime>>() {
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
		this.newTime.setButtonCell(new ListCell<LocalTime>() {
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
    
    private void bindElements() {
    	this.oldDoctor.textProperty().bindBidirectional(this.viewmodel.getOldDoctorProperty());
    	this.oldTime.textProperty().bindBidirectional(this.viewmodel.getOldTimeProperty());
    	this.oldReason.textProperty().bindBidirectional(this.viewmodel.getOldReasonProperty());
    	
    	this.newDoctor.textProperty().bindBidirectional(this.viewmodel.getNewDoctorProperty());
    	this.newDate.valueProperty().bindBidirectional(this.viewmodel.getNewDateProperty());
    	this.newTime.itemsProperty().bindBidirectional(this.viewmodel.getTimeslotsProperty());
    	this.viewmodel.getNewTimeProperty().bind(this.newTime.getSelectionModel().selectedItemProperty());
    	this.newReason.textProperty().bindBidirectional(this.viewmodel.getNewReasonProperty());
    }
    
    @FXML
    void selectDoctor() {
    	this.showGetDoctorDialog();
    }
	
	private boolean showGetDoctorDialog() {
		Dialog<Boolean> dialog = new Dialog<Boolean>();
	    dialog.setTitle("Select Doctor");
	    dialog.setHeaderText("Enter the doctors info below.");
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
				if (fnameTextField.textProperty().get() == null || lnameTextField.textProperty().get() == null) {
					errorLabel.textProperty().set("Please fill in all data before proceeding.");
				} else if (EditAppointmentPage.this.viewmodel.setDoctor(fnameTextField.textProperty().get(), lnameTextField.textProperty().get())) {
					dialog.setResult(true);
				} else {
					errorLabel.textProperty().set("Could not find doctor. Please try again.");
				}
			}
		});
		
	    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
				stage.close();
				dialog.resultProperty().set(false);
			}
		});
	    
	    return dialog.showAndWait().orElse(false);
	}

	@FXML
	void updateAppointment() {
		if (this.newTime.getSelectionModel().getSelectedItem() == null) {
			this.timeErrMsg.textProperty().set("Please choose a time slot.");
		}
		if (this.newReason.textProperty().get().isBlank()) {
			this.reasonErrMsg.textProperty().set("Please specify a reason" + System.lineSeparator() + "for this appointment.");
		}
		Alert alert = new Alert(AlertType.ERROR);
    	if (this.viewmodel.updateAppointment()) {
    		alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Success");
    		alert.setHeaderText("Successfully updated appointment.");
    		this.backToViewPatient();
    	} else {
    		alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Could not schedule appointment.");
    	}
    	alert.showAndWait();
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
     * 
     * @precondition patient != null
	 * @postcondition true
	 * @param patient the patient
	 */
    public void setPatient(Patient patient) {
    	if (patient == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_PATIENT);
    	}
    	this.viewmodel.setPatient(patient);
    	this.patientInfoLabel.setText(patient.getFirstName() + " " + patient.getLastName() + ", "
    			+ DateTimeFormatter.ofPattern("MM-dd-yyyy").format(patient.getDateOfBirth()));
    }

    /**
     * Sets the appointment
     * 
     * @precondition appointment != null
	 * @postcondition true
     * @param appointment the appointment
     */
    public void setAppointment(Appointment appointment) {
    	if (appointment == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_APPOINTMENT);
    	}
    	this.viewmodel.setOldAppointment(appointment);
    	this.newTime.getSelectionModel().select(appointment.getAppointmentTime().toLocalTime());
    }
}
