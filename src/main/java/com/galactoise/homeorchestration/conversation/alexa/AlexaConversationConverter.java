package com.galactoise.homeorchestration.conversation.alexa;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.galactoise.alexamodel.AlexaInput;
import com.galactoise.alexamodel.AlexaRequestType;
import com.galactoise.homeorchestration.conversation.ConversationRequest;
import com.galactoise.homeorchestration.conversation.ConversationRequest.ConversationRequestBuilder;

public class AlexaConversationConverter {

	public static ConversationRequest convertAlexaInputToConversationRequest(AlexaInput alexaInput){
		ConversationRequestBuilder conversationRequestBuilder = ConversationRequest.builder();
		
		if(alexaInput == null){
			return conversationRequestBuilder.build();
		}
		
		if(alexaInput.getSession() != null && alexaInput.getSession().getAttributes() != null){
			HashMap<String,Object> attributes = alexaInput.getSession().getAttributes();
			
			if(attributes.containsKey("conversationHistory")){
				LinkedList<String> conversationHistory = new LinkedList<String>();
				conversationHistory.addAll((List<String>) attributes.remove("conversationHistory"));
				conversationRequestBuilder = conversationRequestBuilder.withConversationHistory(conversationHistory);
			}

			conversationRequestBuilder = conversationRequestBuilder.withAttributes(attributes);
		}
		
		if(alexaInput.getRequest() != null && alexaInput.getRequest().getIntent() != null){
			conversationRequestBuilder = conversationRequestBuilder.withRequestName(alexaInput.getRequest().getIntent().getName());
		}else if(alexaInput.getRequest() != null && alexaInput.getRequest().getType() != null){
			if(alexaInput.getRequest().getType().equals(AlexaRequestType.LaunchRequest)){
				conversationRequestBuilder = conversationRequestBuilder.withRequestName("LAUNCH");
			}else if(alexaInput.getRequest().getType().equals(AlexaRequestType.SessionEndedRequest)){
				conversationRequestBuilder = conversationRequestBuilder.withRequestName("END");
			}
		}
		
		return conversationRequestBuilder.build();
	}
}
