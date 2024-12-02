package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_system.model.User;
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;

/**
 * Class used to login to system database access.
 * Interfaces between this client and the remote DB.
 * 
 * @author Stefan
 * @version Fall 2024
 */
public class DBLogin {
	private NurseDAL nurseDB;
	private AdminDAL adminDB;
	
	/**
	 * Instantiates a new DBLogin.
	 * 
	 * @precondition true
	 * @postcondition true
	 */
	public DBLogin() {
		this.nurseDB = new NurseDAL();
		this.adminDB = new AdminDAL();
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
				
        return this.checkUserInTable(username, password, "nurse") || this.checkUserInTable(username, password, "admin");
	}

	private boolean checkUserInTable(String username, String password, String tableName) {
		String query = String.format("select count(*) from %s where username=? and password=?", tableName);
		try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)) {
		    
			stmt.setString(1, username);
			stmt.setString(2, password);
	
			ResultSet rs = stmt.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				int number = rs.getInt(1);
				return number >= 1;
            }
        } catch (SQLException ex) {
			System.out.println("SQLException: "	+ ex.getMessage());
			System.out.println("SQLState: "		+ ex.getSQLState());
			System.out.println("VendorError: "	+ ex.getErrorCode());
		}

        return false;
	}
	
	/**
	 * Returns a user with the given login credential
	 * 
	 * @precondition username != null && password != null && !username.isBlank() && !password.isBlank()
	 * @postcondition true
	 * @param username the user's username
	 * @param password the user's password
	 * @return the user corresponding to the given login credentials
	 */
	public User getUserDetails(String username, String password) {
		User result = this.nurseDB.getNurse(username, password);
		if (result == null) {
			result = this.adminDB.getAdmin(username, password);
		}
		return result;
	}
}
