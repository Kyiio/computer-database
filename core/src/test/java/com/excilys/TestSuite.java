package com.excilys;

import com.excilys.validator.CompanyValidatorTest;
import com.excilys.validator.ComputerValidatorTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ComputerValidatorTest.class, CompanyValidatorTest.class })
public class TestSuite {

}
