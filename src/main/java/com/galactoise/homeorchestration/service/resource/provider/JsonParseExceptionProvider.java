package com.galactoise.homeorchestration.service.resource.provider;

import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonParseException;
import com.galactoise.homeorchestration.exception.MalformedJSONException;

@Provider
public class JsonParseExceptionProvider implements ExceptionMapper<JsonParseException> {

	private static final Logger LOGGER = Logger.getLogger(JsonParseExceptionProvider.class.getName());

	@Override
	public Response toResponse(JsonParseException arg0) {
		LOGGER.info(arg0.getOriginalMessage());
		return (new MalformedJSONException(String.valueOf(Math.random()),"Malformed JSON, could not deserialize.  " + arg0.getMessage())).getResponse();
	}

}
