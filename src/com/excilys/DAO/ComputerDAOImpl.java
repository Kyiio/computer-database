package com.excilys.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excilys.exception.DAOException;
import com.excilys.mapper.ResultsMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerDAOImpl implements ComputerDAO{

	private final String INSERT_QUERY = "INSERT INTO computer(COMPANY_ID, DISCONTINUED, INTRODUCED, NAME) VALUES (?, ?, ?, ?);";
	private final String UPDATE_QUERY = "UPDATE computer SET COMPANY_ID=?, DISCONTINUED=?, INTRODUCED=?, NAME=? WHERE ID=?";
	private final String DELETE_QUERY = "DELETE FROM computer WHERE ID=?";
	
	private final String LIST_ALL_QUERY  	= "SELECT * FROM computer LEFT JOIN company ON COMPANY_ID = company.ID";
	private final String GET_BY_ID_QUERY 	= "SELECT * FROM computer LEFT JOIN company ON COMPANY_ID = company.ID WHERE computer.ID = ?";
	private final String GET_BY_NAME_QUERY 	= "SELECT * FROM computer LEFT JOIN company ON COMPANY_ID = company.ID WHERE computer.NAME = ?";
	
	private DAOFactory daoFactory;
	
	public ComputerDAOImpl() {
		
	}
	
	public ComputerDAOImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void checkData(String name, Timestamp introduced, Timestamp discontinued) throws DAOException{
		
		if(name == null || name.length() == 0){
			throw new DAOException("Computer's name must be set !");
		}
		
		if((introduced == null && discontinued != null) || (introduced != null && discontinued != null && introduced.after(discontinued))){
			throw new DAOException("Discontinued date should be set after Introduced date !");
		}
		
	}
	
	public void checkData(Computer computer) throws DAOException{
		
		checkData(computer.getName(), computer.getIntroduced(), computer.getDiscontinued());
		
	}
	
	@Override
	public void insertComputer(Company company, Timestamp introduced, Timestamp discontinued, String name) throws DAOException{
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		checkData(name, introduced, discontinued);
		
		try {
			connection = daoFactory.getConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(INSERT_QUERY);
			preparedStatement.setInt(1, company.getId());
			preparedStatement.setTimestamp(2, discontinued);
			preparedStatement.setTimestamp(3, introduced);
			preparedStatement.setString(4, name);
						
			preparedStatement.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException("Error rollback while inserting", e);
			}
			throw new DAOException("Error while inserting in Computer table", e);
			
		}finally {
			ConnectionCloser.fermeturesSilencieuses(preparedStatement, connection);
		}	
	}

	@Override
	public void updateComputer(Computer computer) throws DAOException{
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		checkData(computer);
				
		try {
			connection = daoFactory.getConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setInt(1, computer.getCompany().getId());
			preparedStatement.setTimestamp(2, computer.getDiscontinued());
			preparedStatement.setTimestamp(3, computer.getIntroduced());
			preparedStatement.setString(4, computer.getName());
			preparedStatement.setInt(5, computer.getId());
			
			preparedStatement.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException("Error rollback while updating", e);
			}
			throw new DAOException("Error while updating" + computer +" in Computer table",e);
			
		}finally {
			ConnectionCloser.fermeturesSilencieuses(preparedStatement, connection);
		}
		
	}

	@Override
	public void deleteComputer(int id) throws DAOException{
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = daoFactory.getConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(DELETE_QUERY);
			preparedStatement.setInt(1, id);
						
			preparedStatement.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new DAOException("Error rollback while deleting", e);
			}
			
			throw new DAOException("Error while trying to delete the computer with the folling id" + id + " from Computer table", e);
			
		}finally {
			ConnectionCloser.fermeturesSilencieuses(preparedStatement, connection);
		}
	}

	@Override
	public Computer getById(int id) {

		ResultSet results = null;
		Computer computerResult = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = daoFactory.getConnection();
			
			preparedStatement = connection.prepareStatement(GET_BY_ID_QUERY);
			preparedStatement.setInt(1, id);
 			
			results = preparedStatement.executeQuery();
			
			ArrayList<Computer> computerList = ResultsMapper.getComputerList(results);
			
			if(computerList.size() > 0){
				computerResult = computerList.get(0);
			}
			
		} catch (SQLException e) {
			throw new DAOException("Error while trying to retrieve computer with id :" + id, e);
			
		}finally {
			ConnectionCloser.fermeturesSilencieuses(results, preparedStatement, connection);
		}	
		
		return computerResult;
	}

	@Override
	public ArrayList<Computer> getByName(String name) {

		ResultSet results = null;
		ArrayList<Computer> computerResult = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = daoFactory.getConnection();
			
			preparedStatement = connection.prepareStatement(GET_BY_NAME_QUERY);
			preparedStatement.setString(1, name);
 			
			results = preparedStatement.executeQuery();
					
			computerResult = ResultsMapper.getComputerList(results);
			
		} catch (SQLException e) {
			throw new DAOException("Error while trying to retrieve computer with name :" + name, e);
			
		}finally {
			ConnectionCloser.fermeturesSilencieuses(results, preparedStatement, connection);
		}	
		
		return computerResult;
	}

	@Override
	public ArrayList<Computer> listComputers() {

		ResultSet results = null;
		ArrayList<Computer> computerResult = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = daoFactory.getConnection();
			
			preparedStatement = connection.prepareStatement(LIST_ALL_QUERY);
 			
			results = preparedStatement.executeQuery();
					
			computerResult = ResultsMapper.getComputerList(results);
			
		} catch (SQLException e) {
			throw new DAOException("Error while trying to retrieve the computer list", e);
			
		}finally {
			ConnectionCloser.fermeturesSilencieuses(results, preparedStatement, connection);
		}	
		
		return computerResult;
	}

}
