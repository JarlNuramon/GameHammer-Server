package org.sadoke.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	
	@ManyToOne
	private ArmyList player1ArmyList;
	@ManyToOne
	private ArmyList player2ArmyList;
	
	private Integer turn;
	private Integer phase;
	// 1 Command phase
	// 2 Movement Phase
	// 3 Psychic phase
	// 4 Shooting phase
	// 5 Charge
	// 6 Fight
	// 7 Morale Phase
	// 8 Command phase
	// 9 Movement Phase
	// 10 Psychic phase
	// 11 Shooting phase
	// 12 Charge
	// 13 Fight
	// 14 Morale Phase
}
