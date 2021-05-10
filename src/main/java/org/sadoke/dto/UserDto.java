package org.sadoke.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.sadoke.annotation.ValidEmail;
import org.sadoke.annotation.ValidPassword;

import lombok.Builder;
import lombok.Data;

@Data
@ValidPassword
@Builder
public class UserDto {

	@NotNull
	@NotEmpty
	private String userId;

	@NotNull
	@NotEmpty
	private String password;
	private String matchingPassword;

	@NotNull
	@NotEmpty
	@ValidEmail
	private String email;
}
