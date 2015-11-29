package ca.architech.registration.controller.domain;

public enum RegistrationStatus {

	OK("OK"),
	ERROR("ERROR");
	
	private final String status;
	
	RegistrationStatus(String status) {
		this.status = status;
	}
	
	String getStatus() {
		return this.status;
	}
}
