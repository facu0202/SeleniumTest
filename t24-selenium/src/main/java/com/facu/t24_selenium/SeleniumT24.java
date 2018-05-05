package com.facu.t24_selenium;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class SeleniumT24 {
	
	public boolean runT24Example() {
		System.setProperty("webdriver.chrome.driver","/home/facundo/tools/selenium-java-3.11.0/chrome/chromedriver");
		
		WebDriver driver = new ChromeDriver();
		driver.get("http://172.17.209.47:5001/BrowserWebMT/servlet/BrowserServlet?");
		
		driver.findElement(By.id("signOnName")).sendKeys("fferro");
		driver.findElement(By.id("password")).sendKeys("123123");
		
		driver.findElement(By.id("sign-in")).click();
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		List <WebElement>elements = driver.findElements(By.tagName("frame"));
		driver.switchTo().frame(elements.get(0));
		driver.findElement(By.id("commandValue")).sendKeys("CHEQUE.ISSUE,SOLICITUDCHQ I CHCC.1000001054.0000003");
		driver.findElement(By.id("commandValue")).sendKeys(Keys.RETURN);
		
		String currentHandle=(driver.getWindowHandle());
		Set<String> otherWindows = driver.getWindowHandles();
		for (String name : otherWindows) {
			if (!currentHandle.equals(name))
			{
				driver.switchTo().window(name);
				break;
			}			
		}
		
		driver.findElement(By.id("fieldName:CHEQUE.STATUS")).sendKeys("1");
		driver.findElement(By.id("fieldName:L.SUC.DESTINO")).sendKeys("1");
		
		driver.findElement(By.xpath("//*[@title='Commit the deal']")).click();
		
		return true;

	}	

}
