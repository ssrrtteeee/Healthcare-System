package edu.westga.cs3230.healthcare_system.model;

/**
 * Represent a test.
 * 
 * @author Stefan
 * @version Fall 2024
 *
 */
public class Test {
	
    private int testCode;
    private String name;
    private Double lowValue;
    private Double highValue;
    private String unitMeasurementName;

    /**
     * Creates a new test object.
     * @param testCode the test code for the test
     * @param name the name for the test
     * @param lowValue the low value for the test
     * @param highValue the high value for the test
     * @param unitMeasurementName the name of the unit of measurement
     */
    public Test(int testCode, String name, Double lowValue, Double highValue, String unitMeasurementName) {
        this.testCode = testCode;
        this.name = name;
        this.lowValue = lowValue;
        this.highValue = highValue;
        this.unitMeasurementName = unitMeasurementName;
    }

    /**
     * Gets the test code.
     * @return the test code for the test
     */
    public int getTestCode() {
        return this.testCode;
    }

    /**
     * Gets the test name.
     * @return the name of the test
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the tests low value.
     * @return the low value for the test
     */
    public Double getLowValue() {
        return this.lowValue;
    }

    /**
     * Gets the test high value
     * @return the high value for the test
     */
    public Double getHighValue() {
        return this.highValue;
    }

    /**
     * Gets the name of the unit of measurment;
     * @return the name for the unit of measurment
     */
    public String getUnitMeasurementName() {
        return this.unitMeasurementName;
    }

    @Override
    public String toString() {
        return "testCode: " + this.testCode + System.lineSeparator() +
               "name: " + this.name + System.lineSeparator() +
               "lowValue: " + this.lowValue + System.lineSeparator() +
               "highValue: " + this.highValue + System.lineSeparator() +
               "unitMeasurementName" + this.unitMeasurementName;
    }
}
