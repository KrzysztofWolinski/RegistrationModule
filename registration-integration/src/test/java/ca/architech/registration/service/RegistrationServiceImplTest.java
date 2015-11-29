package ca.architech.registration.service;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.architech.registration.domain.User;
import ca.architech.registration.repository.UserRepository;
import ca.architech.registration.service.impl.RegistrationServiceImpl;
import ca.architech.registration.service.impl.ValidationServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RegistrationServiceImplTest.Config.class })
public class RegistrationServiceImplTest {

	private String validUsername = "username";
	
	private String validPassword = "Passw0rd";
	
	private String invalidUsername = "user";
	
	private String invalidPassword = "password";
	
	
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
	
	
	@Inject
	RegistrationService registrationService; 
	
	@Inject
	UserRepository userRepository;
	
	@Before
	public void init() {
		when(userRepository.findByUsername(Mockito.anyString()))
			.thenReturn(null);
	}
	
	@Test
	public void shouldInitializeContext() {
		
	}
	
	@Test
	public void shouldAcceptValidData() {
		User user = getUser(this.validUsername, this.validPassword);
		List<String> result = registrationService.validateNewUser(user);
				
		assertThat("Valid data was not accepted", result, is(empty()));
	}
	
	@Test
	public void shouldNotAcceptInvalidUsername() {
		User user = getUser(this.invalidUsername, this.validPassword);
		List<String> result = registrationService.validateNewUser(user);
				
		assertThat("Invalid username was accepted", result, is(not(empty())));
		assertThat("Wrong error message", result, contains(this.usernameTooShortError));
	}
	
	@Test
	public void shouldNotAcceptInvalidPassword() {
		User user = getUser(this.validUsername, this.invalidPassword);
		List<String> result = registrationService.validateNewUser(user);
				
		assertThat("Invalid password was accepted", result, is(not(empty())));
		assertThat("Wrong error message", result, contains(this.passwordTooSimpleError));
	}
	
	@Test
	public void shouldNotAcceptNotUniqueUsername() {
		User existingUser = getUser(this.validUsername, this.validPassword);
		when(userRepository.findByUsername(Mockito.anyString())).thenReturn(existingUser);
		
		User user = getUser(this.validUsername, this.validPassword);
		List<String> result = registrationService.validateNewUser(user);
				
		assertThat("Valid data was not accepted", result, is(not(empty())));
		assertThat("Wrong error message", result, contains(this.usernameNotUniqueError));
	}
	
	@Test
	public void shouldUseUserRepositoryToSaveUser() {
		User user = getUser(this.validUsername, this.validPassword);
		this.registrationService.registerNewUser(user);
		
		verify(userRepository, times(1)).save(Mockito.any(User.class));
	}
	
	private User getUser(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		return user;
	}
	
	@Configuration
	@PropertySources({ @PropertySource("classpath:properties/registration-test.properties") })
	static class Config {
		
		@Bean
		RegistrationService registrationService() {
			return new RegistrationServiceImpl();
		}
		
		@Bean
		ValidationService validationService() {
			return new ValidationServiceImpl();
		}
		
		@Bean
		UserRepository userRepository() {
			return Mockito.mock(UserRepository.class);
		}
		
		@Bean
		public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
			return new PropertySourcesPlaceholderConfigurer();
		}
	}
}
