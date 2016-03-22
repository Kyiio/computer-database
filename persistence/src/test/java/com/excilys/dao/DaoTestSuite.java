package com.excilys.dao;

import com.excilys.dao.impl.CompanyDaoImplTest;
import com.excilys.dao.impl.ComputerDaoImplTest;
import com.excilys.dao.impl.UserDaoImplTest;
import com.excilys.dao.util.QueryBuilderTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ComputerDaoImplTest.class, CompanyDaoImplTest.class, UserDaoImplTest.class,
    QueryBuilderTest.class })

public class DaoTestSuite {
}
