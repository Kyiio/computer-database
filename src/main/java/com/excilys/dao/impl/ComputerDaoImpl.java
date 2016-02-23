package com.excilys.dao.impl;

import com.excilys.dao.ComputerDao;
import com.excilys.dao.ConnectionCloser;
import com.excilys.dao.ConnectionManager;
import com.excilys.dao.exception.DaoException;
import com.excilys.dao.mapper.ComputerDaoMapper;
import com.excilys.dao.util.QueryBuilder;
import com.excilys.dao.util.QueryParameterMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

public class ComputerDaoImpl implements ComputerDao {

  private static final String INSERT_QUERY      =
      "INSERT INTO computer(COMPANY_ID, DISCONTINUED, INTRODUCED, NAME) VALUES (?, ?, ?, ?);";
  private static final String UPDATE_QUERY      =
      "UPDATE computer SET COMPANY_ID=?, DISCONTINUED=?, INTRODUCED=?, NAME=? WHERE ID=?";
  private static final String DELETE_QUERY      = "DELETE FROM computer WHERE ID=?";

  private static final String LIST_ALL_QUERY    =
      "SELECT * FROM computer LEFT JOIN company ON COMPANY_ID = company.ID";
  private static final String GET_BY_ID_QUERY   =
      "SELECT * FROM computer LEFT JOIN company ON COMPANY_ID = company.ID WHERE computer.ID = ?";
  private static final String GET_BY_NAME_QUERY =
      "SELECT * FROM computer LEFT JOIN company ON COMPANY_ID = company.ID WHERE computer.NAME = ?";

  private static ComputerDao  INSTANCE;
  private ConnectionManager   connectionManager;

  static {
    INSTANCE = new ComputerDaoImpl(ConnectionManager.getInstance());
  }

  public static ComputerDao getInstance() {
    return INSTANCE;
  }

  private ComputerDaoImpl(ConnectionManager connectionManager) {
    this.connectionManager = connectionManager;
  }

  @Override
  public int insertComputer(Company company, LocalDate introduced, LocalDate discontinued,
      String name) throws DaoException {

    int newId = -1;
    PreparedStatement preparedStatement = null;
    Connection connection = connectionManager.getConnection();

    if (name == null || name.length() <= 0) {
      throw new DaoException("Then name of the computer must be set !");
    }

    try {

      preparedStatement =
          connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

      if (company == null) {
        preparedStatement.setNull(1, java.sql.Types.BIGINT);
      } else {
        preparedStatement.setInt(1, company.getId());
      }

      if (discontinued == null) {
        preparedStatement.setNull(2, java.sql.Types.TIMESTAMP);
      } else {
        preparedStatement.setTimestamp(2, Timestamp.valueOf(discontinued.atStartOfDay()));
      }

      if (introduced == null) {
        preparedStatement.setNull(3, java.sql.Types.TIMESTAMP);
      } else {
        preparedStatement.setTimestamp(3, Timestamp.valueOf(introduced.atStartOfDay()));
      }

      preparedStatement.setString(4, name);

      preparedStatement.executeUpdate();
      ResultSet resultSet = preparedStatement.getGeneratedKeys();

      if (resultSet.next()) {
        newId = resultSet.getInt(1);
      }
    } catch (SQLException e) {
      throw new DaoException("Error while inserting in Computer table", e);
    } finally {
      connectionManager.closeConnection();
      ConnectionCloser.silentClose(preparedStatement);
    }

    return newId;
  }

  @Override
  public void updateComputer(Computer computer) throws DaoException {

    PreparedStatement preparedStatement = null;
    Connection connection = connectionManager.getConnection();

    if (computer == null) {
      throw new DaoException("The given computer is null !");
    }

    try {

      preparedStatement = connection.prepareStatement(UPDATE_QUERY);
      if (computer.getCompany() == null) {
        preparedStatement.setNull(1, java.sql.Types.BIGINT);
      } else {
        preparedStatement.setInt(1, computer.getCompany().getId());
      }

      if (computer.getDiscontinued() == null) {
        preparedStatement.setNull(2, java.sql.Types.TIMESTAMP);
      } else {
        preparedStatement.setTimestamp(2,
            Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
      }

      if (computer.getIntroduced() == null) {
        preparedStatement.setNull(3, java.sql.Types.TIMESTAMP);
      } else {
        preparedStatement.setTimestamp(3,
            Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
      }

      preparedStatement.setString(4, computer.getName());
      preparedStatement.setInt(5, computer.getId());

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      throw new DaoException("Error while updating" + computer + " in Computer table", e);
    } finally {
      connectionManager.closeConnection();
      ConnectionCloser.silentClose(preparedStatement);
    }
  }

  @Override
  public void deleteComputer(int id) throws DaoException {

    Connection connection = connectionManager.getConnection();
    PreparedStatement preparedStatement = null;

    try {
      preparedStatement = connection.prepareStatement(DELETE_QUERY);
      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      throw new DaoException("Error while trying to delete the computer with the folling id" + id
          + " from Computer table", e);
    } finally {
      connectionManager.closeConnection();
      ConnectionCloser.silentClose(preparedStatement);
    }
  }

  @Override
  public Computer getById(int id) throws DaoException {

    ResultSet results = null;
    Computer computerResult = null;
    PreparedStatement preparedStatement = null;
    Connection connection = connectionManager.getConnection();

    try {
      preparedStatement = connection.prepareStatement(GET_BY_ID_QUERY);
      preparedStatement.setInt(1, id);

      results = preparedStatement.executeQuery();

      ArrayList<Computer> computerList = ComputerDaoMapper.getComputerList(results);

      if (computerList.size() > 0) {
        computerResult = computerList.get(0);
      }

    } catch (SQLException e) {
      throw new DaoException("Error while trying to retrieve computer with id :" + id, e);
    } finally {
      connectionManager.closeConnection();
      ConnectionCloser.silentClose(results, preparedStatement);
    }
    return computerResult;
  }

  @Override
  public ArrayList<Computer> getByName(String name) throws DaoException {


    ResultSet results = null;
    ArrayList<Computer> computerResult = null;
    PreparedStatement preparedStatement = null;
    Connection connection = connectionManager.getConnection();

    try {
      preparedStatement = connection.prepareStatement(GET_BY_NAME_QUERY);
      preparedStatement.setString(1, name);

      results = preparedStatement.executeQuery();

      computerResult = ComputerDaoMapper.getComputerList(results);

    } catch (SQLException e) {
      throw new DaoException("Error while trying to retrieve computer with name :" + name, e);
    } finally {
      connectionManager.closeConnection();
      ConnectionCloser.silentClose(results, preparedStatement);
    }

    return computerResult;
  }

  @Override
  public ArrayList<Computer> listComputers() throws DaoException {

    ResultSet results = null;
    ArrayList<Computer> computerResult = null;
    PreparedStatement preparedStatement = null;
    Connection connection = connectionManager.getConnection();

    try {
      preparedStatement = connection.prepareStatement(LIST_ALL_QUERY);
      results = preparedStatement.executeQuery();

      computerResult = ComputerDaoMapper.getComputerList(results);

    } catch (SQLException e) {
      throw new DaoException("Error while trying to retrieve the computer list", e);
    } finally {
      connectionManager.closeConnection();
      ConnectionCloser.silentClose(results, preparedStatement);
    }

    return computerResult;
  }

  @Override
  public ArrayList<Computer> selectWithParameters(QueryParameters queryParameters)
      throws DaoException {

    ResultSet results = null;
    ArrayList<Computer> computerResult = null;
    PreparedStatement preparedStatement = null;
    Connection connection = connectionManager.getConnection();

    try {
      preparedStatement =
          QueryParameterMapper.createPageQueryAndGetPreparedStatement(queryParameters, connection);

      results = preparedStatement.executeQuery();

      computerResult = ComputerDaoMapper.getComputerList(results);

    } catch (SQLException e) {
      throw new DaoException(
          "Error while trying to retrieve the computer page with parameters : " + queryParameters,
          e);
    } finally {
      connectionManager.closeConnection();
      ConnectionCloser.silentClose(results, preparedStatement);
    }

    return computerResult;
  }

  @Override
  public int getCount(QueryParameters queryParameters) throws DaoException {

    int nbComputer = 0;
    ResultSet results = null;
    PreparedStatement preparedStatement = null;
    Connection connection = connectionManager.getConnection();

    try {

      preparedStatement =
          QueryParameterMapper.createCountQueryAndGetPreparedStatement(queryParameters, connection);
      results = preparedStatement.executeQuery();

      if (results != null && results.next()) {
        nbComputer = results.getInt(1);
      }
    } catch (SQLException e) {
      throw new DaoException(
          "Error while trying to retrieve the number of computer. Query parameters : "
              + queryParameters,
          e);
    } finally {
      connectionManager.closeConnection();
      ConnectionCloser.silentClose(results, preparedStatement);
    }

    return nbComputer;
  }

  @Override
  public void deleteComputersForCompanyId(int companyId) {

    QueryBuilder queryBuilder = new QueryBuilder();
    queryBuilder.deleteFrom("computer").where("COMPANY_ID =").append(companyId);
    String query = queryBuilder.getQuery();

    PreparedStatement preparedStatement = null;
    Connection connection = connectionManager.getConnection();

    try {
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.executeUpdate();

    } catch (SQLException e) {

      throw new DaoException(
          "Error while trying to delete the computers associated to the company id : " + companyId,
          e);

    } finally {
      connectionManager.closeConnection();
      ConnectionCloser.silentClose(preparedStatement);
    }
  }
}
