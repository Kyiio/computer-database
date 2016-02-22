package com.excilys.dao.mapper;

import com.excilys.exception.MappingException;
import com.excilys.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This interface offers a method to map a resultSet into a CompanyList.
 *
 * @author B. Herbaut
 */
public interface CompanyDaoMapper {

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
        convertedResults.add(new Company(resultSet.getInt("ID"), resultSet.getString("NAME")));
      }

    } catch (SQLException e) {
      throw new MappingException("Error while trying to map a resultSet into a list of Company", e);
    }

    return convertedResults;
  }

}
