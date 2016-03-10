package com.excilys;

import com.excilys.integration.AddComputerIntegrationTest;
import com.excilys.integration.DeleteComputerIntegrationTest;
import com.excilys.integration.PageSizeAndPageNumberIntegrationTest;
import com.excilys.integration.SearchComputerIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ PageSizeAndPageNumberIntegrationTest.class, AddComputerIntegrationTest.class,
    DeleteComputerIntegrationTest.class, SearchComputerIntegrationTest.class })
public class SeleniumIntegrationTestSuite {

}
