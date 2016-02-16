package com.excilys.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.dao.impl.ComputerDAOImpl;
import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;
import com.excilys.servlets.DashboardServlet;
import com.excilys.validator.ComputerValidator;

public class ComputerServiceImpl implements ComputerService{

	public static ComputerService INSTANCE;

	static 
	{
		INSTANCE = new ComputerServiceImpl();
	}
	
	public static ComputerService getInstance(){
		return INSTANCE;
	}
	
	private ComputerServiceImpl() {
		
	}
	
	@Override
	public void updateComputer(Computer computer) {
		
		ComputerValidator.checkId(computer.getId());
		ComputerValidator.checkName(computer.getName());
		ComputerValidator.checkDateConsitency(computer.getIntroduced(), computer.getDiscontinued());
		
		ComputerDAOImpl.getInstance().updateComputer(computer);
	}

	@Override
	public int insertComputer(Company company, LocalDateTime introduced, LocalDateTime discontinued, String name) {
		
		ComputerValidator.checkName(name);
		ComputerValidator.checkDateConsitency(introduced, discontinued);
		
		return ComputerDAOImpl.getInstance().insertComputer(company, discontinued, introduced, name);
	}

	@Override
	public void deleteComputer(int id) {
		
		ComputerValidator.checkId(id);
		ComputerDAOImpl.getInstance().deleteComputer(id);
	}

	@Override
	public Computer getById(int id) {
		
		ComputerValidator.checkId(id);
		return ComputerDAOImpl.getInstance().getById(id);
	}

	@Override
	public ArrayList<Computer> getByName(String name) {
		
		ComputerValidator.checkName(name);
		return ComputerDAOImpl.getInstance().getByName(name);
	}

	@Override
	public ArrayList<Computer> listComputers() {
		
		return ComputerDAOImpl.getInstance().listComputers();
	}

	@Override
	public ArrayList<Computer> getXComputersStartingAtIndexY(int offset, int pageNumber) {
		
		ComputerValidator.checkOffset(offset);
		ComputerValidator.checkPageNumber(pageNumber);
				
		return ComputerDAOImpl.getInstance().getXComputersStartingAtIndexY(offset, pageNumber);
	}

	@Override
	public int getNbComputer() {
		return ComputerDAOImpl.getInstance().getNbComputer();
	}

}
