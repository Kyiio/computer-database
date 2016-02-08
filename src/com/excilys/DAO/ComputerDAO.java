package com.excilys.DAO;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public interface ComputerDAO{
	
	public void insertComputer(Company company, Timestamp discontinued, Timestamp introduced, String name);
	public void updateComputer(Computer computer);
	public void deleteComputer(int id);
	
	public Computer getById(int id);
	
	public ArrayList<Computer> getByName(String name);
	public ArrayList<Computer> listComputers();

}
