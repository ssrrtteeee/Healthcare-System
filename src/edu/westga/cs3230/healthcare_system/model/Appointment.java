package edu.westga.cs3230.healthcare_system.model;

import java.time.LocalDateTime;

import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;

/**
 * Represents an appointment.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class Appointment {
	private LocalDateTime appointmentTime;
	private int doctorId;
	private int patientId;
	private String reason;
	
	/**
	 * Creates a new appointment with the specified information.
	 * 
	 * @precondition appointmentTime != null && reason != null
	 * @postcondition true
	 * @param appointmentTime the time of the appointment
	 * @param doctorId the doctor id
	 * @param patientId the patient id
	 * @param reason the reason for the appointment
	 */
	public Appointment(LocalDateTime appointmentTime, int doctorId, int patientId, String reason) {
		if (appointmentTime == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_APMT_TIME);
		}
		if (reason == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_APMT_REASON);
		}
		this.appointmentTime = appointmentTime;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.reason = reason;
	}

	/**
	 * Gets the time.
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the appointmentTime
	 */
	public LocalDateTime getAppointmentTime() {
		return this.appointmentTime;
	}

	/**
	 * Gets the doctor ID
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the doctorId
	 */
	public int getDoctorId() {
		return this.doctorId;
	}

	/**
	 * Gets the patient ID
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the patientId
	 */
	public int getPatientId() {
		return this.patientId;
	}

	/**
	 * Gets the reason
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the reason
	 */
	public String getReason() {
		return this.reason;
	}
}
