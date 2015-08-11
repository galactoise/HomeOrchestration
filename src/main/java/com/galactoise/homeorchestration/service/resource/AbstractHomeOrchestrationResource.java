package com.galactoise.homeorchestration.service.resource;

import java.util.logging.Logger;

public abstract class AbstractHomeOrchestrationResource {

	protected static final Logger LOGGER = Logger.getLogger(AbstractHomeOrchestrationResource.class.getName());
	public static final String NOT_IMPLEMENTED_OUTPUT = "{\"NOT_IMPLEMENTED\":\"Endpoint is not yet implemented.\"}";
	
}
