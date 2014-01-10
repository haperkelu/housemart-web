package org.housemart.dao.entities;

import java.util.Date;

public class HouseInteractionExtEntity extends HouseInteractionEntity {
	
	protected String houseInfo;
	
	protected String agentName;

	public String getHouseInfo() {
		return houseInfo;
	}

	public void setHouseInfo(String houseInfo) {
		this.houseInfo = houseInfo;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	
	
}