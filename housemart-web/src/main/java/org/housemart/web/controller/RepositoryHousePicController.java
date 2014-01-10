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
import org.housemart.dao.entities.HousePicEntity;
import org.housemart.dao.entities.RepositoryHousePicEntity;
import org.housemart.dao.entities.ResidenceEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.web.beans.HouseDirection;
import org.housemart.web.beans.HouseRoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RepositoryHousePicController extends BaseController {
  @Autowired
  GenericDao<RepositoryHousePicEntity> repositoryHousePicDao;
  @Autowired
  GenericDao<HousePicEntity> housePicDao;
  @Autowired
  GenericDao<ResidenceEntity> residenceDao;
  
  @RequestMapping(value = "repository/getHousePicList.controller")
  public String housePicList(Model model,
      @RequestParam("residenceId") String residenceId, String houseId,
      HouseRoomType roomType, Integer shiGE, HouseDirection direction,
      Integer area) {
    
    model.addAttribute("residenceId", residenceId);
    model.addAttribute("houseId", houseId);
    model.addAttribute("roomType", roomType);
    model.addAttribute("shiGE", shiGE);
    model.addAttribute("direction", direction);
    model.addAttribute("area", area);
    
    Area areaBean = option2Area(area);
    List<RepositoryHousePicEntity> picList = fecthList(residenceId, roomType,
        shiGE, direction, areaBean.minArea, areaBean.maxArea,
        RepositoryHousePicEntity.Type.HousePic.getValue());
    model.addAttribute("list", picList);
    model.addAttribute("size", picList == null ? 0 : picList.size());
    
    ResidenceEntity entity = residenceDao.load("loadResidence",
        Integer.valueOf(residenceId));
    model.addAttribute("residenceId", residenceId);
    model.addAttribute("houseId", houseId);
    if (entity != null) {
      model.addAttribute("residenceName", entity.getResidenceName());
    }
    
    return "/repository/housePicList";
  }
  
  @RequestMapping(value = "repository/getRoomTypePicList.controller")
  public String roomTypePicList(Model model,
      @RequestParam("residenceId") String residenceId, String houseId,
      HouseRoomType roomType, Integer shiGE, HouseDirection direction,
      Integer area) {
    
    model.addAttribute("residenceId", residenceId);
    model.addAttribute("houseId", houseId);
    model.addAttribute("roomType", roomType);
    model.addAttribute("shiGE", shiGE);
    model.addAttribute("direction", direction);
    model.addAttribute("area", area);
    
    Area areaBean = option2Area(area);
    List<RepositoryHousePicEntity> picList = fecthList(residenceId, roomType,
        shiGE, direction, areaBean.minArea, areaBean.maxArea,
        RepositoryHousePicEntity.Type.RoomType.getValue());
    
    model.addAttribute("list", picList);
    model.addAttribute("size", picList == null ? 0 : picList.size());
    
    ResidenceEntity entity = residenceDao.load("loadResidence",
        Integer.valueOf(residenceId));
    model.addAttribute("residenceId", residenceId);
    model.addAttribute("houseId", houseId);
    if (entity != null) {
      model.addAttribute("residenceName", entity.getResidenceName());
    }
    
    return "/repository/roomTypePicList";
  }
  
  @RequestMapping(value = "repository/bindHousePics.controller")
  public ModelAndView bindHousePics(Model model,
      @RequestParam("residenceId") String residenceId,
      @RequestParam("houseId") String houseId, String[] seletedPics) {
    if (ArrayUtils.isEmpty(seletedPics)) {
      return new ModelAndView(new RedirectView(
          "/repository/getHousePicList.controller?residenceId=" + residenceId
              + "&houseId=" + houseId));
    }
    
    for (String pic : seletedPics) {
      if (StringUtils.isBlank(pic)) {
        continue;
      }
      
      RepositoryHousePicEntity picEntity = repositoryHousePicDao.load(
          "loadHousePic", Integer.valueOf(pic));
      if (picEntity != null && picEntity.getCloudURL() != null) {
        updateLoadImage(picEntity.getCloudURL(),
            HousePicEntity.Type.HousePic.getValue(), Integer.valueOf(houseId));
      }
    }
    
    return new ModelAndView(new RedirectView(
        "/housePicConsole.controller?houseId=" + houseId));
  }
  
  @RequestMapping(value = "repository/deleteHousePics.controller")
  public ModelAndView deleteHousePics(Model model,
      @RequestParam("residenceId") String residenceId,
      @RequestParam("houseId") String houseId,
      @RequestParam("seletedPics") String[] seletedPics) {
    
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
        "/repository/getHousePicList.controller?residenceId=" + residenceId
            + "&houseId=" + houseId));
  }
  
  @RequestMapping(value = "repository/bindRoomTypePics.controller")
  public ModelAndView bindRoomTypePics(Model model,
      @RequestParam("residenceId") String residenceId,
      @RequestParam("houseId") String houseId, String[] seletedPics) {
    if (ArrayUtils.isEmpty(seletedPics)) {
      return new ModelAndView(new RedirectView(
          "/repository/getRoomTypePicList.controller?residenceId="
              + residenceId + "&houseId=" + houseId));
    }
    
    for (String pic : seletedPics) {
      if (StringUtils.isBlank(pic)) {
        continue;
      }
      
      RepositoryHousePicEntity picEntity = repositoryHousePicDao.load(
          "loadHousePic", Integer.valueOf(pic));
      if (picEntity != null && picEntity.getCloudURL() != null) {
        updateLoadImage(picEntity.getCloudURL(),
            HousePicEntity.Type.RoomType.getValue(), Integer.valueOf(houseId));
      }
    }
    return new ModelAndView(new RedirectView(
        "/houseRoomTypeConsole.controller?houseId=" + houseId));
  }
  
  @RequestMapping(value = "repository/deleteRoomTypePics.controller")
  public ModelAndView deleteRoomTypePics(Model model,
      @RequestParam("residenceId") String residenceId,
      @RequestParam("houseId") String houseId,
      @RequestParam("seletedPics") String[] seletedPics) {
    
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
        "/repository/getRoomTypePicList.controller?residenceId=" + residenceId
            + "&houseId=" + houseId));
  }
  
  @SuppressWarnings({"unchecked", "rawtypes"})
  protected List<RepositoryHousePicEntity> fecthList(String residenceId,
      HouseRoomType roomType, Integer shiGE, HouseDirection direction,
      Integer propertyAreaGE, Integer propertyAreaLT, int picType) {
    
    Map para = new HashMap();
    para.put("residenceId", residenceId);
    para.put("minArea", propertyAreaGE);
    para.put("maxArea", propertyAreaLT);
    if (roomType != null) {
      para.put("ting", roomType.getTing());
      para.put("shi", roomType.getShi());
      para.put("wei", roomType.getWei());
      para.put("yangtai", roomType.getYangtai());
    }
    if (direction != null) {
      if (StringUtils.isNotBlank(direction.getEast())) para.put("east",
          direction.getEast());
      if (StringUtils.isNotBlank(direction.getSouth())) para.put("south",
          direction.getSouth());
      if (StringUtils.isNotBlank(direction.getWest())) para.put("west",
          direction.getWest());
      if (StringUtils.isNotBlank(direction.getNorth())) para.put("north",
          direction.getNorth());
    }
    if (shiGE != null) {
      para.put("shiGE", shiGE);
    }
    para.put("type", picType);
    
    List<RepositoryHousePicEntity> picList = repositoryHousePicDao.select(
        "findHousePic", para);
    
    return picList;
  }
  
  public class Area {
    
    Integer minArea;
    Integer maxArea;
    
    public int getMinArea() {
      return minArea;
    }
    
    public void setMinArea(Integer minArea) {
      this.minArea = minArea;
    }
    
    public Integer getMaxArea() {
      return maxArea;
    }
    
    public void setMaxArea(Integer maxArea) {
      this.maxArea = maxArea;
    }
  }
  
  protected Area option2Area(Integer opt) {
    
    Area area = new Area();
    
    if (opt == null) return area;
    
    if (opt == 1) {
      area.setMinArea(0);
      area.setMaxArea(50);
    } else if (opt == 2) {
      area.setMinArea(50);
      area.setMaxArea(70);
    } else if (opt == 3) {
      area.setMinArea(70);
      area.setMaxArea(90);
    } else if (opt == 4) {
      area.setMinArea(90);
      area.setMaxArea(120);
    } else if (opt == 5) {
      area.setMinArea(120);
      area.setMaxArea(150);
    } else if (opt == 6) {
      area.setMinArea(150);
      area.setMaxArea(200);
    } else if (opt == 7) {
      area.setMinArea(200);
    }
    
    return area;
    
  }
}
