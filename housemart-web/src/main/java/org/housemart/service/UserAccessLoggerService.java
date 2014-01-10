/**   
* @Title: UserAccessService.java 
* @Package org.housemart.service 
* @Description: TODO
* @author Pie.Li   
* @date 2014-1-6 下午8:21:59 
* @version V1.0   
*/
package org.housemart.service;

import java.util.Date;
import java.util.List;

import org.housemart.dao.entities.UserAccessEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.rpc.stubs.log.UserAccessDTO;
import org.housemart.rpc.stubs.log.UserAccessLoggerServiceStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Pie.Li
 *
 */
public class UserAccessLoggerService implements UserAccessLoggerServiceStub {

	private static final Logger logger = LoggerFactory.getLogger(UserAccessLoggerService.class);	
	@SuppressWarnings("rawtypes")
	private GenericDao userAccessDao;
	
	@Override
	public void access(String tag, int userId, String URL, String text) {
		
		try {
			UserAccessEntity entity = new UserAccessEntity();
			entity.setBizTag(tag);
			entity.setUserId(userId);
			entity.setUrl(URL);
			entity.setAccessText(text);
			userAccessDao.add("addUserAccessLog", entity);
		} catch (Exception e) {
			logger.error("User Access Error", e);
		}
		
	}

	/**
	 * @param userAccessDao the userAccessDao to set
	 */
	public void setUserAccessDao(@SuppressWarnings("rawtypes") GenericDao userAccessDao) {
		this.userAccessDao = userAccessDao;
	}

	@Override
	public List<UserAccessDTO> retrieveList(String tag, Date laterThan,
			String urlQuery) {
		// TODO Auto-generated method stub
		return null;
	}

}
