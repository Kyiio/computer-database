package com.excilys.DAO;

import java.util.ArrayList;

import com.excilys.model.Company;

public interface CompanyDAO{

	/*public void create(String name);
	public void update(int id, String name);
	public void delete(int id);*/
	
	public Company getById(int id);
	public ArrayList<Company> getByName(String name);
	public ArrayList<Company> listCompanies();
	
}
