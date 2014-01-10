/**
 * 
 */
package org.housemart.dao.entities;

import java.util.Date;

/**
 * @author user
 *
 */
public class HouseContactView {

	private int id;
	private int accountId;
	private int type; // 0 本小区, 1 外小区 
	private Date fromDate;
	private Date endDate;
	private int base;
	private int viewCount;
	private Date addTime;
	private Date updateTime;
	
	public static enum TypeEnum {
    own(0), cross(1);
    public int value;
    
    TypeEnum(int status) {
      this.value = status;
    }
  }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getBase() {
		return base;
	}
	public void setBase(int base) {
		this.base = base;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
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
  public int getType() {
    return type;
  }
  public void setType(int type) {
    this.type = type;
  }

	
}
