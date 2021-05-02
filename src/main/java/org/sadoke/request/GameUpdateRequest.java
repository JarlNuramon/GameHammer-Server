package org.sadoke.request;

import lombok.Data;

@Data
public class GameUpdateRequest {

	private Integer player1Score;
	private Integer player2Score;
	private Integer player1CP;
	private Integer player2CP;
}
