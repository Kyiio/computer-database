package com.excilys.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import com.excilys.dao.ComputerDao;
import com.excilys.dao.DbTestConnector;
import com.excilys.dao.exception.DaoException;
import com.excilys.model.Company;
import com.excilys.model.Computer;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
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

  /** The cfm. */
  private static DbTestConnector cfm;

  /** The computer dao. */
  private static ComputerDao     computerDAO;

  /**
   * Inits the.
   */
  @BeforeClass
  public static void init() {

    cfm = new DbTestConnector();
    cfm.initSchema("config/db_test/Test_SCHEMA.sql");
    cfm.initDataSource();

    computerDAO = ComputerDaoImpl.getInstance();
  }

  /**
   * End.
   */
  @AfterClass
  public static void end() {
    cfm = null;
    computerDAO = null;
  }

  /**
   * Import data set.
   */
  @Before
  public void importDataSet() {

    IDataSet dataSet;
    try {
      dataSet = cfm.readDataSet("src/test/resources/ComputerDAOImpl_dataset.xml");
      cfm.cleanlyInsert(dataSet);
    } catch (Exception e) {
      e.printStackTrace();
    }
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

    ITable computerTable =
        cfm.getDatabaseTester().getConnection().createDataSet().getTable("computer");
    int id =
        Integer.valueOf(computerTable.getValue(computerTable.getRowCount() - 1, "ID").toString());
    String computerName = (String) computerTable.getValue(computerTable.getRowCount() - 1, "NAME");

    assertEquals("Toto's computer", computerName);
    assertEquals(newId, id);
  }

  /**
   * Test insert computer2.
   *
   * @throws Exception
   *           the exception
   */
  @Test
  public void testInsertComputer2() throws Exception {

    int newId = computerDAO.insertComputer(new Company(1, "Company's name"),
        LocalDate.of(2000, 10, 10), LocalDate.of(2016, 10, 10), "Toto's computer");

    ITable computerTable =
        cfm.getDatabaseTester().getConnection().createDataSet().getTable("computer");
    int id =
        Integer.valueOf(computerTable.getValue(computerTable.getRowCount() - 1, "ID").toString());
    String computerName = (String) computerTable.getValue(computerTable.getRowCount() - 1, "NAME");

    assertEquals("Toto's computer", computerName);
    assertEquals(newId, id);
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

    IDataSet dataSet = cfm.getDatabaseTester().getConnection().createDataSet();
    int oldNumberOfLine = dataSet.getTable("computer").getRowCount();

    computerDAO.deleteComputer(1);

    assertEquals(oldNumberOfLine - 1, dataSet.getTable("computer").getRowCount());
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

    int oldNumberOfLine = -1;
    IDataSet dataSet = null;

    dataSet = cfm.getDatabaseTester().getConnection().createDataSet();
    oldNumberOfLine = dataSet.getTable("computer").getRowCount();

    computerDAO.deleteComputer(-200);

    assertEquals(oldNumberOfLine, dataSet.getTable("computer").getRowCount());
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
    assertEquals(listComputer.size(),
        cfm.readDataSet("src/test/resources/ComputerDAOImpl_dataset.xml").getTable("computer")
            .getRowCount());
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
    
    Computer cp = new Computer(computer);

    computer.setName("new computer name");
    computerDAO.updateComputer(computer);

    computer = null;
    computer = computerDAO.getById(1);

    assertNotEquals(computer, cp);
  }
}
