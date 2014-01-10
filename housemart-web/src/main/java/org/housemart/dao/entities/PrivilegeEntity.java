package org.housemart.dao.entities;

public class PrivilegeEntity implements Entity {

	private int id;
	private String name;
	private String resource;
	private String operationList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getOperationList() {
		return operationList;
	}
	public void setOperationList(String operationList) {
		this.operationList = operationList;
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.id;
	}
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PrivilegeEntity){
			return this.id == ((PrivilegeEntity)obj).id;
		}
		return super.equals(obj);
	}
	
	
	
}
