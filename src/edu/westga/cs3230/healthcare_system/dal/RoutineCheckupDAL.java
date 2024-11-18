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
}
