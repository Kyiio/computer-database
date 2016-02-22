package com.excilys.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.dao.ComputerDAO;
import com.excilys.dao.DBTestConnector;
import com.excilys.dao.exception.DAOException;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerDAOImplTest {

	private static DBTestConnector cfm;
	private static ComputerDAO computerDAO;

	@BeforeClass
	public static void init() {

		cfm = new DBTestConnector();
		cfm.initSchema("config/db_test/Test_SCHEMA.sql");
		cfm.initDataSource();

		computerDAO = ComputerDAOImpl.getInstance();
	}

	@AfterClass
	public static void end() {
		cfm = null;
		computerDAO = null;
	}

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

	@Test
	public void testInsertComputer() throws Exception {

		int newId = computerDAO.insertComputer(null, null, null, "Toto's computer");

		ITable computerTable = cfm.getDatabaseTester().getConnection().createDataSet().getTable("computer");
		int id = Integer.valueOf(computerTable.getValue(computerTable.getRowCount() - 1, "ID").toString());
		String computerName = (String) computerTable.getValue(computerTable.getRowCount() - 1, "NAME");

		assertEquals("Toto's computer", computerName);
		assertEquals(newId, id);
	}

	@Test
	public void testInsertComputer2() throws Exception {

		int newId = computerDAO.insertComputer(new Company(1, "Company's name"), LocalDate.of(2000, 10, 10),
				LocalDate.of(2016, 10, 10), "Toto's computer");

		ITable computerTable = cfm.getDatabaseTester().getConnection().createDataSet().getTable("computer");
		int id = Integer.valueOf(computerTable.getValue(computerTable.getRowCount() - 1, "ID").toString());
		String computerName = (String) computerTable.getValue(computerTable.getRowCount() - 1, "NAME");

		assertEquals("Toto's computer", computerName);
		assertEquals(newId, id);
	}

	@Test(expected = DAOException.class)
	public void testInsertComputerNull() {
		computerDAO.insertComputer(null, null, null, null);
	}

	@Test(expected = DAOException.class)
	public void testInsertComputerNameEmpty() {
		computerDAO.insertComputer(null, null, null, "");
	}

	@Test
	public void testDeleteComputer() throws SQLException, Exception {

		IDataSet dataSet = cfm.getDatabaseTester().getConnection().createDataSet();
		int oldNumberOfLine = dataSet.getTable("computer").getRowCount();

		computerDAO.deleteComputer(1);

		assertEquals(oldNumberOfLine - 1, dataSet.getTable("computer").getRowCount());
	}

	@Test
	public void testDeleteComputerFakeId() throws SQLException, Exception {

		int oldNumberOfLine = -1;
		IDataSet dataSet = null;

		dataSet = cfm.getDatabaseTester().getConnection().createDataSet();
		oldNumberOfLine = dataSet.getTable("computer").getRowCount();

		computerDAO.deleteComputer(-200);

		assertEquals(oldNumberOfLine, dataSet.getTable("computer").getRowCount());
	}

	@Test
	public void testListComputer() throws DataSetException, Exception {

		ArrayList<Computer> listComputer = computerDAO.listComputers();
		assertEquals(listComputer.size(),
				cfm.readDataSet("src/test/resources/ComputerDAOImpl_dataset.xml").getTable("computer").getRowCount());
	}

	@Test
	public void testGetComputerById() {

		Computer computer = computerDAO.getById(1);
		assertEquals(computer.getId(), 1);
	}

	@Test
	public void testGetComputerByFakeId() {

		Computer computer = computerDAO.getById(-1);
		assertNull(computer);
	}

	@Test(expected = DAOException.class)
	public void testUpdateComputerNull() {
		computerDAO.updateComputer(null);
	}

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
