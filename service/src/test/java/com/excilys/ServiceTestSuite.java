package com.excilys;

import com.excilys.service.impl.CompanyServiceImplTest;
import com.excilys.service.impl.ComputerServiceImplTest;
import com.excilys.validator.CompanyValidatorTest;
import com.excilys.validator.ComputerValidatorTest;
import com.excilys.validator.QueryParamatersValidatorTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ComputerServiceImplTest.class, CompanyServiceImplTest.class,
    QueryParamatersValidatorTest.class, ComputerValidatorTest.class, CompanyValidatorTest.class })
public class ServiceTestSuite {

}
