package com.galactoise.homeorchestration.service.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.galactoise.homeorchestration.exception.mycokerewards.RewardStringException;
import com.galactoise.homeorchestration.util.MyCokeRewardsDriverSingleton;
import com.galactoise.homeorchestration.util.MyCokeRewardsPage;

public class MyCokeRewardsManagerTest {

	@Test
	public void testScrubRewardString(){
		String rewardString = "w t f six nine";
		String output = MyCokeRewardsManager.scrubRewardString(rewardString);
		assertNotNull(output);
		assertEquals("WTF69", output);
	}

	@Test(expected = RewardStringException.class)
	public void testScrubRewardString_nullStringThrowsException(){
		String rewardString = null;
		MyCokeRewardsManager.scrubRewardString(rewardString);
	}

	@Test(expected = RewardStringException.class)
	public void testScrubRewardString_emptyStringThrowsException(){
		String rewardString = null;
		MyCokeRewardsManager.scrubRewardString(rewardString);
	}

	@Test
	public void testScrubRewardString_periodsReplaced(){
		String rewardString = "w. t. f. six. nine. ..";
		String output = MyCokeRewardsManager.scrubRewardString(rewardString);
		assertNotNull(output);
		assertEquals("WTF69", output);
	}

	@Test
	public void testScrubRewardString_wordsReplaced(){
		String rewardString = "are and in be seven zero";
		String output = MyCokeRewardsManager.scrubRewardString(rewardString);
		assertNotNull(output);
		assertEquals("RNNB70", output);
	}

	@Test(expected = RewardStringException.class)
	public void testScrubRewardString_invalidLetter(){
		String rewardString = "w t s six nine";
		MyCokeRewardsManager.scrubRewardString(rewardString);
	}

	@Test(expected = RewardStringException.class)
	public void testScrubRewardString_invalidNumber(){
		String rewardString = "w t f six three";
		MyCokeRewardsManager.scrubRewardString(rewardString);
	}

	@Test
	public void testScrubRewardString_caseInsensitive(){
		String rewardString = "w T f Six NINE";
		String output = MyCokeRewardsManager.scrubRewardString(rewardString);
		assertNotNull(output);
		assertEquals("WTF69", output);
	}
	
	@Test
	@Ignore
	public void testRecordRewardString(){
		String rewardString = "VK4THKTJJ5WFF9";
		
		MyCokeRewardsManager myCokeRewardsManager = new MyCokeRewardsManager();
		myCokeRewardsManager.recordMyCokeReward(rewardString);
	}
	
	@Test
	@Ignore
	public void testSingleton(){
		MyCokeRewardsDriverSingleton instance = MyCokeRewardsDriverSingleton.getInstance();
		assertNotNull(instance);
		WebDriver driver = instance.getDriver();
		assertNotNull(driver);
		MyCokeRewardsPage rewardsPage = new MyCokeRewardsPage(driver);
		assertNotNull(rewardsPage);
	}
}
