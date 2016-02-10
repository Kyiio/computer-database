package com.excilys.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class that offers different methods to close properly a ResultSet, a Statement and a Connection 
 * @author Bastien Herbaut 
 */
public class ConnectionCloser {

	/**
	 * Method that close the result set
	 * @param resultSet The result set that we want to close
	 */
	public static void silentClose(ResultSet resultSet) {
	    if (resultSet != null) {
	        try {
	            resultSet.close();
	        } catch (SQLException e) {
	            System.err.println("Fail closing the resultSet :" + e.getMessage());
	        }
	    }
	}

	/**
	 * Method that close the statement
	 * @param statement The statement that we want to close
	 */
	public static void silentClose(Statement statement) {
	    if (statement != null) {
	        try {
	            statement.close();
	        } catch (SQLException e) {
	            System.err.println("Fail closing the statement :" + e.getMessage());
	        }
	    }
	}

	/**
	 * Method that close the connection
	 * @param connection The connection that we want to close
	 */
	public static void silentClose(Connection connection) {
	    if (connection != null) {
	        try {
	            connection.close();
	        } catch (SQLException e) {
	            System.err.println("Fail closing the connection : " + e.getMessage());
	        }
	    }
	}

	/**
	 * Method that close the statement and the connection
	 * @param statement The statement that we want to close
	 * @param connection The connection that we want to close
	 */
	public static void silentClose(Statement statement, Connection connection) {
	    silentClose(statement);
	    silentClose(connection);
	}

	/**
	 * Method that close the resultSet, the statement and the connection
	 * @param resultSet The resultSet that we want to close
	 * @param statement The statement that we want to close
	 * @param connection The connection that we want to close
	 */
	public static void silentClose(ResultSet resultSet, Statement statement, Connection connection) {
	    silentClose(resultSet);
	    silentClose(statement);
	    silentClose(connection);
	}
	
}
