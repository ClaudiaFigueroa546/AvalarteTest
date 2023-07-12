package com.test.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class baseLogin {
	private WebDriver driver;
	
	
	By userXpath = By.xpath("/html/body/div/div[2]/form/input[1]");
	By passXpath = By.xpath("/html/body/div/div[2]/form/input[2]");
	By sendButton = By.xpath("/html/body/div/div[2]/form/button");
	By loginText = By.xpath("/html/body/div[1]/h1");

	public baseLogin(WebDriver driver) {
		//super();
		this.driver = driver;
	}
	
	public WebDriver chromeConnection() {
		
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	
	
	public void login(WebDriver driver, String user, String password) {
		
		WebElement username = driver.findElement(userXpath);
		username.sendKeys(user);
		
		WebElement pass = driver.findElement(passXpath);
		pass.sendKeys(password);
		
		WebElement send = driver.findElement(sendButton);
		send.click();
		
		
	}

}
