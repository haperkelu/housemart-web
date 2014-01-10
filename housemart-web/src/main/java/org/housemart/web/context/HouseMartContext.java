package org.housemart.web.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

public class HouseMartContext {

	private static final ThreadLocal<Map<String, Object>> threadSession = new ThreadLocal<Map<String, Object>>(){
	    protected synchronized Map<String, Object> initialValue() {
	        return new HashMap<String, Object>();
	      }
	    }; 
	/**
	 * 
	 * @return
	 * 
	 */
	public static int getCurrentUserId(){		
		Object obj = threadSession.get().get("userId");
		if(obj == null){
			return -1;
		}
		return ((Integer)obj).intValue();
	}
	
	/**
	 * 
	 * @param userId
	 */
	public static void setCurrentUserId(int userId){
		threadSession.get().put("userId", userId);
	}
	
	public static void setManagerId(int mangerId){
		threadSession.get().put("managerId", mangerId);
	}
	
	public static void setRegionManagerIds(Set<Integer> mangerIds){
		threadSession.get().put("regionManagers", mangerIds);
	}
	
	public static void setResidenceIds(Set<Integer> residenceIds){
		threadSession.get().put("residenceIds", residenceIds);
	}
	public static void setRegionIds(Set<Integer> regionIds){
		threadSession.get().put("regionIds", regionIds);
	}
	public static void setPlateIds(Set<Integer> plateIds){
		threadSession.get().put("plateIds", plateIds);
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<Integer> getRegionMangerIds(){
		Object obj = threadSession.get().get("regionManagers");
		if(obj == null){
			return null;
		}
		return ((Set<Integer>)obj);
	}
	
	public static Set<Integer> getResidenceIds(){
		Object obj = threadSession.get().get("residenceIds");
		if(obj == null){
			return null;
		}
		return ((Set<Integer>)obj);
	}
	public static Set<Integer> getRegionIds(){
		Object obj = threadSession.get().get("regionIds");
		if(obj == null){
			return null;
		}
		return ((Set<Integer>)obj);
	}
	public static Set<Integer> getPlateIds(){
		Object obj = threadSession.get().get("plateIds");
		if(obj == null){
			return null;
		}
		return ((Set<Integer>)obj);
	}
	/**
	 * 
	 * @return
	 */
	public static int getMangerId(){
		Object obj = threadSession.get().get("managerId");
		if(obj == null){
			return 0;
		}
		return ((Integer)obj).intValue();
	}
	
	public static boolean isManager(){
		Set<Integer> mangerIds = getRegionMangerIds();
		if(!CollectionUtils.isEmpty(mangerIds)){
			if(mangerIds.contains(getCurrentUserId())){
				return true;
			}
		}
		return false;
	}
	
	public static int getType(){
		Object obj = threadSession.get().get("type");
		if(obj == null){
			return 0;
		}
		return ((Integer)obj).intValue();
	}
	
	public static void setType(int type){
		threadSession.get().put("type", type);
	}
	
	public static String getSysMsg(){
		Object obj = threadSession.get().get("sysMsg");
		if(obj == null){
			return "";
		}
		else
		{
			threadSession.get().put("sysMsg", "");
		}
		return (String)obj;
	}
	
	public static void setSysMsg(String sysMsg){
		threadSession.get().put("sysMsg", sysMsg);
	}
	
}
