package com.galactoise.homeorchestration.service.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.galactoise.homeorchestration.exception.mycokerewards.RewardStringException;

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
}
