package com.galactoise.homeorchestration.service.resource.alexa;

import java.util.HashMap;

import com.galactoise.alexamodel.AlexaCard;
import com.galactoise.alexamodel.AlexaInput;
import com.galactoise.alexamodel.AlexaOutput;
import com.galactoise.alexamodel.AlexaOutputSpeech;
import com.galactoise.alexamodel.AlexaResponse;
import com.galactoise.homeorchestration.service.resource.AbstractHomeOrchestrationResource;

public abstract class AbstractAlexaResource extends AbstractHomeOrchestrationResource {

	
	public AlexaOutput generateGenericAlexaOutput(){

    	AlexaOutput output = new AlexaOutput();
    	output.setSessionAttributes(new HashMap<String,Object>());
    	
    	AlexaResponse response = new AlexaResponse();
    	
    	AlexaOutputSpeech outputSpeech = new AlexaOutputSpeech();
    	outputSpeech.setText("Test Output");
    	response.setOutputSpeech(outputSpeech);
    	
    	AlexaCard card = new AlexaCard();
    	card.setContent("Test Output");
    	card.setTitle("Test Output");
    	response.setCard(card);
    	
    	response.setShouldEndSession(true);
    	
    	output.setResponse(response);
    	
    	return output;
	}
	
	public AlexaOutput generateAlexaOutputFromAlexaInput(AlexaInput input){

    	AlexaOutput output = new AlexaOutput();
    	output.setSessionAttributes(input.getSession().getAttributes());
    	
    	AlexaResponse response = new AlexaResponse();
    	
    	output.setResponse(response);
    	
    	return output;
	}
	
	public abstract AlexaOutput doLaunchRequest(AlexaInput alexaInput);
	
	public abstract AlexaOutput doIntentRequest(AlexaInput alexaInput);
}
