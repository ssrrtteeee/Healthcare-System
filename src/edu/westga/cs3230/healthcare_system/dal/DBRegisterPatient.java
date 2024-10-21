package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_system.model.Patient;

/**
 * Class used to login to system database access.
 * Interfaces between this client and the remote DB.
 * 
 * @author Stefan
 * @version Fall 2024
 */
public class DBRegisterPatient {
	public static final int		DB_PORT				= 3306;
	public static final String	DB_SERVER_HOST_NAME	= "cs-dblab01.uwg.westga.edu";
	public static final String	DB_NAME				= "cs3230f24h";
	public static final String	DB_USERNAME			= "cs3230f24h";
	public static final String	DB_PASSWORD			= "FTSabWXs4bz0IXsLn6nB";
	
	/**
	 * Gets the connection string.
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the connection string
	 */
	public String getConnectionString() {
		System.out.println(String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", DB_SERVER_HOST_NAME, DB_PORT, DB_NAME, DB_USERNAME, DB_PASSWORD));
		return String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", DB_SERVER_HOST_NAME, DB_PORT, DB_NAME, DB_USERNAME, DB_PASSWORD);
	}
	
	public void registerPatient(Patient patient) throws IllegalArgumentException {
		boolean registeredPatient = false;
		String query = "INSERT INTO patient (f_name, l_name, gender, dob, city, state, address, zip_code,  phone_number, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(this.getConnectionString());
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
        if (registeredPatient == false) {
        	throw new IllegalArgumentException("Couldn't register patient.");
        }
	}
}
