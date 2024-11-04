package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.westga.cs3230.healthcare_system.model.Appointment;

public class AppointmentDAL {
	private static final int APPOINTMENT_DURATION = 20;
	
	/**
	 * Gets a list of all timeslots that both the specified doctor and patient are free.
	 * 
	 * @precondition true
	 * @postcondition true
	 * @param day the day to retrieve the timeslots for
	 * @return the collection of specialties, or null if an error occurs.
	 */
	public Collection<LocalTime> getOpenTimeSlots(LocalDate day, int did, int pid) {
		String query =
				"SELECT appointment_time "
			  + "FROM appointment "
			  + "WHERE doctor_id = ? AND patient_id = ? AND DATE(appointment_time) = ?";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {
			int totalTimeSlots = 24 * 60 / APPOINTMENT_DURATION;
			Set<LocalTime> occupiedSlots = new HashSet<LocalTime>();
			Set<LocalTime> result = new HashSet<LocalTime>(totalTimeSlots);
			
			stmt.setInt(1, did);
			stmt.setInt(2, pid);
			stmt.setDate(3, Date.valueOf(day));
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Time time = rs.getTime(1);
				
				occupiedSlots.add(time.toLocalTime());
			}
			for (int i = 0; i < totalTimeSlots; i++) {
				int hour = i / 3;
				int minute = i * 20 % 60;
				result.add(LocalTime.of(hour, minute));
			}
			
			result.removeAll(occupiedSlots);
			
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
	    }
	}
	
	public boolean scheduleAppointment(Appointment appointment) {
		String query =
				  "INSERT INTO appointment (appointment_time, doctor_id, patient_id, reason)"
				+ "VALUE (?, ?, ?, ?)";
	    try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)) {
		    
			stmt.setTimestamp(1, Timestamp.valueOf(appointment.getAppointmentTime()));
			stmt.setInt(2, appointment.getDoctorId());
			stmt.setInt(3, appointment.getPatientId());
			stmt.setString(4, appointment.getReason());
	
			stmt.executeUpdate();
			return true;
	    } catch (SQLException ex) {
			System.out.println("SQLException: "	+ ex.getMessage());
			System.out.println("SQLState: "		+ ex.getSQLState());
			System.out.println("VendorError: "	+ ex.getErrorCode());
			return false;
		} catch (Exception e) {
	        System.out.println(e.toString());
	        return false;
	    }
	}
}
