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
import org.openqa.selenium.Alert;
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
@ContextConfiguration(locations = { "classpath:./testApplicationContext.xml" })
public class AddComputerIntegrationTest {

  private WebDriver       driver;
  private String          baseUrl;
  private boolean         acceptNextAlert    = true;
  private StringBuffer    verificationErrors = new StringBuffer();

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
    baseUrl = "http://127.0.0.1:6060/";
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    driver.manage().window().maximize();
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
  }

  /**
   * Integration test : Add computer feature.
   *
   * @throws Exception the exception
   */
  @Test
  public void addComputer() throws Exception {
    driver.get(baseUrl + "/computerdatabase/dashboard?computer-per-page=10&page-number=1&order-by="
        + "&order-type=&search-name=");
    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.id("computerName")).clear();
    driver.findElement(By.id("computerName")).sendKeys("Toto integration test");
    driver.findElement(By.id("computerName")).clear();

    // We check that the error message regarding the computer's name shows
    // up

    checkText("computerNameErr", "^The computer name must be specified !$");

    driver.findElement(By.id("computerName")).sendKeys("Toto integration test");

    // We check that we can't put a discontinued date if we don't have set
    // the introduced date

    driver.findElement(By.id("discontinuedDate")).clear();
    driver.findElement(By.id("discontinuedDate")).sendKeys("2016-10-01");

    checkText("introducedErr",
        "^The introduced value must be specified if you put the discontinued one !$");
    checkText("discontinuedErr",
        "^You can't specify the discontinued date if you don't set the introduced one !$");

    // We check that the discontinued date must be set after the introduced
    // date

    driver.findElement(By.id("introducedDate")).clear();
    driver.findElement(By.id("introducedDate")).sendKeys("2017-12-02");

    checkText("introducedErr", "^The introduced value must be set before the discontinued one !$");
    checkText("discontinuedErr", "^The discontinued value must be set after the introduced one !$");

    // We check that all is fine with the current dates

    driver.findElement(By.id("introducedDate")).clear();
    driver.findElement(By.id("introducedDate")).sendKeys("2015-12-02");

    checkText("introducedErr", "^$");
    checkText("discontinuedErr", "^$");

    /*
     * We set the company & submit the form, if all is fine no exception is thrown and we are
     * redirected to the dashboard
     */
    new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Netronics");
    driver.findElement(By.id("submit")).click();

    // We then try to find the newly introduced computer in the database

    ArrayList<Computer> computerList = computerService.getByName("Toto integration test");
    assertNotEquals(0, computerList.size());

    // And we check the insertion went well for all fields

    Computer computer = computerList.get(0);

    assertNotNull(computer);
    assertEquals("Toto integration test", computer.getName());
    assertEquals(LocalDate.of(2015, 12, 2), computer.getIntroduced());
    assertEquals(LocalDate.of(2016, 10, 1), computer.getDiscontinued());
    assertEquals("Netronics", computer.getCompany().getName());

    // An dwe delete the computer so that the database remains clean
    computerService.deleteComputer(computer.getId());

  }

  /**
   * Check text.
   *
   * @param id the id
   * @param text the text
   */
  private void checkText(String id, String text) {

    String errorText = driver.findElement(By.id(id)).getText();
    assertTrue(errorText.matches(text));
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
