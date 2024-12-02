package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_system.model.Nurse;
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;

/**
 * Class used to get nurse information from database.
 * @author Stefan
 * @version Fall 2024
 *
 */
public class NurseDAL {
	
	/**
	 * Gets the nurse with the given id.
	 * @param nurseId the id of the nurse to retrieve
	 * @return the nurse with the given id
	 */
	public Nurse getNurse(int nurseId) {
		String query =
				"SELECT f_name, l_name, username "
			  + "FROM nurse "
			  + "WHERE id = ?";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {
			stmt.setInt(1, nurseId);

			ResultSet rs = stmt.executeQuery();

			rs.next();
			String fName = rs.getString(1);
			String lName = rs.getString(2);
			String username = rs.getString(3);
			
			return new Nurse(fName, lName, username, nurseId);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
	    }
	}

	/**
	 * Gets the nurse with the specified username and password.
	 * 
	 * @precondition username != null && password != null && !username.isBlank() && !password.isBlank()
	 * @postcondition true
	 * @param username
	 * @param password
	 * @return the nurse with the specified info, or null if none could be found.
	 */
	public Nurse getNurse(String username, String password) {
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
		
		Nurse result = null;
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
			result = new Nurse(fname, lname, username, id);
        } catch (SQLException ex) {
			System.out.println("SQLException: "	+ ex.getMessage());
			System.out.println("SQLState: "		+ ex.getSQLState());
			System.out.println("VendorError: "	+ ex.getErrorCode());
        }
		return result;
	}
}
