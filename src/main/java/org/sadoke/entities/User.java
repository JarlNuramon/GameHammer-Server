package org.sadoke.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User {
	@Id
	private String userId;
	private String email;
	private String password;
	private boolean enabled;
	@OneToMany(mappedBy = "player1", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Match> matchesAsHost;
	@OneToMany(mappedBy = "player2", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Match> matchesAsPlayer;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ArmyList> lists;

	@Override
	public String toString() {
		return "user: " + userId + " password: " + password;
	}
}
