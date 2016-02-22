package com.excilys;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.excilys.dao.DAOTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   DAOTestSuite.class
})
public class TestSuite {

}
