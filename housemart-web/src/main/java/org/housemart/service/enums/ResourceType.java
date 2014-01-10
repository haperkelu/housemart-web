package org.housemart.service.enums;

public enum ResourceType {
	
	House(1),  //房源
	Residence(2),  //小区
	House_Process(3),  //房源跟进
	House_Contact(4);  //房东联系方式
	
	private int type;	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	private ResourceType(int type){
		this.type = type;
	}
	
}
