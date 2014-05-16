/**
 * Created on 2014-1-1
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.util;

public class PicUtils {
  
  private static final String SUFIX_WATER_MASK = "watermark/1/image/aHR0cDovL2hvdXNlbWFydC5xaW5pdWRuLmNvbS93YXRlcm1hcmslMjAwNF8xMzgxNDA0MTg1MTk0LnBuZw==/dissolve/100/gravity/Center";
  private static boolean ENABLE_WATER_MASK = false;
  
  public static String wrapWaterMask(String picUrl) {
	  return ENABLE_WATER_MASK? (picUrl + "?" + SUFIX_WATER_MASK): picUrl;
  }
  
}
