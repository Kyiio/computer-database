package com.excilys.service.impl;

import com.excilys.dto.CompanyDto;
import com.excilys.dto.mapper.CompanyDtoMapper;
import com.excilys.model.Company;
import com.excilys.service.CompanyDtoService;
import com.excilys.service.CompanyService;
import com.excilys.validator.CompanyValidator;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("companyDtoService")
public class CompanyDtoServiceImpl implements CompanyDtoService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDtoServiceImpl.class);

  @Autowired
  private SessionFactory      sessionFactory;

  @Autowired
  public CompanyService       companyService;

  @Override
  public CompanyDto getById(long id) {

    LOGGER.info("DTO : Get by id :" + id);
    LOGGER.error("Is the factory closed ? : " + sessionFactory.isClosed());

    CompanyValidator.checkId(id);
    Company company = companyService.getById(id);

    return CompanyDtoMapper.getCompanyDto(company);
  }

  @Override
  public ArrayList<CompanyDto> getByName(String name) {
    LOGGER.info("DTO : Get by name :" + name);
    LOGGER.error("Is the factory closed ? : " + sessionFactory.isClosed());

    CompanyValidator.checkName(name);
    ArrayList<Company> companyList = companyService.getByName(name);

    return CompanyDtoMapper.getCompanyDtoList(companyList);
  }

  @Override
  public ArrayList<CompanyDto> listCompanies() {
    LOGGER.info("DTO : Get list :");
    LOGGER.error("Is the factory closed ? : " + sessionFactory.isClosed());

    ArrayList<Company> companyList = companyService.listCompanies();

    return CompanyDtoMapper.getCompanyDtoList(companyList);
  }
}
