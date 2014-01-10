package org.housemart.dao.entities;

import java.util.Date;

public class ClientNotificationEntity {

	protected Integer id;

	protected String content;
	protected Integer target;
	protected String clientUIDs;	
	
	protected Integer accountID;
	protected String accountName;
	protected Integer status;
	
	protected Date addTime;
	protected Date sendTime;
	
	public static enum TargetEnum {
		All(0), Client(1), Broker(2), Custom(3);
	    public Integer value;
	    
	    TargetEnum(Integer target) {
	    	this.value = target;
	    }
	}
	
	public static enum StatusEnum {
		Unprocessed(0), Processed(1);
	    public Integer value;
	    
	    StatusEnum(Integer status) {
	    	this.value = status;
	    }
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Integer getTarget() {
		return target;
	}


	public void setTarget(Integer target) {
		this.target = target;
	}

	public Integer getAccountID() {
		return accountID;
	}

	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}


	public String getClientUIDs() {
		return clientUIDs;
	}


	public void setClientUIDs(String clientUIDs) {
		this.clientUIDs = clientUIDs;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Date getSendTime() {
		return sendTime;
	}


	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	
	
}