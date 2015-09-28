package com.galactoise.homeorchestration.util;

import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.galactoise.homeorchestration.exception.mycokerewards.MyCokeRewardsPageException;

@SuppressWarnings("serial")
public class MyCokeRewardsServlet extends HttpServlet {

	private Properties properties;
	protected static final Logger LOGGER = Logger.getLogger(MyCokeRewardsServlet.class.getName());
	
	public void init() throws ServletException{
		LOGGER.info("Starting up MyCokeRewardsServlet");

		properties = PropertiesSingleton.getPropertiesSingletonInstance().getProperties();

		LOGGER.info("Initializing MyCokeRewards browser driver");
		WebDriver driver = MyCokeRewardsDriverSingleton.getInstance().getDriver();
		
		try{
		LOGGER.info("Building new MyCokeRewardsPage and going to home page.");
		MyCokeRewardsPage rewardsPage = new MyCokeRewardsPage(driver);
		rewardsPage.goTo(properties.getProperty("mycokerewards.homepageurl"));
		
		LOGGER.info("Logging in with driver instance.");
		rewardsPage.login(properties.getProperty("mycokerewards.loginurl"), properties.getProperty("mycokerewards.user.email"),properties.getProperty("mycokerewards.user.password"));
		}catch(MyCokeRewardsPageException e){
			LOGGER.severe("Failed to properly initialize MyCokeRewardsPage: " + e.getErrorMessage());
		}
	}
	
	public void destroy(){
		super.destroy();
		LOGGER.info("Shutting down driver instances.");
		MyCokeRewardsDriverSingleton.getInstance().getDriver().close();
		MyCokeRewardsDriverSingleton.getInstance().getDriver().quit();
	}
}
