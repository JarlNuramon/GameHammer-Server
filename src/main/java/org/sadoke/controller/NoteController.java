package org.sadoke.controller;

import java.util.List;

import org.sadoke.dto.ArmyDto;
import org.sadoke.dto.MatchDto;
import org.sadoke.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	
	private final NoteService noteService;
	
	@GetMapping(value = "all/{userid}")
	@Operation(summary = "Get all Notes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Game was declared to end", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MatchDto.class)) }),
			@ApiResponse(responseCode = "400", description = "ehm this shouldn't happen. Please notify the devs", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }) })
	public ResponseEntity<List<ArmyDto>> getAllLists(@PathVariable("userid") String userid) throws Exception {
		return ResponseEntity.ok(noteService.getUsersList(userid));
	}

}
