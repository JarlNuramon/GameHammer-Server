package org.sadoke;

import org.sadoke.dto.UserDto;
import org.sadoke.entities.ArmyList;
import org.sadoke.entities.User;
import org.sadoke.request.CreateArmyListRequest;
import org.sadoke.request.MatchRequest;
import org.sadoke.service.MatchService;
import org.sadoke.service.NoteService;
import org.sadoke.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ComponentScan
@ComponentScan(basePackages = "org.sadoke")
@EnableSpringConfigured
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	@Autowired
	private MatchService matchService;
	@Autowired
	private NoteService noteService;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User u1 = userService.registerNewUserAccount(
				UserDto.builder().userId("HeroMarine").password("123").matchingPassword("123").email("super@gmail.com").build());
		User u2 = userService.registerNewUserAccount(UserDto.builder().userId("Destroyer").password("123")
				.matchingPassword("123").email("super2xx@gmail.com").build());
		Map<Integer,String> notesDestroyer = new HashMap<>();
		Map<Integer,String> notesHeroMarine = new HashMap<>();
		notesHeroMarine.put(1, "<ul><li>Destruction! </li>"
				+ "<li>Sergent: Upgrade soldier! </li></ul>");
		notesDestroyer.put(1, "<ul><li>MY WILL BE DONE! </li>"
				+ "<li>Technomancer: Rites of Reanimation! </li>"
				+"<li>Living Metal! </li></ul>");
		notesDestroyer.put(2, "<ul><li>Just Advance! </li>"
				+ "<li>Protocol of the Storm: +1 MS TO ALL UNITS </li></ul>");
		notesDestroyer.put(3, "<ul><li>NOTHING HERE TOO SEE! </li></ul>");
		notesDestroyer.put(4, "<ul><li>Ehm Shooting?! You an Assault army </li>"
				+ "<li>Protocol of the Shooting: +1 AP TO ALL UNITS </li></ul>");
		notesDestroyer.put(5, "<ul><li>Here it goes...</li>"
				+ "<li>Protocol of the Hungry Void: +1 AP and +1 S </li>+ \"<li>Protocol of the Hungry Void: +1 AP and +1 S </li>"
				+ "<li>Awakend by Murder: +1 on Charge </li>"
				+ "<li>Chronometer: 1 free Reroll on Charge </li>"
				+ "<li>Blood Rites: +1 A for one Unit. Only Novokh Units </li></ul>");

		userService.confirmRegistration(u1);
		userService.confirmRegistration(u2);
		
		
		ArmyList notePlayer1 = noteService.createArmyList(
				CreateArmyListRequest.builder().armyList("HQ: <br/> <ul><li>Marine Sergent</li></ul> <br/>"
						+ "Troops: <br/> <ul><li> 10x Ultramarines</li><li> 10x Ultramarines</li></ul><br/>"
						+ "Elite: <br/> <ul><li> 10x Terminator</li><li> 10x Relic Terminator</li></ul><br/>")
				.name("HEROES OF MANKIND").race("Adeptus Astares: Ultramarines").owner("HeroMarine").notes(notesHeroMarine).build());
		ArmyList notePlayer2 = noteService.createArmyList(
				CreateArmyListRequest.builder().armyList("HQ: <br/> <ul><li>1x Overlordt</li></ul> <br/>"
						+ "Troops: <br/> <ul><li> 20x Warriors</li><li> 5x Immortals</li></ul><br/>"
						+ "Elite: <br/> <ul><li> 1x Nightbringer</li><li> 5x Skorpekh Destroyer</li></ul><br/>")
				.notes(notesDestroyer)
				.name("DESTROYER OF MANKIND").race("Necrons").owner("Destroyer").build());
		
		
		matchService.createMatch(MatchRequest.builder().id(1).userIdPlayer1("HeroMarine").userIdPlayer2("Destroyer").user1Race("Adeptus Astares: Ultramarines")
				.user2Race("Necrons").user1Army(notePlayer1.getName()).user2Army(notePlayer2.getName()).date(Date.valueOf(LocalDate.parse("2020-02-25"))).build());

	}

}
