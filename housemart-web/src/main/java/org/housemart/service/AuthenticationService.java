package org.housemart.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.housemart.dao.entities.AccountEntity;
import org.housemart.dao.entities.AccountResidenceEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.dao.generic.UniqueIdObject;
import org.housemart.web.beans.HouseMartToken;
import org.housemart.web.context.HouseMartContext;
import org.housemart.web.context.SpringContextHolder;


public class AuthenticationService {

	@SuppressWarnings("rawtypes")
	private static final GenericDao accountDao = SpringContextHolder.getBean("accountDao"); 
	
	@SuppressWarnings("rawtypes")
	private GenericDao accountResidenceDao = SpringContextHolder.getBean("accountResidenceDao");
	
	public HouseMartToken login(String userName, String password){
		
		//普通用户
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginName", userName);
		map.put("password", password);
		@SuppressWarnings("unchecked")
		List<AccountEntity> result = (List<AccountEntity>)accountDao.select("loadAccountByNameAndPassword", map);
		if(!CollectionUtils.isEmpty(result)){
			HouseMartToken token = new HouseMartToken();
			token.setLoggin(true);
			token.setTokenStr(generateStr(result.get(0).getId().toString(), password));
			
			HouseMartContext.setType(result.get(0).getType());
			
			return token;
		}	
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public boolean isLoggin(String input){
		if(!StringUtils.isEmpty(input)){
			String[] arr = input.split("_");
			if(arr != null && arr.length == 3){
				Date then = new Date(Long.parseLong(arr[2] + 3600 * 24 * 7));
				if(then.after(new Date())){
					String original = StringUtils.EMPTY;
					for(int i = 0; i < arr[0].length(); i += 2){
						
						char c1 = arr[0].charAt(i);
						char c2 = arr[0].charAt(i + 1);
						int targetValue = Integer.parseInt(String.valueOf(c1) + String.valueOf(c2), 16);
						original += String.valueOf((char)targetValue);						
					}
					
					String password = StringUtils.EMPTY;
					for(int i = 0; i < arr[1].length(); i += 2){
						
						char c1 = arr[1].charAt(i);
						char c2 = arr[1].charAt(i + 1);
						int targetValue = Integer.parseInt(String.valueOf(c1) + String.valueOf(c2), 16);
						password += String.valueOf((char)targetValue);						
					}
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", original);
					map.put("password", password);
					AccountEntity result = (AccountEntity)accountDao.load("loadAccountByIdAndPassword", map); 
					if(result != null){
						HouseMartContext.setCurrentUserId(Integer.parseInt(original));
						HouseMartContext.setManagerId(result.getManagerID());
						HouseMartContext.setType(result.getType());
						
						Set<Integer> regionMangers = new HashSet<Integer>();
						Set<Integer> residenceList = new HashSet<Integer>();
						Set<Integer> regionList = new HashSet<Integer>();
						Set<Integer> plateList = new HashSet<Integer>();
						//小区
						List<AccountResidenceEntity> list = (List<AccountResidenceEntity>)accountResidenceDao.select("findAccountResidenceList", new UniqueIdObject(Integer.parseInt(original)));
						if(!CollectionUtils.isEmpty(list)){
													
							List<Integer> tempResidenceList = 		
							(List<Integer>)CollectionUtils.collect(list, new Transformer(){
								@Override
								public Object transform(Object input) {
									return ((AccountResidenceEntity)input).getResidenceID();
								}								
							});
							
							if(tempResidenceList != null) {
								residenceList = new HashSet<Integer>(tempResidenceList);
							}
							
							HouseMartContext.setResidenceIds(residenceList);
							
							for(AccountResidenceEntity item: list){
								Map<String, Object> managerMap = new HashMap<String, Object>();
								managerMap.put("id", item.getResidenceID());
								List<AccountResidenceEntity> managerEntity = (List<AccountResidenceEntity>) accountResidenceDao.select("loadResidenceManager", managerMap);
								if(CollectionUtils.isNotEmpty(managerEntity)){
									for(AccountResidenceEntity item1: managerEntity){
										regionMangers.add(item1.getAccountID());
									}									
								}
							}
						}
						if(!CollectionUtils.isEmpty(regionMangers)){
							HouseMartContext.setRegionManagerIds(regionMangers);
						}
						
						list = (List<AccountResidenceEntity>)accountResidenceDao.select("findAccountRegionList", new UniqueIdObject(Integer.parseInt(original)));
						if (!list.isEmpty())
						{
							for (int i = 0; i < list.size(); i++)
							{
								AccountResidenceEntity entity = list.get(i);
								if (entity.getResidenceIDType().equals(AccountResidenceEntity.ResidenceIDTypeEnum.Region.value))
								{
									regionList.add(entity.getResidenceID());
								}
								else
								{
									plateList.add(entity.getResidenceID());
								}
							}
						}
						HouseMartContext.setRegionIds(regionList);
						HouseMartContext.setPlateIds(plateList);
						
						return true;
					}

				};
			}
		}
		
		return false;
	}
	
	
	private String generateStr(String userName, String password){
		
		if(!StringUtils.isEmpty(userName)){
			
			byte[] usernameArr = (userName).getBytes();
			String result = StringUtils.EMPTY;
			for(int i = 0; i < usernameArr.length; i ++){				
				int tergetNum = (int)usernameArr[i];
				String item = Integer.toHexString(tergetNum);
				result += item.length() == 1 ? '0' + item: item;
			}
			if(StringUtils.isNotEmpty(password)){
				byte[] passArr = password.getBytes();
				for(int i = 0; i < passArr.length; i ++){				
					int tergetNum = (int)passArr[i];
					String item = Integer.toHexString(tergetNum);
					if(i == 0){
						result += "_" + ((item.length() == 1) ? '0' + item: item);
					}else {
						result += item.length() == 1 ? '0' + item: item;
					}					
				}
			}
			result += "_" + new Date().getTime();
			return result;
		}
		return StringUtils.EMPTY;
	}
	
	
}
