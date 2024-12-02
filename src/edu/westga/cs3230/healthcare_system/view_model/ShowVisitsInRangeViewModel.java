package edu.westga.cs3230.healthcare_system.view_model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Dictionary;

import edu.westga.cs3230.healthcare_system.dal.AdminDAL;
import edu.westga.cs3230.healthcare_system.dal.TestResultDAL;
import edu.westga.cs3230.healthcare_system.model.TestResults;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * The viewmodel class for the ShowVisitsInRange page/
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class ShowVisitsInRangeViewModel {
	private ListProperty<Dictionary<String, Object>> visitsListProperty;
	private ObjectProperty<LocalDate> visitDateProperty;
	private StringProperty patientIDProperty;
	private StringProperty patientNameProperty;
	private StringProperty doctorNameProperty;
	private StringProperty nurseNameProperty;
	private StringProperty diagnosisProperty;
	private ListProperty<TestResults> testsListProperty;
	private ObjectProperty<LocalDate> testDateProperty;
	private BooleanProperty testAbnormalityProperty;
	
	private AdminDAL adminDB;
	private TestResultDAL testResultsDB;
	
	/**
	 * Gets the visits list property
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the visitsListProperty
	 */
	public ListProperty<Dictionary<String, Object>> getVisitsListProperty() {
		return this.visitsListProperty;
	}

	/**
	 * Gets the patient ID property
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the patientIDProperty
	 */
	public StringProperty getPatientIDProperty() {
		return this.patientIDProperty;
	}
	
	/**
	 * Gets the patient name property
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the patientNameProperty
	 */
	public StringProperty getPatientNameProperty() {
		return this.patientNameProperty;
	}
	
	/**
	 * Gets the doctor name property
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the doctorNameProperty
	 */
	public StringProperty getDoctorNameProperty() {
		return this.doctorNameProperty;
	}
	
	/**
	 * Gets the nurse name property
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the nurseNameProperty
	 */
	public StringProperty getNurseNameProperty() {
		return this.nurseNameProperty;
	}
	
	/**
	 * Gets the diagnosis property 
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the diagnosisProperty
	 */
	public StringProperty getDiagnosisProperty() {
		return this.diagnosisProperty;
	}
	
	/**
	 * Gets the visit date property
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the visitDateProperty
	 */
	public ObjectProperty<LocalDate> getVisitDateProperty() {
		return this.visitDateProperty;
	}
	
	/**
	 * Gets the test results property
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the testListProperty
	 */
	public ListProperty<TestResults> getTestsProperty() {
		return this.testsListProperty;
	}
	
	
	/**
	 * Gets the test date property
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the testDateProperty
	 */
	public ObjectProperty<LocalDate> getTestDateProperty() {
		return this.testDateProperty;
	}
	
	/**
	 * Gets the test abnormality property
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the testAbnormalityProperty
	 */
	public BooleanProperty getTestAbnormalityProperty() {
		return this.testAbnormalityProperty;
	}

	/**
	 * Instantiates a new ShowVisitsInRangeViewModel
	 * 
	 * @precondition true
	 * @postcondition true
	 */
	public ShowVisitsInRangeViewModel() {
		this.visitsListProperty = new SimpleListProperty<Dictionary<String, Object>>();
		this.visitDateProperty = new SimpleObjectProperty<LocalDate>();
		this.patientIDProperty = new SimpleStringProperty();
		this.patientNameProperty = new SimpleStringProperty();
		this.doctorNameProperty = new SimpleStringProperty();
		this.nurseNameProperty = new SimpleStringProperty();
		this.diagnosisProperty = new SimpleStringProperty();
		this.testDateProperty = new SimpleObjectProperty<LocalDate>();
		this.testsListProperty = new SimpleListProperty<TestResults>();
		this.testAbnormalityProperty = new SimpleBooleanProperty();
		this.diagnosisProperty = new SimpleStringProperty();
		
		this.adminDB = new AdminDAL();
		this.testResultsDB = new TestResultDAL();
	}

	/**
	 * Sets the range of dates that should be shown.
	 * 
	 * @precondition startDate != null && endDate != null && !startDate.isAfter(endDate)
	 * @postcondition true
	 * @param startDate the start date
	 * @param endDate the end date
	 * @throws SQLException
	 */
	public void setDates(LocalDate startDate, LocalDate endDate) throws SQLException {
		this.visitsListProperty.set(FXCollections.observableArrayList(this.adminDB.getVisitsInfoBetweenDates(startDate, endDate)));
	}
	
	/**
	 * Changes the visit whose info is being displayed to the specified visit.
	 * 
	 * @precondition newVisit != null
	 * @postcondition true
	 * @param newVisit the new visit
	 */
	public void changeSelectedVisit(Dictionary<String, Object> newVisit) {
		this.visitDateProperty.set(((LocalDateTime) newVisit.get("visitDate")).toLocalDate());
		this.patientIDProperty.set(((Integer) newVisit.get("patientID")).toString());
		this.patientNameProperty.set((String) newVisit.get("patientName"));
		this.doctorNameProperty.set((String) newVisit.get("doctorName"));
		this.nurseNameProperty.set((String) newVisit.get("nurseName"));
		this.diagnosisProperty.set((String) newVisit.get("diagnosis"));
		
		this.testsListProperty.set(FXCollections.observableArrayList(this.testResultsDB.getTestResultsFor((LocalDateTime) newVisit.get("visitDate"), (Integer) newVisit.get("doctorID"))));
	}


	/**
	 * Changes the test whose infor is being displayed to the specified test.
	 * 
	 * @precondition newTest != null
	 * @postcondition true
	 * @param newTest the new test
	 */
	public void changeSelectedTest(TestResults newTest) {
		if (newTest == null) {
			this.testDateProperty.set(null);
			this.testAbnormalityProperty.set(false);
		} else {
			this.testDateProperty.set(newTest.getTestDateTime().toLocalDate());
			this.testAbnormalityProperty.set(newTest.isTestAbnormal());
		}
	}
}
