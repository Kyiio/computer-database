package com.excilys;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.excilys.integration.SeleniumIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   SeleniumIT.class
})
public class SeleniumITSuite {

}
