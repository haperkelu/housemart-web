/**
 * Created on 2013-10-20
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.beans;

import java.util.List;


public class HousePicOrderByTypeParameterBean {
  private int type;
  private List<HousePicOrderByTypeItem> items;
  
  public int getType() {
    return type;
  }
  
  public void setType(int type) {
    this.type = type;
  }
  
  public List<HousePicOrderByTypeItem> getItems() {
    return items;
  }
  
  public void setItems(List<HousePicOrderByTypeItem> items) {
    this.items = items;
  }
  
  public static class HousePicOrderByTypeItem {
    int id;
    int order;
    
    public int getId() {
      return id;
    }
    
    public void setId(int id) {
      this.id = id;
    }
    
    public int getOrder() {
      return order;
    }
    
    public void setOrder(int order) {
      this.order = order;
    }
  }
}
