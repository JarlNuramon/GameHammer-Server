package org.sadoke.request;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class MatchRequest {
	private long id;
	private String userIdPlayer1;
	private String userIdPlayer2;
	private String user1Race;
	private String user2Race;
	private Date date;
}
