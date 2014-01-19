package org.housemart.dao.entities;

public class ResidenceEntity implements Entity {
 
  protected int id;
  protected int residenceId;
  protected String parentRegionId;
  protected String regionId;
  protected String regionName;
  protected String plateName;
  protected String residenceName;
  private String aliasName;
  protected String address;
 
  protected String headCount;
  protected String parking;
  protected String propertyType;
  protected String greenRate;
  protected String volumeRate;
  protected String propertyFee;
  protected String developer;
  protected String finishedTime;
 
  protected int buildingCount;
  protected int cellCount;
  protected int picCount;
  private int picApprove;
 
  /** 是否设置经纬度 **/
  private boolean isPositionSet;
 
  private String pinyinName;
  private String firstPinyin;
  private Integer creator;
 
  protected int status;
 
  protected Integer forceShow;
  protected Integer zombie;
  protected Integer lockBasicInfo;
  protected Integer lockPic;
  protected Integer lockMap;
 
  protected int updateAuditPendingCount = 0;
 
  protected int year;
  protected int month;
  protected double annualPriceInc;
  protected double avgPrice;
  protected double annualTurnover;
  protected double annualRentRevenue;
  
  private int onSaleCount;
  private int onRentCount;
  
  public static enum StatusEnum {
    Default(0), Valid(1), Invalid(2);
    public Integer value;
   
    StatusEnum(Integer status) {
      this.value = status;
    }
  }
 
  public static enum ForceShowEnum {
    Default(0), Show(1);
    public int value;
   
    ForceShowEnum(int forceShow) {
      this.value = forceShow;
    }
  }
 
  public static enum ZombieEnum {
    Default(0), Zombie(1);
    public int value;
   
    ZombieEnum(int zombie) {
      this.value = zombie;
    }
  }
 
  public static enum LockBasicInfoEnum {
    notlock(0), lock(1);
    public int value;
   
    LockBasicInfoEnum(int value) {
      this.value = value;
    }
  }
 
  public static enum LockMapEnum {
    notlock(0), lock(1);
    public int value;
   
    LockMapEnum(int value) {
      this.value = value;
    }
  }
 
  public static enum LockPicEnum {
    notlock(0), lock(1);
    public int value;
   
    LockPicEnum(int value) {
      this.value = value;
    }
  }
 
  public boolean isPositionSet() {
    return isPositionSet;
  }
 
  public void setPositionSet(boolean isPositionSet) {
    this.isPositionSet = isPositionSet;
  }
 
  public int getId() {
    return id;
  }
 
  public void setId(int id) {
    this.id = id;
  }
 
  public int getResidenceId() {
    return residenceId;
  }
 
  public void setResidenceId(int residenceId) {
    this.residenceId = residenceId;
  }
 
  public String getRegionId() {
    return regionId;
  }
 
  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }
 
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
 
  public String getAddress() {
    return address;
  }
 
  public void setAddress(String address) {
    this.address = address;
  }
 
  public int getBuildingCount() {
    return buildingCount;
  }
 
  public void setBuildingCount(int buildingCount) {
    this.buildingCount = buildingCount;
  }
 
  public int getCellCount() {
    return cellCount;
  }
 
  public void setCellCount(int cellCount) {
    this.cellCount = cellCount;
  }
 
  public int getPicCount() {
    return picCount;
  }
 
  public void setPicCount(int picCount) {
    this.picCount = picCount;
  }
 
  public String getPinyinName() {
    return pinyinName;
  }
 
  public void setPinyinName(String pinyinName) {
    this.pinyinName = pinyinName;
  }
 
  public String getFirstPinyin() {
    return firstPinyin;
  }
 
  public void setFirstPinyin(String firstPinyin) {
    this.firstPinyin = firstPinyin;
  }
 
  public String getHeadCount() {
    return headCount;
  }
 
  public void setHeadCount(String headCount) {
    this.headCount = headCount;
  }
 
  public String getParking() {
    return parking;
  }
 
  public void setParking(String parking) {
    this.parking = parking;
  }
 
  public String getPropertyType() {
    return propertyType;
  }
 
  public void setPropertyType(String propertyType) {
    this.propertyType = propertyType;
  }
 
  public String getGreenRate() {
    return greenRate;
  }
 
  public void setGreenRate(String greenRate) {
    this.greenRate = greenRate;
  }
 
  public String getVolumeRate() {
    return volumeRate;
  }
 
  public void setVolumeRate(String volumeRate) {
    this.volumeRate = volumeRate;
  }
 
  public String getPropertyFee() {
    return propertyFee;
  }
 
  public void setPropertyFee(String propertyFee) {
    this.propertyFee = propertyFee;
  }
 
  public String getDeveloper() {
    return developer;
  }
 
  public void setDeveloper(String developer) {
    this.developer = developer;
  }
 
  public String getFinishedTime() {
    return finishedTime;
  }
 
  public void setFinishedTime(String finishedTime) {
    this.finishedTime = finishedTime;
  }
 
  public int getStatus() {
    return status;
  }
 
  public void setStatus(int status) {
    this.status = status;
  }
 
  public Integer getCreator() {
    return creator;
  }
 
  public void setCreator(Integer creator) {
    this.creator = creator;
  }
 
  public String getParentRegionId() {
    return parentRegionId;
  }
 
  public void setParentRegionId(String parentRegionId) {
    this.parentRegionId = parentRegionId;
  }
 
  public Integer getForceShow() {
    return forceShow;
  }
 
  public void setForceShow(Integer forceShow) {
    this.forceShow = forceShow;
  }
 
  public Integer getZombie() {
    return zombie;
  }
 
  public void setZombie(Integer zombie) {
    this.zombie = zombie;
  }
 
  public int getUpdateAuditPendingCount() {
    return updateAuditPendingCount;
  }
 
  public void setUpdateAuditPendingCount(int updateAuditPendingCount) {
    this.updateAuditPendingCount = updateAuditPendingCount;
  }

  public Integer getLockBasicInfo() {
    return lockBasicInfo;
  }

  public void setLockBasicInfo(Integer lockBasicInfo) {
    this.lockBasicInfo = lockBasicInfo;
  }

  public Integer getLockPic() {
    return lockPic;
  }

  public void setLockPic(Integer lockPic) {
    this.lockPic = lockPic;
  }

  public Integer getLockMap() {
    return lockMap;
  }

  public void setLockMap(Integer lockMap) {
    this.lockMap = lockMap;
  }

public int getPicApprove() {
	return picApprove;
}

public void setPicApprove(int picApprove) {
	this.picApprove = picApprove;
}

public String getAliasName() {
	return aliasName;
}

public void setAliasName(String aliasName) {
	this.aliasName = aliasName;
}

public double getAnnualPriceInc() {
  return annualPriceInc;
}

public void setAnnualPriceInc(double annualInc) {
  this.annualPriceInc = annualInc;
}

public double getAvgPrice() {
  return avgPrice;
}

public void setAvgPrice(double avgPrice) {
  this.avgPrice = avgPrice;
}

public double getAnnualTurnover() {
  return annualTurnover;
}

public void setAnnualTurnover(double annualTurnover) {
  this.annualTurnover = annualTurnover;
}

public double getAnnualRentRevenue() {
  return annualRentRevenue;
}

public void setAnnualRentRevenue(double annualRentRevenue) {
  this.annualRentRevenue = annualRentRevenue;
}

public int getYear() {
  return year;
}

public void setYear(int year) {
  this.year = year;
}

public int getMonth() {
  return month;
}

public void setMonth(int month) {
  this.month = month;
}

public int getOnSaleCount() {
  return onSaleCount;
}

public void setOnSaleCount(int onSaleCount) {
  this.onSaleCount = onSaleCount;
}

public int getOnRentCount() {
  return onRentCount;
}

public void setOnRentCount(int onRentCount) {
  this.onRentCount = onRentCount;
}


}