package ca.architech.registration.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.architech.registration.service.impl.ValidationServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ValidationServiceImplTest.Config.class })
public class ValidationServiceImplTest {

	@Inject
	ValidationService validationService;
	
	@Test
	public void shouldInitializeContext() {
		
	}
	
	@Test
	public void shouldValidateMinLength() {
		String sampleInvalidString = "abc";
		String sampleValidString = "abcdefg";
		int minLength = 5;
		
		boolean invalidResult = validationService.validateMinLength(sampleInvalidString, minLength);
		boolean validResult = validationService.validateMinLength(sampleValidString, minLength);
		
		assertThat("Valid input " + sampleValidString + " not recognised", validResult, is(true));
		assertThat("Invalid input " + sampleInvalidString + " recognised as valid", invalidResult, is(false));
	}
	
	@Test
	public void shouldValidateMaxLength() {
		String sampleValidString = "abc";
		String sampleInvalidString = "abcdefg";
		int maxLength = 5;
		
		boolean invalidResult = validationService.validateMaxLength(sampleInvalidString, maxLength);
		boolean validResult = validationService.validateMaxLength(sampleValidString, maxLength);
		
		assertThat("Valid input " + sampleValidString + " not recognised", validResult, is(true));
		assertThat("Invalid input " + sampleInvalidString + " recognised as valid", invalidResult, is(false));
	}
	
	@Test
	public void shouldValidateUppercaseLetters() {
		String sampleValidString = "aBcDef";
		String sampleInvalidString = "abcdefg";
		
		boolean invalidResult = validationService.validateHasUppercaseLetters(sampleInvalidString);
		boolean validResult = validationService.validateHasUppercaseLetters(sampleValidString);
		
		assertThat("Valid input " + sampleValidString + " not recognised", validResult, is(true));
		assertThat("Invalid input " + sampleInvalidString + " recognised as valid", invalidResult, is(false));
	}
	
	@Test
	public void shouldValidateLowercaseLetters() {
		String sampleValidString = "aBcDef";
		String sampleInvalidString = "ABCDEF";
		
		boolean invalidResult = validationService.validateHasLowercaseLetters(sampleInvalidString);
		boolean validResult = validationService.validateHasLowercaseLetters(sampleValidString);
		
		assertThat("Valid input " + sampleValidString + " not recognised", validResult, is(true));
		assertThat("Invalid input " + sampleInvalidString + " recognised as valid", invalidResult, is(false));
	}
	
	@Test
	public void shouldValidateHasDigits() {
		String sampleValidStringHasDigits = "ab1cdef23";
		String sampleValidStringOnlyDigits = "1236";
		String sampleInvalidString = "abcde";
		
		boolean validResultHasDigits = validationService.validateHasDigits(sampleValidStringHasDigits);
		boolean validResultOnlyDigits = validationService.validateHasDigits(sampleValidStringOnlyDigits);
		boolean invalidResult = validationService.validateHasDigits(sampleInvalidString);
		
		assertThat("Valid input " + sampleValidStringHasDigits + " not recognised", validResultHasDigits, is(true));
		assertThat("Valid input " + sampleValidStringOnlyDigits + " not recognised", validResultOnlyDigits, is(true));
		assertThat("Invalid input " + sampleInvalidString + " recognised as valid", invalidResult, is(false));
	}
	
	@Test
	public void shouldValidateHasNoSpecialCharacters() {
		String sampleValidString = "ab1Cdef23";
		String sampleValidStringOnlyLeters = "abcdef";
		String sampleValidStringOnlyDigits = "123654";
		String sampleInvalidString = "abcde@#";
		
		boolean validResult = validationService.validateHasNoSpecialCharacters(sampleValidString);
		boolean validResultOnlyDigits = validationService.validateHasNoSpecialCharacters(sampleValidStringOnlyDigits);
		boolean validResultOnlyLetters = validationService.validateHasNoSpecialCharacters(sampleValidStringOnlyLeters);
		boolean invalidResult = validationService.validateHasNoSpecialCharacters(sampleInvalidString);
		
		assertThat("Valid input " + sampleValidString + " not recognised", validResult, is(true));
		assertThat("Valid input " + sampleValidStringOnlyDigits + " not recognised", validResultOnlyDigits, is(true));
		assertThat("Valid input " + sampleValidStringOnlyLeters + " not recognised", validResultOnlyLetters, is(true));
		assertThat("Invalid input " + sampleInvalidString + " recognised as valid", invalidResult, is(false));
	}
	
	@Configuration
	@PropertySources({ @PropertySource("classpath:properties/registration-test.properties") })
	static class Config {
		
		@Bean
		ValidationService validationService() {
			return new ValidationServiceImpl();
		}
		
		@Bean
		public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
			return new PropertySourcesPlaceholderConfigurer();
		}
		
	}
}

