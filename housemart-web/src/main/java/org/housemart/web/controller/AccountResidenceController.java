/**
 * Created on 2012-9-20
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.housemart.dao.entities.AccountExtEntity;
import org.housemart.dao.entities.ResidenceExtEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.dao.generic.UniqueIdObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author kenneth.chen
 */
@Controller
public class AccountResidenceController extends BaseController {
	
	Log log = LogFactory.getLog(this.getClass());
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao residenceDao;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao accountDao;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "accountResidenceList.controller", method = RequestMethod.GET)
	public String accountResidenceList(Model model, 
			@RequestParam(value="regionId", required=false) Integer regionId,
			@RequestParam(value="plateId", required=false) Integer plateId)
	{
		
		List<ResidenceExtEntity> list = null;
		if(plateId == null)
		{
			plateId = 380;
		}
		if(regionId == null)
		{
			regionId = 359;
		}
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", plateId);
		param.put("status", 1);
		list = residenceDao.select("findResidenceList", param);
		
		model.addAttribute("list", list);
		model.addAttribute("plateId", plateId);
		model.addAttribute("regionId", regionId);
		return "account/residenceList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "setResidenceManager.controller", method = RequestMethod.GET)
	public String setResidenceManager(Model model, 
			@RequestParam(value="id", required=false) Integer id)
	{
		ResidenceExtEntity residence = (ResidenceExtEntity)residenceDao.load("loadResidence", id);
		
		List<AccountExtEntity> accountList = null;
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("positionType", "'区域经理'");
		accountList = accountDao.select("findAccountList", map);
		
		model.addAttribute("residence", residence);
		model.addAttribute("accountList", accountList);
		return "account/residenceManager";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "setResidenceAgent.controller", method = RequestMethod.GET)
	public String setResidenceAgent(Model model, 
			@RequestParam(value="id", required=false) Integer id)
	{
		ResidenceExtEntity residence = (ResidenceExtEntity)residenceDao.load("loadResidence", id);
		
		List<AccountExtEntity> accountList = null;
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("positionType", "'初级经纪人', '经纪人'");
		accountList = accountDao.select("findAccountList", map);
		
		model.addAttribute("residence", residence);
		model.addAttribute("accountList", accountList);
		return "account/residenceAgent";
	}
	
	@RequestMapping(value = "saveResidenceAccounts.controller", method = RequestMethod.POST)
	public String saveResidenceAccounts(Model model, 
			@RequestParam(value="residenceID", required=true) Integer residenceID,
			@RequestParam(value="positionType", required=true) String positionType,
			@RequestParam(value="accountID", required=false) Integer[] accountID
			)
	{
		ResidenceExtEntity residence = (ResidenceExtEntity)residenceDao.load("loadResidence", residenceID);
		
		if (positionType.equals("区域经理"))
		{		
			residence.setManagers(accountID);
		}
		else
		{
			residence.setAgents(accountID);
		}
		
		return "redirect:/accountResidenceList.controller?plateId=" + residence.getRegionId();
	}
}
