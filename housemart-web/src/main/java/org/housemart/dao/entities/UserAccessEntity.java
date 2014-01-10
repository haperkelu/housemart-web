package org.housemart.dao.entities;

public class UserAccessEntity {

	
	private int id;
	private String bizTag;
	private int userId;
	private String accessText;
	private String url;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBizTag() {
		return bizTag;
	}
	public void setBizTag(String bizTag) {
		this.bizTag = bizTag;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAccessText() {
		return accessText;
	}
	public void setAccessText(String accessText) {
		this.accessText = accessText;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
