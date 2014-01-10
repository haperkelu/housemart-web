/**
 * Created on 2013-9-12
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.housemart.dao.entities.ResidenceEntity;

public class ResidenceHelper {
  
  public static enum EXCEL_COL {
    ALL("全部"), ID("小区编号"), REGION("小区编号"), PLATE("板块"), NAME("小区名称"), YEAR(
        "数据年份"), MONTH("数据月份"), ANNUAL_PRICE_INCREMENT("年涨幅"), ANNUAL_TURNOVER_PERCENT(
        "年换手百分比"), ANNUAL_TURNOVER_RATE("年换手比"), RENT_REVENUE("年租金回报"), AVERAGE_PRICE(
        "小区均价"), MIN_RENT_PRICE("最低租金"), MAX_RENT_PRICE("最高租金"), HEADCOUNT(
        "小区户数");
    String val;
    
    EXCEL_COL(String val) {
      this.val = val;
    }
    
    public String getVal() {
      return val;
    }
    
    public void setVal(String val) {
      this.val = val;
    }
    
    public static EXCEL_COL getValueOf(String val) {
      
      if (StringUtils.isBlank(val)) {
        return null;
      }
      EXCEL_COL result = null;
      for (EXCEL_COL col : EXCEL_COL.values()) {
        if (col.getVal().equals(val)) {
          result = col;
          break;
        }
      }
      return result;
    }
  }
  
  public Map<String,Object> residence2Map(ResidenceEntity entity) {
    Map<String,Object> map = new HashMap<String,Object>();
    
    map.put("residenceName", entity.getResidenceName());
    map.put("address", entity.getAddress());
    map.put("headCount", entity.getHeadCount());
    map.put("parking", entity.getParking());
    map.put("propertyType", entity.getPropertyType());
    map.put("propertyFee", entity.getPropertyFee());
    
    map.put("greenRate", entity.getGreenRate());
    map.put("volumeRate", entity.getVolumeRate());
    
    map.put("developer", entity.getDeveloper());
    map.put("finishedTime", entity.getFinishedTime());
    map.put("volumeRate", entity.getVolumeRate());
    
    return map;
  }
}
