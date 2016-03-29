package com.excilys.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;

import com.excilys.dao.CompanyDao;
import com.excilys.dao.ComputerDao;
import com.excilys.dao.exception.DaoException;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;
import com.excilys.validator.exception.ValidationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:./service-context-test.xml" })
public class CompanyServiceImplTest {

  @Autowired
  private CompanyService companyService;

  @Autowired
  private CompanyDao     companyDao;

  @Autowired
  private ComputerDao    computerDao;

  /**
   * Inits the mocks.
   */
  @Before
  public void initMocks() {
    Mockito.when(companyDao.getById(anyLong())).thenReturn(new Company(1L, "Company's name"));
    Mockito.when(companyDao.getByName(anyString())).thenReturn(new ArrayList<Company>());
    Mockito.when(companyDao.listCompanies()).thenReturn(new ArrayList<Company>());

    Mockito.doNothing().when(computerDao).deleteComputersForCompanyId(anyLong());
  }

  @Test
  public void testGetById() {
    assertNotNull(companyService.getById(10L));
  }

  @Test(expected = ValidationException.class)
  public void testGetByIdFail() {

    Company company = companyService.getById(-10L);
    assertEquals("Company's name", company.getName());
  }

  @Test
  public void testGetByName() {

    assertNotNull(companyService.getByName("Company's name"));
  }

  @Test(expected = ValidationException.class)
  public void testGetByNameNull() {

    companyService.getByName(null);
  }

  @Test(expected = ValidationException.class)
  public void testGetByEmptyName() {

    companyService.getByName("");
  }

  @Test
  public void testListCompanies() {

    assertNotNull(companyService.listCompanies());
  }

  @Test
  public void testDeleteCompany() {

    Mockito.doNothing().when(companyDao).deleteCompany(anyLong());
    companyService.deleteCompany(1L);
  }

  @Test(expected = ValidationException.class)
  public void testDeleteCompanyFakeCompany() {
    Mockito.doNothing().when(companyDao).deleteCompany(anyLong());
    companyService.deleteCompany(-100L);
  }

  @Test(expected = DaoException.class)
  public void testDeleteCompanyWithException() {
    Mockito.doThrow(new DaoException("Fail test")).when(companyDao).deleteCompany(anyLong());
    companyService.deleteCompany(1L);
  }

}
