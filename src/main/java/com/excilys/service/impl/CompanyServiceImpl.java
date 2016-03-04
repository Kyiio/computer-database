package com.excilys.service.impl;

import com.excilys.dao.CompanyDao;
import com.excilys.dao.ComputerDao;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;
import com.excilys.validator.CompanyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

  @Autowired
  private CompanyDao  companyDao;

  @Autowired
  private ComputerDao computerDao;

  @Override
  public Company getById(long id) {
    CompanyValidator.checkId(id);
    return companyDao.getById(id);
  }

  @Override
  public ArrayList<Company> getByName(String name) {
    CompanyValidator.checkName(name);
    return companyDao.getByName(name);
  }

  @Override
  public ArrayList<Company> listCompanies() {

    return companyDao.listCompanies();
  }

  @Override
  @Transactional(readOnly = false)
  public void deleteCompany(long id) {

    CompanyValidator.checkId(id);

    // We first delete all the computers associated to the company that
    // we will delete
    computerDao.deleteComputersForCompanyId(id);

    // And finally we delete the company itself
    companyDao.deleteCompany(id);

  }

}
