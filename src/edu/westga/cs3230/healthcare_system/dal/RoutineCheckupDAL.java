package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import edu.westga.cs3230.healthcare_system.model.RoutineCheckup;
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;

/**
 * Class used to send and get routine checkup for appointments from the DB.
 * 
 * @author Stefan
 * @version Fall 2024
 */
public class RoutineCheckupDAL {

	/**
	 * Adds the specified routine checkup to the database.
	 * 
	 * @precondition routineCheckup != null
	 * @postcondition true
	 * @param routineCheckup the routine checkup to add
	 * @throws Exception
	 */
	public void addRoutineCheckup(RoutineCheckup routineCheckup) throws Exception {
		if (routineCheckup == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_ROUTINE_CHECKUP);
		}
		
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
	
	/**
	 * Checks if there is already a routine checkup for the given appointment time and doctor id.
	 * @param appointmentTime the appointment time and date
	 * @param doctorId the id for the doctor
	 * @return true if a routine checkup exists, false otherwise
	 */
	public RoutineCheckup getRoutineCheckup(LocalDateTime appointmentTime, int doctorId) {
		String query =
				"SELECT * "
			  + "FROM visit_details "
			  + "WHERE doctor_id = ?  AND appointment_time = ?";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {	
			stmt.setInt(1, doctorId);
			stmt.setTimestamp(2, Timestamp.valueOf(appointmentTime));
			
			ResultSet rs = stmt.executeQuery();
			rs.next();
			LocalDateTime time = rs.getTimestamp(1) == null ? null : rs.getTimestamp(1).toLocalDateTime();
			int nurseId = rs.getInt(3);
			Double height = rs.getDouble(4);
			Double weight = rs.getDouble(5);
			int systolicBP = rs.getInt(6);
			int dastolicBP = rs.getInt(7);
			int bodyTemperature = rs.getInt(8);
			int pulse = rs.getInt(9);
			String symptoms = rs.getString(10);
			String initialDiagnosis = rs.getString(11);
			String finalDiagnosis = rs.getString(12);
			
			RoutineCheckup checkup = new RoutineCheckup(time, doctorId, nurseId, height, weight, systolicBP, dastolicBP, bodyTemperature, pulse, symptoms, initialDiagnosis);
			checkup.setFinalDiagnosis(finalDiagnosis);
			return checkup;
			
		} catch (SQLException ex) {
			return null;
	    }
	}
	
	/**
	 * Checks if there is already a routine checkup for the given appointment time and doctor id.
	 * @param appointmentTime the appointment time and date
	 * @param doctorId the id for the doctor
	 * @return true if a routine checkup exists, false otherwise
	 */
	public boolean hasRoutineCheckup(LocalDateTime appointmentTime, int doctorId) {
		String query =
				"SELECT 0 "
			  + "FROM visit_details "
			  + "WHERE doctor_id = ?  AND appointment_time = ?";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {	
			stmt.setInt(1, doctorId);
			stmt.setTimestamp(2, Timestamp.valueOf(appointmentTime));
			
			ResultSet rs = stmt.executeQuery();
			return rs.next();
			
		} catch (SQLException e) {
			return false;
	    }
	}
	
	public boolean updateFinalDiagnosis(LocalDateTime appointmentTime, int doctorId, String diagnosis) {
		if (diagnosis == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_FINAL_DIAGNOSIS);
		}
		if (diagnosis.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_FINAL_DIAGNOSIS);
		}
		
		String query =
				"UPDATE visit_details "
			  + "SET final_diagnosis = ?"
			  + "WHERE appointment_time = ? AND doctor_id = ?";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {	
			stmt.setString(1, diagnosis);
			stmt.setTimestamp(2, Timestamp.valueOf(appointmentTime));
			stmt.setInt(3, doctorId);
			
			stmt.execute();
			
			return true;
			
		} catch (SQLException e) {
			return false;
	    }
	}
}
