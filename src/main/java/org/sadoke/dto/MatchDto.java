package org.sadoke.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchDto {
	private String player1;
	private String player2;
	private Integer player1Score;
	private Integer player2Score;
	private Integer player1CP;
	private Integer player2CP;
	private String player1Race;
	private String player2Race;
	private Integer turn;
	private Integer phase;
		
}
