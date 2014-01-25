/**
 * Created on 2012-10-21
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
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.dao.entities.HouseAuditHistoryEntity;
import org.housemart.dao.entities.HouseContactEntity;
import org.housemart.dao.entities.HouseEntity;
import org.housemart.dao.entities.HouseExtEntity;
import org.housemart.dao.entities.RegionEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.service.AuditService;
import org.housemart.service.HouseService;
import org.housemart.service.enums.ActionType;
import org.housemart.service.enums.AuditTypeEnum;
import org.housemart.service.enums.GranularityType;
import org.housemart.service.enums.HouseEnumUtils;
import org.housemart.service.enums.ResourceType;
import org.housemart.service.priviledge.PrivilegeService;
import org.housemart.util.PinyinTranslator;
import org.housemart.web.beans.AjaxResultBean;
import org.housemart.web.beans.AuditCommitterChangeBean;
import org.housemart.web.beans.AuditInvalidHouseBean;
import org.housemart.web.beans.AuditNewHouseBean;
import org.housemart.web.beans.AuditStatusAndContentBean;
import org.housemart.web.context.HouseMartContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author pqin
 */
@Controller
public class HouseAuditHistoryController extends BaseController {
  
  private static Log logger = LogFactory.getLog(HouseAuditHistoryController.class);
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao houseAuditHistoryDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao houseDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  private GenericDao houseContactDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao houseSaleDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao houseRentDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao housePropertyDao;
  
  @Autowired
  private AuditService auditService;
  
  @Autowired
  private HouseService houseService;
  
  @Autowired
  PrivilegeService privilegeService;
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "ajax/getContentUpdateHistoryByHouseId.controller")
  public ModelAndView getHouseAuditHistoryList(Integer houseId, Integer type) {
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("houseId", houseId);
    map.put("type", type);
    // map.put("result", 1);
    List<RegionEntity> list = houseAuditHistoryDao.select("findHouseAuditHistoryList", map);
    AjaxResultBean result = new AjaxResultBean();
    result.setBizData(list);
    return new ModelAndView("jsonView", "json", result);
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "getMyUpdateHistory.controller")
  public String getMyHouseAuditHistoryList(Model model) {
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("commiterId", HouseMartContext.getCurrentUserId());
    // map.put("result", 1);
    List<RegionEntity> list = houseAuditHistoryDao.select("findHouseAuditHistoryList", map);
    model.addAttribute("list", list);
    return "audit/myUpdateHistory";
  }
  
  @RequestMapping(value = "ajax/markHouseInvalid.controller")
  public ModelAndView markHouseInvalid(Integer houseId) {
    
    int power = privilegeService.hasPower(HouseMartContext.getCurrentUserId(), ActionType.Update, ResourceType.House,
        GranularityType.Field, houseId, null);
    if (power == 3) { // 直接修改
      HouseEntity house = (HouseExtEntity) houseDao.load("loadHouseExt", houseId);
      house.setStatus(HouseEntity.StatusEnum.Invalid.value);
      houseDao.update("updateHouse", house);
    }
    
    if (power == 2) {
      auditService.requestHouseInvalid(houseId);
    }
    
    AjaxResultBean result = new AjaxResultBean();
    return new ModelAndView("jsonView", "json", result);
  }
  
  @RequestMapping(value = "ajax/committerUpdateAudit.controller")
  public ModelAndView committerUpdateAudit(Integer houseId, String contactName, String contactMobile, String housePropertyArea) {
    
    int power = privilegeService.hasPower(HouseMartContext.getCurrentUserId(), ActionType.Update, ResourceType.House,
        GranularityType.Field, houseId, null);
    
    if (power == 3) { // 直接修改
      HouseContactEntity contact = houseService.loadHouseContact(houseId);
      contact.setName(contactName);
      contact.setMobile(contactMobile);
      contact.setUpdateTime(new Date());
      contact.setCommitter(HouseMartContext.getCurrentUserId());
      houseContactDao.update("updateHouseContact", contact);
      
      HouseEntity house = (HouseExtEntity) houseDao.load("loadHouseExt", houseId);
      house.setPropertyArea(Float.valueOf(housePropertyArea));
      houseDao.update("updateHouse", house);
      
    }
    
    if (power == 2) {
      auditService.requestHouseCommitterChange(houseId, contactName, contactMobile, housePropertyArea);
    }
    
    AjaxResultBean result = new AjaxResultBean();
    result.setCode(1);
    return new ModelAndView("jsonView", "json", result);
  }
  
  /**
   * 新房源审核
   * 
   * @param model
   * @return
   */
  @RequestMapping(value = "houseAudit.controller")
  public String houseAuditNewHouse(Model model, Integer sourceType, String residenceName, String creatorName) {
    
    getAuditListForNewHouse(model, sourceType, residenceName, creatorName);
    
    return "audit/newHouseAudit";
  }
  
  /**
   * 登盘人变更审核
   * 
   * @param model
   * @return
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "houseAuditCommitterChange.controller")
  public String houseAuditCommitterChange(Model model) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("result", 0);
    map.put("type", AuditTypeEnum.CommitterUpdateAudit.getValue());
    List<HouseAuditHistoryEntity> list = (List<HouseAuditHistoryEntity>) houseAuditHistoryDao.select("findHouseAuditHistoryList",
        map);
    if (!CollectionUtils.isEmpty(list)) {
      CollectionUtils.filter(list, new Predicate() {
        
        public boolean evaluate(Object object) {
          HouseAuditHistoryEntity item = (HouseAuditHistoryEntity) object;
          
          HouseExtEntity house = (HouseExtEntity) houseDao.load("loadHouseExt", item.getHouseId());
          if (house != null) {
            
            if ((HouseMartContext.getRegionIds() != null && house.getRegionParentId() != null && HouseMartContext.getRegionIds()
                .contains(house.getRegionParentId().intValue()))
                || (HouseMartContext.getPlateIds() != null && house.getRegionId() != null && HouseMartContext.getPlateIds()
                    .contains(house.getRegionId().intValue()))
                || (HouseMartContext.getResidenceIds() != null && house.getResidenceId() != null && HouseMartContext
                    .getResidenceIds().contains(house.getResidenceId()))) {
              if (HouseMartContext.getRegionMangerIds() != null
                  && HouseMartContext.getRegionMangerIds().contains(HouseMartContext.getCurrentUserId())) {
                return true;
              }
            }
            
          }
          return false;
        }
        
      });
      List<AuditNewHouseBean> newList = (List<AuditNewHouseBean>) CollectionUtils.collect(list, new Transformer() {
        
        public Object transform(Object obj) {
          HouseAuditHistoryEntity item = (HouseAuditHistoryEntity) obj;
          ObjectMapper mapper = new ObjectMapper();
          AuditCommitterChangeBean result = new AuditCommitterChangeBean();
          
          try {
            AuditNewHouseBean orinal = mapper.readValue(item.getPreContent(), AuditNewHouseBean.class);
            AuditNewHouseBean newBean = mapper.readValue(item.getPostContent(), AuditNewHouseBean.class);
            orinal.setAddTime(item.getAddTime());
            orinal.setHouseId(item.getHouseId());
            
            BeanUtils.copyProperties(orinal, result);
            result.setNewContactInfo(newBean.getContactInfo());
            result.setId(item.getId());
            
          } catch (Exception e) {
            logger.error("audit new house json parse error", e);
          }
          return result;
        }
      });
      model.addAttribute("list", newList);
    }
    
    return "audit/houseCommitterChange";
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "houseInvalidAudit.controller")
  public String houseAuditInvalid(Model model) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("type", AuditTypeEnum.InvalidLoggingAudit.getValue());
    map.put("result", 0);
    List<HouseAuditHistoryEntity> list = (List<HouseAuditHistoryEntity>) houseAuditHistoryDao.select("findHouseAuditHistoryList",
        map);
    if (!CollectionUtils.isEmpty(list)) {
      CollectionUtils.filter(list, new Predicate() {
        
        public boolean evaluate(Object object) {
          HouseAuditHistoryEntity item = (HouseAuditHistoryEntity) object;
          if (isManager() && isHouseInOwnResidence(item.getHouseId())) {
            return true;
          }
          
          return false;
        }
        
      });
      
      List<AuditInvalidHouseBean> newList = (List<AuditInvalidHouseBean>) CollectionUtils.collect(list, new Transformer() {
        
        public Object transform(Object obj) {
          HouseAuditHistoryEntity item = (HouseAuditHistoryEntity) obj;
          AuditInvalidHouseBean result = new AuditInvalidHouseBean();
          
          if (!StringUtils.isEmpty(item.getPreContent())) {
            ObjectMapper mapper = new ObjectMapper();
            try {
              result = mapper.readValue(item.getPreContent(), AuditInvalidHouseBean.class);
              result.setId(item.getId());
              result.setAddTime(item.getAddTime());
              result.setHouseId(item.getHouseId());
              result.setCommitterName("getUser:" + item.getCommitterId());
            } catch (Exception e) {
              logger.error("audit new house json parse error", e);
            }
            
          }
          
          return result;
        }
      });
      
      model.addAttribute("list", newList);
    }
    
    return "audit/invalidHouseAudit";
  }
  
  @RequestMapping(value = "audit/approveNewHouse.controller")
  public ModelAndView approveNewHouse(@RequestParam("houseId") int houseId, @RequestParam("id") int id) {
    
    auditService.approveNewHouse(houseId, id);
    HouseExtEntity entityExt = (HouseExtEntity) houseDao.load("loadHouseExt", houseId);
    
    return new ModelAndView(new RedirectView("/houseAudit.controller?sourceType=" + entityExt.getSourceType()));
    
  }
  
  @RequestMapping(value = "audit/approveNewHouses.controller")
  public ModelAndView approveNewHouses(@RequestParam("houseIds") String houseIds, @RequestParam("ids") String ids) {
    
    if (StringUtils.isNotBlank(houseIds) && StringUtils.isNotBlank(ids)) {
      String[] hs = houseIds.split(",");
      String[] is = ids.split(",");
      auditService.approveNewHouses(hs, is);
      
      HouseExtEntity entityExt = (HouseExtEntity) houseDao.load("loadHouseExt", Integer.valueOf(hs[0]));
      return new ModelAndView(new RedirectView("/houseAudit.controller?sourceType=" + entityExt.getSourceType()));
    }
    
    return new ModelAndView(new RedirectView("/houseAudit.controller?sourceType=1"));
    
  }
  
  @RequestMapping(value = "audit/approveNewHouseInDetailPage.controller")
  public ModelAndView approveNewHouseInDetailPage(@RequestParam("houseId") int houseId, @RequestParam("id") int id) {
    
    auditService.approveNewHouse(houseId, id);
    
    return new ModelAndView(new RedirectView("/audit/nextNewHouseAudit.controller"));
    
  }
  
  @RequestMapping(value = "audit/nextNewHouseAudit.controller")
  public ModelAndView nextNewHouseAudit() {
    
    int houseId = 0;
    int auditId = 0;
    List<AuditNewHouseBean> auditList = getAuditListForNewHouse(null, HouseEntity.SourceTypeEnum.external.value, null, null);
    if (!CollectionUtils.isEmpty(auditList)) {
      houseId = auditList.get(0).getHouseId();
      auditId = auditList.get(0).getId();
    }
    
    if (houseId > 0 && auditId > 0) {
      return new ModelAndView(new RedirectView("/external/houseView.controller?houseId=" + houseId + "&auditId=" + auditId));
    } else {
      return new ModelAndView(new RedirectView("/houseAudit.controller?sourceType=3"));
    }
    
  }
  
  @RequestMapping(value = "audit/rejectNewHouse.controller")
  public ModelAndView rejectNewHouse(@RequestParam("houseId") int houseId, @RequestParam("id") int id,
      @RequestParam("comments") String comments) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("id", id);
    map.put("result", 2);
    map.put("comments", comments);
    houseAuditHistoryDao.update("updateAuditHistoryResultStatus", map);
    
    HouseEntity entity = new HouseEntity();
    entity.setId(houseId);
    entity.setStatus(2);
    entity.setUpdateTime(new Date());
    houseDao.update("updateHouse", entity);
    
    HouseExtEntity entityExt = (HouseExtEntity) houseDao.load("loadHouseExt", houseId);
    
    return new ModelAndView(new RedirectView("/houseAudit.controller?sourceType=" + entityExt.getSourceType()));
    
  }
  
  @RequestMapping(value = "audit/rejectNewHouses.controller")
  public ModelAndView rejectNewHouses(@RequestParam("houseIds") String houseIds, @RequestParam("ids") String ids,
      @RequestParam("comments") String comments) {
    
    int sourceType = HouseEntity.SourceTypeEnum.internal.value;
    
    if (StringUtils.isNotBlank(houseIds) && StringUtils.isNotBlank(ids)) {
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("ids", ids);
      map.put("result", 2);
      map.put("comments", comments);
      houseAuditHistoryDao.update("updateAuditHistoryResultStatusByIds", map);
      
      String[] hs = houseIds.split(",");
      
      for (String hsId : hs) {
        HouseEntity entity = new HouseEntity();
        entity.setId(Integer.valueOf(hsId));
        entity.setStatus(2);
        entity.setUpdateTime(new Date());
        houseDao.update("updateHouse", entity);
      }
      
      HouseExtEntity entityExt = (HouseExtEntity) houseDao.load("loadHouseExt", Integer.valueOf(hs[0]));
      sourceType = entityExt.getSourceType();
    }
    
    return new ModelAndView(new RedirectView("/houseAudit.controller?sourceType=" + sourceType));
    
  }
  
  @RequestMapping(value = "audit/rejectNewHouseInDetailPage.controller")
  public ModelAndView rejectNewHouseInDetailPage(@RequestParam("houseId") int houseId, @RequestParam("id") int id,
      @RequestParam("comments") String comments) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("id", id);
    map.put("result", 2);
    map.put("comments", comments);
    houseAuditHistoryDao.update("updateAuditHistoryResultStatus", map);
    
    HouseEntity entity = new HouseEntity();
    entity.setId(houseId);
    entity.setStatus(2);
    entity.setUpdateTime(new Date());
    houseDao.update("updateHouse", entity);
    
    return new ModelAndView(new RedirectView("/audit/nextNewHouseAudit.controller"));
    
  }
  
  @RequestMapping(value = "audit/approveCommitterChange.controller")
  public ModelAndView approveCommitterChange(@RequestParam("houseId") int houseId, @RequestParam("id") int id) {
    
    try {
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("id", id);
      map.put("result", 1);
      houseAuditHistoryDao.update("updateAuditHistoryResultStatus", map);
      
      HouseExtEntity entityExt = (HouseExtEntity) houseDao.load("loadHouseExt", houseId);
      HouseContactEntity contact = entityExt.generateHouseContactEntity();
      
      HouseAuditHistoryEntity audit = (HouseAuditHistoryEntity) houseAuditHistoryDao.load("loadHouseAuditHistory", id);
      ObjectMapper mapper = new ObjectMapper();
      AuditCommitterChangeBean bean = mapper.readValue(audit.getPostContent(), AuditCommitterChangeBean.class);
      String[] contactArr = bean.getContactInfo().split("/");
      contact.setName(contactArr[0]);
      contact.setMobile(contactArr[1]);
      contact.setUpdateTime(Calendar.getInstance().getTime());
      contact.setCommitter(HouseMartContext.getCurrentUserId());
      houseContactDao.update("updateHouseContactByHouseId", contact);
      
      HouseEntity entity = new HouseEntity();
      entity.setId(houseId);
      entity.setPropertyArea(Float.parseFloat(contactArr[2]));
      entity.setUpdateTime(Calendar.getInstance().getTime());
      houseDao.update("updateHouse", entity);
      
    } catch (Exception e) {
      logger.error("[approveCommitterChange]audit error", e);
    }
    
    return new ModelAndView(new RedirectView("/houseAuditCommitterChange.controller"));
    
  }
  
  @RequestMapping(value = "audit/rejectCommitterChange.controller")
  public ModelAndView rejectCommitterChange(@RequestParam("houseId") int houseId, @RequestParam("id") int id) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("id", id);
    map.put("result", 2);
    houseAuditHistoryDao.update("updateAuditHistoryResultStatus", map);
    
    return new ModelAndView(new RedirectView("/houseAuditCommitterChange.controller"));
    
  }
  
  @RequestMapping(value = "audit/approveInvalidHouse.controller")
  public ModelAndView approveInvalidHouse(@RequestParam("houseId") int houseId, @RequestParam("id") int id) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("id", id);
    map.put("result", 1);
    houseAuditHistoryDao.update("updateAuditHistoryResultStatus", map);
    
    HouseEntity entity = new HouseEntity();
    entity.setId(houseId);
    entity.setUpdateTime(Calendar.getInstance().getTime());
    entity.setStatus(2);
    houseDao.update("updateHouse", entity);
    
    return new ModelAndView(new RedirectView("/houseInvalidAudit.controller"));
    
  }
  
  @RequestMapping(value = "audit/rejectInvalidHouse.controller")
  public ModelAndView rejectInvalidHouse(@RequestParam("houseId") int houseId, @RequestParam("id") int id) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("id", id);
    map.put("result", 1);
    houseAuditHistoryDao.update("updateAuditHistoryResultStatus", map);
    
    return new ModelAndView(new RedirectView("/houseInvalidAudit.controller"));
    
  }
  
  // 盘源状态与内容变更
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "houseAuditStatusAndContent.controller")
  public String houseAuditStatusAndContent(Model model) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("type", 5);
    map.put("result", 0);
    List<HouseAuditHistoryEntity> list = (List<HouseAuditHistoryEntity>) houseAuditHistoryDao.select("findHouseAuditHistoryList",
        map);
    if (!CollectionUtils.isEmpty(list)) {
      CollectionUtils.filter(list, new Predicate() {
        
        public boolean evaluate(Object object) {
          HouseAuditHistoryEntity item = (HouseAuditHistoryEntity) object;
          
          HouseExtEntity house = (HouseExtEntity) houseDao.load("loadHouseExt", item.getHouseId());
          if (house != null) {
            
            if (isAdmin()) {
              return true;
            } else {
              if ((HouseMartContext.getRegionIds() != null && house.getRegionParentId() != null && HouseMartContext.getRegionIds()
                  .contains(house.getRegionParentId().intValue()))
                  || (HouseMartContext.getPlateIds() != null && house.getRegionId() != null && HouseMartContext.getPlateIds()
                      .contains(house.getRegionId().intValue()))
                  || (HouseMartContext.getResidenceIds() != null && house.getResidenceId() != null && HouseMartContext
                      .getResidenceIds().contains(house.getResidenceId()))) {
                if (HouseMartContext.getRegionMangerIds() != null
                    && HouseMartContext.getRegionMangerIds().contains(HouseMartContext.getCurrentUserId())) {
                  return true;
                }
              }
            }
            
          }
          return false;
        }
        
      });
      
      List<AuditStatusAndContentBean> newList = (List<AuditStatusAndContentBean>) CollectionUtils.collect(list, new Transformer() {
        
        public Object transform(Object obj) {
          HouseAuditHistoryEntity item = (HouseAuditHistoryEntity) obj;
          ObjectMapper mapper = new ObjectMapper();
          AuditStatusAndContentBean result = new AuditStatusAndContentBean();
          
          try {
            AuditStatusAndContentBean orinal = mapper.readValue(item.getPreContent(), AuditStatusAndContentBean.class);
            AuditStatusAndContentBean newBean = mapper.readValue(item.getPostContent(), AuditStatusAndContentBean.class);
            
            orinal.setAddTime(item.getAddTime());
            orinal.setHouseId(item.getHouseId());
            
            BeanUtils.copyProperties(orinal, result);
            String content = StringUtils.EMPTY;
            
            if (orinal.getSaleStatus() != newBean.getSaleStatus()) {
              content += "售房状态:" + HouseEnumUtils.getHouseSaleStatusName(orinal.getSaleStatus()) + "==>"
                  + HouseEnumUtils.getHouseSaleStatusName(newBean.getSaleStatus()) + "<br/>";
            }
            
            if (orinal.getRentStatus() != newBean.getRentStatus()) {
              content += "租房状态:" + HouseEnumUtils.getHouseRentStatusName(orinal.getRentStatus()) + "==>"
                  + HouseEnumUtils.getHouseRentStatusName(newBean.getRentStatus()) + "<br/>";
            }
            
            if (orinal.getArea() != newBean.getArea()) {
              content += "面积:" + orinal.getArea() + "==>" + newBean.getArea() + "<br/>";
            }
            
            if (!StringUtils.equals(orinal.getContactName(), newBean.getContactName())) {
              content += "业主姓名:" + orinal.getContactName() + "==>" + newBean.getContactName() + "<br/>";
            }
            
            if (!StringUtils.equals(orinal.getContactMobile(), newBean.getContactMobile())) {
              content += "业主电话:" + orinal.getContactMobile() + "==>" + newBean.getContactMobile() + "<br/>";
            }
            
            //
            if (orinal.getRoomType() != newBean.getRoomType()) {
              content += "房型:" + orinal.getRoomType() + "==>" + newBean.getRoomType() + "<br/>";
            }
            
            if (orinal.getBuildTime() != null && !orinal.getBuildTime().equals(newBean.getBuildTime())) {
              content += "建房时间:" + orinal.getBuildTime() + "==>" + newBean.getBuildTime() + "<br/>";
            }
            
            if (!StringUtils.equals(orinal.getHouseType(), newBean.getHouseType())) {
              content += "类型:" + orinal.getHouseType() + "==>" + newBean.getHouseType() + "<br/>";
            }
            
            if (orinal.getOccupiedArea() != newBean.getOccupiedArea()) {
              content += "占地面积:" + orinal.getOccupiedArea() + "==>" + newBean.getOccupiedArea() + "<br/>";
            }
            
            if (orinal.getDecorating() != newBean.getDecorating()) {
              content += "装修:" + orinal.getDecorating() + "==>" + newBean.getDecorating() + "<br/>";
            }
            
            if (orinal.getSalePrice() != newBean.getSalePrice()) {
              content += "挂牌价:" + orinal.getSalePrice() + "==>" + newBean.getSalePrice() + "<br/>";
            }
            
            if (orinal.getSaleBasePrice() != newBean.getSaleBasePrice()) {
              content += "底价:" + orinal.getSaleBasePrice() + "==>" + newBean.getSaleBasePrice() + "<br/>";
            }
            
            if (!StringUtils.equals(orinal.getDealType(), newBean.getDealType())) {
              content += "交易类型:" + orinal.getDealType() + "==>" + newBean.getDealType() + "<br/>";
            }
            
            if (orinal.getSaleRec() != newBean.getSaleRec()) {
              content += "出售状态:" + orinal.getSaleRec() + "==>" + newBean.getSaleRec() + "<br/>";
            }
            
            if (!StringUtils.equals(orinal.getSaleTagList(), newBean.getSaleTagList())) {
              content += "售房特色:" + orinal.getSaleTagList() + "==>" + newBean.getSaleTagList() + "<br/>";
            }
            
            if (!StringUtils.equals(orinal.getSaleMemo(), newBean.getSaleMemo())) {
              content += "特殊说明:" + orinal.getSaleMemo() + "==>" + newBean.getSaleMemo() + "<br/>";
            }
            
            if (orinal.getRentPrice() != newBean.getRentPrice()) {
              content += "租金:" + orinal.getRentPrice() + "==>" + newBean.getRentPrice() + "<br/>";
            }
            
            if (orinal.getRentBasePrice() != newBean.getRentBasePrice()) {
              content += "租金底价:" + orinal.getRentBasePrice() + "==>" + newBean.getRentBasePrice() + "<br/>";
            }
            
            if (orinal.getRentType() != newBean.getRentType()) {
              content += "租房类型:" + orinal.getRentType() + "==>" + newBean.getRentType() + "<br/>";
            }
            
            if (orinal.getRentRec() != newBean.getRentRec()) {
              content += "出租状态:" + orinal.getRentRec() + "==>" + newBean.getRentRec() + "<br/>";
            }
            
            if (!StringUtils.equals(orinal.getRentTagList(), newBean.getRentTagList())) {
              content += "租房特色:" + orinal.getRentTagList() + "==>" + newBean.getRentTagList() + "<br/>";
            }
            
            if (!StringUtils.equals(orinal.getRentMemo(), newBean.getRentMemo())) {
              content += "特色说明:" + orinal.getRentMemo() + "==>" + newBean.getRentMemo() + "<br/>";
            }
            
            if (orinal.getViewHouseType() != newBean.getViewHouseType()) {
              content += "看房方式:" + orinal.getViewHouseType() + "==>" + newBean.getViewHouseType() + "<br/>";
            }
            
            result.setContent(content);
            result.setId(item.getId());
            result.setAddTime(item.getAddTime());
            
          } catch (Exception e) {
            logger.error("audit new house json parse error", e);
          }
          return result;
        }
      });
      model.addAttribute("list", newList);
    }
    return "audit/houseAuditStatusAndContent";
  }
  
  @RequestMapping(value = "audit/approveStatusAndContent.controller")
  public ModelAndView approveStatusAndContent(@RequestParam("houseId") int houseId, @RequestParam("id") int id) {
    
    auditService.approveStatusAndContent(houseId, id);
    
    return new ModelAndView(new RedirectView("/houseAuditStatusAndContent.controller"));
    
  }
  
  @RequestMapping(value = "audit/RejectStatusAndContent.controller")
  public ModelAndView auditRejectStatusAndContent(@RequestParam("houseId") int houseId, @RequestParam("id") int id) {
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("id", id);
    map.put("result", 2);
    houseAuditHistoryDao.update("updateAuditHistoryResultStatus", map);
    return new ModelAndView(new RedirectView("/houseAuditStatusAndContent.controller"));
    
  }
  
  /**
   * 
   * @param houseId
   * @return
   */
  private boolean isHouseInOwnResidence(int houseId) {
    
    HouseExtEntity house = (HouseExtEntity) houseDao.load("loadHouseExt", houseId);
    if (house != null) {
      return (HouseMartContext.getRegionIds() != null && house.getRegionParentId() != null && HouseMartContext.getRegionIds()
          .contains(house.getRegionParentId().intValue()))
          || (HouseMartContext.getPlateIds() != null && house.getRegionId() != null && HouseMartContext.getPlateIds().contains(
              house.getRegionId().intValue()))
          || (HouseMartContext.getResidenceIds() != null && house.getResidenceId() != null && HouseMartContext.getResidenceIds()
              .contains(house.getResidenceId()));
    }
    
    return false;
  }
  
  /**
   * 
   * @return
   */
  private boolean isManager() {
    
    return HouseMartContext.getRegionMangerIds() != null
        && HouseMartContext.getRegionMangerIds().contains(HouseMartContext.getCurrentUserId());
    
  }
  
  private boolean isAdmin() {
    return HouseMartContext.getCurrentUserId() == 0;
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "rejectedHouseList.controller")
  public String rejectedHouseList(Model model, int sourceType, int auditType) {
    
    List<AuditNewHouseBean> auditList = new ArrayList<AuditNewHouseBean>();
    List<HouseAuditHistoryEntity> tmpAuditList = null;
    
    if (isAdmin()) {
      Map<String,Object> para = new HashMap<String,Object>();
      para.put("sourceType", sourceType);
      para.put("auditType", auditType);
      tmpAuditList = houseAuditHistoryDao.select("findRejectedList", para);
    } else {
      if (isManager() && HouseMartContext.getResidenceIds() != null) {
        Map<String,Object> para = new HashMap<String,Object>();
        para.put("residenceIds", StringUtils.join(HouseMartContext.getResidenceIds(), ","));
        para.put("regionIds", StringUtils.join(HouseMartContext.getRegionIds(), ","));
        para.put("plateIds", StringUtils.join(HouseMartContext.getPlateIds(), ","));
        para.put("sourceType", sourceType);
        para.put("auditType", auditType);
        tmpAuditList = houseAuditHistoryDao.select("findRejectedList", para);
      }
    }
    
    if (tmpAuditList != null) {
      for (HouseAuditHistoryEntity item : tmpAuditList) {
        HouseExtEntity house = (HouseExtEntity) houseDao.load("loadHouseExt", item.getHouseId());
        if (StringUtils.isNotBlank(house.getAccountName())) {
          item.setHouseCreatorName(house.getAccountName());
        }
      }
      
      auditList = (List<AuditNewHouseBean>) CollectionUtils.collect(tmpAuditList, new Transformer() {
        
        public Object transform(Object obj) {
          HouseAuditHistoryEntity item = (HouseAuditHistoryEntity) obj;
          AuditNewHouseBean result = new AuditNewHouseBean();
          
          if (!StringUtils.isEmpty(item.getPostContent())) {
            ObjectMapper mapper = new ObjectMapper();
            try {
              result = mapper.readValue(item.getPostContent(), AuditNewHouseBean.class);
              result.setResidencePinyin(new PinyinTranslator().toPinyin(result.getResidenceName()));
              result.setId(item.getId());
              result.setAddTime(item.getAddTime());
              result.setHouseId(item.getHouseId());
              result.setCreatorName(item.getHouseCreatorName());
              result.setComments(item.getComments());
            } catch (Exception e) {
              logger.error("list reject house error", e);
            }
            
          }
          
          return result;
        }
      });
    }
    
    model.addAttribute("list", auditList);
    return "audit/rejectedHouseList";
    
  }
  
  @RequestMapping(value = "ajax/requestAddNewHouse.controller")
  public ModelAndView requestAddNewHouse(Model model, int houseId) {
    int result = auditService.requestAddNewHouse(houseId);
    if (result > -1) {
      HouseMartContext.setSysMsg("已提交审核，请耐心等候");
    } else {
      HouseMartContext.setSysMsg("提交审核失败，请联系管理员");
    }
    AjaxResultBean resultBean = new AjaxResultBean();
    resultBean.setBizData(result);
    resultBean.setMsg(HouseMartContext.getSysMsg());
    return new ModelAndView("jsonView", "json", result);
  }
  
  @SuppressWarnings("unchecked")
  private List<AuditNewHouseBean> getAuditListForNewHouse(Model model, Integer sourceType, String residenceName, String creatorName) {
    
    List<AuditNewHouseBean> newList = new ArrayList<AuditNewHouseBean>();
    
    int totalCount = 0;
    int internalCount = 0;
    int externalCount = 0;
    
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("result", 0);
    List<HouseAuditHistoryEntity> list = (List<HouseAuditHistoryEntity>) houseAuditHistoryDao.select("findHouseAuditHistoryList",
        map);
    
    if (!CollectionUtils.isEmpty(list)) {
      
      List<HouseAuditHistoryEntity> filteredList = new ArrayList<HouseAuditHistoryEntity>();
      
      // filter
      for (HouseAuditHistoryEntity item : list) {
        if (item.getType() == 1 && item.getResult() != null && item.getResult() == 0) {
          
          HouseExtEntity house = (HouseExtEntity) houseDao.load("loadHouseExt", item.getHouseId());
          
          if (house != null) {
            
            if (isAdmin()) {
              
              // residence search
              if (StringUtils.isNotBlank(residenceName)) {
                if (house.getResidenceName() == null || !house.getResidenceName().contains(residenceName)) {
                  continue;
                }
              }
              
              // account search
              if (StringUtils.isNotBlank(creatorName)) {
                if (house.getAccountName() == null || !house.getAccountName().contains(creatorName)) {
                  continue;
                }
              }
              
              item.setHouseCreatorName(house.getAccountName());
              
              if (house.getSourceType().equals(HouseEntity.SourceTypeEnum.external.value)) {
                externalCount++;
                if (sourceType.equals(HouseEntity.SourceTypeEnum.external.value)) {
                  filteredList.add(item);
                }
              } else if (house.getSourceType().equals(HouseEntity.SourceTypeEnum.internal.value)) {
                internalCount++;
                if (sourceType.equals(HouseEntity.SourceTypeEnum.internal.value)) {
                  filteredList.add(item);
                }
              } else {
                // TODO::custom service
              }
              
              totalCount++;
              
            } else {
              if ((HouseMartContext.getRegionIds() != null && house.getRegionParentId() != null && HouseMartContext.getRegionIds()
                  .contains(house.getRegionParentId().intValue()))
                  || (HouseMartContext.getPlateIds() != null && house.getRegionId() != null && HouseMartContext.getPlateIds()
                      .contains(house.getRegionId().intValue()))
                  || (HouseMartContext.getResidenceIds() != null && house.getResidenceId() != null && HouseMartContext
                      .getResidenceIds().contains(house.getResidenceId()))) {
                
                if (HouseMartContext.getRegionMangerIds() != null
                    && HouseMartContext.getRegionMangerIds().contains(HouseMartContext.getCurrentUserId())) {
                  
                  // residence search
                  if (StringUtils.isNotBlank(residenceName)) {
                    if (house.getResidenceName() == null || !house.getResidenceName().contains(residenceName)) {
                      continue;
                    }
                  }
                  
                  // account search
                  if (StringUtils.isNotBlank(creatorName)) {
                    if (house.getAccountName() == null || !house.getAccountName().contains(creatorName)) {
                      continue;
                    }
                  }
                  
                  item.setHouseCreatorName(house.getAccountName());
                  
                  if (house.getSourceType().equals(HouseEntity.SourceTypeEnum.external.value)) {
                    externalCount++;
                    if (sourceType.equals(HouseEntity.SourceTypeEnum.external.value)) {
                      filteredList.add(item);
                    }
                  } else if (house.getSourceType().equals(HouseEntity.SourceTypeEnum.internal.value)) {
                    internalCount++;
                    if (sourceType.equals(HouseEntity.SourceTypeEnum.internal.value)) {
                      filteredList.add(item);
                    }
                  } else {
                    // TODO::custom service
                  }
                  
                  totalCount++;
                }
              }
            }
          }
        }
      }
      
      logger.info("AuditTmpListCount: " + (filteredList == null ? 0 : filteredList.size()));
      
      newList = (List<AuditNewHouseBean>) CollectionUtils.collect(filteredList, new Transformer() {
        
        public Object transform(Object obj) {
          HouseAuditHistoryEntity item = (HouseAuditHistoryEntity) obj;
          AuditNewHouseBean result = new AuditNewHouseBean();
          
          if (!StringUtils.isEmpty(item.getPostContent())) {
            ObjectMapper mapper = new ObjectMapper();
            try {
              result = mapper.readValue(item.getPostContent(), AuditNewHouseBean.class);
              result.setResidencePinyin(new PinyinTranslator().toPinyin(result.getResidenceName()));
              result.setId(item.getId());
              result.setAddTime(item.getAddTime());
              result.setHouseId(item.getHouseId());
              result.setCreatorName(item.getHouseCreatorName());
            } catch (Exception e) {
              logger.error("audit new house json parse error", e);
            }
            
          }
          
          return result;
        }
      });
      
      if (newList != null) {
        Collections.sort(newList, new Comparator<AuditNewHouseBean>() {
          public int compare(AuditNewHouseBean arg0, AuditNewHouseBean arg1) {
            String a0 = arg0.getResidencePinyin() == null ? "" : arg0.getResidencePinyin();
            String a1 = arg1.getResidencePinyin() == null ? "" : arg1.getResidencePinyin();
            return a0.compareTo(a1);
          }
        });
      }
      
      logger.info("AuditFinalListCount: " + (newList == null ? 0 : newList.size()));
      if (model != null) {
        model.addAttribute("list", newList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("internalCount", internalCount);
        model.addAttribute("externalCount", externalCount);
        model.addAttribute("sourceType", sourceType);
        model.addAttribute("residenceName", residenceName);
        model.addAttribute("creatorName", creatorName);
      }
    }
    
    return newList;
  }
}
