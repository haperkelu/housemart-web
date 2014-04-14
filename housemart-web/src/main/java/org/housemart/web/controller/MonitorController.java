/**   
* @Title: MonitorController.java 
* @Package org.housemart.web.controller 
* @Description: TODO
* @author Pie.Li   
* @date 2014-4-7 下午10:21:40 
* @version V1.0   
*/
package org.housemart.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.housemart.dao.entities.UserAccessEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.dao.generic.PaginateObject;
import org.housemart.framework.web.context.SpringContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author Pie.Li
 *
 */
@Controller
public class MonitorController {

	@SuppressWarnings("rawtypes")
	private GenericDao userAccessDao = SpringContextHolder.getBean("userAccessDao");
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "monitor/list.controller")
	public String addToFavoriteByHouseAndUser(Model model, Integer page, Integer pageSize) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		PaginateObject		paginateObject	=	userAccessDao.paginate("findUserAccessList", page, pageSize, map);
		List<Object> list = paginateObject.getResult();
		if(!CollectionUtils.isEmpty(list)){
			model.addAttribute("list", list);
			model.addAttribute("paginateObject", paginateObject);
		}
		return "monitor/list";			
		
	}
	
	
}
