package edu.westga.cs3230.healthcare_system.view.dialogs;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Dictionary;

import edu.westga.cs3230.healthcare_system.model.TestResults;
import edu.westga.cs3230.healthcare_system.view_model.ShowVisitsInRangeViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 * The codebehind for the ShowVisitsInRange page
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class ShowVisitsInRange {
    @FXML private ListView<Dictionary<String, Object>> visitsListView;
    @FXML private DatePicker visitDatePicker;
    @FXML private TextField pidTextField;
    @FXML private TextField pnameTextField;
    @FXML private TextField dnameTextField;
    @FXML private TextField nnameTextField;
	@FXML private TextArea diagnosisTextArea;
    @FXML private ListView<TestResults> testsListView;
    @FXML private DatePicker testDatePicker;
    @FXML private CheckBox testAbnormalityCheckbox;
    @FXML private TextArea testResultsTextArea;

    private ShowVisitsInRangeViewModel viewmodel;
    
    /**
     * Instantiates a new ShowVisitsInRange page
     * 
     * @precondition true
     * @postcondition true
     */
    public ShowVisitsInRange() {
    	this.viewmodel = new ShowVisitsInRangeViewModel();
    }
    
    @FXML
    void initialize() {
    	this.bindElements();
    	
    	this.visitsListView.setCellFactory(new Callback<ListView<Dictionary<String, Object>>, ListCell<Dictionary<String, Object>>>() {
			@Override
			public ListCell<Dictionary<String, Object>> call(ListView<Dictionary<String, Object>> appointmentsListView) {
				return new ListCell<Dictionary<String, Object>>() {
					@Override
					protected void updateItem(Dictionary<String, Object> item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText("");
						} else {
							setText(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss").format((LocalDateTime) item.get("visitDate")) + ", " + item.get("patientName"));
						}
					}
				};
			};
    	});
    	this.testsListView.setCellFactory(new Callback<ListView<TestResults>, ListCell<TestResults>>() {
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
			};
    	});
    	
    	this.visitsListView.getSelectionModel().selectedItemProperty().addListener((unused, oldVal, newVal) -> {
    		this.viewmodel.changeSelectedVisit(newVal);
    	});
    	this.testsListView.getSelectionModel().selectedItemProperty().addListener((unused, oldVal, newVal) -> {
    		this.viewmodel.changeSelectedTest(newVal);
    	});
    }
    
	private void bindElements() {
		this.visitsListView.itemsProperty().bindBidirectional(this.viewmodel.getVisitsListProperty());
		this.visitDatePicker.valueProperty().bindBidirectional(this.viewmodel.getVisitDateProperty());
		this.pidTextField.textProperty().bind(this.viewmodel.getPatientIDProperty());
		this.pnameTextField.textProperty().bindBidirectional(this.viewmodel.getPatientNameProperty());
		this.dnameTextField.textProperty().bindBidirectional(this.viewmodel.getDoctorNameProperty());
		this.nnameTextField.textProperty().bindBidirectional(this.viewmodel.getNurseNameProperty());
		this.diagnosisTextArea.textProperty().bindBidirectional(this.viewmodel.getDiagnosisProperty());
		this.testResultsTextArea.textProperty().bindBidirectional(this.viewmodel.getTestResultsProperty());
		
		this.testsListView.itemsProperty().bindBidirectional(this.viewmodel.getTestsProperty());
		this.testDatePicker.valueProperty().bindBidirectional(this.viewmodel.getTestDateProperty());
		this.testAbnormalityCheckbox.selectedProperty().bindBidirectional(this.viewmodel.getTestAbnormalityProperty());
	}

	/**
	 * Sets the range of dates for the visits that should be shown.
	 * 
	 * @precondition startDate != null && endDate != null && !startDate.isAfter(endDate)
	 * @postcondition true
	 * @param startDate the start date
	 * @param endDate the end date
	 * @throws SQLException
	 */
	public void setDates(LocalDate startDate, LocalDate endDate) throws SQLException {
		this.viewmodel.setDates(startDate, endDate);
	}
}
