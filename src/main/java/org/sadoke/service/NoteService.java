package org.sadoke.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.sadoke.ApplicationProperties;
import org.sadoke.entities.Note;
import org.sadoke.repository.ArmyListRepository;
import org.sadoke.entities.ArmyList;
import org.sadoke.request.CreateArmyListRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@RequiredArgsConstructor
public class NoteService {
private final ArmyListRepository armyListRepo;
	private final UserService userService;
	@Transactional
	public ArmyList createArmyList(CreateArmyListRequest req) {
		ArmyList army = armyListRepo.save( ArmyList.builder()
		.owner(userService.getUser(req.getOwner())).name(req.getName())
		.armyList(req.getArmyList()).race(req.getRace()).build());

		List<Note> notes = createNotesFromMap(req.getNotes(),army);
		army.setLists(notes);
		return army;
	}
	private List<Note> createNotesFromMap(Map<Integer, String> notes, ArmyList l) {
		List<Note> noteList = new ArrayList<>();
		for(Integer i : notes.keySet()) 
			noteList.add(Note.builder().armyList(l).notes(notes.get(i)).phase(i).build());
		
		return noteList;
	}

}
