package org.sadoke;

import java.util.ArrayList;

import org.sadoke.entities.User;
import org.sadoke.repository.UserRepository;
import org.sadoke.request.MatchRequest;
import org.sadoke.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ComponentScan
@ComponentScan(basePackages = "org.sadoke")
@EnableSpringConfigured
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private MatchService matchService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepo.save(User.builder().matchesAsHost(new ArrayList<>()).matchesAsPlayer(new ArrayList<>()).enabled(true)
				.email("super@gmail.com").password(passwordEncoder.encode("123")).userId("a").build());
		userRepo.save(User.builder().matchesAsHost(new ArrayList<>()).matchesAsPlayer(new ArrayList<>()).enabled(true)
				.email("super2@gmail.com").password(passwordEncoder.encode("123")).userId("b").build());
		matchService.createMatch(MatchRequest.builder().userIdPlayer1("a").userIdPlayer2("b").user1Race("Necron")
				.user2Race("Death Guard").build());

	}

}
