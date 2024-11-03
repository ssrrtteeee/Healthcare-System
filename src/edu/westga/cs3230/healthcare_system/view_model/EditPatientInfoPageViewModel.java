package edu.westga.cs3230.healthcare_system.view_model;

import java.time.LocalDate;

import edu.westga.cs3230.healthcare_system.dal.PatientDAL;
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

/**
 * ViewModel for EditPatientInfoPage.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class EditPatientInfoPageViewModel {
	private StringProperty fnameErrorMsgProperty;
	private StringProperty lnameErrorMsgProperty;
	private StringProperty dobErrorMsgProperty;
	private StringProperty cityErrorMsgProperty;
	private StringProperty addressErrorMsgProperty;
	private StringProperty zipcodeErrorMsgProperty;
	private StringProperty phoneNumErrorMsgProperty;
	
	private StringProperty fnameProperty;
	private StringProperty lnameProperty;
	private ListProperty<String> gendersListProperty;
	private StringProperty genderProperty;
	private ObjectProperty<LocalDate> dobProperty;
	private StringProperty cityProperty;
	private ListProperty<USStates> statesProperty;
	private ObjectProperty<USStates> stateProperty;
	private StringProperty addressProperty;
	private StringProperty zipcodeProperty;
	private StringProperty phoneNumProperty;
	private BooleanProperty isActiveProperty;
	
	private int patientId;
	private PatientDAL db;

	/**
	 * Gets the fnameErrorMsg property
	 * 
	 * @precondition true
	 * @postconditino true
	 * @return the fnameErrorMsg property
	 */
	public StringProperty getFnameErrorMsgProperty() {
		return this.fnameErrorMsgProperty;
	}

	/**
	 * Gets the lnameErrorMsg property
	 * 
	 * @precondition true
	 * @postconditino true
	 * @return the lnameErrorMsg property
	 */
	public StringProperty getLnameErrorMsgProperty() {
		return this.lnameErrorMsgProperty;
	}

	/**
	 * Gets the dobErrorMsg property
	 * 
	 * @precondition true
	 * @postconditino true
	 * @return the dobErrorMsg property
	 */
	public StringProperty getDobErrorMsgProperty() {
		return this.dobErrorMsgProperty;
	}

	/**
	 * Gets the cityErrorMsg property
	 * 
	 * @precondition true
	 * @postconditino true
	 * @return the cityErrorMsg property
	 */
	public StringProperty getCityErrorMsgProperty() {
		return this.cityErrorMsgProperty;
	}

	/**
	 * Gets the addressErrorMsg property
	 * 
	 * @precondition true
	 * @postconditino true
	 * @return the addressErrorMsg property
	 */
	public StringProperty getAddressErrorMsgProperty() {
		return this.addressErrorMsgProperty;
	}

	/**
	 * Gets the zipcodeErrorMsg property
	 * 
	 * @precondition true
	 * @postconditino true
	 * @return the zipcodeErrorMsg property
	 */
	public StringProperty getZipcodeErrorMsgProperty() {
		return this.zipcodeErrorMsgProperty;
	}

	/**
	 * Gets the phoneNumErrorMsg property
	 * 
	 * @precondition true
	 * @postconditino true
	 * @return the phoneNumErrorMsg property
	 */
	public StringProperty getPhoneNumErrorMsgProperty() {
		return this.phoneNumErrorMsgProperty;
	}

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
	 * Gets the genders list property.
	 *
	 * @precondition true
	 * @postcondition true
	 * @return the genders list property
	 */
	public ListProperty<String> getGendersListProperty() {
		return this.gendersListProperty;
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
	 * Gets the states property.
	 *
	 * @precondition true
	 * @postcondition true
	 * @return the states property
	 */
	public ListProperty<USStates> getStatesProperty() {
		return this.statesProperty;
	}

	/**
	 * Gets the state property.
	 *
	 * @precondition true
	 * @postcondition true
	 * @return the state property
	 */
	public ObjectProperty<USStates> getStateProperty() {
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
	 * Instantiates a new edit patient info page view model.
	 */
	public EditPatientInfoPageViewModel() {
		this.fnameErrorMsgProperty = new SimpleStringProperty();
		this.lnameErrorMsgProperty = new SimpleStringProperty();
		this.dobErrorMsgProperty = new SimpleStringProperty();
		this.cityErrorMsgProperty = new SimpleStringProperty();
		this.addressErrorMsgProperty = new SimpleStringProperty();
		this.zipcodeErrorMsgProperty = new SimpleStringProperty();
		this.phoneNumErrorMsgProperty = new SimpleStringProperty();
		
		this.fnameProperty = new SimpleStringProperty();
		this.lnameProperty = new SimpleStringProperty();
		this.gendersListProperty = new SimpleListProperty<String>(FXCollections.observableArrayList());
		this.genderProperty = new SimpleStringProperty();
		this.dobProperty = new SimpleObjectProperty<>();
		this.cityProperty = new SimpleStringProperty();
		this.statesProperty = new SimpleListProperty<USStates>(FXCollections.observableArrayList());
		this.stateProperty = new SimpleObjectProperty<USStates>();
		this.addressProperty = new SimpleStringProperty();
		this.zipcodeProperty = new SimpleStringProperty();
		this.phoneNumProperty = new SimpleStringProperty();
		this.isActiveProperty = new SimpleBooleanProperty();
		
		this.gendersListProperty.add("Male");
		this.gendersListProperty.add("Female");
		this.statesProperty.setAll(USStates.values());
		
		this.db = new PatientDAL();
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

		this.patientId = patient.getId();
		this.fnameProperty.set(patient.getFirstName());
		this.lnameProperty.set(patient.getLastName());
		this.genderProperty.set(patient.getGender());
		this.dobProperty.set(patient.getDateOfBirth());
		this.cityProperty.set(patient.getCity());
		for (USStates currState : USStates.values()) {
			if (patient.getState().equals(currState.getAbbreviation())) {
				this.stateProperty.set(currState);
			}
		}
		this.addressProperty.set(patient.getAddress());
		this.zipcodeProperty.set(patient.getZipcode());
		this.phoneNumProperty.set(patient.getPhoneNumber());
		this.isActiveProperty.set(patient.isActive());
	}

	/**
	 * Confirms this patient's new info.
	 * If any information is invalid, displays error messages as appropriate.
	 * Otherwise, updates the database with the new information.
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return true if updated, false otherwise.
	 */
	public boolean onConfirm() {
		boolean hasErrors = false;
		if (this.fnameProperty.get().isBlank()) {
			this.fnameErrorMsgProperty.set("First name should not be blank.");
			hasErrors = true;
		} else {
			this.fnameErrorMsgProperty.set("");
		}
		if (this.lnameProperty.get().isBlank()) {
			this.lnameErrorMsgProperty.set("Last name should not be blank.");
			hasErrors = true;
		} else {
			this.lnameErrorMsgProperty.set("");
		}
		if (this.dobProperty.get() == null) {
			this.dobErrorMsgProperty.set("Please enter a valid date of birth.");
			hasErrors = true;
		} else if (this.dobProperty.get().isAfter(LocalDate.now())) {
			this.dobErrorMsgProperty.set("Patient date of birth should not be in the future.");
			hasErrors = true;
		} else {
			this.dobErrorMsgProperty.set("");
		}
		if (this.cityProperty.get().isBlank()) {
			this.cityErrorMsgProperty.set("City should not be blank.");
			hasErrors = true;
		} else {
			this.cityErrorMsgProperty.set("");
		}
		if (this.addressProperty.get().isBlank()) {
			this.addressErrorMsgProperty.set("Address should not be blank.");
			hasErrors = true;
		} else {
			this.addressErrorMsgProperty.set("");
		}
		if (!this.zipcodeProperty.get().matches("\\d{5}")) {
			this.zipcodeErrorMsgProperty.set("Zip code must be 5 digits.");
			hasErrors = true;
		} else {
			this.zipcodeErrorMsgProperty.set("");
		}
		if (!this.phoneNumProperty.get().matches("\\d{10}")) {
			this.phoneNumErrorMsgProperty.set("Phone number should be 10 digits." + System.lineSeparator() + "(No spaces or dashes)");
			hasErrors = true;
		} else {
			this.phoneNumErrorMsgProperty.set("");
		}
		
		if (!hasErrors) {
			Patient patient = new Patient(this.fnameProperty.get(), this.lnameProperty.get(), this.cityProperty.get(), this.addressProperty.get(), this.zipcodeProperty.get(), this.phoneNumProperty.get(), this.genderProperty.get().substring(0, 1), this.stateProperty.get().getAbbreviation(), this.dobProperty.get(), this.isActiveProperty.get());
			return this.db.updatePatient(this.patientId, patient);
		}
		return false;
	}
}
