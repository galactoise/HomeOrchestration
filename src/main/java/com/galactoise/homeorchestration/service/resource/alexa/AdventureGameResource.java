package com.galactoise.homeorchestration.service.resource.alexa;

import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.galactoise.alexamodel.AlexaInput;
import com.galactoise.homeorchestration.service.resource.AbstractHomeOrchestrationResource;

@Path("/alexa/AdventureGame")
public class AdventureGameResource extends AbstractHomeOrchestrationResource {

	protected static final Logger LOGGER = Logger.getLogger(AdventureGameResource.class.getName());
	
	@POST
	public String handleAdventureGameIntent(AlexaInput alexaInput){
		LOGGER.info(alexaInput.toString());
		
		
		
		return NOT_IMPLEMENTED_OUTPUT;
	}
}
