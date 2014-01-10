/** 
* @Title: AreaPositionEntity.java
* @Package org.housemart.dao.entities
* @Description: TODO
* @author Pie.Li
* @date 2013-3-27 下午3:29:12
* @version V1.0 
*/
package org.housemart.dao.entities;

/**
 * @ClassName: AreaPositionEntity
 * @Description: TODO
 * @date 2013-3-27 下午3:29:12
 * 
 */
public class AreaPositionEntity {

	private int id;
	private int cityId;
	private int type;
	private int positionId;
	private String lat;
	private String lng;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the cityId
	 */
	public int getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the positionId
	 */
	public int getPositionId() {
		return positionId;
	}
	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}
	/**
	 * @return the lng
	 */
	public String getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}
	
	
	
}
