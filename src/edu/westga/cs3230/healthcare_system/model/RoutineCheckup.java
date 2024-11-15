package edu.westga.cs3230.healthcare_system.model;

import java.time.LocalDateTime;

import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;

/**
 * The routine checkup class.
 * @author Stefan
 * @version Fall 2024
 *
 */
public class RoutineCheckup {
    private LocalDateTime appointmentTime;
    private int doctorId;
    private int recordingNurseId;
    private double patientHeight;
    private double patientWeight;
    private int systolicBP;
    private int diastolicBP;
    private int bodyTemperature; 
    private int pulse; 
    private String symptoms;
    private String initialDiagnosis;

    /**
     * Instantiates a new routine checkup with the specified values.
     * 
     * @precondition appointmentTime != null && symptoms != null && initialDiagnosis != null
     * @postcondition true
     * @param appointmentTime
     * @param doctorId
     * @param recordingNurseId
     * @param patientHeight
     * @param patientWeight
     * @param systolicBP
     * @param diastolicBP
     * @param bodyTemperature
     * @param pulse
     * @param symptoms
     * @param initialDiagnosis
     */
    public RoutineCheckup(LocalDateTime appointmentTime, int doctorId, int recordingNurseId,
                          double patientHeight, double patientWeight, int systolicBP,
                          int diastolicBP, int bodyTemperature, int pulse,
                          String symptoms, String initialDiagnosis) {
    	if (appointmentTime == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_APMT_TIME);
    	}
    	if (symptoms == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_SYMPTOMS);
    	}
    	if (initialDiagnosis == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_INITIAL_DIAGNOSIS);
    	}
    	if (initialDiagnosis.isBlank()) {
    		throw new IllegalArgumentException(ErrMsgs.BLANK_INITIAL_DIAGNOSIS);
    	}
    	
        this.appointmentTime = appointmentTime;
        this.doctorId = doctorId;
        this.recordingNurseId = recordingNurseId;
        this.patientHeight = patientHeight;
        this.patientWeight = patientWeight;
        this.systolicBP = systolicBP;
        this.diastolicBP = diastolicBP;
        this.bodyTemperature = bodyTemperature;
        this.pulse = pulse;
        this.symptoms = symptoms;
        this.initialDiagnosis = initialDiagnosis;
    }

    /**
     * Gets the appointment time
     * 
     * @precondition true
	 * @postcondition true
     * @return the appointment time
     */
    public LocalDateTime getAppointmentTime() {
        return this.appointmentTime;
    }

    /**
     * Gets the doctor's ID
     * 
     * @precondition true
	 * @postcondition true
     * @return the ID
     */
    public int getDoctorId() {
        return this.doctorId;
    }

    /**
     * Gets the recording nurse's ID
     * 
     * @precondition true
	 * @postcondition true
     * @return the ID
     */
    public int getRecordingNurseId() {
        return this.recordingNurseId;
    }

    /**
     * Gets the patient's height
     * 
     * @precondition true
	 * @postcondition true
     * @return the height
     */
    public double getPatientHeight() {
        return this.patientHeight;
    }

    /**
     * Gets the patient's weight
     * 
     * @precondition true
	 * @postcondition true
     * @return the weight
     */
    public double getPatientWeight() {
        return this.patientWeight;
    }

    /**
     * Gets the systolic blood pressure
     * 
     * @precondition true
	 * @postcondition true
     * @return the systolic BP
     */
    public int getSystolicBP() {
        return this.systolicBP;
    }

    /**
     * Gets the diastolic blood pressure
     * 
     * @precondition true
	 * @postcondition true
     * @return the diastolic BP
     */
    public int getDiastolicBP() {
        return this.diastolicBP;
    }

    /**
     * Gets the body temperature
     * 
     * @precondition true
	 * @postcondition true
     * @return the temperature
     */
    public int getBodyTemperature() {
        return this.bodyTemperature;
    }

    /**
     * Gets the pulse
     * 
     * @precondition true
	 * @postcondition true
     * @return the pulse
     */
    public int getPulse() {
        return this.pulse;
    }

    /**
     * Gets the symptoms
     * 
     * @precondition true
	 * @postcondition true
     * @return the symptoms
     */
    public String getSymptoms() {
        return this.symptoms;
    }

    /**
     * Gets the initial diagnosis
     * 
     * @precondition true
	 * @postcondition true
     * @return the initial diagnosis
     */
    public String getInitialDiagnosis() {
        return this.initialDiagnosis;
    }
}
