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
import edu.westga.cs3230.healthcare_system.resources.ErrMsgs;

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
	
	/**
	 * Updates the test with the specified test code for the visit associated with the specified appointment time and doctor id.
	 * 
	 * @precondition appointmentTime != null && testDatetime != null && result != null
	 * @postcondition true
	 * @param appointmentTime the time of the visit's appointment
	 * @param doctorId the id of the doctor that the visit was for
	 * @param testCode the code for this specific test
	 * @param testDatetime the date and time that the test was completed
	 * @param testResult the result of the test
	 * @param testAbnormality whether the test was abnormal
	 * @return true if successful, false otherwise.
	 */
	public boolean updateTestResults(LocalDateTime appointmentTime, int doctorId, int testCode, LocalDateTime testDatetime, String testResult, boolean testAbnormality) {
		if (appointmentTime == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_APMT_TIME);
		}
		if (testDatetime == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_TEST_TIME);
		}
		if (testResult == null) {
			throw new IllegalArgumentException(ErrMsgs.NULL_TEST_RESULT);
		}
		
        try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				CallableStatement stmt = connection.prepareCall("CALL update_test_result(?, ?, ?, ?, ?, ?)")
		) {
			stmt.setTimestamp(1, Timestamp.valueOf(appointmentTime));
			stmt.setInt(2, doctorId);
			stmt.setInt(3, testCode);
			stmt.setTimestamp(4, Timestamp.valueOf(testDatetime));
			stmt.setString(5, testResult);
			stmt.setBoolean(6, testAbnormality);
	
			stmt.execute();
			return true;
		} catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
	}
}
