package org.sadoke.request;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateArmyListRequest {

	private String name;
	private String race;
	private String armyList;
	private String owner;
	private Map<Integer,String> notes;
}
