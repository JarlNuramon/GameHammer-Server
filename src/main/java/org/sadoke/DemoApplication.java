package org.sadoke;

import org.sadoke.dto.UserDto;
import org.sadoke.entities.User;
import org.sadoke.request.MatchRequest;
import org.sadoke.service.MatchService;
import org.sadoke.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootApplication
@ComponentScan
@ComponentScan(basePackages = "org.sadoke")
@EnableSpringConfigured
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	@Autowired
	private MatchService matchService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User u1 = userService.registerNewUserAccount(
				UserDto.builder().userId("HeroMarine").password("123").matchingPassword("123").email("super@gmail.com").build());
		User u2 = userService.registerNewUserAccount(UserDto.builder().userId("Destroyer").password("123")
				.matchingPassword("123").email("super2xx@gmail.com").build());
		userService.confirmRegistration(u1);
		userService.confirmRegistration(u2);
		matchService.createMatch(MatchRequest.builder().id(1).userIdPlayer1("HeroMarine").userIdPlayer2("Destroyer").user1Race("Adeptus Astares: Ultramarines")
				.user2Race("Necrons").date(Date.valueOf(LocalDate.parse("2020-02-25"))).build());

	}

}
