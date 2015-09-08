package com.galactoise.homeorchestration.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.galactoise.homeorchestration.exception.mycokerewards.MyCokeRewardsPageException;

public class MyCokeRewardsPage {

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
	}
	
	public String getTitle(){
		return driver.getTitle();
	}
	
	public void closeDriver(){
		driver.close();
	}

	public void login(String email, String password) throws MyCokeRewardsPageException{

		//TODO: DO SOMETHING USEFUL WITH THE MESSAGING HERE, DON'T JUST PRINTLN		
		
		String pageUrl = driver.getCurrentUrl();
		
		WebElement emailBox = driver.findElement(By.id("capture_signIn_traditionalSignIn_emailAddress"));
		WebElement passwordBox = driver.findElement(By.id("capture_signIn_traditionalSignIn_password"));
		WebElement submitButton = driver.findElement(By.id("capture_signIn_traditionalSignIn_signInButton"));
		emailBox.sendKeys(email);
		passwordBox.sendKeys(password);
		submitButton.submit();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			new WebDriverWait(driver, 2).until(ExpectedConditions.not(currentUrlIs(pageUrl)));
		}catch(NotFoundException | TimeoutException e){
			List<WebElement> formErrors = driver.findElement(By.id("capture_signIn_userInformationForm_errorMessages")).findElements(By.xpath(".//div"));
			if(formErrors.size() > 0){
				System.out.println("Found form errors on submit.");
				for(WebElement formError : formErrors){
					System.out.println(formError.getText());
				}
			}
			
			WebElement emailBoxErrorTip = driver.findElement(By.id("capture_signIn_form_item_traditionalSignIn_emailAddress")).findElements(By.className("capture_tip_error")).get(0);
			String emailBoxErrorTipText = emailBoxErrorTip.getText();
			
			if(emailBoxErrorTipText.isEmpty()){
				System.out.println("No email error tip present.");
			}else{
				System.out.println("Error tip: " + emailBoxErrorTipText);
			}

			throw new MyCokeRewardsPageException("Could not login.");
		}
		
		System.out.println("Successfully logged in.");
	}
	
	public static ExpectedCondition<Boolean> currentUrlIs(final String urlToCompare){
		return new ExpectedCondition<Boolean>(){

			@Override
			public Boolean apply(WebDriver driver) {
				return driver.getCurrentUrl().equalsIgnoreCase(urlToCompare);
			}
		};
	}
}
