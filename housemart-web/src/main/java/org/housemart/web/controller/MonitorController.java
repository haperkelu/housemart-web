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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author Pie.Li
 *
 */
@Controller
public class MonitorController {

	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao userAccessDao;
	
	@RequestMapping(value = "monitor/list.controller")
	public String addToFavoriteByHouseAndUser(Model model, Integer page, Integer pageSize) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tag", new String[] {"Page Request Context", "Page Load Performance"});
		PaginateObject		paginateObject	=	userAccessDao.paginate("findUserAccessList", page, pageSize, map, "findUserAccessListCount");
		List<Object> list = paginateObject.getResult();
		if(!CollectionUtils.isEmpty(list)){
			model.addAttribute("list", list);
			model.addAttribute("paginateObject", paginateObject);
		}
		return "monitor/list";			
		
	}
	
	@RequestMapping(value = "monitor/item.controller")
	public String showItem(Model model, @RequestParam("file")Integer id) {
		
		UserAccessEntity item = (UserAccessEntity)userAccessDao.load("loadRecord", id);
		model.addAttribute("record", item);
		return "monitor/item";			
		
	}	
	
	
}
