/**
 * Created on 2012-10-21
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.dao.entities;


/**
 * @author pqin
 */
public class HouseAuditHistoryExtEntity {
  int saleStatus;
  int rentStatus;
  public int getSaleStatus() {
    return saleStatus;
  }
  public void setSaleStatus(int saleStatus) {
    this.saleStatus = saleStatus;
  }
  public int getRentStatus() {
    return rentStatus;
  }
  public void setRentStatus(int rentStatus) {
    this.rentStatus = rentStatus;
  }
  
}
