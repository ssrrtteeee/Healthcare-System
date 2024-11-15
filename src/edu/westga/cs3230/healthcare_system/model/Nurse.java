package edu.westga.cs3230.healthcare_system.model;

/** A Nurse.
 * 
 * @author Stefan
 * @version Fall 2024
 */
public class Nurse {
	private String firstName;
	private String lastName;
	private String username;
	private int id;
	
	/**
	 * Creates a new nurse user.
	 * @param firstName first name
	 * @param lastName last name
	 * @param username the user username
	 * @param id id
	 */
	public Nurse(String firstName, String lastName, String username, int id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.id = id;
	}
	
	/**
	 * Gets the first name
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the first name
	 */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Gets the last name
	 * 
	 * @precondition true
	 * @postcondition true
     * @return the last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Gets the username
	 * 
	 * @precondition true
	 * @postcondition true
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the ID
	 * 
	 * @precondition true
	 * @postcondition true
     * @return the ID
     */
    public int getId() {
        return this.id;
    }
}