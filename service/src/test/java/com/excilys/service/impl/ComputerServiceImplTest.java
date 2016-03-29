package com.excilys.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;

import com.excilys.dao.ComputerDao;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;
import com.excilys.service.ComputerService;
import com.excilys.validator.exception.ValidationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:./service-context-test.xml" })
public class ComputerServiceImplTest {

  @Autowired
  private ComputerService computerService;

  @Autowired
  private ComputerDao     computerDao;

  private QueryParameters queryParameter;

  /**
   * Inits the mocks.
   */
  @Before
  public void initMocks() {

    // QueryParameters mock

    MockitoAnnotations.initMocks(this);
    queryParameter = Mockito.mock(QueryParameters.class);
    Mockito.when(queryParameter.getSearch()).thenReturn("%");
    Mockito.when(queryParameter.getPageSize()).thenReturn(10);
    Mockito.when(queryParameter.getPageNumber()).thenReturn(1);
    Mockito.when(queryParameter.getOffset()).thenReturn(0);
    Mockito.when(queryParameter.getByContent()).thenReturn("computer.id");
    Mockito.when(queryParameter.getOrder()).thenReturn("ASC");

    // ComputerDao mock

    Mockito.doNothing().when(computerDao).updateComputer(any(Computer.class));
    Mockito.doNothing().when(computerDao).deleteComputer(anyLong());
    Mockito.when(computerDao.getByName(anyString())).thenReturn(new ArrayList<Computer>());
    Mockito.when(computerDao.listComputers()).thenReturn(new ArrayList<Computer>());
    Mockito.when(computerDao.getCount(any(QueryParameters.class))).thenReturn(10L);
    Mockito.when(computerDao.selectWithParameters(any(QueryParameters.class)))
        .thenReturn(new ArrayList<Computer>());
    Mockito.when(computerDao.getById(anyLong()))
        .thenReturn(new Computer(1, null, "My computer", null, null));
    Mockito.when(computerDao.insertComputer(any(Company.class), any(LocalDate.class),
        any(LocalDate.class), anyString())).thenReturn(10L);
  }

  @Test
  public void testUpdate() {

    computerService.updateComputer(new Computer(1, null, "Computer's name", null, null));
  }

  @Test(expected = ValidationException.class)
  public void testUpdateNameNull() {

    computerService.updateComputer(new Computer(1, null, null, null, null));
  }

  @Test(expected = ValidationException.class)
  public void testUpdateEmptyName() {

    computerService.updateComputer(new Computer(1, null, "", null, null));
  }

  @Test(expected = ValidationException.class)
  public void testUpdateDateToOldName() {

    computerService
        .updateComputer(new Computer(1, null, "Computer's name", null, LocalDate.of(1700, 1, 1)));
  }

  @Test(expected = ValidationException.class)
  public void testInvalidIntroducedNotSpecifiedUpdate() {

    computerService
        .updateComputer(new Computer(1, null, "Computer's name", LocalDate.of(2001, 1, 1), null));
  }

  @Test(expected = ValidationException.class)
  public void testInvalidDatesNotConsistentUpdate() {

    computerService.updateComputer(new Computer(1, null, "Computer's name",
        LocalDate.of(2010, 1, 1), LocalDate.of(2015, 1, 1)));
  }

  @Test
  public void testInsert() {

    assertEquals(10L, computerService.insertComputer(null, null, null, "Computer's name"));
  }

  @Test
  public void testDelete() {

    computerService.deleteComputer(1);
  }

  @Test(expected = ValidationException.class)
  public void testDeleteFail() {

    computerService.deleteComputer(-1);
  }

  @Test
  public void testGetById() {

    Computer computer = computerService.getById(100);

    assertEquals("My computer", computer.getName());
  }

  @Test(expected = ValidationException.class)
  public void testGetByFakeId() {

    computerService.getById(-100);
  }

  @Test(expected = ValidationException.class)
  public void testGetByNameFail() {

    computerService.getByName("");
  }

  @Test(expected = ValidationException.class)
  public void testGetByNameNull() {

    computerService.getByName(null);
  }

  @Test
  public void testGetByName() {

    assertNotNull(computerService.getByName("A name"));
  }

  @Test
  public void testListComputer() {

    assertNotNull(computerService.listComputers());
  }

  @Test
  public void testGetCount() {

    assertEquals(10L, computerService.getCount(queryParameter));
  }

  @Test(expected = ValidationException.class)
  public void testGetCountFail() {

    computerService.getCount(null);
  }

  @Test
  public void testSelect() {

    assertNotNull(computerService.selectWithParameters(queryParameter));
  }

  @Test(expected = ValidationException.class)
  public void testGetSelectFail() {

    computerService.selectWithParameters(null);
  }
}
