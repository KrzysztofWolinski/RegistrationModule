package ca.architech.registration.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;

import ca.architech.registration.domain.User;
import ca.architech.registration.repository.UserRepository;
import ca.architech.registration.service.RegistrationService;
import ca.architech.registration.service.ValidationService;

public class RegistrationServiceImpl implements RegistrationService {

	@Inject
	private UserRepository userRepository;
	
	@Inject 
	private ValidationService validationService;
	
	
	@Value("${validation.username.min-length}")
	private int usernameMinLength;
	
	@Value("${validation.password.min-length}")
	private int passwordMinLength;
	
	@Value("${error.message.username.special-characters}")
	private String usernameSpecialCharactersError;
	
	@Value("${error.message.username.too-short}")
	private String usernameTooShortError;
	
	@Value("${error.message.username.not-unique}")
	private String usernameNotUniqueError;
	
	@Value("${error.message.password.too-short}")
	private String passwordTooShortError;
	
	@Value("${error.message.password.too-simple}")
	private String passwordTooSimpleError;
	
	
	@Override
	public void registerNewUser(User user) {	
		userRepository.save(user);		
	}

	@Override
	public List<String> validateNewUser(User user) {
		List<String> errorList = new ArrayList<String>();
		
		String username = user.getUsername();
		String password = user.getPassword();
		
		if (!validationService.validateHasNoSpecialCharacters(username)) {
			errorList.add(usernameSpecialCharactersError);
		}
		
		if (!validationService.validateMinLength(username, usernameMinLength)) {
			errorList.add(usernameTooShortError);
		}
		
		if (!isUsernameUnique(username)) {
			errorList.add(usernameNotUniqueError);
		}
		
		if (!validationService.validateMinLength(password, passwordMinLength)) {
			errorList.add(passwordTooShortError);
		}
		
		if (!isPasswordComplicatedEnough(password)) {
			errorList.add(passwordTooSimpleError);
		}
		
		return errorList;
	}

	private boolean isUsernameUnique(String username) {
		User user = this.userRepository.findByUsername(username);
		
		if (user == null) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isPasswordComplicatedEnough(String password) {
		if (validationService.validateHasLowercaseLetters(password) && 
			validationService.validateHasUppercaseLetters(password) && 
			validationService.validateHasDigits(password)) {
			return true;
		} else {
			return false;
		}
	}
}
