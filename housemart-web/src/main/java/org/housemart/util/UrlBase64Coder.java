/**
 * Created on 2013-11-17
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.util;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.UrlBase64;

/**
 * 
 * @author pqin
 */
public class UrlBase64Coder {
  
  public final static String ENCODING = "UTF-8";
  
  // 加密
  public static String encoded(String data) throws UnsupportedEncodingException {
    byte[] b = UrlBase64.encode(data.getBytes(ENCODING));
    return new String(b, ENCODING);
  }
  
  // 解密
  public static String decode(String data) throws UnsupportedEncodingException {
    byte[] b = UrlBase64.decode(data.getBytes(ENCODING));
    return new String(b, ENCODING);
  }
}
