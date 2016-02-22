package com.excilys.dao;

import com.excilys.dao.exception.DaoException;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

import java.io.File;
import java.nio.charset.Charset;
import java.sql.SQLException;

/**
 * The Class DBTestConnector.
 */
public class DbTestConnector {

  /** The connection factory. */
  private ConnectionFactory connectionFactory;

  /** The database tester. */
  private IDatabaseTester databaseTester;

  /** The data source. */
  private JdbcDataSource dataSource;

  /**
   * Instantiates a new DB test connector.
   */
  public DbTestConnector() {
    initConnection();
  }

  /**
   * Inits the data source.
   */
  public void initDataSource() {

    dataSource = new JdbcDataSource();
    dataSource.setURL(connectionFactory.getUrl());
    dataSource.setUser(connectionFactory.getUsername());
    dataSource.setPassword(connectionFactory.getPassword());
  }

  /**
   * Inits the schema.
   *
   * @param pathToSqlSchema
   *          the path to sql schema
   */
  public void initSchema(String pathToSqlSchema) {

    try {
      RunScript.execute(connectionFactory.getUrl(), connectionFactory.getUsername(),
          connectionFactory.getPassword(), pathToSqlSchema, Charset.forName("UTF8"), false);
    } catch (SQLException e) {
      throw new DaoException("Fail to init database schema", e);
    }
  }

  /**
   * Inits the connection.
   */
  private void initConnection() {
    connectionFactory = ConnectionFactory.getInstance();
  }

  /**
   * Cleanly insert.
   *
   * @param dataSet
   *          the data set
   * @throws Exception
   *           the exception
   */
  public void cleanlyInsert(IDataSet dataSet) throws Exception {

    databaseTester =
        new JdbcDatabaseTester(connectionFactory.getDriver(), connectionFactory.getUrl(),
            connectionFactory.getUsername(), connectionFactory.getPassword());
    databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();
  }

  /**
   * Read data set.
   *
   * @param pathToTheFile
   *          the path to the file
   * @return the i data set
   * @throws Exception
   *           the exception
   */
  public IDataSet readDataSet(String pathToTheFile) throws Exception {

    return new FlatXmlDataSetBuilder().build(new File(pathToTheFile));
  }

  /**
   * Gets the database tester.
   *
   * @return the database tester
   */
  public IDatabaseTester getDatabaseTester() {

    return databaseTester;
  }
}
