/**
 * Created on 2013-11-10
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.schedule.tasks;

import java.util.TimerTask;

import org.housemart.service.HouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HouseInteractionNoticeDeletionTask extends TimerTask {
  
  private static final Logger logger = LoggerFactory
      .getLogger(HouseInteractionNoticeDeletionTask.class);
  
  private HouseService houseService = new HouseService();
  
  @Override
  public void run() {
    
    try {
      logger.info("begin deleting over date house interaction notices");
      
      houseService.deleteOverDateInteraction();
      
      logger.info("finish deleting over date house interaction notices");
    } catch (Exception e) {
      logger.error("Index Error", e);
    }
    
  }
  
}
