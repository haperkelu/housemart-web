/**
 * Created on 2013-11-29
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.dao.entities;

public class ResidenceMonthRawData {
  int residenceId;
  String residenceName;
  String regionName;
  String plateName;
  int year;
  int month;
  double annualPriceIncrement;
  double annualTurnoverPercent;
  double rentRevenue;
  double averagePrice;
  public int getResidenceId() {
    return residenceId;
  }
  public void setResidenceId(int residenceId) {
    this.residenceId = residenceId;
  }
  public String getResidenceName() {
    return residenceName;
  }
  public void setResidenceName(String residenceName) {
    this.residenceName = residenceName;
  }
  public String getRegionName() {
    return regionName;
  }
  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }
  public String getPlateName() {
    return plateName;
  }
  public void setPlateName(String plateName) {
    this.plateName = plateName;
  }
  public int getYear() {
    return year;
  }
  public void setYear(int year) {
    this.year = year;
  }
  public int getMonth() {
    return month;
  }
  public void setMonth(int month) {
    this.month = month;
  }
  public double getAnnualPriceIncrement() {
    return annualPriceIncrement;
  }
  public void setAnnualPriceIncrement(double annualPriceIncrement) {
    this.annualPriceIncrement = annualPriceIncrement;
  }
  public double getAnnualTurnoverPercent() {
    return annualTurnoverPercent;
  }
  public void setAnnualTurnoverPercent(double annualTurnoverPercent) {
    this.annualTurnoverPercent = annualTurnoverPercent;
  }
  public double getRentRevenue() {
    return rentRevenue;
  }
  public void setRentRevenue(double rentRevenue) {
    this.rentRevenue = rentRevenue;
  }
  public double getAveragePrice() {
    return averagePrice;
  }
  public void setAveragePrice(double averagePrice) {
    this.averagePrice = averagePrice;
  }
  
}
