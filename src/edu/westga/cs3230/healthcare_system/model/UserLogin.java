package edu.westga.cs3230.healthcare_system.model;

import java.io.IOException;

import edu.westga.cs3230.healthcare_system.dal.DBLogin;

/** Manager of the users.
 * 
 * @author Stefan
 * @version Fall 2024
 */
public class UserLogin {
	
	private Nurse currentSessionUser;
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
	 */
	public boolean login(String username, String password) throws IllegalArgumentException {
		return this.dbLoginAcess.checkIfLoginIsValid(username, password);
	}
	
	public Nurse getUserInformation(String username, String password) throws IllegalArgumentException {
		return this.dbLoginAcess.getUserDetails(username, password);
	}
	
	/**
	 * returns the current session user
	 * 
	 * @return the current session user
	 */
	public Nurse getSessionUser() {
		return this.currentSessionUser;
	}
}
