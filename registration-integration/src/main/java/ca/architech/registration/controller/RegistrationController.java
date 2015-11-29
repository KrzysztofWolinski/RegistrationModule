package ca.architech.registration.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ca.architech.registration.controller.domain.RegistrationRequestDto;
import ca.architech.registration.controller.domain.RegistrationResponseDto;
import ca.architech.registration.controller.domain.RegistrationStatus;
import ca.architech.registration.domain.User;
import ca.architech.registration.service.RegistrationService;

@RestController
@RequestMapping("/")
public class RegistrationController {

	@Inject
	private RegistrationService registrationService;
	
	@RequestMapping(value = "register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RegistrationResponseDto login(@RequestBody RegistrationRequestDto request) {
		RegistrationResponseDto response = new RegistrationResponseDto();
		User newUser = request.getUser();
		
		List<String> errorList = registrationService.validateNewUser(newUser);
		
		if (errorList.isEmpty()) {
			registrationService.registerNewUser(newUser);
			
			response.setStatus(RegistrationStatus.OK);
		} else {
			response.setStatus(RegistrationStatus.ERROR);
			response.setMessages(errorList);
		}
		
		return response;
	}
	
}
