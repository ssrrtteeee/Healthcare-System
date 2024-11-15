package edu.westga.cs3230.healthcare_system.model;

import java.time.LocalDate;

import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;

/**
 * The patient class.
 * @author Stefan
 * @version Fall 2024
 */
public class Patient {
	private int id;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String zipcode;
    private String phoneNumber;
    private String gender;
    private String state;
    private LocalDate dateOfBirth;
    private boolean active;

    /**
     * Constructs a patient with the specified information, including unique ID.
     * 
     * @precondition firstName != null && lastName != null && city != null && address != null &&
     * 					zipcode != null && phoneNumber != null && gender != null && state != null &&
     * 					dateOfBirth != null &&
     * 					!firstName.isBlank() && !lastName.isBlank() && !city.isBlank() && !address.isBlank() &&
     * 					!zipcode.isBlank() && !phoneNumber.isBlank() && !gender.isBlank() && !state.isBlank()
     * @postcondition true
     * @param firstName
     * @param lastName
     * @param city
     * @param address
     * @param zipcode
     * @param phoneNumber
     * @param gender
     * @param state
     * @param dateOfBirth
     * @param active
     * @param id
     */
    public Patient(String firstName, String lastName, String city, String address,
            String zipcode, String phoneNumber, String gender,
            String state, LocalDate dateOfBirth, boolean active, int id
    ) {
    	if (firstName == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_FNAME);
    	}
    	if (lastName == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_LNAME);
    	}
    	if (city == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_CITY);
    	}
    	if (address == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_ADDRESS);
    	}
    	if (zipcode == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_ZIPCODE);
    	}
    	if (phoneNumber == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_PHONE_NUMBER);
    	}
    	if (gender == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_GENDER);
    	}
    	if (state == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_STATE);
    	}
    	if (dateOfBirth == null) {
    		throw new IllegalArgumentException(ErrMsgs.NULL_DOB);
    	}
    	if (firstName.isBlank()) {
    		throw new IllegalArgumentException(ErrMsgs.BLANK_FNAME);
    	}
    	if (lastName.isBlank()) {
    		throw new IllegalArgumentException(ErrMsgs.BLANK_LNAME);
    	}
    	if (city.isBlank()) {
    		throw new IllegalArgumentException(ErrMsgs.BLANK_CITY);
    	}
    	if (address.isBlank()) {
    		throw new IllegalArgumentException(ErrMsgs.BLANK_ADDRESS);
    	}
    	if (zipcode.isBlank()) {
    		throw new IllegalArgumentException(ErrMsgs.BLANK_ZIPCODE);
    	}
    	if (phoneNumber.isBlank()) {
    		throw new IllegalArgumentException(ErrMsgs.BLANK_PHONE_NUMBER);
    	}
    	if (gender.isBlank()) {
    		throw new IllegalArgumentException(ErrMsgs.BLANK_GENDER);
    	}
    	if (state.isBlank()) {
    		throw new IllegalArgumentException(ErrMsgs.BLANK_STATE);
    	}
    	
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.state = state;
        this.dateOfBirth = dateOfBirth;
        this.active = active;
    	this.id = id;
    }

    /**
     * Constructs a Patient with the specified values and an ID of -1.
     * 
     * @precondition firstName != null && lastName != null && city != null && address != null &&
     * 					zipcode != null && phoneNumber != null && gender != null && state != null &&
     * 					dateOfBirth != null &&
     * 					!firstName.isBlank() && !lastName.isBlank() && !city.isBlank() && !address.isBlank() &&
     * 					!zipcode.isBlank() && !phoneNumber.isBlank() && !gender.isBlank() && !state.isBlank()
     * @postcondition getFirstName() == firstName && getLastName() == lastName && getCity() = city &&
     * 					getAddress() == address && getZipcode() == zipcode && phoneNumber() == phoneNumber &&
     * 					getGender() == gender && getState() == state && getDateOfBirth() == dateOfBirth &&
     * 					isActive() == active && getId() == id
     * @param firstName
     * @param lastName
     * @param city
     * @param address
     * @param zipcode
     * @param phoneNumber
     * @param gender
     * @param state
     * @param dateOfBirth
     * @param active
     */
    public Patient(String firstName, String lastName, String city, String address,
                   String zipcode, String phoneNumber, String gender,
                   String state, LocalDate dateOfBirth, boolean active
    ) {
    	this(firstName, lastName, city, address, zipcode, phoneNumber, gender, state, dateOfBirth, active, -1);
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
     * Gets the city
     * 
     * @precondition true
	 * @postcondition true
     * @return the city
     */
    public String getCity() {
    	return this.city;
    }
    
    /**
     * Gets the address
     * 
     * @precondition true
	 * @postcondition true
     * @return the address
     */
    public String getAddress() {
    	return this.address;
    }
    
    /**
     * Gets the zipcode
     * 
     * @precondition true
	 * @postcondition true
     * @return the zipcode
     */
    public String getZipcode() {
    	return this.zipcode;
    }
    
    /**
     * Gets the phone number
     * 
     * @precondition true
	 * @postcondition true
     * @return the phone number
     */
    public String getPhoneNumber() {
    	return this.phoneNumber;
    }
    
    /**
     * Gets the gender
     * 
     * @precondition true
	 * @postcondition true
     * @return the gender
     */
    public String getGender() {
    	return this.gender;
    }
    
    /**
     * Gets the state
     * 
     * @precondition true
	 * @postcondition true
     * @return the state
     */
    public String getState() {
    	return this.state;
    }
    
    /**
     * Gets the date of birth
     * 
     * @precondition true
	 * @postcondition true
     * @return the date of birth
     */
    public LocalDate getDateOfBirth() {
    	return this.dateOfBirth;
    }
    
    /**
     * Returns whether or not this patient is active
     * 
     * @precondition true
	 * @postcondition true
     * @return whether the patient is active or not
     */
    public boolean isActive() {
    	return this.active;
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