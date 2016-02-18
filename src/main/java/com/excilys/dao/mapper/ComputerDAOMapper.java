package com.excilys.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.exception.MappingException;
import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * 
 * This interface offers a method to map a resultSet into a ComputerList
 * 
 * @author B. Herbaut
 *
 */
public interface ComputerDAOMapper {

	/**
	 * This method maps the resultSet into an ArrayList of Computer
	 * 
	 * @param resultSet
	 *            The resultSet we want to map
	 * @return The mapped computer list
	 */
	public static ArrayList<Computer> getComputerList(ResultSet resultSet) {

		ArrayList<Computer> convertedResults = new ArrayList<Computer>();

		Timestamp discontinuedTimestamp;
		Timestamp introducedTimestamp;

		LocalDateTime discontinuedLocalDateTime;
		LocalDateTime introducedLocalDateTime;

		try {
			while (resultSet.next()) {

				discontinuedTimestamp = resultSet.getTimestamp("DISCONTINUED");
				introducedTimestamp = resultSet.getTimestamp("INTRODUCED");

				if (discontinuedTimestamp == null) {
					discontinuedLocalDateTime = null;
				} else {
					discontinuedLocalDateTime = discontinuedTimestamp.toLocalDateTime();
				}

				if (introducedTimestamp == null) {
					introducedLocalDateTime = null;
				} else {
					introducedLocalDateTime = introducedTimestamp.toLocalDateTime();
				}

				convertedResults.add(new Computer(resultSet.getInt("computer.ID"),
						new Company(resultSet.getInt("company.ID"), resultSet.getString("company.NAME")),
						resultSet.getString("computer.NAME"), discontinuedLocalDateTime, introducedLocalDateTime));
			}

		} catch (SQLException e) {
			throw new MappingException("Error while trying to Map a resultSet into a ComputerList", e);
		}

		return convertedResults;
	}
}