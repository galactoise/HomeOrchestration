package com.galactoise.homeorchestration.util;

import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class MyCokeRewardsDriverSingleton {

	protected static final Logger LOGGER = Logger.getLogger(MyCokeRewardsDriverSingleton.class.getName());

	private static MyCokeRewardsDriverSingleton instance = new MyCokeRewardsDriverSingleton();
	
	private Properties properties;
	
	private WebDriver driver;
	
	private MyCokeRewardsDriverSingleton(){
		properties = PropertiesSingleton.getPropertiesSingletonInstance().getProperties();
		String browserDriver = properties.getProperty("webdriver.browser.driver");
		browserDriver = browserDriver == null ? "chrome" : browserDriver;
		DesiredCapabilities capabilities;
		switch(browserDriver){
		case "phantomjs": 
			capabilities = DesiredCapabilities.htmlUnit();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, properties.getProperty("phantomjs.executable.path"));
			driver = new PhantomJSDriver(capabilities);
			break;
		case "chrome":
		default: 
			capabilities = DesiredCapabilities.chrome();
			String chromeBinaryPath = "C:" + File.separator + "Users" + File.separator + "Eric" + File.separator + "AppData" + File.separator + "Local" + File.separator + "Google" + File.separator + "Chrome" + File.separator + "Application" + File.separator + "chrome.exe";
			String chromeDriverPath = "C:" + File.separator + "apps" + File.separator + "chromedriver" + File.separator + "chromedriver.exe";
			capabilities.setCapability(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, chromeDriverPath);
			ChromeOptions options = new ChromeOptions();

			options.setBinary(chromeBinaryPath);
			LOGGER.info("ChromeDriver path: " + chromeDriverPath);
			LOGGER.info("ChromeBinary path: " + chromeBinaryPath);
			capabilities.setCapability("chrome.binary", chromeBinaryPath);
			capabilities.setCapability("webdriver.chrome.driver", chromeDriverPath);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);

			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			System.setProperty("chrome.binary",chromeBinaryPath);
			driver = new ChromeDriver(capabilities);
			break;
		}


	}
	
	public static MyCokeRewardsDriverSingleton getInstance(){
		return instance;
	}
	
	public WebDriver getDriver(){
		return driver;
	}
}
