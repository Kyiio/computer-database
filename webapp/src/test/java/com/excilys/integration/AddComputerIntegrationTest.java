package com.excilys.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.model.Computer;
import com.excilys.service.ComputerService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * The Class AddComputerIntegrationTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:./webapp-context.xml" })
public class AddComputerIntegrationTest {

  private WebDriver       driver;
  private String          baseUrl;
  private StringBuffer    verificationErrors = new StringBuffer();

  private final String    computerName       = "Toto integration test";

  @Resource(name = "computerService")
  private ComputerService computerService;

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://127.0.0.1:6060";
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    driver.manage().window().maximize();

    driver.get(baseUrl + "/computerdatabase/login");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("admin");
    driver.findElement(By.id("passwd")).clear();
    driver.findElement(By.id("passwd")).sendKeys("admin");
    driver.findElement(By.name("submit")).click();
  }

  /**
   * After.
   */
  @After
  public void after() {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }

    ArrayList<Computer> computerList = computerService.getByName(computerName);

    for (Computer computer : computerList) {
      computerService.deleteComputer(computer.getId());
    }
  }

  /**
   * Integration test : Add computer feature.
   *
   * @throws Exception the exception
   */
  @Test
  public void addComputerEnglish() throws Exception {

    driver.get(baseUrl + "/computerdatabase/computers?computer-per-page=10&page-number=1&lang=en");
    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.id("computerName")).clear();
    driver.findElement(By.id("computerName")).sendKeys(computerName);
    driver.findElement(By.id("computerName")).clear();

    // We check that the error message regarding the computer's name shows up

    checkText("computerNameErr",
        "The computer name is empty or is to long (more than 100 characters)!");

    driver.findElement(By.id("computerName")).sendKeys(computerName);

    // We check that we can't put a discontinued date if we don't have set
    // the introduced date

    driver.findElement(By.id("discontinuedDate")).clear();
    driver.findElement(By.id("discontinuedDate")).sendKeys("10-30-2016");

    checkText("introducedErr",
        "The introduced value must be specified if you put the discontinued one !");
    checkText("discontinuedErr",
        "You can't specify the discontinued date if you don't set the introduced one !");

    // We check that the discontinued date must be set after the introduced
    // date

    driver.findElement(By.id("introducedDate")).clear();
    driver.findElement(By.id("introducedDate")).sendKeys("12-25-2017");

    checkText("introducedErr", "The introduced value must be set before the discontinued one !");
    checkText("discontinuedErr", "The discontinued value must be set after the introduced one !");

    // We check that if a wrong format is entered there is an error

    driver.findElement(By.id("introducedDate")).clear();
    driver.findElement(By.id("introducedDate")).sendKeys("2015-12");

    checkText("introducedErr", "Wrong format entered (or the date is before January 02 1970)");

    // We check that all is fine with the current dates

    driver.findElement(By.id("introducedDate")).clear();
    driver.findElement(By.id("introducedDate")).sendKeys("12-02-2015");

    checkText("introducedErr", "");
    checkText("discontinuedErr", "");

    /*
     * We set the company & submit the form, if all is fine no exception is thrown and we are
     * redirected to the dashboard
     */
    new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Netronics");
    driver.findElement(By.id("submit")).click();

    // We then try to find the newly introduced computer in the database

    ArrayList<Computer> computerList = computerService.getByName(computerName);
    assertNotEquals(0, computerList.size());

    // And we check the insertion went well for all fields

    Computer computer = computerList.get(0);

    assertNotNull(computer);
    assertEquals(computerName, computer.getName());
    assertEquals(LocalDate.of(2015, 12, 2), computer.getIntroduced());
    assertEquals(LocalDate.of(2016, 10, 30), computer.getDiscontinued());
    assertEquals("Netronics", computer.getCompany().getName());

    // An dwe delete the computer so that the database remains clean
    computerService.deleteComputer(computer.getId());

  }
  /*
   * @Test public void addComputerFrench() throws Exception {
   * 
   * SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
   * sessionLocaleResolver.setDefaultLocale(Locale.FRENCH);
   * 
   * driver.get(baseUrl + "/computerdatabase/add-computer?lang=fr");
   * driver.findElement(By.id("computerName")).clear();
   * driver.findElement(By.id("computerName")).sendKeys(computerName);
   * driver.findElement(By.id("computerName")).clear();
   * 
   * // We check that the error message regarding the computer's name shows up
   * 
   * checkText("computerNameErr",
   * "Le nom de l'ordinateur ne contient pas entre 1 et 100 caractéres !");
   * 
   * driver.findElement(By.id("computerName")).sendKeys(computerName);
   * 
   * // We check that we can't put a discontinued date if we don't have set // the introduced date
   * 
   * driver.findElement(By.id("discontinuedDate")).clear();
   * driver.findElement(By.id("discontinuedDate")).sendKeys("01-10-2016");
   * 
   * checkText("introducedErr",
   * "La date de mise en service doit être renseignée si la date de mise hors service est " +
   * "spécifiée !"); checkText("discontinuedErr",
   * "Impossible de renseigner la date de mise hors service sans mettre la date de " +
   * "mise en service également !");
   * 
   * // We check that the discontinued date must be set after the introduced // date
   * 
   * driver.findElement(By.id("introducedDate")).clear();
   * driver.findElement(By.id("introducedDate")).sendKeys("17-02-2017");
   * 
   * checkText("introducedErr",
   * "La date de mise en service doit être antérieure à la date de mise hors service !");
   * checkText("discontinuedErr",
   * "La mise hors service doit avoir lieu après la mise en service de l'ordinateur !");
   * 
   * // We check that if a wrong format is entered there is an error
   * 
   * driver.findElement(By.id("introducedDate")).clear();
   * driver.findElement(By.id("introducedDate")).sendKeys("2015-12");
   * 
   * checkText("introducedErr",
   * "La date n'est pas au bon format (ou est inférieure au 2 Janvier 1970)!");
   * 
   * // We check that all is fine with the current dates
   * 
   * driver.findElement(By.id("introducedDate")).clear();
   * driver.findElement(By.id("introducedDate")).sendKeys("30-12-2015");
   * 
   * checkText("introducedErr", ""); checkText("discontinuedErr", "");
   * 
   * 
   * // We set the company & submit the form, if all is fine no exception is thrown and we are //
   * redirected to the dashboard
   * 
   * new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Netronics");
   * driver.findElement(By.id("submit")).click();
   * 
   * // We then try to find the newly introduced computer in the database
   * 
   * ArrayList<Computer> computerList = computerService.getByName(computerName); assertNotEquals(0,
   * computerList.size());
   * 
   * // And we check the insertion went well for all fields
   * 
   * Computer computer = computerList.get(0);
   * 
   * assertNotNull(computer); assertEquals(computerName, computer.getName());
   * assertEquals(LocalDate.of(2015, 12, 30), computer.getIntroduced());
   * assertEquals(LocalDate.of(2016, 10, 1), computer.getDiscontinued()); assertEquals("Netronics",
   * computer.getCompany().getName());
   * 
   * // An dwe delete the computer so that the database remains clean
   * computerService.deleteComputer(computer.getId());
   * 
   * }
   */

  /**
   * Check text.
   *
   * @param id the id
   * @param text the text
   */
  private void checkText(String id, String text) {

    String errorText = driver.findElement(By.id(id)).getText();
    assertTrue(errorText.equals(text));
  }
}
