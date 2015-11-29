package ca.architech.registration.service;

import java.util.List;

import ca.architech.registration.domain.User;

public interface RegistrationService {

	public List<String> validateNewUser(User user);
	
	public void registerNewUser(User user);
	
}
