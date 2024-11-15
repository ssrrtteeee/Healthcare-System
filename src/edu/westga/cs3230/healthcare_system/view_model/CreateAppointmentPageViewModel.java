package edu.westga.cs3230.healthcare_system.view_model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import edu.westga.cs3230.healthcare_system.dal.AppointmentDAL;
import edu.westga.cs3230.healthcare_system.dal.DoctorDAL;
import edu.westga.cs3230.healthcare_system.model.Appointment;
import edu.westga.cs3230.healthcare_system.model.Doctor;
import edu.westga.cs3230.healthcare_system.model.Patient;
import javafx.beans.property.StringProperty;

/**
 * ViewModel for CreateAppointmentPage.
 * 
 * @author Stefan
 * @version Fall 2024
 */
public class CreateAppointmentPageViewModel {
	private StringProperty doctorErrorMsgProperty;
	private StringProperty appointmentDateErrorMsgProperty;
	private StringProperty appointmentTimeErrorMsgProperty;
	private StringProperty appointmentReasonErrorMsgProperty;
	
	private int patientId;
	private Patient patient;
	private Doctor doctor;
	private DoctorDAL doctorDB;
	private AppointmentDAL appointmentDB;
	
	/**
	 * Instantiates a new edit patient info page view model.
	 */
	public CreateAppointmentPageViewModel() {
		this.doctorDB = new DoctorDAL();
		this.appointmentDB = new AppointmentDAL();
	}
	
	/**
	 * Gets the selected doctor
	 * @return the selected doctor
	 */
	public Doctor getDoctor() {
		return this.doctor;
	}
	
	/**
	 * Gets the fnameErrorMsg property
	 * 
	 * @precondition true
	 * @postconditino true
	 * @return the fnameErrorMsg property
	 */
	public StringProperty getDoctorErrorMsgProperty() {
		return this.doctorErrorMsgProperty;
	}

	/**
	 * Gets the lnameErrorMsg property
	 * 
	 * @precondition true
	 * @postconditino true
	 * @return the lnameErrorMsg property
	 */
	public StringProperty getAppointmentDateErrorMsgProperty() {
		return this.appointmentDateErrorMsgProperty;
	}

	/**
	 * Gets the dobErrorMsg property
	 * 
	 * @precondition true
	 * @postconditino true
	 * @return the dobErrorMsg property
	 */
	public StringProperty getAppointmentTimeErrorMsgProperty() {
		return this.appointmentTimeErrorMsgProperty;
	}

	/**
	 * Gets the cityErrorMsg property
	 * 
	 * @precondition true
	 * @postconditino true
	 * @return the cityErrorMsg property
	 */
	public StringProperty getAppointmentReasonErrorMsgProperty() {
		return this.appointmentReasonErrorMsgProperty;
	}
	
	/**
	 * Gets a list of all timeslots that both the specified doctor and patient are free.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param date the day to retrieve the timeslots for
	 * @return the collection of local times, or null if an error occurs.
	 */
	public Collection<LocalTime> getOpenTimes(LocalDate date) {
		return this.appointmentDB.getOpenTimeSlots(date, this.doctor.getId(), this.patientId);
	}
	
	/**
	 * Creates a new appointment
	 * @param doctor the doctor the patient will be seing for the appointment
	 * @param date the date of the appointment
	 * @param time the time of the appointment
	 * @param reason the reason for the appointment
	 * @return true if the appointment is scheduled, false otherwise
	 */
	public boolean createAppointment(Doctor doctor, LocalDate date, LocalTime time, String reason) {
		LocalDateTime combinedDateTime = date.atTime(time);
		Appointment appointment = new Appointment(combinedDateTime, doctor.getId(), this.patientId, reason);
		return this.appointmentDB.scheduleAppointment(appointment);
	}
	
	/**
	 * Gets the info for the doctor whose info has been selected.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param firstName the first name
	 * @param lastName the last name
	 * @return the doctor that was searched for or null if the doctor wasn't found
	 */
	public Doctor getDoctor(String firstName, String lastName) {
		Doctor doctor = this.doctorDB.retrieveDoctor(firstName, lastName);
		this.doctor = doctor == null ? this.doctor : doctor;
		return doctor;
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
		this.patient = patient;
		this.patientId = patient.getId();
	}
}
