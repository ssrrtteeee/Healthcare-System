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
	
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }


    public int getId() {
        return id;
    }
}