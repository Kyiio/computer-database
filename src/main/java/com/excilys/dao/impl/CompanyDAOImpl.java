package com.excilys.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ConnectionCloser;
import com.excilys.dao.ConnectionFactory;
import com.excilys.dao.exception.DAOException;
import com.excilys.dao.mapper.CompanyDAOMapper;
import com.excilys.model.Company;

public class CompanyDAOImpl implements CompanyDAO{
	
	private final String LIST_ALL_QUERY  	= "SELECT * FROM company";
	private final String GET_BY_ID_QUERY 	= "SELECT * FROM company WHERE ID = ?";
	private final String GET_BY_NAME_QUERY 	= "SELECT * FROM company WHERE NAME = ?";
	
	private final String DELETE_QUERY = "DELETE FROM company WHERE ID=?";

	private static CompanyDAO INSTANCE;
	
	private ConnectionFactory connectionFactory;
	
	static{
		INSTANCE = new CompanyDAOImpl(ConnectionFactory.getInstance());
	}
	
	public static CompanyDAO getInstance(){
		return INSTANCE;
	}
	
	private CompanyDAOImpl(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	@Override
	public Company getById(int id) throws DAOException {

		ResultSet results = null;
		Company companyResult = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = connectionFactory.getConnection();
			
			preparedStatement = connection.prepareStatement(GET_BY_ID_QUERY);
			preparedStatement.setInt(1, id);
 			
			results = preparedStatement.executeQuery();
		
			ArrayList<Company> companyList = CompanyDAOMapper.getCompanyList(results);
			
			if(companyList.size() > 0){
				companyResult = companyList.get(0);
			}
			
			
		} catch (SQLException e) {
			throw new DAOException("Error retrieving company with id " + id, e);
			
		}finally {
			ConnectionCloser.silentClose(results, preparedStatement, connection);
		}	
		
		return companyResult;
	}

	@Override
	public ArrayList<Company> getByName(String name) throws DAOException {
		
		ResultSet results = null;
		Connection connection = null;
		ArrayList<Company> companyResults = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = connectionFactory.getConnection();
			
			preparedStatement = connection.prepareStatement(GET_BY_NAME_QUERY);
			preparedStatement.setString(1, name);
 			
			results = preparedStatement.executeQuery();
					
			companyResults = CompanyDAOMapper.getCompanyList(results);
			
		} catch (SQLException e) {
			throw new DAOException("Error retrieving company with name " + name, e);
			
		}finally {
			ConnectionCloser.silentClose(results, preparedStatement, connection);
		}	
		
		return companyResults;

	}

	@Override
	public ArrayList<Company> listCompanies() throws DAOException {

		ResultSet results = null;
		Connection connection = null;
		ArrayList<Company> companyResults = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = connectionFactory.getConnection();
			
			preparedStatement = connection.prepareStatement(LIST_ALL_QUERY);

			results = preparedStatement.executeQuery();
					
			companyResults = CompanyDAOMapper.getCompanyList(results);
			
		} catch (SQLException e) {
			throw new DAOException("Error retrieving company list", e);
			
		}finally {
			ConnectionCloser.silentClose(results, preparedStatement, connection);
		}	
		
		return companyResults;
	}

	@Override
	public void deleteCompany(int id, Connection connection) {

		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(DELETE_QUERY);
			preparedStatement.setInt(1, id);
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException("Error trying to delete the company with id : " + id, e);
		}
	}
	
}
