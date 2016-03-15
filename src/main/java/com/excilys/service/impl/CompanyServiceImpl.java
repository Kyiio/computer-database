package com.excilys.service.impl;

import com.excilys.dao.CompanyDao;
import com.excilys.dao.ComputerDao;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;
import com.excilys.validator.CompanyValidator;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service("companyService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class CompanyServiceImpl implements CompanyService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

  @Autowired
  private CompanyDao  companyDao;

  @Autowired
  private ComputerDao computerDao;

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public Company getById(long id) {

    LOGGER.info("Get company by id: " + id);
    LOGGER.error("Is the factory closed ? : " + sessionFactory.isClosed());

    CompanyValidator.checkId(id);
    return companyDao.getById(id);
  }

  @Override
  public ArrayList<Company> getByName(String name) {

    LOGGER.info("Get company by name: " + name);
    LOGGER.error("Is the factory closed ? : " + sessionFactory.isClosed());

    CompanyValidator.checkName(name);
    return companyDao.getByName(name);
  }

  @Override
  public ArrayList<Company> listCompanies() {
    LOGGER.info("List companies: ");
    LOGGER.error("Is the factory closed ? : " + sessionFactory.isClosed());
    return companyDao.listCompanies();
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  public void deleteCompany(long id) {

    LOGGER.info("Delete company by id: " + id);
    LOGGER.error("Is the factory closed ? : " + sessionFactory.isClosed());
    CompanyValidator.checkId(id);

    // We first delete all the computers associated to the company that
    // we will delete
    computerDao.deleteComputersForCompanyId(id);

    // And finally we delete the company itself
    companyDao.deleteCompany(id);

  }

}
