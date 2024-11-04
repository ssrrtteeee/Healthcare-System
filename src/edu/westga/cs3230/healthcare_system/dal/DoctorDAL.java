package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;

import edu.westga.cs3230.healthcare_system.model.Doctor;
//import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;
import edu.westga.cs3230.healthcare_system.model.Patient;

/**
 * The data access object for the Doctor class.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class DoctorDAL {
	
	/**
	 * Gets a collection of specialties in the system, sorted in ascending order.
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the collection of specialties, or null if an error occurs.
	 */
	public Collection<String> retrieveSpecialties() {
		String query =
				"SELECT UNIQUE specialty_name "
			  + "FROM doctor_specialty "
			  + "ORDER BY specialty_name";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {
			Collection<String> result = new LinkedList<String>();
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String specialty = rs.getString(1);
				
				result.add(specialty);
			}
			
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
	    }
	}
	
	/**
	 * Retrieves the list of doctors with the specified specialty
	 * 
	 * @precondition specialty != null
	 * @postcondition true
	 * @param specialty the specialty
	 * @return the list of doctors, or null if an error occurs.
	 */
	public Collection<Doctor> retrieveDoctorsBySpecialty(String specialty) {
		if (specialty == null) {
			throw new IllegalArgumentException("Specialty cannot be null.");
		}
		
		String query =
				"SELECT id, f_name, l_name"
			  + "FROM doctor "
			  + "WHERE id IN ("
			  + "  SELECT doctor_id "
			  + "  FROM doctor_specialty"
			  + "  WHERE specialty_name = ?"
			  + ");";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {
			Collection<Doctor> result = new LinkedList<Doctor>();
			
			stmt.setString(1, specialty);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt(1);
				String fName = rs.getString(2);
				String lName = rs.getString(3);
				
				result.add(new Doctor(id, fName, lName));
			}
			
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
	    }
	}
	
	/**
	 * Retrieves the doctor with the specified first name and last name
	 * 
	 * @precondition firstName != null && lastName != null
	 * @postcondition true
	 * @param firstName the first name of the desired patient
	 * @param lastName the patient's last name
	 * @return the doctor, or null if none could be found.
	 */
	public Doctor retrieveDoctor(String firstName, String lastName) {
		if (firstName == null) {
			throw new IllegalArgumentException(PatientDAL.NULL_FIRST_NAME);
		}
		if (lastName == null) {
			throw new IllegalArgumentException(PatientDAL.NULL_LAST_NAME);
		}
		
		String query =
				"SELECT f_name, l_name, id "
			  + "FROM doctor "
			  + "WHERE f_name = ? AND l_name = ?";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			ResultSet rs = stmt.executeQuery();

			rs.next();
			String fName = rs.getString(1);
			String lName = rs.getString(2);
			int id = rs.getInt(3);
			
			return new Doctor(id, fName, lName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
	    }
	}
	
	/**
	 * Retrieves the doctor with the specified id
	 * 
	 * @precondition firstName != null && lastName != null
	 * @postcondition true
	 * @param did the id
	 * @return the doctor, or null if none could be found.
	 */
	public Doctor retrieveDoctor(int did) {
		String query =
				"SELECT f_name, l_name, id "
			  + "FROM doctor "
			  + "WHERE id = ?";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {
			stmt.setInt(1, did);
			ResultSet rs = stmt.executeQuery();

			rs.next();
			String fName = rs.getString(1);
			String lName = rs.getString(2);
			int id = rs.getInt(3);
			
			return new Doctor(id, fName, lName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
	    }
	}
}
