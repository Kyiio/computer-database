package com.excilys.main;

import java.util.Calendar;

import com.excilys.DAO.ComputerDAO;
import com.excilys.DAO.DAOFactory;
import com.excilys.model.Computer;

public class Main {

	public static void main(String[] args) {
		
		ComputerDAO computerDAO = DAOFactory.getInstance().getComputerDao();
		System.out.println(computerDAO.listComputers());
		
		/*
		
		Computer computer1 = computerDAO.getById(574);
		System.out.println(computer1);
		
		String oldName = computer1.getName();
		
		computer1.setName("Toto");
		computer1.setIntroduced(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		computer1.setDiscontinued(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		computerDAO.updateComputer(computer1);
		System.out.println(computerDAO.getById(574));
		
		computer1.setName(oldName);
		computerDAO.updateComputer(computer1);
		
		computerDAO.deleteComputer(computer1.getId());
		
		computerDAO.insertComputer(computer1.getCompany(), computer1.getDiscontinued(), computer1.getIntroduced(), computer1.getName());
		System.out.println(computerDAO.listComputers());
		
		*/
				
		System.out.println();
	}
	
}
