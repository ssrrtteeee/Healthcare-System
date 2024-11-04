package edu.westga.cs3230.healthcare_system.model;

import java.time.LocalDateTime;

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

    public RoutineCheckup(LocalDateTime appointmentTime, int doctorId, int recordingNurseId,
                          double patientHeight, double patientWeight, int systolicBP,
                          int diastolicBP, int bodyTemperature, int pulse,
                          String symptoms, String initialDiagnosis) {
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

    public LocalDateTime getAppointmentTime() {
        return this.appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public int getDoctorId() {
        return this.doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getRecordingNurseId() {
        return this.recordingNurseId;
    }

    public void setRecordingNurseId(int recordingNurseId) {
        this.recordingNurseId = recordingNurseId;
    }

    public double getPatientHeight() {
        return this.patientHeight;
    }

    public void setPatientHeight(double patientHeight) {
        this.patientHeight = patientHeight;
    }

    public double getPatientWeight() {
        return this.patientWeight;
    }

    public void setPatientWeight(double patientWeight) {
        this.patientWeight = patientWeight;
    }

    public int getSystolicBP() {
        return this.systolicBP;
    }

    public void setSystolicBP(int systolicBP) {
        this.systolicBP = systolicBP;
    }

    public int getDiastolicBP() {
        return this.diastolicBP;
    }

    public void setDiastolicBP(int diastolicBP) {
        this.diastolicBP = diastolicBP;
    }

    public int getBodyTemperature() {
        return this.bodyTemperature;
    }

    public void setBodyTemperature(int bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public int getPulse() {
        return this.pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public String getSymptoms() {
        return this.symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getInitialDiagnosis() {
        return this.initialDiagnosis;
    }

    public void setInitialDiagnosis(String initialDiagnosis) {
        this.initialDiagnosis = initialDiagnosis;
    }
}

