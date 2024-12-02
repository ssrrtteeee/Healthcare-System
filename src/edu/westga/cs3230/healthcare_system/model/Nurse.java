package edu.westga.cs3230.healthcare_system.model;

/** A Nurse.
 * 
 * @author Stefan
 * @version Fall 2024
 */
public class Nurse extends User {
	/**
	 * Creates a new nurse user.
	 * 
	 * @precondition firstName != null && lastName != null && username != null && !firstName.isBlank() && !lastName.isBlank() && !userame.isBlank()
	 * @postcondition getFirstName().equals(firstName) && getLastName().equals(lastName) && getUsername().equals(username)
	 * @param firstName first name
	 * @param lastName last name
	 * @param username the user username
	 * @param id id
	 */
	public Nurse(String firstName, String lastName, String username, int id) {
		super(firstName, lastName, username, id);
	}
}