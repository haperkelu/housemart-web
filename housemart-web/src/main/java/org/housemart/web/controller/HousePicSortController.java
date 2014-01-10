/**
 * Created on 2012-11-1
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.dao.entities.HouseEntity;
import org.housemart.dao.entities.HouseExtEntity;
import org.housemart.dao.entities.HousePicEntity;
import org.housemart.dao.entities.HousePicSortEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.web.beans.AjaxResultBean;
import org.housemart.web.beans.HousePicOrderByTypeParameterBean;
import org.housemart.web.beans.HousePicOrderByTypeParameterBean.HousePicOrderByTypeItem;
import org.housemart.web.beans.HousePicOrderByTypeParameterListBean;
import org.housemart.web.context.HouseMartContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author pqin
 */
@Controller
public class HousePicSortController extends BaseController {
  
  @Autowired
  GenericDao<HousePicSortEntity> housePicSortDao;
  @Autowired
  GenericDao<HouseEntity> houseDao;
  
  @RequestMapping(value = "addOrUpdateSort.controller")
  public ModelAndView addOrUpdateSort(Model model,
      @RequestParam("picId") int picId, Integer houseId, Integer residenceId,
      String sort) {
    
    int type = 0;
    HousePicEntity pEntity = (HousePicEntity) housePicDao.load("loadHousePic",
        picId);
    
    Map<String,Object> para = new HashMap<String,Object>();
    
    List<HousePicSortEntity> picSorts = null;
    if (pEntity.getType() == HousePicEntity.Type.Residence.getValue()
        || pEntity.getType() == HousePicEntity.Type.ExternalResidence
            .getValue()) {
      type = HousePicEntity.Type.ExternalResidence.getValue();
      para.put("type", type);
      para.put("residenceId", pEntity.getResidenceId());
      picSorts = housePicSortDao.select("findHousePicSortByResidenceIdAndType",
          para);
      
    } else {
      type = pEntity.getType();
      para.put("type", type);
      para.put("houseId", pEntity.getHouseId());
      picSorts = housePicSortDao.select("findHousePicSortByHouseIdAndType",
          para);
    }
    
    if (CollectionUtils.isEmpty(picSorts)) {
      logger.info("AddPicSort::" + sort);
      HousePicSortEntity picSortEntity = new HousePicSortEntity();
      picSortEntity.setHouseId(pEntity.getHouseId());
      picSortEntity.setResidenceId(pEntity.getResidenceId());
      picSortEntity.setType(type);
      picSortEntity.setLastUpdater(HouseMartContext.getCurrentUserId());
      picSortEntity.setSort(sort);
      picSortEntity.setUpdateTime(new Date());
      housePicSortDao.add("addHousePicSort", picSortEntity);
    } else {
      logger.info("UpdatePicSort::" + picSorts.get(0).getId());
      HousePicSortEntity picSortEntity = picSorts.get(0);
      para.clear();
      para.put("sort", sort);
      para.put("updateTime", new Date());
      para.put("lastUpdater", HouseMartContext.getCurrentUserId());
      para.put("id", picSortEntity.getId());
      housePicSortDao.update("updateSort", para);
    }
    
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
    
    AjaxResultBean result = new AjaxResultBean();
    result.setCode(1);
    return new ModelAndView("jsonView", "json", result);
  }
  
  @RequestMapping(value = "ajax/addOrUpdateSort.controller")
  public ModelAndView addOrUpdateSort(Model model, Integer houseId,
      Integer residenceId, String data) {
    
    AjaxResultBean result = new AjaxResultBean();
    
    if ((houseId == null && residenceId == null) || StringUtils.isBlank(data)) {
      result.setCode(2);
      result.setMsg("Invalid parameters!");
      return new ModelAndView("jsonView", "json", result);
    }
    
    ObjectMapper om = new ObjectMapper();
    HousePicOrderByTypeParameterListBean picWithOrder;
    try {
      picWithOrder = om.readValue(data,
          HousePicOrderByTypeParameterListBean.class);
    } catch (Exception e) {
      result.setCode(2);
      result.setMsg("Invalid data parameters : " + data);
      return new ModelAndView("jsonView", "json", result);
    }
    
    for (HousePicOrderByTypeParameterBean orderByTypeBean : picWithOrder
        .getPics()) {
      
      Integer finalHouseId = houseId == null ? 0 : houseId;
      Integer finalResidenceId = null;
      String sort = parameterToSort(orderByTypeBean);
      
      Map<String,Object> para = new HashMap<String,Object>();
      para.put("type", orderByTypeBean.getType());
      
      List<HousePicSortEntity> picSorts = null;
      if (orderByTypeBean.getType() == HousePicEntity.Type.Residence.getValue()
          || orderByTypeBean.getType() == HousePicEntity.Type.ExternalResidence
              .getValue()) {
        // residence pic
        if (residenceId != null) {
          finalResidenceId = residenceId;
        } else {
          HouseExtEntity house = (HouseExtEntity) houseDao.load("loadHouseExt",
              finalHouseId);
          if (house != null) {
            finalResidenceId = house.getResidenceId();
          }
        }
        
        if (finalResidenceId == null) {
          result.setCode(2);
          result.setMsg("Can't find house, id: " + finalHouseId);
          return new ModelAndView("jsonView", "json", result);
        }
        
        para.put("residenceId", finalResidenceId);
        picSorts = housePicSortDao.select(
            "findHousePicSortByResidenceIdAndType", para);
      } else {
        // house pic
        HouseExtEntity house = (HouseExtEntity) houseDao.load("loadHouseExt",
            finalHouseId);
        if (house != null) {
          finalResidenceId = house.getResidenceId();
          para.put("houseId", finalHouseId);
          picSorts = housePicSortDao.select("findHousePicSortByHouseIdAndType",
              para);
        } else {
          result.setCode(2);
          result.setMsg("Can't find house, id: " + finalHouseId);
          return new ModelAndView("jsonView", "json", result);
        }
      }
      
      if (CollectionUtils.isEmpty(picSorts)) {
        logger.info("AddPicSort::" + sort);
        HousePicSortEntity picSortEntity = new HousePicSortEntity();
        picSortEntity.setHouseId(finalHouseId);
        picSortEntity.setResidenceId(finalResidenceId);
        picSortEntity.setType(orderByTypeBean.getType());
        picSortEntity.setLastUpdater(HouseMartContext.getCurrentUserId());
        picSortEntity.setSort(sort);
        picSortEntity.setUpdateTime(new Date());
        housePicSortDao.add("addHousePicSort", picSortEntity);
      } else {
        logger.info("UpdatePicSort::" + picSorts.get(0).getId());
        HousePicSortEntity picSortEntity = picSorts.get(0);
        para.clear();
        para.put("sort", sort);
        para.put("updateTime", new Date());
        para.put("lastUpdater", HouseMartContext.getCurrentUserId());
        para.put("id", picSortEntity.getId());
        housePicSortDao.update("updateSort", para);
      }
      
      // update house entity update time.
      HouseEntity houseEntity = new HouseEntity();
      if (orderByTypeBean.getType() == HousePicEntity.Type.ExternalResidence
          .getValue()
          || orderByTypeBean.getType() == HousePicEntity.Type.Residence
              .getValue()) {
        houseEntity.setResidenceId(finalResidenceId);
        houseEntity.setUpdateTime(Calendar.getInstance().getTime());
        houseDao.update("updateHouseByResidenceId", houseEntity);
      } else {
        houseEntity.setId(finalHouseId);
        houseEntity.setUpdateTime(Calendar.getInstance().getTime());
        houseDao.update("updateHouse", houseEntity);
      }
      
    }
    
    result.setCode(1);
    return new ModelAndView("jsonView", "json", result);
  }
  
  private String parameterToSort(
      HousePicOrderByTypeParameterBean orderByTypeBean) {
    
    if (orderByTypeBean == null || orderByTypeBean.getItems() == null) {
      return null;
    }
    
    String sort = null;
    
    List<HousePicOrderByTypeItem> finalOrder = new ArrayList<HousePicOrderByTypeItem>(
        orderByTypeBean.getItems());
    Collections.sort(finalOrder, new Comparator<HousePicOrderByTypeItem>() {
      
      @Override
      public int compare(HousePicOrderByTypeItem o1, HousePicOrderByTypeItem o2) {
        return (new Integer(o1.getOrder())).compareTo(o2.getOrder());
      }
    });
    
    List<String> sortList = new ArrayList<String>();
    for (HousePicOrderByTypeItem item : finalOrder) {
      sortList.add(String.valueOf(item.getId()));
    }
    sort = StringUtils.join(sortList, ",");
    return sort;
    
  }
  
}
