/** 
* @Title: ReportController.java
* @Package org.housemart.web.controller
* @Description: TODO
* @author Pie.Li
* @date 2013-4-16 下午4:04:58
* @version V1.0 
*/
package org.housemart.web.controller;

import java.util.HashMap;
import java.util.Map;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.dao.generic.PaginateObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: ReportController
 * @Description: TODO
 * @date 2013-4-16 下午4:04:58
 * 
 */
@Controller
public class ReportController {

	
	@Autowired
	GenericDao houseLeadsDao;
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "appUserPublishedHouse.controller")
	public String residecenDetail(Model model, @RequestParam("page") int pageNo, @RequestParam("pageSize") int pageSize){
		
		Map<String, Object> map = new HashMap<String, Object>();		
		PaginateObject obj = (PaginateObject) houseLeadsDao.paginate("findAllList", pageNo, pageSize, map);
		model.addAttribute("paginateObject", obj);
		return "report/appUserPublishedHouse";
		
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "appUserFeedback.controller")
	public String residecenDetail1(Model model, @RequestParam("page") int pageNo, @RequestParam("pageSize") int pageSize){
		
		Map<String, Object> map = new HashMap<String, Object>();		
		PaginateObject obj = houseLeadsDao.paginate("findAllFeedBackList", pageNo, pageSize, map);
		model.addAttribute("paginateObject", obj);
		return "report/appUserPublishedFeedback";
		
	}	
	
}
