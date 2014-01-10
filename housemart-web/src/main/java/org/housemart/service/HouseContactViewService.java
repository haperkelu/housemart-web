package org.housemart.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.housemart.dao.entities.AccountEntity;
import org.housemart.dao.entities.AccountResidenceEntity;
import org.housemart.dao.entities.HouseContactView;
import org.housemart.dao.entities.HouseEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.dao.generic.UniqueIdObject;
import org.housemart.web.context.HouseMartContext;
import org.housemart.web.context.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HouseContactViewService {
  
  /**
   * logger
   */
  protected static final Logger logger = LoggerFactory
      .getLogger("CommonLogger");
  
  @SuppressWarnings("rawtypes")
  private GenericDao houseContactViewDao = SpringContextHolder
      .getBean("houseContactViewDao");
  
  @SuppressWarnings("rawtypes")
  private static final GenericDao accountDao = SpringContextHolder
      .getBean("accountDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao accountResidenceDao = SpringContextHolder
      .getBean("accountResidenceDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao houseDao = SpringContextHolder.getBean("houseDao");
  
  private static int QUOTA_INTER = 300;
  private static int QUOTA_CROSS = 20;
  
  private static final String ADMIN_NAME = "管理员";
  private static final String MANAGER_NAME = "区域经理";
  private static final String BROKER_NAME = "经纪人";
  
  public boolean isBrokerViewContact(int accountId, int houseId) {
    
    AccountEntity result = (AccountEntity) accountDao.load("loadAccountById",
        accountId);
    
    if (result == null) {
      logger.info("accountId:" + accountId + ";houseId:" + houseId
          + "  don't have account record!");
      return false;
    }
    
    HouseEntity house = (HouseEntity) houseDao.load("loadHouseExt", houseId);
    if (house == null) {
      logger.info("accountId:" + accountId + ";houseId:" + houseId
          + "  don't have house record!");
      return false;
    }
    
    // 管理员超级权限
    if (result.getPositionType().equalsIgnoreCase(ADMIN_NAME)) {
      return true;
    }
    
    // 登盘人次数不限
    if (HouseMartContext.getCurrentUserId() == house.getCreator()) {
      return true;
    }
    
    List<AccountResidenceEntity> residenceList = (List<AccountResidenceEntity>) accountResidenceDao
        .select("findAccountResidenceList", new UniqueIdObject(accountId));
    if (CollectionUtils.isEmpty(residenceList)) {
      logger.info("accountId:" + accountId + ";houseId:" + houseId
          + "  don't have residenceList record!");
      return false;
    }
    
    // 外围经纪人
    if (result.getType() != null && result.getType() == 1) {
      return false;
    }
    
    // 内部经纪人/双重身份经纪人
    if (result.getType() == null || result.getType() == 0 || result.getType() == 2) {
      
      // 内部区域经理
      if (result.getPositionType().equalsIgnoreCase(MANAGER_NAME)) {
        
        if (belongToResidence(house, residenceList)) { // 无限次
          logger.info("accountId:" + accountId + ";houseId:" + houseId
              + "  you got be kidding me!");
          return true;
        } else { // 次数限制
          int type = HouseContactView.TypeEnum.cross.value;
          
          Map<String,Object> map1 = new HashMap<String,Object>();
          map1.put("accountId", accountId);
          map1.put("type", type);
          List<HouseContactView> viewList = houseContactViewDao.select(
              "findAccountViewInfo", map1);
          
          if (CollectionUtils.isEmpty(viewList)) { // 第一次查看
            HouseContactView entity = new HouseContactView();
            entity.setAccountId(accountId);
            entity.setType(type);
            
            entity.setFromDate(DateUtils.truncate(new Date(), Calendar.DATE));
            entity.setEndDate(DateUtils.addDays(
                DateUtils.truncate(new Date(), Calendar.DATE), 1));
            entity.setBase(QUOTA_CROSS);
            
            entity.setViewCount(0);
            entity.setAddTime(new Date());
            entity.setUpdateTime(new Date());
            houseContactViewDao.add("addAccountView", entity);
            if (entity.getId() > 0) {
              incrementView(accountId, type);
              return true;
            }
          } else { // 后续查看
            if (viewList.size() != 1) {
              logger.info("accountId:" + accountId + ";houseId:" + houseId
                  + "  too many contactView records!");
              return false;
            }
            
            HouseContactView contactView = viewList.get(0);
            if (new Date().after(contactView.getEndDate())) {
              resetDateRange(accountId, type);
            }
            
            if (contactView.getViewCount() < contactView.getBase()) {
              incrementView(accountId, type);
              return true;
            } else {
              logger.info("accountId:" + accountId + ";houseId:" + houseId
                  + "  you guys run out of quota!");
              return false;
            }
          }
        }
        
      }
      
      // 内部经济人
      if (result.getPositionType().equalsIgnoreCase(BROKER_NAME)) {
        
        int type;
        int base;
        if (belongToResidence(house, residenceList)) { // 小区内
          type = HouseContactView.TypeEnum.own.value;
          base = QUOTA_INTER;
        } else { // 跨小区
          type = HouseContactView.TypeEnum.cross.value;
          base = QUOTA_CROSS;
        }
        
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("accountId", accountId);
        map1.put("type", type);
        List<HouseContactView> viewList = houseContactViewDao.select(
            "findAccountViewInfo", map1);
        
        if (CollectionUtils.isEmpty(viewList)) { // 第一次查看
          HouseContactView entity = new HouseContactView();
          entity.setAccountId(accountId);
          entity.setType(type);
          
          entity.setFromDate(DateUtils.truncate(new Date(), Calendar.DATE));
          entity.setEndDate(DateUtils.addDays(
              DateUtils.truncate(new Date(), Calendar.DATE), 1));
          entity.setBase(base);
          
          entity.setViewCount(0);
          entity.setAddTime(new Date());
          entity.setUpdateTime(new Date());
          houseContactViewDao.add("addAccountView", entity);
          if (entity.getId() > 0) {
            incrementView(accountId, houseId);
            return true;
          }
        } else {
          if (viewList.size() != 1) {
            logger.info("accountId:" + accountId + ";houseId:" + houseId
                + "  too many contactView records!");
            return false;
          }
          
          HouseContactView contactView = viewList.get(0);
          if (new Date().after(contactView.getEndDate())) {
            resetDateRange(accountId, type);
            viewList = houseContactViewDao.select(
                "findAccountViewInfo", map1);
            contactView = viewList.get(0);
          }
          if (contactView.getViewCount() < contactView.getBase()) {
            incrementView(accountId, type);
            return true;
          } else {
            logger.info("accountId:" + accountId + ";houseId:" + houseId
                + "  you guys run out of quota!");
            return false;
          }
        }
        
      }
      
    }
    
    logger.info("accountId:" + accountId + ";houseId:" + houseId
        + "  the final corner you got!");
    return false;
    
  }
  
  /**
   * 房源是否在本小区之内
   * 
   * @param house
   * @param residenceList
   * @return
   */
  private boolean belongToResidence(HouseEntity house,
      List<AccountResidenceEntity> residenceList) {
    if (house == null || CollectionUtils.isEmpty(residenceList)) {
      
      return false;
      
    }
    for (AccountResidenceEntity item : residenceList) {
      if (item.getResidenceID() != null
          && item.getResidenceID().equals(house.getResidenceId())) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * 
   * @param accountId
   * @param houseId
   */
  private void incrementView(int accountId, int type) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("accountId", accountId);
    map.put("type", type);
    houseContactViewDao.update("incrementViewCount", map);
  }
  
  private void resetDateRange(int accountId, int type) {
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("accountId", accountId);
    map.put("type", type);
    map.put("fromDate", DateUtils.truncate(new Date(), Calendar.DATE));
    map.put("endDate",
        DateUtils.addDays(DateUtils.truncate(new Date(), Calendar.DATE), 1));
    houseContactViewDao.update("updateFromToDate", map);
  }
  
}
