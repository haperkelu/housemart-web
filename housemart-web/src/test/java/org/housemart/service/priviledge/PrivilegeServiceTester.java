package org.housemart.service.priviledge;

import org.housemart.service.enums.ActionType;
import org.housemart.service.enums.GranularityType;
import org.housemart.service.enums.ResourceType;
import org.housemart.web.context.HouseMartContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/beans/spring*.xml"})
public class PrivilegeServiceTester {
  @Autowired
  PrivilegeService privilegeService;
  
  @Test
  public void haspowerTest() {
    
    // 所有者经纪人
    HouseMartContext.setCurrentUserId(17);
    System.out.println(privilegeService.hasPower(17, ActionType.View,
        ResourceType.House_Contact, GranularityType.Entity, 2673, null));
    
    // 本小区管理员
    HouseMartContext.setCurrentUserId(17);
    System.out.println(privilegeService.hasPower(17, ActionType.View,
        ResourceType.House_Contact, GranularityType.Entity, 2673, null));
    
    // 跨小区管理员
    
    // 跨小区经纪人
  }
}
