package edu.westga.cs3230.healthcare_system.model;

import java.time.LocalDate;

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

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public String getZipcode() { return zipcode; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getGender() { return gender; }
    public String getState() { return state; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
}