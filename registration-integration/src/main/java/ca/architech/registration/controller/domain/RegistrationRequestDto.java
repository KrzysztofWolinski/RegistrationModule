package ca.architech.registration.controller.domain;

import ca.architech.registration.domain.User;


public class RegistrationRequestDto {

	private String username;
	
	private String password;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public User getUser() {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		return user;
	}
	
}
