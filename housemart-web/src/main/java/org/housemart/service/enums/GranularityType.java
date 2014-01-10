package org.housemart.service.enums;

public enum GranularityType {

	Entity(1),
	Field(2);
	private int type;	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	private GranularityType(int type){
		this.type = type;
	}
	
	
}
