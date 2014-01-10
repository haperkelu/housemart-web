package org.housemart.web.beans;

import java.util.Date;

public class AuditNewResidenceBean {
	
	private int id;
	private int residenceId;
	private String plateName;
	private String residenceName;
	private String residencePinyin;
	private String creatorName;
	private Date addTime;
	
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
}
