/**
 * Created on 2013-9-13
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.dao.entities.ResidenceAuditHistoryEntity;
import org.housemart.dao.entities.ResidenceEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.service.AuditService;
import org.housemart.service.enums.AuditTypeEnum;
import org.housemart.util.PinyinTranslator;
import org.housemart.web.beans.AuditNewResidenceBean;
import org.housemart.web.beans.AuditResidenceStatusAndContentBean;
import org.housemart.web.context.HouseMartContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ResidenceAuditHistoryController extends BaseController {
  
  @Autowired
  private AuditService auditService;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceAuditHistoryDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceDao;
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "auditNewResidence.controller")
  public String auditNewResidence(Model model) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("type", AuditTypeEnum.LoggingAudit.getValue());
    map.put("result", ResidenceAuditHistoryEntity.ResultEnum.Default.getValue());
    List<ResidenceAuditHistoryEntity> list = (List<ResidenceAuditHistoryEntity>) residenceAuditHistoryDao
        .select("findResidenceAuditHistoryList", map);
    
    if (!CollectionUtils.isEmpty(list)) {
      
      List<ResidenceAuditHistoryEntity> filteredList = new ArrayList<ResidenceAuditHistoryEntity>();
      List<AuditNewResidenceBean> newList = new ArrayList<AuditNewResidenceBean>();
      
      // filter
      for (ResidenceAuditHistoryEntity item : list) {
        ResidenceEntity residence = (ResidenceEntity) residenceDao.load(
            "loadResidenceById", item.getResidenceId());
        
        if (residence != null) {
          if ((HouseMartContext.getRegionIds() != null && HouseMartContext
              .getRegionIds().contains(
                  Integer.valueOf(residence.getParentRegionId())))
              || (HouseMartContext.getPlateIds() != null && HouseMartContext
                  .getPlateIds().contains(
                      Integer.valueOf(residence.getRegionId())))) {
            if (HouseMartContext.getRegionMangerIds() != null
                && HouseMartContext.getRegionMangerIds().contains(
                    HouseMartContext.getCurrentUserId())) {
              
              filteredList.add(item);
              
              AuditNewResidenceBean result = new AuditNewResidenceBean();
              result.setResidencePinyin(new PinyinTranslator()
                  .toPinyin(residence.getRegionName()));
              result.setId(item.getId());
              result.setAddTime(item.getAddTime());
              result.setResidenceId(item.getResidenceId());
              result.setResidenceName(residence.getResidenceName());
              result.setPlateName(residence.getPlateName());
              newList.add(result);
              
            }
          }
          
        }
      }
      
      model.addAttribute("list", newList);
    }
    
    return "audit/newResidenceAudit";
  }
  
  // 小区内容
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "auditResidenceContent.controller")
  public String residenceAuditContent(Model model) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("type", AuditTypeEnum.ContentUpdateAudit.getValue());
    map.put("result", ResidenceAuditHistoryEntity.ResultEnum.Default.getValue());
    List<ResidenceAuditHistoryEntity> list = (List<ResidenceAuditHistoryEntity>) residenceAuditHistoryDao
        .select("findResidenceAuditHistoryList", map);
    
    List<ResidenceAuditHistoryEntity> filteredList = new ArrayList<ResidenceAuditHistoryEntity>();
    
    if (!CollectionUtils.isEmpty(list)) {
      // filter
      for (ResidenceAuditHistoryEntity item : list) {
        ResidenceEntity residence = (ResidenceEntity) residenceDao.load(
            "loadResidenceById", item.getResidenceId());
        
        if (residence != null) {
          if ((HouseMartContext.getRegionIds() != null && HouseMartContext
              .getRegionIds().contains(
                  Integer.valueOf(residence.getParentRegionId())))
              || (HouseMartContext.getPlateIds() != null && HouseMartContext
                  .getPlateIds().contains(
                      Integer.valueOf(residence.getRegionId())))) {
            if (HouseMartContext.getRegionMangerIds() != null
                && HouseMartContext.getRegionMangerIds().contains(
                    HouseMartContext.getCurrentUserId())) {
              
              filteredList.add(item);
            }
          }
          
        }
      }
      
      List<AuditResidenceStatusAndContentBean> newList = (List<AuditResidenceStatusAndContentBean>) CollectionUtils
          .collect(filteredList, new Transformer() {
            
            public Object transform(Object obj) {
              ResidenceAuditHistoryEntity item = (ResidenceAuditHistoryEntity) obj;
              ObjectMapper mapper = new ObjectMapper();
              AuditResidenceStatusAndContentBean result = new AuditResidenceStatusAndContentBean();
              ResidenceEntity residence = (ResidenceEntity) residenceDao.load(
                  "loadResidenceById", item.getResidenceId());
              
              try {
                AuditResidenceStatusAndContentBean orinal = mapper.readValue(
                    item.getPreContent(),
                    AuditResidenceStatusAndContentBean.class);
                AuditResidenceStatusAndContentBean newBean = mapper.readValue(
                    item.getPostContent(),
                    AuditResidenceStatusAndContentBean.class);
                
                BeanUtils.copyProperties(orinal, result);
                String content = StringUtils.EMPTY;
                
                if (!StringUtils.equals(orinal.getAddress(),
                    newBean.getAddress())) {
                  content += "地址:" + orinal.getAddress() + "==>"
                      + newBean.getAddress() + "<br/>";
                }
                
                if (!StringUtils.equals(orinal.getDeveloper(),
                    newBean.getDeveloper())) {
                  content += "开发商:" + orinal.getDeveloper() + "==>"
                      + newBean.getDeveloper() + "<br/>";
                }
                
                if (!StringUtils.equals(orinal.getGreenRate(),
                    newBean.getGreenRate())) {
                  content += "绿化率:" + orinal.getGreenRate() + "==>"
                      + newBean.getGreenRate() + "<br/>";
                }
                
                if (!StringUtils.equals(orinal.getHeadCount(),
                    newBean.getHeadCount())) {
                  content += "总户数:" + orinal.getHeadCount() + "==>"
                      + newBean.getHeadCount() + "<br/>";
                }
                
                if (!StringUtils.equals(orinal.getParking(),
                    newBean.getParking())) {
                  content += "停车位:" + orinal.getParking() + "==>"
                      + newBean.getParking() + "<br/>";
                }
                
                if (!StringUtils.equals(orinal.getPropertyFee(),
                    newBean.getPropertyFee())) {
                  content += "物业费:" + orinal.getPropertyFee() + "==>"
                      + newBean.getPropertyFee() + "<br/>";
                }
                
                if (!StringUtils.equals(orinal.getPropertyType(),
                    newBean.getPropertyType())) {
                  content += "类型:" + orinal.getPropertyType() + "==>"
                      + newBean.getPropertyType() + "<br/>";
                }
                
                if (!StringUtils.equals(orinal.getVolumeRate(),
                    newBean.getVolumeRate())) {
                  content += "容积率:" + orinal.getVolumeRate() + "==>"
                      + newBean.getVolumeRate() + "<br/>";
                }
                
                if (!StringUtils.equals(orinal.getFinishedTime(),
                    newBean.getFinishedTime())) {
                  content += "竣工时间:" + orinal.getFinishedTime() + "==>"
                      + newBean.getFinishedTime() + "<br/>";
                }
                
                result.setAddTime(item.getAddTime());
                result.setContent(content);
                result.setId(item.getId());
                result.setResidenceId(item.getResidenceId());
                result.setResidenceName(residence.getResidenceName());
                result.setPlateName(residence.getPlateName());
              } catch (Exception e) {
                logger.error("audit new residence json parse error", e);
              }
              return result;
            }
          });
      model.addAttribute("list", newList);
    }
    return "audit/updateResidenceAudit";
  }
  
  @RequestMapping(value = "audit/approveNewResidence.controller")
  public ModelAndView approveNewResidence(
      @RequestParam("residenceId") int residenceId, @RequestParam("id") int id) {
    
    auditService.approveNewResidence(id);
    
    return new ModelAndView(new RedirectView("/auditNewResidence.controller"));
    
  }
  
  @RequestMapping(value = "audit/rejectNewResidence.controller")
  public ModelAndView rejectNewResidence(
      @RequestParam("residenceId") int residenceId, @RequestParam("id") int id) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("id", id);
    map.put("result", 2);
    residenceAuditHistoryDao.update("updateAuditHistoryResultStatus", map);
    
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("id", residenceId);
    param.put("status", 2);
    residenceDao.update("updateStatus", param);
    
    return new ModelAndView(new RedirectView("/auditNewResidence.controller"));
  }
  
  @RequestMapping(value = "audit/approveResidenceStatusAndContent.controller")
  public ModelAndView approveStatusAndContent(
      @RequestParam("residenceId") int residenceId, @RequestParam("id") int id) {
    
    auditService.approveResidenceStatusAndContent(residenceId, id);
    
    return new ModelAndView(new RedirectView(
        "/auditResidenceContent.controller"));
    
  }
  
  @RequestMapping(value = "audit/rejectResidenceStatusAndContent.controller")
  public ModelAndView rejectStatusAndContent(
      @RequestParam("residenceId") int residenceId, @RequestParam("id") int id) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("id", id);
    map.put("result", 2);
    residenceAuditHistoryDao.update("updateAuditHistoryResultStatus", map);
    
    return new ModelAndView(new RedirectView(
        "/auditResidenceContent.controller"));
    
  }
  
}
