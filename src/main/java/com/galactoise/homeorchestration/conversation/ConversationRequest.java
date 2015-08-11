package com.galactoise.homeorchestration.conversation;

import java.util.LinkedList;
import java.util.Map;

public class ConversationRequest {

	private final Map<String,Object> attributes;
	private final LinkedList<String> conversationHistory;
	private final String requestName;
	private final Boolean conversationTerminus;
	
	private ConversationRequest(Map<String, Object> attributes,
			LinkedList<String> conversationHistory, String requestName,
			Boolean conversationTerminus) {
		super();
		this.attributes = attributes;
		this.conversationHistory = conversationHistory;
		this.requestName = requestName;
		this.conversationTerminus = conversationTerminus;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	public LinkedList<String> getConversationHistory() {
		return conversationHistory;
	}
	
	public String getRequestName() {
		return requestName;
	}

	public Boolean isConversationTerminus() {
		return conversationTerminus;
	}
	
	public static ConversationRequestBuilder builder(){
		return new ConversationRequestBuilder();
	}
	
	public static class ConversationRequestBuilder{
		
		private Map<String,Object> attributes;
		private LinkedList<String> conversationHistory;
		private String requestName;
		private Boolean conversationTerminus;
		
		public ConversationRequestBuilder withAttributes(Map<String,Object> attributes){
			this.attributes = attributes;
			return this;
		}
		
		public ConversationRequestBuilder withConversationHistory(LinkedList<String> conversationHistory){
			this.conversationHistory = conversationHistory;
			return this;
		}
		
		public ConversationRequestBuilder withRequestName(String requestName){
			this.requestName = requestName;
			return this;
		}
		
		public ConversationRequestBuilder shouldTerminateConversation(Boolean conversationTerminus){
			this.conversationTerminus = conversationTerminus;
			return this;
		}
		
		public ConversationRequest build(){
			return new ConversationRequest(attributes, conversationHistory, requestName, conversationTerminus);
		}
	}
}
