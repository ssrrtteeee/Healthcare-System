package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	 * Allows execution of any query.
	 * 
	 * @precondition query != null
	 * @postcondition true
	 * @param query the query to execute
	 * @return the result set of the query
	 * @throws SQLException 
	 */
	public ResultSet executeQuery(String query) throws SQLException {
		try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)) {
			return stmt.executeQuery();
        }
	}
}
