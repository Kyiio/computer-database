package com.excilys.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.dao.ConnectionManager;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;
import com.excilys.service.impl.ComputerServiceImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * The Class AddComputerIntegrationTest.
 */
public class AddComputerIntegrationTest {

  private WebDriver                driver;
  private String                   baseUrl;
  private boolean                  acceptNextAlert    = true;
  private StringBuffer             verificationErrors = new StringBuffer();

  private static ComputerService   computerServiceDao = ComputerServiceImpl.getInstance();
  private static ConnectionManager connectionManager;

  /**
   * Inits the.
   */
  @BeforeClass
  public static void init() {
    computerServiceDao = ComputerServiceImpl.getInstance();
    connectionManager = ConnectionManager.getInstance();
  }

  /**
   * End.
   */
  @AfterClass
  public static void end() {
    connectionManager = null;
  }

  /**
   * Sets the up.
   *
   * @throws Exception
   *           the exception
   */
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:6060/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    connectionManager.startTransaction();
  }

  /**
   * After.
   */
  @After
  public void after() {
    connectionManager.rollback();
    connectionManager.closeConnection();

    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  /**
   * Integration test : Add computer feature.
   *
   * @throws Exception
   *           the exception
   */
  @Test
  public void addComputer() throws Exception {
    driver.get(baseUrl + "/computerdatabase/dashboard?computer-per-page=10&page-number=1&order-by="
        + "&order-type=&search-name=");
    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.id("computerName")).clear();
    driver.findElement(By.id("computerName")).sendKeys("Toto integration test");
    driver.findElement(By.id("computerName")).clear();

    // We check that the error message regarding the computer's name shows up

    String errorText = driver.findElement(By.id("computerNameErr")).getText();
    assertTrue(errorText.matches("^The computer name must be specified !$"));

    driver.findElement(By.id("computerName")).sendKeys("Toto integration test");

    // We check that we can't put a discontinued date if we don't have set the introduced date

    driver.findElement(By.id("discontinued")).clear();
    driver.findElement(By.id("discontinued")).sendKeys("2016-10-01");

    errorText = driver.findElement(By.id("introducedErr")).getText();
    assertTrue(errorText
        .matches("^The introduced value must be specified if you put the discontinued one !$"));

    errorText = driver.findElement(By.id("discontinuedErr")).getText();
    assertTrue(errorText.matches(
        "^You can't specify the discontinued date if you don't set the introduced one !$"));

    // We check that the discontinued date must be set after the introduced date

    driver.findElement(By.id("introduced")).clear();
    driver.findElement(By.id("introduced")).sendKeys("2017-12-02");

    errorText = driver.findElement(By.id("introducedErr")).getText();
    assertTrue(
        errorText.matches("^The introduced value must be set before the discontinued one !$"));

    errorText = driver.findElement(By.id("discontinuedErr")).getText();
    assertTrue(
        errorText.matches("^The discontinued value must be set after the introduced one !$"));

    // We check that all is fine with the current dates

    driver.findElement(By.id("introduced")).clear();
    driver.findElement(By.id("introduced")).sendKeys("2015-12-02");

    errorText = driver.findElement(By.id("introducedErr")).getText();
    assertTrue(errorText.matches("^$"));

    errorText = driver.findElement(By.id("discontinuedErr")).getText();
    assertTrue(errorText.matches("^$"));

    /*
     * We set the company & submit the form, if all is fine no exception is thrown and we are
     * redirected to the dashboard
     */
    new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Netronics");
    driver.findElement(By.id("submit")).click();

    // We then try to find the newly introduced computer in the database

    ArrayList<Computer> computerList = computerServiceDao.getByName("Toto integration test");

    assertNotEquals(0, computerList.size());

    // And we check the insertion went well for all fields

    Computer computer = computerList.get(0);

    assertNotNull(computer);
    assertEquals("Toto integration test", computer.getName());
    assertEquals(LocalDate.of(2015, 12, 2), computer.getIntroduced());
    assertEquals(LocalDate.of(2016, 10, 1), computer.getDiscontinued());
    assertEquals("Netronics", computer.getCompany().getName());

    // then we delete the computer so that the database remains unchanged

    computerServiceDao.deleteComputer(computer.getId());
  }

  /*
   * driver.findElement(By.id("search-name")).clear();
   * driver.findElement(By.id("search-name")).sendKeys("Toto integration test");
   * driver.findElement(By.id("searchsubmit")).click();
   * driver.findElement(By.id("editComputer")).click();
   * driver.findElement(By.xpath("(//input[@name='cb'])[2]")).click();
   * driver.findElement(By.xpath("//a[@id='deleteSelected']/i")).click();
   * assertTrue(closeAlertAndGetItsText() .matches(
   * "^Are you sure you want to delete the selected computers[\\s\\S]$"));
   * 
   */

  /**
   * Close alert and get its text.
   *
   * @return the string
   */
  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
