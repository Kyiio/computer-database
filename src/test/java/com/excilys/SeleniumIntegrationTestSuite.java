package com.excilys;

import com.excilys.integration.AddComputerIntegrationTest;
import com.excilys.integration.PageSizeAndPageNumberIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ PageSizeAndPageNumberIntegrationTest.class,
    AddComputerIntegrationTest.class })
public class SeleniumIntegrationTestSuite {

}
