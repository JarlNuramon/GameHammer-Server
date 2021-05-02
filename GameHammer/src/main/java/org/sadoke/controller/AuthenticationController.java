package org.sadoke.controller;

import org.sadoke.request.TokenRequest;
import org.sadoke.response.TokenResponse;
import org.sadoke.service.AuthenticationService;
import org.sadoke.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "api/v1/authenticate")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

	private final TokenService tokenService;
	private final AuthenticationService authService;

	@GetMapping(value = "/dev/{token}")
	@Operation(summary = "Checks if the token is valid")
	public Boolean createAuthenticationToken(@PathVariable String token) throws Exception {
		return tokenService.validateToken(token);
	}

	@PostMapping(value = "/authenticate")
	@Operation(summary = "creates a authentication cookie which can be used for the login")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucessfull Login", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Failed Login", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }) })
	public ResponseEntity<TokenResponse> createAuthenticationToken(@RequestBody TokenRequest authenticationRequest)
			throws Exception {
		log.info("dev authentication request incoming");
		authService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final String token = tokenService.generateToken(authenticationRequest.getUsername());
		log.info("Token generated = {}", token);
		return ResponseEntity.ok(new TokenResponse(token));
	}

}
