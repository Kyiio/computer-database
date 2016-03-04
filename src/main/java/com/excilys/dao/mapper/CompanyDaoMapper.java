package com.excilys.dao.mapper;

import com.excilys.exception.MappingException;
import com.excilys.model.Company;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This interface offers a method to map a resultSet into a CompanyList.
 *
 * @author B. Herbaut
 */
public class CompanyDaoMapper implements RowMapper<Company> {

  /**
   * This function maps the resultSet into an ArrayList of Company.
   *
   * @param resultSet
   *          The resultSet we want to map
   * @return The mapped company list
   */
  public static ArrayList<Company> getCompanyList(ResultSet resultSet) {

    ArrayList<Company> convertedResults = new ArrayList<Company>();

    try {
      while (resultSet.next()) {
        convertedResults.add(getCompany(resultSet));
      }

    } catch (SQLException e) {
      throw new MappingException("Error while trying to map a resultSet into a list of Company", e);
    }

    return convertedResults;
  }

  /**
   * Gets the company.
   *
   * @param resultSet the result set
   * @return the company
   * @throws SQLException the SQL exception
   */
  public static Company getCompany(ResultSet resultSet) throws SQLException {
    return new Company(resultSet.getLong("ID"), resultSet.getString("NAME"));
  }

  @Override
  public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return getCompany(resultSet);
  }

}
