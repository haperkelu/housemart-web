package org.housemart.dao.entities;

public class AccountRevokeExtEntity extends AccountRevokeEntity {

	protected String accountLoginName;
	protected String accountName;
	protected String managerName;
	protected String managerPhone;
	
	public String getAccountLoginName() {
		return accountLoginName;
	}
	public void setAccountLoginName(String accountLoginName) {
		this.accountLoginName = accountLoginName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerPhone() {
		return managerPhone;
	}
	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}
	
	
}