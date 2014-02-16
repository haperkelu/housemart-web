/**
 * Created on 2012-11-7
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.dao.entities.AccountEntity;
import org.housemart.dao.entities.AccountExtEntity;
import org.housemart.dao.entities.AccountResidenceEntity;
import org.housemart.dao.entities.HouseAuditHistoryEntity;
import org.housemart.dao.entities.HouseContactEntity;
import org.housemart.dao.entities.HouseEntity;
import org.housemart.dao.entities.HouseEntity.StatusEnum;
import org.housemart.dao.entities.HouseExtEntity;
import org.housemart.dao.entities.HousePicSortEntity;
import org.housemart.dao.entities.HousePropertyEntity;
import org.housemart.dao.entities.HouseRentEntity;
import org.housemart.dao.entities.HouseSaleEntity;
import org.housemart.dao.entities.HouseSoldBuildingEntity;
import org.housemart.dao.entities.ResidenceAuditHistoryEntity;
import org.housemart.dao.entities.ResidenceEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.dao.generic.UniqueIdObject;
import org.housemart.message.HouseInteractionNotifier;
import org.housemart.service.enums.AuditTypeEnum;
import org.housemart.web.beans.AjaxResultBean;
import org.housemart.web.beans.HouseRentEquipment;
import org.housemart.web.beans.HouseRoomType;
import org.housemart.web.context.HouseMartContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author pqin
 */
@Controller
public class HouseControllerExt extends HouseController {
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao accountDao;
  
  @Autowired
  GenericDao<HousePicSortEntity> housePicSortDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao accountResidenceDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceAuditHistoryDao;
  
  
  @RequestMapping(value = "external/myHouseList", method = RequestMethod.GET)
  public String externalPanel(Model model, Integer page, Integer pageSize,
      String orderByClause, Integer tabIndex) {
    
    tabIndex = tabIndex == null ? 1 : tabIndex;
    
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("sourceType", HouseEntity.SourceTypeEnum.external.value);
    map.put("creator", HouseMartContext.getCurrentUserId());
    
    String saleRent = this.getRequest().getParameter("saleRent");
    String residenceId = this.getRequest().getParameter("residenceId");
    
    saleRent = (saleRent == null ? "0" : saleRent);
    residenceId = (residenceId == null ? "0" : residenceId);
    
    if (tabIndex == 1) {
      // 上架
      map.put("status", HouseEntity.StatusEnum.Valid.value);
      map.put("onboard", true);
      map.put("auditType", AuditTypeEnum.LoggingAudit.getValue());
    } else if (tabIndex == 2) {
      // 审核中, status = 0
      map.put("status", HouseEntity.StatusEnum.Default.value);
      map.put("auditResult",
          HouseAuditHistoryEntity.ResultEnum.Default.getValue());
      map.put("auditType", AuditTypeEnum.LoggingAudit.getValue());
    } else if (tabIndex == 3) {
      // 拒绝
      map.put("status", HouseEntity.StatusEnum.Invalid.value);
      map.put("auditResult",
          HouseAuditHistoryEntity.ResultEnum.Reject.getValue());
      map.put("auditType", AuditTypeEnum.LoggingAudit.getValue());
    } else if (tabIndex == 4) {
      // 下架
      map.put("status", HouseEntity.StatusEnum.OffBoard.value);
    } else if (tabIndex == 5) {
      // 未提交审核
      map.put("status", HouseEntity.StatusEnum.Default.value);
      map.put("auditResultNull", true);
      map.put("auditTypeNull", true);
    }
    
    if (saleRent.equals("1")) {
      map.put("rentStatus", 1);
    } else if (saleRent.equals("2")) {
      map.put("saleStatus", 1);
    }
    
    if (!residenceId.equals("0")) {
      map.put("residenceId", Integer.parseInt(residenceId));
    }
    
    map.put("orderByClause",
        "RES.PinyinName ASC, H.UpdateTime DESC");
    map.put("tabIndex", tabIndex);
    model = super.findListWithPic(model, map, page, pageSize, orderByClause);
    
    model.addAttribute("tabIndex", tabIndex);
    model.addAttribute("saleRent", saleRent);
    model.addAttribute("residenceId", residenceId);
    
    // 上架房源数
    Map<Object,Object> mapCount = new HashMap<Object,Object>();
    mapCount.put("sourceType", HouseEntity.SourceTypeEnum.external.value);
    mapCount.put("creator", HouseMartContext.getCurrentUserId());
    mapCount.put("status", HouseEntity.StatusEnum.Valid.value);
    mapCount.put("onboard", true);
    mapCount.put("auditType", AuditTypeEnum.LoggingAudit.getValue());
    
    Integer onboardCount = houseDao.count("countHouseWithPic", mapCount);
    onboardCount = (onboardCount == null ? 0 : onboardCount);
    
    // 审核房源数
    mapCount = new HashMap<Object,Object>();
    mapCount.put("sourceType", HouseEntity.SourceTypeEnum.external.value);
    mapCount.put("creator", HouseMartContext.getCurrentUserId());
    mapCount.put("status", HouseEntity.StatusEnum.Default.value);
    mapCount.put("auditResult",
        HouseAuditHistoryEntity.ResultEnum.Default.getValue());
    mapCount.put("auditType", AuditTypeEnum.LoggingAudit.getValue());
    
    Integer auditCount = houseDao.count("countHouseWithPic", mapCount);
    auditCount = (auditCount == null ? 0 : auditCount);
    
    // 拒绝房源数
    mapCount = new HashMap<Object,Object>();
    mapCount.put("sourceType", HouseEntity.SourceTypeEnum.external.value);
    mapCount.put("creator", HouseMartContext.getCurrentUserId());
    mapCount.put("status", HouseEntity.StatusEnum.Invalid.value);
    mapCount.put("auditResult",
        HouseAuditHistoryEntity.ResultEnum.Reject.getValue());
    Integer rejectCount = houseDao.count("countHouseWithPic", mapCount);
    rejectCount = (rejectCount == null ? 0 : rejectCount);
    
    // 下架房源数
    mapCount = new HashMap<Object,Object>();
    mapCount.put("sourceType", HouseEntity.SourceTypeEnum.external.value);
    mapCount.put("creator", HouseMartContext.getCurrentUserId());
    mapCount.put("status", HouseEntity.StatusEnum.OffBoard.value);
    Integer offboardCount = houseDao.count("countHouseWithPic", mapCount);
    offboardCount = (offboardCount == null ? 0 : offboardCount);
    
    // 未提交审核房源数
    mapCount = new HashMap<Object,Object>();
    mapCount.put("sourceType", HouseEntity.SourceTypeEnum.external.value);
    mapCount.put("creator", HouseMartContext.getCurrentUserId());
    mapCount.put("status", HouseEntity.StatusEnum.Default.value);
    mapCount.put("auditResultNull", true);
    mapCount.put("auditTypeNull", null);
    Integer notRequestAuditCount = houseDao
        .count("countHouseWithPic", mapCount);
    notRequestAuditCount = (notRequestAuditCount == null ? 0
        : notRequestAuditCount);
    
    // 经纪人小区列表
    int currentUserId = HouseMartContext.getCurrentUserId();
    AccountExtEntity account = (AccountExtEntity) this.accountDao.load(
        "loadAccountById", currentUserId);
    List<AccountResidenceEntity> accountResidences = account
        .findResidencesByAccount();
    
    model.addAttribute("onboardCount", onboardCount);
    model.addAttribute("auditCount", auditCount);
    model.addAttribute("rejectCount", rejectCount);
    model.addAttribute("offboardCount", offboardCount);
    model.addAttribute("notRequestAuditCount", notRequestAuditCount);
    
    model.addAttribute("accountResidences", accountResidences);
    
    return "external/house/myHouses";
  }
  
  @RequestMapping(value = "external/houseView.controller", method = RequestMethod.GET)
  public String houseViewExt(Model model, Integer houseId, Integer auditId) {
    
    model = find(model, houseId);
    
    String sort = null;
    Map<String,Object> para = new HashMap<String,Object>();
    para.put("houseId", houseId);
    para.put("type", 3);
    List<HousePicSortEntity> picSorts = housePicSortDao.select(
        "findHousePicSortByHouseIdAndType", para);
    if (CollectionUtils.isNotEmpty(picSorts)) {
      sort = picSorts.get(0).getSort();
    }
    
    model.addAttribute("sort", sort);
    model.addAttribute("picType", 3);
    model.addAttribute("auditId", auditId);
    
    return "external/house/houseView";
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "houseView.controller", method = RequestMethod.GET)
  public String houseView(Model model, Integer houseId) {
    
    model = find(model, houseId);
    
    String sort = null;
    Map<String,Object> para = new HashMap<String,Object>();
    para.put("houseId", houseId);
    para.put("type", 3);
    List<HousePicSortEntity> picSorts = housePicSortDao.select(
        "findHousePicSortByHouseIdAndType", para);
    if (CollectionUtils.isNotEmpty(picSorts)) {
      sort = picSorts.get(0).getSort();
    }
    
    model.addAttribute("sort", sort);
    model.addAttribute("picType", 3);
    
    HouseExtEntity house = (HouseExtEntity) model.asMap().get("house");
    
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("type", AccountEntity.TypeEnum.External.value + ","
        + AccountEntity.TypeEnum.Combine.value);
    map.put("residenceID", house.getResidenceId());
    List<AccountExtEntity> accountList = (List<AccountExtEntity>) accountResidenceDao
        .select("findResidenceAccountList", map);
    
    model.addAttribute("accountList", accountList);
    
    return "house/houseViewExt";
  }
  
  @RequestMapping(value = "houseEdit.controller", method = RequestMethod.GET)
  public String houseEdit(Model model, Integer houseId) {
    
    model = find(model, houseId);
    
    HouseExtEntity house = (HouseExtEntity) model.asMap().get("house");
    
    int currentUserId = HouseMartContext.getCurrentUserId();
    AccountExtEntity account = (AccountExtEntity) this.accountDao.load(
        "loadAccountById", currentUserId);
    
    String viewType = this.getRequest().getParameter("viewType");
    String create = this.getRequest().getParameter("create");
    
    if (house.getId() == null) {
      
      if (viewType == null
          && (account.getType().equals(AccountEntity.TypeEnum.Internal.value) || account
              .getType().equals(AccountEntity.TypeEnum.Combine.value))) {
        
        return "house/houseRegister";
        
      } else {
        
        List<AccountResidenceEntity> accountResidences = account
            .findResidencesByAccount();
        Map<Object,Object> param = new HashMap<Object,Object>();
        param.put("viewType", viewType);
        model.addAttribute("param", param);
        model.addAttribute("create", "true");
        model.addAttribute("accountResidences", accountResidences);
        return "external/house/houseRegister";
      }
    } else {
      if (house.getSourceType().equals(
          HouseEntity.SourceTypeEnum.external.value)) {
        List<AccountResidenceEntity> accountResidences = account
            .findResidencesByAccount();
        Map<Object,Object> param = new HashMap<Object,Object>();
        if (house.getRentRentStatus() != null && house.getRentRentStatus() == 1) {
          viewType = "rent";
        } else {
          viewType = "sale";
        }
        param.put("viewType", viewType);
        model.addAttribute("create", create);
        model.addAttribute("param", param);
        model.addAttribute("accountResidences", accountResidences);
        
        if (account.getType().equals(AccountEntity.TypeEnum.Internal.value)
            || account.getType().equals(AccountEntity.TypeEnum.Combine.value)) {
          return "external/house/houseRegisterInternal";
        } else {
          return "external/house/houseRegister";
        }
      } else {
        return "house/houseDetail";
      }
    }
  }
  
  @RequestMapping(value = "ajax/ViewHouseContact.controller", method = RequestMethod.GET)
  public ModelAndView houseContactView(Model model, int houseId) {
    
    AjaxResultBean result = new AjaxResultBean();
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("houseId", houseId);
    
    boolean haspower = houseContactViewService.isBrokerViewContact(
        HouseMartContext.getCurrentUserId(), houseId);
    
    if (haspower) {
      find(model, houseId);
      Map<String,Object> data = new HashMap<String,Object>();
      if (model.asMap().get("maxinData") != null) {
        data.put("maxinData", model.asMap().get("maxinData"));
      }
      if (model.asMap().get("house") != null) {
        data.put("house", model.asMap().get("house"));
      }
      result.setBizData(data);
      result.setCode(1);
    } else {
      result.setCode(0);
    }
    
    return new ModelAndView("jsonView", "json", result);
    
  }
  
  @SuppressWarnings({"rawtypes", "deprecation", "unused"})
  @RequestMapping(value = "external/houseUpdate.controller", method = RequestMethod.POST)
  public ModelAndView addOrUpdateExt(Model model, HouseExtEntity house,
      HouseRoomType roomType, HouseRentEquipment rentEquipt) throws Exception {
    
    if (roomType.getShi() != null) {
      Integer[] rTypeArray = roomType.toArray();
      house.setRoomType(arrayToInteger(rTypeArray, rTypeArray.length));
    }
    
    if (StringUtils.isNotBlank(house.getBuildTimeString())) {
      house.setBuildTime(new Date(
          Integer.valueOf(house.getBuildTimeString()) - 1900, 0, 1));
    }
    
    try {
      if (rentEquipt != null) house.setRentEquipmentList(HouseRentEquipment
          .obj2String(rentEquipt));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    
    HouseSaleEntity sale = null;
    HouseRentEntity rent = null;
    
    if (house != null && house.getId() != null) {
      // 房源更新
      HouseExtEntity housePre = (HouseExtEntity) houseDao.load("loadHouseExt",
          house.getId());
      
      if (housePre.getCreator() != HouseMartContext.getCurrentUserId()) {
        return new ModelAndView(new RedirectView(
            "external/myHouseList?sysMsg=无权限保存该房源"));
      }
      
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
      
      sale = house.generateHouseSaleEntity();
      rent = house.generateHouseRentEntity();
      
      sale.setSaleStatus(housePre.getSaleSaleStatus());
      rent.setRentStatus(housePre.getRentRentStatus());
      
      house.setUpdateTime(Calendar.getInstance().getTime());
      sale.setUpdateTime(Calendar.getInstance().getTime());
      rent.setUpdateTime(Calendar.getInstance().getTime());
      
      houseDao.update("updateHouse", house);
      
      List houseProperty = houseSaleDao.select("findHouseSaleListByHouseId",
          new UniqueIdObject(house.getId()));
      
      List houseSale = houseSaleDao.select("findHouseSaleListByHouseId",
          new UniqueIdObject(house.getId()));
      
      if (houseSale != null && houseSale.size() > 0) {
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
      HouseMartContext.setSysMsg("房源信息已保存");
      
      String create = this.getRequest().getParameter("create");
      return new ModelAndView(new RedirectView(
          "/external/housePicConsole.controller?houseId="
              + house.getId()
              + "&sourceType="
              + house.getSourceType()
              + "&picType=3"
              + (StringUtils.equalsIgnoreCase("true", create) ? "&create=true"
                  : "")));
    } else {
      // 登盘
      house.setSourceType(HouseEntity.SourceTypeEnum.external.value);
      
      house.setStatus(StatusEnum.Default.value);
      house.setAddTime(Calendar.getInstance().getTime());
      house.setUpdateTime(Calendar.getInstance().getTime());
      house.setCreator(HouseMartContext.getCurrentUserId());
      houseDao.add("addHouse", house);
      
      sale = house.generateHouseSaleEntity();
      rent = house.generateHouseRentEntity();
      
      String viewType = this.getRequest().getParameter("viewType");
      
      if (viewType.equals("sale")) {
        sale.setPrice(house.getSalePrice() * 10000);
        sale.setAvgPrice(sale.getPrice() / house.getPropertyArea());
        sale.setSaleStatus(HouseSaleEntity.SaleStatusEnum.Saling.saleStatus);
      }
      
      if (viewType.equals("rent")) {
        rent.setRentStatus(HouseRentEntity.RentStatusEnum.Renting.rentStatus);
      }
      
      sale.setHouseId(house.getId());
      sale.setSource("2");
      sale.setAddTime(Calendar.getInstance().getTime());
      sale.setUpdateTime(Calendar.getInstance().getTime());
      rent.setHouseId(house.getId());
      rent.setAddTime(Calendar.getInstance().getTime());
      rent.setUpdateTime(Calendar.getInstance().getTime());
      
      houseSaleDao.add("addHouseSale", sale);
      houseRentDao.add("addHouseRent", rent);
      
      // 保存不提交审核
      // auditService.requestAddNewHouse(house.getId());
      HouseMartContext.setSysMsg("已添加新房源");
      return new ModelAndView(new RedirectView(
          "/external/housePicConsole.controller?houseId=" + house.getId()
              + "&sourceType=" + house.getSourceType()
              + "&picType=3&create=true"));
    }
    
  }
  
  @RequestMapping(value = "isBrokerViewContact.controller", method = RequestMethod.GET)
  public ModelAndView isBrokerViewContact(Model model,
      @RequestParam int houseId, @RequestParam int brokerId) {
    
    AjaxResultBean result = new AjaxResultBean();
    
    boolean haspower = houseContactViewService.isBrokerViewContact(brokerId,
        houseId);
    
    if (haspower) {
      result.setCode(1);
    } else {
      result.setCode(0);
    }
    
    return new ModelAndView("jsonView", "json", result);
    
  }
  
  @RequestMapping(value = "soldHouseEdit.controller", method = RequestMethod.GET)
  public String saleHouseEdit(Model model, Integer houseId) {
    model = find(model, houseId);
    return "house/soldHouseDetailEditable";
  }
  
  @RequestMapping(value = "soldHouseSubmit.controller", method = RequestMethod.POST)
  public ModelAndView saleHouseUpdate(Model model, HouseExtEntity house) {
    HouseContactEntity contact = null;
    HousePropertyEntity property = null;
    HouseSaleEntity sale = null;
    HouseSoldBuildingEntity soldBuildingInfo = null;
    
    if (house != null && house.getId() != null) {
      contact = house.generateHouseContactEntity();
      property = house.generateHousePropertyEntity();
      sale = house.generateHouseSaleEntity();
      soldBuildingInfo = house.generateHouseSoldBuildingEntity();
      
      house.setUpdateTime(Calendar.getInstance().getTime());
      contact.setUpdateTime(Calendar.getInstance().getTime());
      property.setUpdateTime(Calendar.getInstance().getTime());
      sale.setUpdateTime(Calendar.getInstance().getTime());
      
      houseDao.update("updateHouse", house);
      houseContactDao.update("updateHouseContactByHouseId", contact);
      housePropertyDao.update("updateHousePropertyByHouseId", property);
      houseSaleDao.update("updateHouseSaleByHouseId", sale);
      houseSoldBuildingDao.update("updateHouseSaleByHouseId", soldBuildingInfo);
    } else {
      house.setStatus(StatusEnum.Valid.value);
      house.setAddTime(Calendar.getInstance().getTime());
      house.setUpdateTime(Calendar.getInstance().getTime());
      house.setCreator(HouseMartContext.getCurrentUserId());
      houseDao.add("addHouse", house);
      
      contact = house.generateHouseContactEntity();
      property = house.generateHousePropertyEntity();
      sale = house.generateHouseSaleEntity();
      soldBuildingInfo = house.generateHouseSoldBuildingEntity();
      
      contact.setHouseId(house.getId());
      contact.setAddTime(Calendar.getInstance().getTime());
      contact.setUpdateTime(Calendar.getInstance().getTime());
      property.setHouseId(house.getId());
      property.setAddTime(Calendar.getInstance().getTime());
      property.setUpdateTime(Calendar.getInstance().getTime());
      
      sale.setHouseId(house.getId());
      sale.setAddTime(Calendar.getInstance().getTime());
      sale.setUpdateTime(Calendar.getInstance().getTime());
      
      soldBuildingInfo.setHouseId(house.getId());
      soldBuildingInfo.setBuildingInfo(house.getSoldBuildingInfo());
      
      houseContactDao.add("addHouseContact", contact);
      housePropertyDao.add("addHouseProperty", property);
      houseSaleDao.add("addHouseSale", sale);
      houseSoldBuildingDao.add("addHouseSoldBuilding", soldBuildingInfo);
    }
    
    return new ModelAndView(new RedirectView(
        "soldHouseEdit.controller?houseId=" + house.getId()));
  }
  
  @RequestMapping(value = "soldHouseDetail.controller", method = RequestMethod.GET)
  public String soldHouseDetail(Model model, Integer houseId) {
    model = find(model, houseId);
    return "house/soldHouseDetail";
  }
  
  @RequestMapping(value = "myHouseList.controller", method = RequestMethod.GET)
  public String myHouseList(Model model, Integer page, Integer pageSize,
      String orderByClause) {
    // int houseStatus = 1;
    model = super.findList(model, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null,
        HouseMartContext.getCurrentUserId(), null, null, null, null, null,
        null, null, null, true, null, page, pageSize, orderByClause);
    return "house/myHouseList";
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "myExternalHouseList.controller", method = RequestMethod.GET)
  public String myExternalHouseList(Model model, Integer page,
      Integer pageSize, String orderByClause, Integer regionId,
      Integer plateId, String residenceName, String creatorName,
      Integer saleRent, Integer status, Integer clientType
      ) {
    
	saleRent = (saleRent == null ? 0 : saleRent);
	
	clientType = (clientType == null ? 0 : clientType);
	
	model.addAttribute("saleRent", saleRent);
	model.addAttribute("status", status);
	model.addAttribute("clientType", clientType);
	
	
    Integer currentUserId = HouseMartContext.getCurrentUserId();
    AccountExtEntity account = (AccountExtEntity) this.accountDao.load(
            "loadAccountById", currentUserId);
    
    String type = "";
    if (currentUserId == 0 || account.getPositionType().equals("区域经理"))
    {
      currentUserId = null;
      model.addAttribute("accountType", "admin");
    }
    else
    {
      
      if (account.getPositionType().equals("区域经理")) {
        List<AccountResidenceEntity> accountRegions = account
            .findRegionsByAccount();
        List<AccountResidenceEntity> regions = new ArrayList<AccountResidenceEntity>();
        List<AccountResidenceEntity> plates = new ArrayList<AccountResidenceEntity>();
        
        for (AccountResidenceEntity accountRegion : accountRegions) {
          if (accountRegion.getResidenceIDType().equals(
              AccountResidenceEntity.ResidenceIDTypeEnum.Plate.value)) {
            plates.add(accountRegion);
          } else {
            regions.add(accountRegion);
          }
        }
        
        model.addAttribute("regions", regions);
        model.addAttribute("plates", plates);
        model.addAttribute("accountType", "manager");
      } else {
        model.addAttribute("accountType", "broker");
      }
    }
    
    if (!type.equals("broker")) {
      Map<Object,Object> map = new HashMap<Object,Object>();
      map.put("type", AccountEntity.TypeEnum.External.value + ","
          + AccountEntity.TypeEnum.Combine.value);
      List<AccountExtEntity> accountList = (List<AccountExtEntity>) accountResidenceDao
          .select("findResidenceAccountList", map);
      
      model.addAttribute("accountList", accountList);
    }
    
    if (StringUtils.isEmpty(residenceName)) {
      residenceName = null;
    }
    
    if (StringUtils.isEmpty(creatorName)) {
      creatorName = null;
    }
    
    model = super.findList(model, null, regionId, plateId, residenceName,
        creatorName, null, null, null, null, null, null, null, status, saleRent == 1 ? 1 : null,
        saleRent == 2 ? 1 : null, currentUserId, null, null, null, null, null, null,
        HouseEntity.SourceTypeEnum.external.value, null, true,
        clientType,
        page, pageSize, orderByClause);
    
    return "house/myExternalHouseList";
  }
  
  @RequestMapping(value = "myFavoriteHouseList.controller", method = RequestMethod.GET)
  public String myFavoriteHouseList(Model model, Integer page,
      Integer pageSize, String orderByClause) {
    // int houseStatus = 1;
    model = super.findList(model, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null,
        HouseMartContext.getCurrentUserId(), true, null, null, null, null,
        null, null, true, null, page, pageSize, orderByClause);
    return "house/myFavoriteHouseList";
  }
  
  @RequestMapping(value = "soldHouseList.controller", method = RequestMethod.GET)
  public String soldHouseList(Model model, Integer regionCityId,
      Integer regionParentId, Integer regionRegionId, String residenceName,
      String buildingInfo, String cellInfo, Integer salePriceGT,
      Integer salePriceLE, Integer propertyAreaGT, Integer propertyAreaLE,
      Integer page, Integer pageSize, String orderByClause) {
    int saleStatus = 3;
    model = super.findList(model, regionCityId, regionParentId, regionRegionId,
        residenceName, null, buildingInfo, cellInfo, salePriceGT, salePriceLE,
        propertyAreaGT, propertyAreaLE, null, null, saleStatus, null, null,
        null, null, null, null, null, null,
        HouseEntity.SourceTypeEnum.internal.value, null, true, null,
        page, pageSize, orderByClause);
    return "house/soldHouseList";
  }
  
  @RequestMapping(value = "houseList.controller", method = RequestMethod.GET)
  public String houseList(Model model, Integer regionCityId,
      Integer regionParentId, Integer regionRegionId, String residenceName,
      String creatorName, String buildingInfo, String cellInfo,
      Integer salePriceGT, Integer salePriceLE, Integer propertyAreaGT,
      Integer propertyAreaLE, HouseRoomType roomType, Integer status,
      Integer saleStatus, Integer rentStatus, String saleIntention,
      String rentIntention, String dialResult, String phone,
      Integer sourceType, String sourceTypeIn, Integer page, Integer pageSize,
      String orderByClause) {
    model = super.findList(model, regionCityId, regionParentId, regionRegionId,
        residenceName, creatorName, buildingInfo, cellInfo, salePriceGT,
        salePriceLE, propertyAreaGT, propertyAreaLE, roomType, status,
        saleStatus, rentStatus, null, HouseMartContext.getCurrentUserId(),
        null, saleIntention, rentIntention, dialResult, phone, null,
        sourceTypeIn, true, null, page, pageSize, orderByClause);
    return "house/houseList";
  }
  
  @RequestMapping(value = "ajax/deleteHouse.controller")
  public ModelAndView deleteHouse(@RequestParam("id") int id, @RequestParam(required = false) Integer verify) {
      
	verify = (verify == null ? 1 : verify);
	
    AjaxResultBean result = new AjaxResultBean();
    
    HouseExtEntity house = (HouseExtEntity) houseDao.load("loadHouseExt", id);
    
    int accound_id = HouseMartContext.getCurrentUserId();
    
    if (verify == 0 || house.getCreator().equals(accound_id)) {
      house.setStatus(HouseEntity.StatusEnum.Deleted.value);
      house.setUpdateTime(new Date());
      houseDao.update("updateHouse", house);
      
      List<Integer> houseIds = new ArrayList<Integer>();
      houseIds.add(house.getId());
      HouseInteractionNotifier notifier = new HouseInteractionNotifier();
      notifier.changeToWithoutInteraction(houseIds);
    }
    return new ModelAndView("jsonView", "json", result);
  }
  
  /*
   * internal ios server调用
   */
  @RequestMapping(value = "ajax/deleteHouseInt.controller")
  public ModelAndView deleteHouseInt(@RequestParam("ids") String ids) {
    
    AjaxResultBean result = new AjaxResultBean();
    
    String[] houses = ids.split(",");
    
    for (int i = 0; i < houses.length; i++) {
    	this.deleteHouse(Integer.parseInt(houses[i].trim()), 0);
    }
    
    return new ModelAndView("jsonView", "json", result);
  }
  
  @RequestMapping(value = "ajax/deactiveHouse.controller")
  public ModelAndView deactiveHouse(
      @RequestParam(value = "id", required = false) Integer id,
      @RequestParam(value = "ids", required = false) String ids,
      @RequestParam(value = "comments", required = false) String comments,
      @RequestParam(required = false) Integer verify
      ) {
    
	verify = (verify == null ? 1 : verify);
	
    AjaxResultBean result = new AjaxResultBean();
    
    String[] houses = new String[1];
    
    if (ids != null) {
      houses = ids.split(",");
    } else {
      houses[0] = id.toString();
    }
    
    List<Integer> houseIds = new ArrayList<Integer>();
    
    for (int i = 0; i < houses.length; i++) {
      int houseId = Integer.parseInt(houses[i]);
      
      HouseExtEntity house = (HouseExtEntity) houseDao.load("loadHouseExt",
          houseId);
      
      house.setStatus(HouseEntity.StatusEnum.OffBoard.value);
      house.setUpdateTime(new Date());
      houseDao.update("updateHouse", house);
      
      houseIds.add(house.getId());
      
      auditService.offboardHouses(houses, comments);
    }
    
    HouseInteractionNotifier notifier = new HouseInteractionNotifier();
    notifier.changeToWithoutInteraction(houseIds);
    
    return new ModelAndView("jsonView", "json", result);
  }
  
  /*
   * internal ios server调用
   */
  @RequestMapping(value = "ajax/deactiveHouseInt.controller")
  public ModelAndView deactiveHouseInt(
      @RequestParam(value = "ids", required = false) String ids,
      @RequestParam(value = "comments", required = false) String comments) {
	  
	  return this.deactiveHouse(null, ids, comments, 0);
  }
  
  @RequestMapping(value = "ajax/applyHouse.controller")
  public ModelAndView applyHouse(@RequestParam("id") int id, @RequestParam(required = false) Integer verify) {
    
	verify = (verify == null ? 1 : verify);
	
    AjaxResultBean result = new AjaxResultBean();
    
    HouseExtEntity house = (HouseExtEntity) houseDao.load("loadHouseExt", id);
    
    int accound_id = HouseMartContext.getCurrentUserId();
    if (verify == 0 || house.getCreator().equals(accound_id)) {
      house.setStatus(HouseEntity.StatusEnum.Default.value);
      house.setUpdateTime(new Date());
      houseDao.update("updateHouse", house);
      auditService.requestAddNewHouse(id);
    }
    return new ModelAndView("jsonView", "json", result);
  }
  
  /*
   * internal ios server调用
   */
  @RequestMapping(value = "ajax/applyHouseInt.controller")
  public ModelAndView applyHouseInt(
      @RequestParam(value = "ids", required = false) String ids) {
    
	  AjaxResultBean result = new AjaxResultBean();
	  
	  String[] houses = ids.split(",");
	  
	  for (int i = 0; i < houses.length; i++) {
		  this.applyHouse(Integer.parseInt(houses[i].trim()), 0);
	  }
	    
	  return new ModelAndView("jsonView", "json", result);
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "ajax/assignHouse.controller")
  public ModelAndView assignHouse(
      @RequestParam(value = "id", required = false) Integer id,
      @RequestParam(value = "ids", required = false) String ids,
      @RequestParam("brokerId") int brokerId,
      @RequestParam(value = "assignHistory", required = false) Integer assignHistory
      ) {
    
    AjaxResultBean result = new AjaxResultBean();
    
    assignHistory = (assignHistory == null ? 1 : assignHistory);
    
    String[] houses = new String[1];
    
    if (ids != null) {
      houses = ids.split(",");
    } else {
      houses[0] = id.toString();
    }
    
    for (int i = 0; i < houses.length; i++) {
      
      int houseId = Integer.parseInt(houses[i]);
      HouseExtEntity house = (HouseExtEntity) houseDao.load("loadHouseExt", houseId);
      
      if (!house.getCreator().equals(brokerId)) {
    	Integer old_creator = house.getCreator();
        HouseEntity houseUpdate = new HouseEntity();
        houseUpdate.setId(houseId);
        houseUpdate.setCreator(brokerId);
        houseUpdate.setLastUpdater(house.getCreator());
        houseUpdate.setUpdateTime(new Date());
        houseDao.update("updateHouse", houseUpdate);
        
        // 回收交互权
        this.houseService.revokeInteraction(houseId);
        
        if (assignHistory == 1)
        {
        	this.houseService.doInteractionTransfer(houseId, old_creator, brokerId, "分配房源时迁移");
        }
    
      }
    }
    
    return new ModelAndView("jsonView", "json", result);
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "external/myResidenceList.controller", method = RequestMethod.GET)
  public String residenceList(Model model) {
    
    List<AccountResidenceEntity> list = accountResidenceDao.select(
        "findAccountResidenceList",
        new UniqueIdObject(HouseMartContext.getCurrentUserId()));
    List<ResidenceEntity> rList = null;
    if (CollectionUtils.isNotEmpty(list)) {
      rList = new ArrayList<ResidenceEntity>();
      for (AccountResidenceEntity item : list) {
        ResidenceEntity entiti = (ResidenceEntity) residenceDao.load(
            "loadResidence", item.getResidenceID());
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("residenceId", entiti.getId());
        param.put("commiterId", HouseMartContext.getCurrentUserId());
        param.put("result",
            ResidenceAuditHistoryEntity.ResultEnum.Default.toString());
        param.put("type", AuditTypeEnum.ContentUpdateAudit.getValue());
        List<ResidenceAuditHistoryEntity> auditHistories = residenceAuditHistoryDao
            .select("findResidenceAuditHistoryList", param);
        if(CollectionUtils.isNotEmpty(auditHistories)){
          entiti.setUpdateAuditPendingCount(auditHistories.size());
        }
        rList.add(entiti);
      }
      model.addAttribute("list", rList);
    }
    
    return "external/residence/list";
    
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "external/myResidenceCheckList.controller", method = RequestMethod.GET)
  public String residenceCheck(Model model,
      @RequestParam(required = false) String keyword) {
    
    if (!StringUtils.isEmpty(keyword)) {
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("name", keyword);
      List<Integer> ids = maxinRawDao.select("findResidenceIdLikeName", map);
      if (CollectionUtils.isNotEmpty(ids)) {
        List<ResidenceEntity> rList = new ArrayList<ResidenceEntity>();
        for (Integer item : ids) {
          ResidenceEntity entiti = (ResidenceEntity) residenceDao.load(
              "loadResidence", item);
          rList.add(entiti);
        }
        model.addAttribute("list", rList);
      }
      
    }
    
    return "external/residence/search";
    
  }
}
