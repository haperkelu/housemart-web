package org.housemart.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.housemart.dao.entities.HousePicEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.pic.api.HessianPicServiceInterface;
import org.housemart.pic.api.PicSaveResult;
import org.housemart.resource.ResourceProvider;
import org.housemart.service.AuthenticationService;
import org.housemart.util.UrlBase64Coder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 
 * @author pai.li
 * 
 */
public class BaseController {
  
  @Autowired
  protected AuthenticationService authenticationService;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  protected GenericDao housePicDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  protected GenericDao repositoryHousePicDao;
  
  @Autowired
  protected ResourceProvider resourceProvider;
  
  /**
   * logger
   */
  protected static final Logger logger = LoggerFactory
      .getLogger(BaseController.class);
  
  public BaseController() {
    Assert.assertNotNull(logger);
    logger.debug("basicController constructor");
  }
  
  /**
   * 
   * @return
   */
  protected HttpSession getSeesion() {
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes();
    return attr.getRequest().getSession(true);
  }
  
  /**
   * 
   * @return
   */
  protected HttpServletRequest getRequest() {
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes();
    return attr.getRequest();
  }
  
  /**
   * 
   * @param key
   * @param value
   */
  protected void setViewProperty(String key, Object value) {
    HttpServletRequest req = this.getRequest();
    req.setAttribute(key, value);
  }
  
  /**
   * 
   * @Title: updateLoadImage
   * @Description: TODO
   * @param @param file
   * @param @return
   * @param @throws IOException
   * @return String
   * @throws
   */
  protected int updateLoadImage(MultipartFile file, int type, int targetId)
      throws IOException {
    
    int picId = -1;
    if (file == null || file.getSize() == 0) {
      return picId;
    }
    String[] arrNames = file.getOriginalFilename().split("\\.");
    final String fileName = UrlBase64Coder.encoded(arrNames[0]) + "_"
        + Calendar.getInstance().getTime().getTime() + "." + arrNames[1];
    final String url = "/upload/" + fileName;
    String fileFullPath = "/mnt" + url;
    
    File extraFile = new File("/alidata1");
    if (extraFile.exists()) {
      fileFullPath = "/alidata1" + url;
    }
    
    FileCopyUtils.copy(file.getBytes(), new File(fileFullPath));
    
    HousePicEntity housePic = new HousePicEntity();
    housePic.setHouseId((type == 2 || type == 3) ? targetId : 0);
    housePic.setResidenceId((type == 1 || type == 0) ? targetId : 0);
    housePic.setType(type);
    housePic.setUrl(url);
    housePic.setStatus(1);
    housePic.setAddTime(Calendar.getInstance().getTime());
    housePic.setUpdateTime(Calendar.getInstance().getTime());
    if(type ==0){
      //external residence
      housePic.setShowStatus(0);
    }else{
      housePic.setShowStatus(1);
    }
    picId = housePicDao.add("addHousePic", housePic);
    
    if (picId <= 0) {
      throw new RuntimeException("Pic Save DB Error!");
    }
    
    String URL = resourceProvider.getValue("housemart.pic.service.url");
    HessianProxyFactory factory = new HessianProxyFactory();
    HessianPicServiceInterface service = (HessianPicServiceInterface) factory
        .create(HessianPicServiceInterface.class, URL);
    
    PicSaveResult result = service.savePicToCloud(picId, fileName, "image/"
        + arrNames[1], fileFullPath);
    
    if (result != null && result.getCode() == 200) {
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("id", picId);
      map.put("cloudURL", result.getUrl());
      housePicDao.update("updateCloudURL", map);
    }
    return picId;
  }
  
  protected void updateLoadImage(String cloudUrl, int type, int targetId) {
    if (cloudUrl == null) {
      return;
    }
    
    HousePicEntity housePic = new HousePicEntity();
    housePic.setHouseId((type == 2 || type == 3) ? targetId : 0);
    housePic.setResidenceId(type == 1 ? targetId : 0);
    housePic.setCloudUrl(cloudUrl);
    housePic.setType(type);
    housePic.setStatus(1);
    housePic.setAddTime(Calendar.getInstance().getTime());
    housePic.setUpdateTime(Calendar.getInstance().getTime());
    if(type ==0){
      //external residence
      housePic.setShowStatus(0);
    }else{
      housePic.setShowStatus(1);
    }
    int picId = housePicDao.add("addHousePic", housePic);
    
    if (picId <= 0) {
      throw new RuntimeException("Pic Save DB Error!");
    }
  }
  
  protected String updateLoadFile(MultipartFile file, String name,
      String contentType) throws IOException {
    
    String cloudURL = null;
    if (file == null || file.getSize() == 0) {
      return cloudURL;
    }
    String[] arrNames = file.getOriginalFilename().split("\\.");
    String tmpName = null;
    if (StringUtils.isNotBlank(name)) {
      tmpName = name
          + "_"
          + DateUtils.truncate(Calendar.getInstance().getTime(),
              Calendar.SECOND).getTime() + "." + arrNames[1];
    } else {
      tmpName = UrlBase64Coder.encoded(arrNames[0]) + "_"
          + Calendar.getInstance().getTime().getTime() + "." + arrNames[1];
    }
    
    final String fileName = tmpName;
    final String url = "/upload/" + fileName;
    String fileFullPath = "/mnt" + url;
    
    File extraFile = new File("/alidata1");
    if (extraFile.exists()) {
      fileFullPath = "/alidata1" + url;
    }
    
    FileCopyUtils.copy(file.getBytes(), new File(fileFullPath));
    
    String URL = resourceProvider.getValue("housemart.pic.service.url");
    HessianProxyFactory factory = new HessianProxyFactory();
    HessianPicServiceInterface service = (HessianPicServiceInterface) factory
        .create(HessianPicServiceInterface.class, URL);
    
    PicSaveResult result = service.savePicToCloud(-1, fileName, contentType,
        fileFullPath);
    
    if (result != null && result.getCode() == 200) {
      cloudURL = result.getUrl();
    }
    
    return cloudURL;
    
  }
  
}
