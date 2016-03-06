package com.excilys;

import com.excilys.integration.AddComputerIntegrationTest;
import com.excilys.integration.PageSizeAndPAgeNumberIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ PageSizeAndPAgeNumberIntegrationTest.class,
    AddComputerIntegrationTest.class })
public class SeleniumIntegrationTestSuite {

}
