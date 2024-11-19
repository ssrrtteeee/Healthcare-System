package edu.westga.cs3230.healthcare_system.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3230.healthcare_system.model.Test;

/**
 * The data access object for the test class.
 * @author Stefan
 * @version Fall 2024
 *
 */
public class TestDAL {
	
	/**
	 * Gets all of the currently available tests.
	 * @return the available medical tests
	 */
	public static List<Test> getTests() {
		List<Test> tests = new ArrayList<Test>();
        String query = "SELECT test_code, name, low_value, high_value, name_unit_measurement FROM test";

        try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString());
        		PreparedStatement stmt = con.prepareStatement(query);
        		) {
        	
        	ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int testCode = rs.getInt("test_code");
                String name = rs.getString("name");
                Double lowValue = rs.getDouble("low_value");
                if (rs.wasNull()) {
                    lowValue = null;
                }
                Double highValue = rs.getDouble("high_value");
                if (rs.wasNull()) {
                    highValue = null;
                }
                String unitMeasurementName = rs.getString("name_unit_measurement");

                Test test = new Test(testCode, name, lowValue, highValue, unitMeasurementName);
                tests.add(test);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tests;
	}
	
	/**
	 * Gets the tests ordered for a given appointment
	 * @param appointmentTime the time of the appointment
	 * @param doctorId the id of the doctor for the appointment
	 * @return all of the tests ordered for the appointment
	 */
	public List<Test> getTestsFor(LocalDateTime appointmentTime, int doctorId) {
		List<Test> tests = new ArrayList<Test>();
        String query = "SELECT test.test_code, name, low_value, high_value, name_unit_measurement "
        		+ "FROM test, test_for_visit "
        		+ "WHERE test.test_code = test_for_visit.test_code AND doctor_id = ?  AND appointment_time = ?";

        try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString());
        		PreparedStatement stmt = con.prepareStatement(query);
        		) {
        	
	    	stmt.setInt(1, doctorId);
        	stmt.setTimestamp(2, Timestamp.valueOf(appointmentTime));
        	
        	ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int testCode = rs.getInt("test_code");
                String name = rs.getString("name");
                Double lowValue = rs.getDouble("low_value");
                if (rs.wasNull()) {
                    lowValue = null;
                }
                Double highValue = rs.getDouble("high_value");
                if (rs.wasNull()) {
                    highValue = null;
                }
                String unitMeasurementName = rs.getString("name_unit_measurement");

                Test test = new Test(testCode, name, lowValue, highValue, unitMeasurementName);
                tests.add(test);
            }
            
	        return tests;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tests;
	}
	
	/**
	 * Adds the given tests and updates the initial diagnosis
	 * @param appointmentTime the time the appointment is scheduled
	 * @param doctorId the id of the doctor assigned to the appointment
	 * @param initialDiagnosis the initial diagnosis made by the doctor
	 * @param testCodes the test codes for the tests
	 * @return returns true if successful, or false otherwise
	 */
	public boolean addTestsAndUpdateInitialDiagnosis(LocalDateTime appointmentTime, int doctorId, String initialDiagnosis, List<Integer> testCodes) {
        Connection conn = null;
        CallableStatement callableStmt = null;
        PreparedStatement updateStmt = null;
        String sqlCallQuery = "CALL add_test_for_visit(?, ?, ?)";
        String sqlUpdateQuery = "UPDATE visit_details SET initial_diagnosis = ? "
        		+ "WHERE appointment_time = ? "
        		+ "AND doctor_id = ?";

        try {
            conn = DriverManager.getConnection(DBAccessor.getConnectionString());

            conn.setAutoCommit(false);
            
            callableStmt = conn.prepareCall(sqlCallQuery);

            for (Integer testCode: testCodes) {
	            callableStmt.setInt(1, doctorId);
	            callableStmt.setTimestamp(2, Timestamp.valueOf(appointmentTime));
	            callableStmt.setInt(3, testCode);
	            callableStmt.addBatch();
            }
            
            callableStmt.executeBatch(); 
            
            updateStmt = conn.prepareStatement(sqlUpdateQuery);
            
            updateStmt.setString(1, initialDiagnosis);
            updateStmt.setTimestamp(2, Timestamp.valueOf(appointmentTime));
            updateStmt.setInt(3, doctorId);
            
            updateStmt.executeUpdate();
            
            conn.commit();
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println(ex);
                    return false;
                } catch (SQLException rollbackEx) {
                	System.out.println(rollbackEx);
                	return false;
                }
            }
        }
        return true;
    }
}
