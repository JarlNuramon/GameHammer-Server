package org.sadoke.service;

import java.util.Optional;

import org.sadoke.entities.User;
import org.sadoke.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public void authenticate(String username, String password) throws Exception {
		if (password.isEmpty())
			throw new NullPointerException("password is empty");
		try {
			Optional<User> user = userRepository.findById(username);
			if (!user.isPresent() || !user.get().getPassword().equals(passwordEncoder.encode(password)))
				throw new Exception("Failed because user does not exist or password is wrong");

		} catch (Exception e) {
			log.error("Login failed", e);
			throw e;
		}

	}
}
