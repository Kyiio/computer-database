package com.excilys.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * The Class SeleniumIT.
 */
public class SeleniumIntegrationTest {

  /** The driver. */
  private WebDriver driver;

  /** The base url. */
  private String baseUrl;

  /** The verification errors. */
  private StringBuffer verificationErrors = new StringBuffer();

  /**
   * Sets the up.
   *
   * @throws Exception
   *           the exception
   */
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:6060";
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    driver.manage().window().maximize();
  }

  /**
   * Test next and previous.
   *
   * @throws Exception
   *           the exception
   */
  @Test
  public void testNextAndPrevious() throws Exception {

    driver.get(baseUrl + "/computerdatabase/dashboard?page-number=1");

    driver.findElement(By.xpath("//li[contains(@class, 'active')]/a[text()='1']"));

    driver.findElement(By.linkText("Next")).click();
    driver.findElement(By.xpath("//li[contains(@class, 'active')]/a[text()='2']"));

    try {
      driver.findElement(By.xpath("//li[contains(@class, 'active')]/a[text()='1']"));
      fail();
    } catch (NoSuchElementException e) {
      assertTrue(true);
    }

    driver.findElement(By.linkText("Previous")).click();
    driver.findElement(By.xpath("//li[contains(@class, 'active')]/a[text()='1']"));
    try {
      driver.findElement(By.xpath("//li[contains(@class, 'active')]/a[text()='2']"));
      fail();
    } catch (NoSuchElementException e) {
      assertTrue(true);
    }
  }

  /**
   * Test nb computer per page.
   */
  @Test
  public void testNbComputerPerPage() {

    driver.get(baseUrl + "/computerdatabase/dashboard");

    int rowCounts = driver.findElements(By.xpath("//tbody[@id='results']/tr")).size();
    assertEquals(10, rowCounts);

    driver.findElement(By.xpath("//a[contains(@class, 'btn btn-default') and text()='50']"))
        .click();
    rowCounts = driver.findElements(By.xpath("//tbody[@id='results']/tr")).size();
    assertEquals(50, rowCounts);

    driver.findElement(By.xpath("//a[contains(@class, 'btn btn-default') and text()='100']"))
        .click();
    rowCounts = driver.findElements(By.xpath("//tbody[@id='results']/tr")).size();
    assertEquals(100, rowCounts);

    driver.findElement(By.xpath("//a[contains(@class, 'btn btn-default') and text()='10']"))
        .click();
    rowCounts = driver.findElements(By.xpath("//tbody[@id='results']/tr")).size();
    assertEquals(10, rowCounts);
  }

  /**
   * Tear down.
   *
   * @throws Exception
   *           the exception
   */
  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
