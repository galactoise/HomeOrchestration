package com.galactoise.homeorchestration.exception.conversation.alexa;

import java.util.logging.Logger;

import com.galactoise.alexamodel.AlexaCard;
import com.galactoise.alexamodel.AlexaInput;
import com.galactoise.alexamodel.AlexaOutput;
import com.galactoise.alexamodel.AlexaOutputSpeech;
import com.galactoise.alexamodel.AlexaResponse;

public class AlexaException extends AlexaOutput {

	protected static final Logger LOGGER = Logger.getLogger(AlexaException.class.getName());

	public AlexaException(AlexaInput alexaInput, String errorMessage){
		LOGGER.info(errorMessage);
		setAlexaErrorOutputFromAlexaInput(alexaInput, errorMessage);
	}
	
	public void setAlexaErrorOutputFromAlexaInput(AlexaInput alexaInput, String errorMessage){
		
		setSessionAttributes(alexaInput.getSession().getAttributes());
		
		AlexaCard alexaCard = new AlexaCard();
		alexaCard.setTitle("ERROR");
		alexaCard.setTitle(errorMessage);
		
		AlexaOutputSpeech alexaOutputSpeech = new AlexaOutputSpeech();
		alexaOutputSpeech.setText(errorMessage);
		alexaOutputSpeech.setType(AlexaOutputSpeech.PLAIN_TEXT_TYPE);
		
		AlexaResponse alexaResponse = new AlexaResponse();
		alexaResponse.setOutputSpeech(alexaOutputSpeech);
		alexaResponse.setShouldEndSession(true);
		alexaResponse.setCard(alexaCard);
		
		setResponse(alexaResponse);
	}
}
