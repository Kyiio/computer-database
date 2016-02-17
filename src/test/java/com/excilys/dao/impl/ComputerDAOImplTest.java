package com.excilys.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ConnectionFactory;
import com.excilys.dao.DBTestConnector;
import com.excilys.dao.exception.DAOException;
import com.excilys.model.Computer;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionFactory.class)
public class ComputerDAOImplTest {

	private static DBTestConnector cfm;
	private static ComputerDAO computerDAO;

	@BeforeClass
	public static void init() {

		cfm = new DBTestConnector();
		cfm.initConnection();
		cfm.initSchema("config/db_test/Test_SCHEMA.sql");
		cfm.initDataSource();

		computerDAO = ComputerDAOImpl.getInstance();
	}
	
	@AfterClass
	public static void end(){
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
	public void testInsertComputer() {

		int newId = computerDAO.insertComputer(null, null, null, "Toto's computer");

		try {

			ITable computerTable = cfm.getDatabaseTester().getConnection().createDataSet().getTable("computer");
			int id = Integer.valueOf(computerTable.getValue(computerTable.getRowCount() - 1, "ID").toString());
			String computerName = (String) computerTable.getValue(computerTable.getRowCount() - 1, "NAME");

			assertEquals("Toto's computer", computerName);
			assertEquals(newId, id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsertComputerNull() {

		try {
			computerDAO.insertComputer(null, null, null, null);
			assertTrue(false);
		} catch (DAOException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testDeleteComputer() {

		try {

			IDataSet dataSet = cfm.getDatabaseTester().getConnection().createDataSet();
			int oldNumberOfLine = dataSet.getTable("computer").getRowCount();
			
			computerDAO.deleteComputer(1);
			
			assertEquals(oldNumberOfLine-1, dataSet.getTable("computer").getRowCount());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteComputerFakeId() {

		int oldNumberOfLine = -1;
		IDataSet dataSet = null;
		
		try {
			dataSet = cfm.getDatabaseTester().getConnection().createDataSet();
			oldNumberOfLine = dataSet.getTable("computer").getRowCount();
			
			computerDAO.deleteComputer(-200);
			
		} catch (DAOException e) {
			try {
				assertEquals(oldNumberOfLine, dataSet.getTable("computer").getRowCount());
			} catch (DataSetException e1) {
				e1.printStackTrace();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	@Test
	public void testListComputer() {
		
		ArrayList<Computer> listComputer = computerDAO.listComputers();
		try {
			assertEquals(listComputer.size(), cfm.readDataSet("src/test/resources/ComputerDAOImpl_dataset.xml").getTable("computer").getRowCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetComputerById() {

		Computer computer = computerDAO.getById(1);
		assertEquals(computer.getId(),1);
	}

	@Test
	public void testGetComputerByFakeId() {

		Computer computer = computerDAO.getById(-1);
		assertNull(computer);
	}
	
	
	@Test
	public void testUpdateComputerNull() {

		try {
			computerDAO.updateComputer(null);
			assertTrue(false);
		} catch (DAOException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testUpdateComputer() {

		try {
			Computer computer = computerDAO.getById(1);
			Computer cp = new Computer(computer);
			
			computer.setName("new computer name");
			computerDAO.updateComputer(computer);
			
			computer = null;
			computer = computerDAO.getById(1);
			
			assertNotEquals(computer, cp);
		} catch (DAOException e) {
			assertTrue(true);
		}
	}
}
