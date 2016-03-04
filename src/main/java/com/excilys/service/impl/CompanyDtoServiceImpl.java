package com.excilys.service.impl;

import com.excilys.dto.CompanyDto;
import com.excilys.dto.mapper.CompanyDtoMapper;
import com.excilys.model.Company;
import com.excilys.service.CompanyDtoService;
import com.excilys.service.CompanyService;
import com.excilys.validator.CompanyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("companyDtoService")
public class CompanyDtoServiceImpl implements CompanyDtoService {

  @Autowired
  public CompanyService companyService;

  @Override
  public CompanyDto getById(long id) {
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
