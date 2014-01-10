/**
http://bbs.fudan.edu.cn/bbs/tcon?new=1&bid=217&f=3085828754 * Created on 2012-9-20
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.housemart.dao.entities.AccountEntity;
import org.housemart.dao.entities.AccountExtEntity;
import org.housemart.dao.entities.AccountResidenceEntity;
import org.housemart.dao.entities.AccountRevokeExtEntity;
import org.housemart.dao.entities.AccountRole;
import org.housemart.dao.entities.HousePicEntity;
import org.housemart.dao.entities.ResidenceExtEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.pic.api.HessianPicServiceInterface;
import org.housemart.pic.api.PicSaveResult;
import org.housemart.service.enums.PositionType;
import org.housemart.web.views.AccountExcelView;
import org.housemart.web.views.AccountHouseExcelView;
import org.housemart.web.beans.AjaxResultBean;
import org.housemart.web.context.HouseMartContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 
 * @author kenneth.chen
 */
@Controller
public class AccountController extends BaseController {
	
	Log log = LogFactory.getLog(this.getClass());
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao accountDao;
	
	@Autowired
	GenericDao houseDao;
	
	@Autowired
	GenericDao accountRevokeDao;
	
	@Autowired
	GenericDao privilegeDao;
	
	@Autowired
	GenericDao residenceDao;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "accountList.controller", method = RequestMethod.GET)
	public String accountList(Model model,
			@RequestParam(value="keyword", required=false) String keyword,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize
			) {
		keyword = (keyword == null ? "" : keyword.trim());
		page = (page == null ? 0 : page);
		pageSize = (pageSize == null ? 50 : pageSize);
		
		List<AccountExtEntity> list = null;
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("skip", page * pageSize);
		map.put("count", pageSize);
		map.put("orderByClause", "acc.ID DESC");
		
		if (keyword.length() > 0)
		{
			map.put("searchKeyword", keyword);
		}
		 
		list = accountDao.select("findAccountList", map);
		Integer totalCount = accountDao.count("countAccountList", map);
		
		model.addAttribute("list", list);
		
		Map<Object, Object> pager = new HashMap<Object, Object>();
		pager.put("totalCount", totalCount);
		pager.put("page", page);
		pager.put("pageSize", pageSize);
		
		model.addAttribute("pager", pager);
		
		model.addAttribute("keyword", keyword);
		
		return "account/accountList";
	}
	
	@RequestMapping(value = "accountExport.controller", method = RequestMethod.GET)
	public ModelAndView accountExport(ModelMap model) {
		
		List<AccountExtEntity> list = null;
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("orderByClause", "acc.ID DESC");
		map.put("status", "0, 1");
		 
		list = (List<AccountExtEntity>)accountDao.select("findAccountList", map);

		model.addAttribute("list", list);
		
		return new ModelAndView(new AccountExcelView(), model);
	}
	
	@RequestMapping(value = "accountHouseExport.controller", method = RequestMethod.GET)
	public ModelAndView accountHouseExport(ModelMap model,
			@RequestParam(value="houseType", required=false) String houseType,
			@RequestParam(value="showAll", required=false) Integer showAll
			) {
		
		houseType = (houseType == null ? "sale" : houseType);
		showAll = (showAll == null ? 0 : showAll);
	
		List<Map<Object, Object>> list = null;
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("orderByClause", "acc.ID DESC");
		map.put("status", showAll == 1 ? "0, 1" : "1");
		map.put("type", AccountEntity.TypeEnum.External.value + "," + AccountEntity.TypeEnum.Combine.value);
		
		if (houseType.equals("rent"))
		{
			map.put("forRent", 1);
		}
		else
		{
			houseType = "sale";
			map.put("forSale", 1);
		}
		
		list = (List<Map<Object, Object>>)houseDao.select("findAccountHouseSummary", map);
		
		Map<String, Integer> brokerCountMap = new HashMap<String, Integer>();
		Map<String, String> brokersMap = new HashMap<String, String>();
		
		for (Map<Object, Object> data : list)
		{
			if (!brokerCountMap.containsKey(data.get("residenceID").toString()))
			{
				ResidenceExtEntity residence = (ResidenceExtEntity)residenceDao.load("loadResidence", (Integer)data.get("residenceID"));
				if (residence != null)
				{
					residence.updateBrokerList();
				
					brokerCountMap.put(data.get("residenceID").toString(), residence.getBrokerCount());
					brokersMap.put(data.get("residenceID").toString(), residence.getBrokerList());
				}
				else
				{
					brokerCountMap.put(data.get("residenceID").toString(), 0);
					brokersMap.put(data.get("residenceID").toString(), "");
				}
			}
			
			data.put("brokerCount", brokerCountMap.get(data.get("residenceID").toString()));
			data.put("brokers", brokersMap.get(data.get("residenceID").toString()));
		}
		
		model.addAttribute("list", list);
		model.addAttribute("houseType", houseType);
		
		return new ModelAndView(new AccountHouseExcelView(), model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "accountEdit.controller", method = RequestMethod.GET)
	public String accountEdit(Model model, 
			@RequestParam(value="id", required=false) Integer id)
	{
		
		AccountExtEntity account = null;
		List<AccountResidenceEntity> accountResidences = null;
		List<AccountResidenceEntity> accountRegions = new ArrayList<AccountResidenceEntity>();
		List<AccountResidenceEntity> accountPlates = new ArrayList<AccountResidenceEntity>();
		
		if (id == null)
		{
			account = new AccountExtEntity();
		}
		else
		{
			account = (AccountExtEntity)this.accountDao.load("loadEditAccountById", id);
			accountResidences = account.findResidencesByAccount();
			
			for(int i = 0; i < accountResidences.size(); i++)
			{
				accountResidences.get(i).updateBrokerList();
			}
			
			List<AccountResidenceEntity> accountAllRegions = account.findRegionsByAccount();
			
			if (!accountAllRegions.isEmpty())
			{
				for (int i = 0; i < accountAllRegions.size(); i++)
				{
					AccountResidenceEntity entity = accountAllRegions.get(i);
					if (entity.getResidenceIDType().equals(AccountResidenceEntity.ResidenceIDTypeEnum.Region.value))
					{
						accountRegions.add(entity);
					}
					else
					{
						accountPlates.add(entity);
					}
				}
			}
		}
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("positionType", "'区域经理'");
		List<AccountExtEntity> managers = accountDao.select("findAccountList", map);
		
		model.addAttribute("account", account);
		model.addAttribute("managers", managers);
		model.addAttribute("accountResidences", accountResidences);
		model.addAttribute("accountRegions", accountRegions);
		model.addAttribute("accountPlates", accountPlates);
		
		return "account/accountEdit";
	}

	@RequestMapping(value = "accountSave.controller", method = RequestMethod.POST)
	public String accountSave(Model model, 
			@RequestParam(value="id", required=true) Integer id,
			@RequestParam(value="loginName", required=true) String loginName,
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="gender", required=true) Integer gender,
			@RequestParam(value="identityID", required=true) String identityID,
			@RequestParam(value="contactInfo1", required=true) String contactInfo1,
			@RequestParam(value="contactInfo2", required=true) String contactInfo2,
			@RequestParam(value="positionType", required=true) String positionType,
			@RequestParam(value="managerID", required=true) Integer managerID,
			@RequestParam(value="residenceID", required=false) Integer[] residenceID,
			@RequestParam(value="plateID", required=false) Integer[] plateID,
			@RequestParam(value="regionID", required=false) Integer[] regionID,
			@RequestParam(value="type", required=true) Integer type,
			@RequestParam(value="company", required=false) String company,
			@RequestParam(value="companyAddress", required=false) String companyAddress,
			@RequestParam(value="note", required=false) String note,
			@RequestParam(value="picFile", required=false) MultipartFile picFile,
			@RequestParam(value="idFile", required=false) MultipartFile idFile,
			@RequestParam(value="cardFile", required=false) MultipartFile cardFile,
			@RequestParam(value="picLocked", required=false) Integer picLocked,
			@RequestParam(value="idLocked", required=false) Integer idLocked,
			@RequestParam(value="cardLocked", required=false) Integer cardLocked,
			@RequestParam(value="weixin", required=false) String weixin,
			@RequestParam(value="weixinJoined", required=false) Integer weixinJoined
			)
	{
		
		AccountExtEntity account = null;
		
		loginName = loginName.trim();
		name = name.trim();
		identityID = identityID.trim();
		
		//检查用户名
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("loginName", loginName);
		if (id > -1)
		{
			map.put("idNotIn", id);
		}
		List<AccountExtEntity> duplist = (List<AccountExtEntity>)accountDao.select("findAccountList", map);
		
		
		if (duplist.size() > 0)
		{
			HouseMartContext.setSysMsg("用户名已存在");
			return "redirect:/accountList.controller";
		}
		
		//检查身份证
		map = new HashMap<Object, Object>();
		map.put("identityID", identityID);
		if (id > -1)
		{
			map.put("idNotIn", id);
		}
		duplist = (List<AccountExtEntity>)accountDao.select("findAccountList", map);
		
		if (duplist.size() > 0)
		{
			HouseMartContext.setSysMsg("身份证号已存在");
			return "redirect:/accountList.controller";
		}
		
		if (id == -1)
		{	
			account = new AccountExtEntity();
		}
		else
		{
			account = (AccountExtEntity)this.accountDao.load("loadEditAccountById", id);
		}
		
		if (id == -1)
		{
			account.setLoginName(loginName);
			account.setPassword(AccountExtEntity.hashPassword(""));
			account.setAddTime(new Date());
			account.setSource(AccountEntity.SourceEnum.ERP.value);
			account.setStatus(AccountEntity.StatusEnum.Valid.value);
		}
		else if (account.getStatus().equals(AccountEntity.StatusEnum.Applied.value) ||
				account.getStatus().equals(AccountEntity.StatusEnum.Pending.value) )
		{
			account.setLoginName(loginName);
		}
		
		account.setType(type);
		account.setCompany(company);
		account.setCompanyAddress(companyAddress);
		account.setNote(note);
		account.setName(name);
		account.setGender(gender);
		account.setIdentityID(identityID);
		account.setContactInfo1(contactInfo1);
		account.setContactInfo2(contactInfo2);
		account.setPositionType(positionType);
		
		account.setPicLocked(picLocked == null ? 0 : 1);
		account.setIdLocked(idLocked == null ? 0 : 1);
		account.setCardLocked(cardLocked == null ? 0 : 1);
		
		account.setWeixin(weixin);
		account.setWeixinJoined(weixinJoined == null ? 0 : 1);
		
		if (positionType.equals("区域经理"))
		{
			account.setManagerID(0);
		}
		else
		{
			account.setManagerID(managerID);
		}
		
		account.setUpdateTime(new Date());
		
		int result = 0;
		if (id == -1)
		{
			result = this.accountDao.add("addAccount", account);
			account.setId(result);
			account = (AccountExtEntity)this.accountDao.load("loadAccountById", result);
		}
		else
		{
			result = this.accountDao.update("updateAccount", account);
		}
		
		if (result >= 0)
		{
			account.setResidences(residenceID, plateID, regionID);
			AccountRole accountRole = new AccountRole();
			accountRole.setAccountId(account.getId());
			if(positionType != null){
				
				if(positionType.equalsIgnoreCase(PositionType.JUNIOR_BROKER)){
					accountRole.setRoleId(2);
				}
				
				if(positionType.equalsIgnoreCase(PositionType.BROKER)){
					accountRole.setRoleId(3);
				}
				
				if(positionType.equalsIgnoreCase(PositionType.MANAGER)){
					accountRole.setRoleId(4);
				}
				
				
			}
			
			if (picFile != null && picFile.getSize() > 0)
			{
				String fileName = account.getId().toString();
				try
				{
					String url = this.uploadAccountPic(picFile, fileName);
					
					if (!url.isEmpty())
					{
						account.setPicURL(url);
						this.accountDao.update("updateAccount", account);
					}
				}
				catch(Exception ex)
				{
					
				}
			}
			
			if (idFile != null && idFile.getSize() > 0)
			{
				String fileName = account.getId().toString() + "_ID";
				try
				{
					String url = this.uploadAccountPic(idFile, fileName);
					
					if (!url.isEmpty())
					{
						account.setIdURL(url);
						this.accountDao.update("updateAccount", account);
					}
				}
				catch(Exception ex)
				{
					
				}
			}
			
			if (cardFile != null && cardFile.getSize() > 0)
			{
				String fileName = account.getId().toString() + "_Card";
				try
				{
					String url = this.uploadAccountPic(cardFile, fileName);
					
					if (!url.isEmpty())
					{
						account.setCardURL(url);
						this.accountDao.update("updateAccount", account);
					}
				}
				catch(Exception ex)
				{
					
				}
			}
			
			map = new HashMap<Object, Object>();
			map.put("ids", account.getId());
			map.put("status", 1);
			this.accountDao.update("updateAccountByMap", map);
			
			privilegeDao.deletePhysical("deleteAccountRole", accountRole);
			privilegeDao.add("addAccountRole", accountRole);
		}
		
		return "redirect:/accountList.controller";
	}
	
	@RequestMapping(value = "ajax/resetAccountPassword.controller")
	public ModelAndView resetAccountPassword(@RequestParam("id") int id){
		
		AjaxResultBean result = new AjaxResultBean();
		
		AccountExtEntity account = (AccountExtEntity)this.accountDao.load("loadAccountById", id);
		
		account.resetPassword();
		
		return new ModelAndView("jsonView", "json", result);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/closeAccount.controller")
	public ModelAndView closeAccount(@RequestParam("id") int id){
		
		AjaxResultBean result = new AjaxResultBean();
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("accountID", id);
		map.put("status", 0);
		List<AccountRevokeExtEntity> list = accountRevokeDao.select("findAccountRevokeList", map);
		
		if (list.isEmpty())
		{
			AccountExtEntity account = (AccountExtEntity)this.accountDao.load("loadAccountById", id);
			int revokeId = account.revoke();
			
			account = (AccountExtEntity)this.accountDao.load("loadAccountById", HouseMartContext.getCurrentUserId());
			if (revokeId > 0 && account.getLoginName().toLowerCase().equals("admin"))
			{
				AccountRevokeExtEntity revoke = (AccountRevokeExtEntity)accountRevokeDao.load("loadAccountRevokeById", revokeId);
				revoke.setStatus(1);
				accountRevokeDao.update("updateAccountRevoke", revoke);
				accountDao.delete("deleteAccount", revoke.getAccountID());
			}
		}
		
		return new ModelAndView("jsonView", "json", result);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "accountRevokeList.controller", method = RequestMethod.GET)
	public String accountRevokeList(Model model) {
		
		List<AccountRevokeExtEntity> list = null;
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		list = accountRevokeDao.select("findAccountRevokeList", map);
			
		model.addAttribute("list", list);
		
		return "account/accountRevokeList";
	}
	
	@RequestMapping(value = "ajax/revokeAccount.controller")
	public ModelAndView revokeAccount(@RequestParam("id") int id, @RequestParam("status") int status){
		
		AjaxResultBean result = new AjaxResultBean();
		
		AccountRevokeExtEntity revoke = (AccountRevokeExtEntity)accountRevokeDao.load("loadAccountRevokeById", id);
		revoke.setStatus(status);
		accountRevokeDao.update("updateAccountRevoke", revoke);
		if (status == 1)
		{
			accountDao.delete("deleteAccount", revoke.getAccountID());
		}
		
		return new ModelAndView("jsonView", "json", result);
	}
	
	@RequestMapping(value = "ajax/getCurrentAccount.controller")
	public ModelAndView getCurrentAccount(){
		
		AccountExtEntity account = null;
		
		int id = HouseMartContext.getCurrentUserId();
		
		if (id >= 0)
		{
			account = (AccountExtEntity)accountDao.load("loadAccountById", id);
		}
		
		return new ModelAndView("jsonView", "json", account);
	}
	
	@RequestMapping(value = "ajax/checkPassword.controller", method = RequestMethod.POST)
	public ModelAndView checkPassword(@RequestParam("password") String password){
		
		AccountExtEntity account = null;
		
		int id = HouseMartContext.getCurrentUserId();
		boolean result;
		
		if (id >= 0)
		{
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id", id);
			map.put("password", password);
			account = (AccountExtEntity)accountDao.load("loadAccountByIdAndPassword", map);
			result = (account != null);
		}
		else
		{
			result = false;
		}
		
		return new ModelAndView("jsonView", "json", result);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "myAccount.controller", method = RequestMethod.GET)
	public String myAccount(Model model)
	{
		
		AccountExtEntity account = (AccountExtEntity)this.accountDao.load("loadAccountById", HouseMartContext.getCurrentUserId());
		List<AccountResidenceEntity> accountResidences = account.findResidencesByAccount();
		model.addAttribute("account", account);
		model.addAttribute("accountResidences", accountResidences);
		
		return "account/myAccount";
	}

	@RequestMapping(value = "myAccountSave.controller", method = RequestMethod.POST)
	public String myAccountSave(Model model, 
			@RequestParam(value="gender", required=false) Integer gender,
			@RequestParam(value="contactInfo1", required=true) String contactInfo1,
			@RequestParam(value="contactInfo2", required=true) String contactInfo2,
			@RequestParam(value="picFile", required=false) MultipartFile picFile,
			@RequestParam(value="idFile", required=false) MultipartFile idFile,
			@RequestParam(value="cardFile", required=false) MultipartFile cardFile,
			@RequestParam(value="weixin", required=false) String weixin
			)
	{
		
		AccountExtEntity account = (AccountExtEntity)this.accountDao.load("loadAccountById", HouseMartContext.getCurrentUserId());
		
		if (gender != null)
		{
			account.setGender(gender);
		}
		
		account.setContactInfo1(contactInfo1);
		account.setContactInfo2(contactInfo2);
		account.setWeixin(weixin);
		account.setUpdateTime(new Date());
		
		if (picFile != null && picFile.getSize() > 0)
		{
			String fileName = account.getId().toString();
			try
			{
				String url = this.uploadAccountPic(picFile, fileName);
				
				if (!url.isEmpty())
				{
					account.setPicURL(url);
				}
			}
			catch(Exception ex)
			{
				
			}
		}
		
		if (idFile != null && idFile.getSize() > 0)
		{
			String fileName = account.getId().toString() + "_ID";
			try
			{
				String url = this.uploadAccountPic(idFile, fileName);
				
				if (!url.isEmpty())
				{
					account.setIdURL(url);
					this.accountDao.update("updateAccount", account);
				}
			}
			catch(Exception ex)
			{
				
			}
		}
		
		if (cardFile != null && cardFile.getSize() > 0)
		{
			String fileName = account.getId().toString() + "_Card";
			try
			{
				String url = this.uploadAccountPic(cardFile, fileName);
				
				if (!url.isEmpty())
				{
					account.setCardURL(url);
					this.accountDao.update("updateAccount", account);
				}
			}
			catch(Exception ex)
			{
				
			}
		}
		
		int result = this.accountDao.update("updateAccount", account);
		HouseMartContext.setSysMsg("保存成功");
		return "redirect:/myAccount.controller";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "changeMyPwd.controller", method = RequestMethod.GET)
	public String changeMyPwd(Model model)
	{		
		return "account/changeMyPwd";
	}

	@RequestMapping(value = "changeMyPwdSave.controller", method = RequestMethod.POST)
	public String changeMyPwdSave(Model model, 
			@RequestParam(value="oldPwd", required=true) String oldPwd,
			@RequestParam(value="newPwd", required=true) String newPwd
			)
	{
		
		AccountExtEntity account = (AccountExtEntity)this.accountDao.load("loadAccountById", HouseMartContext.getCurrentUserId());
		
		int result = account.resetPassword(oldPwd, newPwd);
		
		return "redirect:/changeMyPwd.controller";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/searchAccount.controller", method = RequestMethod.GET)
	public ModelAndView searchAccount(Model model,
		@RequestParam(value="searchKeyword") String searchKeyword) {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("searchKeyword", searchKeyword);
		
		List<AccountExtEntity> list = (List<AccountExtEntity>)accountDao.select("findAccountList", map);
	
		return new ModelAndView("jsonView", "json", list);
	}
	
	@RequestMapping(value = "ajax/uploadMyAccountPic.controller", method = RequestMethod.POST)
	public ModelAndView uploadMyAccountPic(Model model, 
			@RequestParam(value="type", required=true) Integer type,
			@RequestParam(value="file", required=true) MultipartFile file
			)
	{
		boolean result = true;
		
		AccountExtEntity account = (AccountExtEntity)this.accountDao.load("loadAccountById", HouseMartContext.getCurrentUserId());
		
		account.setUpdateTime(new Date());
		
		if (file != null && file.getSize() > 0)
		{
			String fileName = account.getId().toString();
			
			if (type == 2)
			{
				fileName += "_ID";
			}
			else if (type == 3)
			{
				fileName += "_Card";
			}
			
			try
			{
				String url = this.uploadAccountPic(file, fileName);
				
				if (!url.isEmpty())
				{
					if (type == 1)
					{
						account.setPicURL(url);
					}
					else if (type == 2)
					{
						account.setIdURL(url);
					}
					else if (type == 3)
					{
						account.setCardURL(url);
					}
					
					this.accountDao.update("updateAccount", account);
				}
				else
				{
					result = false;
				}
			}
			catch(Exception ex)
			{
				result = false;
			}
		}
		
		return new ModelAndView("jsonView", "json", result);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/deleteMyAccountPic.controller", method = RequestMethod.GET)
	public ModelAndView deleteMyAccountPic(Model model,
		@RequestParam(value="type") Integer type) {
		
		boolean result = true;
		
		AccountExtEntity account = (AccountExtEntity)this.accountDao.load("loadAccountById", HouseMartContext.getCurrentUserId());
		
		if (type == 1 && account.getPicLocked() == 0)
		{
			account.setPicURL("");
		}
		else if (type == 2 && account.getIdLocked() == 0)
		{
			account.setIdURL("");
		}
		else if (type == 3 && account.getCardLocked() == 0)
		{
			account.setCardURL("");
		}
		else
		{
			result = false;
		}
		
		if (result)
		{
			account.setUpdateTime(new Date());
			this.accountDao.update("updateAccount", account);
		}		
		
		return new ModelAndView("jsonView", "json", result);
	}
	
	protected String uploadAccountPic(MultipartFile file, String fileName) throws IOException{
		
		if(file == null || file.getSize() == 0){
			return "";
		}
		
		String[] arrNames = file.getOriginalFilename().split("\\.");
		final String uniqueFileName = "acc_" + fileName 
				+ "_" 
				+ Calendar.getInstance().getTime().getTime()
				+ "." + arrNames[1];
		final String url = "/upload/" + uniqueFileName;
		String fileFullPath = "/mnt" + url;
		
		File extraFile = new File("/alidata1");
		if(extraFile.exists()){
			fileFullPath = "/alidata1" + url;
		}
		
		FileCopyUtils.copy(file.getBytes(), new File(fileFullPath));		
		
		String URL = resourceProvider.getValue("housemart.pic.service.url");
		HessianProxyFactory factory = new HessianProxyFactory();  
		HessianPicServiceInterface service = 
		(HessianPicServiceInterface) factory.create(HessianPicServiceInterface.class, URL);
		
		PicSaveResult result = service.savePicToCloud(0, uniqueFileName, "image/" + arrNames[1], fileFullPath);
		
		if(result != null && result.getCode() == 200) {
			return result.getUrl();
		}
		else
		{
			return "";
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "page/brokerApply.controller")
	public String brokerApply(Model model) {
		
		return "account/brokerApply";
	}
	
	@RequestMapping(value = "brokerApplySave.controller", method = RequestMethod.POST)
	public String brokerApplySave(Model model, 
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="gender", required=true) Integer gender,
			@RequestParam(value="identityID", required=true) String identityID,
			@RequestParam(value="contactInfo1", required=true) String contactInfo1,
			@RequestParam(value="company", required=true) String company,
			@RequestParam(value="companyAddress", required=true) String companyAddress,
			@RequestParam(value="residences", required=true) String residences,
			@RequestParam(value="weixin", required=true) String weixin
			)
	{
		
		AccountExtEntity account = null;
		
		name = name.trim();
		identityID = identityID.trim();
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("identityID", identityID);
		map.put("status", "1,2,3");
		List<AccountExtEntity> list = (List<AccountExtEntity>)accountDao.select("findAccountList", map);
		
		if (list.size() > 0)
		{
			HouseMartContext.setSysMsg("身份证号已存在，不能重复申请.");
			return "redirect:/page/brokerApply.controller";
		}
		
		account = new AccountExtEntity();
		
		boolean dup = true;
		int count = 1;
		String loginName = name;
		while(dup)
		{
			map = new HashMap<Object, Object>();
			map.put("loginName", loginName);
			List<AccountExtEntity> duplist = (List<AccountExtEntity>)accountDao.select("findAccountList", map);
			if (duplist.size() > 0)
			{
				loginName += count;
				count++;
				dup = true;
			}
			else
			{
				dup = false;
			}
		}
		
		account.setLoginName(loginName);
		account.setPassword(AccountExtEntity.hashPassword("111111"));
		account.setStatus(AccountEntity.StatusEnum.Applied.value);
		account.setAddTime(new Date());
		account.setSource(AccountEntity.SourceEnum.ExtApply.value);
		
		account.setType(AccountEntity.TypeEnum.External.value);
		account.setCompany(company);
		account.setCompanyAddress(companyAddress);
		account.setNote(residences);
		account.setName(name);
		account.setGender(gender);
		account.setIdentityID(identityID);
		account.setContactInfo1(contactInfo1);
		account.setWeixin(weixin);
		
		account.setUpdateTime(new Date());
		
		this.accountDao.add("addAccount", account);
		
		HouseMartContext.setSysMsg("提交成功");
		
		return "redirect:/page/brokerApply.controller";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "accountApplyList.controller", method = RequestMethod.GET)
	public String accountApplyList(Model model,
			@RequestParam(value="tabIndex", required=false) Integer tabIndex,
			@RequestParam(value="keyword", required=false) String keyword,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize
			) {
		
		tabIndex = (tabIndex == null ? 1 : tabIndex);
		keyword = (keyword == null ? "" : keyword.trim());
		page = (page == null ? 0 : page);
		pageSize = (pageSize == null ? 50 : pageSize);
		
		List<AccountExtEntity> list = null;
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("skip", page * pageSize);
		map.put("count", pageSize);
		map.put("orderByClause", "acc.ID DESC");
		map.put("source", AccountEntity.SourceEnum.ExtApply.value);
		
		if (keyword.length() > 0)
		{
			map.put("searchKeyword", keyword);
		}
		
		if (tabIndex == 2)
		{
			map.put("status", AccountEntity.StatusEnum.Valid.value);
		}
		else if (tabIndex == 3)
		{
			map.put("status", AccountEntity.StatusEnum.Pending.value);
		}
		else if (tabIndex == 4)
		{
			map.put("status", AccountEntity.StatusEnum.Invalid.value);
		}
		else
		{
			map.put("status", AccountEntity.StatusEnum.Applied.value);
		}
		 
		list = accountDao.select("findAccountList", map);
		Integer totalCount = accountDao.count("countAccountList", map);
		
		model.addAttribute("list", list);
		
		Map<Object, Object> pager = new HashMap<Object, Object>();
		pager.put("totalCount", totalCount);
		pager.put("page", page);
		pager.put("pageSize", pageSize);
		
		model.addAttribute("pager", pager);
		
		model.addAttribute("keyword", keyword);
		
		model.addAttribute("tabIndex", tabIndex);
		
		return "account/accountApplyList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "accountApplyOp.controller", method = RequestMethod.GET)
	public String accountApplyOp(Model model,
			@RequestParam(value="fromTab", required=false) Integer fromTab,
			@RequestParam(value="op", required=true) Integer op,
			@RequestParam(value="accountIds", required=true) String accountIds,
			@RequestParam(value="pageSize", required=false) Integer pageSize
			) {
		
		fromTab = (fromTab == null ? 1 : fromTab);
		
		if (!accountIds.equals("") && (op.equals(AccountEntity.StatusEnum.Pending.value) || op.equals(AccountEntity.StatusEnum.Invalid.value)))
		{
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("ids", accountIds);
			map.put("status", op);
			this.accountDao.update("updateAccountByMap", map);
		}
		
		HouseMartContext.setSysMsg("操作成功");
		return "redirect:/accountApplyList.controller?tabIndex=" + fromTab;
	}
}
