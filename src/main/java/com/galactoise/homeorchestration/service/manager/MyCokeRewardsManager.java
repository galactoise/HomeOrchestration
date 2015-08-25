package com.galactoise.homeorchestration.service.manager;

import java.util.logging.Logger;

public class MyCokeRewardsManager {


	protected static final Logger LOGGER = Logger.getLogger(MyCokeRewardsManager.class.getName());
	
	public void recordMyCokeReward(String rewardString){
		LOGGER.info("[rewardString:" + rewardString + "]");
	}
}
