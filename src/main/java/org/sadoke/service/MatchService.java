package org.sadoke.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.sadoke.entities.Match;
import org.sadoke.entities.MatchDetails;
import org.sadoke.entities.User;
import org.sadoke.repository.DetailsRepository;
import org.sadoke.repository.MatchRepository;
import org.sadoke.repository.UserRepository;
import org.sadoke.request.GameUpdateRequest;
import org.sadoke.request.MatchRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchService {
	private final UserRepository userRepos;
	private final MatchRepository matchRepos;
	private final DetailsRepository detailsRepository;

	public boolean playerExists(String userId) {
		return userRepos.userExists(userId) > 0;
	}

	@Transactional
	public void adjustScore(Long id, GameUpdateRequest request) throws Exception {
		Optional<Match> m = matchRepos.findById(id);
		if (!m.isPresent())
			throw new Exception("Match not found!");
		MatchDetails mDetails = m.get().getDetails();
		mDetails.setPlayer1Score(request.getPlayer1Score());
		mDetails.setPlayer2Score(request.getPlayer2Score());
		mDetails.setPlayer1CP(request.getPlayer1CP());
		mDetails.setPlayer2CP(request.getPlayer2CP());
		detailsRepository.save(mDetails);
	}

	@Transactional
	public void createMatch(MatchRequest m) throws Exception {
		User user1 = userRepos.findById(m.getUserIdPlayer1()).get();
		User user2 = userRepos.findById(m.getUserIdPlayer2()).get();
		matchRepos.save(Match.builder().player1(user1).player2(user2).details(createDetails(m)).build());
	}

	private MatchDetails createDetails(MatchRequest m) {
		return MatchDetails.builder().player1CP(0).player2CP(0).player1Score(0).player2Score(0)
				.player1Race(m.getUser1Race()).player2Race(m.getUser2Race()).turn(0).phase(0).build();
	}
}
