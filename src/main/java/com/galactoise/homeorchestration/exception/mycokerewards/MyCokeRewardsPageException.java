package com.galactoise.homeorchestration.exception.mycokerewards;

import com.galactoise.homeorchestration.exception.HomeOrchestrationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@SuppressWarnings("serial")
public class MyCokeRewardsPageException extends WebApplicationException {

	protected static final String errorCode = "MY_COKE_REWARDS_PAGE_EXCEPTION";
	
	public MyCokeRewardsPageException(String errorMessage) {
		super(Response.status(Response.Status.BAD_REQUEST).entity(
				new HomeOrchestrationException("", errorCode, errorMessage)).type(MediaType.APPLICATION_JSON)
				.build()); 
	}
}
