package com.excilys.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.excilys.exception.DAOConfigurationException;


/**
 * Class that instantiate the connection with the database by using the information in dao.properties file
 * It also offers function to get all the DAO object
 * @author Herbaut Bastien
 */
public class ConnectionFactory {
	
    private static final String FILE_PROPERTIES         = "dao.properties";
    private static final String PROPERTY_URL            = "url";
    private static final String PROPERTY_DRIVER         = "driver";
    private static final String PROPERTY_USER_NAME 		= "nomutilisateur";
    private static final String PROPERTY_PASSWORD    	= "motdepasse";

    private static ConnectionFactory INSTANCE;
    
    private String url;
    private String username;
    private String password;
    private String driver;

    private ConnectionFactory(String url, String username, String password, String driver) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;
    }

    /**
     * This block retrieve the information from the FILE_PROPERTIES file in order to connect to the database 
     * such as the username, the url and the password
     * It also loads the JDBC driver.
     */
    static
    {
        Properties properties = new Properties();
        String url;
        String driver;
        String userName;
        String password;

        InputStream fichierProperties = ConnectionFactory.class.getClassLoader().getResourceAsStream( FILE_PROPERTIES );
        
        if(fichierProperties == null) {
            throw new DAOConfigurationException( "The properties file " + FILE_PROPERTIES + " can't be found" );
        }

        try {
            properties.load( fichierProperties );
            url = properties.getProperty(PROPERTY_URL);
            driver = properties.getProperty(PROPERTY_DRIVER);
            userName = properties.getProperty(PROPERTY_USER_NAME);
            password = properties.getProperty(PROPERTY_PASSWORD);
        
        }catch(IOException e) {
           throw new DAOConfigurationException( "Fail loading the properties file " + FILE_PROPERTIES, e );
        }

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new DAOConfigurationException("Can't find the driver", e);
        }

        INSTANCE = new ConnectionFactory(url, userName, password, driver);
    }
    
    /**
     * This method give the instance of connection
     * 
     * @return The ConnectionFactory instance
     * @throws DAOConfigurationException
     */
    public static ConnectionFactory getInstance(){
         return INSTANCE;
    }

    /**
     * @return A connection to the database
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
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
