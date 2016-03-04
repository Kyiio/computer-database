package com.excilys.dao.mapper;

import com.excilys.exception.MappingException;
import com.excilys.model.Company;
import com.excilys.model.Computer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This interface offers a method to map a resultSet into a ComputerList.
 *
 * @author B. Herbaut
 */
@Service
public class ComputerDaoMapper implements RowMapper<Computer> {

  /**
   * This method maps the resultSet into an ArrayList of Computer.
   *
   * @param resultSet The resultSet we want to map
   * @return The mapped computer list
   */
  public static ArrayList<Computer> getComputerList(ResultSet resultSet) {

    ArrayList<Computer> convertedResults = new ArrayList<Computer>();

    try {
      while (resultSet.next()) {

        convertedResults.add(getComputer(resultSet));
      }
    } catch (SQLException e) {
      throw new MappingException("Error while trying to Map a resultSet into a computer", e);
    }

    return convertedResults;

  }

  /**
   * Gets the computer.
   *
   * @param resultSet the result set
   * @return the computer
   * @throws SQLException the SQL exception
   */
  public static Computer getComputer(ResultSet resultSet) throws SQLException {

    Timestamp discontinuedTimestamp;
    Timestamp introducedTimestamp;

    LocalDate discontinuedLocalDate;
    LocalDate introducedLocalDate;

    discontinuedTimestamp = resultSet.getTimestamp("DISCONTINUED");
    introducedTimestamp = resultSet.getTimestamp("INTRODUCED");

    if (discontinuedTimestamp == null) {
      discontinuedLocalDate = null;
    } else {
      discontinuedLocalDate = discontinuedTimestamp.toLocalDateTime().toLocalDate();
    }

    if (introducedTimestamp == null) {
      introducedLocalDate = null;
    } else {
      introducedLocalDate = introducedTimestamp.toLocalDateTime().toLocalDate();
    }

    return new Computer(resultSet.getLong("computer.ID"),
        new Company(resultSet.getLong("company.ID"), resultSet.getString("company.NAME")),
        resultSet.getString("computer.NAME"), discontinuedLocalDate, introducedLocalDate);
  }

  @Override
  public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {

    return getComputer(resultSet);
  }

}
