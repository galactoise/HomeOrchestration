package com.galactoise.homeorchestration.service.resource.alexa;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.galactoise.homeorchestration.service.manager.HueProxyManager;
import com.galactoise.homeorchestration.service.resource.AbstractHomeOrchestrationResource;

@Path("/alexa/HueProxy")
public class HueProxyResource extends AbstractHomeOrchestrationResource {

	protected static final Logger LOGGER = Logger.getLogger(HueProxyResource.class.getName());
	
	private HueProxyManager hueProxyManager;
	
	public HueProxyResource(){
		hueProxyManager = new HueProxyManager();
	}
	
	@GET
	public String getHueProxyRoot(){
		return NOT_IMPLEMENTED_OUTPUT;
	}
}
