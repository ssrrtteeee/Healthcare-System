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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.westga.cs3230.healthcare_system.model.Appointment;
import edu.westga.cs3230.healthcare_system.model.Patient;
import javafx.util.Pair;

public class AppointmentDAL {
	private static final int APPOINTMENT_DURATION = 20;
	
	/**
	 * Gets a list of all timeslots that both the specified doctor and patient are free.
	 * 
	 * @precondition true
	 * @postcondition true
	 * @param day the day to retrieve the timeslots for
	 * @param did the doctor id
	 * @param pid the patient id
	 * @return the collection of specialties, or null if an error occurs.
	 */
	public Collection<LocalTime> getOpenTimeSlots(LocalDate day, int did, int pid) {
		String query =
				"SELECT appointment_time "
			  + "FROM appointment "
			  + "WHERE (doctor_id = ? OR patient_id = ?) AND DATE(appointment_time) = ? "
			  + "ORDER BY appointment_time ASC";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {
			int totalTimeSlots = 24 * 60 / APPOINTMENT_DURATION;
			Set<LocalTime> occupiedSlots = new HashSet<LocalTime>();
			Collection<LocalTime> result = new ArrayList<LocalTime>(totalTimeSlots);
			
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
				LocalTime time = LocalTime.of(hour, minute);

				if (LocalDateTime.of(day, time).isAfter(LocalDateTime.now())) {
					result.add(time);					
				}
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

	public Collection<Pair<String, Appointment>> getAppointmentsFor(Patient patient) {
		if (patient == null) {
			throw new IllegalArgumentException("Patient cannot be null.");
		}
		
		String query =
				"SELECT doctor_id, patient_id, appointment_time, reason, f_name, l_name "
			  + "FROM appointment JOIN doctor ON doctor_id = id "
			  + "WHERE patient_id = ?";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {
			stmt.setInt(1, patient.getId());
			ResultSet rs = stmt.executeQuery();

			Collection<Pair<String, Appointment>> result = new HashSet<Pair<String, Appointment>>(); 
			
			while (rs.next()) {
				int doctorId = rs.getInt(1);
				int patientId = rs.getInt(2);
				LocalDateTime appointmentTime = rs.getTimestamp(3).toLocalDateTime();
				String reason = rs.getString(4);
				String fName = rs.getString(5);
				String lName = rs.getString(6);
				if (appointmentTime.isAfter(LocalDateTime.now())) {
					result.add(new Pair<String, Appointment>(fName + " " + lName, new Appointment(appointmentTime, doctorId, patientId, reason)));
				}
			}

			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
	    }
	}

	public boolean updateAppointment(Appointment oldApmt, Appointment newApmt) {
		if (oldApmt == null) {
			throw new IllegalArgumentException("Old appointment cannot be null.");
		}
		if (newApmt == null) {
			throw new IllegalArgumentException("New appointment cannot be null.");
		}
		
		String query =
				"UPDATE appointment "
			  + "SET doctor_id = ?, appointment_time = ?, reason = ? "
			  + "WHERE doctor_id = ? AND appointment_time = ?";
		
        try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)
		) {
        	stmt.setInt(1, newApmt.getDoctorId());
			stmt.setTimestamp(2, Timestamp.valueOf(newApmt.getAppointmentTime()));
			stmt.setString(3, newApmt.getReason());
			stmt.setInt(4, oldApmt.getDoctorId());
			stmt.setTimestamp(5, Timestamp.valueOf(oldApmt.getAppointmentTime()));
			
			stmt.executeUpdate();
			
			return true;
		} catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
	}
}
