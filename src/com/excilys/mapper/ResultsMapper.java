package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ResultsMapper {

	public static ArrayList<Company> getCompanyList(ResultSet resultSet){
		
		ArrayList<Company> convertedResults = new ArrayList<Company>();
		
		try {
			while (resultSet.next()) {
				convertedResults.add(new Company(resultSet.getInt("ID"),
						resultSet.getString("NAME")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return convertedResults;
	}
	
	public static ArrayList<Computer> getComputerList(ResultSet resultSet){
		
		ArrayList<Computer> convertedResults = new ArrayList<Computer>();
		
		try {
			while (resultSet.next()) {
				convertedResults.add(new Computer(resultSet.getInt("computer.ID"),
						new Company(resultSet.getInt("company.ID"), resultSet.getString("company.NAME")),
						resultSet.getString("computer.NAME"),
						resultSet.getTimestamp("DISCONTINUED"),
						resultSet.getTimestamp("INTRODUCED")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return convertedResults;
	}
	
}
