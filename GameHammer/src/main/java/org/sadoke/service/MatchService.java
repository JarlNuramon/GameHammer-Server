package org.sadoke.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.sadoke.entities.Match;
import org.sadoke.entities.MatchDetails;
import org.sadoke.repository.DetailsRepository;
import org.sadoke.repository.MatchRepository;
import org.sadoke.repository.UserRepository;
import org.sadoke.request.GameUpdateRequest;
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
		return userRepos.findByUserId(userId) != null;
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
}
