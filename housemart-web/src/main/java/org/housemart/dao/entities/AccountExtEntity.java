package org.housemart.dao.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import org.housemart.dao.entities.AccountEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.dao.generic.UniqueIdObject;
import org.housemart.web.context.SpringContextHolder;

public class AccountExtEntity extends AccountEntity {

	protected Integer countHouse = 0;
	protected Integer countHouseInteraction = 0;
	protected Integer countHouseMono= 0;
	
	@SuppressWarnings("rawtypes")
	private GenericDao accountResidence = SpringContextHolder.getBean("accountResidenceDao");
	
	@SuppressWarnings("rawtypes")
	private GenericDao account = SpringContextHolder.getBean("accountDao");
	
	@SuppressWarnings("rawtypes")
	private GenericDao accountRevoke = SpringContextHolder.getBean("accountRevokeDao");
	
	public static String hashPassword(String pwd)
	{
		if (pwd.equals(""))
		{
			pwd = "111111";
		}
		return pwd;
	}
	
	
	public void clearResidences()
	{
		this.accountResidence.deletePhysical("deleteAccountResidences", new UniqueIdObject(this.id));
	}
	
	public void setResidences(Integer[] residences, Integer[] plates, Integer[] regions)
	{
		this.clearResidences();
		if (residences != null)
		{
			for(int i = 0; i < residences.length; i++)
			{
				AccountResidenceEntity accountResidence = new AccountResidenceEntity();
				accountResidence.setAccountID(this.id);
				accountResidence.setResidenceID(residences[i]);
				accountResidence.setResidenceIDType(AccountResidenceEntity.ResidenceIDTypeEnum.Residence.value);
				accountResidence.setAddTime(new Date());
				this.accountResidence.add("addAccountResidence", accountResidence);
			}
		}
		
		if (plates != null)
		{
			for(int i = 0; i < plates.length; i++)
			{
				AccountResidenceEntity accountResidence = new AccountResidenceEntity();
				accountResidence.setAccountID(this.id);
				accountResidence.setResidenceID(plates[i]);
				accountResidence.setResidenceIDType(AccountResidenceEntity.ResidenceIDTypeEnum.Plate.value);
				accountResidence.setAddTime(new Date());
				this.accountResidence.add("addAccountResidence", accountResidence);
			}
		}
		
		if (regions != null)
		{
			for(int i = 0; i < regions.length; i++)
			{
				AccountResidenceEntity accountResidence = new AccountResidenceEntity();
				accountResidence.setAccountID(this.id);
				accountResidence.setResidenceID(regions[i]);
				accountResidence.setResidenceIDType(AccountResidenceEntity.ResidenceIDTypeEnum.Region.value);
				accountResidence.setAddTime(new Date());
				this.accountResidence.add("addAccountResidence", accountResidence);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountResidenceEntity> findResidencesByAccount()
	{
		return accountResidence.select("findAccountResidenceList",  new UniqueIdObject(this.id));
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountResidenceEntity> findRegionsByAccount()
	{
		return accountResidence.select("findAccountRegionList",  new UniqueIdObject(this.id));
	}
	
	public Integer getCountHouse() {
		return countHouse;
	}


	public Integer getCountHouseInteraction() {
		return countHouseInteraction;
	}


	public Integer getCountHouseMono() {
		return countHouseMono;
	}
	
	public int resetPassword()
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", this.id);
		map.put("password", "111111");
		return account.update("updateAccountPassword", map);
	}
	
	public int resetPassword(String oldPwd, String newPwd)
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", this.id);
		map.put("password", newPwd);
		map.put("oldPwd", oldPwd);
		return account.update("updateAccountPassword", map);
	}
	
	public int revoke()
	{
		AccountRevokeEntity revoke = new AccountRevokeEntity();
		revoke.accountID = this.id;
		revoke.status = 0;
		revoke.addTime = new Date(); 
		return accountRevoke.add("addAccountRevoke", revoke);
	}
	
	public String getTypeLabel()
	{
		if (this.type == null)
		{
			return "";
		}
		else if (this.type.equals(AccountEntity.TypeEnum.Internal.value))
		{
			return "内部";
		}
		else if (this.type.equals(AccountEntity.TypeEnum.External.value))
		{
			return "外部";
		}
		else
		{
			return "内部 + 外部";
		}
		
	}
	
	public String getAddTimeLabel()
	{
		if (this.addTime == null)
		{
			return "";
		}
		else
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return df.format(this.addTime);
		}
		
	}
	
	public String getRevokeTimeLabel()
	{
		if (this.status == null || this.status == 1)
		{
			return "";
		}
		else
		{
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			map.put("accountStatus", 0);
			map.put("accountID", this.id);
			map.put("status", 1);
			List<AccountRevokeEntity> revokeList = (List<AccountRevokeEntity>)accountRevoke.select("findAccountRevokeList", map);
			if (revokeList.size() > 0)
			{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				return df.format(revokeList.get(0).getAddTime());
			}
			else
			{
				return "";
			}
		}
		
	}
	
}