package com.galactoise.homeorchestration.conversation.alexa.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.galactoise.alexamodel.AlexaInput;

public class UnknownIntentException extends WebApplicationException {

	private static final long serialVersionUID = 2244313135765341142L;
	
	public UnknownIntentException(AlexaInput alexaInput, String errorMessage) {
		super(Response.status(Response.Status.BAD_REQUEST).entity(
				new AlexaException(alexaInput, errorMessage)).type(MediaType.APPLICATION_JSON)
				.build()); 
	}
}
