package com.excilys.service.impl;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

import com.excilys.dao.ComputerDAO;
import com.excilys.dao.impl.ComputerDAOImpl;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;
import com.excilys.service.ComputerService;
import com.excilys.validator.CompanyValidator;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.QueryParametersValidator;

public class ComputerServiceImpl implements ComputerService {

	public static ComputerService INSTANCE;
	public ComputerDAO computerDAO;

	static {
		INSTANCE = new ComputerServiceImpl();
	}

	public static ComputerService getInstance() {
		return INSTANCE;
	}

	private ComputerServiceImpl() {
		computerDAO = ComputerDAOImpl.getInstance();
	}

	@Override
	public void updateComputer(Computer computer) {

		ComputerValidator.checkId(computer.getId());
		ComputerValidator.checkName(computer.getName());
		ComputerValidator.checkDateConsitency(computer.getIntroduced(), computer.getDiscontinued());

		computerDAO.updateComputer(computer);
	}

	@Override
	public int insertComputer(Company company, LocalDate introduced, LocalDate discontinued, String name) {

		ComputerValidator.checkName(name);
		ComputerValidator.checkDateConsitency(introduced, discontinued);

		return computerDAO.insertComputer(company, discontinued, introduced, name);
	}

	@Override
	public void deleteComputer(int id) {

		ComputerValidator.checkId(id);
		computerDAO.deleteComputer(id);
	}

	@Override
	public void deleteComputerAssociatedToCompany(int companyId, Connection connection) {

		CompanyValidator.checkId(companyId);
		computerDAO.deleteComputersForCompanyId(companyId, connection);
		
	}
	
	@Override
	public Computer getById(int id) {

		ComputerValidator.checkId(id);
		return computerDAO.getById(id);
	}

	@Override
	public ArrayList<Computer> getByName(String name) {

		ComputerValidator.checkName(name);
		return computerDAO.getByName(name);
	}

	@Override
	public ArrayList<Computer> listComputers() {

		return computerDAO.listComputers();
	}

	@Override
	public ArrayList<Computer> selectWithParameters(QueryParameters queryParameters) {

		QueryParametersValidator.validateQueryParameters(queryParameters);

		return computerDAO.selectWithParameters(queryParameters);
	}

	@Override
	public int getCount(QueryParameters queryParameters) {
		return computerDAO.getCount(queryParameters);
	}

}
