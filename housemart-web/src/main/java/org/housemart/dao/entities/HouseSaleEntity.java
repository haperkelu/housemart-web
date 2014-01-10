/**
 * Created on 2012-9-29
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.dao.entities;

import java.util.Date;

public class HouseSaleEntity {
	private Integer id;
	private Integer houseId;
	private String tagList;
	private Float avgPrice;
	private Float price;
	private Float basePrice;
	private String dealType;
	private Integer saleStatus;
	private Integer saleRec;
	private String source;
	private String memo;
	private Date dealTime;
	private Date addTime;
	private Date updateTime;
	private Integer owner;

	public static enum DealTypeEnum {
		DutchPay(1), BuyerPay(2);
		public Integer dealType;
		DealTypeEnum(Integer dealType) {
			this.dealType = dealType;
		}
	}
	public static enum SaleStatusEnum {
		Saling(1), NotSale(2);
		public Integer saleStatus;
		SaleStatusEnum(Integer saleStatus) {
			this.saleStatus = saleStatus;
		}
	}
	public static enum SaleRecEnum {
		None(0), Emergent(1), Recommend(2);
		public Integer saleRec;
		SaleRecEnum(Integer saleRec) {
			this.saleRec = saleRec;
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
	public String getTagList() {
		return tagList;
	}
	public void setTagList(String tagList) {
		this.tagList = tagList;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Float basePrice) {
		this.basePrice = basePrice;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public Integer getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}
	public Integer getSaleRec() {
		return saleRec;
	}
	public void setSaleRec(Integer saleRec) {
		this.saleRec = saleRec;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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
	public Integer getOwner() {
		return owner;
	}
	public void setOwner(Integer owner) {
		this.owner = owner;
	}
	public Float getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(Float avgPrice) {
		this.avgPrice = avgPrice;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
}
