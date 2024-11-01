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

	private BooleanProperty controlsActiveProperty;
	
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
	 * Gets the controls active property.
	 *
	 * @precondition true
	 * @postconditino true
	 * @return the controls active property
	 */
	public BooleanProperty getControlsActiveProperty() {
		return this.controlsActiveProperty;
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
		
		this.controlsActiveProperty = new SimpleBooleanProperty(false);		
		
		this.gendersListProperty.add("Male");
		this.gendersListProperty.add("Female");
		this.statesProperty.setAll(USStates.values());
		
		this.db = new PatientDAL();
		
		this.disableControls();
	}
	
	/**
	 * Gets the info for the patient whose info has been selected.
	 * 
	 * @precondition true
	 * @postcondition true
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param dateOfBirth the patient's date of birth
	 */
	public void getPatient(String firstName, String lastName, LocalDate dateOfBirth) {
		Patient patient = this.db.retrievePatient(firstName, lastName, dateOfBirth);
		if (patient != null) {
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
			this.controlsActiveProperty.set(false);
		} else {
			this.disableControls();
		}
	}

	private void disableControls() {
		this.fnameProperty.set("");
		this.lnameProperty.set("");
		this.genderProperty.set("");
		this.dobProperty.set(null);
		this.cityProperty.set("");
		this.stateProperty.set(null);
		this.addressProperty.set("");
		this.zipcodeProperty.set("");
		this.phoneNumProperty.set("");
		this.isActiveProperty.set(false);
		this.controlsActiveProperty.set(true);
	}
	
	/**
	 * Confirms this patient's new info.
	 */
	public void onConfirm() {
		Patient patient = new Patient(this.fnameProperty.get(), this.lnameProperty.get(), this.cityProperty.get(), this.addressProperty.get(), this.zipcodeProperty.get(), this.phoneNumProperty.get(), this.genderProperty.get().substring(0, 1), this.stateProperty.get().getAbbreviation(), this.dobProperty.get(), this.isActiveProperty.get());
		this.db.updatePatient(this.patientId, patient);
	}

}
