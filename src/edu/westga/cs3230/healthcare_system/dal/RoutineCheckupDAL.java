package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import edu.westga.cs3230.healthcare_system.model.RoutineCheckup;

public class RoutineCheckupDAL {

	public void addRoutineCheckup(RoutineCheckup routineCheckup) throws Exception{
		String query = "INSERT INTO visit_details (appointment_time, doctor_id, recording_nurse_id, "
                + "patient_height, patient_weight, systolic_bp, diastolic_bp, "
                + "body_temperature, pulse, symptoms, initial_diagnosis) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)) {
		    
	    	stmt.setTimestamp(1, Timestamp.valueOf(routineCheckup.getAppointmentTime()));
	    	stmt.setInt(2, routineCheckup.getDoctorId());
	    	stmt.setInt(3, routineCheckup.getRecordingNurseId());
	    	stmt.setDouble(4, routineCheckup.getPatientHeight());
	    	stmt.setDouble(5, routineCheckup.getPatientWeight());
	    	stmt.setInt(6, routineCheckup.getSystolicBP());
	    	stmt.setInt(7, routineCheckup.getDiastolicBP());
	    	stmt.setInt(8, routineCheckup.getBodyTemperature());
	    	stmt.setInt(9, routineCheckup.getPulse());
	    	stmt.setString(10, routineCheckup.getSymptoms());
	    	stmt.setString(11, routineCheckup.getInitialDiagnosis());
	
			stmt.executeUpdate();
	    }
	}
}
