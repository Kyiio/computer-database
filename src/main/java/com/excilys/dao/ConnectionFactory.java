package com.excilys.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.excilys.dao.exception.DAOConfigurationException;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

/**
 * Class that instantiate the connection with the database by using the
 * information in dao.properties file It also offers function to get all the DAO
 * object
 * 
 * @author B. Herbaut
 */
public class ConnectionFactory {

	private static final String FILE_PROPERTIES = "connection.properties";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_USER_NAME = "nomutilisateur";
	private static final String PROPERTY_PASSWORD = "motdepasse";

	private static ConnectionFactory INSTANCE;

	private String url;
	private String username;
	private String password;
	private String driver;
	
	private BoneCP connectionPool;

	private ConnectionFactory(BoneCP connectionPool, String url, String userName, String password, String driver) {
		this.connectionPool = connectionPool;
		this.url = url;
		this.username = userName;
		this.password = password;
		this.driver = driver;
	}

	/**
	 * This block retrieve the information from the FILE_PROPERTIES file in
	 * order to connect to the database such as the username, the url and the
	 * password It also loads the JDBC driver.
	 */
	static {
		Properties properties = new Properties();
		String url;
		String driver;
		String userName;
		String password;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream propertiesFile = classLoader.getResourceAsStream(FILE_PROPERTIES);

		if (propertiesFile == null) {
			throw new DAOConfigurationException("The properties file " + FILE_PROPERTIES + " can't be found");
		}
		
		try {
			properties.load(propertiesFile);
			url = properties.getProperty(PROPERTY_URL);
			driver = properties.getProperty(PROPERTY_DRIVER);
			userName = properties.getProperty(PROPERTY_USER_NAME);
			password = properties.getProperty(PROPERTY_PASSWORD);

		} catch (IOException e) {
			throw new DAOConfigurationException("Fail loading the properties file " + FILE_PROPERTIES, e);
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DAOConfigurationException("Can't find the driver", e);
		}
		
		BoneCP connectionPool = null;
		
		try{
			BoneCPConfig config = new BoneCPConfig();

	        // We give the connection information to the BoneCP config
	        config.setJdbcUrl(url);
	        config.setUsername(userName);
	        config.setPassword(password);

	        // We set the size of the connection pool
	        
	        //The min connection per partition is the number of connection that will be open when we create the connection pool
	        config.setMinConnectionsPerPartition(5);
	        //The max connection per partition is the maximum number of connection that will be open 
	        config.setMaxConnectionsPerPartition(10);
	        //The partition count is the number of partition
	        config.setPartitionCount(2);
	        
	        connectionPool = new BoneCP(config);
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOConfigurationException("Error while instantiating the connection pool",e);
		}
                
		INSTANCE = new ConnectionFactory(connectionPool, url, userName, password, driver);
	}

	/**
	 * This method give the instance of connectionFactory
	 * 
	 * @return The ConnectionFactory instance
	 * @throws DAOConfigurationException
	 */
	public static ConnectionFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * Method that returns a connection from the connection pool.
	 * 
	 * @return A connection to the database
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return connectionPool.getConnection();
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getDriver() {
		return driver;
	}

}
