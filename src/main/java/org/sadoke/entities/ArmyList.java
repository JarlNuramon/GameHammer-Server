package org.sadoke.entities;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ArmyList {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Basic(fetch = FetchType.LAZY)
	private long id;
	
	private String name;
	
	@ManyToOne
	private User owner;

	private String race;

	private String armyList;

	@OneToMany(mappedBy = "armyList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 @OrderBy("phase ASC")
	private List<Note> lists;
	
	@OneToMany(mappedBy = "player1ArmyList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MatchDetails> matches;

	@OneToMany(mappedBy = "player2ArmyList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MatchDetails> matches2;
}
