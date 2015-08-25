package com.galactoise.homeorchestration.service.resource.provider;

import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.galactoise.homeorchestration.exception.HomeOrchestrationException;
import com.galactoise.homeorchestration.exception.InvalidJSONException;

@Provider
public class JsonMappingExceptionProvider implements ExceptionMapper<JsonMappingException> {

	private static final Logger LOGGER = Logger.getLogger(JsonMappingExceptionProvider.class.getName());
	
	@Override
	public Response toResponse(JsonMappingException arg0) {
		LOGGER.info(arg0.getOriginalMessage());
		return (new InvalidJSONException(String.valueOf(Math.random()),"Could not deserialize JSON into object.  " + arg0.getMessage())).getResponse();
	}

}
