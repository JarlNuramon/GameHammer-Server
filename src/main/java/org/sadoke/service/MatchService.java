package org.sadoke.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.sadoke.dto.GameDto;
import org.sadoke.dto.GameDto.GameDtoBuilder;
import org.sadoke.dto.MatchDto;
import org.sadoke.dto.MatchDto.MatchDtoBuilder;
import org.sadoke.entities.Match;
import org.sadoke.entities.MatchDetails;
import org.sadoke.entities.MatchDetails.MatchDetailsBuilder;
import org.sadoke.entities.Note;
import org.sadoke.entities.User;
import org.sadoke.repository.ArmyListRepository;
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
	private final ArmyListRepository armyListRepository;

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
	public long createMatch(MatchRequest m) throws Exception {
		User user1 = userRepos.findById(m.getUserIdPlayer1()).get();
		User user2 = userRepos.findById(m.getUserIdPlayer2()).get();
		Date date = (m.getDate() != null) ? m.getDate() : new Date(System.currentTimeMillis());
		Match match = matchRepos.save(Match.builder().player1(user1).player2(user2).details(createDetails(m,user1,user2)).date(date).build());
		return match.getId();
	}

	private MatchDetails createDetails(MatchRequest m, User user1, User user2) {
		
		MatchDetailsBuilder builder = MatchDetails.builder().player1CP(0).player2CP(0).player1Score(0).player2Score(0).turn(1).phase(1)
				.player1Race(m.getUser1Race()).player2Race(m.getUser2Race());
		if(m.getUser1Army()!=null)
			builder.player1ArmyList(armyListRepository.getArmyByName(m.getUser1Army(), user1));
		if(m.getUser2Army()!=null)
			builder.player2ArmyList(armyListRepository.getArmyByName(m.getUser2Army(), user2));
		
		return builder.build();
			
	}

	@Transactional
	public void nextTurn(String matchid) throws Exception {
		Optional<Match> m = matchRepos.findById(Long.parseLong(matchid));
		if (!m.isPresent())
			throw new Exception("Match not found!");
		MatchDetails mDetails = m.get().getDetails();
		mDetails.setTurn(mDetails.getTurn() + 1);
		mDetails.setPhase(1);
		detailsRepository.save(mDetails);
	}

	@Transactional
	public void nextPhase(String matchid) throws Exception {
		Optional<Match> m = matchRepos.findById(Long.parseLong(matchid));
		if (!m.isPresent())
			throw new Exception("Match not found!");
		MatchDetails mDetails = m.get().getDetails();
		mDetails.setPhase(mDetails.getPhase() + 1);
		detailsRepository.save(mDetails);
	}

	public MatchDto getMatchDto(String matchid) throws Exception {
		Optional<Match> m = matchRepos.findById(Long.parseLong(matchid));
		if (!m.isPresent())
			throw new Exception("Match not found!");

		return buildMatchDto(m.get());
	}

	private MatchDto buildMatchDto(Match m) {
		MatchDtoBuilder builder =  MatchDto.builder().id(m.getId()).phase(m.getDetails().getPhase()).turn(m.getDetails().getTurn())
				.player1(m.getPlayer1().getUserId()).player2(m.getPlayer2().getUserId())
				.player1CP(m.getDetails().getPlayer1CP()).player2CP(m.getDetails().getPlayer2CP())
				.player1Score(m.getDetails().getPlayer1Score()).player2Score(m.getDetails().getPlayer2Score())
				.player1Race(m.getDetails().getPlayer1Race()).player2Race(m.getDetails().getPlayer2Race())
				.date(m.getDate())
				.state(m.isFinished()? 1:0);
		
		if(m.getDetails().getPlayer1ArmyList()!=null)
			builder.player1armyList(m.getDetails().getPlayer1ArmyList().getName())
				.player1armyListContent(m.getDetails().getPlayer1ArmyList().getArmyList());
		if(m.getDetails().getPlayer1ArmyList()!=null)
			builder.player2armyList( m.getDetails().getPlayer2ArmyList().getName())
				.player2armyListContent( m.getDetails().getPlayer2ArmyList().getArmyList());
							
		return  builder.build();
				
	}

	public GameDto getGameDto(String matchid) throws Exception {
		Optional<Match> m = matchRepos.findById(Long.parseLong(matchid));
		if (!m.isPresent())
			throw new Exception("Match not found!");

		return buildGameDto(m.get());
	}

	private GameDto buildGameDto(Match m) {
		GameDtoBuilder builder =  GameDto.builder().id(m.getId()).phase(m.getDetails().getPhase()).turn(m.getDetails().getTurn())
				.player1(m.getPlayer1().getUserId()).player2(m.getPlayer2().getUserId())
				.player1CP(m.getDetails().getPlayer1CP()).player2CP(m.getDetails().getPlayer2CP())
				.player1Score(m.getDetails().getPlayer1Score()).player2Score(m.getDetails().getPlayer2Score())
				.player1Race(m.getDetails().getPlayer1Race()).player2Race(m.getDetails().getPlayer2Race())
				.date(m.getDate())
				.state(m.isFinished()? 1:0);
		
		if(m.getDetails().getPlayer1ArmyList()!=null) {
			builder.player1armyList(m.getDetails().getPlayer1ArmyList().getName());
			log.info("notes = {}", m.getDetails().getPlayer2ArmyList().getLists());
			for(Note note : m.getDetails().getPlayer1ArmyList().getLists())
				if(note.getPhase()==m.getDetails().getPhase().intValue())
					builder.player1Note(note.getNotes());
		}
		
		if(m.getDetails().getPlayer2ArmyList()!=null) {
			builder.player2armyList(m.getDetails().getPlayer2ArmyList().getName());
			for(Note note : m.getDetails().getPlayer2ArmyList().getLists())
				if((note.getPhase())==(m.getDetails().getPhase().intValue()+7)%14)
					builder.player2Note(note.getNotes());
		}
							
		return  builder.build();
				
	}
	
	public MatchDto[] hostMatches(String user) throws Exception {
		Optional<User> u = userRepos.findById(user);
		if (!u.isPresent()) throw new Exception("User not found!");
		List<Match> matches = u.get().getMatchesAsHost();
		matches.addAll(u.get().getMatchesAsPlayer());
		return matches.stream().map(this::buildMatchDto).toArray(MatchDto[]::new);
	}

	public void endGame(String matchid) throws Exception {
		Optional<Match> m = matchRepos.findById(Long.parseLong(matchid));
		if (!m.isPresent())
			throw new Exception("Match not found!");
		m.get().setFinished(true);
		matchRepos.save(m.get());
		
	}
}
