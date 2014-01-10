package org.housemart.dao.entities;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.housemart.framework.serialize.JsonDateSerializer;

public class ResidenceNameHistoryEntity {

	private int id;
	private int residenceId;
	private String oldName;
	private Date addTime;
	private int accountId;
	private String accountName;
	
	//type=1 residenceName; type=2 aliasName
	private int type;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getResidenceId() {
		return residenceId;
	}
	public void setResidenceId(int residenceId) {
		this.residenceId = residenceId;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getAddTime() {
		return addTime;
	}
	
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}	
	
}
