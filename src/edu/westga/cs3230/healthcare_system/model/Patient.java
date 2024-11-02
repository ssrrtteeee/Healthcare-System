package edu.westga.cs3230.healthcare_system.model;

import java.time.LocalDate;

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
     * 
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

    public Patient(String firstName, String lastName, String city, String address,
                   String zipcode, String phoneNumber, String gender,
                   String state, LocalDate dateOfBirth, boolean active
    ) {
    	this(firstName, lastName, city, address, zipcode, phoneNumber, gender, state, dateOfBirth, active, -1);
    }

    public String getFirstName() { return this.firstName; }
    
    public String getLastName() { return this.lastName; }
    
    public String getCity() { return this.city; }
    
    public String getAddress() { return this.address; }
    
    public String getZipcode() { return this.zipcode; }
    
    public String getPhoneNumber() { return this.phoneNumber; }
    
    public String getGender() { return this.gender; }
    
    public String getState() { return this.state; }
    
    public LocalDate getDateOfBirth() { return this.dateOfBirth; }
    
    public boolean isActive() { return this.active; }
    
    public int getId() { return this.id; }
}