package org.housemart.dao.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.web.context.SpringContextHolder;

public class AccountResidenceEntity {

	protected Integer id;

	protected Integer accountID;
	protected Integer residenceID;
	protected Integer residenceIDType;
	
	protected String residenceName;
	protected String residencePinyinName;
	
	protected Date addTime;
	protected Date updateTime;
	
	protected List<AccountExtEntity> managers;
	protected List<AccountExtEntity> agents;
	
	protected int brokerCount;
	protected String brokerList;
	
	protected Integer residenceZombie;
	
	@SuppressWarnings("rawtypes")
	private GenericDao accountResidence = SpringContextHolder.getBean("accountResidenceDao");
	
	public static enum ResidenceIDTypeEnum {
		Residence(0), Plate(1), Region(2);
	    public Integer value;
	    
	    ResidenceIDTypeEnum(Integer type) {
	    	this.value = type;
	    }
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccountID() {
		return accountID;
	}

	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

	public Integer getResidenceID() {
		return residenceID;
	}

	public void setResidenceID(Integer residenceID) {
		this.residenceID = residenceID;
	}
	
	public Integer getResidenceIDType() {
		return residenceIDType;
	}

	public void setResidenceIDType(Integer residenceIDType) {
		this.residenceIDType = residenceIDType;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	public String getResidenceName() {
		return residenceName;
	}

	public void setResidenceName(String residenceName) {
		this.residenceName = residenceName;
	}

	public String getResidencePinyinName() {
		return residencePinyinName;
	}

	public void setResidencePinyinName(String residencePinyinName) {
		this.residencePinyinName = residencePinyinName;
	}

	public int getBrokerCount() {
		return brokerCount;
	}

	public void setBrokerCount(int brokerCount) {
		this.brokerCount = brokerCount;
	}

	public String getBrokerList() {
		return brokerList;
	}

	public void setBrokerList(String brokerList) {
		this.brokerList = brokerList;
	}
	
	public void updateBrokerList()
	{
		this.getAgents();
		this.getManagers();
		  
		this.brokerCount = agents.size() + managers.size();
		  
		this.brokerList = "";
		  
		for(AccountExtEntity account : agents)
		{
			this.brokerList += (this.brokerList.isEmpty() ? "" : ", ") + account.getName(); 
		}
		  
		for(AccountExtEntity account : managers)
		{
			this.brokerList += (this.brokerList.isEmpty() ? "" : ", ") + account.getName(); 
		}
		  
		if (this.brokerList.isEmpty())
		{
			this.brokerList = "未分配";
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountExtEntity> getManagers(){
		
		if (managers == null)
		{
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("positionType", "'区域经理'");
			map.put("residenceID", this.residenceID);
			managers = accountResidence.select("findResidenceAccountList",  map);
		}
		return managers;
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountExtEntity> getAgents() {
		
		if (agents == null)
		{
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("positionType", "'初级经纪人', '经纪人'");
			map.put("residenceID", this.residenceID);
			agents = accountResidence.select("findResidenceAccountList",  map);
		}
		return agents;
	}

	public Integer getResidenceZombie() {
		return residenceZombie;
	}

	public void setResidenceZombie(Integer residenceZombie) {
		this.residenceZombie = residenceZombie;
	}
	
}