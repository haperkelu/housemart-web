package org.housemart.service.enums;

public enum PriviledgeResultType {
	
	NoRight(1), Audit(2), Direct(3);
	
	private int value;
	PriviledgeResultType(int value) {
		this.setValue(value);
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	

}
