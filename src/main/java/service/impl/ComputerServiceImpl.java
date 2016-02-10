package service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.dao.impl.ComputerDAOImpl;
import com.excilys.model.Company;
import com.excilys.model.Computer;

import service.ComputerService;

public class ComputerServiceImpl extends ComputerService{

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
		
		checkData(computer);
		ComputerDAOImpl.getInstance().updateComputer(computer);
	}

	@Override
	public int insertComputer(Company company, LocalDateTime introduced, LocalDateTime discontinued, String name) {
		
		checkData(name, introduced, discontinued);
		return ComputerDAOImpl.getInstance().insertComputer(company, discontinued, introduced, name);
	}

	@Override
	public void deleteComputer(int id) {
		
		ComputerDAOImpl.getInstance().deleteComputer(id);
	}

	@Override
	public Computer getById(int id) {

		return ComputerDAOImpl.getInstance().getById(id);
	}

	@Override
	public ArrayList<Computer> getByName(String name) {

		return ComputerDAOImpl.getInstance().getByName(name);
	}

	@Override
	public ArrayList<Computer> listComputers() {
		
		return ComputerDAOImpl.getInstance().listComputers();
	}

}
