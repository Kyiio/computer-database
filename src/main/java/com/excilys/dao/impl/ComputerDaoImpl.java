package com.excilys.dao.impl;

import com.excilys.dao.ComputerDao;
import com.excilys.dao.ConnectionCloser;
import com.excilys.dao.ConnectionFactory;
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

  private static final String INSERT_QUERY =
      "INSERT INTO computer(COMPANY_ID, DISCONTINUED, INTRODUCED, NAME) VALUES (?, ?, ?, ?);";
  private static final String UPDATE_QUERY =
      "UPDATE computer SET COMPANY_ID=?, DISCONTINUED=?, INTRODUCED=?, NAME=? WHERE ID=?";
  private static final String DELETE_QUERY = "DELETE FROM computer WHERE ID=?";

  private static final String LIST_ALL_QUERY =
      "SELECT * FROM computer LEFT JOIN company ON COMPANY_ID = company.ID";
  private static final String GET_BY_ID_QUERY =
      "SELECT * FROM computer LEFT JOIN company ON COMPANY_ID = company.ID WHERE computer.ID = ?";
  private static final String GET_BY_NAME_QUERY =
      "SELECT * FROM computer LEFT JOIN company ON COMPANY_ID = company.ID WHERE computer.NAME = ?";

  private static ComputerDao INSTANCE;

  static {
    INSTANCE = new ComputerDaoImpl();
  }

  public static ComputerDao getInstance() {
    return INSTANCE;
  }

  private ComputerDaoImpl() {
  }

  @Override
  public int insertComputer(Company company, LocalDate introduced, LocalDate discontinued,
      String name) throws DaoException {

    int newId = -1;
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    if (name == null || name.length() <= 0) {
      throw new DaoException("Then name of the computer must be set !");
    }

    try {
      connection = ConnectionFactory.getInstance().getConnection();
      connection.setAutoCommit(false);

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

      connection.commit();

    } catch (SQLException e) {

      try {
        connection.rollback();
      } catch (SQLException e1) {
        throw new DaoException("Error rollback on connection while inserting ", e);
      }
      throw new DaoException("Error while inserting in Computer table", e);

    } finally {
      ConnectionCloser.silentClose(preparedStatement, connection);
    }

    return newId;
  }

  @Override
  public void updateComputer(Computer computer) throws DaoException {

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    if (computer == null) {
      throw new DaoException("The given computer is null !");
    }

    try {
      connection = ConnectionFactory.getInstance().getConnection();
      connection.setAutoCommit(false);

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

      connection.commit();

    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException e1) {
        throw new DaoException("Error rollback while updating", e);
      }
      throw new DaoException("Error while updating" + computer + " in Computer table", e);

    } finally {
      ConnectionCloser.silentClose(preparedStatement, connection);
    }
  }

  @Override
  public void deleteComputer(int id) throws DaoException {

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = ConnectionFactory.getInstance().getConnection();
      connection.setAutoCommit(false);

      preparedStatement = connection.prepareStatement(DELETE_QUERY);
      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();

      connection.commit();

    } catch (SQLException e) {

      try {
        connection.rollback();
      } catch (SQLException e1) {
        throw new DaoException("Error rollback while deleting", e);
      }

      throw new DaoException("Error while trying to delete the computer with the folling id" + id
          + " from Computer table", e);

    } finally {
      ConnectionCloser.silentClose(preparedStatement, connection);
    }
  }

  @Override
  public Computer getById(int id) throws DaoException {

    ResultSet results = null;
    Computer computerResult = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = ConnectionFactory.getInstance().getConnection();

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
      ConnectionCloser.silentClose(results, preparedStatement, connection);
    }
    return computerResult;
  }

  @Override
  public ArrayList<Computer> getByName(String name) throws DaoException {

    ResultSet results = null;
    ArrayList<Computer> computerResult = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = ConnectionFactory.getInstance().getConnection();

      preparedStatement = connection.prepareStatement(GET_BY_NAME_QUERY);
      preparedStatement.setString(1, name);

      results = preparedStatement.executeQuery();

      computerResult = ComputerDaoMapper.getComputerList(results);

    } catch (SQLException e) {
      throw new DaoException("Error while trying to retrieve computer with name :" + name, e);

    } finally {
      ConnectionCloser.silentClose(results, preparedStatement, connection);
    }

    return computerResult;
  }

  @Override
  public ArrayList<Computer> listComputers() throws DaoException {

    ResultSet results = null;
    ArrayList<Computer> computerResult = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = ConnectionFactory.getInstance().getConnection();
      preparedStatement = connection.prepareStatement(LIST_ALL_QUERY);
      results = preparedStatement.executeQuery();

      computerResult = ComputerDaoMapper.getComputerList(results);

    } catch (SQLException e) {
      throw new DaoException("Error while trying to retrieve the computer list", e);

    } finally {
      ConnectionCloser.silentClose(results, preparedStatement, connection);
    }

    return computerResult;
  }

  @Override
  public ArrayList<Computer> selectWithParameters(QueryParameters queryParameters)
      throws DaoException {

    ResultSet results = null;
    ArrayList<Computer> computerResult = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = ConnectionFactory.getInstance().getConnection();

      preparedStatement =
          QueryParameterMapper.createPageQueryAndGetPreparedStatement(queryParameters, connection);

      results = preparedStatement.executeQuery();

      computerResult = ComputerDaoMapper.getComputerList(results);

    } catch (SQLException e) {
      throw new DaoException(
          "Error while trying to retrieve the computer page with parameters : " + queryParameters,
          e);

    } finally {
      ConnectionCloser.silentClose(results, preparedStatement, connection);
    }

    return computerResult;
  }

  @Override
  public int getCount(QueryParameters queryParameters) throws DaoException {

    ResultSet results = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    int nbComputer = 0;

    try {
      connection = ConnectionFactory.getInstance().getConnection();

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
      ConnectionCloser.silentClose(results, preparedStatement, connection);
    }

    return nbComputer;
  }

  @Override
  public void deleteComputersForCompanyId(int companyId, Connection connection) {

    QueryBuilder queryBuilder = new QueryBuilder();
    queryBuilder.deleteFrom("computer").where("COMPANY_ID =").append(companyId);
    String query = queryBuilder.getQuery();

    PreparedStatement preparedStatement = null;

    try {
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.executeUpdate();

    } catch (SQLException e) {

      throw new DaoException(
          "Error while trying to delete the computers associated to the company id : " + companyId,
          e);

    } finally {
      ConnectionCloser.silentClose(preparedStatement);
    }
  }
}
