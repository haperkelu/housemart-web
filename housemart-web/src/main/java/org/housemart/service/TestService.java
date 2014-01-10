package org.housemart.service;

import org.housemart.dao.entities.ResidenceBuildingEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.web.context.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;


public class TestService {

	@SuppressWarnings("rawtypes")
	@Autowired
	private GenericDao residenceDao = SpringContextHolder.getBean("residenceDao");
	
	public void testTransaction(){
				
		ResidenceBuildingEntity entity = new ResidenceBuildingEntity();
		entity.setResidenceId(1);
		entity.setPrefix("1");
		entity.setSuffix("1");
		entity.setCodeType(1);
		entity.setCodeBegin("22");
		entity.setCodeEnd("22");
		entity.setPeriod("111");
		entity.setStair(1);
		entity.setHouseHold(2);
		entity.setBuildingType("ddd");
		residenceDao.add("addResidenceBuilding", entity);
		throw new RuntimeException("ddd");
	}
	
}
