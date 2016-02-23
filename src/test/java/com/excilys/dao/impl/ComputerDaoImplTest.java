package com.excilys.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.excilys.dao.ComputerDao;
import com.excilys.dao.ConnectionManager;
import com.excilys.dao.exception.DaoException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QueryParameters;

import org.dbunit.dataset.DataSetException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The Class ComputerDaoImplTest.
 */
public class ComputerDaoImplTest {

  private static ComputerDao       computerDAO;
  private static ConnectionManager connectionManager;

  /**
   * Inits the.
   */
  @BeforeClass
  public static void init() {
    computerDAO = ComputerDaoImpl.getInstance();
    connectionManager = ConnectionManager.getInstance();
  }

  /**
   * End.
   */
  @AfterClass
  public static void end() {
    computerDAO = null;
    connectionManager = null;
  }

  @Before
  public void before() {
    connectionManager.startTransaction();
  }

  @After
  public void after() {
    connectionManager.rollback();
    connectionManager.closeConnection();
  }

  /**
   * Test insert computer.
   *
   * @throws Exception
   *           the exception
   */
  @Test
  public void testInsertComputer() throws Exception {

    int newId = computerDAO.insertComputer(null, null, null, "Toto's computer");

    Computer computer = computerDAO.getById(newId);
    assertNotNull(computer);

    assertEquals("Toto's computer", computer.getName());
    assertEquals(newId, computer.getId());

    computerDAO.deleteComputer(computer.getId());
  }

  /**
   * Test insert computer2.
   *
   * @throws Exception
   *           the exception
   */
  @Test
  public void testInsertComputer2() throws Exception {

    int newId = computerDAO.insertComputer(new Company(1, "Apple Inc."), LocalDate.of(2000, 10, 10),
        LocalDate.of(2016, 10, 10), "Toto's computer");

    Computer computer = computerDAO.getById(newId);

    assertNotNull(computer);
    assertEquals(computer.getName(), "Toto's computer");
    assertEquals(computer.getIntroduced(), LocalDate.of(2000, 10, 10));
    assertEquals(computer.getDiscontinued(), LocalDate.of(2016, 10, 10));
    assertEquals(computer.getCompany().getName(), "Apple Inc.");
    assertEquals(newId, computer.getId());

    computerDAO.deleteComputer(computer.getId());
  }

  /**
   * Test insert computer null.
   */
  @Test(expected = DaoException.class)
  public void testInsertComputerNull() {
    computerDAO.insertComputer(null, null, null, null);
  }

  /**
   * Test insert computer name empty.
   */
  @Test(expected = DaoException.class)
  public void testInsertComputerNameEmpty() {
    computerDAO.insertComputer(null, null, null, "");
  }

  /**
   * Test delete computer.
   *
   * @throws SQLException
   *           the SQL exception
   * @throws Exception
   *           the exception
   */
  @Test
  public void testDeleteComputer() throws SQLException, Exception {

    QueryParameters queryParameters = new QueryParameters();
    int oldNumberOfLine = computerDAO.getCount(queryParameters);

    final Computer computer = computerDAO.getById(1);
    computerDAO.deleteComputer(1);

    assertEquals(oldNumberOfLine - 1, computerDAO.getCount(queryParameters));

    Computer computer2 = computerDAO.getById(1);
    assertNull(computer2);

    computerDAO.insertComputer(computer.getCompany(), computer.getDiscontinued(),
        computer.getIntroduced(), computer.getName());
  }

  /**
   * Test delete computer fake id.
   *
   * @throws SQLException
   *           the SQL exception
   * @throws Exception
   *           the exception
   */
  @Test
  public void testDeleteComputerFakeId() throws SQLException, Exception {

    QueryParameters queryParameters = new QueryParameters();
    int oldNumberOfLine = computerDAO.getCount(queryParameters);

    computerDAO.deleteComputer(-200);

    assertEquals(oldNumberOfLine, computerDAO.getCount(queryParameters));
  }

  /**
   * Test list computer.
   *
   * @throws DataSetException
   *           the data set exception
   * @throws Exception
   *           the exception
   */
  @Test
  public void testListComputer() throws DataSetException, Exception {

    ArrayList<Computer> listComputer = computerDAO.listComputers();

    QueryParameters queryParameters = new QueryParameters();
    int nbComputerInDatabase = computerDAO.getCount(queryParameters);

    assertEquals(listComputer.size(), nbComputerInDatabase);
  }

  /**
   * Test get computer by id.
   */
  @Test
  public void testGetComputerById() {

    Computer computer = computerDAO.getById(1);
    assertEquals(computer.getId(), 1);
  }

  /**
   * Test get computer by fake id.
   */
  @Test
  public void testGetComputerByFakeId() {

    Computer computer = computerDAO.getById(-1);
    assertNull(computer);
  }

  /**
   * Test update computer null.
   */
  @Test(expected = DaoException.class)
  public void testUpdateComputerNull() {
    computerDAO.updateComputer(null);
  }

  /**
   * Test update computer.
   */

  @Test

  public void testUpdateComputer() {

    Computer computer = computerDAO.getById(1);

    final Computer cp = new Computer(computer);

    computer.setName("new computer name");
    computerDAO.updateComputer(computer);

    computer = null;
    computer = computerDAO.getById(1);

    assertNotEquals(computer, cp);
  }
}
