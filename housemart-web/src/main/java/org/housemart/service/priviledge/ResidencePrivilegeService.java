/**
 * Created on 2013-9-13
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.service.priviledge;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.housemart.dao.entities.PrivilegeEntity;
import org.housemart.service.enums.ActionType;
import org.housemart.service.enums.PriviledgeResultType;
import org.housemart.web.context.HouseMartContext;

public class ResidencePrivilegeService extends PrivilegeService {
  
  /**
   * 
   * @param accountId
   * @param plateId
   *          创建小区传plateId，编辑小区传residenceId
   * @return1:无权限 2:需要审核 3:直接更改
   */
  public int hasPower(int accountId, ActionType actionType, int regionId,
      int plateId) {
    
    // 参数验证
    if (accountId < 0 || (actionType == null) || plateId < 0) {
      return PriviledgeResultType.NoRight.getValue();
    }
    
    if (!(actionType.getType() == ActionType.Add.getType() || actionType
        .getType() == ActionType.Update.getType())) {
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
      // 超级管理员
      if (list.contains(getSuperPriviledge())) {
        logger.info("super admin:" + accountId);
        return PriviledgeResultType.Direct.getValue();
      }
    }
    
    // 区域经理
    Set<Integer> managerIds = HouseMartContext.getRegionMangerIds();
    if (!CollectionUtils.isEmpty(managerIds)) {
      if (managerIds.contains(HouseMartContext.getCurrentUserId())) {
        
        if (actionType.getType() == ActionType.Add.getType()) {
          Set<Integer> regionIds = HouseMartContext.getRegionIds();
          logger.info("parseAbovePlateIds: " + plateId);
          if (!CollectionUtils.isEmpty(regionIds)) {
            if (regionIds.contains(regionId)) {
              return PriviledgeResultType.Direct.getValue();
            }
          }
          
          Set<Integer> plateIds = HouseMartContext.getPlateIds();
          logger.info("parseAbovePlateIds: " + plateId);
          if (!CollectionUtils.isEmpty(plateIds)) {
            if (plateIds.contains(plateId)) {
              return PriviledgeResultType.Direct.getValue();
            }
          }
        }
        if (actionType.getType() == ActionType.Update.getType()) {
          Set<Integer> regionIds = HouseMartContext.getRegionIds();
          logger.info("parseAbovePlateIds: " + plateId);
          if (!CollectionUtils.isEmpty(regionIds)) {
            if (regionIds.contains(regionId)) {
              return PriviledgeResultType.Direct.getValue();
            }
          }
          
          Set<Integer> plateIds = HouseMartContext.getPlateIds();
          logger.info("parseAbovePlateIds: " + plateId);
          if (!CollectionUtils.isEmpty(plateIds)) {
            if (plateIds.contains(plateId)) {
              return PriviledgeResultType.Direct.getValue();
            }
          }
        }
        
      }
    }
    
    // 其它
    return PriviledgeResultType.Audit.getValue();
    
  }
  
}
