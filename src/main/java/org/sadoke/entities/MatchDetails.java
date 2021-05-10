package org.sadoke.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class MatchDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Basic(fetch = FetchType.LAZY)
	private long id;
	private Integer player1Score;
	private Integer player2Score;
	private Integer player1CP;
	private Integer player2CP;
	private String player1Race;
	private String player2Race;
	private Integer turn;
	private Integer phase;

}
