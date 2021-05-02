package org.sadoke.controller;

import org.sadoke.dto.MatchDto;
import org.sadoke.request.PlayerRequest;
import org.sadoke.response.AllMatchesReponse;
import org.sadoke.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(path = "api/v1/dashboard")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
public class DashboardController {

	private final MatchService matchService;

	@GetMapping(value = "{userId}/get")
	@Operation(summary = "Returns last 20 matches which were player")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Matches will be returned", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AllMatchesReponse.class)) }),
			@ApiResponse(responseCode = "400", description = "He wasnt found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }) })
	public ResponseEntity<AllMatchesReponse> getAllMatches(@RequestBody PlayerRequest request) throws Exception {
		return ResponseEntity.ok(null);
	}

	@GetMapping(value = "{userId}/{matchId}")
	@Operation(summary = "Returns saved details about the match")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Match will infos willl be returned", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MatchDto.class)) }),
			@ApiResponse(responseCode = "400", description = "He wasnt found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }) })
	public ResponseEntity<MatchDto> getMatch(@RequestBody PlayerRequest request) throws Exception {
		return ResponseEntity.ok(null);
	}
}
