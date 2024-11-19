package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.westga.cs3230.healthcare_system.model.Nurse;

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
				"SELECT f_name, l_name "
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
			
			return new Nurse(fName, lName, null, nurseId);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
	    }
	}
}
