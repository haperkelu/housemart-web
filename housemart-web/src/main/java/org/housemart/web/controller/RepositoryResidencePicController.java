/**
 * Created on 2013-5-11
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.crawl.pic.service.ResidencePicCrawlerService;
import org.housemart.crawl.pic.service.ResidencePicCrawlerService.UploadResultBean;
import org.housemart.dao.entities.HousePicEntity;
import org.housemart.dao.entities.RepositoryHousePicEntity;
import org.housemart.dao.entities.ResidenceEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.web.context.HouseMartContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RepositoryResidencePicController extends BaseController {
  @Autowired
  GenericDao<RepositoryHousePicEntity> repositoryHousePicDao;
  
  @Autowired
  GenericDao<HousePicEntity> housePicDao;
  
  @Autowired
  GenericDao<ResidenceEntity> residenceDao;
  
  @Autowired
  ResidencePicCrawlerService residencePicCrawlerService;
  
  @RequestMapping(value = "repository/getResidencePicListById.controller")
  public String residencePicList(Model model,
      @RequestParam("residenceId") String residenceId) {
    Map<String,String> para = new HashMap<String,String>();
    para.put("residenceId", residenceId);
    para.put("type", "1");
    List<RepositoryHousePicEntity> picList = repositoryHousePicDao.select(
        "findResidencePicById", para);
    
    model.addAttribute("list", picList);
    model.addAttribute("size", picList == null ? 0 : picList.size());
    
    ResidenceEntity entity = residenceDao.load("loadResidence",
        Integer.valueOf(residenceId));
    model.addAttribute("residenceId", residenceId);
    if (entity != null) {
      model.addAttribute("residenceName", entity.getResidenceName());
    }
    
    return "/repository/residencePicList";
  }
  
  @RequestMapping(value = "repository/bindResidencePics.controller")
  public ModelAndView bindResidencePics(Model model,
      @RequestParam("residenceId") String residenceId, String[] seletedPics) {
    if (ArrayUtils.isEmpty(seletedPics)) {
      return new ModelAndView(new RedirectView(
          "/repository/getResidencePicListById.controller?residenceId="
              + residenceId));
    }
    
    for (String pic : seletedPics) {
      if (StringUtils.isBlank(pic)) continue;
      
      RepositoryHousePicEntity picEntity = repositoryHousePicDao.load(
          "loadHousePic", Integer.valueOf(pic));
      if (picEntity != null && picEntity.getCloudURL() != null) {
        updateLoadImage(picEntity.getCloudURL(),
            HousePicEntity.Type.Residence.getValue(),
            Integer.valueOf(residenceId));
      }
    }
    return new ModelAndView(new RedirectView(
        "/residencePicList.controller?residenceId=" + residenceId));
  }
  
  @RequestMapping(value = "repository/deleteResidencePics.controller")
  public ModelAndView deleteResidencePics(Model model,
      @RequestParam("residenceId") String residenceId, String[] seletedPics) {
    
    for (String pic : seletedPics) {
      if (StringUtils.isBlank(pic)) {
        continue;
      }
      
      Map<Object,Object> map = new HashMap<Object,Object>();
      map.put("id", Integer.valueOf(pic));
      map.put("status", RepositoryHousePicEntity.Status.Invalid.getValue());
      
      repositoryHousePicDao.update("updateStatus", map);
    }
    
    return new ModelAndView(new RedirectView(
        "/repository/getResidencePicListById.controller?residenceId="
            + residenceId));
  }
  
  @RequestMapping(value = "repository/crawlResidencePics.controller")
  public ModelAndView crawlResidencePics(Model model,
      @RequestParam("residenceId") int residenceId,
      @RequestParam("anjukeId") int anjukeId) throws Exception {
    
    List<Integer> picIds = residencePicCrawlerService.crawlAndSaveResidencePic(
        residenceId, anjukeId);
    UploadResultBean resultBean = new UploadResultBean();
    if (picIds != null) {
      resultBean = residencePicCrawlerService.uploadPics(picIds);
    }
    HouseMartContext.setSysMsg("共抓取到" + resultBean.getTotalCount() + "张图片，成功上传"
        + resultBean.getSuccessCount() + "张图片");
    
    return new ModelAndView(new RedirectView(
        "/repository/getResidencePicListById.controller?residenceId="
            + residenceId));
    
  }
  
}
