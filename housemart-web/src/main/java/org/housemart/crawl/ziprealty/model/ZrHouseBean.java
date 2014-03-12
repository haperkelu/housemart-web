/**
 * Created on 2014-3-10
 * 
 */
package org.housemart.crawl.ziprealty.model;

import java.util.Date;
import java.util.List;

public class ZrHouseBean {
  private int id;
  private Date addTime;
  private Date updateTime;
  
  private String title;
  private String block;
  private String mls;
  private String neighborhood;
  private String houseDetail;
  private String price;
  private String status;
  private String listed;
  private String link;
  private List<String> qnPics;
  private String zrPics;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public Date getAddTime() {
    return addTime;
  }
  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
  public Date getUpdateTime() {
    return updateTime;
  }
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getBlock() {
    return block;
  }
  public void setBlock(String block) {
    this.block = block;
  }
  public String getMls() {
    return mls;
  }
  public void setMls(String mls) {
    this.mls = mls;
  }
  public String getNeighborhood() {
    return neighborhood;
  }
  public void setNeighborhood(String neighborhood) {
    this.neighborhood = neighborhood;
  }
  public String getHouseDetail() {
    return houseDetail;
  }
  public void setHouseDetail(String houseDetail) {
    this.houseDetail = houseDetail;
  }
  public String getPrice() {
    return price;
  }
  public void setPrice(String price) {
    this.price = price;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getListed() {
    return listed;
  }
  public void setListed(String listed) {
    this.listed = listed;
  }
  public String getLink() {
    return link;
  }
  public void setLink(String link) {
    this.link = link;
  }
  public List<String> getQnPics() {
    return qnPics;
  }
  public void setQnPics(List<String> qnPics) {
    this.qnPics = qnPics;
  }
  public String getZrPics() {
    return zrPics;
  }
  public void setZrPics(String zrPics) {
    this.zrPics = zrPics;
  }
  
  
}
