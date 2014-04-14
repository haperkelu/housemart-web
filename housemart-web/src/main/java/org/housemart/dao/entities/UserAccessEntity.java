package org.housemart.dao.entities;

import jxl.write.DateTime;

public class UserAccessEntity {

	
	private int id;
	private String bizTag;
	private int userId;
	private String accessText;
	private String url;
	private DateTime addTime;
	
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
	public DateTime getAddTime() {
		return addTime;
	}
	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(DateTime addTime) {
		this.addTime = addTime;
	}

}
