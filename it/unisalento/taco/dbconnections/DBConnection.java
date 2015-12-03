package it.unisalento.taco.dbconnections;

import java.sql.*;

public class DBConnection {
	
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private String sqluser = "root";
	private String sqlpsw = "root";
	
	public ResultSet queryDB(String query) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    connect = DriverManager.getConnection("jdbc:mysql://localhost/tacodb", sqluser, sqlpsw);
		    statement = connect.createStatement();
		    resultSet = statement.executeQuery(query);
		} catch (Exception e) {
		      throw e;
		    } finally {
		    	//close();
		    }
		return resultSet;
	}
	
	private void close() {
		try {
	      if (resultSet != null) {
	        resultSet.close();
	      }

	      if (statement != null) {
	        statement.close();
	      }

	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	}
	
}
