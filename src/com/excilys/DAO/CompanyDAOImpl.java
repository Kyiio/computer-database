package com.excilys.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.exception.DAOException;
import com.excilys.mapper.ResultsMapper;
import com.excilys.model.Company;

public class CompanyDAOImpl implements CompanyDAO{
	
	private final String LIST_ALL_QUERY  	= "SELECT * FROM company";
	private final String GET_BY_ID_QUERY 	= "SELECT * FROM company WHERE ID = ?";
	private final String GET_BY_NAME_QUERY 	= "SELECT * FROM company WHERE NAME = ?";

	private DAOFactory daoFactory;
	
	public CompanyDAOImpl() {
		
	}
	
	public CompanyDAOImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public Company getById(int id) {

		ResultSet results = null;
		Company companyResult = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = daoFactory.getConnection();
			
			preparedStatement = connection.prepareStatement(GET_BY_ID_QUERY);
			preparedStatement.setInt(1, id);
 			
			results = preparedStatement.executeQuery();
		
			ArrayList<Company> companyList = ResultsMapper.getCompanyList(results);
			
			if(companyList.size() > 0){
				companyResult = companyList.get(0);
			}
			
			
		} catch (SQLException e) {
			throw new DAOException(e);
			
		}finally {
			ConnectionCloser.fermeturesSilencieuses(results, preparedStatement, connection);
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
			connection = daoFactory.getConnection();
			
			preparedStatement = connection.prepareStatement(GET_BY_NAME_QUERY);
			preparedStatement.setString(1, name);
 			
			results = preparedStatement.executeQuery();
					
			companyResults = ResultsMapper.getCompanyList(results);
			
		} catch (SQLException e) {
			throw new DAOException(e);
			
		}finally {
			ConnectionCloser.fermeturesSilencieuses(results, preparedStatement, connection);
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
			connection = daoFactory.getConnection();
			
			preparedStatement = connection.prepareStatement(LIST_ALL_QUERY);

			results = preparedStatement.executeQuery();
					
			companyResults = ResultsMapper.getCompanyList(results);
			
		} catch (SQLException e) {
			throw new DAOException(e);
			
		}finally {
			ConnectionCloser.fermeturesSilencieuses(results, preparedStatement, connection);
		}	
		
		return companyResults;
	}

}
