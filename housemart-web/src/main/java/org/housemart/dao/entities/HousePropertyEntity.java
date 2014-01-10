/**
 * Created on 2012-9-23
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.dao.entities;

import java.util.Date;
/**
 * 
 * @author pqin
 */
public class HousePropertyEntity {
	private Integer id;
	private Integer houseId;
	private String propertyCode;
	private String propertyOwner;
	private String loan;
	private Integer version;
	private Date addTime;
	private Date updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer propertyId) {
		this.id = propertyId;
	}
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public String getPropertyCode() {
		return propertyCode;
	}
	public void setPropertyCode(String propertyPropertyCode) {
		this.propertyCode = propertyPropertyCode;
	}
	public String getPropertyOwner() {
		return propertyOwner;
	}
	public void setPropertyOwner(String propertyPropertyOwner) {
		this.propertyOwner = propertyPropertyOwner;
	}
	public String getLoan() {
		return loan;
	}
	public void setLoan(String propertyLoan) {
		this.loan = propertyLoan;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer propertyVersion) {
		this.version = propertyVersion;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date propertyAddTime) {
		this.addTime = propertyAddTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date propertyUpdateTime) {
		this.updateTime = propertyUpdateTime;
	}
}
