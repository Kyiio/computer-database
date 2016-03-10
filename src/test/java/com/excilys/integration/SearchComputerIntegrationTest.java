package com.excilys.integration;

import static org.junit.Assert.fail;

import com.excilys.service.ComputerService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:./applicationContext.xml" })
public class SearchComputerIntegrationTest {

  private WebDriver       driver;
  private String          baseUrl;
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

  @Test
  public void testSearch() {

    final String computerName = "Toto integration test";

    // We insert the computer we are going to search
    Long id = computerService.insertComputer(null, null, null, computerName);

    driver.get(baseUrl + "/computerdatabase/computers?computer-per-page=10&page-number=1&lang=en");

    driver.findElement(By.id("searchName")).clear();
    driver.findElement(By.id("searchName")).sendKeys(computerName);
    driver.findElement(By.id("searchSubmit")).click();

    driver.findElement(
        By.xpath("//tbody[@id='results']//tr[1]//a[contains(@href, 'computers/edit?computerId=" + id
            + "') and text()='" + computerName + "']"));

    // We delete the computer so that the database remains clean

    computerService.deleteComputer(id);
  }
}
