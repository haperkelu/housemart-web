package org.housemart.crawl.ziprealty.model;

import java.util.Date;

public class ZrHouseHmHouse {
    private int id;
    private int zrId;
    private int hmId;
    private int accountId;
    private Date addTime;
    private Date updateTime;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getZrId() {
	return zrId;
    }

    public void setZrId(int zrId) {
	this.zrId = zrId;
    }

    public int getHmId() {
	return hmId;
    }

    public void setHmId(int hmId) {
	this.hmId = hmId;
    }

    public int getAccountId() {
	return accountId;
    }

    public void setAccountId(int accountId) {
	this.accountId = accountId;
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

}
