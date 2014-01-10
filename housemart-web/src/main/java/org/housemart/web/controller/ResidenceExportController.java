/**
 * Created on 2013-11-24
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.dao.entities.RegionEntity;
import org.housemart.dao.entities.ResidenceEntity;
import org.housemart.dao.entities.ResidenceMonthDataEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.util.RegularHelper;
import org.housemart.web.views.ResidenceExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResidenceExportController extends BaseController {
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao regionDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceMonthDataDao;
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "residenceExport.controller", method = RequestMethod.GET)
  public ModelAndView residenceList(ModelMap model,
      @RequestParam String regionId, String plateId) {
    
    List<ResidenceEntity> rlist = null;
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("status", 1);
    if (!StringUtils.isEmpty(plateId)) {
      param.put("id", plateId);
      
      Map<String,Object> paramPlate = new HashMap<String,Object>();
      paramPlate.put("id", plateId);
      List<RegionEntity> plates = regionDao
          .select("findRegionById", paramPlate);
      if (CollectionUtils.isNotEmpty(plates)) {
        model.addAttribute("plateName", plates.get(0).getName());
      }
    }
    if (!StringUtils.isEmpty(regionId)) {
      param.put("regionId", regionId);
      
      Map<String,Object> paramRegion = new HashMap<String,Object>();
      paramRegion.put("id", regionId);
      List<RegionEntity> regions = regionDao.select("findRegionById",
          paramRegion);
      if (CollectionUtils.isNotEmpty(regions)) {
        model.addAttribute("regionName", regions.get(0).getName());
      }
    }
    rlist = residenceDao.select("findResidenceList", param);
    
    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    if (rlist != null) {
      for (ResidenceEntity r : rlist) {
        if (r == null) {
          continue;
        }
        
        Map<String,Object> residence = new HashMap<String,Object>();
        residence.put("residenceId", r.getResidenceId());
        residence.put("region", r.getRegionName());
        residence.put("plate", r.getPlateName());
        residence.put("residenceName", r.getResidenceName());
        String headCount = "0";
        if (r.getHeadCount() != null) {
          headCount = RegularHelper.getFirstValue(r.getHeadCount(),
              RegularHelper.REG_NUMBER);
          headCount = StringUtils.isBlank(headCount) ? "0" : headCount;
        }
        residence.put("headCount", headCount);
        
        Map<String,Object> para = new HashMap<String,Object>();
        para.put("residenceId", r.getResidenceId());
        List<ResidenceMonthDataEntity> monthDatas = residenceMonthDataDao
            .select("findByResidence", para);
        
        ResidenceMonthDataEntity monthData = null;
        if (monthDatas != null && monthDatas.size() > 0) {
          monthData = monthDatas.get(0);
        }
        
        if (monthData != null) {
          residence.put("year", monthData.getYear());
          residence.put("month", monthData.getMonth());
          residence.put("annualPriceIncrement",
              monthData.getAnnualPriceIncrement());
          residence.put("annualTurnoverPercent",
              monthData.getAnnualTurnoverPercent());
          residence.put(
              "annualTurnoverRate",
              monthData.getAnnualTurnoverRate() != null ? monthData
                  .getAnnualTurnoverRate() : "");
          residence.put("rentRevenue", monthData.getRentRevenue());
          
          residence.put("averagePrice", monthData.getAveragePrice());
          residence.put("minRentPrice", monthData.getMinRentPrice());
          residence.put("maxRentPrice", monthData.getMaxRentPrice());
        } else {
          residence.put("year", 0);
          residence.put("month", 0);
          residence.put("annualPriceIncrement", 0);
          residence.put("annualTurnoverPercent", 0);
          residence.put("annualTurnoverRate", "");
          residence.put("rentRevenue", 0);
          
          residence.put("averagePrice", 0);
          residence.put("minRentPrice", 0);
          residence.put("maxRentPrice", 0);
        }
        
        list.add(residence);
      }
    }
    
    model.addAttribute("list", list);
    return new ModelAndView(new ResidenceExcelView(), model);
    
  }
}
