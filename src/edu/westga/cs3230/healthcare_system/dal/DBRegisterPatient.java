package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_system.model.Patient;

/**
 * Class used to register a new patient.
 * Interfaces between this client and the remote DB.
 * 
 * @author Stefan
 * @version Fall 2024
 */
public class DBRegisterPatient {
	
	/**
	 * Adds a new patient to the database with the given information.
	 * @param patient the patient to be added
	 * @throws IllegalArgumentException
	 */
	public void registerPatient(Patient patient) throws IllegalArgumentException {
		String query = "INSERT INTO patient (f_name, l_name, gender, dob, city, state, address, zip_code,  phone_number, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
			stmt.setBoolean(10, true);
	
			stmt.executeUpdate();
        } catch (SQLException ex) {
			System.out.println("SQLException: "	+ ex.getMessage());
			System.out.println("SQLState: "		+ ex.getSQLState());
			System.out.println("VendorError: "	+ ex.getErrorCode());
		} catch (Exception e) {
            System.out.println(e.toString());
        }
	}
}
