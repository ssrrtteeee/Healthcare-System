package edu.westga.cs3230.healthcare_system.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3230.healthcare_system.model.TestResults;

/**
 * The data access object for test results.
 * @author Stefan
 * @version Fall 2024
 *
 */
public class TestResultDAL {
	
	/**
	 * Gets the test results for the given time and doctor.
	 * @param appointmentTime the time of the appointment
	 * @param doctorId the doctors id
	 * @return list of all the test results for that appointment
	 */
	public List<TestResults> getTestResultsFor(LocalDateTime appointmentTime, int doctorId) {
		List<TestResults> tests = new ArrayList<TestResults>();
        String query = "SELECT name, test_datetime, test_result, test_abnormality "
        		+ "FROM test, test_for_visit "
        		+ "WHERE test.test_code = test_for_visit.test_code AND doctor_id = ?  AND appointment_time = ? AND test_datetime IS NOT NULL";

        try (Connection con = DriverManager.getConnection(DBAccessor.getConnectionString());
        		PreparedStatement stmt = con.prepareStatement(query);
        		) {
        	
	    	stmt.setInt(1, doctorId);
        	stmt.setTimestamp(2, Timestamp.valueOf(appointmentTime));
        	
        	ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                LocalDateTime testTime = rs.getTimestamp("test_datetime") == null ? null : rs.getTimestamp("test_datetime").toLocalDateTime();
                String result = rs.getString("test_result");
                boolean testAbnormality = rs.getBoolean("test_abnormality");

                TestResults test = new TestResults(name, testTime, result, testAbnormality);
                tests.add(test);
            }
            
	        return tests;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tests;
	}
}
