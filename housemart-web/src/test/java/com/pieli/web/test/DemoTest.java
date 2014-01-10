package com.pieli.web.test;

import org.brilliance.middleware.client.ClientWrapper;
import org.housemart.rpc.stubs.AuditServiceStub;
import org.housemart.schedule.tasks.MaxinSynDataTask;
import org.junit.Test;


public class DemoTest {
	
	MaxinSynDataTask maxinSynDataTask;
	

	@Test
	public void fn1() {
		
		AuditServiceStub stub = (AuditServiceStub) ClientWrapper.powerStub(AuditServiceStub.class, "42.121.87.85", 8088);
		int result = stub.requestAddNewHouse(132);
		System.out.println(result);
	}
	
	public void fn(){
		String[] arr = "3131_1370421688406".split("_");
		String o = "";
		for(int i = 0; i < arr[0].length(); i += 2){
			
			char c1 = arr[0].charAt(i);
			char c2 = arr[0].charAt(i + 1);
			int targetValue = Integer.parseInt(String.valueOf(c1) + String.valueOf(c2), 16);
			o += String.valueOf((char)targetValue);						
		}
		System.out.println(o);
	};
	
}
