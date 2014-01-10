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
public class HouseExtEntity extends HouseEntity {
  private Integer contactId;
  private Integer contactContactType;
  private String contactName;
  private String contactMobile;
  private String contactMemo;
  private Integer contactStatus;
  private Date contactAddTime;
  private Date contactUpdateTime;
  private Integer contactCommitter;
  private Integer propertyId;
  private String propertyPropertyCode;
  private String propertyPropertyOwner;
  private String propertyLoan;
  private Integer propertyVersion;
  private Date propertyAddTime;
  private Date propertyUpdateTime;
  private Integer rentId;
  private String rentTagList;
  private Float rentPrice;
  private Float rentBasePrice;
  private Integer rentRentType;
  private Integer rentRentStatus;
  private Integer rentRentRec;
  private String rentEquipmentList;
  private String rentMemo;
  private Date rentDealTime;
  private Date rentAddTime;
  private Date rentUpdateTime;
  private Integer rentOwner;
  private Integer saleId;
  private String saleTagList;
  private Float saleAvgPrice;
  private Float salePrice;
  private Float saleBasePrice;
  private String saleDealType;
  private Integer saleSaleStatus;
  private Integer saleSaleRec;
  private String saleSource;
  private String saleMemo;
  private Date saleDealTime;
  private Date saleAddTime;
  private Date saleUpdateTime;
  private Integer saleOwner;
  private Double residenceRegionId;
  private String residenceName;
  private String residenceAliasName;
  private Float residenceAvg;
  private String residenceAddress;
  private String residenceHeadCount;
  private String residenceParking;
  private String residenceGreenRate;
  private String residencePropertyFee;
  private String residenceVolumeRate;
  private String residencePropertyType;
  private String residenceDeveloper;
  private Integer residenceAnjuKeId;
  private String residenceArea;
  private Integer residenceStatus;
  private Integer cellCodeType;
  private String cellCodeBegin;
  private String cellCodeEnd;
  private Integer cellStatus;
  private Integer cellFloorBegin;
  private Integer cellFloorEnd;
  private Integer buildingId;
  private String buildingPrefix;
  private String buildingSuffix;
  private Integer buildingCodeType;
  private String buildingCodeBegin;
  private String buildingCodeEnd;
  private Integer buildingStatus;
  private String buildingPeriod;
  private Integer buildingStair;
  private Integer buildingHouseHold;
  private String buildingBuildingType;
  private Double regionId;
  private String regionName;
  private Double regionCityId;
  private Double regionParentId;
  private Integer regionLevel;
  private String regionDescription;
  private Date regionAddTime;
  private String regionUrl;
  private String regionNickName;
  private Integer favoriteId;
  private Integer favoriteUserId;
  private Date favoriteAddTime;
  private Integer soldBuildingId;
  private String soldBuildingInfo;
  private String buildTimeString;
  private String accountName;
  
  
  public Integer getContactId() {
    return contactId;
  }
  
  public void setContactId(Integer contactId) {
    this.contactId = contactId;
  }
  
  public Integer getContactContactType() {
    return contactContactType;
  }
  
  public void setContactContactType(Integer contactContactType) {
    this.contactContactType = contactContactType;
  }
  
  public String getContactName() {
    return contactName;
  }
  
  public void setContactName(String contactName) {
    this.contactName = contactName;
  }
  
  public String getContactMobile() {
    return contactMobile;
  }
  
  public String getContactMemo() {
    return contactMemo;
  }
  
  public void setContactMemo(String contactMemo) {
    this.contactMemo = contactMemo;
  }
  
  public void setContactMobile(String contactMobile) {
    this.contactMobile = contactMobile;
  }
  
  public Integer getContactStatus() {
    return contactStatus;
  }
  
  public void setContactStatus(Integer contactStatus) {
    this.contactStatus = contactStatus;
  }
  
  public Date getContactAddTime() {
    return contactAddTime;
  }
  
  public void setContactAddTime(Date contactAddTime) {
    this.contactAddTime = contactAddTime;
  }
  
  public Date getContactUpdateTime() {
    return contactUpdateTime;
  }
  
  public void setContactUpdateTime(Date contactUpdateTime) {
    this.contactUpdateTime = contactUpdateTime;
  }
  
  public Integer getContactCommitter() {
    return contactCommitter;
  }
  
  public void setContactCommitter(Integer contactCommitter) {
    this.contactCommitter = contactCommitter;
  }
  
  public Integer getPropertyId() {
    return propertyId;
  }
  
  public void setPropertyId(Integer propertyId) {
    this.propertyId = propertyId;
  }
  
  public String getPropertyPropertyCode() {
    return propertyPropertyCode;
  }
  
  public void setPropertyPropertyCode(String propertyPropertyCode) {
    this.propertyPropertyCode = propertyPropertyCode;
  }
  
  public String getPropertyPropertyOwner() {
    return propertyPropertyOwner;
  }
  
  public void setPropertyPropertyOwner(String propertyPropertyOwner) {
    this.propertyPropertyOwner = propertyPropertyOwner;
  }
  
  public String getPropertyLoan() {
    return propertyLoan;
  }
  
  public void setPropertyLoan(String propertyLoan) {
    this.propertyLoan = propertyLoan;
  }
  
  public Integer getPropertyVersion() {
    return propertyVersion;
  }
  
  public void setPropertyVersion(Integer propertyVersioin) {
    this.propertyVersion = propertyVersioin;
  }
  
  public Date getPropertyAddTime() {
    return propertyAddTime;
  }
  
  public void setPropertyAddTime(Date propertyAddTime) {
    this.propertyAddTime = propertyAddTime;
  }
  
  public Date getPropertyUpdateTime() {
    return propertyUpdateTime;
  }
  
  public void setPropertyUpdateTime(Date propertyUpdateTime) {
    this.propertyUpdateTime = propertyUpdateTime;
  }
  
  public Integer getRentId() {
    return rentId;
  }
  
  public void setRentId(Integer rentId) {
    this.rentId = rentId;
  }
  
  public String getRentTagList() {
    return rentTagList;
  }
  
  public void setRentTagList(String rentTagList) {
    this.rentTagList = rentTagList;
  }
  
  public Float getRentPrice() {
    return rentPrice;
  }
  
  public void setRentPrice(Float rentPrice) {
    this.rentPrice = rentPrice;
  }
  
  public Float getRentBasePrice() {
    return rentBasePrice;
  }
  
  public void setRentBasePrice(Float rentBasePrice) {
    this.rentBasePrice = rentBasePrice;
  }
  
  public Integer getRentRentType() {
    return rentRentType;
  }
  
  public void setRentRentType(Integer rentRentType) {
    this.rentRentType = rentRentType;
  }
  
  public Integer getRentRentStatus() {
    return rentRentStatus;
  }
  
  public void setRentRentStatus(Integer rentRentStatus) {
    this.rentRentStatus = rentRentStatus;
  }
  
  public Integer getRentRentRec() {
    return rentRentRec;
  }
  
  public void setRentRentRec(Integer rentRentRec) {
    this.rentRentRec = rentRentRec;
  }
  
  public String getRentEquipmentList() {
    return rentEquipmentList;
  }
  
  public void setRentEquipmentList(String rentEquipmentList) {
    this.rentEquipmentList = rentEquipmentList;
  }
  
  public String getRentMemo() {
    return rentMemo;
  }
  
  public void setRentMemo(String rentMemo) {
    this.rentMemo = rentMemo;
  }
  
  public Date getRentDealTime() {
    return rentDealTime;
  }
  
  public void setRentDealTime(Date rentDealTime) {
    this.rentDealTime = rentDealTime;
  }
  
  public Date getRentAddTime() {
    return rentAddTime;
  }
  
  public void setRentAddTime(Date rentAddTime) {
    this.rentAddTime = rentAddTime;
  }
  
  public Date getRentUpdateTime() {
    return rentUpdateTime;
  }
  
  public void setRentUpdateTime(Date rentUpdateTime) {
    this.rentUpdateTime = rentUpdateTime;
  }
  
  public Integer getRentOwner() {
    return rentOwner;
  }
  
  public void setRentOwner(Integer rentOwner) {
    this.rentOwner = rentOwner;
  }
  
  public Integer getSaleId() {
    return saleId;
  }
  
  public void setSaleId(Integer saleId) {
    this.saleId = saleId;
  }
  
  public String getSaleTagList() {
    return saleTagList;
  }
  
  public void setSaleTagList(String saleTagList) {
    this.saleTagList = saleTagList;
  }
  
  public Float getSalePrice() {
    return salePrice;
  }
  
  public void setSalePrice(Float salePrice) {
    this.salePrice = salePrice;
  }
  
  public Float getSaleBasePrice() {
    return saleBasePrice;
  }
  
  public void setSaleBasePrice(Float saleBasePrice) {
    this.saleBasePrice = saleBasePrice;
  }
  
  public String getSaleDealType() {
    return saleDealType;
  }
  
  public void setSaleDealType(String saleDealType) {
    this.saleDealType = saleDealType;
  }
  
  public Integer getSaleSaleStatus() {
    return saleSaleStatus;
  }
  
  public void setSaleSaleStatus(Integer saleSaleStatus) {
    this.saleSaleStatus = saleSaleStatus;
  }
  
  public Integer getSaleSaleRec() {
    return saleSaleRec;
  }
  
  public void setSaleSaleRec(Integer saleSaleRec) {
    this.saleSaleRec = saleSaleRec;
  }
  
  public String getSaleMemo() {
    return saleMemo;
  }
  
  public void setSaleMemo(String saleMemo) {
    this.saleMemo = saleMemo;
  }
  
  public Date getSaleAddTime() {
    return saleAddTime;
  }
  
  public void setSaleAddTime(Date saleAddTime) {
    this.saleAddTime = saleAddTime;
  }
  
  public Date getSaleUpdateTime() {
    return saleUpdateTime;
  }
  
  public void setSaleUpdateTime(Date saleUpdateTime) {
    this.saleUpdateTime = saleUpdateTime;
  }
  
  public Integer getSaleOwner() {
    return saleOwner;
  }
  
  public void setSaleOwner(Integer saleOwner) {
    this.saleOwner = saleOwner;
  }
  
  public Double getResidenceRegionId() {
    return residenceRegionId;
  }
  
  public void setResidenceRegionId(Double residenceRegionId) {
    this.residenceRegionId = residenceRegionId;
  }
  
  public String getResidenceName() {
    return residenceName;
  }
  
  public void setResidenceName(String residenceName) {
    this.residenceName = residenceName;
  }
  
  public String getResidenceAliasName() {
    return residenceAliasName;
  }
  
  public void setResidenceAliasName(String residenceAliasName) {
    this.residenceAliasName = residenceAliasName;
  }
  
  public Float getResidenceAvg() {
    return residenceAvg;
  }
  
  public void setResidenceAvg(Float residenceAvg) {
    this.residenceAvg = residenceAvg;
  }
  
  public String getResidenceAddress() {
    return residenceAddress;
  }
  
  public void setResidenceAddress(String residenceAddress) {
    this.residenceAddress = residenceAddress;
  }
  
  public String getResidenceHeadCount() {
    return residenceHeadCount;
  }
  
  public void setResidenceHeadCount(String residenceHeadCount) {
    this.residenceHeadCount = residenceHeadCount;
  }
  
  public String getResidenceParking() {
    return residenceParking;
  }
  
  public void setResidenceParking(String residenceParking) {
    this.residenceParking = residenceParking;
  }
  
  public String getResidenceGreenRate() {
    return residenceGreenRate;
  }
  
  public void setResidenceGreenRate(String residenceGreenRate) {
    this.residenceGreenRate = residenceGreenRate;
  }
  
  public String getResidencePropertyFee() {
    return residencePropertyFee;
  }
  
  public void setResidencePropertyFee(String residencePropertyFee) {
    this.residencePropertyFee = residencePropertyFee;
  }
  
  public String getResidenceVolumeRate() {
    return residenceVolumeRate;
  }
  
  public void setResidenceVolumeRate(String residenceVolumeRate) {
    this.residenceVolumeRate = residenceVolumeRate;
  }
  
  public String getResidencePropertyType() {
    return residencePropertyType;
  }
  
  public void setResidencePropertyType(String residencePropertyType) {
    this.residencePropertyType = residencePropertyType;
  }
  
  public String getResidenceDeveloper() {
    return residenceDeveloper;
  }
  
  public void setResidenceDeveloper(String residenceDeveloper) {
    this.residenceDeveloper = residenceDeveloper;
  }
  
  public Integer getResidenceAnjuKeId() {
    return residenceAnjuKeId;
  }
  
  public void setResidenceAnjuKeId(Integer residenceAnjuKeId) {
    this.residenceAnjuKeId = residenceAnjuKeId;
  }
  
  public String getResidenceArea() {
    return residenceArea;
  }
  
  public void setResidenceArea(String residenceArea) {
    this.residenceArea = residenceArea;
  }
  
  public Integer getResidenceStatus() {
    return residenceStatus;
  }
  
  public void setResidenceStatus(Integer residenceStatus) {
    this.residenceStatus = residenceStatus;
  }
  
  public Integer getCellCodeType() {
    return cellCodeType;
  }
  
  public void setCellCodeType(Integer cellCodeType) {
    this.cellCodeType = cellCodeType;
  }
  
  public String getCellCodeBegin() {
    return cellCodeBegin;
  }
  
  public void setCellCodeBegin(String cellCodeBegin) {
    this.cellCodeBegin = cellCodeBegin;
  }
  
  public String getCellCodeEnd() {
    return cellCodeEnd;
  }
  
  public void setCellCodeEnd(String cellCodeEnd) {
    this.cellCodeEnd = cellCodeEnd;
  }
  
  public Integer getCellStatus() {
    return cellStatus;
  }
  
  public void setCellStatus(Integer cellStatus) {
    this.cellStatus = cellStatus;
  }
  
  public Integer getCellFloorBegin() {
    return cellFloorBegin;
  }
  
  public void setCellFloorBegin(Integer cellFloorBegin) {
    this.cellFloorBegin = cellFloorBegin;
  }
  
  public Integer getCellFloorEnd() {
    return cellFloorEnd;
  }
  
  public void setCellFloorEnd(Integer cellFloorEnd) {
    this.cellFloorEnd = cellFloorEnd;
  }
  
  public Integer getBuildingId() {
    return buildingId;
  }
  
  public void setBuildingId(Integer buildingId) {
    this.buildingId = buildingId;
  }
  
  public String getBuildingPrefix() {
    return buildingPrefix;
  }
  
  public void setBuildingPrefix(String buildingPrefix) {
    this.buildingPrefix = buildingPrefix;
  }
  
  public String getBuildingSuffix() {
    return buildingSuffix;
  }
  
  public void setBuildingSuffix(String buildingSuffix) {
    this.buildingSuffix = buildingSuffix;
  }
  
  public Integer getBuildingCodeType() {
    return buildingCodeType;
  }
  
  public void setBuildingCodeType(Integer buildingCodeType) {
    this.buildingCodeType = buildingCodeType;
  }
  
  public String getBuildingCodeBegin() {
    return buildingCodeBegin;
  }
  
  public void setBuildingCodeBegin(String buildingCodeBegin) {
    this.buildingCodeBegin = buildingCodeBegin;
  }
  
  public String getBuildingCodeEnd() {
    return buildingCodeEnd;
  }
  
  public void setBuildingCodeEnd(String buildingCodeEnd) {
    this.buildingCodeEnd = buildingCodeEnd;
  }
  
  public Integer getBuildingStatus() {
    return buildingStatus;
  }
  
  public void setBuildingStatus(Integer buildingStatus) {
    this.buildingStatus = buildingStatus;
  }
  
  public String getBuildingPeriod() {
    return buildingPeriod;
  }
  
  public void setBuildingPeriod(String buildingPeriod) {
    this.buildingPeriod = buildingPeriod;
  }
  
  public Integer getBuildingStair() {
    return buildingStair;
  }
  
  public void setBuildingStair(Integer buildingStair) {
    this.buildingStair = buildingStair;
  }
  
  public Integer getBuildingHouseHold() {
    return buildingHouseHold;
  }
  
  public void setBuildingHouseHold(Integer buildingHouseHold) {
    this.buildingHouseHold = buildingHouseHold;
  }
  
  public String getBuildingBuildingType() {
    return buildingBuildingType;
  }
  
  public void setBuildingBuildingType(String buildingBuildingType) {
    this.buildingBuildingType = buildingBuildingType;
  }
  
  public Double getRegionId() {
    return regionId;
  }
  
  public void setRegionId(Double regionId) {
    this.regionId = regionId;
  }
  
  public String getRegionName() {
    return regionName;
  }
  
  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }
  
  public Double getRegionCityId() {
    return regionCityId;
  }
  
  public void setRegionCityId(Double regionCityId) {
    this.regionCityId = regionCityId;
  }
  
  public Double getRegionParentId() {
    return regionParentId;
  }
  
  public void setRegionParentId(Double regionParentId) {
    this.regionParentId = regionParentId;
  }
  
  public Integer getRegionLevel() {
    return regionLevel;
  }
  
  public void setRegionLevel(Integer regionLevel) {
    this.regionLevel = regionLevel;
  }
  
  public String getRegionDescription() {
    return regionDescription;
  }
  
  public void setRegionDescription(String regionDescription) {
    this.regionDescription = regionDescription;
  }
  
  public Date getRegionAddTime() {
    return regionAddTime;
  }
  
  public void setRegionAddTime(Date regionAddTime) {
    this.regionAddTime = regionAddTime;
  }
  
  public String getRegionUrl() {
    return regionUrl;
  }
  
  public void setRegionUrl(String regionUrl) {
    this.regionUrl = regionUrl;
  }
  
  public String getRegionNickName() {
    return regionNickName;
  }
  
  public void setRegionNickName(String regionNickName) {
    this.regionNickName = regionNickName;
  }
  
  public Integer getFavoriteId() {
    return favoriteId;
  }
  
  public void setFavoriteId(Integer favoriteId) {
    this.favoriteId = favoriteId;
  }
  
  public Integer getFavoriteUserId() {
    return favoriteUserId;
  }
  
  public void setFavoriteUserId(Integer favoriteUserId) {
    this.favoriteUserId = favoriteUserId;
  }
  
  public Date getFavoriteAddTime() {
    return favoriteAddTime;
  }
  
  public void setFavoriteAddTime(Date favoriteAddTime) {
    this.favoriteAddTime = favoriteAddTime;
  }
  
  public Float getSaleAvgPrice() {
    return saleAvgPrice;
  }
  
  public void setSaleAvgPrice(Float saleAvgPrice) {
    this.saleAvgPrice = saleAvgPrice;
  }
  
  public String getSaleSource() {
    return saleSource;
  }
  
  public void setSaleSource(String saleSource) {
    this.saleSource = saleSource;
  }
  
  public Date getSaleDealTime() {
    return saleDealTime;
  }
  
  public void setSaleDealTime(Date saleDealTime) {
    this.saleDealTime = saleDealTime;
  }
  
  public String getSoldBuildingInfo() {
    return soldBuildingInfo;
  }
  
  public void setSoldBuildingInfo(String soldBuildingInfo) {
    this.soldBuildingInfo = soldBuildingInfo;
  }
  
  public Integer getSoldBuildingId() {
    return soldBuildingId;
  }
  
  public void setSoldBuildingId(Integer soldBuildingId) {
    this.soldBuildingId = soldBuildingId;
  }
  
  public String getBuildTimeString() {
    return buildTimeString;
  }
  
  public void setBuildTimeString(String buildTimeString) {
    this.buildTimeString = buildTimeString;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public HouseContactEntity generateHouseContactEntity() {
    HouseContactEntity contract = null;
    contract = new HouseContactEntity();
    contract.setId(this.getContactId());
    contract.setHouseId(this.getId());
    contract.setContactType(this.getContactContactType());
    contract.setName(this.getContactName());
    contract.setMobile(this.getContactMobile());
    contract.setStatus(this.getContactStatus());
    contract.setAddTime(this.getContactAddTime());
    contract.setUpdateTime(this.getContactUpdateTime());
    contract.setCommitter(this.getContactCommitter());
    contract.setMemo(this.getContactMemo());
    return contract;
  }
  
  public HousePropertyEntity generateHousePropertyEntity() {
    HousePropertyEntity property = new HousePropertyEntity();
    property.setId(this.getPropertyId());
    property.setHouseId(this.getId());
    property.setPropertyCode(this.getPropertyPropertyCode());
    property.setPropertyOwner(this.getPropertyPropertyOwner());
    property.setLoan(this.getPropertyLoan());
    property.setVersion(this.getPropertyVersion());
    property.setAddTime(this.getPropertyAddTime());
    property.setUpdateTime(this.getPropertyUpdateTime());
    return property;
  }
  
  public HouseSaleEntity generateHouseSaleEntity() {
    HouseSaleEntity sale = new HouseSaleEntity();
    sale.setId(this.getSaleId());
    sale.setHouseId(this.getId());
    sale.setTagList(this.getSaleTagList());
    sale.setAvgPrice(this.getSaleAvgPrice());
    sale.setPrice(this.getSalePrice());
    sale.setBasePrice(this.getSaleBasePrice());
    sale.setDealType(this.getSaleDealType());
    sale.setSaleRec(this.getSaleSaleRec());
    sale.setSaleStatus(this.getSaleSaleStatus());
    sale.setSource(this.getSaleSource());
    sale.setMemo(this.getSaleMemo());
    sale.setDealTime(this.getSaleDealTime());
    sale.setAddTime(this.getSaleAddTime());
    sale.setUpdateTime(this.getSaleUpdateTime());
    sale.setOwner(this.getSaleOwner());
    return sale;
  }
  
  public HouseRentEntity generateHouseRentEntity() {
    HouseRentEntity rent = new HouseRentEntity();
    rent.setId(this.getRentId());
    rent.setHouseId(this.getId());
    rent.setTagList(this.getRentTagList());
    rent.setPrice(this.getRentPrice());
    rent.setBasePrice(this.getRentBasePrice());
    rent.setRentType(this.getRentRentType());
    rent.setRentRec(this.getRentRentRec());
    rent.setRentStatus(this.getRentRentStatus());
    rent.setEquipmentList(this.getRentEquipmentList());
    rent.setMemo(this.getRentMemo());
    rent.setDealTime(this.getRentDealTime());
    rent.setUpdateTime(this.getRentUpdateTime());
    rent.setAddTime(this.getRentAddTime());
    rent.setOwner(this.getRentOwner());
    return rent;
  }
  
  public HouseSoldBuildingEntity generateHouseSoldBuildingEntity() {
    HouseSoldBuildingEntity soldBuilding = new HouseSoldBuildingEntity();
    soldBuilding.setId(this.getSoldBuildingId());
    soldBuilding.setHouseId(this.getId());
    soldBuilding.setBuildingInfo(this.getSoldBuildingInfo());
    return soldBuilding;
  }
  
}
