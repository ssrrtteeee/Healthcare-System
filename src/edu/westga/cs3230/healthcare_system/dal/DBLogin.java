package edu.westga.cs3230.healthcare_system.dal;

import org.mindrot.jbcrypt.BCrypt;

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
	 * Hashes a given password
	 * @param password the password to be hashed
	 * @return hashed password
	 */
	public String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	/**
	 * Returns the hashed password for the given username.
	 * @param username the users username
	 * @return the users hashed password
	 */
	public String getPassword(String username) {
		if (username == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_USERNAME);
		}
		if (username.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_USERNAME);
		}
		
		String query = "select password from nurse where username=?";
		String password = null;
        try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)) {
		    
			stmt.setString(1, username);
	
			ResultSet rs = stmt.executeQuery();
			rs.next();
			password = rs.getString(1);
        } catch (SQLException ex) {
			System.out.println("SQLException: "	+ ex.getMessage());
			System.out.println("SQLState: "		+ ex.getSQLState());
			System.out.println("VendorError: "	+ ex.getErrorCode());
        }

        if (password == null) {
        	throw new IllegalArgumentException("Couldn't check password.");
        }
        return password;
	}
	
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
		
		String dbPassword = this.getPassword(username);
		return BCrypt.checkpw(password, dbPassword);
	}
	
	/**
	 * Returns a nurse with the given username
	 * 
	 * @precondition username != null && !username.isBlank()
	 * @postcondition none
	 * @param username the nurse's username
	 * @return the nurse corresponding to the given username
	 */
	public Nurse getUserDetails(String username) {
		if (username == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_USERNAME);
		}
		if (username.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_USERNAME);
		}
		
		Nurse nurse = null;
		String query = "select f_name, l_name, id from nurse where username=?";
        try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)) {
		    
			stmt.setString(1, username);
	
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
