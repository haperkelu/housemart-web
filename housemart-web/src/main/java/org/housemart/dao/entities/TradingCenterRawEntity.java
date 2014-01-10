/**
 * Created on 2013-8-8
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.dao.entities;

public class TradingCenterRawEntity {
  String regionName;
  String plateName;
  String residenceName;
  String residenceAddress;
  String houseType;
  String propertyArea;
  String price;
  String dealDate;
  
  public String getRegionName() {
    return regionName;
  }
  public void setRegionName(String regionName) {
    this.regionName = regionName;
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
  public String getResidenceAddress() {
    return residenceAddress;
  }
  public void setResidenceAddress(String residenceAddress) {
    this.residenceAddress = residenceAddress;
  }
  public String getHouseType() {
    return houseType;
  }
  public void setHouseType(String houseType) {
    this.houseType = houseType;
  }
  public String getPropertyArea() {
    return propertyArea;
  }
  public void setPropertyArea(String propertyArea) {
    this.propertyArea = propertyArea;
  }
  public String getPrice() {
    return price;
  }
  public void setPrice(String price) {
    this.price = price;
  }
  public String getDealDate() {
    return dealDate;
  }
  public void setDealDate(String dealDate) {
    this.dealDate = dealDate;
  }
  
  
}
