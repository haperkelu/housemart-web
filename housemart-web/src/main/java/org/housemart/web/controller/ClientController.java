/**
 * Created on 2013-10-22
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.dao.entities.ClientNotificationEntity;
import org.housemart.dao.entities.ClientRegisterEntity;
import org.housemart.dao.entities.KeyValueEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.push.BaiduYunAPI;
import org.housemart.framework.push.JavaPNSProvider;
import org.housemart.service.ClientService;
import org.housemart.util.ClientHelper;
import org.housemart.web.context.HouseMartContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ClientController extends BaseController {
  
	
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao clientRegisterDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao clientNotificationDao;
  
  @Autowired
  ClientService clientService;
	
  ClientHelper clientHelper = new ClientHelper();

  @Autowired
  private GenericDao<KeyValueEntity> keyValueDao;
  private ObjectMapper mapper = new ObjectMapper();
  
  public static enum OsTypeEnum {
    i, w, a;
  };
  
  public static enum ClientTypeEnum {
    p, b;
  }
  
  @RequestMapping(value = "client/version/update")
  public ModelAndView updateVersionInfo(Model model,
      @RequestParam String currentVersion,
      @RequestParam String requiredVersion,
      @RequestParam String currentVersionInfo, @RequestParam char osType,
      @RequestParam char clientType) throws JsonGenerationException,
      JsonMappingException, IOException {
    
    if (StringUtils.isBlank(currentVersion)
        || StringUtils.isBlank(requiredVersion)) {
      return new ModelAndView(new RedirectView(
          "/client/version/view.controller"));
    }
    
    String finalKey = clientHelper.toFinalKey(clientType, osType);
    Map<String,Object> para = new HashMap<String,Object>();
    para.put("key", finalKey);
    List<KeyValueEntity> keyValues = keyValueDao.select("selectByKey", para);
    
    if (CollectionUtils.isEmpty(keyValues)) {
      Map<String,String> value = new HashMap<String,String>();
      value.put(ClientHelper.ATTR_CURRENT_VERSION, currentVersion);
      value.put(ClientHelper.ATTR_CURRENT_VERSION_INFO, currentVersionInfo);
      value.put(ClientHelper.ATTR_REQUIRED_VERSION, requiredVersion);
      
      KeyValueEntity entity = new KeyValueEntity();
      entity.setKey(finalKey);
      entity.setValue(mapper.writeValueAsString(value));
      entity.setAddTime(new Date());
      entity.setUpdateTime(new Date());
      keyValueDao.add("addKeyValue", entity);
      
    } else {
      Map<String,String> value = new HashMap<String,String>();
      value.put(ClientHelper.ATTR_CURRENT_VERSION, currentVersion);
      value.put(ClientHelper.ATTR_CURRENT_VERSION_INFO, currentVersionInfo);
      value.put(ClientHelper.ATTR_REQUIRED_VERSION, requiredVersion);
      
      KeyValueEntity entity = keyValues.get(0);
      entity.setValue(mapper.writeValueAsString(value));
      entity.setUpdateTime(new Date());
      keyValueDao.update("updateById", entity);
      
    }
    
    model.addAttribute(ClientHelper.ATTR_CURRENT_VERSION, currentVersion);
    model.addAttribute(ClientHelper.ATTR_CURRENT_VERSION_INFO, currentVersionInfo);
    model.addAttribute(ClientHelper.ATTR_REQUIRED_VERSION, requiredVersion);
    
    return new ModelAndView(new RedirectView("/client/version/view.controller"));
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "client/version/view")
  public String viewVersionInfo(Model model) throws JsonParseException,
      JsonMappingException, IOException {
    
    for (ClientTypeEnum clientType : ClientTypeEnum.values()) {
      
      for (OsTypeEnum osType : OsTypeEnum.values()) {
        String finalKey = clientHelper.toFinalKey(clientType.toString().charAt(0), osType
            .toString().charAt(0));
        Map<String,Object> para = new HashMap<String,Object>();
        para.put("key", finalKey);
        List<KeyValueEntity> keyValues = keyValueDao
            .select("selectByKey", para);
        
        if (CollectionUtils.isNotEmpty(keyValues)) {
          KeyValueEntity entity = keyValues.get(0);
          Map<String,String> value = mapper.readValue(entity.getValue(),
              Map.class);
          
          model.addAttribute(ClientHelper.ATTR_CURRENT_VERSION,
              value.get(ClientHelper.ATTR_CURRENT_VERSION));
          model.addAttribute(ClientHelper.ATTR_CURRENT_VERSION_INFO,
              value.get(ClientHelper.ATTR_CURRENT_VERSION_INFO));
          model.addAttribute(ClientHelper.ATTR_REQUIRED_VERSION,
              value.get(ClientHelper.ATTR_REQUIRED_VERSION));
          
          model.addAttribute(finalKey, value);
        }
      }
    }
    
    return ("client/version");
  }
    
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "client/notification/notify.controller")
	public String notificationNotify(Model model,
			@RequestParam(value="target", required=false) Integer target,
			@RequestParam(value="clientUIds", required=false) String clientUIds,
			@RequestParam(value="content", required=false) String content,
			@RequestParam(value="sendTime", required=false) String sendTime
			) {
		
		if (StringUtils.isEmpty(content))
		{
			return "redirect:/client/notification/list.controller";
		}
		else
		{
			if (!StringUtils.isEmpty(clientUIds)) 
			{
				target = ClientNotificationEntity.TargetEnum.Custom.value;
			}
			try
			{
				ClientNotificationEntity notification = new ClientNotificationEntity();
				notification.setContent(content);
				notification.setTarget(target);
				notification.setClientUIDs(clientUIds);
				Date time = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				notification.setSendTime(df.parse(sendTime));
				notification.setStatus(ClientNotificationEntity.StatusEnum.Unprocessed.value);
				notification.setAccountID(HouseMartContext.getCurrentUserId());
				
				int id = clientNotificationDao.add("addClientNotification", notification);
			}
			catch (Exception ex)
			{
				
			}
			
			return "redirect:/client/notification/list.controller";
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "client/notification/list.controller")
	public String notificationList(Model model,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize
			) {
		
		page = (page == null ? 0 : page);
		pageSize = (pageSize == null ? 50 : pageSize);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("skip", page * pageSize);
		map.put("count", pageSize);
		map.put("orderByClause", "cn.SendTime DESC");
		
		List<ClientNotificationEntity> list = clientNotificationDao.select("findClientNotificationList", map);
		
		model.addAttribute("notificationList", list);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date defaultTime = new Date();
		model.addAttribute("defaultTime", df.format(defaultTime));
		
		Map<Object, Object> pager = new HashMap<Object, Object>();
		pager.put("page", page);
		pager.put("pageSize", pageSize);
		
		model.addAttribute("pager", pager);
		
		return "client/notificationNotify";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "client/notification/process.controller")
	public String notificationProcess(Model model,
			@RequestParam(value="id", required=true) Integer id,
			@RequestParam(value="send", required=true) Integer send
			) {
		
		clientService.processClientNotification(id);
		if (send == 1)
		{
			clientService.sendClientNotifications();
		}
		
		return "redirect:/client/notification/list.controller";
	}
}
