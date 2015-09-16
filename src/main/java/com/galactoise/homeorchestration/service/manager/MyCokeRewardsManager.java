package com.galactoise.homeorchestration.service.manager;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.galactoise.homeorchestration.exception.mycokerewards.MyCokeRewardsPageException;
import com.galactoise.homeorchestration.exception.mycokerewards.RewardStringException;
import com.galactoise.homeorchestration.util.MyCokeRewardsPage;
import com.galactoise.homeorchestration.util.PropertiesSingleton;

public class MyCokeRewardsManager {
	
	private Properties properties;
	private MyCokeRewardsPage rewardsPage;

	protected static final Logger LOGGER = Logger.getLogger(MyCokeRewardsManager.class.getName());
	protected static final HashSet<Character> VALID_REWARD_STRING_LETTERS = new HashSet<Character>(Arrays.asList('b','f','h','j','k','l','m','n','p','r','t','v','w','x'));
	
	public MyCokeRewardsManager(){
		properties = PropertiesSingleton.getPropertiesSingletonInstance().getProperties();
//		DesiredCapabilities capability = DesiredCapabilities.htmlUnit();
//		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//		capability.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, properties.getProperty("phantomjs.executable.path"));
//		rewardsPage = new MyCokeRewardsPage(new PhantomJSDriver(capability));
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		String chromeBinaryPath = "C:" + File.separator + "Users" + File.separator + "Eric" + File.separator + "AppData" + File.separator + "Local" + File.separator + "Google" + File.separator + "Chrome" + File.separator + "Application" + File.separator + "chrome.exe";
		String chromeDriverPath = "C:" + File.separator + "apps" + File.separator + "chromedriver" + File.separator + "chromedriver.exe";
		capabilities.setCapability(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, chromeDriverPath);
		ChromeOptions options = new ChromeOptions();

		options.setBinary(chromeBinaryPath);
		LOGGER.info("ChromeDriver path: " + chromeDriverPath);
		LOGGER.info("ChromeBinary path: " + chromeBinaryPath);
		capabilities.setCapability("chrome.binary", chromeBinaryPath);
		capabilities.setCapability("webdriver.chrome.driver", chromeDriverPath);
		capabilities.setPlatform(Platform.WINDOWS);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		System.setProperty("chrome.binary",chromeBinaryPath);
		rewardsPage = new MyCokeRewardsPage(new ChromeDriver(capabilities));
	}
	
	public void recordMyCokeReward(String rewardString){
		LOGGER.info("Recording rewardString:" + rewardString);
		long start;
		long finish;
		
		start = System.currentTimeMillis();
		if(!rewardsPage.checkOnHomepage(properties.getProperty("mycokerewards.homepageurl"))){
			rewardsPage.goTo(properties.getProperty("mycokerewards.homepageurl"));
			if(!rewardsPage.checkOnHomepage(properties.getProperty("mycokerewards.homepageurl"))){
				rewardsPage.screenshotCurrentPage();
				throw new MyCokeRewardsPageException("Could not browse to homepage, looping back to page: " + rewardsPage.getDriver().getCurrentUrl());
			}else{
				LOGGER.info("Now on home page.");
			}
		}
		if(rewardsPage.checkLoggedInToHomepage()){
			finish = System.currentTimeMillis();
			LOGGER.info("Confirmed on homepage in " + (finish - start) + "ms");
		}else{
			finish = System.currentTimeMillis();
			LOGGER.info("Discovered not on homepage in " + (finish - start) + "ms");
			
			start = System.currentTimeMillis();
			rewardsPage.login(properties.getProperty("mycokerewards.loginurl"), properties.getProperty("mycokerewards.user.email"),properties.getProperty("mycokerewards.user.password"));
			finish = System.currentTimeMillis();
			LOGGER.info("Finished login attempt in " + (finish - start) + "ms");
		}
		
		if(rewardsPage.checkPageReadyForCode()){
			LOGGER.info("Rewards page is ready for code.");
			rewardsPage.recordCode(rewardString);
		}else{
			throw new MyCokeRewardsPageException("Page was not ready to recieve reward code.");
		}
		
		if(rewardsPage.checkIfDrinkSelectionNeeded()){
			rewardsPage.selectDrink();
		}
		
		if(rewardsPage.checkIfRecordedSuccessfully()){
			return;
		}
		rewardsPage.findRecordingErrorIfPresent();
		throw new MyCokeRewardsPageException("Workflow completed but reward code was not successfully recorded.");
	}

	public static String scrubRewardString(String rewardString) {
		if(rewardString == null || rewardString.isEmpty()){
			throw new RewardStringException("Reward string was null.");
		}
		String cleanRewardString = rewardString.replace(".", "");
		String outputString = "";
		String[] rewardStringTokens = cleanRewardString.split(" ");
		for(String rewardStringToken : rewardStringTokens){
			try{
			if(rewardStringToken.length() > 1){
				outputString += RewardStringNumbers.valueOf(rewardStringToken.toUpperCase()).getValueAsIntegerString();
			}else{
				RewardStringLetters.valueOf(rewardStringToken.toUpperCase()); //will throw an exception if unknown character is used
				outputString += rewardStringToken.toUpperCase();
			}
			}catch(IllegalArgumentException e){
				throw new RewardStringException("Could not map '" + rewardStringToken + "' to known MyCokeReward character.");
			}
		}
		LOGGER.info("Converted '" + rewardString + "' to '" + outputString + "'.");
		return outputString;
	}
	
	public void closeDriver(){
		long start = System.currentTimeMillis();
		rewardsPage.closeDriver();
		long finish = System.currentTimeMillis();
		LOGGER.info("Closed driver in " + (finish - start) + "ms");
	}
	
	public enum RewardStringLetters{
		B,
		F,
		H,
		J,
		K,
		L,
		M,
		N,
		P,
		R,
		T,
		V,
		W,
		X
	}
	
	public enum RewardStringNumbers{
		ZERO("0"),
		FOUR("4"),
		FIVE("5"),
		SIX("6"),
		SEVEN("7"),
		NINE("9");
		
		private String numericValue;
		
		private RewardStringNumbers(String numericValue){
			this.numericValue = numericValue;
		}
		
		public String getValueAsIntegerString(){
			return numericValue;
		}
	}
}
