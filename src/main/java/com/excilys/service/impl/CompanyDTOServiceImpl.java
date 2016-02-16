package com.excilys.service.impl;

import java.util.ArrayList;

import com.excilys.dto.CompanyDTO;
import com.excilys.mapper.DTOMapper;
import com.excilys.model.Company;
import com.excilys.service.CompanyDTOService;
import com.excilys.validator.CompanyValidator;

public class CompanyDTOServiceImpl implements  CompanyDTOService{

	public static CompanyDTOService INSTANCE;

	static 
	{
		INSTANCE = new CompanyDTOServiceImpl();
	}
	
	public static CompanyDTOService getInstance(){
		return INSTANCE;
	}
	
	private CompanyDTOServiceImpl() {
		
	}
	
	@Override
	public CompanyDTO getById(int id) {
		CompanyValidator.checkId(id);
		Company company = CompanyServiceImpl.getInstance().getById(id);
		
		return new CompanyDTO(company);
	}

	@Override
	public ArrayList<CompanyDTO> getByName(String name) {
		CompanyValidator.checkName(name);
		ArrayList<Company> companyList = CompanyServiceImpl.getInstance().getByName(name);

		return DTOMapper.getCompanyDTOListFromCompanyList(companyList);
	}

	@Override
	public ArrayList<CompanyDTO> listCompanies() {
		ArrayList<Company> companyList = CompanyServiceImpl.getInstance().listCompanies();
		
		return DTOMapper.getCompanyDTOListFromCompanyList(companyList);
	}
}
