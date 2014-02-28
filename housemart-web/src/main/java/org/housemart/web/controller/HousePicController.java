/**
 * Created on 2012-11-1
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.crawl.pic.service.ResidencePicCrawlerService;
import org.housemart.dao.entities.HouseEntity;
import org.housemart.dao.entities.HouseExtEntity;
import org.housemart.dao.entities.HousePicEntity;
import org.housemart.dao.entities.HousePicSortEntity;
import org.housemart.dao.entities.RepositoryHousePicEntity;
import org.housemart.dao.entities.ResidenceEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.pic.api.HessianPicServiceInterface;
import org.housemart.pic.api.PicSaveResult;
import org.housemart.util.GenericCollections;
import org.housemart.util.PicUtils;
import org.housemart.web.beans.AjaxResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * @author pqin
 */
@Controller
public class HousePicController extends BaseController {
  @Autowired
  GenericDao<HousePicEntity> housePicDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao houseDao;
  @Autowired
  GenericDao<HousePicSortEntity> housePicSortDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceDao;
  
  @Autowired
  ResidencePicCrawlerService residencePicCrawlerService;
  
  @RequestMapping(value = "external/housePicConsole.controller")
  public String externalHousePicConsole(Model model, int houseId, int picType) {
    
    HouseExtEntity house = (HouseExtEntity) houseDao.load("loadHouseExt",
        houseId);
    
    if (house.getStatus().equals(HouseEntity.StatusEnum.Valid.value)) {
      return "";
    }
    
    ResidenceEntity residenceEntity = (ResidenceEntity) residenceDao.load(
        "loadResidenceById", house.getResidenceId());
    
    String sort = null;
    List<HousePicSortEntity> picSorts = null;
    
    Map<String,Object> para = new HashMap<String,Object>();
    para.put("type", picType);
    if (picType == HousePicEntity.Type.ExternalResidence.getValue()
        && house.getResidenceId() != null) {
      para.put("residenceId", house.getResidenceId());
      picSorts = housePicSortDao.select("findHousePicSortByResidenceIdAndType",
          para);
    } else {
      para.put("houseId", houseId);
      picSorts = housePicSortDao.select("findHousePicSortByHouseIdAndType",
          para);
    }
    
    if (CollectionUtils.isNotEmpty(picSorts)) {
      sort = picSorts.get(0).getSort();
    }
    
    model.addAttribute("house", house);
    model.addAttribute("sort", sort);
    model.addAttribute("picType", picType);
    model.addAttribute("create", this.getRequest().getParameter("create"));
    model.addAttribute("residence", residenceEntity);
    
    return ("external/house/housePic");
    
  }
  
  @RequestMapping(value = "ajax/getPicListByHouseId.controller")
  public ModelAndView housePicList(Integer houseId, int type) {
    Map<Object,Object> map = new HashMap<Object,Object>();
    
    String sort = null;
    List<HousePicEntity> list = null;
    if (type == HousePicEntity.Type.Residence.getValue()
        || type == HousePicEntity.Type.ExternalResidence.getValue()) {
      
      HouseExtEntity house = (HouseExtEntity) houseDao.load("loadHouseExt",
          houseId);
      
      if (house != null && house.getResidenceId() != null) {
        map.put("residenceId", house.getResidenceId());
        list = housePicDao.select("findResidencePicById", map);
        
        Map<String,Object> para = new HashMap<String,Object>();
        // para.put("type", type);
        para.put("type", HousePicEntity.Type.ExternalResidence.getValue());
        para.put("residenceId", house.getResidenceId());
        List<HousePicSortEntity> picSorts = housePicSortDao.select(
            "findHousePicSortByResidenceIdAndType", para);
        
        if (CollectionUtils.isNotEmpty(picSorts)) {
          HousePicSortEntity ps = picSorts.get(0);
          if (ps != null) {
            sort = ps.getSort();
          }
        }
      }
      
    } else {
      map.put("type", type);
      map.put("houseId", houseId);
      list = housePicDao.select("findHousePicByHouseIdAndType", map);
      
      Map<String,Object> para = new HashMap<String,Object>();
      para.put("type", type);
      para.put("houseId", houseId);
      List<HousePicSortEntity> picSorts = housePicSortDao.select(
          "findHousePicSortByHouseIdAndType", para);
      if (CollectionUtils.isNotEmpty(picSorts)) {
        HousePicSortEntity ps = picSorts.get(0);
        if (ps != null) {
          sort = ps.getSort();
        }
      }
    }
    
    if (list != null && StringUtils.isNotBlank(sort)) {
      list = sortPics(list, sort);
    }
    
    // water mask
    if (list != null) {
      for (HousePicEntity p : list) {
        if (p.getCloudUrl() != null) {
          p.setCloudUrl(PicUtils.wrapWaterMask(p.getCloudUrl()));
        }
      }
    }
    
    AjaxResultBean result = new AjaxResultBean();
    result.setBizData(list);
    return new ModelAndView("jsonView", "json", result);
  }
  
  @RequestMapping(value = "houseRoomTypeConsole.controller")
  public String houseRoomTypeConsole(Model model, Integer houseId) {
    HouseEntity house = (HouseEntity) houseDao.load("loadHouse", houseId);
    model.addAttribute("house", house);
    return ("house/houseRoomType");
  }
  
  @RequestMapping(value = "housePicConsole.controller")
  public String housePicConsole(Model model, Integer houseId) {
    HouseEntity house = (HouseEntity) houseDao.load("loadHouse", houseId);
    model.addAttribute("house", house);
    return ("house/housePic");
  }
  
  @RequestMapping(value = "soldHousePicConsole.controller")
  public String soldHousePicConsole(Model model, Integer houseId) {
    HouseEntity house = new HouseEntity();
    house.setId(houseId);
    model.addAttribute("house", house);
    return ("house/soldHousePic");
  }
  
  @RequestMapping(value = "housePicUpload.controller")
  public ModelAndView housePicUpload(
      @RequestParam("imageFile") MultipartFile file, Model model,
      Integer houseId, Integer houseType, Integer sourceType) {
    
    try {
      this.updateLoadImage(file, houseType, houseId);
      
      // update house entity update time.
      HouseEntity houseEntity = new HouseEntity();
      houseEntity.setId(houseId);
      houseEntity.setUpdateTime(Calendar.getInstance().getTime());
      houseDao.update("updateHouse", houseEntity);
      
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    
    if (sourceType == null) {
      if (houseType.equals(HousePicEntity.Type.RoomType.getValue())) {
        return new ModelAndView(new RedirectView(
            "houseRoomTypeConsole.controller?houseId=" + houseId));
      } else if (houseType.equals(HousePicEntity.Type.HousePic.getValue())) {
        return new ModelAndView(new RedirectView(
            "housePicConsole.controller?houseId=" + houseId));
      } else {
        return new ModelAndView(new RedirectView(
            "housePicConsole.controller?houseId=" + houseId));
      }
    } else {
      return new ModelAndView(new RedirectView(
          "external/housePicConsole.controller?houseId=" + houseId));
    }
  }
  
  /*
   * internal ios server调用
   */
  @RequestMapping(value = "ajax/housePicUploadInt.controller")
  public ModelAndView housePicUploadInt(
      @RequestParam("imageFile") MultipartFile file, Integer houseId,
      Integer picType) {
    return this.multiHousePicUpload(file, null, houseId, 0, picType);
  }
  
  @RequestMapping(value = "multiPicUpload.controller")
  public ModelAndView multiHousePicUpload(
      @RequestParam("imageFile") MultipartFile file, Model model,
      Integer houseId, Integer residenceId, Integer picType) {
    
    List<Map<String,Object>> bizData = new ArrayList<Map<String,Object>>();
    try {
      // update house entity update time.
      HouseEntity houseEntity = new HouseEntity();
      int picId = -1;
      
      if (picType == HousePicEntity.Type.Residence.getValue()
          || picType == HousePicEntity.Type.ExternalResidence.getValue()) {
        
        if (residenceId > 0) {
          picId = this.updateLoadImage(file, picType, residenceId);
          houseEntity.setResidenceId(residenceId);
          houseEntity.setUpdateTime(Calendar.getInstance().getTime());
          houseDao.update("updateHouseByResidenceId", houseEntity);
        }
        
      } else {
        
        if (houseId > 0) {
          picId = this.updateLoadImage(file, picType, houseId);
          houseEntity.setId(houseId);
          houseEntity.setUpdateTime(Calendar.getInstance().getTime());
          houseDao.update("updateHouse", houseEntity);
        }
        
      }
      
      if (picId > -1) {
        HousePicEntity pEntity = housePicDao.load("loadHousePic", picId);
        if (pEntity != null) {
          Map<String,Object> picData = new HashMap<String,Object>();
          picData.put("id", pEntity.getId());
          picData.put("url", pEntity.getCloudUrl());
          bizData.add(picData);
        }
      }
      
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    
    AjaxResultBean result = new AjaxResultBean();
    result.setCode(1);
    result.setBizData(bizData);
    
    return new ModelAndView("jsonView", "json", result);
    
  }
  
  @RequestMapping(value = "repositoryPicUpload.controller")
  public ModelAndView houseRepositoryUpload(
      @RequestParam("imageFile") MultipartFile file, Model model,
      @RequestParam("picId") int picId, @RequestParam("token") String token) {
    AjaxResultBean result = new AjaxResultBean();
    try {
      if (token == null || !token.equalsIgnoreCase("817")) {
        result.setMsg("token not right");
        return new ModelAndView("jsonView", "json", result);
      }
      
      if (file == null || file.getSize() == 0) {
        result.setMsg("file not found");
        return new ModelAndView("jsonView", "json", result);
      }
      String[] arrNames = file.getOriginalFilename().split("\\.");
      final String fileName = arrNames[0] + "_"
          + Calendar.getInstance().getTime().getTime() + "." + arrNames[1];
      final String url = "/upload/" + fileName;
      final String fileFullPath = "/mnt" + url;
      FileCopyUtils.copy(file.getBytes(), new File(fileFullPath));
      
      String URL = resourceProvider.getValue("housemart.pic.service.url");
      HessianProxyFactory factory = new HessianProxyFactory();
      HessianPicServiceInterface service = (HessianPicServiceInterface) factory
          .create(HessianPicServiceInterface.class, URL);
      
      PicSaveResult remoteResult = service.savePicToCloud(picId, fileName,
          "image/" + arrNames[1], fileFullPath);
      
      if (remoteResult != null && remoteResult.getCode() == 200) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id", picId);
        map.put("cloudURL", remoteResult.getUrl());
        map.put("status", 1);
        map.put("url", fileFullPath);
        housePicDao.update("updateRepositoryPicCloudURL", map);
      } else {
        result.setMsg("hessian handle error");
        result.setCode(result == null ? 0 : result.getCode());
        return new ModelAndView("jsonView", "json", result);
      }
      
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      result.setMsg("server error");
      return new ModelAndView("jsonView", "json", result);
    }
    result.setMsg("success");
    return new ModelAndView("jsonView", "json", result);
    
  }
  
  @SuppressWarnings({"unchecked", "rawtypes"})
  @RequestMapping(value = "removePic.controller")
  public ModelAndView removePic(Model model, @RequestParam("id") int id,
      Integer houseId, Integer sourceType) {
    
    Map para = new HashMap();
    para.put("id", id);
    para.put("status", 2);
    housePicDao.update("updateStatus", para);
    
    HousePicEntity pEntity = housePicDao.load("loadHousePic", id);
    
    HouseEntity houseEntity = new HouseEntity();
    if (pEntity.getType() == HousePicEntity.Type.ExternalResidence.getValue()
        || pEntity.getType() == HousePicEntity.Type.Residence.getValue()) {
      
      // to repository
      residencePicCrawlerService.addHousePicToRespositoryDb(null,
          pEntity.getResidenceId(), pEntity.getCloudUrl(),
          RepositoryHousePicEntity.Type.Residence.getValue());
      
      houseEntity.setResidenceId(pEntity.getResidenceId());
      houseEntity.setUpdateTime(Calendar.getInstance().getTime());
      houseDao.update("updateHouseByResidenceId", houseEntity);
    } else {
      
      houseEntity.setId(pEntity.getHouseId());
      houseEntity.setUpdateTime(Calendar.getInstance().getTime());
      houseDao.update("updateHouse", houseEntity);
    }
    
    HousePicEntity.Type typeEnum = HousePicEntity.Type.valueOf(pEntity
        .getType());
    
    if (sourceType == null
        || sourceType == HouseEntity.SourceTypeEnum.internal.value
        || sourceType == HouseEntity.SourceTypeEnum.customerservice.value) {
      
      switch (typeEnum) {
        case HousePic:
          return new ModelAndView(new RedirectView(
              "/housePicConsole.controller?houseId=" + pEntity.getHouseId()));
        case RoomType:
          return new ModelAndView(new RedirectView(
              "/houseRoomTypeConsole.controller?houseId="
                  + pEntity.getHouseId()));
        case Residence:
        default:
          return new ModelAndView(new RedirectView(
              "/residencePicList.controller?residenceId="
                  + pEntity.getResidenceId()));
      }
    } else {
      // 外围房源
      if (houseId != null && houseId > 0) {
        // 房源页面
        return new ModelAndView(new RedirectView(
            "/external/housePicConsole.controller?houseId=" + houseId
                + "&picType=" + pEntity.getType()));
      } else {
        // 小区页面的小区图片
        return new ModelAndView(new RedirectView(
            "/external/residencePicConsole.controller?residenceId="
                + pEntity.getResidenceId()));
      }
    }
  }
  
  @SuppressWarnings({"unchecked", "rawtypes"})
  @RequestMapping(value = "ajax/removePic.controller")
  public ModelAndView removePic(Model model, @RequestParam("id") int id,
      @RequestParam(required = false) Integer verify) {
    
    verify = (verify == null ? 1 : verify);
    
    Map para = new HashMap();
    para.put("id", id);
    para.put("status", 2);
    housePicDao.update("updateStatus", para);
    
    HousePicEntity pEntity = housePicDao.load("loadHousePic", id);
    
    HouseEntity houseEntity = new HouseEntity();
    if (pEntity.getType() == HousePicEntity.Type.ExternalResidence.getValue()
        || pEntity.getType() == HousePicEntity.Type.Residence.getValue()) {
      // to repository
      residencePicCrawlerService.addHousePicToRespositoryDb(null,
          pEntity.getResidenceId(), pEntity.getCloudUrl(),
          RepositoryHousePicEntity.Type.Residence.getValue());
      
      houseEntity.setResidenceId(pEntity.getResidenceId());
      houseEntity.setUpdateTime(Calendar.getInstance().getTime());
      houseDao.update("updateHouseByResidenceId", houseEntity);
    } else {
      
      houseEntity.setId(pEntity.getHouseId());
      houseEntity.setUpdateTime(Calendar.getInstance().getTime());
      houseDao.update("updateHouse", houseEntity);
    }
    
    AjaxResultBean result = new AjaxResultBean();
    result.setCode(1);
    return new ModelAndView("jsonView", "json", result);
    
  }
  
  /*
   * internal ios server调用
   */
  @RequestMapping(value = "ajax/housePicDeleteInt.controller")
  public ModelAndView removePicInt(Model model,
      @RequestParam("picIds") String picIds) {
    
    AjaxResultBean result = new AjaxResultBean();
    
    String[] pics = picIds.split(",");
    
    for (int i = 0; i < pics.length; i++) {
      this.removePic(null, Integer.parseInt(pics[i].trim()), 0);
    }
    
    result.setCode(1);
    return new ModelAndView("jsonView", "json", result);
  }
  
  @SuppressWarnings({})
  @RequestMapping(value = "ajax/showPic.controller")
  public ModelAndView showPic(Model model, @RequestParam("id") int id) {
    
    AjaxResultBean result = new AjaxResultBean();
    
    Map<String,Object> para = new HashMap<String,Object>();
    para.put("id", id);
    para.put("showStatus", 1);
    housePicDao.update("updateShowStatus", para);
    
    HousePicEntity pEntity = housePicDao.load("loadHousePic", id);
    
    // update house entity update time.
    HouseEntity houseEntity = new HouseEntity();
    if (pEntity.getType() == HousePicEntity.Type.ExternalResidence.getValue()
        || pEntity.getType() == HousePicEntity.Type.Residence.getValue()) {
      houseEntity.setResidenceId(pEntity.getResidenceId());
      houseEntity.setUpdateTime(Calendar.getInstance().getTime());
      houseDao.update("updateHouseByResidenceId", houseEntity);
    } else {
      houseEntity.setId(pEntity.getHouseId());
      houseEntity.setUpdateTime(Calendar.getInstance().getTime());
      houseDao.update("updateHouse", houseEntity);
    }
    
    result.setCode(1);
    return new ModelAndView("jsonView", "json", result);
  }
  
  /**
   * 显示旧的小区上传控件
   * 
   * @param model
   * @param residenceId
   * @return
   */
  @RequestMapping(value = "residencePicList.controller")
  public String residencePicUpload(Model model,
      @RequestParam("residenceId") Integer residenceId) {
    
//    Map<Object,Object> map = new HashMap<Object,Object>();
//    map.put("residenceId", residenceId);
//    map.put("type", 1);
//    List<HousePicEntity> list = housePicDao.select("findResidencePicById", map);
//    if (!CollectionUtils.isEmpty(list)) {
//      model.addAttribute("list", list);
//    }
//    
//    Map<Object,Object> externalMap = new HashMap<Object,Object>();
//    externalMap.put("residenceId", residenceId);
//    externalMap.put("type", 0);
//    List<HousePicEntity> externalList = housePicDao.select(
//        "findResidencePicById", externalMap);
//    if (!CollectionUtils.isEmpty(externalList)) {
//      model.addAttribute("externalList", externalList);
//    }
    

    Map<Object,Object> map = new HashMap<Object,Object>();
    
    List<HousePicEntity> list = null;
    map.put("residenceId", residenceId);
    list = housePicDao.select("findResidencePicById", map);
    
    String sort = null;
    List<HousePicSortEntity> picSorts = null;
    Map<String,Object> para = new HashMap<String,Object>();
    para.put("type", HousePicEntity.Type.ExternalResidence.getValue());
    para.put("residenceId", residenceId);
    picSorts = housePicSortDao.select("findHousePicSortByResidenceIdAndType",
        para);
    
    if (CollectionUtils.isNotEmpty(picSorts)) {
      sort = picSorts.get(0).getSort();
    }
    
    if (list != null && StringUtils.isNotBlank(sort)) {
      list = sortPics(list, sort);
    }
    model.addAttribute("list", list);
    
    ResidenceEntity entity = (ResidenceEntity) residenceDao.load(
        "loadResidence", residenceId);
    model.addAttribute("residenceId", residenceId);
    if (entity != null) {
      model.addAttribute("residenceName", entity.getResidenceName());
    }
    return "/residence/residenceImageUpload";
  }
  
  /**
   * 旧的小区上传控件
   * 
   * @param file
   * @param model
   * @param residenceId
   * @return
   */
  @RequestMapping(value = "residencePicUpload.controller")
  public ModelAndView residencePicUpload(
      @RequestParam("imageFile") MultipartFile file, Model model,
      @RequestParam("residenceId") Integer residenceId) {
    
    try {
      this.updateLoadImage(file, 1, residenceId);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
    
    return new ModelAndView(new RedirectView(
        "residencePicList.controller?residenceId=" + residenceId));
  }
  
  /**
   * 外围小区入口的小区图片
   * 
   * @param model
   * @param residenceId
   * @return
   */
  @RequestMapping(value = "external/residencePicConsole.controller")
  public String externalResidencePicConsole(Model model, int residenceId) {
    
    ResidenceEntity entity = (ResidenceEntity) residenceDao.load(
        "loadResidenceById", residenceId);
    if (entity != null) {
      model.addAttribute("entity", entity);
    }
    
    String sort = null;
    List<HousePicSortEntity> picSorts = null;
    
    Map<String,Object> para = new HashMap<String,Object>();
    para.put("type", HousePicEntity.Type.ExternalResidence.getValue());
    para.put("residenceId", residenceId);
    picSorts = housePicSortDao.select("findHousePicSortByResidenceIdAndType",
        para);
    
    if (CollectionUtils.isNotEmpty(picSorts)) {
      sort = picSorts.get(0).getSort();
    }
    
    model.addAttribute("residenceId", residenceId);
    model.addAttribute("sort", sort);
    model.addAttribute("picType",
        HousePicEntity.Type.ExternalResidence.getValue());
    
    return ("external/residence/residencePic");
    
  }
  
  /**
   * 小区图片
   * 
   * @param residenceId
   * @param type
   * @param sort
   * @return
   */
  @RequestMapping(value = "ajax/getPicListByResidenceId.controller")
  public ModelAndView residencePicList(int residenceId, int type) {
    Map<Object,Object> map = new HashMap<Object,Object>();
    // map.put("type", type);
    
    List<HousePicEntity> list = null;
    // if (type == HousePicEntity.Type.Residence.getValue()
    // || type == HousePicEntity.Type.ExternalResidence.getValue()) {
    map.put("residenceId", residenceId);
    list = housePicDao.select("findResidencePicById", map);
    // }
    
    String sort = null;
    List<HousePicSortEntity> picSorts = null;
    Map<String,Object> para = new HashMap<String,Object>();
    para.put("type", HousePicEntity.Type.ExternalResidence.getValue());
    para.put("residenceId", residenceId);
    picSorts = housePicSortDao.select("findHousePicSortByResidenceIdAndType",
        para);
    
    if (CollectionUtils.isNotEmpty(picSorts)) {
      sort = picSorts.get(0).getSort();
    }
    
    if (list != null && StringUtils.isNotBlank(sort)) {
      list = sortPics(list, sort);
    }
    
    // water mask
    if (list != null) {
      for (HousePicEntity p : list) {
        if (p.getCloudUrl() != null) {
          p.setCloudUrl(PicUtils.wrapWaterMask(p.getCloudUrl()));
        }
      }
    }
    
    AjaxResultBean result = new AjaxResultBean();
    result.setBizData(list);
    return new ModelAndView("jsonView", "json", result);
  }
  
  public List<HousePicEntity> sortPics(List<HousePicEntity> pics, String sort) {
    
    List<HousePicEntity> sortedPics = null;
    
    if (StringUtils.isNotBlank(sort)) {
      sortedPics = new ArrayList<HousePicEntity>();
      List<String> picIds = GenericCollections.split(sort, ",");
      for (int i = 0; i < picIds.size(); i++) {
        if (StringUtils.isBlank(picIds.get(i))) {
          continue;
        }
        HousePicEntity entity = new HousePicEntity();
        entity.setId(Integer.valueOf(picIds.get(i)));
        entity = GenericCollections.getData(pics, entity);
        GenericCollections.removeData(pics, entity);
        if (entity != null) {
          sortedPics.add(entity);
        }
      }
      sortedPics.addAll(pics);
      
    } else {
      sortedPics = pics;
    }
    
    return sortedPics;
    
  }
}
