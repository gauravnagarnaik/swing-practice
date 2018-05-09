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

			String url = "jdbc:mysql://sql2.njit.edu/sss329";

			Class.forName("com.mysql.jdbc.Driver");
			//return DriverManager.getConnection(url, "sss329", "vLsLHc1Lu");

			Connection connection = DriverManager.getConnection("jdbc:mysql://sql2.njit.edu/sss329?user=sss329&password=");

			return connection;

		}
		catch(Exception e) {
			e.printStackTrace();
			throw new SQLException("Could not obtain connection for database");
		}
	}

}
