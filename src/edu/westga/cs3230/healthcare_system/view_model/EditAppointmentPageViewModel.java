package edu.westga.cs3230.healthcare_system.view_model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import edu.westga.cs3230.healthcare_system.dal.AppointmentDAL;
import edu.westga.cs3230.healthcare_system.dal.DoctorDAL;
import edu.westga.cs3230.healthcare_system.model.Appointment;
import edu.westga.cs3230.healthcare_system.model.Doctor;
import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * ViewModel for CreateAppointmentPage.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class EditAppointmentPageViewModel {
	private StringProperty doctorErrorMsgProperty;
	private StringProperty appointmentReasonErrorMsgProperty;
	
	private StringProperty oldDoctorProperty;
	private StringProperty oldTimeProperty;
	private StringProperty oldReasonProperty;
	private StringProperty newDoctorProperty;
	private ObjectProperty<LocalDate> newDateProperty;
	private ListProperty<LocalTime> timeslotsProperty;
	private ObjectProperty<LocalTime> newTimeProperty;
	private StringProperty newReasonProperty;
	
	private Patient patient;
	private Doctor doctor;
	private Appointment oldApmt;
	private DoctorDAL doctorDB;
	private AppointmentDAL appointmentDB;
	
	/**
	 * Instantiates a new edit appointment page view model.
	 * 
     * @precondition true
	 * @postcondition true
	 */
	public EditAppointmentPageViewModel() {
		this.doctorErrorMsgProperty = new SimpleStringProperty();
		this.appointmentReasonErrorMsgProperty = new SimpleStringProperty();
		this.oldDoctorProperty = new SimpleStringProperty();
		this.oldTimeProperty = new SimpleStringProperty();
		this.oldReasonProperty = new SimpleStringProperty();
		this.newDoctorProperty = new SimpleStringProperty();
		this.newDateProperty = new SimpleObjectProperty<LocalDate>();
		this.timeslotsProperty = new SimpleListProperty<LocalTime>();
		this.newTimeProperty = new SimpleObjectProperty<LocalTime>();
		this.newReasonProperty = new SimpleStringProperty();
		
		this.doctorDB = new DoctorDAL();
		this.appointmentDB = new AppointmentDAL();
		
		this.newDateProperty.addListener((unused, oldVal, newVal) -> {
			this.populateTimeSlots();
		});
	}
	
	/**
	 * Gets the doctor error message property
	 * 
	 * @precondition true
	 * @postconditino true
	 * @return the doctor error message property
	 */
	public StringProperty getDoctorErrorMsgProperty() {
		return this.doctorErrorMsgProperty;
	}

	/**
	 * Gets the reason error message property
	 * 
	 * @precondition true
	 * @postconditino true
	 * @return the reason error message property
	 */
	public StringProperty getAppointmentReasonErrorMsgProperty() {
		return this.appointmentReasonErrorMsgProperty;
	}
		
	/**
	 * Gets the old doctor property
     * 
     * @precondition true
	 * @postcondition true
	 * @return the oldDoctorProperty
	 */
	public StringProperty getOldDoctorProperty() {
		return this.oldDoctorProperty;
	}

	/**
	 * Gets the old time property
     * 
     * @precondition true
	 * @postcondition true
	 * @return the oldTimeProperty
	 */
	public StringProperty getOldTimeProperty() {
		return this.oldTimeProperty;
	}

	/**
	 * Gets the old reason property
     * 
     * @precondition true
	 * @postcondition true
	 * @return the oldReasonProperty
	 */
	public StringProperty getOldReasonProperty() {
		return this.oldReasonProperty;
	}

	/**
	 * Gets the new doctor property
     * 
     * @precondition true
	 * @postcondition true
	 * @return the newDoctorProperty
	 */
	public StringProperty getNewDoctorProperty() {
		return this.newDoctorProperty;
	}

	/**
	 * Gets the new date property
     * 
     * @precondition true
	 * @postcondition true
	 * @return the newDateProperty
	 */
	public ObjectProperty<LocalDate> getNewDateProperty() {
		return this.newDateProperty;
	}

	/**
	 * Gets the new timeslots set property
     * 
     * @precondition true
	 * @postcondition true
	 * @return the timeslotsProperty
	 */
	public ListProperty<LocalTime> getTimeslotsProperty() {
		return this.timeslotsProperty;
	}

	/**
	 * Gets the new time property
     * 
     * @precondition true
	 * @postcondition true
	 * @return the newTimeProperty
	 */
	public ObjectProperty<LocalTime> getNewTimeProperty() {
		return this.newTimeProperty;
	}

	/**
	 * Gets the new reason property
     * 
     * @precondition true
	 * @postcondition true
	 * @return the newReasonProperty
	 */
	public StringProperty getNewReasonProperty() {
		return this.newReasonProperty;
	}

	/**
	 * Updates the appointment with the current information
	 * 
     * @precondition true
	 * @postcondition true
	 * @return true if the appointment could be updated, false otherwise
	 */
	public boolean updateAppointment() {
		if (this.newTimeProperty.get() == null || this.newReasonProperty.get().isBlank()) {
			return false;
		}
		LocalDateTime combinedDateTime = LocalDateTime.of(this.newDateProperty.get(), this.newTimeProperty.get());
		Appointment newApmt = new Appointment(combinedDateTime, this.doctor.getId(), this.patient.getId(), this.newReasonProperty.get());
		return this.appointmentDB.updateAppointment(this.oldApmt, newApmt);
	}
	
	/**
	 * Gets the patient the appointment is for.
	 * 
     * @precondition true
	 * @postcondition true
	 * @return the patient the appointment is for
	 */
	public Patient getPatient() {
		return this.patient;
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
			throw new IllegalArgumentException("Patient cannot be null.");
		}
		this.patient = patient;
	}

	/**
	 * Sets the doctor to the one with the specified information
     * 
     * @precondition fname != null && lname != null && !fname.isBlank() && !lname.isBlank()
	 * @postcondition true
	 * @param fname
	 * @param lname
	 * @return true if the doctor could be changed, false otherwise
	 */
	public boolean setDoctor(String fname, String lname) {
		Doctor newDoctor = this.doctorDB.retrieveDoctor(fname, lname);
		
		if (newDoctor == null) {
			return false;
		}
		
		this.doctor = newDoctor;
		this.newDoctorProperty.set(newDoctor.getFirstName() + " " + newDoctor.getLastName());
		this.populateTimeSlots();
		return true;
	}
	
	/**
	 * Sets the old appointment data to that of the specified appointment
     * 
     * @precondition apmt != null
	 * @postcondition true
	 * @param apmt the appointment
	 */
	public void setOldAppointment(Appointment apmt) {
		if (apmt == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_APPOINTMENT);
		}
		this.oldApmt = apmt;
		this.doctor = this.doctorDB.retrieveDoctor(apmt.getDoctorId());
		this.oldDoctorProperty.set(this.doctor.getFirstName() + " " + this.doctor.getLastName());
		this.oldTimeProperty.set(DateTimeFormatter.ofPattern("hh:mm a").format(apmt.getAppointmentTime()));
		this.oldReasonProperty.set(apmt.getReason());
		this.newDoctorProperty.set(this.oldDoctorProperty.get());
		this.newDateProperty.set(apmt.getAppointmentTime().toLocalDate());
		this.populateTimeSlots();
		this.newReasonProperty.set(apmt.getReason());
	}

	/**
	 * Populates the time slots to match the current appointment info.
     * 
     * @precondition true
	 * @postcondition true
	 */
	public void populateTimeSlots() {
		this.timeslotsProperty.set(FXCollections.observableArrayList(this.appointmentDB.getOpenTimeSlots(this.newDateProperty.get(), this.doctor.getId(), this.patient.getId())));
	}
}
