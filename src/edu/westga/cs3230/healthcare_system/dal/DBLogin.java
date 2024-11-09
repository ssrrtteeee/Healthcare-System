package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_system.model.Nurse;
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;

/**
 * Class used to login to system database access.
 * Interfaces between this client and the remote DB.
 * 
 * @author Stefan
 * @version Fall 2024
 */
public class DBLogin {
	
	/**
	 * Checks if the login credentials are valid.
	 * 
	 * @precondition username != null && !username.isBlank() && password != null && !password.isBlank()
	 * @postcondition true
	 * @param username the user's username
	 * @param password the user's password
	 * @return true if they are valid, false otherwise
	 */
	public boolean checkIfLoginIsValid(String username, String password) {
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
		
		boolean correctLogin = false;
		String query = "select count(*) from nurse where username=? and password=?";
        try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)) {
		    
			stmt.setString(1, username);
			stmt.setString(2, password);
	
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int number = rs.getInt(1);
				correctLogin = number >= 1;
            }
        } catch (SQLException ex) {
			System.out.println("SQLException: "	+ ex.getMessage());
			System.out.println("SQLState: "		+ ex.getSQLState());
			System.out.println("VendorError: "	+ ex.getErrorCode());
		}

        return correctLogin;
	}
	
	/**
	 * Returns a nurse with the given login credential
	 * 
	 * @precondition 
	 * @postcondition true
	 * @param username the nurse's username
	 * @param password the nurse's password
	 * @return the nurse corresponding to the given login credentials
	 */
	public Nurse getUserDetails(String username, String password) {
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
		
		Nurse nurse = null;
		String query = "select f_name, l_name, id from nurse where username=? and password=?";
        try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)) {
		    
			stmt.setString(1, username);
			stmt.setString(2, password);
	
			ResultSet rs = stmt.executeQuery();
			rs.next();
			String fname = rs.getString(1);
			String lname = rs.getString(2);
			int id = rs.getInt(3);
			nurse = new Nurse(fname, lname, username, id);
        } catch (SQLException ex) {
			System.out.println("SQLException: "	+ ex.getMessage());
			System.out.println("SQLState: "		+ ex.getSQLState());
			System.out.println("VendorError: "	+ ex.getErrorCode());
        }

        if (nurse == null) {
        	throw new IllegalArgumentException("Couldn't find user details.");
        }
        
        return nurse;
	}
}
