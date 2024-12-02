package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.dal.DoctorDAL;
import edu.westga.cs3230.healthcare_system.dal.NurseDAL;
import edu.westga.cs3230.healthcare_system.dal.RoutineCheckupDAL;
import edu.westga.cs3230.healthcare_system.model.Doctor;
import edu.westga.cs3230.healthcare_system.model.Nurse;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.model.RoutineCheckup;
import edu.westga.cs3230.healthcare_system.model.Test;
import edu.westga.cs3230.healthcare_system.model.TestResults;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;
import javafx.scene.control.DatePicker;
import edu.westga.cs3230.healthcare_system.view_model.ViewVisitDetailsPageViewModel;
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
import javafx.scene.control.CheckBox;
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
 * Creates the view visit details page.
 * @author Stefan
 * @version Fall 2024
 */
public class ViewVisitDetailsPage extends CommonFunctionality {

	@FXML private Label currentUserLabel;
    @FXML private Label currentPatientLabel;
    @FXML private Label currentDoctorLabel;
    @FXML private Label currentNurseLabel;
    
    @FXML private TextField patientHeight;
    @FXML private TextField patientWeight;
    @FXML private TextField systolicBP;
    @FXML private TextField diastolicBP;
    @FXML private TextField bodyTemperature;
    @FXML private TextField pulse;
    @FXML private TextArea symptoms;
    @FXML private TextArea initialDiagnosis;
    @FXML private TextArea finalDiagnosis;

    @FXML private ListView<Test> orderedTests;
    @FXML private ListView<TestResults> performedTests;
	
    @FXML private TextArea orderedTestsDetails;
    @FXML private TextArea performedTestsResults;
    
    @FXML private Button updateTestsButton;
    @FXML private Button finalDiagnosisButton;
    
    private LocalDateTime appointmentTime;
    private int doctorId;
    private Patient patient;
    
    private NurseDAL nurseDB;
    private RoutineCheckupDAL routineCheckupDB;
    private DoctorDAL doctorDB;
    private boolean setDoctorId = false;
    private boolean setAppointmentTime = false;
    
    private ViewVisitDetailsPageViewModel viewModel;
    
    @FXML
    void initialize() {
    	this.viewModel = new ViewVisitDetailsPageViewModel();
        this.currentUserLabel.setText(UserLogin.getUserlabel());
        this.doctorDB = new DoctorDAL();
        this.routineCheckupDB = new RoutineCheckupDAL();
        this.nurseDB = new NurseDAL();
	    this.bindElements();
	    this.setupCellFactories();
	    
	    this.orderedTests.getSelectionModel().selectedItemProperty().addListener((unused, oldVal, newVal) -> {
	    	this.updateTestsButton.disableProperty().set(newVal == null);
	    });
	    this.initCommon();
    }
    
    private void setupCellFactories() {
        this.orderedTests.setCellFactory(new Callback<ListView<Test>, ListCell<Test>>() {
			@Override
			public ListCell<Test> call(ListView<Test> appointmentsListView) {
				return new ListCell<Test>() {
					@Override
					protected void updateItem(Test item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText("");
						} else {
							setText(item.getName());
						}
					}
				};
			}
		});
        this.performedTests.setCellFactory(new Callback<ListView<TestResults>, ListCell<TestResults>>() {
			@Override
			public ListCell<TestResults> call(ListView<TestResults> appointmentsListView) {
				return new ListCell<TestResults>() {
					@Override
					protected void updateItem(TestResults item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText("");
						} else {
							setText(item.getTestName());
						}
					}
				};
			}
		});
    }
    
    private void bindElements() {
        this.orderedTests.setItems(this.viewModel.getOrderedTestsProperty());
        this.performedTests.setItems(this.viewModel.getPerformedTestsProperty());
    	
        this.orderedTestsDetails.textProperty().bind(this.viewModel.getOrderedTestDescriptionProperty());
        this.performedTestsResults.textProperty().bind(this.viewModel.getPerformedTestDescriptionProperty());
        this.viewModel.getSelectedOrderedTestsProperty().bind(this.orderedTests.getSelectionModel().selectedItemProperty());
        this.viewModel.getSelectedPerformedTestsProperty().bind(this.performedTests.getSelectionModel().selectedItemProperty());
        this.viewModel.createSelectedElementBindings();
    }
    
    private void loadCheckupDetails() {
    	if (!this.setDoctorId || !this.setAppointmentTime)  {
    		return;
    	}
    	RoutineCheckup checkup = this.routineCheckupDB.getRoutineCheckup(this.appointmentTime, this.doctorId);
    	if (checkup != null) {
    		this.viewModel.loadAvailableTests(this.appointmentTime, this.doctorId);
    		System.out.println(checkup.getRecordingNurseId());
    		Nurse nurse = this.nurseDB.getNurse(checkup.getRecordingNurseId());
        	this.currentNurseLabel.setText("Recording nurse Name: " + nurse.getFirstName() + " " + nurse.getLastName());
        	this.patientHeight.setText(String.valueOf(checkup.getPatientHeight()));
        	this.patientWeight.setText(String.valueOf(checkup.getPatientWeight()));
        	this.systolicBP.setText(String.valueOf(checkup.getSystolicBP()));
        	this.diastolicBP.setText(String.valueOf(checkup.getDiastolicBP()));
        	this.bodyTemperature.setText(String.valueOf(checkup.getBodyTemperature()));
        	this.pulse.setText(String.valueOf(checkup.getPulse()));
        	this.symptoms.setText(checkup.getSymptoms());
        	this.initialDiagnosis.setText(checkup.getInitialDiagnosis());
        	this.finalDiagnosis.setText(checkup.getFinalDiagnosis());
        	if (checkup.getFinalDiagnosis() != null) {
        		this.finalDiagnosisButton.visibleProperty().set(false);
        	}
    	}
    }
    
    /**
     * Sets the appointment date and time to the specified value.
     * 
     * @precondition time != null
     * @postcondition true
     * @param time the time
     */
    public void setAppointmentTime(LocalDateTime time) {
    	if (time == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_APMT_TIME);
    	}
    	this.appointmentTime = time;
    	this.setAppointmentTime = true;
    	this.loadCheckupDetails();
    }
    
    /**
     * Sets the doctor to the one with the specified ID
     * 
     * @precondition none
	 * @postcondition none
     * @param doctorId the ID
     */
    public void setDoctor(int doctorId) {
    	this.doctorId = doctorId;
    	Doctor doctor = this.doctorDB.retrieveDoctor(doctorId);
    	this.currentDoctorLabel.setText("Doctor Name: " + doctor.getFirstName() + " " + doctor.getLastName());
    	this.setDoctorId = true;
    	this.loadCheckupDetails();
    }
    
    /**
     * Sets the patient to the specified value
     * 
     * @precondition patient != null
	 * @postcondition true
     * @param patient the patient
     */
    public void setPatient(Patient patient) {
    	if (patient == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_PATIENT);
    	}
    	this.patient = patient;
    	this.currentPatientLabel.setText(
    			"Patient Name: " + patient.getFirstName() + " " + patient.getLastName() + System.lineSeparator()
    			+ "Patient Date of Birth: " + DateTimeFormatter.ofPattern("MM-dd-yyyy").format(patient.getDateOfBirth()));
    }
    
    @FXML
    void updateTestResult() {
    	Test currTest = this.orderedTests.getSelectionModel().getSelectedItem();
    	boolean manualAbnormalityInput = currTest.getLowValue() == null && currTest.getHighValue() == null;
    	
    	Dialog<Boolean> dialog = new Dialog<Boolean>();
	    dialog.setTitle("Enter test results");
	    dialog.setHeaderText("Enter the test result info below.");
	    dialog.setResizable(false);

	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));
	    
	    Label dateLabel = new Label("Test date and time: ");
	    Label resultLabel = new Label("Results: ");
	    Label abnormalityLabel = new Label("Result abnormality: ");
	    Label errorLabel = new Label();
	    errorLabel.setTextFill(Color.RED);
	    
	    TextField hourTextField = new TextField();
	    TextField minuteTextField = new TextField();
	    DatePicker testDatePicker = new DatePicker();
	    TextArea resultTextArea = new TextArea();
	    CheckBox abnormalityCheckBox = new CheckBox();
	    abnormalityCheckBox.setVisible(manualAbnormalityInput);
	    GridPane.setColumnSpan(errorLabel, 4);
	    GridPane.setColumnSpan(resultTextArea, 3);

	    Button confirmButton = new Button("Confirm");
	    Button cancelButton = new Button("Cancel");
	    confirmButton.setPrefSize(80, 30);
	    cancelButton.setPrefSize(80, 30);
	    
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
	    
	    grid.addRow(1, dateLabel, testDatePicker, hourTextField, minuteTextField);
	    grid.addRow(2, resultLabel, resultTextArea);
	    if (manualAbnormalityInput) {
	    	grid.addRow(3, abnormalityLabel, abnormalityCheckBox);
		    grid.addRow(4, confirmButton, cancelButton);
		    grid.addRow(5, errorLabel);
    	} else {
    		grid.addRow(3, confirmButton, cancelButton);
    		grid.addRow(4, errorLabel);
    	}
	    
	    dialog.getDialogPane().setContent(grid);

	    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (testDatePicker.valueProperty().get() == null
						|| hourTextField.textProperty().get().isBlank() || minuteTextField.textProperty().get().isBlank()
						|| resultTextArea.textProperty().get().isBlank()
				) {
					errorLabel.textProperty().set("Please fill in all data before proceeding.");
				} else {
					LocalDateTime testCompletionDateTime;
					try {
						testCompletionDateTime = LocalDateTime.of(testDatePicker.getValue(), LocalTime.of(Integer.parseInt(hourTextField.getText()), Integer.parseInt(minuteTextField.getText())));
					} catch (NumberFormatException ex) {
						errorLabel.textProperty().set("Please input a valid time (0-23, 0-59).");
						return;
					}
					boolean abnormal = abnormalityCheckBox.selectedProperty().get();
					try {
						if (currTest.getHighValue() != null && currTest.getHighValue() < Double.parseDouble(resultTextArea.getText().strip())) {
							abnormal = true;
						}
						if (currTest.getLowValue() != null && currTest.getLowValue() > Double.parseDouble(resultTextArea.getText().strip())) {
							abnormal = true;
						}
					} catch (Exception e) {
						errorLabel.textProperty().set("Test result must be a number.");
						return;
					}
					if (ViewVisitDetailsPage.this.viewModel.updateTestResult(ViewVisitDetailsPage.this.appointmentTime, ViewVisitDetailsPage.this.doctorId, currTest.getTestCode(), testCompletionDateTime, resultTextArea.getText().trim(), abnormal)) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Success");
						alert.setHeaderText("Updated test successfully.");
						alert.showAndWait();
						Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
						stage.close();
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Error");
						alert.setHeaderText("Could not update the test.");
						alert.setContentText("Please try again.");
						alert.showAndWait();
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
    
    @FXML
    void updateFinalDiagnosis() {
    	Dialog<Boolean> dialog = new Dialog<Boolean>();
	    dialog.setTitle("Enter final diagnosis");
	    dialog.setHeaderText("Enter the final diagnosis below.");
	    dialog.setResizable(false);

	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));
	    
	    TextArea diagnosisArea = new TextArea();
	    Label errorLabel = new Label();
	    errorLabel.setTextFill(Color.RED);
	    
	    GridPane.setColumnSpan(errorLabel, 2);
	    GridPane.setColumnSpan(diagnosisArea, 2);

	    Button confirmButton = new Button("Confirm");
	    Button cancelButton = new Button("Cancel");
	    confirmButton.setPrefSize(80, 30);
	    cancelButton.setPrefSize(80, 30);
	    
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
	    
	    grid.addRow(1, diagnosisArea);
        grid.addRow(2, confirmButton, cancelButton);
	    grid.addRow(3, errorLabel);

	    dialog.getDialogPane().setContent(grid);

	    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (diagnosisArea.getText().isBlank()) {
					errorLabel.textProperty().set("Please enter a diagnosis before proceeding.");
				} else {
					if (ViewVisitDetailsPage.this.viewModel.updateFinalDiagnosis(ViewVisitDetailsPage.this.appointmentTime, ViewVisitDetailsPage.this.doctorId, diagnosisArea.getText())) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Success");
						alert.setHeaderText("Updated final diagnosis successfully.");
						alert.showAndWait();
						ViewVisitDetailsPage.this.finalDiagnosis.setText(diagnosisArea.textProperty().get());
						ViewVisitDetailsPage.this.finalDiagnosisButton.visibleProperty().set(false);
						Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
						stage.close();
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Error");
						alert.setHeaderText("Could not update the final diagnosis.");
						alert.setContentText("Please try again.");
						alert.showAndWait();
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
    
    @FXML
    private void goBack() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource(Main.VIEW_PATIENT_INFO_PAGE));
    	try {
			loader.load();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    	Parent parent = loader.getRoot();
    	Scene scene = new Scene(parent);
    	Stage addTodoStage = new Stage();
    	addTodoStage.setTitle(Main.TITLE);
    	addTodoStage.setScene(scene);
    	addTodoStage.initModality(Modality.APPLICATION_MODAL);
    	
    	ViewPatientInfoPage page = loader.getController();
    	page.setPatient(this.patient);
    	    	
    	addTodoStage.show();
    	
    	Stage stage = (Stage) this.currentUserLabel.getScene().getWindow();
    	stage.close();
    }
}
