package com.galactoise.homeorchestration.service.resource.alexa;

import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.galactoise.alexamodel.AlexaCard;
import com.galactoise.alexamodel.AlexaInput;
import com.galactoise.alexamodel.AlexaOutput;
import com.galactoise.alexamodel.AlexaOutputSpeech;
import com.galactoise.alexamodel.AlexaReprompt;
import com.galactoise.alexamodel.AlexaResponse;
import com.galactoise.homeorchestration.exception.conversation.alexa.UnknownIntentException;
import com.galactoise.homeorchestration.model.alexa.MyCokeRewardsIntents;
import com.galactoise.homeorchestration.service.manager.MyCokeRewardsManager;

@Path("/alexa/MyCokeRewards")
@Produces("application/json")
public class MyCokeRewardsResource extends AbstractAlexaResource {

	protected static final Logger LOGGER = Logger.getLogger(MyCokeRewardsResource.class.getName());
	
	public static final String LAST_REWARD_STRING_ATTRIBUTE = "lastRewardString";
	
	MyCokeRewardsManager myCokeRewardsManager;
	
	public MyCokeRewardsResource(){
		myCokeRewardsManager = new MyCokeRewardsManager();
	}
	
	@POST
	public AlexaOutput recordMyCokeReward(AlexaInput alexaInput){
		LOGGER.info(alexaInput.toString());
		
		AlexaOutput alexaOutput = generateGenericAlexaOutput();
		
		if(alexaInput == null || alexaInput.getRequest() == null){
			LOGGER.info("input was not valid.");
		}
		
		switch(alexaInput.getRequest().getType()){
		case LaunchRequest:
			alexaOutput = doLaunchRequest(alexaInput);
			break;
		case IntentRequest:
			alexaOutput = doIntentRequest(alexaInput);
			break;
		default:
			LOGGER.info("Intent type " + alexaInput.getRequest().getType() + " had no action associated with it.");
			break;
		}
		return alexaOutput;
	}

	@Override
	public AlexaOutput doLaunchRequest(AlexaInput alexaInput) {
		AlexaOutput alexaOutput = generateAlexaOutputFromAlexaInput(alexaInput);
		AlexaCard alexaCard = new AlexaCard();
		alexaCard.setTitle("Enter Code");
		alexaCard.setTitle("Please enter your MyCokeRewards code...");
		
		AlexaOutputSpeech alexaOutputSpeech = new AlexaOutputSpeech();
		alexaOutputSpeech.setText("Please enter your reward code");
		alexaOutputSpeech.setType(AlexaOutputSpeech.PLAIN_TEXT_TYPE);
					
		AlexaReprompt alexaReprompt = new AlexaReprompt();
		alexaReprompt.setOutputSpeech(alexaOutputSpeech);
		
		AlexaResponse alexaResponse = new AlexaResponse();
		alexaResponse.setOutputSpeech(alexaOutputSpeech);
		alexaResponse.setReprompt(alexaReprompt);
		alexaResponse.setCard(alexaCard);
		
		alexaOutput.setResponse(alexaResponse);
		
		return alexaOutput;
	}

	@Override
	public AlexaOutput doIntentRequest(AlexaInput alexaInput) {
		String intentName = alexaInput.getRequest().getIntent().getName();
		switch(intentName){
		case "REWARD":
			return doRewardIntentRequest(alexaInput);
		case "REPEAT":
			return doRepeatIntentRequest(alexaInput);
		default:
			throw new UnknownIntentException(alexaInput, "Could not find intent with name '" + intentName + "' for MyCokeRewards resource.");
		}
	}

	public AlexaOutput doRewardIntentRequest(AlexaInput alexaInput){

		String rewardString = alexaInput.getRequest().getIntent().getSlots().get("rewardString").getValue();
		
		myCokeRewardsManager.recordMyCokeReward(rewardString);		

		AlexaOutput alexaOutput = generateAlexaOutputFromAlexaInput(alexaInput);
		AlexaCard alexaCard = new AlexaCard();
		alexaCard.setTitle("Enter another code");
		alexaCard.setTitle("If you'd like to enter another code, please do so now");
		
		AlexaOutputSpeech alexaOutputSpeech = new AlexaOutputSpeech();
		alexaOutputSpeech.setText("If you'd like to enter another code, please do so now");
		alexaOutputSpeech.setType(AlexaOutputSpeech.PLAIN_TEXT_TYPE);
		
		AlexaResponse alexaResponse = new AlexaResponse();
		alexaResponse.setOutputSpeech(alexaOutputSpeech);
		alexaResponse.setCard(alexaCard);
		
		alexaOutput.setResponse(alexaResponse);
		
		alexaOutput.getSessionAttributes().put(LAST_REQUEST_INTENT_TYPE_ATTRIBUTE, MyCokeRewardsIntents.REWARD);	
		alexaOutput.getSessionAttributes().put(LAST_REWARD_STRING_ATTRIBUTE, rewardString);
		
		return alexaOutput;
	}
	
	private AlexaOutput doRepeatIntentRequest(AlexaInput alexaInput) {
		
		MyCokeRewardsIntents lastIntentType = MyCokeRewardsIntents.valueOf((String) alexaInput.getSession().getAttributes().get(LAST_REQUEST_INTENT_TYPE_ATTRIBUTE));
		if(!lastIntentType.equals(MyCokeRewardsIntents.REWARD) && !lastIntentType.equals(MyCokeRewardsIntents.REPEAT)){
			throw new UnknownIntentException(alexaInput, "Could not repeat input for intent type '" + lastIntentType + "'.");
		}
		
		String lastRewardString = (String) alexaInput.getSession().getAttributes().get(LAST_REWARD_STRING_ATTRIBUTE);

		AlexaOutput alexaOutput = generateAlexaOutputFromAlexaInput(alexaInput);
		AlexaCard alexaCard = new AlexaCard();
		alexaCard.setTitle(lastRewardString);
		alexaCard.setTitle("Last reward string recorded was, " + lastRewardString);
		
		AlexaOutputSpeech alexaOutputSpeech = new AlexaOutputSpeech();
		alexaOutputSpeech.setText(lastRewardString);
		alexaOutputSpeech.setType(AlexaOutputSpeech.PLAIN_TEXT_TYPE);
		
		AlexaResponse alexaResponse = new AlexaResponse();
		alexaResponse.setOutputSpeech(alexaOutputSpeech);
		alexaResponse.setCard(alexaCard);
		
		AlexaReprompt alexaReprompt = new AlexaReprompt();
		AlexaOutputSpeech alexaRepromptOutputSpeech = new AlexaOutputSpeech();
		alexaRepromptOutputSpeech.setText("If you'd like to hear it again, please say repeat.  Otherwise, enter another code.");
		alexaRepromptOutputSpeech.setType(AlexaOutputSpeech.PLAIN_TEXT_TYPE);
		alexaReprompt.setOutputSpeech(alexaRepromptOutputSpeech);		
		
		alexaOutput.setResponse(alexaResponse);
		
		alexaOutput.getSessionAttributes().put(LAST_REQUEST_INTENT_TYPE_ATTRIBUTE, MyCokeRewardsIntents.REPEAT);
		
		return alexaOutput;
	}
}
