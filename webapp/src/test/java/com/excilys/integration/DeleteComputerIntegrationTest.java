package com.excilys.integration;

import static org.junit.Assert.assertNull;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:./webapp-context.xml" })
public class DeleteComputerIntegrationTest {

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

  @Test
  public void testDeleteOneComputer() {

    Long id = computerService.insertComputer(null, null, null, "Toto integration test");

    driver.get(baseUrl + "/computerdatabase/computers?lang=en");

    driver.findElement(By.id("searchName")).clear();
    driver.findElement(By.id("searchName")).sendKeys("Toto integration test");
    driver.findElement(By.id("searchSubmit")).click();
    driver.findElement(By.id("editComputersButton")).click();

    driver.findElement(By.xpath("(//input[@name='cb' and @value='" + id + "'])")).click();
    driver.findElement(By.xpath("//a[@id='deleteSelected']/i")).click();
    assertTrue(closeAlertAndGetItsText()
        .equals("Are you sure you want to delete the selected computers ?"));

    Computer computer = computerService.getById(id);
    assertNull(computer);
  }

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
