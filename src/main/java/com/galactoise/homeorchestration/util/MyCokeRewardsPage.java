package com.galactoise.homeorchestration.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.galactoise.homeorchestration.exception.mycokerewards.MyCokeRewardsPageException;

public class MyCokeRewardsPage {

	protected static final Logger LOGGER = Logger.getLogger(MyCokeRewardsPage.class.getName());

	private WebDriver driver;	
	
	public MyCokeRewardsPage(WebDriver driver){
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	public void goTo(String url){

		driver.get(url);
		driver.manage().addCookie(new Cookie("skipSocial", "twitterfacebook"));
	}
	
	public String getTitle(){
		return driver.getTitle();
	}
	
	public void closeDriver(){
		driver.close();
	}
	
	public boolean checkOnHomepage(String urlToMatch){
		if(driver.getCurrentUrl() == null){
			return false;
		}
		if(!driver.getCurrentUrl().equalsIgnoreCase(urlToMatch)){
			LOGGER.info("Expected homepage url: " + urlToMatch);
			LOGGER.info("Actual page url: " + driver.getCurrentUrl());
			return false;
		}
		
		return true;
	}

	public boolean checkOnSocialConnectPage() {
		
		try{
			WebElement signInSocial = driver.findElement(By.id("signInSocial"));
			if(signInSocial.isDisplayed()){
				LOGGER.info("On the social sign in page");
				return true;
			}
			LOGGER.info("Not on the social sign in page.");
			return false;
		}catch(NoSuchElementException e){
			return false;
		}
	}
	
	public boolean checkLoggedInToHomepage(){
		if(driver.getCurrentUrl() == null){
			return false;
		}
		try{
			WebElement profilePointAmount = driver.findElement(By.id("h-profilePointAmount"));
			if(!profilePointAmount.isDisplayed()){
				LOGGER.info("Profile point amount field is not displayed.");
				LOGGER.info("Profile point amount: " + profilePointAmount.getAttribute("innerHTML"));
				screenshotCurrentPage();
				return false;
			}
			LOGGER.info("Confirmed logged in.");
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}

	public boolean checkPageReadyForCode() {
		if(driver.getCurrentUrl() == null){
			return false;
		}
		try{
			WebElement enterCodeField = driver.findElement(By.name("enterCodeField"));
			if(!enterCodeField.isDisplayed()){
				LOGGER.info("Enter code field is not displayed - waiting and retrying.");
				try{
					new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.name("enterCodeField")));
				}catch(TimeoutException te){
					LOGGER.info("Enter code field was never visible.");
					screenshotCurrentPage();
					return false;
				}
				LOGGER.info("Enter code was eventually visible.");
				return false;
				
			}
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}

	public boolean checkIfDrinkSelectionNeeded() {
		if(driver.getCurrentUrl() == null){
			return false;
		}
		try{
			new WebDriverWait(driver, 1, 100).until(ExpectedConditions.visibilityOfElementLocated(By.className("enterCodeBrands")));
		}catch(TimeoutException te){
			LOGGER.info("Code brands box was never visible.");
			return false;
		}

		LOGGER.info("Code brands box was found.");	
		return true;
	}

	public boolean checkIfRecordedSuccessfully() {
		if(driver.getCurrentUrl() == null){
			return false;
		}
		try{
			new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(By.className("enterCodeSuccessBox")));
		}catch(TimeoutException te){
			LOGGER.info("Success box was never visible.");
			return false;
		}

		LOGGER.info("Success box was found.");	
		return true;
	}

	public void findRecordingErrorIfPresent() {
		if(driver.getCurrentUrl() == null){
			throw new MyCokeRewardsPageException("Driver's current page was null and could not be accessed.");
		}
		try{
			WebElement enterCodeErrorMessage = driver.findElement(By.className("enterCodeErrorMessage"));
			if(enterCodeErrorMessage.isDisplayed()){
				LOGGER.info("Error during recording was: " + enterCodeErrorMessage.getText());
				throw new MyCokeRewardsPageException(enterCodeErrorMessage.getText(), true);
			}
		}catch(NoSuchElementException e){
			throw new MyCokeRewardsPageException("Could not find error code box.");
		}
	}

	public void login(String loginUrl, String email, String password) throws MyCokeRewardsPageException{

		long start = 0;
		long finish;
		
		start = System.currentTimeMillis();
		goTo(loginUrl);
		finish = System.currentTimeMillis();
		LOGGER.info("Finished loading homepage in " + (finish - start) + "ms");
		
		driver.manage().addCookie(new Cookie("skipSocial", "twitterfacebook"));
		String pageUrl = driver.getCurrentUrl();
		try{
			start = System.currentTimeMillis();
			driver.findElement(By.id("signIn"));
			finish = System.currentTimeMillis();
			LOGGER.info("Found sign in form in " + (finish - start) + "ms");
		}catch(NoSuchElementException e){
			finish = System.currentTimeMillis();
			LOGGER.info("Failed to find sign in form in " + (finish - start) + "ms");
			
			LOGGER.info("COULD NOT FIND SIGN IN FORM.");
			
			LOGGER.severe("UNKNOWN PAGE: " + pageUrl);
			throw new MyCokeRewardsPageException("Unknown page browsed to on session start: " + pageUrl);
		}		
		WebElement emailBox;
		WebElement passwordBox;
		WebElement submitButton;
		try{
			start = System.currentTimeMillis();
			emailBox = driver.findElement(By.id("capture_signIn_traditionalSignIn_emailAddress"));
			passwordBox = driver.findElement(By.id("capture_signIn_traditionalSignIn_password"));
			submitButton = driver.findElement(By.id("capture_signIn_traditionalSignIn_signInButton"));
			finish = System.currentTimeMillis();
			LOGGER.info("Found form elements in " + (finish - start) + "ms");
		}catch(NoSuchElementException e){
			finish = System.currentTimeMillis();
			LOGGER.info("Failed to find form elements in " + (finish - start) + "ms");
			
			LOGGER.info("skipSocial cookie: " + driver.manage().getCookieNamed("skipSocial"));
			throw new MyCokeRewardsPageException("Could not find form elements on page: " + pageUrl);
		}
		emailBox.sendKeys(email);
		passwordBox.sendKeys(password);
		submitButton.submit();
		
		try{
			start = System.currentTimeMillis();
			new WebDriverWait(driver, 2).until(ExpectedConditions.not(currentUrlIs(pageUrl)));
			finish = System.currentTimeMillis();
			LOGGER.info("Browsed to homepage in " + (finish - start) + "ms");
		}catch(NotFoundException | TimeoutException e){
			finish = System.currentTimeMillis();
			LOGGER.info("Failed to browse to homepage in " + (finish - start) + "ms");
			

			start = System.currentTimeMillis();
			List<WebElement> formErrors = driver.findElement(By.id("capture_signIn_userInformationForm_errorMessages")).findElements(By.xpath(".//div"));
			if(formErrors.size() > 0){
				LOGGER.info("Found form errors on submit.");
				for(WebElement formError : formErrors){
					LOGGER.info(formError.getText());
				}
			}
			
			WebElement emailBoxErrorTip = driver.findElement(By.id("capture_signIn_form_item_traditionalSignIn_emailAddress")).findElements(By.className("capture_tip_error")).get(0);
			String emailBoxErrorTipText = emailBoxErrorTip.getText();
			
			if(emailBoxErrorTipText.isEmpty()){
				LOGGER.info("No email error tip present.");
			}else{
				LOGGER.info("Error tip: " + emailBoxErrorTipText);
			}
			
			finish = System.currentTimeMillis();
			LOGGER.info("Found troubleshooting data in " + (finish - start) + "ms");

			throw new MyCokeRewardsPageException("Could not login.");
		}
		
		LOGGER.info("Successfully logged in.");
	}
	
	public void recordCode(String rewardString){
		WebElement enterCodeField;
		try{
			enterCodeField = driver.findElement(By.name("enterCodeField"));
		}catch(NoSuchElementException e){
			throw new MyCokeRewardsPageException("Could not find reward code input field.");
		}
		
		try{
			enterCodeField.clear();
			enterCodeField.sendKeys(rewardString);
		}catch(InvalidElementStateException e){
			throw new MyCokeRewardsPageException("Could not enter reward code into input field due to exception: " + e);
		}

		LOGGER.info("Current text of enter code field: " + enterCodeField.getAttribute("value"));
		
		WebElement enterCodeSubmit;
		try{
			enterCodeSubmit = driver.findElement(By.className("enterCodeSubmit"));
		}catch(NoSuchElementException e){
			throw new MyCokeRewardsPageException("Could not find submission button.");
		}
		
		enterCodeSubmit.click();
	}

	public void selectDrink() {
		WebElement enterCodeBrands;
		try{
			enterCodeBrands = driver.findElement(By.className("enterCodeBrands"));
		}catch(NoSuchElementException e){
			throw new MyCokeRewardsPageException("Could not find brands box to select brand for drink.");
		}
		
		List<WebElement> codeBrandOptions;
		try{
			codeBrandOptions = enterCodeBrands.findElements(By.tagName("a"));
		}catch(NoSuchElementException e){
			throw new MyCokeRewardsPageException("Could not find any brands in brands box.");
		}
		
		if(codeBrandOptions.size() <= 0){
			throw new MyCokeRewardsPageException("Could not find any brands in brands box.");
		}
		
		LOGGER.info("Found " + codeBrandOptions.size() + " brand options.");
		
		codeBrandOptions.get(0).click();
	}
	
	public static ExpectedCondition<Boolean> currentUrlIs(final String urlToCompare){
		return new ExpectedCondition<Boolean>(){

			@Override
			public Boolean apply(WebDriver driver) {
				return driver.getCurrentUrl().equalsIgnoreCase(urlToCompare);
			}
		};
	}

	public void screenshotCurrentPage() {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy somewhere
		try {
			FileUtils.copyFile(scrFile, new File("c:\\screenshot" + System.currentTimeMillis() + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
