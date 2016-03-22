package com.excilys.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import com.excilys.dao.CompanyDao;
import com.excilys.dao.ComputerDao;
import com.excilys.dao.exception.DaoException;
import com.excilys.model.Company;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:./persistence-context-test.xml" })
@Transactional(transactionManager = "txManager")
@Rollback(true)
public class CompanyDaoImplTest {

  @Resource(name = "companyDao")
  private CompanyDao  companyDao;

  @Resource(name = "computerDao")
  private ComputerDao computerDao;

  @Test
  public void testGetById() {
    Company company = companyDao.getById(1);
    assertEquals(1, company.getId());
  }

  @Test
  public void testGetByFakeId() {
    Company company = companyDao.getById(-10);
    assertNull(company);
  }

  @Test
  public void testGetByName() {
    Company company = companyDao.getById(1);

    ArrayList<Company> companies = companyDao.getByName(company.getName());

    assertNotEquals(0, companies.size());
  }

  @Test(expected = DaoException.class)
  public void testDeleteCompanyFail() {
    companyDao.deleteCompany(1);
    companyDao.getById(1);
  }

  @Test
  public void testDeleteCompany() {
    computerDao.deleteComputersForCompanyId(1);
    companyDao.deleteCompany(1);

    final Company company = companyDao.getById(1);

    assertNull(company);
  }

  @Test
  public void testListCompanies() {
    ArrayList<Company> companies = companyDao.listCompanies();
    assertNotEquals(0, companies.size());
  }
}
