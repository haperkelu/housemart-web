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
public class HouseContactEntity {
	private Integer id;
	private Integer houseId;
	private Integer contactType;
	private String name;
	private String mobile;
	private String mobile2;
	private String mobile3;
	private String memo;
	private Integer status;
	private Date addTime;
	private Date updateTime;
	private Integer committer;
	public enum ContactTypeEnum {
		Host(1), Agent(2);
		private int value;
		ContactTypeEnum(int value) {
			this.setValue(value);
		}
		public void setValue(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	public enum StatusEnum {
		Applying(1), Valid(2), Reject(3);
		private int value;
		StatusEnum(int value) {
			this.setValue(value);
		}
		public void setValue(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public Integer getContactType() {
		return contactType;
	}
	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String contactMobile) {
		this.mobile = contactMobile;
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
	public Integer getCommitter() {
		return committer;
	}
	public void setCommitter(Integer committer) {
		this.committer = committer;
	}
	/**
	 * @return the mobile2
	 */
	public String getMobile2() {
		return mobile2;
	}
	/**
	 * @param mobile2 the mobile2 to set
	 */
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	/**
	 * @return the mobile3
	 */
	public String getMobile3() {
		return mobile3;
	}
	/**
	 * @param mobile3 the mobile3 to set
	 */
	public void setMobile3(String mobile3) {
		this.mobile3 = mobile3;
	}
	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
