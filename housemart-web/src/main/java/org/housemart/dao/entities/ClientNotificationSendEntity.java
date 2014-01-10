package org.housemart.dao.entities;

import java.util.Date;

public class ClientNotificationSendEntity {

	protected Integer id;

	protected Integer notificationID;
	protected String notificationContent;
	protected String clientUIDs = "";	
	protected Integer status;
	protected Integer sendType;
	
	protected Date processTime;
	
	protected String note;
	
	public static enum StatusEnum {
		Unprocessed(0), Processing(1), Processed(2);
	    public Integer value;
	    
	    StatusEnum(Integer status) {
	    	this.value = status;
	    }
	}
	
	public static enum SendTypeEnum {
		APNSClient(0), APNSBroker(1), BaiduYun(2), BaiduYunClient(3), BaiduYunBroker(4);
	    public Integer value;
	    
	    SendTypeEnum(Integer sendType) {
	    	this.value = sendType;
	    }
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(Integer notificationID) {
		this.notificationID = notificationID;
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

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNotificationContent() {
		return notificationContent;
	}

	public void setNotificatoinContent(String notificationContent) {
		this.notificationContent = notificationContent;
	}
	
	
}