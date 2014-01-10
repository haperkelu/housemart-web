package org.housemart.dao.entities;

public class ResidenceCellEntity {
	
	
	private int id;
	private int buildingId;
	private int codeType = 1;
	private String codeBegin;
	private String codeEnd;
	
	private int floorBegin;
	private int floorEnd;
	private int stair;
	private int houseHold;
	private String type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}
	public int getCodeType() {
		return codeType;
	}
	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}
	public String getCodeBegin() {
		return codeBegin;
	}
	public void setCodeBegin(String codeBegin) {
		this.codeBegin = codeBegin;
	}
	public String getCodeEnd() {
		return codeEnd;
	}
	public void setCodeEnd(String codeEnd) {
		this.codeEnd = codeEnd;
	}
	public int getStair() {
		return stair;
	}
	public void setStair(int stair) {
		this.stair = stair;
	}
	public int getHouseHold() {
		return houseHold;
	}
	public void setHouseHold(int houseHold) {
		this.houseHold = houseHold;
	}
	public int getFloorBegin() {
		return floorBegin;
	}
	public void setFloorBegin(int floorBegin) {
		this.floorBegin = floorBegin;
	}
	public int getFloorEnd() {
		return floorEnd;
	}
	public void setFloorEnd(int floorEnd) {
		this.floorEnd = floorEnd;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
