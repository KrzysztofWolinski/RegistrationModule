package ca.architech.registration.controller.domain;

import java.util.ArrayList;
import java.util.List;


public class RegistrationResponseDto {

	private RegistrationStatus status;
	
	private List<String> messages; 
	
	public RegistrationStatus getStatus() {
		return status;
	}

	public void setStatus(RegistrationStatus status) {
		this.status = status;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	public void addMessage(String message) {
		if (this.messages == null) {
			this.messages = new ArrayList<String>();
		}
		
		this.messages.add(message);
	}

}
