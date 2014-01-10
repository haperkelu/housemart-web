package org.housemart.schedule.tasks;

import java.util.TimerTask;

import org.housemart.service.ClientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationTask extends TimerTask {
		
	private static final Logger logger = LoggerFactory.getLogger(NotificationTask.class);
	
	private ClientService clientService = new ClientService();

	@Override
	public void run() {
		
		try {
			logger.debug("begin process client notification");
				
			clientService.processClientNotifications();
			clientService.sendClientNotifications();
			
			logger.debug("begin process client notification end");
		} catch (Exception e) {
			logger.error("Index Error", e);
		}
		
	}

}
