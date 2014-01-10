package org.housemart.dao.entities;

public class GooglePlaceBaseEntity {

	private int id;
	private int residenceId;
	private String address;
	private String name;
	private String lat;
	private String lng;
	private String type;
	private Boolean isMain;
	
	private String keyword;
	
	public int getResidenceId() {
		return residenceId;
	}
	public void setResidenceId(int residenceId) {
		this.residenceId = residenceId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public Boolean getIsMain() {
		return isMain;
	}
	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}
	
	
}
