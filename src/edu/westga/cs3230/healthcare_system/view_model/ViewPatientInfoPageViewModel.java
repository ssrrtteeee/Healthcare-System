package edu.westga.cs3230.healthcare_system.view_model;

import java.time.LocalDate;

import edu.westga.cs3230.healthcare_system.dal.AppointmentDAL;
import edu.westga.cs3230.healthcare_system.model.Appointment;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.model.USStates;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.util.Pair;

/**
 * ViewModel for ViewPatientInfoPage.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class ViewPatientInfoPageViewModel {
	private StringProperty fnameProperty;
	private StringProperty lnameProperty;
	private StringProperty genderProperty;
	private ObjectProperty<LocalDate> dobProperty;
	private StringProperty cityProperty;
	private StringProperty stateProperty;
	private StringProperty addressProperty;
	private StringProperty zipcodeProperty;
	private StringProperty phoneNumProperty;
	private BooleanProperty isActiveProperty;
	private ListProperty<Pair<String, Appointment>> appointmentsProperty;
	private ObjectProperty<Pair<String, Appointment>> selectedAppointmentProperty;
	
	private Patient patient;
	private AppointmentDAL dbApmt;
	
	/**
	 * Gets the fname property.
	 *
	 * @precondition true
	 * @postcondition true
	 * @return the fname property
	 */
	public StringProperty getFnameProperty() {
		return this.fnameProperty;
	}

	/**
	 * Gets the lname property.
	 *
	 * @precondition true
	 * @postcondition true
	 * @return the lname property
	 */
	public StringProperty getLnameProperty() {
		return this.lnameProperty;
	}
	
	/**
	 * Gets the gender property.
	 *
	 * @precondition true
	 * @postcondition true
	 * @return the genderProperty
	 */
	public StringProperty getGenderProperty() {
		return this.genderProperty;
	}

	/**
	 * Gets the dob property.
	 *
	 * @precondition true
	 * @postcondition true
	 * @return the dobProperty
	 */
	public ObjectProperty<LocalDate> getDobProperty() {
		return this.dobProperty;
	}

	/**
	 * Gets the city property.
	 *
	 * @precondition true
	 * @postcondition true
	 * @return the city property
	 */
	public StringProperty getCityProperty() {
		return this.cityProperty;
	}

	/**
	 * Gets the state property.
	 *
	 * @precondition true
	 * @postcondition true
	 * @return the state property
	 */
	public StringProperty getStateProperty() {
		return this.stateProperty;
	}
	
	/**
	 * Gets the address property.
	 *
	 * @precondition true
	 * @postconditino true
	 * @return the address property
	 */
	public StringProperty getAddressProperty() {
		return this.addressProperty;
	}

	/**
	 * Gets the zipcode property.
	 *
	 * @precondition true
	 * @postconditino true
	 * @return the zipcode property
	 */
	public StringProperty getZipcodeProperty() {
		return this.zipcodeProperty;
	}

	/**
	 * Gets the phone num property.
	 *
	 * @precondition true
	 * @postconditino true
	 * @return the phone num property
	 */
	public StringProperty getPhoneNumProperty() {
		return this.phoneNumProperty;
	}

	/**
	 * Gets the active property.
	 *
	 * @precondition true
	 * @postconditino true
	 * @return the active property
	 */
	public BooleanProperty getIsActiveProperty() {
		return this.isActiveProperty;
	}
	
	/**
	 * Gets the appointments list property.
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the appointments property
	 */
	public ListProperty<Pair<String, Appointment>> getAppointmentsProperty() {
		return this.appointmentsProperty;
	}
	
	/**
	 * Gets the selected appointment property.
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the selected appointment property
	 */
	public ObjectProperty<Pair<String, Appointment>> getSelectedAppointmentProperty() {
		return this.selectedAppointmentProperty;
	}
	
	/**
	 * Gets the current patient being viewed
	 * @return the patient being viewed
	 */
	public Patient getPatient() {
		return this.patient;
	}
	
	/**
	 * Instantiates a new edit patient info page view model.
	 */
	public ViewPatientInfoPageViewModel() {
		this.dbApmt = new AppointmentDAL();
		this.fnameProperty = new SimpleStringProperty();
		this.lnameProperty = new SimpleStringProperty();
		this.genderProperty = new SimpleStringProperty();
		this.dobProperty = new SimpleObjectProperty<>();
		this.cityProperty = new SimpleStringProperty();
		this.stateProperty = new SimpleStringProperty();
		this.addressProperty = new SimpleStringProperty();
		this.zipcodeProperty = new SimpleStringProperty();
		this.phoneNumProperty = new SimpleStringProperty();
		this.isActiveProperty = new SimpleBooleanProperty();
		this.appointmentsProperty = new SimpleListProperty<Pair<String, Appointment>>();
		this.selectedAppointmentProperty = new SimpleObjectProperty<Pair<String, Appointment>>();
	}
	
	/**
	 * Gets the info for the patient whose info has been selected.
	 * 
	 * @precondition patient != null
	 * @postcondition true
	 * @param patient the patient to edit
	 */
	public void setPatient(Patient patient) {
		if (patient == null) {
			throw new IllegalArgumentException("Patient cannot be null.");
		}
		
		this.patient = patient;
		this.fnameProperty.set(patient.getFirstName());
		this.lnameProperty.set(patient.getLastName());
		this.genderProperty.set(patient.getGender());
		this.dobProperty.set(patient.getDateOfBirth());
		this.cityProperty.set(patient.getCity());
		for (USStates currState : USStates.values()) {
			if (patient.getState().equals(currState.getAbbreviation())) {
				this.stateProperty.set(currState.getAbbreviation() + "/" + currState.toString());
			}
		}
		this.addressProperty.set(patient.getAddress());
		this.zipcodeProperty.set(patient.getZipcode());
		this.phoneNumProperty.set(patient.getPhoneNumber());
		this.isActiveProperty.set(patient.isActive());
		this.appointmentsProperty.set(FXCollections.observableArrayList(this.dbApmt.getAppointmentsFor(patient)));
	}
}
