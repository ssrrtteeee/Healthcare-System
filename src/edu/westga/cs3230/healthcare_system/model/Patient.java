package edu.westga.cs3230.healthcare_system.model;

import java.time.LocalDate;

/**
 * The patient class.
 * @author Stefan
 * @version Fall 2024
 */
public class Patient {
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String zipcode;
    private String phoneNumber;
    private String gender;
    private String state;
    private LocalDate dateOfBirth;

    public Patient(String firstName, String lastName, String city, String address,
                   String zipcode, String phoneNumber, String gender,
                   String state, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.state = state;
        this.dateOfBirth = dateOfBirth;
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
}