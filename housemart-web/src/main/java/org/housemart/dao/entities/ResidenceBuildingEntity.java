package org.housemart.dao.entities;

public class ResidenceBuildingEntity {

	private int id;
	private int residenceId;
	private String prefix;
	private String suffix;
	private int codeType = 1;
	private String codeBegin;
	private String codeEnd;	
	private String residenceName;
	private String period;
	private int cellCount;
	
	private int stair;
	private int houseHold;
	private String buildingType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getResidenceId() {
		return residenceId;
	}
	public void setResidenceId(int residenceId) {
		this.residenceId = residenceId;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
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
	public String getResidenceName() {
		return residenceName;
	}
	public void setResidenceName(String residenceName) {
		this.residenceName = residenceName;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public int getCellCount() {
		return cellCount;
	}
	public void setCellCount(int cellCount) {
		this.cellCount = cellCount;
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
	public String getBuildingType() {
		return buildingType;
	}
	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}	
	
}
