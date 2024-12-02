package edu.westga.cs3230.healthcare_system.model;

import java.time.LocalDateTime;

/**
 * The test results class.
 * @author Stefan
 * @version Fall 2024
 *
 */
public class TestResults {
	
	private String testName;
	private LocalDateTime testDateTime;
	private String testResults;
	private boolean testAbnormality;
	
	/**
	 * Creates a new test results object.
	 * @param testName name of the test
	 * @param testDateTime the date and time when the test was performed
	 * @param testResults the results of the test
	 * @param testAbnormality tells if the results were abnormal
	 */
	public TestResults(String testName, LocalDateTime testDateTime, String testResults, boolean testAbnormality) {
        this.testName = testName;
        this.testDateTime = testDateTime;
        this.testResults = testResults;
        this.testAbnormality = testAbnormality;
    }

    public String getTestName() {
        return this.testName;
    }

    public LocalDateTime getTestDateTime() {
        return this.testDateTime;
    }

    public String getTestResults() {
        return this.testResults;
    }

    /**
     * Gets the test abnormality.
     * @return returns true if abnormal, false otherwise.
     */
    public boolean isTestAbnormal() {
        return this.testAbnormality;
    }
    
    @Override
    public String toString() {
        return "Test Name: " + this.testName + System.lineSeparator() +
                "Test Date and Time: " + this.testDateTime + System.lineSeparator() +
                "Test Results: " + this.testResults + System.lineSeparator() +
                "Test Abnormality: " + (this.testAbnormality ? "Yes" : "No");
    }
}
