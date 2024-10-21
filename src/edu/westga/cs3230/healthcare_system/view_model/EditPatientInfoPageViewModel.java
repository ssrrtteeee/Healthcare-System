package edu.westga.cs3230.healthcare_system.view_model;

import java.time.LocalDate;

import edu.westga.cs3230.healthcare_system.dal.DBUpdatePatient;
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
	private ObjectProperty<Integer> idProperty; 
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

	/**
	 * Gets the id property.
	 *
	 * @return the id property
	 */
	public ObjectProperty<Integer> getIdProperty() {
		return this.idProperty;
	}

	/**
	 * Gets the fname property.
	 *
	 * @return the fname property
	 */
	public StringProperty getFnameProperty() {
		return this.fnameProperty;
	}

	/**
	 * Gets the lname property.
	 *
	 * @return the lname property
	 */
	public StringProperty getLnameProperty() {
		return this.lnameProperty;
	}

	/**
	 * Gets the genders list property.
	 *
	 * @return the genders list property
	 */
	public ListProperty<String> getGendersListProperty() {
		return this.gendersListProperty;
	}
	
	/**
	 * Gets the gender property.
	 *
	 * @return the genderProperty
	 */
	public StringProperty getGenderProperty() {
		return this.genderProperty;
	}

	/**
	 * Gets the dob property.
	 *
	 * @return the dobProperty
	 */
	public ObjectProperty<LocalDate> getDobProperty() {
		return this.dobProperty;
	}

	/**
	 * Gets the city property.
	 *
	 * @return the cityProperty
	 */
	public StringProperty getCityProperty() {
		return this.cityProperty;
	}

	/**
	 * Gets the state property.
	 *
	 * @return the stateProperty
	 */
	public ListProperty<USStates> getStatesProperty() {
		return this.statesProperty;
	}

	/**
	 * Gets the state property.
	 *
	 * @return the state property
	 */
	public ObjectProperty<USStates> getStateProperty() {
		return this.stateProperty;
	}
	
	/**
	 * Gets the address property.
	 *
	 * @return the addressProperty
	 */
	public StringProperty getAddressProperty() {
		return this.addressProperty;
	}

	/**
	 * Gets the zipcode property.
	 *
	 * @return the zipcodeProperty
	 */
	public StringProperty getZipcodeProperty() {
		return this.zipcodeProperty;
	}

	/**
	 * Gets the phone num property.
	 *
	 * @return the phoneNumProperty
	 */
	public StringProperty getPhoneNumProperty() {
		return this.phoneNumProperty;
	}

	/**
	 * Gets the active property.
	 *
	 * @return the isActiveProperty
	 */
	public BooleanProperty getIsActiveProperty() {
		return this.isActiveProperty;
	}
	
	/**
	 * Gets the controls active property.
	 *
	 * @return the controls active property
	 */
	public BooleanProperty getControlsActiveProperty() {
		return this.controlsActiveProperty;
	}

	/**
	 * Instantiates a new edit patient info page view model.
	 */
	public EditPatientInfoPageViewModel() {
		this.idProperty = new SimpleObjectProperty<>(0);
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
		this.setupIdListener();
		this.disableControls();
	}
	
	private void setupIdListener() {
		this.idProperty.addListener((unused, newVal, oldVal) -> {
			this.getPatient();
		});

		
	}

	/**
	 * Gets the info for the patient whose id is selected.
	 */
	public void getPatient() {
		Patient patient = DBUpdatePatient.getPatient(this.idProperty.get());
		if (patient != null) {
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
	 * On confirm.
	 */
	public void onConfirm() {
		Patient patient = new Patient(this.fnameProperty.get(), this.lnameProperty.get(), this.cityProperty.get(), this.addressProperty.get(), this.zipcodeProperty.get(), this.phoneNumProperty.get(), this.genderProperty.get().substring(0, 1), this.stateProperty.get().getAbbreviation(), this.dobProperty.get(), this.isActiveProperty.get());
		System.out.println(this.stateProperty.get().getAbbreviation());
		System.out.println(patient.getState());
		DBUpdatePatient.updatePatient(this.idProperty.get(), patient);
	}
}
