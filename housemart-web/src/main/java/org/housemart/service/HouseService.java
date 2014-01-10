package org.housemart.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.housemart.dao.entities.AccountEntity;
import org.housemart.dao.entities.AccountExtEntity;
import org.housemart.dao.entities.HouseContactEntity;
import org.housemart.dao.entities.HouseEntity;
import org.housemart.dao.entities.HouseExtAgileEntity;
import org.housemart.dao.entities.HouseExtEntity;
import org.housemart.dao.entities.HouseInteractionEntity;
import org.housemart.dao.entities.HouseInteractionExtEntity;
import org.housemart.dao.entities.HouseInteractionTransferExtEntity;
import org.housemart.dao.entities.HouseRentEntity;
import org.housemart.dao.entities.HouseSaleEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.message.HouseInteractionNotifier;
import org.housemart.web.context.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HouseService {
  
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @SuppressWarnings("rawtypes")
  private GenericDao houseDao = SpringContextHolder.getBean("houseDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao houseInteractionDao = SpringContextHolder
      .getBean("houseInteractionDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao houseInteractionNoticeDao = SpringContextHolder
      .getBean("houseInteractionNoticeDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao houseInteractionTransferDao = SpringContextHolder
      .getBean("houseInteractionTransferDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao houseSaleDao = SpringContextHolder.getBean("houseSaleDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao houseRentDao = SpringContextHolder.getBean("houseRentDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao accountResidence = SpringContextHolder
      .getBean("accountResidenceDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao accountDao = SpringContextHolder.getBean("accountDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao houseAuditHistoryDao = SpringContextHolder
      .getBean("houseAuditHistoryDao");
  
  public HouseSaleEntity loadSaleHouse(int houseId) {
    
    HouseExtEntity entityExt = (HouseExtEntity) houseDao.load("loadHouseExt",
        houseId);
    return entityExt.generateHouseSaleEntity();
    
  }
  
  public HouseRentEntity loadRentHouse(int houseId) {
    
    HouseExtEntity entityExt = (HouseExtEntity) houseDao.load("loadHouseExt",
        houseId);
    return entityExt.generateHouseRentEntity();
    
  }
  
  public HouseContactEntity loadHouseContact(int houseId) {
    
    HouseExtEntity entityExt = (HouseExtEntity) houseDao.load("loadHouseExt",
        houseId);
    return entityExt.generateHouseContactEntity();
    
  }
  
  public void setInteraction(Integer houseID, Integer agentID, Integer status) {
    
    HouseInteractionEntity interaction = new HouseInteractionEntity();
    HouseEntity house = (HouseEntity) houseDao.load("loadHouse", houseID);
    
    interaction.setHouseID(houseID);
    interaction.setAgentID(agentID);
    interaction.setStatus(status);
    interaction.setAddTime(new Date());
    interaction.setUpdateTime(new Date());
    Date startDate = new Date();
    interaction.setStartDate(startDate);
    Date endDate = new Date(startDate.getTime()
        + (status == 2 ? HouseInteractionEntity.MONO_VALID_TIME
            : HouseInteractionEntity.NORMAL_VALID_TIME));
    
    interaction.setEndDate(endDate);
    
    houseInteractionDao.add("addHouseInteraction", interaction);
  }
  
  @SuppressWarnings("unchecked")
  public HouseInteractionExtEntity getLastInteraction(Integer houseID) {
    
    List<HouseInteractionExtEntity> list = null;
    
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("houseID", houseID);
    map.put("limit", true);
    map.put("limitStart", 0);
    map.put("limitSize", 1);
    list = houseInteractionDao.select("findHouseInteractionList", map);
    
    HouseInteractionExtEntity interaction = null;
    
    if (!list.isEmpty()) {
      interaction = list.get(0);
    }
    return interaction;
  }
  
  @SuppressWarnings("unchecked")
  public HouseInteractionExtEntity getInteraction(Integer houseID) {
    
    List<HouseInteractionExtEntity> list = null;
    
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("houseID", houseID);
    map.put("currentTime", new Date());
    map.put("limit", true);
    map.put("limitStart", 0);
    map.put("limitSize", 1);
    list = houseInteractionDao.select("findHouseInteractionList", map);
    
    HouseInteractionExtEntity interaction = null;
    
    if (!list.isEmpty()) {
      interaction = list.get(0);
    }
    return interaction;
  }
  
  public void checkInteractions() {
    this.revokeInvalidInteractions();
    this.checkSaleInteractions();
    this.checkRentInteractions();
  }
  
  @SuppressWarnings("unchecked")
  private void checkSaleInteractions() {
    // house sale
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("saleStatus", 1);
    List<HouseSaleEntity> housesOnSale = houseSaleDao.select(
        "findHouseSaleList", map);
    
    if (!housesOnSale.isEmpty()) {
      List<Integer> houseIds = new ArrayList<Integer>();
      for (HouseSaleEntity houseSale : housesOnSale) {
        HouseEntity house = (HouseEntity) houseDao.load("loadHouse",
            houseSale.getHouseId());
        if (house == null) {
          continue;
        }
        
        map = new HashMap<Object,Object>();
        map.put("residenceID", house.getResidenceId());
        map.put("accountID",
            houseSale.getOwner() != null ? houseSale.getOwner() : 0);
        List<AccountExtEntity> accounts = accountResidence.select(
            "findResidenceAccountList", map);
        
        AccountExtEntity validOwner = null;
        
        if (!accounts.isEmpty()
            && !accounts.get(0).getPositionType().equals("初级经纪人")) {
          validOwner = accounts.get(0);
        }
        
        HouseInteractionExtEntity interaction = this
            .getLastInteraction(houseSale.getHouseId());
        
        Integer interactionStatus = 0;
        Integer agentID = 0;
        Integer lastAgentID = 0;
        if (interaction == null) {
          // 没分配过交互权
          if (validOwner == null) {
            // 激活人不属于房源小区 或 激活人为初级经纪人，分配其他经纪普通交互权
            interactionStatus = 1;
          } else {
            // 激活人属于房源小区，分配独占交互权
            interactionStatus = 2;
            agentID = houseSale.getOwner();
          }
          
          if (house.getSourceType().equals(
              HouseEntity.SourceTypeEnum.external.value)) {
            // 外部房源
            agentID = checkExternalHouseOwner(house.getId(),
                house.getResidenceId(), house.getCreator());
          }
        } else if (new Date().compareTo(interaction.getEndDate()) > 0) {
          // 交互权已过期，分配普通交互权
          interactionStatus = 1;
          if (validOwner != null
              && interaction.getAgentID() != validOwner.getId()) {
            agentID = houseSale.getOwner();
          } else {
            lastAgentID = interaction.getAgentID();
          }
          
          if (house.getSourceType().equals(
              HouseEntity.SourceTypeEnum.external.value)) {
            // 外部房源
            agentID = checkExternalHouseOwner(house.getId(),
                house.getResidenceId(), house.getCreator());
          }
        }
        
        if (interactionStatus == 1
            && agentID == 0
            && !house.getSourceType().equals(
                HouseEntity.SourceTypeEnum.external.value)) {
          agentID = this.assignHouseInteration(house.getId(),
              house.getResidenceId(), lastAgentID, house.getSourceType());
        }
        
        if (interactionStatus > 0) {
          this.setInteraction(house.getId(), agentID, interactionStatus);
          houseIds.add(house.getId());
        }
      }
      
      if (houseIds.size() > 0) {
        HouseInteractionNotifier notifier = new HouseInteractionNotifier();
        notifier.changeToWithInteraction(houseIds);
      }
    }
  }
  
  @SuppressWarnings("unchecked")
  private void checkRentInteractions() {
    // house sale
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("rentStatus", 1);
    List<HouseRentEntity> housesOnRent = houseRentDao.select(
        "findHouseRentList", map);
    
    if (!housesOnRent.isEmpty()) {
      List<Integer> houseIds = new ArrayList<Integer>();
      for (HouseRentEntity houseRent : housesOnRent) {
        HouseEntity house = (HouseEntity) houseDao.load("loadHouse",
            houseRent.getHouseId());
        if (house == null) {
          continue;
        }
        
        map = new HashMap<Object,Object>();
        map.put("residenceID", house.getResidenceId());
        map.put("accountID",
            houseRent.getOwner() != null ? houseRent.getOwner() : 0);
        List<AccountExtEntity> accounts = accountResidence.select(
            "findResidenceAccountList", map);
        
        AccountExtEntity validOwner = null;
        
        if (!accounts.isEmpty()
            && !accounts.get(0).getPositionType().equals("初级经纪人")) {
          validOwner = accounts.get(0);
        }
        
        HouseInteractionExtEntity interaction = this
            .getLastInteraction(houseRent.getHouseId());
        
        Integer interactionStatus = 0;
        Integer agentID = 0;
        Integer lastAgentID = 0;
        if (interaction == null) {
          // 没分配过交互权
          if (validOwner == null) {
            // 激活人不属于房源小区 或 激活人为初级经纪人，分配其他经纪普通交互权
            interactionStatus = 1;
          } else {
            // 激活人属于房源小区，分配独占交互权
            interactionStatus = 2;
            agentID = houseRent.getOwner();
          }
          
          if (house.getSourceType().equals(
              HouseEntity.SourceTypeEnum.external.value)) {
            // 外部房源
            agentID = checkExternalHouseOwner(house.getId(),
                house.getResidenceId(), house.getCreator());
          }
        } else if (new Date().compareTo(interaction.getEndDate()) > 0) {
          // 交互权已过期，分配普通交互权
          interactionStatus = 1;
          if (validOwner != null
              && interaction.getAgentID() != validOwner.getId()) {
            agentID = houseRent.getOwner();
          } else {
            lastAgentID = interaction.getAgentID();
          }
          
          if (house.getSourceType().equals(
              HouseEntity.SourceTypeEnum.external.value)) {
            // 外部房源
            agentID = checkExternalHouseOwner(house.getId(),
                house.getResidenceId(), house.getCreator());
          }
        }
        
        if (interactionStatus == 1
            && agentID == 0
            && !house.getSourceType().equals(
                HouseEntity.SourceTypeEnum.external.value)) {
          agentID = this.assignHouseInteration(house.getId(),
              house.getResidenceId(), lastAgentID, house.getSourceType());
        }
        
        if (interactionStatus > 0) {
          this.setInteraction(house.getId(), agentID, interactionStatus);
          houseIds.add(house.getId());
        }
      }
      
      if (houseIds.size() > 0) {
        HouseInteractionNotifier notifier = new HouseInteractionNotifier();
        notifier.changeToWithInteraction(houseIds);
      }
    }
  }
  
  private int checkExternalHouseOwner(int houseId, int residenceId, int ownerId) {
    AccountExtEntity account = (AccountExtEntity) this.accountDao.load(
        "loadAccountById", ownerId);
    if (account == null) {
      // 已注销，重新分配
      Integer new_ownerId = this.assignHouseInteration(houseId, residenceId,
          ownerId, HouseEntity.SourceTypeEnum.external.value);
      // 设置新归属权
      HouseEntity house = new HouseEntity();
      house.setId(houseId);
      house.setCreator(new_ownerId);
      house.setLastUpdater(new_ownerId);
      house.setUpdateTime(new Date());
      houseDao.update("updateHouse", house);
      
      this.doInteractionTransfer(houseId, ownerId, new_ownerId, "系统分配");
    }
    
    return ownerId;
  }
  
  @SuppressWarnings("unchecked")
  private Integer assignHouseInteration(Integer houseID, Integer residenceID,
      Integer lastAgentID, Integer sourceType) {
    Integer agentID = 0;
    
    // 查找内部经纪人、区域经理
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("residenceID", residenceID);
    
    List<AccountExtEntity> accounts = accountResidence.select(
        "findResidenceAccountList", map);
    
    List<AccountExtEntity> internalAgents = new ArrayList<AccountExtEntity>();
    List<AccountExtEntity> externalAgents = new ArrayList<AccountExtEntity>();
    List<AccountExtEntity> managers = new ArrayList<AccountExtEntity>();
    
    for (AccountExtEntity account : accounts) {
      if (account.getId() == lastAgentID) {
        agentID = lastAgentID;
      } else if (account.getPositionType().equals("区域经理")) {
        managers.add(account);
      } else if (account.getPositionType().equals("经纪人")) {
        if (account.getType().equals(AccountEntity.TypeEnum.Internal.value)) {
          // 内部经纪人
          internalAgents.add(account);
        }
        if (account.getType().equals(AccountEntity.TypeEnum.External.value)) {
          // 外部经纪人
          externalAgents.add(account);
        }
        if (account.getType().equals(AccountEntity.TypeEnum.Combine.value)) {
          // 内部+外部经纪人
          internalAgents.add(account);
          externalAgents.add(account);
        }
        
      }
    }
    
    double rand = Math.random();
    int index = 0;
    
    if (sourceType.equals(HouseEntity.SourceTypeEnum.external.value)) {
      
    } else {
      if (!internalAgents.isEmpty()) {
        index = (int) Math.floor(internalAgents.size() * rand);
        agentID = internalAgents.get(index).getId();
      }
    }
    
    if (agentID == 0 && !managers.isEmpty()) {
      index = (int) Math.floor(managers.size() * rand);
      agentID = managers.get(index).getId();
    }
    
    return agentID;
  }
  
  @SuppressWarnings("unchecked")
  public HouseInteractionTransferExtEntity getInteractionTransfer(
      Integer houseID, Integer brokerID) {
    
    List<HouseInteractionTransferExtEntity> list = null;
    
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("houseID", houseID);
    map.put("fromBrokerID", brokerID);
    map.put("status", 1);
    list = houseInteractionTransferDao.select(
        "findHouseInteractionTransferList", map);
    
    if (list.isEmpty()) {
      return null;
    } else {
      HouseInteractionTransferExtEntity transfer = list.get(0);
      
      return transfer;
    }
  }
  
  public void doInteractionTransfer(Integer houseID, Integer fromBrokerID,
      Integer toBrokerID, String notes) {
    
    if (fromBrokerID.equals(toBrokerID)) {
      return;
    }
    
    HouseInteractionTransferExtEntity transfer = this.getInteractionTransfer(
        houseID, fromBrokerID);
    
    HouseInteractionTransferExtEntity finalTransfer = this
        .getInteractionTransfer(houseID, toBrokerID);
    
    Integer finalBrokerID = finalTransfer == null ? toBrokerID : finalTransfer
        .getFinalBrokerID();
    
    Map<Object,Object> map = null;
    
    if (transfer != null && !transfer.getToBrokerID().equals(toBrokerID)) {
      map = new HashMap<Object,Object>();
      map.put("id", transfer.getId());
      map.put("status", null);
      map.put("updateTime", new Date());
      this.houseInteractionTransferDao.update("updateHouseInteractionTransfer",
          map);
      transfer.setStatus(null);
    }
    
    if (transfer == null || transfer.getStatus() == null) {
      transfer = new HouseInteractionTransferExtEntity();
      transfer.setHouseID(houseID);
      transfer.setFromBrokerID(fromBrokerID);
      transfer.setToBrokerID(toBrokerID);
      transfer.setFinalBrokerID(finalBrokerID);
      transfer.setStatus(1);
      transfer.setAddTime(new Date());
      transfer.setUpdateTime(new Date());
      transfer.setNotes(notes);
      this.houseInteractionTransferDao.add("addHouseInteractionTransfer",
          transfer);
    }
    
    map = new HashMap<Object,Object>();
    map.put("houseID", houseID);
    map.put("finalBrokerID", fromBrokerID);
    map.put("finalBrokerIDNew", finalBrokerID);
    map.put("updateTime", new Date());
    this.houseInteractionTransferDao.update("updateHouseInteractionTransfer",
        map);
  }
  
  public void revokeInvalidInteractions() {
    Map<Object,Object> map = new HashMap<Object,Object>();
    List<HouseInteractionExtEntity> invalidList = (List<HouseInteractionExtEntity>) houseInteractionDao
        .select("findInvalidHouseInteractionList", map);
    
    for (HouseInteractionExtEntity interaction : invalidList) {
      interaction.setEndDate(new Date());
      houseInteractionDao.update("updateHouseInteraction", interaction);
    }
  }
  
  public void revokeInteraction(HouseInteractionEntity interaction) {
    interaction.setEndDate(new Date());
    houseInteractionDao.update("updateHouseInteraction", interaction);
  }
  
  public void revokeInteraction(Integer houseId) {
    HouseInteractionExtEntity interaction = this.getInteraction(houseId);
    if (interaction != null) {
      this.revokeInteraction(interaction);
      this.checkInteractions();
    }
  }
  
  public void deleteOverDateInteraction() {
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("addTime", DateUtils.addDays(new Date(), -2));
    houseInteractionNoticeDao.deletePhysical("deleteOverDateInteractions",
        param);
  }
  
  // onboard date
  @SuppressWarnings("unchecked")
  public void refreshHouseOnboardTime(AuditService auditService) {
    logger.info("Start Refresh House OnboardTime ... ");
    Date refreshLine = DateUtils.addDays(new Date(), -15);
    Map<Integer,Map<String,Date>> toRefreshIds = new HashMap<Integer,Map<String,Date>>();
    
    // find refresh houses
    int min = houseDao.count("findMinHouseId", null);
    int max = houseDao.count("findMaxHouseId", null) + 1;
    int task = 5000;
    int loop = (max - min) / task;
    int minVal = min;
    int maxVal = max;
    for (int i = 0; i <= loop; i++) {
      logger.info("Find toRefreshHouses " + ((int) min + i * task) + ", total "
          + max);
      minVal = task * i + min;
      if (loop != i) {
        maxVal = minVal + task;
      } else {
        maxVal = max;
      }
      
      Map<String,Object> para = new HashMap<String,Object>();
      para.put("houseIdFrom", minVal);
      para.put("houseIdTo", maxVal);
      List<HouseExtAgileEntity> housesWithInteraction = houseDao.select(
          "findHouseListWithInteraction", para);
      try {
        if (CollectionUtils.isNotEmpty(housesWithInteraction)) {
          for (HouseExtAgileEntity h : housesWithInteraction) {
            try {
              if (h != null && h.getId() != null && h.getAuditTime() != null
                  && h.getOnboardTime() != null
                  && refreshLine.after(h.getOnboardTime())) {
                Map<String,Date> date = new HashMap<String,Date>();
                date.put("onboardTime", h.getOnboardTime());
                date.put("auditTime", h.getAuditTime());
                toRefreshIds.put(h.getId(), date);
              }
            } catch (Exception e) {
              logger.error(e.getMessage(), e);
            }
          }
        }
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
    }
    
    logger.info("Expect " + toRefreshIds.size() + " Houses To Refresh");
    
    List<Integer> houseIds = new ArrayList<Integer>();
    List<Integer> refreshedIds = new ArrayList<Integer>();
    int successCount = 0;
    
    for (Integer hId : toRefreshIds.keySet()) {
      
      try {
        houseIds.clear();
        if (hId == null) {
          continue;
        }
        
        // offboard
        houseIds.add(hId);
        String[] houses = new String[1];
        houses[0] = String.valueOf(hId);
        auditService.offboardHouses(houses, "SystemRefresh");
        
        // onboard
        int auditId = auditService.requestAddNewHouse(hId);
        
        // auto approve
        auditService.approveNewHouse(hId, auditId);
        
        Date addTime = DateUtils.addDays(
            toRefreshIds.get(hId).get("onboardTime"), 15).after(new Date()) ? new Date()
            : DateUtils.addDays(toRefreshIds.get(hId).get("onboardTime"), 15);
        Date updateTime = DateUtils.addDays(
            toRefreshIds.get(hId).get("auditTime"), 15).after(new Date()) ? new Date()
            : DateUtils.addDays(toRefreshIds.get(hId).get("auditTime"), 15);
        
        Map<String,Object> para = new HashMap<String,Object>();
        para.put("id", auditId);
        para.put("addTime", addTime);
        para.put("updateTime", updateTime);
        para.put("comments", "SystemRefresh");
        houseAuditHistoryDao.update("updateAuditHistoryForAutoRefresh", para);
        
        successCount++;
        refreshedIds.add(hId);
        logger.info("Refreshed houseId-" + hId + " auditId-" + auditId);
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
    }
    logger.info("Refresh OnboardTime Finish Expect-" + toRefreshIds.size()
        + " Success-" + successCount);
    logger.info("RefreshedHouseIds :" + refreshedIds.toString());
  }
  
}
