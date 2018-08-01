package com.globallogic.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public abstract class T24Selenium {

	final Logger logger = LogManager.getLogger(this.getClass());
	private static final String TD = "td";
	private static final String TITLE_COMMIT_THE_DEAL = "//*[@title='Commit the deal']";
	private static final String TITLE_RUN_SELECTION = "//*[@title='Run Selection']";
	private static final String DATADISPLAY = "datadisplay";
	private static final String CLASS_ENQUIRYDATA = "//*[@class='enquirydata']";
	private static final String ENQUIRY_DATA_SCROLLER = "enquiryDataScroller";
	private static final String COLUMN_HEADER = "columnHeaderText";
	public static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
	public static final String T24_URL = "t24.url";
	public static final String T24_USER = "t24.user";
	public static final String T24_PASS = "t24.password";

	protected WebDriver webDriver;
	protected Properties t24SeleniumProperties = new Properties();

	public Properties getT24SeleniumProperties() {
		return t24SeleniumProperties;
	}

	public void setT24SeleniumProperties(Properties t24SeleniumProperties) {
		this.t24SeleniumProperties = t24SeleniumProperties;
	}

	public WebDriver getWebDriver() {
		return webDriver;
	}

	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public T24Selenium() {
		super();
		try {
			getT24SeleniumProperties().load(ClassLoader.getSystemResourceAsStream("T24.properties"));
		} catch (IOException e) {
			logger.error("Error",e);
		}
		System.setProperty(WEBDRIVER_CHROME_DRIVER, getT24SeleniumProperties().getProperty(WEBDRIVER_CHROME_DRIVER));
		
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		setWebDriver(new ChromeDriver(options));
		getWebDriver().get(getT24SeleniumProperties().getProperty(T24_URL));
	}

	public void login() {
		getWebDriver().findElement(By.id("signOnName")).sendKeys(getT24SeleniumProperties().getProperty(T24_USER));
		getWebDriver().findElement(By.id("password")).sendKeys(T24_PASS);
		getWebDriver().findElement(By.id("sign-in")).click();
		getWebDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void executeCommand(String command) {
		List<WebElement> elements = getWebDriver().findElements(By.tagName("frame"));
		getWebDriver().switchTo().frame(elements.get(0));
		getWebDriver().findElement(By.id("commandValue")).sendKeys(command);
		getWebDriver().findElement(By.id("commandValue")).sendKeys(Keys.RETURN);
	}

	public void changeToOpenWindows() {
		String currentHandle = (getWebDriver().getWindowHandle());
		Set<String> otherWindows = getWebDriver().getWindowHandles();
		for (String name : otherWindows) {
			if (!currentHandle.equals(name)) {
				getWebDriver().switchTo().window(name);
				break;
			}
		}
	}
	public void setValue(String nameField, String value)
	{
		getWebDriver().findElement(By.id("fieldName:"+nameField)).sendKeys(value);
	}
	public void setValuePosition(String postion, String value)
	{
		getWebDriver().findElement(By.id("value:"+postion)).sendKeys(value);
	}
	public void pressCommitButton()
	{
		getWebDriver().findElement(By.xpath(TITLE_COMMIT_THE_DEAL)).click();
	}
	
	public void execute(String command, Map<String,String> params)
	{
		this.login();
		this.executeCommand(command);
		this.changeToOpenWindows();
		params.keySet().stream().forEach(p->this.setValue(p,params.get(p)));
		this.pressCommitButton();
	}
	
	public void execute(String command, String firstValue)
	{
		this.login();
		this.executeCommand(command);
		this.changeToOpenWindows();
		setValuePosition("1:1:1",firstValue);
		this.pressNQuiryFindButton();
	}
	
	public boolean existReturnField(String field)
	{
		return field!=null;
	}
	public boolean returnsValueNotEmpety(List<String> nameReturnFields)
	{
		return !nameReturnFields.isEmpty();
	}
	
	public void pressNQuiryFindButton()
	{
		getWebDriver().findElement(By.xpath(TITLE_RUN_SELECTION)).click();
	}
	
	
		
	public Map<String,String> findNQuiryFirstResponseValue()
	{
		getWebDriver().findElement(By.id(ENQUIRY_DATA_SCROLLER));		
		WebElement table = getWebDriver().findElement(By.xpath(CLASS_ENQUIRYDATA));
		WebElement detail = getWebDriver().findElement(By.id(DATADISPLAY));
		List<WebElement> values = detail.findElements(By.tagName(TD));
		Actions actions = new Actions(getWebDriver());
		Map<String,String> result = new HashMap<>();
		int i = 1;
		for (WebElement value : values) {
			WebElement element = table.findElement(By.id(COLUMN_HEADER+i));
			actions.moveToElement(element);
			actions.perform();
			actions.moveToElement(value);
			actions.perform();
			result.put(element.getText(), value.getText());
			i++;
		}
		return result;
	}
}
