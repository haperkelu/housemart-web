package org.housemart.dao.entities;

import java.util.Date;

public class AccountEntity {

	protected Integer id;

	protected String loginName;
	protected String password;	
	protected String name;
	protected String identityID;
	protected Integer gender;
	protected String contactInfo1;
	protected String contactInfo2;
	protected String emergency;
	protected String emergencyContact;
	protected Integer status;
	
	protected String positionType;
	
	protected Integer managerID;
	
	protected Integer type;
	protected String company;
	protected String companyAddress;
	protected String note;
	
	protected Date addTime;
	protected Date updateTime;
	
	protected String picURL;
	protected String idURL;
	protected String cardURL;
	
	protected Integer picLocked = 0;
	protected Integer idLocked = 0;
	protected Integer cardLocked = 0;
	
	protected String weixin;
	protected Integer weixinJoined = 0;
	
	protected Integer source = 0;
	
	public static enum TypeEnum {
		Internal(0), External(1), Combine(2);
	    public Integer value;
	    
	    TypeEnum(Integer type) {
	    	this.value = type;
	    }
	}
	
	public static enum StatusEnum {
		Deleted(0), Valid(1), Applied(2), Pending(3), Invalid(4);
	    public Integer value;
	    
	    StatusEnum(Integer status) {
	    	this.value = status;
	    }
	}
	
	public static enum SourceEnum {
		ERP(0), ExtApply(1);
	    public Integer value;
	    
	    SourceEnum(Integer source) {
	    	this.value = source;
	    }
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String value) {
		this.loginName = value;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String value) {
		this.password = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getIdentityID() {
		return identityID;
	}
	public void setIdentityID(String value) {
		this.identityID = value;
	}
	
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer value) {
		this.gender = value;
	}
	
	public String getContactInfo1() {
		return contactInfo1;
	}
	public void setContactInfo1(String value) {
		this.contactInfo1 = value;
	}
	
	public String getContactInfo2() {
		return contactInfo2;
	}
	public void setContactInfo2(String value) {
		this.contactInfo2 = value;
	}
	
	public String getEmergency() {
		return emergency;
	}
	public void setEmergency(String value) {
		this.emergency = value;
	}
	
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String value) {
		this.emergencyContact = value;
	}
	
	public String getPositionType() {
		return positionType;
	}
	public void setPositionType(String value) {
		this.positionType = value;
	}
	
	public Integer getManagerID() {
		return managerID;
	}
	public void setManagerID(Integer value) {
		this.managerID = value;
	}
	
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date value) {
		this.addTime = value;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPicURL() {
		return picURL;
	}
	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}
	public String getIdURL() {
		return idURL;
	}
	public void setIdURL(String idURL) {
		this.idURL = idURL;
	}
	public String getCardURL() {
		return cardURL;
	}
	public void setCardURL(String cardURL) {
		this.cardURL = cardURL;
	}
	public Integer getPicLocked() {
		return picLocked;
	}
	public void setPicLocked(Integer picLocked) {
		this.picLocked = picLocked;
	}
	public Integer getIdLocked() {
		return idLocked;
	}
	public void setIdLocked(Integer idLocked) {
		this.idLocked = idLocked;
	}
	public Integer getCardLocked() {
		return cardLocked;
	}
	public void setCardLocked(Integer cardLocked) {
		this.cardLocked = cardLocked;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getWeixinJoined() {
		return weixinJoined;
	}
	public void setWeixinJoined(Integer weixinJoined) {
		this.weixinJoined = weixinJoined;
	}
	
}