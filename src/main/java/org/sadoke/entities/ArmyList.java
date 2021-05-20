package org.sadoke.entities;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class ArmyList {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Basic(fetch = FetchType.LAZY)
	private long id;

	@ManyToOne
	private User owner;

	private String race;

	private String armyList;

	@OneToMany(mappedBy = "armyList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Note> lists;
}
