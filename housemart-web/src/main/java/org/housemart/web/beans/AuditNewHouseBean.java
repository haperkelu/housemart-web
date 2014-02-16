package org.housemart.web.beans;

import java.util.Date;

public class AuditNewHouseBean {
	
	private int id;
	private int houseId;
	private String plateName;
	private String residenceName;
	private String residencePinyin;
	private String roomNo;
	private String contactInfo;
	private String creatorName;
	private Date addTime;
	private String comments;
	private String clientType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public String getPlateName() {
		return plateName;
	}
	public void setPlateName(String plateName) {
		this.plateName = plateName;
	}
	public String getResidenceName() {
		return residenceName;
	}
	public void setResidenceName(String residenceName) {
		this.residenceName = residenceName;
	}
	public String getResidencePinyin() {
    return residencePinyin;
  }
  public void setResidencePinyin(String residencePinyin) {
    this.residencePinyin = residencePinyin;
  }
  public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	public String getCreatorName() {
    return creatorName;
  }
  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }
  public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
  public String getComments() {
    return comments;
  }
  public void setComments(String comments) {
    this.comments = comments;
  }
  public String getClientType() {
    return clientType;
  }
  public void setClientType(String clientType) {
    this.clientType = clientType;
  }
  
}
