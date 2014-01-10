package org.housemart.web.beans;

public class HouseMartToken {
	
	private boolean isLoggin;
	private String tokenStr;
	public boolean isLoggin() {
		return isLoggin;
	}
	public void setLoggin(boolean isLoggin) {
		this.isLoggin = isLoggin;
	}
	public String getTokenStr() {
		return tokenStr;
	}
	public void setTokenStr(String tokenStr) {
		this.tokenStr = tokenStr;
	}
}
