package com.excilys.dao;

import com.excilys.dao.exception.DaoConfigurationException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The Class ConnectionManager.
 *
 * @author B. Herbaut
 */
public class ConnectionManager {

  private static final ConnectionManager INSTANCE;
  private static ThreadLocal<Connection> threadLocal;

  static {
    INSTANCE = new ConnectionManager();
  }

  private ConnectionManager() {
    threadLocal = new ThreadLocal<Connection>();
  }

  public static ConnectionManager getInstance() {
    return INSTANCE;
  }

  /**
   * Gets the connection.
   *
   * @return The connection
   * @throws SQLException
   *           the SQL exception
   */
  public Connection getConnection() {

    Connection connection = threadLocal.get();

    if (connection == null) {
      try {
        connection = ConnectionFactory.getInstance().getConnection();
      } catch (SQLException e) {
        throw new DaoConfigurationException(
            "Can't retrieve the connection from the ConnectionFactory !", e);
      }
      threadLocal.set(connection);
    }

    return connection;
  }

  /**
   * Close the current connection and remove it from the threadLocal. (Only if the current
   * connection isn't a transaction)
   */
  public void closeConnection() {
    Connection connection = threadLocal.get();

    try {
      if (connection != null && connection.getAutoCommit()) {
        ConnectionCloser.silentClose(connection);
        threadLocal.set(null);
      }
    } catch (SQLException e) {
      throw new DaoConfigurationException("Can't close the connection : " + connection, e);
    }
  }

  /**
   * Starts a transaction.
   */
  public void startTransaction() {
    Connection connection = getConnection();
    try {
      threadLocal.get().setAutoCommit(false);
    } catch (SQLException e) {
      throw new DaoConfigurationException(
          "Can't set auto commit to false for the connection : " + connection, e);
    }
  }

  /**
   * Ends the current transaction.
   */
  public void endTransaction() {

    Connection connection = threadLocal.get();

    if (connection == null) {
      throw new DaoConfigurationException("Can't commit on a transaction that doesn't exists !");
    }

    try {
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      throw new DaoConfigurationException(
          "Problem trying to end the transaction ! Connection : " + connection, e);
    }
  }

  /**
   * Rollback the current transaction.
   */
  public void rollback() {

    Connection connection = threadLocal.get();

    if (connection == null) {
      throw new DaoConfigurationException("Can't rollback on a transaction that doesn't exists !");
    }

    try {
      connection.rollback();
      connection.setAutoCommit(false);
    } catch (SQLException e) {
      throw new DaoConfigurationException(
          "Problem trying to rollback the transaction ! Connection : " + connection, e);
    }
  }
}
