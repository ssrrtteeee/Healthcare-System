package edu.westga.cs3230.healthcare_system.model;

import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;

/**
 * Represents a doctor.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class Doctor {
	private int id;
	private String fName;
	private String lName;
	
	/**
	 * Creates a new doctor with the specified info.
	 * 
	 * @precondition fName != null && lName != null && !fName.isBlank() && !lName.isBlank()
	 * @postcondition getId() == id && getFirstName() == fName && getLastName() == lName
	 * @param id the doctor's id
	 * @param fName the doctor's first name
	 * @param lName the doctor's last name
	 */
	public Doctor(int id, String fName, String lName) {
		if (fName == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_FNAME);
		}
		if (lName == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_LNAME);
		}
		if (fName.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_FNAME);
		}
		if (lName.isBlank()) {
			throw new IllegalArgumentException(ErrMsgs.BLANK_LNAME);
		}
		this.id = id;
		this.fName = fName;
		this.lName = lName;
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
	
	/**
	 * Gets the first name
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the last name
	 */
    public String getFirstName() {
        return this.fName;
    }

    /**
     * Gets the last name
	 * 
	 * @precondition true
	 * @postcondition true
     * @return the last name
     */
    public String getLastName() {
        return this.lName;
    }
}
