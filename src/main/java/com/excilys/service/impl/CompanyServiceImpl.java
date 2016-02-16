package com.excilys.service.impl;

import java.util.ArrayList;

import com.excilys.dao.impl.CompanyDAOImpl;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;
import com.excilys.validator.CompanyValidator;

public class CompanyServiceImpl implements CompanyService{

	public static CompanyService INSTANCE;

	static 
	{
		INSTANCE = new CompanyServiceImpl();
	}
	
	public static CompanyService getInstance(){
		return INSTANCE;
	}
	
	private CompanyServiceImpl() {
		
	}

	@Override
	public Company getById(int id) {
		CompanyValidator.checkId(id);
		return CompanyDAOImpl.getInstance().getById(id);
	}

	@Override
	public ArrayList<Company> getByName(String name) {
		CompanyValidator.checkName(name);
		return CompanyDAOImpl.getInstance().getByName(name);
	}

	@Override
	public ArrayList<Company> listCompanies() {

		return CompanyDAOImpl.getInstance().listCompanies();
	}
	
}
