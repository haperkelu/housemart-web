package org.housemart.service.enums;

public class ResourceDescription {

	private String resource;
	private String granularity;
	private String[] whiteListField;
	private OrgDescription whiteListOrgRule;	
	private OrgDescription orgRule;
	
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getGranularity() {
		return granularity;
	}
	public void setGranularity(String granularity) {
		this.granularity = granularity;
	}
	public String[] getWhiteListField() {
		return whiteListField;
	}
	public void setWhiteListField(String[] whiteListField) {
		this.whiteListField = whiteListField;
	}
	public OrgDescription getWhiteListOrgRule() {
		return whiteListOrgRule;
	}
	public void setWhiteListOrgRule(OrgDescription whiteListOrgRule) {
		this.whiteListOrgRule = whiteListOrgRule;
	}
	public OrgDescription getOrgRule() {
		return orgRule;
	}
	public void setOrgRule(OrgDescription orgRule) {
		this.orgRule = orgRule;
	}

}
