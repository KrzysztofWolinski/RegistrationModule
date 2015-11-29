package ca.architech.registration.service.impl;

import java.util.regex.Pattern;

import ca.architech.registration.service.ValidationService;

public class ValidationServiceImpl implements ValidationService {

	@Override
	public boolean validateMinLength(String input, int minLength) {
		if (input.length() >= minLength) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean validateMaxLength(String input, int maxLength) {
		if (input.length() < maxLength) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean validateHasUppercaseLetters(String input) {
		String upperInput = input.toLowerCase();
		
		if (upperInput.equals(input)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean validateHasLowercaseLetters(String input) {
		String lowercaseInput = input.toUpperCase();
		
		if (lowercaseInput.equals(input)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean validateHasDigits(String input) {
		return input.matches(".*\\d+.*");
	}

	@Override
	public boolean validateHasNoSpecialCharacters(String input) {
		Pattern p = Pattern.compile("^.*[^a-zA-Z0-9 ].*$");
		return !p.matcher(input).find();
	}
	
}
