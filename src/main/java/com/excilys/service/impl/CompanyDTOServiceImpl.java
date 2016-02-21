package com.excilys.service.impl;

import java.util.ArrayList;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.mapper.CompanyDTOMapper;
import com.excilys.model.Company;
import com.excilys.service.CompanyDTOService;
import com.excilys.service.CompanyService;
import com.excilys.validator.CompanyValidator;

public class CompanyDTOServiceImpl implements CompanyDTOService {

	public static CompanyDTOService INSTANCE;
	public CompanyService companyService;
	
	static {
		INSTANCE = new CompanyDTOServiceImpl();
	}

	public static CompanyDTOService getInstance() {
		return INSTANCE;
	}

	private CompanyDTOServiceImpl() {
		companyService = CompanyServiceImpl.getInstance();
	}

	@Override
	public CompanyDTO getById(int id) {
		CompanyValidator.checkId(id);
		Company company = companyService.getById(id);

		return CompanyDTOMapper.getCompanyDTO(company);
	}

	@Override
	public ArrayList<CompanyDTO> getByName(String name) {
		CompanyValidator.checkName(name);
		ArrayList<Company> companyList = companyService.getByName(name);

		return CompanyDTOMapper.getCompanyDTOList(companyList);
	}

	@Override
	public ArrayList<CompanyDTO> listCompanies() {
		ArrayList<Company> companyList = companyService.listCompanies();

		return CompanyDTOMapper.getCompanyDTOList(companyList);
	}
}
