/**
 * Created on 2013-11-17
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.dao.entities.AppClientFileEntity;
import org.housemart.dao.entities.KeyValueEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.util.ClientHelper;
import org.housemart.web.context.HouseMartContext;
import org.housemart.web.controller.ClientController.ClientTypeEnum;
import org.housemart.web.controller.ClientController.OsTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ClientFileController extends BaseController {
  
  private static Log log = LogFactory.getLog(ClientFileController.class);
  
  @Autowired
  GenericDao<AppClientFileEntity> appClientFileDao;
  @Autowired
  private GenericDao<KeyValueEntity> keyValueDao;
  
  private ClientHelper clientHelper = new ClientHelper();
  private ObjectMapper mapper = new ObjectMapper();
  
  @RequestMapping(value = "client/appFile/list")
  public ModelAndView findList() {
    
    List<AppClientFileEntity> popList = null;
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("osType", OsTypeEnum.a.toString());
    param.put("clientType", ClientTypeEnum.p.toString());
    popList = appClientFileDao.select("findAppClientFileList", param);
    
    List<AppClientFileEntity> brokerList = null;
    param = new HashMap<String,Object>();
    param.put("osType", OsTypeEnum.a.toString());
    param.put("clientType", ClientTypeEnum.b.toString());
    brokerList = appClientFileDao.select("findAppClientFileList", param);
    
    Map<String,Object> data = new HashMap<String,Object>();
    data.put("popItems", popList);
    data.put("brokerItems", brokerList);
    return new ModelAndView("/client/appFile", data);
  }
  
  @RequestMapping(value = "client/appFile/add")
  public ModelAndView add(Model model,
      @RequestParam("appFile") MultipartFile file,
      @RequestParam char clientType, @RequestParam String version)
      throws IOException {
    
    String name = null;
    if (clientType == ClientTypeEnum.b.toString().charAt(0)) {
      name = "housemart-agent";
    } else {
      name = "housemart";
    }
    
    String cloudURL = updateLoadFile(file, name, "application/vnd.android.package-archive");
    
    if (StringUtils.isNotBlank(cloudURL)) {
      AppClientFileEntity appClient = new AppClientFileEntity();
      appClient.setOsType(OsTypeEnum.a.toString());
      appClient.setClientType(String.valueOf(clientType));
      appClient.setVersion(version);
      appClient.setStatus(1);
      appClient.setCloudURL(cloudURL);
      appClient.setAddTime(new Date());
      appClient.setUpdateTime(new Date());
      appClientFileDao.add("addAppClientFile", appClient);
    } else {
      HouseMartContext.setSysMsg("上传失败");
    }
    log.info("UploadFileCloudURL:::" + cloudURL);
    return new ModelAndView(new RedirectView("/client/appFile/list.controller"));
    
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "client/appFile/download")
  public ModelAndView download(@RequestParam char clientType)
      throws JsonParseException, JsonMappingException, IOException {
    
    String version = "";
    String finalKey = clientHelper.toFinalKey(clientType, OsTypeEnum.a
        .toString().charAt(0));
    Map<String,Object> para = new HashMap<String,Object>();
    para.put("key", finalKey);
    List<KeyValueEntity> keyValues = keyValueDao.select("selectByKey", para);
    if (CollectionUtils.isNotEmpty(keyValues)) {
      KeyValueEntity entity = keyValues.get(0);
      Map<String,String> value = mapper.readValue(entity.getValue(), Map.class);
      version = value.get(ClientHelper.ATTR_CURRENT_VERSION);
    }
    log.info("clientType::" + clientType + "osType::" + OsTypeEnum.a.toString()
        + "version::" + version);
    
    String url = "";
    List<AppClientFileEntity> clientList = null;
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("osType", OsTypeEnum.a.toString());
    param.put("clientType", String.valueOf(clientType));
    param.put("version", version);
    clientList = appClientFileDao.select("findAppClientFileList", param);
    
    if (CollectionUtils.isNotEmpty(clientList)) {
      url = clientList.get(0).getCloudURL();
      if (clientType == ClientTypeEnum.b.toString().charAt(0)) {
        url = url + "?download/housemart-agent.apk";
      } else {
        url = url + "?download/housemart.apk";
      }
    }
    
    return new ModelAndView(new RedirectView(url));
  }
  
}
