/**
 * Created on 2013-5-17
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.dao.entities;

import java.util.Date;

public class HouseExtAgileEntity extends HouseEntity {
  
  private Float rentPrice;
  private Integer rentRentStatus;
  private Float salePrice;
  private Integer saleSaleStatus;
  private String residenceName;
  private Integer cellCodeType;
  private String cellCodeBegin;
  private String cellCodeEnd;
  private Integer cellFloorBegin;
  private Integer cellFloorEnd;
  private Integer buildingId;
  private String buildingPrefix;
  private String buildingSuffix;
  private Integer buildingCodeType;
  private String buildingCodeBegin;
  private String buildingCodeEnd;
  private String buildingPeriod;
  private Integer buildingStair;
  private Integer favoriteId;
  private String soldBuildingInfo;
  private Integer sourceType;
  private String buildingNo;
  private String cellNo;
  private Date onboardTime;
  private Date applyTime;
  private String creatorName;
  
  private String maxinBuildingNo;
  private String maxinCellNo;
  
  private Date auditTime;
  
  public Float getRentPrice() {
    return rentPrice;
  }
  
  public void setRentPrice(Float rentPrice) {
    this.rentPrice = rentPrice;
  }
  
  public Integer getRentRentStatus() {
    return rentRentStatus;
  }
  
  public void setRentRentStatus(Integer rentRentStatus) {
    this.rentRentStatus = rentRentStatus;
  }
  
  public Float getSalePrice() {
    return salePrice;
  }
  
  public void setSalePrice(Float salePrice) {
    this.salePrice = salePrice;
  }
  
  public Integer getSaleSaleStatus() {
    return saleSaleStatus;
  }
  
  public void setSaleSaleStatus(Integer saleSaleStatus) {
    this.saleSaleStatus = saleSaleStatus;
  }
  
  public String getResidenceName() {
    return residenceName;
  }
  
  public void setResidenceName(String residenceName) {
    this.residenceName = residenceName;
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
  
  public Integer getFavoriteId() {
    return favoriteId;
  }
  
  public void setFavoriteId(Integer favoriteId) {
    this.favoriteId = favoriteId;
  }
  
  public String getSoldBuildingInfo() {
    return soldBuildingInfo;
  }
  
  public void setSoldBuildingInfo(String soldBuildingInfo) {
    this.soldBuildingInfo = soldBuildingInfo;
  }
  
  public Integer getSourceType() {
    return sourceType;
  }
  
  public void setSourceType(Integer sourceType) {
    this.sourceType = sourceType;
  }
  
  public String getBuildingNo() {
    return buildingNo;
  }
  
  public void setBuildingNo(String buildingNo) {
    this.buildingNo = buildingNo;
  }
  
  public String getCellNo() {
    return cellNo;
  }
  
  public void setCellNo(String cellNo) {
    this.cellNo = cellNo;
  }
  
  public Date getOnboardTime() {
    return onboardTime;
  }
  
  public void setOnboardTime(Date onboardTime) {
    this.onboardTime = onboardTime;
  }
  
  public Date getApplyTime() {
    return applyTime;
  }
  
  public void setApplyTime(Date applyTime) {
    this.applyTime = applyTime;
  }
  
  public String getCreatorName() {
    return creatorName;
  }
  
  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  public String getMaxinBuildingNo() {
    return maxinBuildingNo;
  }

  public void setMaxinBuildingNo(String maxinBuildingNo) {
    this.maxinBuildingNo = maxinBuildingNo;
  }

  public String getMaxinCellNo() {
    return maxinCellNo;
  }

  public void setMaxinCellNo(String maxinCellNo) {
    this.maxinCellNo = maxinCellNo;
  }

  public Date getAuditTime() {
    return auditTime;
  }

  public void setAuditTime(Date auditTime) {
    this.auditTime = auditTime;
  }

}
