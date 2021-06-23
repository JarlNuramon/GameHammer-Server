package org.sadoke.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameDto {
	private long id;
	private String player1;
	private String player2;
	private Integer player1Score;
	private Integer player2Score;
	private Integer player1CP;
	private Integer player2CP;
	private String player1Race;
	private String player2Race;
	private String player1armyList;
	private String player2armyList;
	private Integer turn;
	private Integer phase;
	private String player1Note;
	private String player2Note;
	private Date date;
	private int state;
		
}
