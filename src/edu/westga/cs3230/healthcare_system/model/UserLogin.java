package edu.westga.cs3230.healthcare_system.model;

import java.io.IOException;

import edu.westga.cs3230.healthcare_system.dal.DBLogin;

/** Manager of the users.
 * 
 * @author Stefan
 * @version Fall 2024
 */
public class UserLogin {
	
	private static Nurse currentSessionUser;
	private DBLogin dbLoginAcess;
	
	/** 
	 * Creates a new UserDatabase.
	 * 
	 * @precondition none
	 * @postcondition this.users contains all of the file data and the default user
	 * 
	 * @throws IOException
	 */
	public UserLogin() throws IOException {
		this.dbLoginAcess = new DBLogin();
	}
	
	/** 
	 * Logs into the system with the given credentials.
	 * 
	 * @param username the username to login with
	 * @param password the password to login with
	 * @return true if login succeeded, false otherwise
	 */
	public boolean login(String username, String password) throws IllegalArgumentException {
		boolean isValid = this.dbLoginAcess.checkIfLoginIsValid(username, password);
		if (isValid) {
			currentSessionUser = this.dbLoginAcess.getUserDetails(username, password);
		}
		
		return isValid;
	}
	
	public Nurse getUserInformation(String username, String password) throws IllegalArgumentException {
		return this.dbLoginAcess.getUserDetails(username, password);
	}
	
	/**
	 * returns the current session user
	 * 
	 * @return the current session user
	 */
	public static Nurse getSessionUser() {
		return currentSessionUser;
	}
	
    /**
     * Returns the user label for any given user.
     * @return the string to be displayed with the user information
     */
    public static String getUserlabel() {
    	return "Username: " + currentSessionUser.getUsername() + System.lineSeparator()
    		 + "Full Name: " + currentSessionUser.getFirstName() + " " + currentSessionUser.getLastName() + System.lineSeparator()
    		 + "ID: " + currentSessionUser.getId();
    }

	
	
}
