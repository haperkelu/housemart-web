package org.housemart.web.beans;

import java.util.Date;

import org.housemart.dao.entities.ResidenceEntity;

public class AuditResidenceStatusAndContentBean extends ResidenceEntity {
  private String content;
  private Date addTime;
  
  Double annualPriceIncrement;
  Double annualTurnoverPercent;
  Double rentRevenue;
  
  public String getContent() {
    return content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public Date getAddTime() {
    return addTime;
  }
  
  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }

  public Double getAnnualPriceIncrement() {
    return annualPriceIncrement;
  }

  public void setAnnualPriceIncrement(Double annualPriceIncrement) {
    this.annualPriceIncrement = annualPriceIncrement;
  }

  public Double getAnnualTurnoverPercent() {
    return annualTurnoverPercent;
  }

  public void setAnnualTurnoverPercent(Double annualTurnoverPercent) {
    this.annualTurnoverPercent = annualTurnoverPercent;
  }

  public Double getRentRevenue() {
    return rentRevenue;
  }

  public void setRentRevenue(Double rentRevenue) {
    this.rentRevenue = rentRevenue;
  }

  
}
