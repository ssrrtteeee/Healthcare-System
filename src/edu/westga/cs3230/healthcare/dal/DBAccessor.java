package edu.westga.cs3230.healthcare.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class used for database access.
 * Interfaces between this client and the remote DB.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class DBAccessor {
	public static final int		DB_PORT				= 3306;
	public static final String	DB_SERVER_HOST_NAME	= "cs-dblab01.uwg.westga.edu";
	public static final String	DB_NAME				= "cs3230f24h";
	public static final String	DB_USERNAME			= "cs3230f24h";
	public static final String	DB_PASSWORD			= "FTSabWXs4bz0IXsLn6nB";
	
	/**
	 * Gets the connection string.
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the connection string
	 */
	public String getConnectionString() {
		return String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", DB_SERVER_HOST_NAME, DB_PORT, DB_NAME, DB_USERNAME, DB_PASSWORD);
	}
		
	private void sendQueryToDatabase() {
        try (Connection con = DriverManager.getConnection(this.getConnectionString()); 
        		Statement stmt = con.createStatement();
        		ResultSet rs = stmt.executeQuery("select lname,DNO from employee")
        ) {
        	//TODO: add functionality here
            while (rs.next()) {
				String name = rs.getString(1);
				int number = rs.getInt(2);
				System.out.println(name + "\t\t" +  number);
            }
        } catch (SQLException ex) {
			System.out.println("SQLException: "	+ ex.getMessage());
			System.out.println("SQLState: "		+ ex.getSQLState());
			System.out.println("VendorError: "	+ ex.getErrorCode());
		} catch (Exception e) {
            System.out.println(e.toString());
        }
	}
}
