package com.galactoise.homeorchestration.conversation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

import com.galactoise.homeorchestration.conversation.ConversationRequest.ConversationRequestBuilder;

public class ConversationRequestBuilderTest {

	
	@Test
	public void testConversationBuilder_constructsFromConversationRequest(){
		ConversationRequestBuilder conversationRequestBuilder = ConversationRequest.builder();
		assertNotNull(conversationRequestBuilder);
		
		ConversationRequest conversationRequest = conversationRequestBuilder.build();
		assertNotNull(conversationRequest);
	}
	
	@Test
	public void testConversationBuilder_constructsStatically(){
		ConversationRequestBuilder conversationRequestBuilder = new ConversationRequest.ConversationRequestBuilder();
		assertNotNull(conversationRequestBuilder);
		
		ConversationRequest conversationRequest = conversationRequestBuilder.build();
		assertNotNull(conversationRequest);
	}
	
	@Test
	public void testConversationBuilder_withAttributes(){
		HashMap<String,Object> attributes = new HashMap<String,Object>();
		attributes.put("firstAttribute", "firstValue");
		
		ConversationRequest conversationRequest = ConversationRequest.builder().withAttributes(attributes).build();
		assertNotNull(conversationRequest);
		
		assertNotNull(conversationRequest.getAttributes());
		
		assertTrue(attributes == conversationRequest.getAttributes());
	}
	
	@Test
	public void testConversationBuilder_withConversationHistory(){
		LinkedList<String> conversationHistory = new LinkedList<String>();
		conversationHistory.add("firstEntry");
		
		ConversationRequest conversationRequest = ConversationRequest.builder().withConversationHistory(conversationHistory).build();
		assertNotNull(conversationRequest);
		
		assertNotNull(conversationRequest.getConversationHistory());
		
		assertEquals(conversationHistory, conversationRequest.getConversationHistory());
	}
	
	@Test
	public void testConversationBuilder_withRequestName(){
		String requestName = "request";
		
		ConversationRequest conversationRequest = ConversationRequest.builder().withRequestName(requestName).build();
		assertNotNull(conversationRequest);
		
		assertNotNull(conversationRequest.getRequestName());
		
		assertEquals(requestName, conversationRequest.getRequestName());
	}
	
	@Test
	public void testConversationBuilder_withConversationTerminus(){
		ConversationRequest conversationRequest = ConversationRequest.builder().shouldTerminateConversation(true).build();
		assertNotNull(conversationRequest);
		
		assertNotNull(conversationRequest.isConversationTerminus());
		
		assertTrue(conversationRequest.isConversationTerminus());
	}
}
