/**
 * Created on 2013-12-22
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.schedule.tasks;

import java.util.TimerTask;

import org.housemart.service.AuditService;
import org.housemart.service.HouseService;

public class HouseOnboardDateTask extends TimerTask {
  
  private HouseService houseService = new HouseService();
  private AuditService auditeService = new AuditService();
  
  @Override
  public void run() {
    houseService.refreshHouseOnboardTime(auditeService);
  }
  
}
