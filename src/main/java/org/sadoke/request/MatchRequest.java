package org.sadoke.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchRequest {

	private String userIdPlayer1;
	private String userIdPlayer2;
	private String user1Race;
	private String user2Race;
}
