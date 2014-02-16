/**
 * Created on 2012-9-20
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.dao.entities;

import java.util.Date;

/**
 * 
 * @author pqin
 */
public class HouseEntity {
  protected Integer id;
  protected String name;
  protected Integer status;
  protected Integer residenceId;
  protected Integer cellId;
  protected Integer picId;
  protected Integer roomTypePicId;
  protected Integer direction;
  protected Float propertyArea;
  protected Float occupiedArea;
  protected Date buildTime;
  protected String houseType;
  protected Integer roomType;
  protected Integer decorating;
  protected Integer floor;
  protected Integer hasKey;
  protected String hasKeyMemo;
  protected Integer viewHouseType;
  protected String memo;
  protected Date addTime;
  protected Date updateTime;
  protected Integer creator;
  protected Integer lastUpdater;
  protected Integer sourceType;
  protected Integer clientType;
  protected String buildingNo;
  protected String cellNo;
  protected String detailName;
  protected String blockUnit;
  
  public static enum StatusEnum {
    Default(0), Valid(1), Invalid(2), Deleted(-1), OffBoard(-2);
    public Integer value;
    
    StatusEnum(Integer status) {
      this.value = status;
    }
  }
  
  public static enum DecoratingEnum {
    Well(1), Simple(2), Blank(3), Crude(4), Luxurious(5);
    public Integer value;
    
    DecoratingEnum(Integer decorating) {
      this.value = decorating;
    }
  }
  
  public static enum HasKeyEnum {
    Has(1), No(0);
    public Integer value;
    
    HasKeyEnum(Integer hasKey) {
      this.value = hasKey;
    }
  }
  
  public static enum ViewHouseTypeEnum {
    Appoint(1), Instant(2);
    public Integer value;
    
    ViewHouseTypeEnum(Integer viewHouseType) {
      this.value = viewHouseType;
    }
  }
  
  public static enum FloorEnum {
    low(1), midlow(2), middle(3), midhigh(4), high(5);
    public Integer value;
    
    FloorEnum(Integer floor) {
      this.value = floor;
    }
  }
  
  public static enum SourceTypeEnum {
    internal(1), customerservice(2), external(3);
    public Integer value;
    
    SourceTypeEnum(Integer sourceType) {
      this.value = sourceType;
    }
  }
  
  public static enum ClientTypeEnum {
		web(1), ios(2), android(3);
		public Integer value;

		ClientTypeEnum(Integer clientType) {
			this.value = clientType;
		}
		
		public static String stringValueOf(int v){
		  String result = null;
		  for(ClientTypeEnum typeEnum : ClientTypeEnum.values()){
		    if(typeEnum.value.intValue() == v){
		      result = typeEnum.name();
		      break;
		    }
		  }
		  return result;
		}
	}
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public Integer getStatus() {
    return status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public Integer getResidenceId() {
    return residenceId;
  }
  
  public void setResidenceId(Integer residenceId) {
    this.residenceId = residenceId;
  }
  
  public Integer getCellId() {
    return cellId;
  }
  
  public void setCellId(Integer cellId) {
    this.cellId = cellId;
  }
  
  public Integer getPicId() {
    return picId;
  }
  
  public void setPicId(Integer picId) {
    this.picId = picId;
  }
  
  public Integer getRoomTypePicId() {
    return roomTypePicId;
  }
  
  public void setRoomTypePicId(Integer roomTypePicId) {
    this.roomTypePicId = roomTypePicId;
  }
  
  public Integer getDirection() {
    return direction;
  }
  
  public void setDirection(Integer direction) {
    this.direction = direction;
  }
  
  public Float getPropertyArea() {
    return propertyArea;
  }
  
  public void setPropertyArea(Float propertyArea) {
    this.propertyArea = propertyArea;
  }
  
  public Float getOccupiedArea() {
    return occupiedArea;
  }
  
  public void setOccupiedArea(Float occupiedArea) {
    this.occupiedArea = occupiedArea;
  }
  
  public Date getBuildTime() {
    return buildTime;
  }
  
  public void setBuildTime(Date buildTime) {
    this.buildTime = buildTime;
  }
  
  public String getHouseType() {
    return houseType;
  }
  
  public void setHouseType(String houseType) {
    this.houseType = houseType;
  }
  
  public Integer getRoomType() {
    return roomType;
  }
  
  public void setRoomType(Integer roomType) {
    this.roomType = roomType;
  }
  
  public Integer getDecorating() {
    return decorating;
  }
  
  public void setDecorating(Integer decorating) {
    this.decorating = decorating;
  }
  
  public Integer getHasKey() {
    return hasKey;
  }
  
  public void setHasKey(Integer hasKey) {
    this.hasKey = hasKey;
  }
  
  public String getHasKeyMemo() {
    return hasKeyMemo;
  }
  
  public void setHasKeyMemo(String hasKeyMemo) {
    this.hasKeyMemo = hasKeyMemo;
  }
  
  public Integer getViewHouseType() {
    return viewHouseType;
  }
  
  public void setViewHouseType(Integer viewHouseType) {
    this.viewHouseType = viewHouseType;
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
  
  public Integer getCreator() {
    return creator;
  }
  
  public void setCreator(Integer creator) {
    this.creator = creator;
  }
  
  public Integer getLastUpdater() {
    return lastUpdater;
  }
  
  public void setLastUpdater(Integer lastUpdator) {
    this.lastUpdater = lastUpdator;
  }
  
  public Integer getFloor() {
    return floor;
  }
  
  public void setFloor(Integer floor) {
    this.floor = floor;
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
  
  public String getDetailName() {
    return detailName;
  }
  
  public void setDetailName(String detailName) {
    this.detailName = detailName;
  }
  
  public String getBlockUnit() {
    return blockUnit;
  }
  
  public void setBlockUnit(String blockUnit) {
    this.blockUnit = blockUnit;
  }

public Integer getClientType() {
	return clientType;
}

public void setClientType(Integer clientType) {
	this.clientType = clientType;
}
  
}
