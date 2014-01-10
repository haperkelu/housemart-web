package org.housemart.service.enums;

public class HouseEnumUtils {

	public static String getHouseSaleStatusName(int status){
		
		if(status <= 0){
			return "不售";
		}
		
		if(status == 1){
			return "在售";
		}
		
		if(status == 2){
			return "不售";
		}
		
		if(status == 3){
			return "已售";
		}
		return "不售";
	}
	
	public static String getHouseRentStatusName(int status){
		
		if(status <= 0){
			return "不租";
		}
		
		if(status == 1){
			return "在租";
		}
		
		if(status == 2){
			return "不租";
		}
		
		if(status == 3){
			return "已租";
		}
		
		return "不售";
	}
	
	
}
