package com.galactoise.homeorchestration.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@SuppressWarnings("serial")
public class MalformedJSONException extends WebApplicationException {

	protected static final String errorCode = "MALFORMED";
	
	public MalformedJSONException(String errorId, String errorMessage) {
		super(Response.status(Response.Status.BAD_REQUEST).entity(
				new HomeOrchestrationException(errorId, errorCode, errorMessage)).type(MediaType.APPLICATION_JSON)
				.build()); 
	}
}
