package com.galactoise.homeorchestration.service.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.galactoise.homeorchestration.model.StatusResponse;

@Path("/")
@Produces("application/json")
public class RootResource extends AbstractHomeOrchestrationResource {

	@GET
	public Object getRoot(){
		return "{\"root\":null}";
	}
	
	@GET
	@Path("/status")
	public Object getServiceStatus(){
		return new StatusResponse();
	}
}
