package com.excilys.validator;

import com.excilys.model.Computer;
import com.excilys.validator.exception.ValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:./core-context-test.xml" })
public class ComputerValidatorTest {

  @Resource(name = "computerValidator")
  private ComputerValidator computerValidator;

  @Test(expected = ValidationException.class)
  public void checkComputerNull() {
    computerValidator.checkComputer(null);
  }

  @Test
  public void checkComputerValid() {
    Computer computer =
        new Computer(1, null, "Toto", LocalDate.of(2015, 10, 10), LocalDate.of(2010, 10, 10));

    computerValidator.checkComputer(computer);
  }

  @Test(expected = ValidationException.class)
  public void checkComputerInvalidId() {
    Computer computer =
        new Computer(-1, null, "Toto", LocalDate.of(2015, 10, 10), LocalDate.of(2010, 10, 10));
    computerValidator.checkComputer(computer);
  }

  @Test(expected = ValidationException.class)
  public void checkComputerInvalidNameEmpty() {
    Computer computer =
        new Computer(1, null, "", LocalDate.of(2015, 10, 10), LocalDate.of(2010, 10, 10));
    computerValidator.checkComputer(computer);
  }

  @Test(expected = ValidationException.class)
  public void checkComputerInvalidNameNull() {
    Computer computer = new Computer(1, null,
        "Un nom trop long ......................................."
            + "........................................................................"
            + "........................................................................."
            + "............................",
        LocalDate.of(2015, 10, 10), LocalDate.of(2010, 10, 10));
    computerValidator.checkComputer(computer);
  }

  @Test(expected = ValidationException.class)
  public void checkComputerInvalidNameToLong() {
    Computer computer =
        new Computer(1, null, null, LocalDate.of(2015, 10, 10), LocalDate.of(2010, 10, 10));
    computerValidator.checkComputer(computer);
  }

  @Test(expected = ValidationException.class)
  public void checkComputerInvalidDateConsistency() {
    Computer computer =
        new Computer(1, null, "Toto", LocalDate.of(2005, 10, 10), LocalDate.of(2010, 10, 10));
    computerValidator.checkComputer(computer);
  }

  @Test(expected = ValidationException.class)
  public void checkComputerInvalidDateIntriducedMissing() {
    Computer computer = new Computer(1, null, "Toto", LocalDate.of(2005, 10, 10), null);
    computerValidator.checkComputer(computer);
  }

  @Test
  public void checkDateIsntToOld() {
    computerValidator.checkDateIsntToOld(LocalDate.of(2015, 12, 10));
  }

  @Test
  public void checkDateIsntToOldWithNull() {
    computerValidator.checkDateIsntToOld(null);
  }

  @Test(expected = ValidationException.class)
  public void checkDateIsntToOldFail() {
    computerValidator.checkDateIsntToOld(LocalDate.of(1800, 12, 10));
  }

}
