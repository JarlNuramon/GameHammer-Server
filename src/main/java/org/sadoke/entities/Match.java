package org.sadoke.entities;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Basic(fetch = FetchType.LAZY)
	private long id;
	@ManyToOne
	private User player1;
	@ManyToOne
	private User player2;
	@OneToOne(cascade = CascadeType.ALL)
	private MatchDetails details;
}
