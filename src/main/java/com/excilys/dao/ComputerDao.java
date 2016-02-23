package com.excilys.dao;

import com.excilys.dao.exception.DaoException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This DAO offers different methods to access and modify the Computer table from the database.
 *
 * @author B. Herbaut
 */
public interface ComputerDao {

  /**
   * Method to insert a new computer in the database.
   *
   * @param company
   *          The company that owns the computer.
   * @param discontinued
   *          The discontinued date.
   * @param introduced
   *          The introduced date.
   * @param name
   *          The name of the computer.
   * @return The id set by the database for the new computer.
   * @throws DaoException
   *           the dao exception
   * @see Company
   */
  public int insertComputer(Company company, LocalDate introduced, LocalDate discontinued,
      String name) throws DaoException;

  /**
   * Method that save the changes made to the computer given in parameter into the database.
   * 
   * @param computer
   *          The computer we want to update.
   * @see Computer
   */
  public void updateComputer(Computer computer);

  /**
   * Method that delete the computer that possess the id given in parameter.
   * 
   * @param id
   *          The id of the computer we want to delete.
   */
  public void deleteComputer(int id);

  /**
   * This method access the database to retrieve the computer with the given id. If the id doesn't
   * match any existing id, null is returned.
   * 
   * @param id
   *          The id of the computer we want to retrieve.
   * @return The matching computer or null.
   * @see Computer
   */
  public Computer getById(int id);

  /**
   * This method retrieve a list of all the computer that possess the name given. If none are found,
   * an empty list is returned.
   * 
   * @param name
   *          The name of the computer(s).
   * @return The list of matching computers.
   */
  public ArrayList<Computer> getByName(String name);

  /**
   * This method retrieves all the computer in the database and store them into a list.
   * 
   * @return The list of all the computers in the database.
   */
  public ArrayList<Computer> listComputers();

  /**
   * This method retrieves all the computer that matches the parameters specified in the given
   * {@link QueryParameters} object.
   * 
   * @param queryParameters
   *          The query parameters.
   * 
   * @return The list of computer that matches the {@link QueryParameters}
   */
  public ArrayList<Computer> selectWithParameters(QueryParameters queryParameters);

  /**
   * Method that returns the number of computer that match the parameters specified in the given
   * {@link QueryParameters} object.
   * 
   * @param queryParameter
   *          The query parameters.
   * @return The number of Computer that match the query parameters.
   */
  public int getCount(QueryParameters queryParameter);

  /**
   * Method that deletes all the computers associated to the given companyId.
   *
   * @param companyId
   *          The id of the company
   */
  public void deleteComputersForCompanyId(int companyId);
}
