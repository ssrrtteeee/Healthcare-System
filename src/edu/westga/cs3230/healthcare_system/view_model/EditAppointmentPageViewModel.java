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
			this.resetTimeSlots();
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
	 * @return the oldDoctorProperty
	 */
	public StringProperty getOldDoctorProperty() {
		return this.oldDoctorProperty;
	}

	/**
	 * @return the oldTimeProperty
	 */
	public StringProperty getOldTimeProperty() {
		return this.oldTimeProperty;
	}

	/**
	 * @return the oldReasonProperty
	 */
	public StringProperty getOldReasonProperty() {
		return this.oldReasonProperty;
	}

	/**
	 * @return the newDoctorProperty
	 */
	public StringProperty getNewDoctorProperty() {
		return this.newDoctorProperty;
	}

	/**
	 * @return the newDateProperty
	 */
	public ObjectProperty<LocalDate> getNewDateProperty() {
		return this.newDateProperty;
	}

	/**
	 * @return the timeslotsProperty
	 */
	public ListProperty<LocalTime> getTimeslotsProperty() {
		return this.timeslotsProperty;
	}

	/**
	 * @return the newTimeProperty
	 */
	public ObjectProperty<LocalTime> getNewTimeProperty() {
		return this.newTimeProperty;
	}

	/**
	 * @return the newReasonProperty
	 */
	public StringProperty getNewReasonProperty() {
		return this.newReasonProperty;
	}

	/**
	 * Updates the appointment
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
	 * @return the patient the appointment is for
	 */
	public Patient getPatient() {
		return this.patient;
	}
	
	/**
	 * Sets the patient.
	 * @param patient the patient
	 */
	public void setPatient(Patient patient) {
		if (patient == null) {
			throw new IllegalArgumentException("Patient cannot be null.");
		}
		this.patient = patient;
	}

	public boolean setDoctor(String fname, String lname) {
		Doctor newDoctor = this.doctorDB.retrieveDoctor(fname, lname);
		
		if (newDoctor == null) {
			return false;
		}
		
		this.doctor = newDoctor;
		this.newDoctorProperty.set(newDoctor.getFirstName() + " " + newDoctor.getLastName());
		this.resetTimeSlots();
		return true;
	}
	
	public void setOldAppointment(Appointment apmt) {
		if (apmt == null) {
			throw new IllegalArgumentException("Old appointment cannot be null.");
		}
		this.oldApmt = apmt;
		this.doctor = this.doctorDB.retrieveDoctor(apmt.getDoctorId());
		this.oldDoctorProperty.set(this.doctor.getFirstName() + " " + this.doctor.getLastName());
		this.oldTimeProperty.set(DateTimeFormatter.ofPattern("hh:mm a").format(apmt.getAppointmentTime()));
		this.oldReasonProperty.set(apmt.getReason());
		this.newDoctorProperty.set(this.oldDoctorProperty.get());
		this.newDateProperty.set(apmt.getAppointmentTime().toLocalDate());
		this.resetTimeSlots();
		this.newReasonProperty.set(apmt.getReason());
	}

	public void resetTimeSlots() {
		this.timeslotsProperty.set(FXCollections.observableArrayList(this.appointmentDB.getOpenTimeSlots(this.newDateProperty.get(), this.doctor.getId(), this.patient.getId())));
	}
}
