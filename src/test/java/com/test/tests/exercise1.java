package com.test.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.test.utils.baseLogin;
import com.test.utils.pageExercise1;

public class exercise1 {
	
	private WebDriver driver;
	
	baseLogin baseLogin1;
	pageExercise1 firstExercise = new pageExercise1(); 

	@Before
	public void setUp() throws Exception {
		baseLogin1 = new baseLogin(driver);
		driver = baseLogin1.chromeConnection();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() throws InterruptedException {
		driver.get("https://tasks.evalartapp.com/automatization/");
		baseLogin1.login(driver, "647039", "10df2f32286b7120Mi00LTkzMDc0Ng==30e0c83e6c29f1c3");
		firstExercise.executeOperations(driver);
		Thread.sleep(6000);
	}

}
