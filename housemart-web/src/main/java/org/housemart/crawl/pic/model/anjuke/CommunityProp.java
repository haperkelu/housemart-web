/**
 * Created on 2013-4-6
 * 
 */
package org.housemart.crawl.pic.model.anjuke;

public class CommunityProp {

	private String address;
	private String block_name;
	private String id;
	private String lat;
	private String lng;
	private String name;
	private String price;

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBlock_name() {
		return block_name;
	}
	public void setBlock_name(String block_name) {
		this.block_name = block_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public String toString() {
		return "CommunityProp [id=" + this.id + ", name=" + this.name + ", block_name=" + this.block_name
				+ ", address=" + this.address + ", price=" + this.price + ", lat=" + this.lat + ", lng=" + this.lng
				+ "]";
	}

}
