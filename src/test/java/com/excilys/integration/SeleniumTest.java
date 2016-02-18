package com.excilys.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTest {

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}

	@Test
	public void testNextAndPrevious() throws Exception {
		
		driver.get(baseUrl + "/computerdatabase/dashboard?page-number=1");
		
		driver.findElement(By.xpath("//li[contains(@class, 'active')]/a[text()='1']"));
		
		driver.findElement(By.linkText("Next")).click();
		driver.findElement(By.xpath("//li[contains(@class, 'active')]/a[text()='2']"));
		
		try{
			driver.findElement(By.xpath("//li[contains(@class, 'active')]/a[text()='1']"));
			fail();
		}catch(NoSuchElementException e){
			
		}
		
		driver.findElement(By.linkText("Previous")).click();
		driver.findElement(By.xpath("//li[contains(@class, 'active')]/a[text()='1']"));
		try{
			driver.findElement(By.xpath("//li[contains(@class, 'active')]/a[text()='2']"));
			fail();
		}catch(NoSuchElementException e){
			
		}
	}

	@Test
	public void testNbComputerPerPage(){
		
		driver.get(baseUrl + "/computerdatabase/dashboard");

		int rowCounts = driver.findElements(By.xpath("//tbody[@id='results']/tr")).size();
		assertEquals(10, rowCounts);

		driver.findElement(By.linkText("50")).click();
		rowCounts = driver.findElements(By.xpath("//tbody[@id='results']/tr")).size();
		assertEquals(50, rowCounts);
		
		driver.findElement(By.linkText("100")).click();
		rowCounts = driver.findElements(By.xpath("//tbody[@id='results']/tr")).size();
		assertEquals(100, rowCounts);
		
		driver.findElement(By.linkText("10")).click();
		rowCounts = driver.findElements(By.xpath("//tbody[@id='results']/tr")).size();
		assertEquals(10, rowCounts);
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
