package com.galactoise.homeorchestration.model;

public class StatusResponse {

	private StatusLevel status = StatusLevel.GREEN;
	
	public StatusLevel getStatus() {
		return status;
	}

	public void setStatus(StatusLevel status) {
		this.status = status;
	}

	public enum StatusLevel{
		GREEN,
		YELLOW,
		RED;
	}
}
