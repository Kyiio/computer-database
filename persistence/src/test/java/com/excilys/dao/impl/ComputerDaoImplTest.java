package com.excilys.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.excilys.dao.ComputerDao;
import com.excilys.dao.exception.DaoException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.annotation.Resource;

/**
 * The Class ComputerDaoImplTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:./persistence-context-test.xml" })
@Transactional(transactionManager = "txManager")
@Rollback(true)
public class ComputerDaoImplTest {

  @Resource(name = "computerDao")
  private ComputerDao     computerDao;

  private QueryParameters queryParameter;

  /**
   * Inits the mocks.
   */
  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
    queryParameter = Mockito.mock(QueryParameters.class);
    Mockito.when(queryParameter.getSearch()).thenReturn("%");
    Mockito.when(queryParameter.getPageSize()).thenReturn(10);
    Mockito.when(queryParameter.getOffset()).thenReturn(0);
    Mockito.when(queryParameter.getByContent()).thenReturn("computer.id");
    Mockito.when(queryParameter.getOrder()).thenReturn("ASC");
  }

  /**
   * Test insert computer.
   *
   * @throws Exception the exception
   */
  @Test
  public void testInsertComputer() throws Exception {

    long newId = computerDao.insertComputer(null, null, null, "Toto's computer");

    Computer computer = computerDao.getById(newId);
    assertNotNull(computer);

    assertEquals("Toto's computer", computer.getName());
    assertEquals(newId, computer.getId());

    computerDao.deleteComputer(computer.getId());
  }

  /**
   * Test insert computer2.
   *
   * @throws Exception the exception
   */
  @Test
  public void testInsertComputer2() throws Exception {

    long newId = computerDao.insertComputer(new Company(1, "Apple Inc."),
        LocalDate.of(2000, 10, 10), LocalDate.of(2016, 10, 10), "Toto's computer");

    Computer computer = computerDao.getById(newId);

    assertNotNull(computer);
    assertEquals(computer.getName(), "Toto's computer");
    assertEquals(computer.getIntroduced(), LocalDate.of(2000, 10, 10));
    assertEquals(computer.getDiscontinued(), LocalDate.of(2016, 10, 10));
    assertEquals("Apple Inc.", computer.getCompany().getName());
    assertEquals(newId, computer.getId());

    computerDao.deleteComputer(computer.getId());
  }

  /**
   * Test insert computer null.
   */
  @Test(expected = DaoException.class)
  public void testInsertComputerNameNull() {
    computerDao.insertComputer(null, null, null, null);
  }

  /**
   * Test insert computer name empty.
   */
  @Test(expected = DaoException.class)
  public void testInsertComputerNameEmpty() {
    computerDao.insertComputer(null, null, null, "");
  }

  /**
   * Test delete computer.
   *
   * @throws SQLException the SQL exception
   * @throws Exception the exception
   */
  @Test
  public void testDeleteComputer() throws SQLException, Exception {

    final Computer computer = computerDao.getById(1);
    computerDao.deleteComputer(1);

    Computer computer2 = computerDao.getById(1);
    assertNull(computer2);

    computerDao.insertComputer(computer.getCompany(), computer.getDiscontinued(),
        computer.getIntroduced(), computer.getName());
  }

  /**
   * Test delete computer fake id.
   *
   * @throws SQLException the SQL exception
   * @throws Exception the exception
   */
  @Test
  public void testDeleteComputerFakeId() throws SQLException, Exception {

    long oldNumberOfLine = computerDao.getCount(queryParameter);

    computerDao.deleteComputer(-200);

    assertEquals(oldNumberOfLine, computerDao.getCount(queryParameter));
  }

  /**
   * Test update computer null.
   */
  @Test(expected = DaoException.class)
  public void testUpdateComputerNull() {
    computerDao.updateComputer(null);
  }

  /**
   * Test update computer.
   */

  @Test
  public void testUpdateComputer() {

    Computer computer = computerDao.getById(1);

    final Computer cp = new Computer(computer);

    computer.setName("new computer name");
    computerDao.updateComputer(computer);

    computer = null;
    computer = computerDao.getById(1);

    assertNotEquals(computer, cp);
  }

  /**
   * Test update computer null.
   */
  @Test
  public void testUpdateComputerWithNulls() {

    Computer computer = computerDao.getById(1);

    final Computer cp = new Computer(computer);

    computer.setName("new computer name");
    computer.setCompany(null);
    computer.setIntroduced(LocalDate.of(2000, 12, 12));
    computer.setDiscontinued(LocalDate.of(2010, 12, 12));

    computerDao.updateComputer(computer);

    computer = null;
    computer = computerDao.getById(1);

    assertNotEquals(computer, cp);
  }

  /**
   * Test list computer.
   *
   * @throws DataSetException the data set exception
   * @throws Exception the exception
   */
  @Test
  public void testListComputer() {

    ArrayList<Computer> listComputer = computerDao.listComputers();

    long nbComputerInDatabase = computerDao.getCount(queryParameter);

    assertEquals(listComputer.size(), nbComputerInDatabase);
  }

  /**
   * Test get computer by id.
   */
  @Test
  public void testGetComputerById() {

    Computer computer = computerDao.getById(1);
    assertEquals(computer.getId(), 1);
  }

  /**
   * Test get computer by fake id.
   */
  @Test
  public void testGetComputerByFakeId() {

    Computer computer = computerDao.getById(-1);
    assertNull(computer);
  }

  @Test
  public void testGetByName() {

    String computerName = "Toto's computer Unit Test";

    computerDao.insertComputer(new Company(1, "Apple Inc."), null, null, computerName);
    final Computer computer = computerDao.getByName(computerName).get(0);

    assertEquals(computer.getName(), computerName);
  }

  /**
   * Test select with paramaters.
   */
  @Test(expected = DaoException.class)
  public void testSelectWithParamatersNull() {
    computerDao.selectWithParameters(null);
  }

  @Test
  public void testSelectWithParamaters() {

    ArrayList<Computer> computerList = computerDao.selectWithParameters(queryParameter);

    assertEquals(computerList.size(), 10);
  }

  @Test
  public void testDeleteComputerByCompanyId() {

    String computerName = "Toto's computer Unit Test";

    Long id = computerDao.insertComputer(new Company(1, "Apple Inc."), null, null, computerName);

    computerDao.deleteComputersForCompanyId(1);

    Computer computer = computerDao.getById(id);

    assertNull(computer);
  }

  @Test(expected = DaoException.class)
  public void testDeleteComputerByNegativeCompanyId() {

    computerDao.deleteComputersForCompanyId(-100);
  }
}
