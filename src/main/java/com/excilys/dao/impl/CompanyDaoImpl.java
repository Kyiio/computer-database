package com.excilys.dao.impl;

import com.excilys.dao.CompanyDao;
import com.excilys.dao.ConnectionCloser;
import com.excilys.dao.ConnectionManager;
import com.excilys.dao.exception.DaoException;
import com.excilys.dao.mapper.CompanyDaoMapper;
import com.excilys.model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyDaoImpl implements CompanyDao {

  private static final String LIST_ALL_QUERY    = "SELECT * FROM company";
  private static final String GET_BY_ID_QUERY   = "SELECT * FROM company WHERE ID = ?";
  private static final String GET_BY_NAME_QUERY = "SELECT * FROM company WHERE NAME = ?";

  private static final String DELETE_QUERY      = "DELETE FROM company WHERE ID=?";

  private static CompanyDao   INSTANCE;

  private ConnectionManager   connectionManager;

  static {
    INSTANCE = new CompanyDaoImpl(ConnectionManager.getInstance());
  }

  public static CompanyDao getInstance() {
    return INSTANCE;
  }

  private CompanyDaoImpl(ConnectionManager connectionManager) {
    this.connectionManager = connectionManager;
  }

  @Override
  public Company getById(int id) throws DaoException {

    ResultSet results = null;
    Company companyResult = null;
    PreparedStatement preparedStatement = null;
    Connection connection = connectionManager.getConnection();

    try {
      preparedStatement = connection.prepareStatement(GET_BY_ID_QUERY);
      preparedStatement.setInt(1, id);

      results = preparedStatement.executeQuery();

      ArrayList<Company> companyList = CompanyDaoMapper.getCompanyList(results);

      if (companyList.size() > 0) {
        companyResult = companyList.get(0);
      }

    } catch (SQLException e) {
      throw new DaoException("Error retrieving company with id " + id, e);
    } finally {
      connectionManager.closeConnection();
      ConnectionCloser.silentClose(results, preparedStatement);
    }

    return companyResult;
  }

  @Override
  public ArrayList<Company> getByName(String name) throws DaoException {

    ResultSet results = null;
    ArrayList<Company> companyResults = null;
    PreparedStatement preparedStatement = null;
    Connection connection = connectionManager.getConnection();

    try {
      preparedStatement = connection.prepareStatement(GET_BY_NAME_QUERY);
      preparedStatement.setString(1, name);

      results = preparedStatement.executeQuery();

      companyResults = CompanyDaoMapper.getCompanyList(results);

    } catch (SQLException e) {
      throw new DaoException("Error retrieving company with name " + name, e);
    } finally {
      connectionManager.closeConnection();
      ConnectionCloser.silentClose(results, preparedStatement);
    }

    return companyResults;
  }

  @Override
  public ArrayList<Company> listCompanies() throws DaoException {

    ResultSet results = null;
    ArrayList<Company> companyResults = null;
    PreparedStatement preparedStatement = null;
    Connection connection = connectionManager.getConnection();

    try {
      preparedStatement = connection.prepareStatement(LIST_ALL_QUERY);

      results = preparedStatement.executeQuery();

      companyResults = CompanyDaoMapper.getCompanyList(results);

    } catch (SQLException e) {
      throw new DaoException("Error retrieving company list", e);

    } finally {
      connectionManager.closeConnection();
      ConnectionCloser.silentClose(results, preparedStatement);
    }

    return companyResults;
  }

  @Override
  public void deleteCompany(int id) {

    PreparedStatement preparedStatement = null;
    Connection connection = connectionManager.getConnection();

    try {
      preparedStatement = connection.prepareStatement(DELETE_QUERY);
      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      throw new DaoException("Error trying to delete the company with id : " + id, e);
    } finally {
      connectionManager.closeConnection();
      ConnectionCloser.silentClose(preparedStatement);
    }
  }

}
