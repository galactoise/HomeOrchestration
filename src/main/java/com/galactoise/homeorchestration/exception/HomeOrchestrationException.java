package com.galactoise.homeorchestration.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeOrchestrationException {

private static final Logger LOGGER = Logger.getLogger(HomeOrchestrationException.class.getName());

	protected String errorId;
	protected String errorMessage;
	protected String errorCode;
	
	public HomeOrchestrationException(String errorId, String errorCode, String errorMessage){
		System.out.println("errorMessage: " + errorMessage);
		this.errorId = errorId;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		LOGGER.log(Level.SEVERE, errorMessage);
	}
	
	public String getErrorId() {
		return errorId;
	}
	
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
