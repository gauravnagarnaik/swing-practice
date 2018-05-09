package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnection {

	private static DbConnection dbInstance = null;

	private DbConnection(){
	}


	public static DbConnection getDbInstance(){
		if (dbInstance == null){
			dbInstance = new DbConnection();
		}
		return dbInstance;
	}


	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		
		try{

			String url = "<MYSQL URL>";

			Class.forName("com.mysql.jdbc.Driver");

			Connection connection = DriverManager.getConnection("URL?user=<user>&password=<password>");

			return connection;

		}
		catch(Exception e) {
			e.printStackTrace();
			throw new SQLException("Could not obtain connection for database");
		}
	}

}
