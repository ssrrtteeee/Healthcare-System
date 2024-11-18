package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import edu.westga.cs3230.healthcare_system.model.Patient;
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;

/**
 * Represents the Patient class database access layer
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class PatientDAL {
	
	/**
	 * Adds a new patient to the database with the given information.
	 * 
	 * @author Stefan
	 * @precondition patient != null
	 * @postcondition true
	 * @param patient the patient to be added
	 * @throws IllegalArgumentException
	 */
	public void registerPatient(Patient patient) throws IllegalArgumentException {
		String query =
			  "INSERT INTO patient (f_name, l_name, gender, dob, city, state, address, zip_code,  phone_number, is_active)"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
		} catch (Exception ex) {
            System.out.println(ex.toString());
        }
	}
	
	/**
	 * Retrieves the patient with the specified first name, last name, and date of birth
	 * 
	 * @precondition firstName != null && lastName != null && dateOfBirth != null &&
	 * 				 !firstName.isBlank() && !lastName.isBlank() && !dateOfBirth.isAfter(LocalDate.now())
	 * @postcondition true
	 * @param firstName the first name of the desired patient
	 * @param lastName the patient's last name
	 * @param dateOfBirth the patient's date of birth
	 * @return the patient, or null if none could be found.
	 */
	public Patient retrievePatient(String firstName, String lastName, LocalDate dateOfBirth) {
		if (firstName == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_FNAME);
		}
		if (lastName == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_LNAME);
		}
		if (dateOfBirth == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_DOB);
		}
		if (firstName.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_FNAME);
		}
		if (lastName.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_LNAME);
		}
		if (dateOfBirth.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException(ErrMsgs.FUTURE_DOB);
		}

		String query =
				"SELECT f_name, l_name, city, address, zip_code, phone_number, gender, state, dob, is_active, id "
			  + "FROM patient "
			  + "WHERE f_name = ? AND l_name = ? AND dob = ?";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setDate(3, Date.valueOf(dateOfBirth));

			ResultSet rs = stmt.executeQuery();

			rs.next();
			String fName = rs.getString(1);
			String lName = rs.getString(2);
			String city = rs.getString(3);
			String address = rs.getString(4);
			String zipCode = rs.getString(5);
			String phoneNumber = rs.getString(6);
			String gender = rs.getString(7);
			String state = rs.getString(8);
			LocalDate dob = rs.getDate(9).toLocalDate();
			boolean isActive = rs.getBoolean(10);
			int id = rs.getInt(11);
			
			return new Patient(fName, lName, city, address, zipCode, phoneNumber, gender, state, dob, isActive, id);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
	    }
	}

	/**
	 * Updates the patient with the specified ID to match the specified patient.
	 * 
	 * @precondition patient != null
	 * @postcondition true
	 * @param id the id
	 * @param patient the patient
	 * @return false if some error occurs, or true otherwise.
	 */
	public boolean updatePatient(int id, Patient patient) {
		if (patient == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_PATIENT);
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
			
			return true;
		} catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
	}

	/**
	 * Gets a collection of all patients having the specified first and last name.
	 * 
	 * @precondition firstName != null && lastName != null && !firstName.isBlank() && !lastName.isBlank()
	 * @postcondition true
	 * @param firstName the patients' first name
	 * @param lastName the patients' last name
	 * @return the collection of patients, or null if an error occurs.
	 */
	public Collection<Patient> retrievePatients(String firstName, String lastName) {
		if (firstName == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_FNAME);
		}
		if (lastName == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_LNAME);
		}
		if (firstName.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_FNAME);
		}
		if (lastName.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_LNAME);
		}
		
		String query =
				"SELECT f_name, l_name, city, address, zip_code, phone_number, gender, state, dob, is_active, id "
			  + "FROM patient "
			  + "WHERE f_name = ? AND l_name = ?";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			ResultSet rs = stmt.executeQuery();

			Collection<Patient> result = new HashSet<Patient>(); 
			
			while (rs.next()) {
				String fName = rs.getString(1);
				String lName = rs.getString(2);
				String city = rs.getString(3);
				String address = rs.getString(4);
				String zipCode = rs.getString(5);
				String phoneNumber = rs.getString(6);
				String gender = rs.getString(7);
				String state = rs.getString(8);
				LocalDate dob = rs.getDate(9).toLocalDate();
				boolean isActive = rs.getBoolean(10);
				int id = rs.getInt(11);
				
				result.add(new Patient(fName, lName, city, address, zipCode, phoneNumber, gender, state, dob, isActive, id));
			}

			return result;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
	    }
	}
	
	/**
	 * Gets the collection of patients whose date of birth is the specified date.
	 * 
	 * @precondition dateOfBirth != null
	 * @postcondition true
	 * @param dateOfBirth the date
	 * @return the collection of patients, or null if an error occurs.
	 */
	public Collection<Patient> retrievePatients(LocalDate dateOfBirth) {
		if (dateOfBirth == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_DOB);
		}
		
		String query =
				"SELECT f_name, l_name, city, address, zip_code, phone_number, gender, state, dob, is_active, id "
			  + "FROM patient "
			  + "WHERE dob = ?";
		try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString()); 
				PreparedStatement stmt = con.prepareStatement(query);
		) {
			stmt.setDate(1, Date.valueOf(dateOfBirth));
			ResultSet rs = stmt.executeQuery();

			Collection<Patient> result = new HashSet<Patient>(); 
			
			while (rs.next()) {
				String fName = rs.getString(1);
				String lName = rs.getString(2);
				String city = rs.getString(3);
				String address = rs.getString(4);
				String zipCode = rs.getString(5);
				String phoneNumber = rs.getString(6);
				String gender = rs.getString(7);
				String state = rs.getString(8);
				LocalDate dob = rs.getDate(9).toLocalDate();
				boolean isActive = rs.getBoolean(10);
				int id = rs.getInt(11);
				
				result.add(new Patient(fName, lName, city, address, zipCode, phoneNumber, gender, state, dob, isActive, id));
			}

			return result;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
	    }
	}
}
