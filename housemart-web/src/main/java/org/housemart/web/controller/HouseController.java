/**
 * Created on 2012-9-20
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.dao.entities.AccountEntity;
import org.housemart.dao.entities.HouseAuditHistoryEntity;
import org.housemart.dao.entities.HouseContactEntity;
import org.housemart.dao.entities.HouseEntity;
import org.housemart.dao.entities.HouseEntity.StatusEnum;
import org.housemart.dao.entities.HouseExtAgileEntity;
import org.housemart.dao.entities.HouseExtEntity;
import org.housemart.dao.entities.HousePropertyEntity;
import org.housemart.dao.entities.HouseRentEntity;
import org.housemart.dao.entities.HouseSaleEntity;
import org.housemart.dao.entities.HouseTagEntity;
import org.housemart.dao.entities.MaxinRawEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.dao.generic.UniqueIdObject;
import org.housemart.service.AuditService;
import org.housemart.service.HouseContactViewService;
import org.housemart.service.HouseService;
import org.housemart.service.HouseTagService;
import org.housemart.service.enums.ActionType;
import org.housemart.service.enums.AuditContentKeys;
import org.housemart.service.enums.AuditTypeEnum;
import org.housemart.service.enums.GranularityType;
import org.housemart.service.enums.ResourceType;
import org.housemart.service.priviledge.PrivilegeService;
import org.housemart.util.PicUtils;
import org.housemart.web.beans.AjaxResultBean;
import org.housemart.web.beans.HouseRentEquipment;
import org.housemart.web.beans.HouseRoomType;
import org.housemart.web.context.HouseMartContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 
 * @author pqin
 */
public class HouseController extends BaseController {
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao houseDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao houseContactDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao housePropertyDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao houseAuditHistoryDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao houseSaleDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao houseRentDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao houseSoldBuildingDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao maxinRawDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao accountDao;
  
  @Autowired
  AuditService auditService;
  @Autowired
  HouseTagService houseTagService;
  @Autowired
  HouseService houseService;
  @Autowired
  PrivilegeService privilegeService;
  @Autowired
  HouseContactViewService houseContactViewService;
  
  @SuppressWarnings("unchecked")
  protected Model find(Model model, Integer houseId) {
    HouseExtEntity house = null;
    Integer[] rTypeArray = null;
    HouseRoomType roomType = null;
    Integer invalidLoggingNumber = null;
    List<HouseAuditHistoryEntity> historyList = null;
    List<HouseTagEntity> saleTagOptions = null;
    List<HouseTagEntity> rentTagOptions = null;
    List<HouseTagEntity> saleTags = null;
    List<HouseTagEntity> rentTags = null;
    HouseRentEquipment rentEquipt = null;
    
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("houseId", houseId);
    map.put("type", AuditTypeEnum.InvalidLoggingAudit.getValue());
    
    if (houseId != null) house = (HouseExtEntity) houseDao.load("loadHouseExt",
        houseId);
    if (house == null) house = new HouseExtEntity();
    
    rTypeArray = integerToArray(house.getRoomType(), 4);
    roomType = HouseRoomType.newInstance(rTypeArray[0], rTypeArray[1],
        rTypeArray[2], rTypeArray[3]);
    historyList = houseAuditHistoryDao.select("findHouseAuditHistoryList", map);
    if (!CollectionUtils.isEmpty(historyList)) {
      invalidLoggingNumber = historyList.size();
    }
    saleTagOptions = houseTagService.getTagOptionsByCategory("sale");
    rentTagOptions = houseTagService.getTagOptionsByCategory("rent");
    if (StringUtils.isNotEmpty(house.getSaleTagList())) saleTags = houseTagService
        .getHouseTagsIn(house.getSaleTagList());
    if (StringUtils.isNotEmpty(house.getRentTagList())) rentTags = houseTagService
        .getHouseTagsIn(house.getRentTagList());
    if (StringUtils.isNotBlank(house.getRentEquipmentList())) {
      try {
        rentEquipt = HouseRentEquipment
            .string2Obj(house.getRentEquipmentList());
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
    } else rentEquipt = new HouseRentEquipment();
    
    MaxinRawEntity maxinData = null;
    if (houseId != null) {
      Object data = maxinRawDao.load("loadRecord", houseId);
      if (data != null) maxinData = (MaxinRawEntity) data;
    }
    
    model.addAttribute("house", house);
    model.addAttribute("roomType", roomType);
    model.addAttribute("invalidLoggingNumber", invalidLoggingNumber);
    model.addAttribute("saleTagOptions", saleTagOptions);
    model.addAttribute("rentTagOptions", rentTagOptions);
    model.addAttribute("saleTags", saleTags);
    model.addAttribute("rentTags", rentTags);
    model.addAttribute("rentEquipments", rentEquipt);
    model.addAttribute("maxinData", maxinData);
    
    return model;
  }
  
  @SuppressWarnings({"unchecked", "rawtypes", "deprecation"})
  @RequestMapping(value = "houseUpdate.controller", method = RequestMethod.POST)
  public ModelAndView addOrUpdate(Model model, HouseExtEntity house,
      HouseRoomType roomType, HouseRentEquipment rentEquipt) throws Exception {
    Integer[] rTypeArray = roomType.toArray();
    house.setRoomType(arrayToInteger(rTypeArray, rTypeArray.length));
    try {
      if (rentEquipt != null) house.setRentEquipmentList(HouseRentEquipment
          .obj2String(rentEquipt));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    
    HouseContactEntity contact = null;
    HousePropertyEntity property = null;
    HouseSaleEntity sale = null;
    HouseRentEntity rent = null;
    
    if (house != null && house.getId() != null) {
      // 房源更新
      
      // 状态与内容变更审核
      HouseExtEntity housePre = (HouseExtEntity) houseDao.load("loadHouseExt",
          house.getId());
      if (housePre.getBuildTime() != null) {
        housePre.setBuildTimeString(String.valueOf(housePre.getBuildTime()
            .getYear()));
      }
      
      if (house.getSalePrice() != null) {
        house.setSalePrice(house.getSalePrice() * 10000);
      }
      if (house.getSaleBasePrice() != null) {
        house.setSaleBasePrice(house.getSaleBasePrice() * 10000);
      }
      if (house.getSaleAvgPrice() != null) {
        house.setSaleAvgPrice(house.getSaleAvgPrice() * 10000);
      }
      
      // field list key
      // TODO:
      int power = 1;
      
      if (housePre.getSourceType().equals(
          HouseEntity.SourceTypeEnum.external.value)) {
        power = 3;
      } else {
        power = privilegeService.hasPower(HouseMartContext.getCurrentUserId(),
            ActionType.Update, ResourceType.House, GranularityType.Field,
            house.getId(), null);
      }
      logger.info("userid " + HouseMartContext.getCurrentUserId()
          + " haspower " + power);
      if (power == 2 || power == 3) {
        // 需审核
        if (housePre.getStatus() != null
            && housePre.getStatus().intValue() == StatusEnum.Valid.value
                .intValue()) {
          Map<String,Object[]> map = new HashMap<String,Object[]>();
          
          // 房源状态变更均需审核
          // saleStatus, rentStatus
          if (housePre.getSaleSaleStatus() == null
              && house.getSaleSaleStatus() != null
              || housePre.getSaleSaleStatus() != null
              && !housePre.getSaleSaleStatus()
                  .equals(house.getSaleSaleStatus())) {
            map.put(
                AuditContentKeys.SALE_STATUS,
                new Object[] {housePre.getSaleSaleStatus(),
                    house.getSaleSaleStatus()});
            if (power == 2) {
              house.setSaleSaleStatus(housePre.getSaleSaleStatus());
            }
          }
          
          if (housePre.getRentRentStatus() == null
              && house.getRentRentStatus() != null
              || housePre.getRentRentStatus() != null
              && !housePre.getRentRentStatus()
                  .equals(house.getRentRentStatus())) {
            map.put(
                AuditContentKeys.RENT_STATUS,
                new Object[] {housePre.getRentRentStatus(),
                    house.getRentRentStatus()});
            if (power == 2) {
              house.setRentRentStatus(housePre.getRentRentStatus());
            }
          }
          
          // 登盘相关信息，更新必须审核
          // Area, ContactName,ContactMobile
          if (house.getPropertyArea() != null
              && !house.getPropertyArea().equals(housePre.getPropertyArea())) {
            map.put(
                AuditContentKeys.AREA,
                new Object[] {housePre.getPropertyArea(),
                    house.getPropertyArea()});
            if (power == 2) {
              house.setPropertyArea(housePre.getPropertyArea());
            }
          }
          
          // 不租/不售状态下时，更新无须审核，处于其他状态下时，该部分所有信息更新均需要审核。
          if (!(HouseSaleEntity.SaleStatusEnum.NotSale.saleStatus
              .equals(housePre.getSaleSaleStatus()) && HouseRentEntity.RentStatusEnum.NotRent.rentStatus
              .equals(housePre.getRentRentStatus()))) {
            if (house.getRoomType() != null
                && !house.getRoomType().equals(housePre.getRoomType())) {
              map.put(AuditContentKeys.ROOM_TYPE,
                  new Object[] {housePre.getRoomType(), house.getRoomType()});
              if (power == 2) {
                house.setRoomType(housePre.getRoomType());
              }
            }
            
            if (!(StringUtils.isBlank(house.getBuildTimeString()) && StringUtils
                .isBlank(housePre.getBuildTimeString()))
                && !StringUtils.equals(house.getBuildTimeString(),
                    housePre.getBuildTimeString())) {
              if (StringUtils.isBlank(house.getBuildTimeString())) {
                house.setBuildTime(null);
              } else {
                house.setBuildTime(new Date(Integer.valueOf(house
                    .getBuildTimeString()) - 1900, 0, 1));
              }
              map.put(AuditContentKeys.BUILD_TIME,
                  new Object[] {housePre.getBuildTime(), house.getBuildTime()});
              if (power == 2) {
                house.setBuildTimeString(housePre.getBuildTimeString());
                house.setBuildTime(housePre.getBuildTime());
              }
            }
            
            if (!(StringUtils.isBlank(house.getHouseType()) && StringUtils
                .isBlank(housePre.getHouseType()))
                && !StringUtils.equals(house.getHouseType(),
                    housePre.getHouseType())) {
              map.put(AuditContentKeys.HOUSE_TYPE,
                  new Object[] {housePre.getHouseType(), house.getHouseType()});
              if (power == 2) {
                house.setHouseType(housePre.getHouseType());
              }
            }
            
            if (house.getOccupiedArea() != null
                && !house.getOccupiedArea().equals(housePre.getOccupiedArea())) {
              map.put(
                  AuditContentKeys.OCCUPIED_AREA,
                  new Object[] {housePre.getOccupiedArea(),
                      house.getOccupiedArea()});
              if (power == 2) {
                house.setOccupiedArea(housePre.getOccupiedArea());
              }
            }
            
            if (house.getDecorating() != null
                && !house.getDecorating().equals(housePre.getDecorating())) {
              map.put(
                  AuditContentKeys.DECORATING,
                  new Object[] {housePre.getDecorating(), house.getDecorating()});
              if (power == 2) {
                house.setDecorating(housePre.getDecorating());
              }
            }
            
            if (house.getRentEquipmentList() != null
                && !house.getRentEquipmentList().equals(
                    housePre.getRentEquipmentList())) {
              map.put(
                  AuditContentKeys.RENT_EQUIPMENT_LIST,
                  new Object[] {housePre.getRentEquipmentList(),
                      house.getRentEquipmentList()});
              if (power == 2) {
                house.setRentEquipmentList(housePre.getRentEquipmentList());
              }
            }
            
            if (house.getSalePrice() != null
                && !house.getSalePrice().equals(housePre.getSalePrice())) {
              map.put(AuditContentKeys.SALE_PRICE,
                  new Object[] {housePre.getSalePrice(), house.getSalePrice()});
              if (power == 2) {
                house.setSalePrice(housePre.getSalePrice());
              }
            }
            
            if (house.getSaleBasePrice() != null
                && !house.getSaleBasePrice()
                    .equals(housePre.getSaleBasePrice())) {
              map.put(
                  AuditContentKeys.SALE_BASE_PRICE,
                  new Object[] {housePre.getSaleBasePrice(),
                      house.getSaleBasePrice()});
              if (power == 2) {
                house.setSaleBasePrice(housePre.getSaleBasePrice());
              }
            }
            
            if (!(StringUtils.isBlank(house.getSaleDealType()) && StringUtils
                .isBlank(housePre.getSaleDealType()))
                && !StringUtils.equals(house.getSaleDealType(),
                    housePre.getSaleDealType())) {
              map.put(
                  AuditContentKeys.SALE_DEAL_TYPE,
                  new Object[] {housePre.getSaleDealType(),
                      house.getSaleDealType()});
              if (power == 2) {
                house.setSaleDealType(housePre.getSaleDealType());
              }
            }
            
            if (house.getSaleSaleRec() != null
                && !house.getSaleSaleRec().equals(housePre.getSaleSaleRec())) {
              map.put(
                  AuditContentKeys.SALE_REC,
                  new Object[] {housePre.getSaleSaleRec(),
                      house.getSaleSaleRec()});
              if (power == 2) {
                house.setSaleSaleRec(housePre.getSaleSaleRec());
              }
            }
            
            if (!(StringUtils.isBlank(house.getSaleTagList()) && StringUtils
                .isBlank(housePre.getSaleTagList()))
                && !StringUtils.equals(house.getSaleTagList(),
                    housePre.getSaleTagList())) {
              map.put(
                  AuditContentKeys.SALE_TAG_LIST,
                  new Object[] {housePre.getSaleTagList(),
                      house.getSaleTagList()});
              if (power == 2) {
                house.setSaleTagList(housePre.getSaleTagList());
              }
            }
            
            if (!(StringUtils.isBlank(house.getSaleMemo()) && StringUtils
                .isBlank(housePre.getSaleMemo()))
                && !StringUtils.equals(house.getSaleMemo(),
                    housePre.getSaleMemo())) {
              map.put(AuditContentKeys.SALE_MEMO,
                  new Object[] {housePre.getSaleMemo(), house.getSaleMemo()});
              if (power == 2) {
                house.setSaleMemo(housePre.getSaleMemo());
              }
            }
            
            if (house.getRentPrice() != null
                && !house.getRentPrice().equals(housePre.getRentPrice())) {
              map.put(AuditContentKeys.RENT_PRICE,
                  new Object[] {housePre.getRentPrice(), house.getRentPrice()});
              if (power == 2) {
                house.setRentPrice(housePre.getRentPrice());
              }
            }
            
            if (house.getRentBasePrice() != null
                && !house.getRentBasePrice()
                    .equals(housePre.getRentBasePrice())) {
              map.put(
                  AuditContentKeys.RENT_BASE_PRICE,
                  new Object[] {housePre.getRentBasePrice(),
                      house.getRentBasePrice()});
              if (power == 2) {
                house.setRentBasePrice(housePre.getRentBasePrice());
              }
            }
            
            if (house.getRentRentType() != null
                && !house.getRentRentType().equals(housePre.getRentRentType())) {
              map.put(
                  AuditContentKeys.RENT_TYPE,
                  new Object[] {housePre.getRentRentType(),
                      house.getRentRentType()});
              if (power == 2) {
                house.setRentRentType(housePre.getRentRentType());
              }
            }
            
            if (house.getRentRentRec() != null
                && !house.getRentRentRec().equals(housePre.getRentRentRec())) {
              map.put(
                  AuditContentKeys.RENT_REC,
                  new Object[] {housePre.getRentRentRec(),
                      house.getRentRentRec()});
              if (power == 2) {
                house.setRentRentRec(housePre.getRentRentRec());
              }
            }
            
            if (!(StringUtils.isBlank(house.getRentTagList()) && StringUtils
                .isBlank(housePre.getRentTagList()))
                && !StringUtils.equals(house.getRentTagList(),
                    housePre.getRentTagList())) {
              map.put(
                  AuditContentKeys.RENT_TAG_LIST,
                  new Object[] {housePre.getRentTagList(),
                      house.getRentTagList()});
              if (power == 2) {
                house.setRentTagList(housePre.getRentTagList());
              }
            }
            
            if (!(StringUtils.isBlank(house.getRentMemo()) && StringUtils
                .isBlank(housePre.getRentMemo()))
                && !StringUtils.equals(house.getRentMemo(),
                    housePre.getRentMemo())) {
              map.put(AuditContentKeys.RENT_MEMO,
                  new Object[] {housePre.getRentMemo(), house.getRentMemo()});
              if (power == 2) {
                house.setRentMemo(housePre.getRentMemo());
              }
            }
            
            if (housePre.getViewHouseType() != null
                && !housePre.getViewHouseType()
                    .equals(house.getViewHouseType())) {
              map.put(
                  AuditContentKeys.VIEW_HOUSE_TYPE,
                  new Object[] {housePre.getViewHouseType(),
                      house.getViewHouseType()});
              if (power == 2) {
                house.setViewHouseType(housePre.getViewHouseType());
              }
            }
          }
          
          if (!map.isEmpty()) {
            int id = auditService.requestHouseStatusAndContentUpdate(
                house.getId(), map);
            if (power == 3) {
              if (id > -1) {
                auditService.approveStatusAndContent(house.getId(), id);
              } else {
                throw new Exception(
                    "requestHouseStatusAndContentUpdate error, audit id is -1");
              }
            }
          }
        }
        
        contact = house.generateHouseContactEntity();
        property = house.generateHousePropertyEntity();
        
        sale = house.generateHouseSaleEntity();
        rent = house.generateHouseRentEntity();
        
        if (housePre.getSourceType().equals(
            HouseEntity.SourceTypeEnum.external.value)) {
          sale.setSaleStatus(housePre.getSaleSaleStatus());
          rent.setRentStatus(housePre.getRentRentStatus());
        }
        
        house.setUpdateTime(Calendar.getInstance().getTime());
        contact.setUpdateTime(Calendar.getInstance().getTime());
        property.setUpdateTime(Calendar.getInstance().getTime());
        sale.setUpdateTime(Calendar.getInstance().getTime());
        rent.setUpdateTime(Calendar.getInstance().getTime());
        
        houseDao.update("updateHouse", house);
        Map houseIdPara = new HashMap();
        houseIdPara.put("houseId", house.getId());
        
        List houseContact = houseContactDao.select(
            "findHouseContactListByHouseId", houseIdPara);
        if (houseContact == null || houseContact.size() < 1) {
          contact.setAddTime(Calendar.getInstance().getTime());
          houseContactDao.add("addHouseContact", contact);
        } else {
          houseContactDao.update("updateHouseContactByHouseId", contact);
        }
        List houseProperty = houseSaleDao.select("findHouseSaleListByHouseId",
            new UniqueIdObject(house.getId()));
        if (houseProperty == null || houseProperty.size() < 1) {
          property.setAddTime(Calendar.getInstance().getTime());
          housePropertyDao.add("addHouseProperty", property);
        } else {
          housePropertyDao.update("updateHousePropertyByHouseId", property);
        }
        
        List houseSale = houseSaleDao.select("findHouseSaleListByHouseId",
            new UniqueIdObject(house.getId()));
        
        if (houseSale == null || houseSale.size() < 1) {
          sale.setAddTime(Calendar.getInstance().getTime());
          houseSaleDao.add("addHouseSale", sale);
        } else {
          houseSaleDao.update("updateHouseSaleByHouseId", sale);
        }
        
        List houseRent = houseRentDao.select("findHouseRentListByHouseId",
            new UniqueIdObject(house.getId()));
        if (houseRent == null || houseRent.size() < 1) {
          rent.setAddTime(Calendar.getInstance().getTime());
          houseRentDao.add("addHouseRent", rent);
        } else {
          houseRentDao.update("updateHouseRentByHouseId", rent);
        }
      }
      HouseMartContext.setSysMsg("房源信息已保存");
    } else {
      // 登盘
      if (StringUtils.isNotBlank(house.getBuildTimeString())) {
        house.setBuildTime(new Date(
            Integer.valueOf(house.getBuildTimeString()) - 1900, 0, 1));
      }
      
      house
          .setSourceType(HouseMartContext.getType() == 0 ? HouseEntity.SourceTypeEnum.internal.value
              : HouseEntity.SourceTypeEnum.external.value);
      
      house.setStatus(StatusEnum.Default.value);
      house.setAddTime(Calendar.getInstance().getTime());
      house.setUpdateTime(Calendar.getInstance().getTime());
      house.setCreator(HouseMartContext.getCurrentUserId());
      houseDao.add("addHouse", house);
      
      contact = house.generateHouseContactEntity();
      property = house.generateHousePropertyEntity();
      sale = house.generateHouseSaleEntity();
      rent = house.generateHouseRentEntity();
      
      contact.setCommitter(HouseMartContext.getCurrentUserId());
      contact.setHouseId(house.getId());
      contact.setAddTime(Calendar.getInstance().getTime());
      contact.setUpdateTime(Calendar.getInstance().getTime());
      property.setHouseId(house.getId());
      property.setAddTime(Calendar.getInstance().getTime());
      property.setUpdateTime(Calendar.getInstance().getTime());
      
      if (house.getSourceType().equals(
          HouseEntity.SourceTypeEnum.external.value)) {
        String viewType = this.getRequest().getParameter("viewType");
        if (viewType.equals("sale")) {
          sale.setPrice(house.getSalePrice() * 10000);
          sale.setAvgPrice(sale.getPrice() / house.getPropertyArea());
          sale.setSaleStatus(HouseSaleEntity.SaleStatusEnum.Saling.saleStatus);
        }
        if (viewType.equals("rent")) {
          rent.setRentStatus(HouseRentEntity.RentStatusEnum.Renting.rentStatus);
        }
      }
      
      sale.setHouseId(house.getId());
      sale.setSource("2");
      sale.setAddTime(Calendar.getInstance().getTime());
      sale.setUpdateTime(Calendar.getInstance().getTime());
      rent.setHouseId(house.getId());
      rent.setAddTime(Calendar.getInstance().getTime());
      rent.setUpdateTime(Calendar.getInstance().getTime());
      
      houseContactDao.add("addHouseContact", contact);
      housePropertyDao.add("addHouseProperty", property);
      houseSaleDao.add("addHouseSale", sale);
      houseRentDao.add("addHouseRent", rent);
      
      int power = privilegeService.hasPower(
          HouseMartContext.getCurrentUserId(), ActionType.Commit,
          ResourceType.House, GranularityType.Entity, house.getId(), null);
      switch (power) {
        case 2:
          // auto request, except external type
          if (house.getSourceType() == null
              || !house.getSourceType().equals(
                  HouseEntity.SourceTypeEnum.external.value)) {
            auditService.requestAddNewHouse(house.getId());
          }
          break;
        case 3:
          // auto request and approve
          int id = auditService.requestAddNewHouse(house.getId());
          if (id > -1) {
            auditService.approveNewHouse(house.getId(), id);
          } else {
            throw new Exception("requestAddNewHouse error, audit id is -1");
          }
          
          house.setStatus(StatusEnum.Valid.value);
          houseDao.update("updateHouse", house);
          break;
      }
      HouseMartContext.setSysMsg("已添加新房源");
    }
    
    if (house.getSourceType() != null
        && house.getSourceType().equals(
            HouseEntity.SourceTypeEnum.external.value)) {
      return new ModelAndView(new RedirectView(
          "/external/housePicConsole.controller?houseId=" + house.getId()
              + "&sourceType=" + house.getSourceType() + "&picType=3"));
    }
    
    return new ModelAndView(new RedirectView("houseEdit.controller?houseId="
        + house.getId()));
  }
  
  @SuppressWarnings({"rawtypes", "unchecked"})
  protected Model findList(Model model, Integer regionCityId,
      Integer regionParentId, Integer regionRegionId, String residenceName,
      String creatorName, String buildingInfo, String cellInfo,
      Integer salePriceGT, Integer salePriceLE, Integer propertyAreaGT,
      Integer propertyAreaLE, HouseRoomType roomType, Integer status,
      Integer saleStatus, Integer rentStatus, Integer creator,
      Integer favUserId, Boolean isMyFavoritePage, String saleIntention,
      String rentIntention, String dialResult, String phone,
      Integer sourceType, String sourceTypeIn, boolean isAgile,
      Integer clientType,
      Integer page, Integer pageSize, String orderByClause
      ) {
    
    String decodedResidenceName = null;
    if (residenceName != null) {
      try {
        decodedResidenceName = URLDecoder.decode(residenceName, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        logger.error(e.getMessage(), e);
      }
    }
    
    String decodedCreatorName = null;
    if (creatorName != null) {
      try {
        decodedCreatorName = URLDecoder.decode(creatorName, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        logger.error(e.getMessage(), e);
      }
    }
    
    String decodedSaleIntention = null;
    if (saleIntention != null) {
      try {
        decodedSaleIntention = URLDecoder.decode(saleIntention, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        logger.error(e.getMessage(), e);
      }
    }
    String decodedRentIntention = null;
    if (rentIntention != null) {
      try {
        decodedRentIntention = URLDecoder.decode(rentIntention, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        logger.error(e.getMessage(), e);
      }
    }
    String decodedDialResult = null;
    if (dialResult != null) {
      try {
        decodedDialResult = URLDecoder.decode(dialResult, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        logger.error(e.getMessage(), e);
      }
    }
    if (page == null) page = 0;
    if (pageSize == null) pageSize = 20;
    
    Map parameter = new HashMap();
    parameter.put("regionCityId", regionCityId);
    parameter.put("regionParentId", regionParentId);
    parameter.put("regionRegionId", regionRegionId);
    parameter
        .put("residenceName", StringUtils.isEmpty(decodedResidenceName) ? null
            : decodedResidenceName);
    parameter.put("creatorName", StringUtils.isEmpty(decodedCreatorName) ? null
        : decodedCreatorName);
    parameter.put("buildingInfo", StringUtils.isEmpty(buildingInfo) ? null
        : buildingInfo);
    parameter.put("cellInfo", StringUtils.isEmpty(cellInfo) ? null : cellInfo);
    parameter.put("salePriceGT", salePriceGT);
    parameter.put("salePriceLE", salePriceLE);
    parameter.put("propertyAreaGT", propertyAreaGT);
    parameter.put("propertyAreaLE", propertyAreaLE);
    if (roomType != null) {
      parameter.put("ting", roomType.getTing());
      parameter.put("shi", roomType.getShi());
      parameter.put("wei", roomType.getWei());
      parameter.put("yangtai", roomType.getYangtai());
    }
//    orderByClause = "H.ID desc";
    parameter.put("orderByClause", orderByClause);
    parameter.put("status", status);
    parameter.put("saleStatus", saleStatus);
    parameter.put("rentStatus", rentStatus);
    parameter.put("favoriteUserId", favUserId);
    parameter.put("isMyFavoritePage", isMyFavoritePage);
    parameter.put("creator", creator);
    parameter
        .put("saleIntention", StringUtils.isBlank(decodedSaleIntention) ? null
            : decodedSaleIntention);
    parameter
        .put("rentIntention", StringUtils.isBlank(decodedRentIntention) ? null
            : decodedRentIntention);
    parameter.put("dialResult", StringUtils.isBlank(decodedDialResult) ? null
        : decodedDialResult);
    parameter.put("sourceType", sourceType);
    parameter.put("sourceTypeIn", sourceTypeIn);
    
    parameter.put("clientType", clientType);
    
    if (StringUtils.isNotBlank(phone)) {
      parameter.put("phone", phone.trim());
    }
    if (isAgile) {
      parameter.put("isAgile", true);
    }
    Integer totalCount = houseDao.count("countHouseExt", parameter);
    
    // for pagination query
    parameter.put("skip", (page != null && pageSize != null) ? page * pageSize
        : null);
    parameter.put("count", pageSize);
    List<HouseExtAgileEntity> houseList = (List<HouseExtAgileEntity>) houseDao
        .select("findHouseExtAgileList", parameter);
    if (houseList != null) {
      
      for (HouseExtAgileEntity h : houseList) {
        MaxinRawEntity maxinData = null;
        if (h.getId() != null) {
          Object data = maxinRawDao.load("loadRecord", h.getId());
          if (data != null) {
            maxinData = (MaxinRawEntity) data;
            h.setMaxinBuildingNo(maxinData.getBuildingNo());
            h.setMaxinCellNo(maxinData.getCellNo());
          }
        }
      }
      
    }
    
    parameter.remove("skip");
    parameter.remove("count");
    
    // for pagination
    parameter.put("page", page);
    parameter.put("pageSize", pageSize);
    parameter.put("totalCount", totalCount);
    parameter.put("pageCount", houseList == null ? 0 : houseList.size());
    model.addAttribute("houseList", houseList);
    model.addAttribute("param", parameter);
    return model;
    
  }
  
  @SuppressWarnings({"rawtypes", "unchecked"})
  protected Model findListWithPic(Model model, Map map, Integer page,
      Integer pageSize, String orderByClause) {
    
    if (page == null) page = 0;
    if (pageSize == null) pageSize = 20;
    
    Integer totalCount = houseDao.count("countHouseWithPic", map);
    
    // for pagination query
    map.put("skip", (page != null && pageSize != null) ? page * pageSize : null);
    map.put("count", pageSize);
    List<Map<Object,Object>> houseList = (List<Map<Object,Object>>) houseDao
        .select("findHouseWithPicList", map);
    // onboardTime, applyTime
    
    Integer tabIndex = (Integer) map.get("tabIndex");
    
    if (houseList != null) {
      for (Map<Object,Object> hExt : houseList) {
        Map<Object,Object> para = new HashMap<Object,Object>();
        para.put("houseId", hExt.get("ID"));
        
        if (tabIndex == 1) {
          // 上架
          para.put("type", AuditTypeEnum.LoggingAudit.getValue());
          para.put("result",
              HouseAuditHistoryEntity.ResultEnum.Agree.getValue());
        } else if (tabIndex == 4) {
          // 下架
          para.put("type", AuditTypeEnum.OffboardAudit.getValue());
          para.put("result",
              HouseAuditHistoryEntity.ResultEnum.Agree.getValue());
        } else if (tabIndex == 2) {
          // 审核
          para.put("result",
              HouseAuditHistoryEntity.ResultEnum.Default.getValue());
        } else if (tabIndex == 3) {
          // 拒绝
          para.put("result",
              HouseAuditHistoryEntity.ResultEnum.Reject.getValue());
        } else if (tabIndex == 5) {
          // 未提交审核
          para.put("typeNull", true);
          para.put("resultNull", true);
        }
        List<HouseAuditHistoryEntity> historyList = houseAuditHistoryDao
            .select("findHouseAuditHistoryList", para);
        if (CollectionUtils.isNotEmpty(historyList)) {
          HouseAuditHistoryEntity latestApprove = historyList.get(0);
          hExt.put("onboardTime", latestApprove.getUpdateTime());
          hExt.put("applyTime", latestApprove.getAddTime());
          hExt.put("auditComments", latestApprove.getComments());
          AccountEntity account = null;
          
          if (latestApprove.getManagerId() != null) {
            account = (AccountEntity) accountDao.load("loadAccountById",
                latestApprove.getCommitterId());
          }
          
          hExt.put("auditor", account == null ? "" : account.getName());
        }
        
        // pic mask
        if (hExt.get("picURL") != null) {
          hExt.put("picURL",
              PicUtils.wrapWaterMask(hExt.get("picURL").toString()));
        }
        
      }
    }
    map.remove("skip");
    map.remove("count");
    
    // for pagination
    map.put("page", page);
    map.put("pageSize", pageSize);
    map.put("totalCount", totalCount);
    map.put("pageCount", houseList == null ? 0 : houseList.size());
    model.addAttribute("houseList", houseList);
    model.addAttribute("param", map);
    return model;
  }
  
  @RequestMapping(value = "ajax/changeResidence.controller")
  public ModelAndView changeResidence(
      @RequestParam("residenceId") int residenceId,
      @RequestParam("houseId") int houseId) {
    
    AjaxResultBean result = new AjaxResultBean();
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("residenceId", residenceId);
    map.put("houseId", houseId);
    houseDao.update("changeResidence", map);
    return new ModelAndView("jsonView", "json", result);
    
  }
  
  /**
   * [1, 0, 0, 1, 1] -> 10011
   * 
   * @param array
   * @param length
   * @return
   */
  protected Integer arrayToInteger(Integer[] array, int length) {
    Integer result = null;
    if (ArrayUtils.isNotEmpty(array) && array.length == length) {
      result = 0;
      for (Integer elem : array) {
        elem = elem == null ? 0 : elem;
        result = result * 10 + elem;
      }
    }
    return result;
  }
  
  /**
   * 10011 -> [1, 0, 0, 1, 1]
   * 
   * @param integer
   * @param length
   * @return
   */
  protected Integer[] integerToArray(Integer integer, int length) {
    Integer[] result = new Integer[length];
    if (integer != null) {
      for (int i = result.length - 1; i >= 0; i--) {
        result[i] = integer % 10;
        integer = integer / 10;
      }
    }
    return result;
  }
  
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,
        true));
  }
}
