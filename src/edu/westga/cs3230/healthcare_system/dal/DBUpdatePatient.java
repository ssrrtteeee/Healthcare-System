package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_system.model.Patient;

/**
 * Class used to update patients.
 * Interfaces between this client and the remote DB.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class DBUpdatePatient {
	
	/**
	 * Updates the patient with the specified ID to match the specified patient.
	 * 
	 * @precondition patient != null
	 * @postcondition true
	 * @param id the id
	 * @param patient the patient
	 */
	public static void updatePatient(int id, Patient patient) {
		if (patient == null) {
			throw new IllegalArgumentException("Patient cannot be null.");
		}
		String query =
			  "UPDATE patient "
			+ "SET f_name = ?, l_name = ?, gender = ?, dob = ?,"
			+ " city = ?, state = ?, address = ?, zip_code = ?,"
			+ " phone_number = ?, is_active = ? "
			+ "WHERE id = ?"
			+ ";";
        try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)) {
		    
			stmt.setString(1, patient.getFirstName());
			stmt.setString(2, patient.getLastName());
			stmt.setString(3, patient.getGender().substring(0, 1));
			stmt.setDate(4, Date.valueOf(patient.getDateOfBirth()));
			stmt.setString(5, patient.getCity());
			stmt.setString(6, patient.getState());
			stmt.setString(7, patient.getAddress());
			stmt.setString(8, patient.getZipcode());
			stmt.setString(9, patient.getPhoneNumber());
			stmt.setBoolean(10, patient.isActive());
			stmt.setInt(11, id);
	
			stmt.executeUpdate();
        } catch (SQLException ex) {
			System.out.println("SQLException: "	+ ex.getMessage());
			System.out.println("SQLState: "		+ ex.getSQLState());
			System.out.println("VendorError: "	+ ex.getErrorCode());
		} catch (Exception e) {
            System.out.println(e.toString());
        }
	}
	
	/**
	 * Gets the patient with the specified ID from the server.
	 * 
	 * @precondition true
	 * @postcondition true
	 * @param id the patient id
	 * @return the patient
	 */
	public static Patient getPatient(int id) {
		String query =
			  "SELECT id, f_name, l_name, gender, dob, city, state, address, zip_code, phone_number, is_active FROM patient WHERE id = ?;";
		Patient result = null;
		
		try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)
		) {
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();
			rs.next();

			result = new Patient(rs.getString("f_name"), rs.getString("l_name"), rs.getString("city"), rs.getString("address"), rs.getString("zip_code"), rs.getString("phone_number"), rs.getString("gender"), rs.getString("state"), rs.getDate("dob").toLocalDate(), rs.getBoolean("is_active"));
        } catch (SQLException ex) {
			System.out.println("SQLException: "	+ ex.getMessage());
			System.out.println("SQLState: "		+ ex.getSQLState());
			System.out.println("VendorError: "	+ ex.getErrorCode());
		} catch (Exception e) {
            System.out.println(e.toString());
        }
		
		return result;
	}
}
