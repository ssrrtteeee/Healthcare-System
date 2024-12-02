package edu.westga.cs3230.healthcare_system.model;

/**
 * Represents an admin.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class Admin extends User {
	
	/**
	 * Instantiates a new admin with the specified information
	 * 
	 * @precondition firstName != null && lastName != null && username != null && !firstName.isBlank() && !lastName.isBlank() && !userame.isBlank()
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param username the username
	 * @param id the id
	 */
	public Admin(String firstName, String lastName, String username, int id) {
		super(firstName, lastName, username, id);
	}
}
