/**
 * Created on 2012-10-21
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.housemart.dao.entities.RegionEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.web.beans.AjaxResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author pqin
 */
@Controller
public class HouseContactController extends BaseController {
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao houseContactDao;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/getContactListByHouseId.controller")
	public ModelAndView getHouseContactList(Integer houseId) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("houseId", houseId);
		List<RegionEntity> list = houseContactDao.select("findHouseContactListByHouseId", map);
		AjaxResultBean result = new AjaxResultBean();
		result.setBizData(list);
		return new ModelAndView("jsonView", "json", result);
	}
}
