package com.excilys.dao;

import java.io.File;
import java.nio.charset.Charset;
import java.sql.SQLException;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

public class DBTestConnector {
	
	private ConnectionFactory connectionFactory;
	private IDatabaseTester databaseTester;
	private JdbcDataSource dataSource;
	
	

	public void initDataSource(){
		
        dataSource = new JdbcDataSource();
		dataSource.setURL(connectionFactory.getUrl());
		dataSource.setUser(connectionFactory.getUsername());
		dataSource.setPassword(connectionFactory.getPassword());
	}
	
	public void initSchema(String pathToSqlSchema){
		
		try {
			RunScript.execute(connectionFactory.getUrl(), connectionFactory.getUsername(), connectionFactory.getPassword(), pathToSqlSchema, Charset.forName("UTF8"), false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void initConnection(){
		connectionFactory = ConnectionFactory.getInstance();
	}
		
	public void cleanlyInsert(IDataSet dataSet) throws Exception {
		
		databaseTester = new JdbcDatabaseTester(connectionFactory.getDriver(), connectionFactory.getUrl(), connectionFactory.getUsername(), connectionFactory.getPassword());
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}

	public IDataSet readDataSet(String pathToTheFile) throws Exception {
		
		return new FlatXmlDataSetBuilder().build(new File(pathToTheFile));
	}
	
	
	public IDatabaseTester getDatabaseTester(){
		
		return databaseTester;
	}	
}
