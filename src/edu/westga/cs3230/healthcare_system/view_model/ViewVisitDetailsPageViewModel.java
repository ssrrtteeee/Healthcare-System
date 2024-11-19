package edu.westga.cs3230.healthcare_system.view_model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import edu.westga.cs3230.healthcare_system.dal.TestDAL;
import edu.westga.cs3230.healthcare_system.dal.TestResultDAL;
import edu.westga.cs3230.healthcare_system.model.Doctor;
import edu.westga.cs3230.healthcare_system.model.Test;
import edu.westga.cs3230.healthcare_system.model.TestResults;
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;
import edu.westga.cs3230.healthcare_system.view.CreateAppointmentPage;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Creates a new view visit details page view model.
 * @author Stefan
 * @version Fall 2024
 *
 */
public class ViewVisitDetailsPageViewModel {
    private ObservableList<Test> orderedTests;
    private ObservableList<TestResults> performedTests;
    
	private ObjectProperty<Test> selectedOrderedTests;
	private ObjectProperty<TestResults> selectedPerformedTests;
	
    private StringProperty orderedTestsDetails;
    private StringProperty performedTestsResults;
    
    private TestDAL testDB;
    private TestResultDAL testResultDB;

    /**
     * Instantiates a new view visit details page view model.
     */
    public ViewVisitDetailsPageViewModel() {
    	this.testDB = new TestDAL();
    	this.testResultDB = new TestResultDAL();
    	
        this.orderedTests = FXCollections.observableArrayList();
        this.performedTests = FXCollections.observableArrayList();

        this.orderedTestsDetails = new SimpleStringProperty("No test selected.");
        this.performedTestsResults = new SimpleStringProperty("No test selected.");
        
        this.selectedOrderedTests = new SimpleObjectProperty<Test>();
        this.selectedPerformedTests = new SimpleObjectProperty<TestResults>();
    }
    
    /**
     * Sets up the binding for selectedOrderedTests and selectedPerformedTests.
     */
    public void createSelectedElementBindings() {
        this.selectedOrderedTests.addListener((unused, oldVal, newVal) -> {
        	String description = newVal == null ? "No test selected." : newVal.toString();
			this.orderedTestsDetails.set(description);
		});
        
        this.selectedPerformedTests.addListener((unused, oldVal, newVal) -> {
         	String description = newVal == null ? "No test selected." : newVal.toString();
			this.performedTestsResults.set(description);
		});
    }

    /**
     * Returns the list of ordered tests.
     * 
     * @return the available tests observable list
     */
    public ObservableList<Test> getOrderedTestsProperty() {
        return this.orderedTests;
    }

    /**
     * Returns the list of performed tests.
     * 
     * @return the selected tests observable list
     */
    public ObservableList<TestResults> getPerformedTestsProperty() {
        return this.performedTests;
    }
    
    /**
     * Returns the selected available test.
     * 
     * @return the selected test in the available tests observable list
     */
    public ObjectProperty<Test> getSelectedOrderedTestsProperty() {
        return this.selectedOrderedTests;
    }

    /**
     * Returns the selected selected test.
     * 
     * @return the selected tests in the selected tests observable list
     */
    public ObjectProperty<TestResults> getSelectedPerformedTestsProperty() {
        return this.selectedPerformedTests;
    }

    /**
     * Returns the description of available tests string property.
     * 
     * @return the available test description string property
     */
    public StringProperty getOrderedTestDescriptionProperty() {
        return this.orderedTestsDetails;
    }

    /**
     * Returns the description of selected tests string property.
     * 
     * @return the selected test description string property
     */
    public StringProperty getPerformedTestDescriptionProperty() {
        return this.performedTestsResults;
    }

    /**
     * Loads the ordered and performed tests for a specific visit.
     * @param appointmentTime the time of the appointment for the visit
     * @param doctorId the id of the doctor overseeing the visit
     */
    public void loadAvailableTests(LocalDateTime appointmentTime, int doctorId) {
    	List<Test> testList = this.testDB.getTestsFor(appointmentTime, doctorId);
    	this.orderedTests.setAll(testList);
    	
    	List<TestResults> testResultsList = this.testResultDB.getTestResultsFor(appointmentTime, doctorId);
    	this.performedTests.setAll(testResultsList);
    }

    /**
     * Updates the results of the test having the specified appointment time, doctor id, and test code with the specified test date/time, result, and abnormality.
     * 
     * @precondition appointmentTime != null && testDateTime != null && testResult != null
     * @postcondition true
     * @param appointmentTime
     * @param doctorId
     * @param testCode
     * @param testDateTime
     * @param testResult
     * @param abnormality
     * @return true if successful, false otherwise.
     */
	public boolean updateTestResult(LocalDateTime appointmentTime, int doctorId, int testCode, LocalDateTime testDateTime, String testResult, boolean abnormality) {
		if (appointmentTime == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_APMT_TIME);
		}
		if (testDateTime == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_TEST_TIME);
		}
		if (testResult == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_TEST_RESULT);
		}

		boolean result = this.testDB.updateTestResults(appointmentTime, doctorId, testCode, testDateTime, testResult, abnormality);
		this.loadAvailableTests(appointmentTime, doctorId);
		return result;
	}
    
    
}
