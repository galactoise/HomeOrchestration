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
import com.galactoise.alexamodel.AlexaRequest;
import com.galactoise.alexamodel.AlexaRequestIntent;
import com.galactoise.alexamodel.AlexaRequestType;
import com.galactoise.alexamodel.AlexaResponse;
import com.galactoise.homeorchestration.service.manager.MyCokeRewardsManager;

@Path("/alexa/MyCokeRewards")
@Produces("application/json")
public class MyCokeRewardsResource extends AbstractAlexaResource {

	protected static final Logger LOGGER = Logger.getLogger(MyCokeRewardsResource.class.getName());
	
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
			doIntentRequest(alexaInput.getRequest());
			break;
		default:
			LOGGER.info("Intent type " + alexaInput.getRequest().getType() + " had no action associated with it.");
			break;
		}
		return alexaOutput;
	}

	private void doIntentRequest(AlexaRequest alexaRequest) {
		String rewardString = alexaRequest.getIntent().getSlots().get("rewardString").getValue();
		
		myCokeRewardsManager.recordMyCokeReward(rewardString);
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
		
		alexaOutput.setResponse(alexaResponse);
		
		return alexaOutput;
	}

	@Override
	public AlexaOutput doIntentRequest(AlexaInput alexaInput) {
		String rewardString = alexaInput.getRequest().getIntent().getSlots().get("rewardString").getValue();
		
		myCokeRewardsManager.recordMyCokeReward(rewardString);
		return null;
	}
}
