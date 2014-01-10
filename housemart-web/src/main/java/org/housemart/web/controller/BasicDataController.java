package org.housemart.web.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.dao.entities.AccountEntity;
import org.housemart.dao.entities.RegionEntity;
import org.housemart.dao.entities.ResidenceBuildingEntity;
import org.housemart.dao.entities.ResidenceCellEntity;
import org.housemart.dao.entities.ResidenceEntity;
import org.housemart.dao.entities.ResidenceExtEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.dao.generic.UniqueIdObject;
import org.housemart.service.AuthenticationService;
import org.housemart.web.beans.AjaxResultBean;
import org.housemart.web.beans.HouseMartToken;
import org.housemart.web.context.HouseMartContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class BasicDataController extends BaseController {
			
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao regionDao;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao residenceDao;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao residenceBuildingDao;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao residenceCellDao;	
	
	@Autowired
	private AuthenticationService authenticationService;
	
  @RequestMapping(value = "index.controller")
  public ModelAndView index() {
    String serverName = getRequest().getServerName();
    logger.info("serverName:" + serverName);
    if (serverName != null
        && (serverName.contains("broker.housemart.cn") || serverName
            .contains("test.housemart.cn"))) {
      // 登录页面
      return new ModelAndView("index");
    }
    logger.info("redirect to html/ folder");
    // 默认首页
    return new ModelAndView(new RedirectView("/html/index.html"));
  }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/getRegionList.controller")
	public ModelAndView getRegionList(){
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("level", 1);
		List<RegionEntity> list = regionDao.select("findRegionListByLevel", map);
		AjaxResultBean result = new AjaxResultBean();
		result.setBizData(list);
		return new ModelAndView("jsonView", "json", result);
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/getPlateList.controller")
	public ModelAndView getPlateList(@RequestParam("parentId") int parentId){
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("parentId", parentId);
		List<RegionEntity> list = regionDao.select("findPlateListByRegionId", map);
		AjaxResultBean result = new AjaxResultBean();
		result.setBizData(list);
		return new ModelAndView("jsonView", "json", result);
		
	}	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/getResidenceListByPlateId.controller")
	public ModelAndView getResidenceListByPlateID(
			@RequestParam(value="plateId", required=false) Integer plateId,
			@RequestParam(value="regionId", required=false) Integer regionId,
			@RequestParam(value="countBroker", required=false) Integer countBroker
			){
		
		countBroker = (countBroker == null ? 0 : countBroker);
		
		List<ResidenceExtEntity> list = null;
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", plateId);
		param.put("regionId", regionId);
		param.put("status", 1);
		
		list = residenceDao.select("findResidenceList", param);
		
		if (countBroker == 1)
		{
			for(int i = 0; i < list.size(); i++)
			{
				list.get(i).updateBrokerList();
			}
		}
		
		AjaxResultBean result = new AjaxResultBean();
		result.setBizData(list);
		return new ModelAndView("jsonView", "json", result);
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/getResidenceListByResidenceName.controller")
	public ModelAndView getResidenceListByResidenceName(
			@RequestParam(value="residenceName") String residenceName,
			@RequestParam(value="countBroker", required=false) Integer countBroker
			) {	
		
		countBroker = (countBroker == null ? 0 : countBroker);
		
		List<ResidenceExtEntity> list = null;
		Map<String,Object> param = new HashMap<String,Object>();
		if (residenceName != null)
		{
			try
			{
				param.put("residenceKey", URLDecoder.decode(residenceName, "UTF-8"));
			}
			catch(Exception ex)
			{
				
			}
		}
		param.put("status", 1);
		param.put("countBroker", countBroker);
		list = residenceDao.select("findResidenceList", param);
		if (countBroker == 1)
		{
			for(int i = 0; i < list.size(); i++)
			{
				list.get(i).updateBrokerList();
			}
		}
		AjaxResultBean result = new AjaxResultBean();
		result.setBizData(list);
		return new ModelAndView("jsonView", "json", result);
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/getCellListByBuildingId.controller")
	public ModelAndView getCellListByBuildingId(@RequestParam("buildingId") int buildingId){
		
		List<ResidenceCellEntity> list = residenceCellDao.select("findResidenceCellListByBuildingId", new UniqueIdObject(buildingId));
		AjaxResultBean result = new AjaxResultBean();
		result.setBizData(list);
		return new ModelAndView("jsonView", "json", result);
		
	} 	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/getBuildingListByResienceId.controller")
	public ModelAndView getBuildingListByResienceId(@RequestParam("residenceId") int residenceId){
		
		List<ResidenceBuildingEntity> buildingList = residenceBuildingDao
				.select("findResidenceBuildingListByResidenceId",
						new UniqueIdObject(residenceId));
		
		AjaxResultBean result = new AjaxResultBean();
		result.setBizData(buildingList);
		return new ModelAndView("jsonView", "json", result);
		
	} 
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login.controller",  method = RequestMethod.GET)
	public String loginPage(Model model) {
	  HouseMartContext.setType(-1);
		return "login";
	}
	
	@RequestMapping(value = "loginExt.controller",  method = RequestMethod.GET)
	public String loginExtPage(Model model) {
	  HouseMartContext.setType(-1);
		return "loginExt";
	}	
	
	@RequestMapping(value = "logout.controller",  method = RequestMethod.GET)
	public ModelAndView logoutPage(Model model, HttpServletResponse resp) {
		
		Cookie[] cookies = this.getRequest().getCookies();
		if(!ArrayUtils.isEmpty(cookies)){
			for(Cookie item: cookies){
				if(item.getName().equals("user")){
					item.setMaxAge(0);   
		            item.setPath("/");
		            resp.addCookie(item);
		            break;
				}
			}
		}
		String landingPage = resourceProvider.getValue("housemart.landingPage.URL");
		HouseMartContext.setType(-1);
		return new ModelAndView(new RedirectView(landingPage));
	}
	
	@RequestMapping(value = "logoutExt.controller",  method = RequestMethod.GET)
	public ModelAndView logoutExtPage(Model model, HttpServletResponse resp) {
		
		int accountType = HouseMartContext.getType();
		
		Cookie[] cookies = this.getRequest().getCookies();
		if(!ArrayUtils.isEmpty(cookies)){
			for(Cookie item: cookies){
				if(item.getName().equals("user")){
					item.setMaxAge(0);   
		            item.setPath("/");
		            resp.addCookie(item);
		            break;
				}
			}
		}
		
		
		if (model.containsAttribute("accountType"))
		{
			accountType = (Integer)model.asMap().get("accountType");
		}
		
		String landingPage = accountType == 0 ? resourceProvider.getValue("housemart.landingPage.URL") :
				"/loginExt.controller";
		HouseMartContext.setType(-1);
		return new ModelAndView(new RedirectView(landingPage));
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "loginSubmit.controller",  method = RequestMethod.POST)
	public ModelAndView loginSubmitPage(Model model, HttpServletResponse resp, @RequestParam("userName") String userName, @RequestParam("password") String password) {
		
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
			return new ModelAndView(new RedirectView("/login"));
		}
		
		HouseMartToken token = authenticationService.login(userName, password);
		if(token != null && token.isLoggin()){
			Cookie item = new Cookie("user", token.getTokenStr());
			item.setPath("/");
			item.setMaxAge(60 * 60 * 24 * 7);
			resp.addCookie(item);
			
			Integer accountType = HouseMartContext.getType();
			
			String landingPage = "";
			if (model.containsAttribute("accountType"))
			{
				if (accountType == AccountEntity.TypeEnum.External.value||
					accountType == AccountEntity.TypeEnum.Combine.value)
				{
					landingPage = resourceProvider.getValue("housemart.landingPage.extURL");
					
				}
				else
				{
					return this.logoutExtPage(model, resp);
				}
			}
			else
			{
				landingPage = resourceProvider.getValue(
					accountType == AccountEntity.TypeEnum.External.value ?
					"housemart.landingPage.extURL" :
					"housemart.landingPage.URL");
			}
			
			return new ModelAndView(new RedirectView(landingPage));
		}else{
		  
		  HouseMartContext.setType(-1);
		  HouseMartContext.setSysMsg("用户名或者密码错误");
		}
		
		return new ModelAndView(new RedirectView("/login"));
	}
	
	@RequestMapping(value = "loginExtSubmit.controller",  method = RequestMethod.POST)
	public ModelAndView loginExtSubmitPage(Model model, HttpServletResponse resp, @RequestParam("userName") String userName, @RequestParam("password") String password) {
		
		model.addAttribute("accountType", AccountEntity.TypeEnum.External);
		return this.loginSubmitPage(model, resp, userName, password);
	}
	
	@RequestMapping(value = "upateLoadForm.controller",  method = RequestMethod.GET)
	public String upateLoadForm(Model model) throws IOException{
		
		return "uploadFile";
		
	} 
	
	@RequestMapping(value = "mapAudit.controller")
	public String mapDataAudit(Model model) {
		
		return "map/audit";
		
	} 
	
	@RequestMapping(value = "page/index.controller")
	public String pageIndex(Model model, 
			@RequestParam("id") String id ) {
		
		return "page/" + id;
		
	} 
}
