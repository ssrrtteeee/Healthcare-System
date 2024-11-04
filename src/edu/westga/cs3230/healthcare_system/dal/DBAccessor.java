package edu.westga.cs3230.healthcare_system.dal;

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
	public static final String	DB_NAME				= "cs3230f24" + "h";
	public static final String	DB_USERNAME			= "cs3230f24h";
	public static final String	DB_PASSWORD			= "FTSabWXs4bz0IXsLn6nB";
	
	/**
	 * Gets the connection string.
	 * 
	 * @precondition true
	 * @postcondition true
	 * @return the connection string
	 */
	public static String getConnectionString() {
		return String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", DB_SERVER_HOST_NAME,  DB_PORT,  DB_NAME,  DB_USERNAME,  DB_PASSWORD);
	}
}
