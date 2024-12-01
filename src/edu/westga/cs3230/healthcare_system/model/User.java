package edu.westga.cs3230.healthcare_system.model;

import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;

/**
 * Represents a user.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public abstract class User {
	private String firstName;
	private String lastName;
	private String username;
	private int id;
	
	/**
	 * Creates a new user.
	 * 
	 * @precondition firstName != null && lastName != null && username != null && !firstName.isBlank() && !lastName.isBlank() && !userame.isBlank()
	 * @postcondition getFirstName().equals(firstName) && getLastName().equals(lastName) && getUsername().equals(username)
	 * @param firstName first name
	 * @param lastName last name
	 * @param username the username
	 * @param id the id
	 */
	public User(String firstName, String lastName, String username, int id) {
		if (firstName == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_FNAME);
		}
		if (lastName == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_LNAME);
		}
		if (username == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_USERNAME);
		}
		
		if (firstName.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_FNAME);
		}
		if (lastName.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_LNAME);
		}
		if (username.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_USERNAME);
		}
		
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
