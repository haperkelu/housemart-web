/**
 * Created on 2012-11-25
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.housemart.dao.entities.HouseProcessEntity;
import org.housemart.dao.entities.RegionEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.web.beans.AjaxResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class HouseProcessController {
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao houseProcessDao;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/getProcessListByHouseId.controller")
	public ModelAndView getHouseProcessList(Integer houseId) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("houseId", houseId);
		List<RegionEntity> list = houseProcessDao.select("findHouseProcessListByHouseId", map);
		AjaxResultBean result = new AjaxResultBean();
		result.setBizData(list);
		return new ModelAndView("jsonView", "json", result);
	}

	@RequestMapping(value = "ajax/addHouseProcess.controller")
	public ModelAndView addHouseProcess(Integer houseId, String memo) {
		HouseProcessEntity process = new HouseProcessEntity();
		process.setHouseId(houseId);
		process.setMemo(memo);
		process.setAddTime(Calendar.getInstance().getTime());
		houseProcessDao.add("addHouseProcess", process);
		AjaxResultBean result = new AjaxResultBean();
		result.setBizData(process);
		return new ModelAndView("jsonView", "json", result);
	}
}
