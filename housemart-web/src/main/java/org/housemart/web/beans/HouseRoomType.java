/**
 * Created on 2013-5-17
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.beans;


public class HouseRoomType {
  
  Integer shi;
  Integer ting;
  Integer wei;
  Integer yangtai;
  
  public static HouseRoomType newInstance(Integer shi, Integer ting,
      Integer wei, Integer yangtai) {
    HouseRoomType result = new HouseRoomType();
    result.shi = shi;
    result.ting = ting;
    result.wei = wei;
    result.yangtai = yangtai;
    return result;
  }
  
  public Integer getShi() {
    return shi;
  }
  
  public void setShi(Integer shi) {
    this.shi = shi;
  }
  
  public Integer getTing() {
    return ting;
  }
  
  public void setTing(Integer ting) {
    this.ting = ting;
  }
  
  public Integer getWei() {
    return wei;
  }
  
  public void setWei(Integer wei) {
    this.wei = wei;
  }
  
  public Integer getYangtai() {
    return yangtai;
  }
  
  public void setYangtai(Integer yangtai) {
    this.yangtai = yangtai;
  }
  
  public Integer[] toArray() {
    Integer[] result = {0, 0, 0, 0};
    if (this.shi != null) result[0] = this.shi;
    if (this.ting != null) result[1] = this.ting;
    if (this.wei != null) result[2] = this.wei;
    if (this.yangtai != null) result[3] = this.yangtai;
    return result;
  }
  
}
