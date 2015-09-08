package com.galactoise.homeorchestration.service.manager;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.galactoise.homeorchestration.exception.mycokerewards.RewardStringException;
import com.galactoise.homeorchestration.util.MyCokeRewardsPage;
import com.galactoise.homeorchestration.util.PropertiesSingleton;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class MyCokeRewardsManager {
	
	private Properties properties;
	MyCokeRewardsPage rewardsPage;

	protected static final Logger LOGGER = Logger.getLogger(MyCokeRewardsManager.class.getName());
	protected static final HashSet<Character> VALID_REWARD_STRING_LETTERS = new HashSet<Character>(Arrays.asList('b','f','h','j','k','l','m','n','p','r','t','v','w','x'));
	
	public MyCokeRewardsManager(){
		properties = PropertiesSingleton.getPropertiesSingletonInstance().getProperties();
		DesiredCapabilities capability = DesiredCapabilities.htmlUnit();
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		/*capability.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, properties.getProperty("phantomjs.executable.path"));
		rewardsPage = new MyCokeRewardsPage(new PhantomJSDriver(capability));*/
		System.setProperty("webdriver.chrome.driver", "C:\\apps\\chromedriver\\chromedriver.exe");
		rewardsPage = new MyCokeRewardsPage(new ChromeDriver());
	}
	
	public void recordMyCokeReward(String rewardString){
		LOGGER.info("Recording rewardString:" + rewardString);
		
		rewardsPage.goTo(properties.getProperty("mycokerewards.loginurl"));
		LOGGER.info(rewardsPage.getTitle());
		rewardsPage.login(properties.getProperty("mycokerewards.user.email"),properties.getProperty("mycokerewards.user.password"));
		
		rewardsPage.closeDriver();
		
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
