/**
 * Created on 2012-9-20
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.housemart.dao.entities.HouseInteractionExtEntity;
import org.housemart.dao.entities.HouseInteractionTransferExtEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.service.HouseService;
import org.housemart.web.beans.AjaxResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author kenneth.chen
 */
@Controller
public class HouseInteractionController extends BaseController {
	
	Log log = LogFactory.getLog(this.getClass());
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao accountDao;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao houseInteractionDao;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao houseInteractionTransferDao;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao houseDao;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao privilegeDao;
	
	@Autowired
	HouseService houseService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "interactionList.controller", method = RequestMethod.GET)
	public String interactionList(Model model,
			@RequestParam(value="filter_all", required=false) Integer filter_all,
			@RequestParam(value="filter_agent_id", required=false) Integer filter_agent_id
		) {
		
		List<HouseInteractionExtEntity> list = null;
		List<HouseInteractionExtEntity> agents = null;
		
		Map<Object, Object> filter = new HashMap<Object, Object>();
		filter.put("show_all", filter_all == null ? false : true);
		filter.put("agent_id", filter_agent_id == null ? -1 : filter_agent_id);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		if (!(Boolean)filter.get("show_all"))
		{
			map.put("currentTime", new Date());
		}
		else
		{
			map.put("accountStatus", "0, 1");
		}
		
		agents = (List<HouseInteractionExtEntity>)houseInteractionDao.select("findHouseInteractionAgents", map);
		
		if (filter_agent_id != null)
		{
			map.put("agentID", filter_agent_id);
		}
		
		list = (List<HouseInteractionExtEntity>)houseInteractionDao.select("findHouseInteractionList", map);
			
		model.addAttribute("list", list);
		model.addAttribute("agents", agents);
		model.addAttribute("filter", filter);
		
		return "house/interactionList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "interactionTransferList.controller", method = RequestMethod.GET)
	public String interactionTransferList(Model model) {
		
		List<HouseInteractionTransferExtEntity> list = null;
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("status", 1);
		list = (List<HouseInteractionTransferExtEntity>)houseInteractionTransferDao.select("findHouseInteractionTransferList", map);
			
		model.addAttribute("list", list);
		
		return "house/interactionTransferList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/interactionTransfer.controller", method = RequestMethod.GET)
	public ModelAndView interactionTransfer(Model model,
		@RequestParam(value="fromIds") String fromIds,
		@RequestParam(value="toBrokerId") Integer toBrokerId,
		@RequestParam(value="notes", required=false) String notes
		) {
		
		AjaxResultBean result = new AjaxResultBean();
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("idIn", fromIds);
		map.put("accountStatus", "0, 1");
		List<HouseInteractionExtEntity> interactions = (List<HouseInteractionExtEntity>)houseInteractionDao.select("findHouseInteractionList", map);
		
		for(HouseInteractionExtEntity interaction: interactions)
		{
			int houseId = interaction.getHouseID();
			int fromBrokerId = interaction.getAgentID();
			
			houseService.doInteractionTransfer(houseId, fromBrokerId, toBrokerId, notes);
		}
		
		return new ModelAndView("jsonView", "json", result);
	}
	
	@RequestMapping(value = "checkInteractions.controller", method = RequestMethod.GET)
	public ModelAndView checkInteractions(Model model) {
		
		houseService.checkInteractions();
		AjaxResultBean result = new AjaxResultBean();
		return new ModelAndView("jsonView", "json", result);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/interactionCancel.controller", method = RequestMethod.GET)
	public ModelAndView interactionCancel(Model model,
		@RequestParam(value="id") Integer id
		) {
		
		AjaxResultBean result = new AjaxResultBean();
		
		HouseInteractionExtEntity interaction = (HouseInteractionExtEntity)houseInteractionDao.load("loadHouseInteractionById", id);
		
		if (interaction != null)
		{
			if (interaction.getEndDate().after(new Date()))
			{
				interaction.setEndDate(new Date());
				houseInteractionDao.update("updateHouseInteraction", interaction);
			}
		}
		
		return new ModelAndView("jsonView", "json", result);
	}
}
