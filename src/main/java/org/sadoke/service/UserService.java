package org.sadoke.service;

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
			throw new UserAlreadyExistException("There is an account with that email address: " + userDto.getEmail());
		}
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setEmail(userDto.getEmail());

		return userRepository.save(user);
	}

	private boolean emailExists(String email) {
		return userRepository.findByEmail(email) != null;
	}

	private boolean userExists(String user) {
		return userRepository.findByEmail(user) != null;
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

	public void saveRegisteredUser(User user) {
		userRepository.save(user);

	}

}