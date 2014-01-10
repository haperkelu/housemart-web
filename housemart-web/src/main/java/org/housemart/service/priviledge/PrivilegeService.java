package org.housemart.service.priviledge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.dao.entities.HouseEntity;
import org.housemart.dao.entities.HouseExtEntity;
import org.housemart.dao.entities.HouseRentEntity;
import org.housemart.dao.entities.HouseSaleEntity;
import org.housemart.dao.entities.PrivilegeEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.dao.generic.UniqueIdObject;
import org.housemart.service.enums.ActionType;
import org.housemart.service.enums.AuditContentKeys;
import org.housemart.service.enums.GranularityType;
import org.housemart.service.enums.PriviledgeResultType;
import org.housemart.service.enums.ResourceDescription;
import org.housemart.service.enums.ResourceType;
import org.housemart.util.CommonUtils;
import org.housemart.web.context.HouseMartContext;
import org.housemart.web.context.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author pai.li
 * 
 */
public class PrivilegeService {
  
  @SuppressWarnings("rawtypes")
  private GenericDao privilegeDao;
  
  @SuppressWarnings("rawtypes")
  private GenericDao houseDao = SpringContextHolder.getBean("houseDao");
  
  @SuppressWarnings("rawtypes")
  private GenericDao accountResidenceDao = SpringContextHolder
      .getBean("accountResidenceDao");
  
  /**
   * logger
   */
  protected static final Logger logger = LoggerFactory
      .getLogger(PrivilegeService.class);
  
  private static List<String> externalHouseUpdateWhiteList;
  static {
    externalHouseUpdateWhiteList = new ArrayList<String>();
    externalHouseUpdateWhiteList.add(AuditContentKeys.DECORATING);
    externalHouseUpdateWhiteList.add(AuditContentKeys.DIRECTION);
    externalHouseUpdateWhiteList.add(AuditContentKeys.BUILD_TIME);
    
    externalHouseUpdateWhiteList.add(AuditContentKeys.RENT_MEMO);
    externalHouseUpdateWhiteList.add(AuditContentKeys.RENT_TAG_LIST);
    externalHouseUpdateWhiteList.add(AuditContentKeys.RENT_EQUIPMENT_LIST);
    
    externalHouseUpdateWhiteList.add(AuditContentKeys.SALE_MEMO);
    externalHouseUpdateWhiteList.add(AuditContentKeys.SALE_TAG_LIST);
  }
  
  @SuppressWarnings("rawtypes")
  public void setPrivilegeDao(GenericDao privilegeDao) {
    this.privilegeDao = privilegeDao;
  }
  
  /**
   * 是否有权限
   * 
   * @param accountId
   * @param action
   * @param type
   * @param granularityType
   * @param bizId
   * @param fieldsValueMap
   * @return 1:无权限 2:需要审核 3:直接更改
   */
  public int hasPower(int accountId, ActionType action, ResourceType type,
      GranularityType granularityType, int bizId, List<String> fieldsKeyList) {
    
    // 参数验证
    if (accountId < 0 || action == null || type == null
        || granularityType == null) {
      return PriviledgeResultType.NoRight.getValue();
    }
    
    logger.info("accountId:" + accountId);
    // 非空检验
    List<PrivilegeEntity> list = this.getPriviledgeList(accountId);
    if (CollectionUtils.isEmpty(list)) {
      logger.info("no right:" + accountId);
      return PriviledgeResultType.NoRight.getValue();
    }
    
    if (!CollectionUtils.isEmpty(list)) {
      if (list.contains(getSuperPriviledge())) {
        logger.info("super admin:" + accountId);
        return PriviledgeResultType.Direct.getValue();
      }
    }
    logger.info("priviledge count:" + list.size());
    for (PrivilegeEntity item : list) {
      
      logger.info("action:" + action.toString() + ";operation:"
          + item.getOperationList());
      
      if (action != null
          && action.toString().equalsIgnoreCase(item.getOperationList())) {
        
        ResourceDescription desc = getResourceDescByJSON(item.getResource());
        if (desc == null) {
          continue;
        }
        logger.info("matching rule:" + accountId);
        // matching rule
        if (type.toString().equalsIgnoreCase(desc.getResource())
            && granularityType.toString().equalsIgnoreCase(
                desc.getGranularity())) {
          
          PriviledgeHandler handler = null;
          if (granularityType.toString().equalsIgnoreCase(
              GranularityType.Entity.toString())) {
            handler = new EntityPriviledgeHandler();
          } else if (granularityType.toString().equalsIgnoreCase(
              GranularityType.Field.toString())) {
            handler = new EntityPriviledgeHandler();
          }
          logger.info("handle rule:" + desc.getResource() + ";" + bizId + ";");
          return handler.getPriviledgeValue(desc, bizId, fieldsKeyList);
        }
        
      }
    }
    
    return 1;
  }
  
  /**
   * 
   * @param accountId
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<PrivilegeEntity> getPriviledgeList(int accountId) {
    
    List<PrivilegeEntity> list = privilegeDao.select(
        "findPrivilegeByAccountId", new UniqueIdObject(accountId));
    if (!CollectionUtils.isEmpty(list)) {
      list = CommonUtils.duplicateList(list);
    }
    return list;
  }
  
  /**
   * 
   * @return 1:无权限 3:直接更改
   */
  public int hasPowerUpdateExternalHouse(int accountId, int houseId,
      List<String> fieldsKeyList) {
    
    Set<Integer> residenceIds = HouseMartContext.getResidenceIds();
    HouseExtEntity entity = (HouseExtEntity) houseDao.load("loadHouseExt",
        houseId);
    
    logger.info("currentUser:" + HouseMartContext.getCurrentUserId());
    
    if (HouseMartContext.getCurrentUserId() == entity.getCreator()) { // 房源所有者
    
      if (HouseEntity.StatusEnum.Invalid.value.equals(entity.getStatus()) || HouseEntity.StatusEnum.Default.value.equals(entity.getStatus())) { // 审核通过前或者无效盘
        return 3;
      } else {
        if (!entity.getSaleSaleStatus().equals(
            HouseSaleEntity.SaleStatusEnum.Saling.saleStatus)
            && !entity.getRentRentStatus().equals(
                HouseRentEntity.RentStatusEnum.Renting.rentStatus)) {
          // 非流通盘
          return 3;
        }
        
        // 流通盘
        for (String field : fieldsKeyList) {
          if (!externalHouseUpdateWhiteList.contains(field)) {
            return 1;
          }
        }
        return 3;
      }
    }
    
    if (CollectionUtils.isNotEmpty(residenceIds)) { // 区域经理
      logger.info("residenceId: " + entity.getResidenceId());
      if (residenceIds.contains(entity.getResidenceId())) {
        return 3;
      }
    }
    
    return 0;
  }
  
  /**
   * 
   * @param resource
   * @return
   */
  private ResourceDescription getResourceDescByJSON(String resource) {
    
    ObjectMapper mapper = new ObjectMapper();
    ResourceDescription target = null;
    try {
      target = mapper.readValue(resource, ResourceDescription.class);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return target;
  }
  
  /**
   * 
   * @Title: getSuperPriviledge
   * @Description: TODO
   * @param @return
   * @return PrivilegeEntity
   * @throws
   */
  protected PrivilegeEntity getSuperPriviledge() {
    PrivilegeEntity entity = new PrivilegeEntity();
    entity.setId(-1); // 超级管理员
    return entity;
  }
  
}
