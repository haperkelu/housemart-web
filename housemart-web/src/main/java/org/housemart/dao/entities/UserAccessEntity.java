package org.housemart.dao.entities;

import java.util.Date;


public class UserAccessEntity {

	
	private int id;
	private String bizTag;
	private int userId;
	private String accessText;
	private String url;
	private Date addTime;
	
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
	/**
	 * @return the addTime
	 */
	public Date getAddTime() {
		return addTime;
	}
	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

}
