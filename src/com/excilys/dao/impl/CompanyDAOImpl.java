package com.excilys.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ConnectionCloser;
import com.excilys.dao.ConnectionFactory;
import com.excilys.exception.DAOException;
import com.excilys.mapper.ResultsMapper;
import com.excilys.model.Company;

public class CompanyDAOImpl implements CompanyDAO{
	
	private final String LIST_ALL_QUERY  	= "SELECT * FROM company";
	private final String GET_BY_ID_QUERY 	= "SELECT * FROM company WHERE ID = ?";
	private final String GET_BY_NAME_QUERY 	= "SELECT * FROM company WHERE NAME = ?";

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
	public Company getById(int id) {

		ResultSet results = null;
		Company companyResult = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = connectionFactory.getConnection();
			
			preparedStatement = connection.prepareStatement(GET_BY_ID_QUERY);
			preparedStatement.setInt(1, id);
 			
			results = preparedStatement.executeQuery();
		
			ArrayList<Company> companyList = ResultsMapper.getCompanyList(results);
			
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
	public ArrayList<Company> getByName(String name) {
		
		ResultSet results = null;
		Connection connection = null;
		ArrayList<Company> companyResults = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = connectionFactory.getConnection();
			
			preparedStatement = connection.prepareStatement(GET_BY_NAME_QUERY);
			preparedStatement.setString(1, name);
 			
			results = preparedStatement.executeQuery();
					
			companyResults = ResultsMapper.getCompanyList(results);
			
		} catch (SQLException e) {
			throw new DAOException("Error retrieving company with name " + name, e);
			
		}finally {
			ConnectionCloser.silentClose(results, preparedStatement, connection);
		}	
		
		return companyResults;

	}

	@Override
	public ArrayList<Company> listCompanies() {

		ResultSet results = null;
		Connection connection = null;
		ArrayList<Company> companyResults = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = connectionFactory.getConnection();
			
			preparedStatement = connection.prepareStatement(LIST_ALL_QUERY);

			results = preparedStatement.executeQuery();
					
			companyResults = ResultsMapper.getCompanyList(results);
			
		} catch (SQLException e) {
			throw new DAOException("Error retrieving company list", e);
			
		}finally {
			ConnectionCloser.silentClose(results, preparedStatement, connection);
		}	
		
		return companyResults;
	}

}
