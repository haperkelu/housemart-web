package org.housemart.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.dao.entities.HouseAuditHistoryEntity;
import org.housemart.dao.entities.HouseContactEntity;
import org.housemart.dao.entities.HouseEntity;
import org.housemart.dao.entities.HouseExtEntity;
import org.housemart.dao.entities.HouseRentEntity;
import org.housemart.dao.entities.HouseSaleEntity;
import org.housemart.dao.entities.ResidenceAuditHistoryEntity;
import org.housemart.dao.entities.ResidenceEntity;
import org.housemart.dao.entities.ResidenceMonthDataEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.rpc.stubs.AuditServiceStub;
import org.housemart.service.enums.AuditTypeEnum;
import org.housemart.util.JsonUtils;
import org.housemart.util.ResidenceHelper;
import org.housemart.web.beans.AuditResidenceStatusAndContentBean;
import org.housemart.web.beans.AuditStatusAndContentBean;
import org.housemart.web.context.HouseMartContext;
import org.housemart.web.context.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AuditService implements AuditServiceStub {
 
  protected static final Logger logger = LoggerFactory
      .getLogger(AuditService.class);
 
  @SuppressWarnings("rawtypes")
  private GenericDao houseAuditHistoryDao = SpringContextHolder.getBean("houseAuditHistoryDao");
  @SuppressWarnings("rawtypes")
  private GenericDao houseDao = SpringContextHolder.getBean("houseDao");
 
  @SuppressWarnings("rawtypes")
  private GenericDao houseSaleDao = SpringContextHolder.getBean("houseSaleDao");
  @SuppressWarnings("rawtypes")
  private GenericDao houseRentDao = SpringContextHolder.getBean("houseRentDao");
  @SuppressWarnings("rawtypes")
  private GenericDao houseContactDao = SpringContextHolder
      .getBean("houseContactDao");
  private HouseService houseService = SpringContextHolder
      .getBean("houseService");
 
  @SuppressWarnings("rawtypes")
  private GenericDao residenceDao = SpringContextHolder.getBean("residenceDao");
  @SuppressWarnings("rawtypes")
  private GenericDao residenceAuditHistoryDao = SpringContextHolder
      .getBean("residenceAuditHistoryDao");
 
  @SuppressWarnings("rawtypes")
  @Autowired
  private GenericDao residenceMonthDataDao = SpringContextHolder
      .getBean("residenceMonthDataDao");
 
  private static final ObjectMapper mapper = new ObjectMapper();
  private final ResidenceHelper rHelper = new ResidenceHelper();
 
  @SuppressWarnings("rawtypes")
  public void setHouseAuditHistoryDao(GenericDao houseAuditHistoryDao) {
    this.houseAuditHistoryDao = houseAuditHistoryDao;
  }
 
  /**
   *
   * @param houseId
   */
  public int requestAddNewHouse(int houseId) {
   
    int id = -1;
    try {
      HouseAuditHistoryEntity auditHistory = new HouseAuditHistoryEntity();
      auditHistory.setHouseId(houseId);
      auditHistory.setCommitterId(HouseMartContext.getCurrentUserId());
      auditHistory.setType(AuditTypeEnum.LoggingAudit.getValue());
      auditHistory.setAddTime(Calendar.getInstance().getTime());
      auditHistory.setUpdateTime(Calendar.getInstance().getTime());
      auditHistory.setManagerId(HouseMartContext.getMangerId()); // to do
      auditHistory.setPreContent(null);
      auditHistory.setResult(0);
     
      HouseEntity entity = (HouseEntity) houseDao.load("loadHouse", houseId);
      HouseExtEntity entityExt = (HouseExtEntity) houseDao.load("loadHouseExt",
          houseId);
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("plateName", entityExt.getRegionName());
      map.put("residenceName", entityExt.getResidenceName());
      map.put("roomNo",
          entityExt.getBuildingCodeBegin() + entityExt.getBuildingSuffix()
              + entityExt.getCellFloorBegin() + entityExt.getCellCodeBegin());
      map.put("contactInfo",
          entityExt.getContactName() + "/" + entityExt.getContactMobile() + "/"
              + entity.getPropertyArea());
      auditHistory.setPostContent(mapper.writeValueAsString(map));
      id = houseAuditHistoryDao.add("addHouseAuditHistory", auditHistory);
    } catch (Exception e) {
      logger.error("[requestAddNewHouse]audit error houseID:" + houseId, e);
    }
    return id;
  }
 
  public void requestHouseCommitterChange(int houseId, String contactName,
      String contactMobile, String housePropertyArea) {
   
    try {
     
      HouseAuditHistoryEntity audit = new HouseAuditHistoryEntity();
      audit.setHouseId(houseId);
      audit.setType(AuditTypeEnum.CommitterUpdateAudit.getValue());
      audit.setUpdateTime(Calendar.getInstance().getTime());
      audit.setAddTime(Calendar.getInstance().getTime());
      audit.setCommitterId(HouseMartContext.getCurrentUserId());
      audit.setManagerId(HouseMartContext.getMangerId());
      audit.setResult(0);
     
      HouseExtEntity entityExt = (HouseExtEntity) houseDao.load("loadHouseExt",
          houseId);
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("plateName", entityExt.getRegionName());
      map.put("residenceName", entityExt.getResidenceName());
      map.put("roomNo",
          entityExt.getBuildingCodeBegin() + entityExt.getBuildingSuffix()
              + entityExt.getCellFloorBegin() + entityExt.getCellCodeBegin());
      map.put("contactInfo",
          entityExt.getContactName() + "/" + entityExt.getContactMobile() + "/"
              + entityExt.getPropertyArea());
      audit.setPreContent(mapper.writeValueAsString(map));
     
      Map<String,Object> map2 = new HashMap<String,Object>();
      map2.put("contactInfo", contactName + "/" + contactMobile + "/"
          + housePropertyArea);
      audit.setPostContent(mapper.writeValueAsString(map2));
     
      houseAuditHistoryDao.add("addHouseAuditHistory", audit);
    } catch (Exception e) {
      logger.error("[requestHouseCommitterChange]audit error houseID:"
          + houseId, e);
    }
   
  }
 
  public void requestHouseInvalid(int houseId) {
   
    try {
      HouseAuditHistoryEntity audit = new HouseAuditHistoryEntity();
      audit.setHouseId(houseId);
      audit.setType(AuditTypeEnum.InvalidLoggingAudit.getValue());
      audit.setUpdateTime(Calendar.getInstance().getTime());
      audit.setAddTime(Calendar.getInstance().getTime());
      audit.setCommitterId(HouseMartContext.getCurrentUserId());
      audit.setManagerId(HouseMartContext.getMangerId());
      audit.setResult(0);
     
      HouseExtEntity entityExt = (HouseExtEntity) houseDao.load("loadHouseExt",
          houseId);
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("plateName", entityExt.getRegionName());
      map.put("residenceName", entityExt.getResidenceName());
      map.put("roomNo",
          entityExt.getBuildingCodeBegin() + entityExt.getBuildingSuffix()
              + entityExt.getCellFloorBegin() + entityExt.getCellCodeBegin());
      audit.setPreContent(mapper.writeValueAsString(map));
     
      houseAuditHistoryDao.add("addHouseAuditHistory", audit);
    } catch (Exception e) {
      logger.error("[requestHouseInvalid]audit error houseID:" + houseId, e);
    }
   
  }
 
  public void offboardHouses(String[] houseIds, String comments) {
   
    try {
     
      for (int i = 0; i < houseIds.length; i++) {
        int houseId = Integer.parseInt(houseIds[i]);
       
        HouseAuditHistoryEntity audit = new HouseAuditHistoryEntity();
        audit.setHouseId(houseId);
        audit.setType(AuditTypeEnum.OffboardAudit.getValue());
        audit.setUpdateTime(Calendar.getInstance().getTime());
        audit.setAddTime(Calendar.getInstance().getTime());
        audit.setCommitterId(HouseMartContext.getCurrentUserId());
        audit.setManagerId(HouseMartContext.getMangerId());
        audit.setResult(1);
        audit.setComments(comments);
        houseAuditHistoryDao.add("addHouseAuditHistory", audit);
      }
    } catch (Exception e) {
      logger.error(
          "[offboardHouses]audit error houseID:"
              + StringUtils.join(houseIds, ", "), e);
    }
   
  }
 
  /**
   *
   * @param houseId
   */
  public int requestHouseStatusAndContentUpdate(int houseId,
      Map<String,Object[]> map) {
   
    int id = -1;
    try {
      HouseAuditHistoryEntity audit = new HouseAuditHistoryEntity();
      audit.setHouseId(houseId);
      audit.setType(AuditTypeEnum.ContentUpdateAudit.getValue());
      audit.setUpdateTime(Calendar.getInstance().getTime());
      audit.setAddTime(Calendar.getInstance().getTime());
      audit.setCommitterId(HouseMartContext.getCurrentUserId());
      audit.setManagerId(HouseMartContext.getMangerId());
      audit.setResult(0);
     
      Map<String,Object> oldMap = new HashMap<String,Object>();
      Map<String,Object> newMap = new HashMap<String,Object>();
     
      HouseExtEntity entityExt = (HouseExtEntity) houseDao.load("loadHouseExt",
          houseId);
     
      oldMap.put("plateName", entityExt.getRegionName());
      oldMap.put("residenceName", entityExt.getResidenceName());
      oldMap.put("roomNo",
          entityExt.getBuildingCodeBegin() + entityExt.getBuildingSuffix()
              + entityExt.getCellFloorBegin() + entityExt.getCellCodeBegin());
     
      @SuppressWarnings("rawtypes")
      Iterator iterator = map.keySet().iterator();
      while (iterator.hasNext()) {
        String key = iterator.next().toString();
        Object[] item = (Object[]) map.get(key);
        oldMap.put(key, item[0]);
        newMap.put(key, item[1]);
      }
      audit.setPreContent(mapper.writeValueAsString(oldMap));
      audit.setPostContent(mapper.writeValueAsString(newMap));
     
      id = houseAuditHistoryDao.add("addHouseAuditHistory", audit);
    } catch (Exception e) {
      logger.error("[requestHouseStatusAndContentUpdate]audit error houseID:"
          + houseId, e);
    }
   
    return id;
   
  }
 
  public void approveNewHouse(int houseId, int id) {
   
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("id", id);
    map.put("result", 1);
    houseAuditHistoryDao.update("updateAuditHistoryResultStatus", map);
   
    HouseEntity entity = new HouseEntity();
    entity.setId(houseId);
    entity.setStatus(1);
    entity.setUpdateTime(new Date());
    houseDao.update("updateHouse", entity);
   
  }
 
  public void approveNewHouses(String[] houseIds, String[] ids) {
   
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("ids", StringUtils.join(ids, ","));
    map.put("result", 1);
    houseAuditHistoryDao.update("updateAuditHistoryResultStatusByIds", map);
   
    if (houseIds != null) {
      for (String hId : houseIds) {
        HouseEntity entity = new HouseEntity();
        entity.setId(Integer.valueOf(hId));
        entity.setStatus(1);
        entity.setUpdateTime(new Date());
        houseDao.update("updateHouse", entity);
      }
    }
   
  }
 
  public void approveStatusAndContent(int houseId, int id) {
   
    try {
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("id", id);
      map.put("result", 1);
      houseAuditHistoryDao.update("updateAuditHistoryResultStatus", map);
     
      HouseAuditHistoryEntity entity = (HouseAuditHistoryEntity) houseAuditHistoryDao
          .load("loadHouseAuditHistory", id);
      AuditStatusAndContentBean newBean = JsonUtils.readValue(
          entity.getPostContent(), AuditStatusAndContentBean.class);
     
      if (newBean.getSaleStatus() != 0) {
        HouseSaleEntity sale = houseService.loadSaleHouse(houseId);
        sale.setSaleStatus(newBean.getSaleStatus());
        houseSaleDao.update("updateHouseSaleByHouseId", sale);
      }
     
      if (newBean.getRentStatus() != 0) {
        HouseRentEntity rent = houseService.loadRentHouse(houseId);
        rent.setRentStatus(newBean.getRentStatus());
        houseRentDao.update("updateHouseRentByHouseId", rent);
      }
     
      if (newBean.getArea() != 0) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setId(houseId);
        houseEntity.setUpdateTime(Calendar.getInstance().getTime());
        houseEntity.setPropertyArea(newBean.getArea());
        houseDao.update("updateHouse", houseEntity);
      }
     
      if (StringUtils.isNotBlank(newBean.getContactName())) {
        HouseContactEntity contact = houseService.loadHouseContact(houseId);
        contact.setName(newBean.getContactName());
        houseContactDao.update("updateHouseContactByHouseId", contact);
      }
     
      if (StringUtils.isNotBlank(newBean.getContactMobile())) {
        HouseContactEntity contact = houseService.loadHouseContact(houseId);
        contact.setMobile(newBean.getContactMobile());
        houseContactDao.update("updateHouseContactByHouseId", contact);
      }
     
      if (newBean.getRoomType() != 0) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setId(houseId);
        houseEntity.setUpdateTime(Calendar.getInstance().getTime());
        houseEntity.setRoomType(newBean.getRoomType());
        houseDao.update("updateHouse", houseEntity);
      }
     
      if (newBean.getBuildTime() != null) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setId(houseId);
        houseEntity.setUpdateTime(Calendar.getInstance().getTime());
        houseEntity.setBuildTime(newBean.getBuildTime());
        houseDao.update("updateHouse", houseEntity);
      }
     
      if (StringUtils.isNotBlank(newBean.getHouseType())) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setId(houseId);
        houseEntity.setUpdateTime(Calendar.getInstance().getTime());
        houseEntity.setHouseType(newBean.getHouseType());
        houseDao.update("updateHouse", houseEntity);
      }
     
      if (newBean.getOccupiedArea() != 0) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setId(houseId);
        houseEntity.setUpdateTime(Calendar.getInstance().getTime());
        houseEntity.setOccupiedArea(newBean.getOccupiedArea());
        houseDao.update("updateHouse", houseEntity);
      }
     
      if (newBean.getDecorating() != 0) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setId(houseId);
        houseEntity.setUpdateTime(Calendar.getInstance().getTime());
        houseEntity.setDecorating(newBean.getDecorating());
        houseDao.update("updateHouse", houseEntity);
      }
     
      if (newBean.getSalePrice() != 0) {
        HouseSaleEntity sale = houseService.loadSaleHouse(houseId);
        sale.setPrice(newBean.getSalePrice());
        houseSaleDao.update("updateHouseSaleByHouseId", sale);
      }
     
      if (newBean.getSaleBasePrice() != 0) {
        HouseSaleEntity sale = houseService.loadSaleHouse(houseId);
        sale.setBasePrice(newBean.getSaleBasePrice());
        houseSaleDao.update("updateHouseSaleByHouseId", sale);
      }
     
      if (StringUtils.isNotBlank(newBean.getDealType())) {
        HouseSaleEntity sale = houseService.loadSaleHouse(houseId);
        sale.setDealType(newBean.getDealType());
        houseSaleDao.update("updateHouseSaleByHouseId", sale);
      }
     
      if (newBean.getSaleRec() > 0) {
        HouseSaleEntity sale = houseService.loadSaleHouse(houseId);
        sale.setSaleRec(newBean.getSaleRec());
        houseSaleDao.update("updateHouseSaleByHouseId", sale);
      }
     
      if (StringUtils.isNotBlank(newBean.getSaleTagList())) {
        HouseSaleEntity sale = houseService.loadSaleHouse(houseId);
        sale.setTagList(newBean.getSaleTagList());
        houseSaleDao.update("updateHouseSaleByHouseId", sale);
      }
     
      if (StringUtils.isNotBlank(newBean.getSaleMemo())) {
        HouseSaleEntity sale = houseService.loadSaleHouse(houseId);
        sale.setMemo(newBean.getSaleMemo());
        houseSaleDao.update("updateHouseSaleByHouseId", sale);
      }
     
      if (newBean.getRentPrice() != 0) {
        HouseRentEntity rent = houseService.loadRentHouse(houseId);
        rent.setPrice(newBean.getRentPrice());
        houseRentDao.update("updateHouseRentByHouseId", rent);
      }
     
      if (newBean.getRentBasePrice() != 0) {
        HouseRentEntity rent = houseService.loadRentHouse(houseId);
        rent.setBasePrice(newBean.getRentBasePrice());
        houseRentDao.update("updateHouseRentByHouseId", rent);
      }
     
      if (newBean.getRentType() != 0) {
        HouseRentEntity rent = houseService.loadRentHouse(houseId);
        rent.setRentType(newBean.getRentType());
        houseRentDao.update("updateHouseRentByHouseId", rent);
      }
     
      if (newBean.getRentRec() != 0) {
        HouseRentEntity rent = houseService.loadRentHouse(houseId);
        rent.setRentRec(newBean.getRentRec());
        houseRentDao.update("updateHouseRentByHouseId", rent);
      }
     
      if (StringUtils.isNotBlank(newBean.getRentTagList())) {
        HouseRentEntity rent = houseService.loadRentHouse(houseId);
        rent.setTagList(newBean.getRentTagList());
        houseRentDao.update("updateHouseRentByHouseId", rent);
      }
     
      if (StringUtils.isNotBlank(newBean.getRentMemo())) {
        HouseRentEntity rent = houseService.loadRentHouse(houseId);
        rent.setMemo(newBean.getRentMemo());
        houseRentDao.update("updateHouseRentByHouseId", rent);
      }
     
      if (newBean.getViewHouseType() != 0) {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setId(houseId);
        houseEntity.setUpdateTime(Calendar.getInstance().getTime());
        houseEntity.setViewHouseType(newBean.getViewHouseType());
        houseDao.update("updateHouse", houseEntity);
      }
     
      HouseEntity houseEntity = new HouseEntity();
      houseEntity.setId(houseId);
      houseEntity.setUpdateTime(Calendar.getInstance().getTime());
      houseDao.update("updateHouse", houseEntity);
    } catch (Exception e) {
      logger.error("[approveStatusAndContent]audit new house json parse error",
          e);
    }
   
  }
 
  public static class HouseContactInfoAuditMessage {
    boolean hasPower;
    int ownNumber;
    int leftNumber;
  }
 
  public HouseContactInfoAuditMessage requestHouseContact() {
    return null;
  }
 
  public int requestAddNewResidence(int residenceId) {
   
    int id = -1;
    try {
      ResidenceAuditHistoryEntity auditHistory = new ResidenceAuditHistoryEntity();
      auditHistory.setResidenceId(residenceId);
      auditHistory.setCommitterId(HouseMartContext.getCurrentUserId());
      auditHistory.setType(AuditTypeEnum.LoggingAudit.getValue());
      auditHistory.setAddTime(Calendar.getInstance().getTime());
      auditHistory.setUpdateTime(Calendar.getInstance().getTime());
      auditHistory.setManagerId(HouseMartContext.getMangerId());
      auditHistory.setPreContent(null);
      auditHistory.setResult(0);
     
      ResidenceEntity entity = (ResidenceEntity) residenceDao.load(
          "loadResidenceById", residenceId);
      auditHistory.setPostContent(mapper.writeValueAsString(rHelper
          .residence2Map(entity)));
      id = residenceAuditHistoryDao.add("addResidenceAuditHistory",
          auditHistory);
    } catch (Exception e) {
      logger.error("[requestAddNewResidence]audit error residenceID:"
          + residenceId, e);
    }
   
    return id;
   
  }
 
  public void approveNewResidence(int id) {
   
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("id", id);
    map.put("result", 1);
    residenceAuditHistoryDao.update("updateAuditHistoryResultStatus", map);
   
    ResidenceAuditHistoryEntity entity = (ResidenceAuditHistoryEntity) residenceAuditHistoryDao
        .load("loadResidenceAuditHistory", id);
    if (entity != null) {
      int rId = entity.getResidenceId();
      Map<String,Object> param = new HashMap<String,Object>();
      param.put("id", rId);
      param.put("status", 1);
      residenceDao.update("updateStatus", param);
    }
  }
 
  /**
   *
   * @param residenceId
   */
  public int requestResidenceStatusAndContentUpdate(int residenceId,
      Map<String,Object[]> map) {
   
    int id = -1;
    try {
      ResidenceAuditHistoryEntity audit = new ResidenceAuditHistoryEntity();
      audit.setResidenceId(residenceId);
      audit.setType(AuditTypeEnum.ContentUpdateAudit.getValue());
      audit.setUpdateTime(Calendar.getInstance().getTime());
      audit.setAddTime(Calendar.getInstance().getTime());
      audit.setCommitterId(HouseMartContext.getCurrentUserId());
      audit.setManagerId(HouseMartContext.getMangerId());
      audit.setResult(0);
     
      Map<String,Object> oldMap = new HashMap<String,Object>();
      Map<String,Object> newMap = new HashMap<String,Object>();
     
      @SuppressWarnings("rawtypes")
      Iterator iterator = map.keySet().iterator();
      while (iterator.hasNext()) {
        String key = iterator.next().toString();
        Object[] item = (Object[]) map.get(key);
        oldMap.put(key, item[0]);
        newMap.put(key, item[1]);
      }
      audit.setPreContent(mapper.writeValueAsString(oldMap));
      audit.setPostContent(mapper.writeValueAsString(newMap));
     
      id = residenceAuditHistoryDao.add("addResidenceAuditHistory", audit);
    } catch (Exception e) {
      logger.error(
          "[requestResidenceStatusAndContentUpdate]audit error residenceID:"
              + residenceId, e);
    }
   
    return id;
   
  }
 
  public void approveResidenceStatusAndContent(int residenceId, int id) {
   
    try {
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("id", id);
      map.put("result", 1);
      residenceAuditHistoryDao.update("updateAuditHistoryResultStatus", map);
     
      ResidenceAuditHistoryEntity entity = (ResidenceAuditHistoryEntity) residenceAuditHistoryDao
          .load("loadResidenceAuditHistory", id);
      AuditResidenceStatusAndContentBean newBean = JsonUtils.readValue(
          entity.getPostContent(), AuditResidenceStatusAndContentBean.class);

      AuditResidenceStatusAndContentBean oldBean = JsonUtils.readValue(
          entity.getPreContent(), AuditResidenceStatusAndContentBean.class);
     
      ResidenceEntity rEntity = (ResidenceEntity) residenceDao.load(
          "loadResidenceById", residenceId);
     
      if (newBean.getResidenceName() != null) {
          rEntity.setResidenceName(newBean.getResidenceName());
      }
       
      if (newBean.getAliasName() != null) {
          rEntity.setAliasName(newBean.getAliasName());
      }
       
      if (newBean.getRegionName() != null) {
          rEntity.setRegionName(newBean.getRegionName());
      }
       
      if (newBean.getRegionId() != null) {
          rEntity.setRegionId(newBean.getRegionId());
      }
       
      if (newBean.getAddress() != null) {
        rEntity.setAddress(newBean.getAddress());
      }
     
      if (newBean.getDeveloper() != null) {
        rEntity.setDeveloper(newBean.getDeveloper());
      }
     
      if (newBean.getFinishedTime() != null) {
        rEntity.setFinishedTime(newBean.getFinishedTime());
      }
     
      if (newBean.getGreenRate() != null) {
        rEntity.setGreenRate(newBean.getGreenRate());
      }
     
      if (newBean.getHeadCount() != null) {
        rEntity.setHeadCount(newBean.getHeadCount());
      }
     
      if (newBean.getParking() != null) {
        rEntity.setParking(newBean.getParking());
      }
     
      if (newBean.getPropertyFee() != null) {
        rEntity.setPropertyFee(newBean.getPropertyFee());
      }
     
      if (newBean.getPropertyType() != null) {
        rEntity.setPropertyType(newBean.getPropertyType());
      }
     
      if (newBean.getVolumeRate() != null) {
        rEntity.setVolumeRate(newBean.getVolumeRate());
      }
     
      if (newBean.getForceShow() != null) {
        rEntity.setForceShow(newBean.getForceShow());
      }
     
      if (newBean.getZombie() != null) {
        rEntity.setZombie(newBean.getZombie());
      }
     
      rEntity.setId(residenceId);
      residenceDao.update("updateResidence", rEntity);
     
      Map<String,Object> param = new HashMap<String,Object>();
      param.put("residenceId", residenceId);
      List<ResidenceMonthDataEntity> monthDatas = residenceMonthDataDao.select(
          "findByResidence", param);
      ResidenceMonthDataEntity monthData = null;
      if (monthDatas != null && monthDatas.size() > 0) {
        monthData = monthDatas.get(0);
      }
     
      if (monthData == null) {
        Calendar c = Calendar.getInstance();
        monthData = new ResidenceMonthDataEntity();
        monthData.setResidenceId(residenceId);
        monthData.setResidenceName(newBean.getResidenceName());
        monthData.setAnnualPriceIncrement(newBean.getAnnualPriceIncrement());
        monthData.setAnnualTurnoverPercent(newBean.getAnnualTurnoverPercent());
        monthData.setRentRevenue(newBean.getRentRevenue());
        monthData.setMonth(c.get(Calendar.MONTH) + 1);
        monthData.setYear(c.get(Calendar.YEAR));
        monthData.setAddTime(new Date());
        monthData.setUpdateTime(new Date());
        residenceMonthDataDao.add("addResidenceMonthData", monthData);
      } else {
        if (newBean.getAnnualPriceIncrement() != null) {
          monthData.setAnnualPriceIncrement(newBean.getAnnualPriceIncrement());
        }
        if (newBean.getAnnualTurnoverPercent() != null) {
          monthData
              .setAnnualTurnoverPercent(newBean.getAnnualTurnoverPercent());
        }
        if (newBean.getRentRevenue() != null) {
          monthData.setRentRevenue(newBean.getRentRevenue());
        }
        monthData.setUpdateTime(new Date());
        residenceMonthDataDao.update("updateResidenceMonthData", monthData);
      }
     
    } catch (Exception e) {
      logger.error(
          "[approveStatusAndContent]audit new residence json parse error", e);
    }
   
  }
}