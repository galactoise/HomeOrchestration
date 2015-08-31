package com.galactoise.homeorchestration.service.manager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Logger;

import com.galactoise.homeorchestration.exception.mycokerewards.RewardStringException;

public class MyCokeRewardsManager {

	protected static final Logger LOGGER = Logger.getLogger(MyCokeRewardsManager.class.getName());
	protected static final HashSet<Character> VALID_REWARD_STRING_LETTERS = new HashSet<Character>(Arrays.asList('b','f','h','j','k','l','m','n','p','r','t','v','w','x'));
	
	public void recordMyCokeReward(String rewardString){
		String cleanRewardString = scrubRewardString(rewardString);
		LOGGER.info("[rewardString:" + cleanRewardString + "]");
	}

	protected static String scrubRewardString(String rewardString) {
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
