/**
 * Created on 2012-10-28
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.housemart.dao.entities.HouseFavoriteEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.web.beans.AjaxResultBean;
import org.housemart.web.context.HouseMartContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author pqin
 */
@Controller
public class HouseFavoriteController extends BaseController {
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao houseFavoriteDao;

	@RequestMapping(value = "ajax/addToFavorite.controller")
	public ModelAndView addToFavoriteByHouseAndUser(Integer houseId, Integer userId) {
		HouseFavoriteEntity fav = new HouseFavoriteEntity();
		fav.setHouseId(houseId);
		fav.setUserId(HouseMartContext.getCurrentUserId());
		fav.setAddTime(Calendar.getInstance().getTime());
		Integer favId = houseFavoriteDao.add("addHouseFavorite", fav);
		AjaxResultBean result = new AjaxResultBean();
		result.setBizData(favId);
		return new ModelAndView("jsonView", "json", result);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/removeFavorite.controller")
	public ModelAndView removeFavoriteByHouseAndUser(Integer houseId, Integer userId) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("houseId", houseId);
		map.put("userId", HouseMartContext.getCurrentUserId());
		List<HouseFavoriteEntity> favoriteList = houseFavoriteDao
				.select("findHouseFavoriteListByHouseIdAndUserId", map);
		for (HouseFavoriteEntity fav : favoriteList) {
			houseFavoriteDao.delete("deleteHouseFavorite", fav.getId());
		}
		AjaxResultBean result = new AjaxResultBean();
		return new ModelAndView("jsonView", "json", result);
	}
}
