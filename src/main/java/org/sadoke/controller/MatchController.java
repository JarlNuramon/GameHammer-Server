package org.sadoke.controller;

import javax.transaction.Transactional;

import org.sadoke.dto.GameDto;
import org.sadoke.dto.MatchDto;
import org.sadoke.request.GameUpdateRequest;
import org.sadoke.request.MatchRequest;
import org.sadoke.request.PlayerRequest;
import org.sadoke.service.MatchService;
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
@RequestMapping(path = "api/v1/match")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MatchController {

	private final MatchService matchService;

	@PostMapping(value = "/findPlayer")
	@Operation(summary = "Returns the searched player you might want to declare a match against")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "He was found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }),
			@ApiResponse(responseCode = "400", description = "He wasnt found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	public ResponseEntity<Boolean> findPlayer(@RequestBody PlayerRequest request) throws Exception {
		return ResponseEntity.ok(matchService.playerExists(request.getUserId()));
	}


	@GetMapping(value = "{matchId}")
	@Operation(summary = "Returns the match with given id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "He was found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MatchDto.class)) }),
			@ApiResponse(responseCode = "400", description = "He wasnt found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }) })
	public ResponseEntity<MatchDto> getMatch(@PathVariable("matchId") String matchid) throws Exception {
		
		return ResponseEntity.ok(matchService.getMatchDto(matchid));
	}
	@GetMapping(value = "game/{matchId}")
	@Operation(summary = "Returns the game with given id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "He was found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MatchDto.class)) }),
			@ApiResponse(responseCode = "400", description = "He wasnt found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }) })
	public ResponseEntity<GameDto> getGame(@PathVariable("matchId") String matchid) throws Exception {
		
		return ResponseEntity.ok(matchService.getGameDto(matchid));
	}	
	
	@PostMapping(value = "{matchId}/nextStep")
	@Operation(summary = "Match goes to the next step")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Match step changed", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class)) }),
			@ApiResponse(responseCode = "400", description = "ehm this shouldn't happen. Please notify the devs", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }) })
	public ResponseEntity<GameDto> nextStep(@PathVariable("matchId") String matchid) throws Exception {
		matchService.nextPhase(matchid);
		return ResponseEntity.ok(matchService.getGameDto(matchid));
	}

	@PostMapping(value = "{matchId}/nextTurn")
	@Operation(summary = "Next Player turn")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Turn ended", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class)) }),
			@ApiResponse(responseCode = "400", description = "ehm this shouldn't happen. Please notify the devs", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }) })
	public ResponseEntity<GameDto> nextTurn(@PathVariable("matchId") String matchid) throws Exception {
		matchService.nextTurn(matchid);
		return ResponseEntity.ok(matchService.getGameDto(matchid));
	}

	@PostMapping(value = "{matchId}/end")
	@Operation(summary = "End game")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Game was declared to end", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MatchDto.class)) }),
			@ApiResponse(responseCode = "400", description = "ehm this shouldn't happen. Please notify the devs", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }) })
	public ResponseEntity<MatchDto> endGame(@PathVariable("matchId") String matchid) throws Exception {
		matchService.endGame(matchid);
		return ResponseEntity.ok(null);
	}

	@PostMapping(value = "{matchId}/update")
	@Operation(summary = "Match needs to be updated")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Game was declared to end", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MatchDto.class)) }),
			@ApiResponse(responseCode = "400", description = "ehm this shouldn't happen. Please notify the devs", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }) })
	public ResponseEntity<MatchDto> adjustScores(@PathVariable("matchId") Long matchid,
			@RequestBody GameUpdateRequest request) throws Exception {
		matchService.adjustScore(matchid, request);
		return ResponseEntity.ok(null);
	}

	@PostMapping(value = "/start")
	@Operation(summary = "Starts match versus declared player")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully started", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class)) }),
			@ApiResponse(responseCode = "400", description = "Match coudln't be started", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }) })
	public ResponseEntity<Long> startMatch(@RequestBody MatchRequest request) throws Exception {
		return ResponseEntity.ok(matchService.createMatch(request));
	}

	@GetMapping(value = "/matchesAsHost/{userId}")
	@Operation(summary = "Returns the list of Matches a Player hosts")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "He was found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MatchDto[].class)) }),
			@ApiResponse(responseCode = "400", description = "He wasnt found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	public ResponseEntity<MatchDto[]> getMatchesAsHost(@PathVariable("userId") String user) throws Exception {
		return ResponseEntity.ok(matchService.hostMatches(user));
	}

}
