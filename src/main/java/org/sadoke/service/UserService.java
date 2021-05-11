package org.sadoke.service;

import java.util.ArrayList;
import java.util.UUID;

import org.sadoke.dto.UserDto;
import org.sadoke.entities.User;
import org.sadoke.entities.VerificationToken;
import org.sadoke.exceptions.UserAlreadyExistException;
import org.sadoke.repository.UserRepository;
import org.sadoke.repository.VerificationTokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final BCryptPasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository tokenRepository;

	public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
		if (emailExists(userDto.getEmail())) {
			throw new UserAlreadyExistException("There is an account with that email address: " + userDto.getEmail());
		}
		if (userExists(userDto.getUserId())) {
			throw new UserAlreadyExistException("There is an account with that user name: " + userDto.getUserId());
		}

		return userRepository.save(User.builder().userId(userDto.getUserId())
				.password(passwordEncoder.encode(userDto.getPassword())).email(userDto.getEmail()).enabled(false)
				.matchesAsHost(new ArrayList<>()).matchesAsPlayer(new ArrayList<>()).build());
	}

	private boolean emailExists(String email) {
		return userRepository.emailExists(email) > 0;
	}

	private boolean userExists(String user) {
		return userRepository.userExists(user) > 0;
	}

	public VerificationToken getVerificationToken(final String VerificationToken) {
		return tokenRepository.findByToken(VerificationToken);
	}

	public void createVerificationTokenForUser(final User user, final String token) {
		final VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}

	public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
		VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
		vToken.updateToken(UUID.randomUUID().toString());
		vToken = tokenRepository.save(vToken);
		return vToken;
	}

	public void confirmRegistration(User user) {
		user.setEnabled(true);
		userRepository.save(user);

	}

	public User getUser(String username) {
		// TODO Auto-generated method stub
		return userRepository.findById(username).get();
	}

}