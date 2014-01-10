/**
 * Created on 2012-10-30
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.dao.entities;

import java.util.Date;

/**
 * @author pqin
 */
public class RepositoryHousePicEntity {
	private Integer id;
	private Integer residenceId;
	private Integer houseId;
	private Integer type;
	private String url;
	private String cloudURL;
	private Integer status;
	private Integer crawlStatus;
	private Date addTime;
	private Date updateTime;

	public enum Type {
		Residence(1), RoomType(2), HousePic(3);
		Type(int value) {
			this.value = value;
		}
		private int value;
		public void setValue(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	public enum CrawlStatus {
		Success(1), Error(2);
		CrawlStatus(int value) {
			this.value = value;
		}
		private int value;
		public void setValue(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
  
  public enum Status {
    Valid(1), Invalid(2);
    Status(int value) {
      this.value = value;
    }
    private int value;
    public int getValue() {
      return value;
    }
    public void setValue(int value) {
      this.value = value;
    }
  }
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getResidenceId() {
		return residenceId;
	}
	public void setResidenceId(Integer residenceId) {
		this.residenceId = residenceId;
	}
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCloudURL() {
		return cloudURL;
	}
	public void setCloudURL(String cloudUrl) {
		this.cloudURL = cloudUrl;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getCrawlStatus() {
		return crawlStatus;
	}
	public void setCrawlStatus(Integer crawlStatus) {
		this.crawlStatus = crawlStatus;
	}

}
