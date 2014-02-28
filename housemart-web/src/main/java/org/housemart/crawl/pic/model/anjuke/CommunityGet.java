/**
 * Created on 2013-4-20
 * 
 */
package org.housemart.crawl.pic.model.anjuke;

import java.util.ArrayList;

public class CommunityGet {
	private String address;
	private String anjuke_trend;
	private String block;
	private String cityid;
	private String completion_time;
	private String default_photo;
	private String developer;
	private String id;
	private String introduction;
	private String lat;
	private String lng;
	private String name;
	private ArrayList<String> photos;
	private String property_company;
	private String property_number;
	private String sale_price;
	private String sale_price_change;
	private String supporting_facilities;
	private String type;

	public String getAddress() {
		return this.address;
	}

	public String getAnjuke_trend() {
		return this.anjuke_trend;
	}

	public String getBlock() {
		return this.block;
	}

	public String getCityid() {
		return this.cityid;
	}

	public String getCompletion_time() {
		return this.completion_time;
	}

	public String getDefault_photo() {
		return this.default_photo;
	}

	public String getDeveloper() {
		return this.developer;
	}

	public String getId() {
		return this.id;
	}

	public String getIntroduction() {
		return this.introduction;
	}

	public String getLat() {
		return this.lat;
	}

	public String getLng() {
		return this.lng;
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<String> getPhotos() {
		return this.photos;
	}

	public String getProperty_company() {
		return this.property_company;
	}

	public String getProperty_number() {
		return this.property_number;
	}

	public String getSale_price() {
		return this.sale_price;
	}

	public String getSale_price_change() {
		return this.sale_price_change;
	}

	public String getSupporting_facilities() {
		return this.supporting_facilities;
	}

	public String getType() {
		return this.type;
	}

	public void setAddress(String paramString) {
		this.address = paramString;
	}

	public void setAnjuke_trend(String paramString) {
		this.anjuke_trend = paramString;
	}

	public void setBlock(String paramString) {
		this.block = paramString;
	}

	public void setCityid(String paramString) {
		this.cityid = paramString;
	}

	public void setCompletion_time(String paramString) {
		this.completion_time = paramString;
	}

	public void setDefault_photo(String paramString) {
		this.default_photo = paramString;
	}

	public void setDeveloper(String paramString) {
		this.developer = paramString;
	}

	public void setId(String paramString) {
		this.id = paramString;
	}

	public void setIntroduction(String paramString) {
		this.introduction = paramString;
	}

	public void setLat(String paramString) {
		this.lat = paramString;
	}

	public void setLng(String paramString) {
		this.lng = paramString;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setPhotos(ArrayList<String> paramArrayList) {
		this.photos = paramArrayList;
	}

	public void setProperty_company(String paramString) {
		this.property_company = paramString;
	}

	public void setProperty_number(String paramString) {
		this.property_number = paramString;
	}

	public void setSale_price(String paramString) {
		this.sale_price = paramString;
	}

	public void setSale_price_change(String paramString) {
		this.sale_price_change = paramString;
	}

	public void setSupporting_facilities(String paramString) {
		this.supporting_facilities = paramString;
	}

	public void setType(String paramString) {
		this.type = paramString;
	}

	public String toString() {
		return "CommunityGet [block=" + this.block + ", id=" + this.id + ", name=" + this.name + ", address="
				+ this.address + ", property_number=" + this.property_number + ", lat=" + this.lat + ", lng="
				+ this.lng + ", cityid=" + this.cityid + ", introduction=" + this.introduction
				+ ", supporting_facilities=" + this.supporting_facilities + ", type=" + this.type + ", developer="
				+ this.developer + ", completion_time=" + this.completion_time + ", property_company="
				+ this.property_company + ", sale_price=" + this.sale_price + ", sale_price_change="
				+ this.sale_price_change + ", anjuke_trend=" + this.anjuke_trend + ", default_photo="
				+ this.default_photo + ", photos=" + this.photos + "]";
	}
}
