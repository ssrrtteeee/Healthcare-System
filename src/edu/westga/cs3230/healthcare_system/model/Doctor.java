package edu.westga.cs3230.healthcare_system.model;

import java.util.Collection;

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
	private Collection<String> specialties;
	
	/**
	 * Creates a new doctor with the specified info.
	 * 
	 * @precondition fName != null && lName != null && !fName.isBlank() && !lName.isBlank()
	 * @postcondition true
	 * @param id the doctor's id
	 * @param fName the doctor's first name
	 * @param lName the doctor's last name
	 */
	public Doctor(int id, String fName, String lName) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
	}
	
	public int getId() {
		return this.id;
	}
	
    public String getFirstName() {
        return fName;
    }

    public String getLastName() {
        return lName;
    }
	
	public void addSpecialty(String specialty) {
		this.specialties.add(specialty);
	}
	
	public Collection<String> getSpecialties() {
		return this.specialties;
	}
}
