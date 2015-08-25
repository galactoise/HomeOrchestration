package com.galactoise.homeorchestration.service.resource.alexa;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.galactoise.alexamodel.AlexaCard;
import com.galactoise.alexamodel.AlexaInput;
import com.galactoise.alexamodel.AlexaOutput;
import com.galactoise.alexamodel.AlexaOutputSpeech;
import com.galactoise.alexamodel.AlexaResponse;
import com.galactoise.homeorchestration.conversation.ConversationRequest;
import com.galactoise.homeorchestration.conversation.alexa.AlexaConversationConverter;
import com.galactoise.homeorchestration.service.resource.AbstractHomeOrchestrationResource;

@Path("/alexa/AdventureGame")
@Produces("application/json")
public class AdventureGameResource extends AbstractAlexaResource {

	protected static final Logger LOGGER = Logger.getLogger(AdventureGameResource.class.getName());
	
	@POST
	public AlexaOutput handleAdventureGameIntent(AlexaInput alexaInput){
		LOGGER.info("Alexa Input: " + alexaInput.toString());
		
		ConversationRequest conversationRequest = AlexaConversationConverter.convertAlexaInputToConversationRequest(alexaInput);
		
		LOGGER.info("Conversation Request: " + conversationRequest);
		
		return generateGenericAlexaOutput();
	}

	@Override
	public AlexaOutput doLaunchRequest(AlexaInput alexaInput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlexaOutput doIntentRequest(AlexaInput alexaInput) {
		// TODO Auto-generated method stub
		return null;
	}
}
