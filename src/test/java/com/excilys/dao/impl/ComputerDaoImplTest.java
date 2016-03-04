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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The Class ComputerDaoImplTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:./testApplicationContext.xml" })
@Transactional(transactionManager = "txManager")
@Rollback(true)
public class ComputerDaoImplTest {

  @Autowired
  private ComputerDao computerDao;

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
    assertEquals(computer.getCompany().getName(), "Apple Inc.");
    assertEquals(newId, computer.getId());

    computerDao.deleteComputer(computer.getId());
  }

  /**
   * Test insert computer null.
   */
  @Test(expected = DaoException.class)
  public void testInsertComputerNull() {
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

    QueryParameters queryParameters = new QueryParameters();
    long oldNumberOfLine = computerDao.getCount(queryParameters);

    final Computer computer = computerDao.getById(1);
    computerDao.deleteComputer(1);

    assertEquals(oldNumberOfLine - 1, computerDao.getCount(queryParameters));

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

    QueryParameters queryParameters = new QueryParameters();
    long oldNumberOfLine = computerDao.getCount(queryParameters);

    computerDao.deleteComputer(-200);

    assertEquals(oldNumberOfLine, computerDao.getCount(queryParameters));
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
   * Test list computer.
   *
   * @throws DataSetException the data set exception
   * @throws Exception the exception
   */
  @Test
  public void testListComputer() throws Exception {

    ArrayList<Computer> listComputer = computerDao.listComputers();

    QueryParameters queryParameters = new QueryParameters();
    long nbComputerInDatabase = computerDao.getCount(queryParameters);

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
}
