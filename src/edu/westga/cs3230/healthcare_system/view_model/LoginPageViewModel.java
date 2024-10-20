package edu.westga.cs3230.healthcare_system.view_model;

import java.io.IOException;

import edu.westga.cs3230.healthcare_system.model.Nurse;
import edu.westga.cs3230.healthcare_system.model.UserLogin;

/** View model class for login page.
 * 
 * @author Stefan
 * @version fall 2022
 *
 */
public class LoginPageViewModel {
	
	private UserLogin userLogin;
	
	/** Creates a new login view model.
	 * 
	 * @precondition none
	 * @postcondition this.userDatabase = new UserDatabase
	 * 
	 * @throws IOException
	 */
	public LoginPageViewModel() throws IOException {
		this.userLogin = new UserLogin();
	}
	
	/** Logs the user into the system
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param username the username for the user
	 * @param password the password for the user
	 * @throws IllegalArgumentException
	 */
	public boolean login(String username, String password) throws IllegalArgumentException {
		if (!this.userLogin.login(username, password)) {
			throw new IllegalArgumentException("The username and password entered did not match our records, try again.");
		} else {
			return true;
		}
	}
	
	public Nurse getUserDetails(String username, String password) {
		return this.userLogin.getUserInformation(username, password);
	}
	
	/** Gets the userDatabase.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the userDatabase
	 */
	public UserLogin getUserDatabase() {
		return this.userLogin;
	}
}
