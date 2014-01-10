package org.housemart.service.priviledge;


import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.housemart.dao.entities.AccountExtEntity;
import org.housemart.dao.entities.HouseEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.service.enums.OrgDescription;
import org.housemart.service.enums.ResourceType;
import org.housemart.web.context.HouseMartContext;
import org.housemart.web.context.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrgRuleEngine {

	private static final Logger logger = LoggerFactory
				.getLogger("CommonLogger");
	@SuppressWarnings("rawtypes")
	private static GenericDao houseDao = SpringContextHolder.getBean("houseDao");
	
	@SuppressWarnings("rawtypes")
	private static GenericDao accountResidenceDao = SpringContextHolder.getBean("accountResidenceDao");

	@SuppressWarnings("rawtypes")
  private static GenericDao accountDao = SpringContextHolder.getBean("accountDao");
	
	/**
	 * 
	 * @param orgRule
	 * @return 
	 */
	public static int parseOwn(String resourceType, OrgDescription orgRule, int bizId){
		
		logger.info("parseOwnBegin:" + resourceType + " " + orgRule != null ? "" + orgRule.getOwn(): "");
		
		if(orgRule == null || orgRule.getOwn() == 0 || resourceType == null){
			return 0;
		}
		Set<Integer> residenceIds = HouseMartContext.getResidenceIds();
		//房源
		logger.info("parseOwnBegin:" + HouseMartContext.getCurrentUserId());
		if(resourceType.equalsIgnoreCase(ResourceType.House.toString())){
			
			HouseEntity entity = (HouseEntity) houseDao.load("loadHouse", bizId);
			
			if(CollectionUtils.isNotEmpty(residenceIds)){ // 本小区经纪人
				logger.info("parseOwnResidenceId: " + entity.getResidenceId());
				
				if(entity.getStatus().equals(HouseEntity.StatusEnum.Invalid.value)){
	        return 3;
	      }
				
				if(residenceIds.contains(entity.getResidenceId())){
					logger.info("parseOwnValue: " + orgRule.getOwn());
					return orgRule.getOwn();
				}
			}
			
			if(entity.getCreator().equals(HouseMartContext.getCurrentUserId())){ // 跨小区自己的房源
			  
			  if(entity.getStatus().equals(HouseEntity.StatusEnum.Invalid.value)){
	        return 3;
	      }
			  
			  return orgRule.getOwn();
	    }
		}
		
		
		
		return 0;
	}
	
	/**
	 * 
	 * @param resourceType
	 * @param orgRule
	 * @param bizId
	 * @return
	 */
	public static int parseCross(String resourceType, OrgDescription orgRule, int bizId){
		
		if(orgRule == null || orgRule.getCross() == 0 || resourceType == null){
			return 0;
		}		
		
		//房源
		if(resourceType.equalsIgnoreCase(ResourceType.House.toString())){
			
			HouseEntity entity = (HouseEntity)houseDao.load("loadHouse", bizId);
			if(entity != null && entity.getResidenceId() != null){
				
				Set<Integer> residenceIds = HouseMartContext.getResidenceIds();
				AccountExtEntity account = (AccountExtEntity) accountDao.load("loadAccountById", HouseMartContext.getCurrentUserId());
				if(!CollectionUtils.isEmpty(residenceIds) || account.getType().equals(1)){ // 有分配小区或者外围经纪人
					if(!residenceIds.contains(entity.getResidenceId())){
						return orgRule.getCross();
					}
				}
			}	
			
		}

		return 0;
		
	}
	
	public static int parseAbove(String resourceType, OrgDescription orgRule, int bizId){
		
		if(orgRule == null || orgRule.getAbove() == 0 || resourceType == null){
			return 0;
		}
		
		if(resourceType.equalsIgnoreCase(ResourceType.House.toString())){
			
			HouseEntity entity = (HouseEntity)houseDao.load("loadHouse", bizId);
			if(entity != null && entity.getResidenceId() != null){
				
				Set<Integer> managerIds = HouseMartContext.getRegionMangerIds();		
				if(!CollectionUtils.isEmpty(managerIds)){
					if(managerIds.contains(HouseMartContext.getCurrentUserId())){
					  
					  Set<Integer> residenceIds = HouseMartContext.getResidenceIds();
		        logger.info("parseAboveResidenceId: " + entity.getResidenceId());
		        if (!CollectionUtils.isEmpty(residenceIds)) {
		          if (residenceIds.contains(entity.getResidenceId())) {
		            logger.info("parseAboveValue: " + orgRule.getAbove());
		            return orgRule.getAbove();
		          }
		        }
		        
					}
				}
			  
			  
			  
      
			}
			
		}
	
	return 0;
		
	}

}
