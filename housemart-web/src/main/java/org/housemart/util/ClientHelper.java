/**
 * Created on 2013-11-17
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.util;

public class ClientHelper {
  
  public static final String KEY_CLIENT_VERSION = "client_version";
  public static final String ATTR_REQUIRED_VERSION = "requiredVersion";
  public static final String ATTR_CURRENT_VERSION = "currentVersion";
  public static final String ATTR_CURRENT_VERSION_INFO = "currentVersionInfo";
  
  public String toFinalKey(char clientType, char osType) {
    return KEY_CLIENT_VERSION + "_" + clientType + "_" + osType;
  }
  
}
