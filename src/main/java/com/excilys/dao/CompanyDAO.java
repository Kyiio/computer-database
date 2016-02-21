package com.excilys.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.excilys.dao.exception.DAOException;
import com.excilys.model.Company;

/**
 * This DAO interface offers different methods to access the Company table from
 * the database.
 * 
 * @author B. Herbaut
 */
public interface CompanyDAO {

	/**
	 * This method return the Company that matches the given id. If none match
	 * returns null
	 * 
	 * @param id
	 *            The id of the Company we want to retrieve.
	 * @return The Company matching the id.
	 * @throws DAOException
	 * @see Company
	 */
	public Company getById(int id) throws DAOException;

	/**
	 * Method that access the database to retrieve the list of company that
	 * possess the name given in parameter.
	 * 
	 * If none matches the name, an empty list is returned.
	 * 
	 * @param name
	 *            The name of the companies we want to retrieve.
	 * @return The list of company that possess the given name.
	 * @throws DAOException
	 * @see Company
	 */
	public ArrayList<Company> getByName(String name) throws DAOException;

	/**
	 * This method the list of all the companies stored in the database.
	 * 
	 * @return The list of all the companies.
	 * @throws DAOException
	 * @see Company
	 */
	public ArrayList<Company> listCompanies() throws DAOException;

	/**
	 * This method delete the company with the given id.
	 * 
	 * @param id
	 * @param connection The connection we need to perform this operation
	 */
	public void deleteCompany(int id, Connection connection);

}
