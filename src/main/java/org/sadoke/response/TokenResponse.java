package org.sadoke.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenResponse {
	private String token;

	public TokenResponse(String jwttoken) {
		this.token = jwttoken;
	}

}
