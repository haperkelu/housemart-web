package org.housemart.schedule.tasks;

import java.util.TimerTask;

import org.housemart.service.HouseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HouseInteractionTask extends TimerTask {
		
	private static final Logger logger = LoggerFactory.getLogger(HouseInteractionTask.class);
	
	private HouseService houseService = new HouseService();

	@Override
	public void run() {
		
		try {
			logger.debug("begin checking house interactions");
				
			houseService.checkInteractions();
			
			logger.debug("checking house interactions end");
		} catch (Exception e) {
			logger.error("Index Error", e);
		}
		
	}

}
