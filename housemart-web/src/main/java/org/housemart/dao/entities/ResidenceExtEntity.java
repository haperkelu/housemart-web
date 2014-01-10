package org.housemart.dao.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.web.context.SpringContextHolder;

public class ResidenceExtEntity extends ResidenceEntity {

	protected List<AccountExtEntity> managers;
	protected List<AccountExtEntity> agents;
	
  float annualPriceIncrement;
  float annualTurnoverPercent;
  float rentRevenue;
  int year;
  int month;
  protected int brokerCount;
  protected String brokerList;
	
	@SuppressWarnings("rawtypes")
	private GenericDao accountResidence = SpringContextHolder.getBean("accountResidenceDao");
	
	public void clearAccounts(Boolean isManager)
	{
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("positionType", isManager ? "'区域经理'" : "'经纪人', '初级经纪人'");
		map.put("residenceID", this.residenceId);
		this.accountResidence.deletePhysical("deleteResidenceAccounts", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountExtEntity> getManagers(){
		
		if (managers == null)
		{
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("positionType", "'区域经理'");
			map.put("residenceID", this.residenceId);
			managers = accountResidence.select("findResidenceAccountList",  map);
		}
		return managers;
	}
	
	public void setManagers(Integer[] list)
	{
		this.clearAccounts(true);
		if (list != null)
		{
			for(int i = 0; i < list.length; i++)
			{
				AccountResidenceEntity accountResidence = new AccountResidenceEntity();
				accountResidence.setAccountID(list[i]);
				accountResidence.setResidenceID(this.residenceId);
				accountResidence.setResidenceIDType(AccountResidenceEntity.ResidenceIDTypeEnum.Residence.value);
				accountResidence.setAddTime(new Date());
				this.accountResidence.add("addAccountResidence", accountResidence);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountExtEntity> getAgents() {
		
		if (agents == null)
		{
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("positionType", "'初级经纪人', '经纪人'");
			map.put("residenceID", this.residenceId);
			agents = accountResidence.select("findResidenceAccountList",  map);
		}
		return agents;
	}
	public void setAgents(Integer[] list)
	{
		this.clearAccounts(false);
		if (list != null)
		{
			for(int i = 0; i < list.length; i++)
			{
				AccountResidenceEntity accountResidence = new AccountResidenceEntity();
				accountResidence.setAccountID(list[i]);
				accountResidence.setResidenceID(this.residenceId);
				accountResidence.setResidenceIDType(AccountResidenceEntity.ResidenceIDTypeEnum.Residence.value);
				accountResidence.setAddTime(new Date());
				this.accountResidence.add("addAccountResidence", accountResidence);
			}
		}
	}

  public float getAnnualPriceIncrement() {
    return annualPriceIncrement;
  }

  public void setAnnualPriceIncrement(float annualPriceIncrement) {
    this.annualPriceIncrement = annualPriceIncrement;
  }

  public float getAnnualTurnoverPercent() {
    return annualTurnoverPercent;
  }

  public void setAnnualTurnoverPercent(float annualTurnoverPercent) {
    this.annualTurnoverPercent = annualTurnoverPercent;
  }

  public float getRentRevenue() {
    return rentRevenue;
  }

  public void setRentRevenue(float rentRevenue) {
    this.rentRevenue = rentRevenue;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
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
	
}