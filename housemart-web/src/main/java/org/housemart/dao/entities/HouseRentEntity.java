/**
 * Created on 2012-9-29
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.dao.entities;

import java.util.Date;

public class HouseRentEntity {
  private Integer id;
  private Integer houseId;
  private String tagList;
  private Float price;
  private Float basePrice;
  private Integer rentType;
  private Integer rentStatus;
  private Integer rentRec;
  private String equipmentList;
  private String memo;
  private Date dealTime;
  private Date addTime;
  private Date updateTime;
  private Integer owner;
  
  public static enum RentTypeEnum {
    Sole(1), Share(2);
    public Integer rentType;
    
    RentTypeEnum(Integer rentType) {
      this.rentType = rentType;
    }
  }
  
  public static enum RentStatusEnum {
    Renting(1), NotRent(2), Rented(3);
    public Integer rentStatus;
    
    RentStatusEnum(Integer rentStatus) {
      this.rentStatus = rentStatus;
    }
  }
  
  public static enum RentRecEnum {
    None(1), Emergent(2), Recommend(3);
    public Integer rentRec;
    
    RentRecEnum(Integer rentRec) {
      this.rentRec = rentRec;
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
  
  public Integer getRentType() {
    return rentType;
  }
  
  public void setRentType(Integer rentType) {
    this.rentType = rentType;
  }
  
  public Integer getRentStatus() {
    return rentStatus;
  }
  
  public void setRentStatus(Integer rentStatus) {
    this.rentStatus = rentStatus;
  }
  
  public Integer getRentRec() {
    return rentRec;
  }
  
  public void setRentRec(Integer rentRec) {
    this.rentRec = rentRec;
  }
  
  public String getEquipmentList() {
    return equipmentList;
  }
  
  public void setEquipmentList(String equipmentList) {
    this.equipmentList = equipmentList;
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
  
  public Date getDealTime() {
    return dealTime;
  }
  
  public void setDealTime(Date dealTime) {
    this.dealTime = dealTime;
  }
  
}
