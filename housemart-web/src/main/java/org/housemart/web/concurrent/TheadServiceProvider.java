package org.housemart.web.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TheadServiceProvider {

	private static final ExecutorService service = Executors.newFixedThreadPool(10);
	
	public TheadServiceProvider(){	
		
	}
	
	public static ExecutorService getThreadService(){
		return service;
	}
	
	
}
