package edu.westga.cs3230.healthcare_system.view_model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import edu.westga.cs3230.healthcare_system.dal.TestDAL;
import edu.westga.cs3230.healthcare_system.model.Test;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates a new order test page view model.
 * @author Stefan
 * @version Fall 2024
 *
 */
public class OrderTestsPageViewModel {

    private StringProperty initialDiagnosis;

    private ObservableList<Test> availableTests;
    private ObservableList<Test> selectedTests;
    
	private ObjectProperty<Test> selectedAvailableTests;
	private ObjectProperty<Test> selectedSelectedTests;
	
    private StringProperty availableTestDescription;
    private StringProperty selectedTestDescription;
    
    private TestDAL testDB;

    /**
     * Instantiates a new order tests page view model.
     */
    public OrderTestsPageViewModel() {
    	this.testDB = new TestDAL();
        this.initialDiagnosis = new SimpleStringProperty("");

        this.availableTests = FXCollections.observableArrayList();
        this.selectedTests = FXCollections.observableArrayList();

        this.availableTestDescription = new SimpleStringProperty("Not test selected.");
        this.selectedTestDescription = new SimpleStringProperty("Not test selected.");
        
        this.selectedAvailableTests = new SimpleObjectProperty<Test>();
        this.selectedSelectedTests = new SimpleObjectProperty<Test>();
        
        this.loadAvailableTests();
    }
    
    /**
     * Sets up the binding for selectedAvailableTests and selectedSelectedTests.
     */
    public void createSelectedElementBindings() {
        this.selectedAvailableTests.addListener((unused, oldVal, newVal) -> {
        	String description = newVal == null ? "Not test selected." : newVal.toString();
			this.availableTestDescription.set(description);
		});
        
        this.selectedSelectedTests.addListener((unused, oldVal, newVal) -> {
         	String description = newVal == null ? "Not test selected." : newVal.toString();
			this.selectedTestDescription.set(description);
		});
    }

    /**
     * Returns the initial diagnosis string property.
     * 
     * @return the initial diagnosis string property
     */
    public StringProperty getInitialDiagnosisProperty() {
        return this.initialDiagnosis;
    }

    /**
     * Returns the list of available tests.
     * 
     * @return the available tests observable list
     */
    public ObservableList<Test> getAvailableTestsProperty() {
        return this.availableTests;
    }

    /**
     * Returns the list of selected tests.
     * 
     * @return the selected tests observable list
     */
    public ObservableList<Test> getSelectedTestsProperty() {
        return this.selectedTests;
    }
    
    /**
     * Returns the selected available test.
     * 
     * @return the selected test in the available tests observable list
     */
    public ObjectProperty<Test> getSelectedAvailableTestsProperty() {
        return this.selectedAvailableTests;
    }

    /**
     * Returns the selected selected test.
     * 
     * @return the selected tests in the selected tests observable list
     */
    public ObjectProperty<Test> getSelectedSelectedTestsProperty() {
        return this.selectedSelectedTests;
    }

    /**
     * Returns the description of available tests string property.
     * 
     * @return the available test description string property
     */
    public StringProperty getAvailableTestDescriptionProperty() {
        return this.availableTestDescription;
    }

    /**
     * Returns the description of selected tests string property.
     * 
     * @return the selected test description string property
     */
    public StringProperty getSelectedTestDescriptionProperty() {
        return this.selectedTestDescription;
    }

    private void loadAvailableTests() {
    	List<Test> testList = TestDAL.getTests();
    	this.availableTests.addAll(testList);
    }

    /**
     * Adds a test to selected tests.
     */
    public void addTestToSelected() {
        if (!this.availableTests.isEmpty()) {
            Test testToAdd = this.selectedAvailableTests.get();
            boolean removed = this.availableTests.remove(testToAdd);
            if (removed) {
            	this.selectedTests.add(testToAdd);
            }
        }
    }

    /**
     * Removes a test from selected tests.
     */
    public void removeTestFromSelected() {
        if (!this.selectedTests.isEmpty()) {
        	Test testToRemove = this.selectedSelectedTests.get();
            boolean removed = this.selectedTests.remove(testToRemove);
            if (removed) {
                this.availableTests.add(testToRemove);
            }
        }
    }

    /**
     * Submits the tests to be ordered.
     * @param appointmentTime the time of the appointment
     * @param doctorId the doctor for the appointment
     * @return true if successfull, false otherwise
     */
    public boolean confirmTests(LocalDateTime appointmentTime, int doctorId) {
        return this.testDB.addTestsAndUpdateInitialDiagnosis(appointmentTime,
        		doctorId,
        		this.initialDiagnosis.getValue(),
        		this.selectedTests.stream().map(Test::getTestCode).collect(Collectors.toList()));
    }
}
