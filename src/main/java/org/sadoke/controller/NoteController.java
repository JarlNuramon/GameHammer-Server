package org.sadoke.controller;

import org.sadoke.dto.MatchDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping(path = "api/v1/note")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
public class NoteController {

	@PostMapping(value = "{matchId}/note")
	@Operation(summary = "GetNoteForTurn")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Game was declared to end", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MatchDto.class)) }),
			@ApiResponse(responseCode = "400", description = "ehm this shouldn't happen. Please notify the devs", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }) })
	public ResponseEntity<MatchDto> adjustScores(@PathVariable("matchId") Long matchid) throws Exception {
		return ResponseEntity.ok(null);
	}

}
