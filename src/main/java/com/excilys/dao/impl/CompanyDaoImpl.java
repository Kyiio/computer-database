package com.excilys.dao.impl;

import com.excilys.dao.CompanyDao;
import com.excilys.dao.mapper.CompanyDaoMapper;
import com.excilys.model.Company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository("companyDao")
public class CompanyDaoImpl implements CompanyDao {

  private static final String LIST_ALL_QUERY    = "SELECT * FROM company";
  private static final String GET_BY_ID_QUERY   = "SELECT * FROM company WHERE ID = ?";
  private static final String GET_BY_NAME_QUERY = "SELECT * FROM company WHERE NAME = ?";

  private static final String DELETE_QUERY      = "DELETE FROM company WHERE ID=?";

  @Autowired
  private JdbcTemplate        jdbcTemplate;
  private CompanyDaoMapper    companyDaoMapper  = new CompanyDaoMapper();

  @Override
  public Company getById(long id) {

    Company companyResult = null;

    ArrayList<Company> companyList =
        (ArrayList<Company>) jdbcTemplate.query(GET_BY_ID_QUERY, companyDaoMapper, id);

    if (companyList.size() > 0) {
      companyResult = companyList.get(0);
    }

    return companyResult;
  }

  @Override
  public ArrayList<Company> getByName(String name) {

    ArrayList<Company> companyResults = null;

    companyResults =
        (ArrayList<Company>) jdbcTemplate.query(GET_BY_NAME_QUERY, companyDaoMapper, name);

    return companyResults;
  }

  @Override
  public ArrayList<Company> listCompanies() {

    ArrayList<Company> companyResults = null;

    companyResults = (ArrayList<Company>) jdbcTemplate.query(LIST_ALL_QUERY, companyDaoMapper);

    return companyResults;
  }

  @Override
  public void deleteCompany(long id) {

    jdbcTemplate.update(DELETE_QUERY);
  }

}
