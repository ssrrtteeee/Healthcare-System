package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;

import edu.westga.cs3230.healthcare_system.model.Admin;
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;

/**
 * The data access object for the Admin class
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class AdminDAL {
	/**
	 * Gets the admin with the specified username and password.
	 * 
	 * @precondition username != null && password != null && !username.isBlank() && !password.isBlank()
	 * @postcondition true
	 * @param username the username
	 * @param password the password
	 * @return the admin with the specified information, or null if none could be found.
	 */
	public Admin getAdmin(String username, String password) {
		if (username == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_USERNAME);
		}
		if (password == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_PASSWORD);
		}
		if (username.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_USERNAME);
		}
		if (password.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_PASSWORD);
		}
		
		Admin result = null;
		String query = "select f_name, l_name, id from admin where username=? and password=?";
        try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)) {
		    
			stmt.setString(1, username);
			stmt.setString(2, password);
	
			ResultSet rs = stmt.executeQuery();
			rs.next();
			String fname = rs.getString(1);
			String lname = rs.getString(2);
			int id = rs.getInt(3);
			result = new Admin(fname, lname, username, id);
        } catch (SQLException ex) {
			System.out.println("SQLException: "	+ ex.getMessage());
			System.out.println("SQLState: "		+ ex.getSQLState());
			System.out.println("VendorError: "	+ ex.getErrorCode());
        }
		return result;
	}
	
	/**
	 * Gets info for all visits between the specified dates, formatted as a dictionary.
	 * Keys are: visitDate, patientID, patientName, doctorName, nurseName, diagnosis
	 * 
	 * @precondition startDate != null && endDate != null && !startDate.isAfter(endDate)
	 * @postcondition true
	 * @param startDate the starting date
	 * @param endDate the ending date
	 * @return a collection of dictionaries containing the queried data
	 * @throws SQLException
	 */
	public Collection<Dictionary<String, Object>> getVisitsInfoBetweenDates(LocalDate startDate, LocalDate endDate) throws SQLException {
		if (startDate == null) {
			throw new IllegalArgumentException("Start date cannot be null.");
		}
		if (endDate == null) {
			throw new IllegalArgumentException("End date cannot be null.");
		}
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("Start date cannot be after end date.");
		}
		
		Collection<Dictionary<String, Object>> result = new LinkedList<Dictionary<String, Object>>(); 
		String query =
				  "SELECT v.appointment_time, p.id, CONCAT(p.f_name, \" \", p.l_name), CONCAT(d.f_name, \" \", d.l_name), CONCAT(n.f_name, \" \", n.l_name), v.final_diagnosis "
				+ "FROM visit_details v JOIN appointment a ON (v.appointment_time = a.appointment_time AND v.doctor_id = a.doctor_id) JOIN patient p ON a.patient_id = p.id JOIN nurse n ON v.recording_nurse_id = n.id JOIN doctor d ON v.doctor_id = d.id "
				+ "WHERE (NOT v.final_diagnosis IS NULL) AND DATE(v.appointment_time) >= ? AND DATE(v.appointment_time) <= ? "
				+ "ORDER BY v.appointment_time DESC, p.l_name DESC";
		
		try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)
		) {
			stmt.setDate(1, Date.valueOf(startDate));
			stmt.setDate(2, Date.valueOf(endDate));
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Dictionary<String, Object> row = new Hashtable<String, Object>();
				row.put("visitDate", rs.getDate(1).toLocalDate());
				row.put("patientID", rs.getInt(2));
				row.put("patientName", rs.getString(3));
				row.put("doctorName", rs.getString(4));
				row.put("nurseName", rs.getString(5));
				row.put("diagnosis", rs.getString(6));
				result.add(row);
			}
        }
		return result;
	}
}
