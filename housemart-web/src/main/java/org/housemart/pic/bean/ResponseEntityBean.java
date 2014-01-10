/**
 * Created on 2013-5-22
 * 
 */
package org.housemart.pic.bean;

public class ResponseEntityBean {
  int code;
  String msg;
  Object bizData;
  
  public int getCode() {
    return code;
  }
  
  public void setCode(int code) {
    this.code = code;
  }
  
  public String getMsg() {
    return msg;
  }
  
  public void setMsg(String message) {
    this.msg = message;
  }
  
  public Object getBizData() {
    return bizData;
  }
  
  public void setBizData(Object bizData) {
    this.bizData = bizData;
  }
  
}
