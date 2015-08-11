package com.galactoise.homeorchestration.conversation.alexa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.galactoise.alexamodel.AlexaInput;
import com.galactoise.alexamodel.AlexaRequest;
import com.galactoise.alexamodel.AlexaRequestIntent;
import com.galactoise.alexamodel.AlexaSession;
import com.galactoise.homeorchestration.conversation.ConversationRequest;

public class AlexaConversationConverterTest {

	AlexaInput alexaInput;
	
	@Before
	public void setup(){
		alexaInput = new AlexaInput();
		
		AlexaSession alexaSession = new AlexaSession();
		
		HashMap<String,Object> attributes = new HashMap<String,Object>();
		attributes.put("firstAttribute", "firstValue");
		
		LinkedList<String> conversationHistory = new LinkedList<String>();
		conversationHistory.add("firstItem");
		attributes.put("conversationHistory", conversationHistory);
		
		alexaSession.setAttributes(attributes);
		
		alexaInput.setSession(alexaSession);
		
		AlexaRequest alexaRequest = new AlexaRequest();
		
		AlexaRequestIntent alexaIntent = new AlexaRequestIntent();
		alexaIntent.setName("intentName");
		
		alexaRequest.setIntent(alexaIntent);
		
		alexaInput.setRequest(alexaRequest);
	}
	
	@Test
	public void testConvertAlexaInputToConversationRequest(){
		ConversationRequest conversationRequest = AlexaConversationConverter.convertAlexaInputToConversationRequest(alexaInput);
		assertNotNull(conversationRequest);
		
		assertNotNull(conversationRequest.getAttributes());
		assertNotNull(conversationRequest.getConversationHistory());
		assertNotNull(conversationRequest.getRequestName());
		assertNull(conversationRequest.isConversationTerminus());
		
		assertTrue(conversationRequest.getAttributes().containsKey("firstAttribute"));
		assertFalse(conversationRequest.getAttributes().containsKey("conversationHistory"));
		
		assertTrue(conversationRequest.getConversationHistory().contains("firstItem"));
		
		assertEquals(conversationRequest.getRequestName(), "intentName");
	}
	
	@Test
	public void testConvertAlexaInputToConversationRequest_nullAttributes(){
		
		alexaInput.getSession().setAttributes(null);
		ConversationRequest conversationRequest = AlexaConversationConverter.convertAlexaInputToConversationRequest(alexaInput);
		assertNotNull(conversationRequest);
		
		assertNull(conversationRequest.getAttributes());
		assertNull(conversationRequest.getConversationHistory());
		assertNotNull(conversationRequest.getRequestName());
		assertNull(conversationRequest.isConversationTerminus());
		
		assertEquals(conversationRequest.getRequestName(), "intentName");
	}
	
	@Test
	public void testConvertAlexaInputToConversationRequest_emptyAttributes(){
		
		alexaInput.getSession().setAttributes(new HashMap<String,Object>());
		ConversationRequest conversationRequest = AlexaConversationConverter.convertAlexaInputToConversationRequest(alexaInput);
		assertNotNull(conversationRequest);
		
		assertNotNull(conversationRequest.getAttributes());
		assertNull(conversationRequest.getConversationHistory());
		assertNotNull(conversationRequest.getRequestName());
		assertNull(conversationRequest.isConversationTerminus());
		
		assertFalse(conversationRequest.getAttributes().containsKey("firstAttribute"));
		assertFalse(conversationRequest.getAttributes().containsKey("conversationHistory"));
		
		assertEquals(conversationRequest.getRequestName(), "intentName");
	}
	
	@Test
	public void testConvertAlexaInputToConversationRequest_nullConversationHistory(){
		
		alexaInput.getSession().getAttributes().remove("conversationHistory");
		ConversationRequest conversationRequest = AlexaConversationConverter.convertAlexaInputToConversationRequest(alexaInput);
		assertNotNull(conversationRequest);
		
		assertNotNull(conversationRequest.getAttributes());
		assertNull(conversationRequest.getConversationHistory());
		assertNotNull(conversationRequest.getRequestName());
		assertNull(conversationRequest.isConversationTerminus());
		
		assertTrue(conversationRequest.getAttributes().containsKey("firstAttribute"));
		assertFalse(conversationRequest.getAttributes().containsKey("conversationHistory"));
		
		assertEquals(conversationRequest.getRequestName(), "intentName");
	}
	
	@Test
	public void testConvertAlexaInputToConversationRequest_nullRequestName(){
		alexaInput.getRequest().getIntent().setName(null);
		ConversationRequest conversationRequest = AlexaConversationConverter.convertAlexaInputToConversationRequest(alexaInput);
		assertNotNull(conversationRequest);
		
		assertNotNull(conversationRequest.getAttributes());
		assertNotNull(conversationRequest.getConversationHistory());
		assertNull(conversationRequest.getRequestName());
		assertNull(conversationRequest.isConversationTerminus());
		
		assertTrue(conversationRequest.getAttributes().containsKey("firstAttribute"));
		assertFalse(conversationRequest.getAttributes().containsKey("conversationHistory"));
		
		assertTrue(conversationRequest.getConversationHistory().contains("firstItem"));
	}
	
	@Test
	public void testConvertAlexaInputToConversationRequest_nullAlexaInput(){
		ConversationRequest conversationRequest = AlexaConversationConverter.convertAlexaInputToConversationRequest(null);
		assertNotNull(conversationRequest);
		
		assertNull(conversationRequest.getAttributes());
		assertNull(conversationRequest.getConversationHistory());
		assertNull(conversationRequest.getRequestName());
		assertNull(conversationRequest.isConversationTerminus());
	}
	
	//Jackson by default will serialize an unknown list into an ArrayList
	@Test
	public void testConvertAlexaInputToConversationRequest_arrayListConversationHistory(){
		ArrayList<String> conversationHistory = new ArrayList<String>();
		conversationHistory.add("firstItem");
		alexaInput.getSession().getAttributes().put("conversationHistory", conversationHistory);
		ConversationRequest conversationRequest = AlexaConversationConverter.convertAlexaInputToConversationRequest(alexaInput);
		assertNotNull(conversationRequest);
		
		assertNotNull(conversationRequest.getAttributes());
		assertNotNull(conversationRequest.getConversationHistory());
		assertNotNull(conversationRequest.getRequestName());
		assertNull(conversationRequest.isConversationTerminus());
		
		assertTrue(conversationRequest.getAttributes().containsKey("firstAttribute"));
		assertFalse(conversationRequest.getAttributes().containsKey("conversationHistory"));
		
		assertTrue(conversationRequest.getConversationHistory().contains("firstItem"));
		
		assertEquals(conversationRequest.getRequestName(), "intentName");
	}
}
