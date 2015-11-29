package ca.architech.registration.service;

public interface ValidationService {

	public boolean validateMinLength(String input, int minLength);
	
	public boolean validateMaxLength(String input, int maxLength);
	
	public boolean validateHasUppercaseLetters(String input);
	
	public boolean validateHasLowercaseLetters(String input);
	
	public boolean validateHasDigits(String input);
	
	public boolean validateHasNoSpecialCharacters(String input);
}
