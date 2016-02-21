package com.excilys.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ConnectionCloser;
import com.excilys.dao.ConnectionFactory;
import com.excilys.dao.exception.DAOException;
import com.excilys.dao.impl.CompanyDAOImpl;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;
import com.excilys.service.exception.ServiceException;
import com.excilys.validator.CompanyValidator;

public class CompanyServiceImpl implements CompanyService {

	public static CompanyService INSTANCE;
	private CompanyDAO companyDAO;
	static {
		INSTANCE = new CompanyServiceImpl();
	}

	public static CompanyService getInstance() {
		return INSTANCE;
	}

	private CompanyServiceImpl() {
		companyDAO = CompanyDAOImpl.getInstance();
	}

	@Override
	public Company getById(int id) {
		CompanyValidator.checkId(id);
		return companyDAO.getById(id);
	}

	@Override
	public ArrayList<Company> getByName(String name) {
		CompanyValidator.checkName(name);
		return companyDAO.getByName(name);
	}

	@Override
	public ArrayList<Company> listCompanies() {

		return companyDAO.listCompanies();
	}

	@Override
	public void deleteCompany(int id) {

		CompanyValidator.checkId(id);
		Connection connection = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			connection.setAutoCommit(false);

			// We first delete all the computers associated to the company that
			// we will delete
			ComputerServiceImpl.getInstance().deleteComputerAssociatedToCompany(id, connection);

			// And finally we delete the company itself
			companyDAO.deleteCompany(id, connection);

			connection.commit();

		} catch (DAOException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException(
						"Error rollback while trying to delete the company and the associated computers ! Company id : "
								+ id,
						e);
			}
			throw new ServiceException("Error will trying delete the company and the associated computers ! Id : " + id,
					e);
		} catch (SQLException e) {
			throw new ServiceException(
					"Can't retrieve the connection to delete the company and the associated computers !", e);
		} finally {
			ConnectionCloser.silentClose(connection);
		}

	}

}
