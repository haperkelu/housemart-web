package org.housemart.web.security;

import java.util.Date;

import org.apache.commons.lang.StringUtils;


public class SimpleSecurityService {
	
	
	public static String encrypt(String input, Date dt){
				
		if(!StringUtils.isEmpty(input)){
			String result = StringUtils.EMPTY;
			for(int i = 0; i < input.getBytes().length; i ++){
				byte b = input.getBytes()[i];
				String hex = Integer.toHexString(b & 0xFF); 
				if (hex.length() == 1) {
			         hex = '0' + hex;
			    } 
				result += hex;		
			}
			
			return result + "_" + dt.getTime();
		}
		return StringUtils.EMPTY;
	}
	
	public static String decrypt(String input){
		
		if(!StringUtils.isEmpty(input) && input.contains("_")){
			
			String[] arr = input.split("_");			
			if(arr != null && arr.length == 2){
				long then = Long.parseLong(arr[1]);
				if((new Date().getTime() - then) <= 24 * 36000 * 1000){
					
					int length = arr[0].toCharArray().length;
					byte[] result = new byte[length / 2];

					for(int i = 0; i < arr[0].toCharArray().length; i ++){
						if(i % 2 == 0){
							char a,b;
							a = arr[0].toCharArray()[i];
							b = arr[0].toCharArray()[i + 1];
							result[i/2] = (byte)(a << 4 | b);	
						}									
					}
					return new String(result);
				}
			}			
		}
		
		return StringUtils.EMPTY;
	}

}
