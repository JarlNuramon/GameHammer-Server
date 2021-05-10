package org.sadoke.controller;

import java.util.Calendar;

import org.sadoke.entities.User;
import org.sadoke.entities.VerificationToken;
import org.sadoke.listener.OnRegistrationCompleteEvent;
import org.sadoke.request.RegisterRequest;
import org.sadoke.response.RegisteredResponse;
import org.sadoke.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "api/v1/user")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final ApplicationEventPublisher eventPublisher;

	@PostMapping(value = "/register")
	@Operation(summary = "Register a player")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Email to confirm registration will be sent", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RegisteredResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "The validation failed for multiple reasons", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RegisteredResponse.class)) }) })
	public ResponseEntity<RegisteredResponse> register(@RequestBody RegisterRequest request) {
		try {
			User registered = userService.registerNewUserAccount(request.getUserDto());
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered));
			return ResponseEntity.ok().body(RegisteredResponse.builder().message("VerificationMail sendt").build());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(RegisteredResponse.builder().message(e.getMessage()).build());
		}

	}

	@GetMapping("/regitrationConfirm")
	@Operation(summary = "Confirms the registration to enable the user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Confirms registration", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RegisteredResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Registration vailed", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RegisteredResponse.class)) }) })
	public String confirmRegistration(@RequestParam("token") String token) {
		VerificationToken verificationToken = userService.getVerificationToken(token);
		if (verificationToken == null)
			return "No token. Verification failed!";
		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return "expired";
		}
		userService.confirmRegistration(user);
		return "successfull";
	}
}
