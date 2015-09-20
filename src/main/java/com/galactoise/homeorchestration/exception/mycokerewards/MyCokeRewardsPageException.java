package com.galactoise.homeorchestration.exception.mycokerewards;

import com.galactoise.homeorchestration.exception.HomeOrchestrationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@SuppressWarnings("serial")
public class MyCokeRewardsPageException extends WebApplicationException {

	protected static final String errorCode = "MY_COKE_REWARDS_PAGE_EXCEPTION";
	
	private boolean recoverable = false;
	private String errorMessage;
	
	public MyCokeRewardsPageException(String errorMessage) {
		this(errorMessage, false);
	}
	
	public MyCokeRewardsPageException(String errorMessage, boolean recoverable) {
		super(Response.status(Response.Status.BAD_REQUEST).entity(
				new HomeOrchestrationException("", errorCode, errorMessage)).type(MediaType.APPLICATION_JSON)
				.build()); 
		this.errorMessage = errorMessage;
		this.recoverable = recoverable;
	}

	public boolean isRecoverable() {
		return recoverable;
	}

	public void setRecoverable(boolean recoverable) {
		this.recoverable = recoverable;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
