package com.excilys.service.impl;

import com.excilys.dto.CompanyDto;
import com.excilys.dto.mapper.CompanyDtoMapper;
import com.excilys.model.Company;
import com.excilys.service.CompanyDtoService;
import com.excilys.service.CompanyService;
import com.excilys.validator.CompanyValidator;

import java.util.ArrayList;

public class CompanyDtoServiceImpl implements CompanyDtoService {

  public static CompanyDtoService INSTANCE;
  public CompanyService           companyService;

  static {
    INSTANCE = new CompanyDtoServiceImpl();
  }

  public static CompanyDtoService getInstance() {
    return INSTANCE;
  }

  private CompanyDtoServiceImpl() {
    companyService = CompanyServiceImpl.getInstance();
  }

  @Override
  public CompanyDto getById(int id) {
    CompanyValidator.checkId(id);
    Company company = companyService.getById(id);

    return CompanyDtoMapper.getCompanyDto(company);
  }

  @Override
  public ArrayList<CompanyDto> getByName(String name) {
    CompanyValidator.checkName(name);
    ArrayList<Company> companyList = companyService.getByName(name);

    return CompanyDtoMapper.getCompanyDtoList(companyList);
  }

  @Override
  public ArrayList<CompanyDto> listCompanies() {
    ArrayList<Company> companyList = companyService.listCompanies();

    return CompanyDtoMapper.getCompanyDtoList(companyList);
  }
}
