/**
 * 
 */
package org.housemart.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.housemart.dao.entities.GooglePlaceBaseEntity;
import org.housemart.dao.entities.ResidenceBuildingEntity;
import org.housemart.dao.entities.ResidenceCellEntity;
import org.housemart.dao.entities.ResidenceEntity;
import org.housemart.dao.entities.ResidenceNameHistoryEntity;
import org.housemart.dao.entities.ResidenceExtEntity;
import org.housemart.dao.entities.ResidenceMonthDataEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.dao.generic.UniqueIdObject;
import org.housemart.resource.ResourceProvider;
import org.housemart.service.AuditService;
import org.housemart.service.SearchService;
import org.housemart.service.enums.ActionType;
import org.housemart.service.enums.PriviledgeResultType;
import org.housemart.service.enums.ResidenceAuditContentKeys;
import org.housemart.service.priviledge.ResidencePrivilegeService;
import org.housemart.util.JsonUtils;
import org.housemart.util.PinyinTranslator;
import org.housemart.web.beans.AjaxResultBean;
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
 * @author pai.li
 * 
 */
@Controller
public class ResidenceController extends BaseController {
  
  @Autowired
  private ResourceProvider resourceProvider;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceBuildingDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceCellDao;
  
  @Autowired
  SearchService searchService;
  
  @Autowired
  @SuppressWarnings("rawtypes")
  GenericDao googlePlaceDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceMonthDataDao;
  
  @Autowired
  @SuppressWarnings("rawtypes")
  GenericDao residenceNameHistoryDao;
  
 @Autowired
  ResidencePrivilegeService residencePrivilegeService;
  
  @Autowired
  AuditService auditService;
  
  /**
   * 小区列表
   * 
   * @param model
   * @return
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "residenceList.controller", method = RequestMethod.GET)
  public String residenceList(Model model, final String positionSet, String forceShow, String zombie, 
		 String lockBasicInfo, String lockMap, String lockPic, final String hasPic, final String picApprove, String regionId, String plateId,
		 String residenceName, Integer page, Integer pageSize) {
    
    String decodedResidenceName = null;
    if (residenceName != null) {
      try {
        decodedResidenceName = URLDecoder.decode(residenceName, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        logger.error(e.getMessage(), e);
      }
    }
    
    List<Object> list = null;
    List<ResidenceEntity> newList = null;
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("residenceName", decodedResidenceName);
    param.put("status", 1);
    if (!StringUtils.isEmpty(plateId)) {
      param.put("id", plateId);
    }
    if (!StringUtils.isEmpty(regionId)) {
      param.put("regionId", regionId);
    }
    if (!StringUtils.isEmpty(positionSet)) {
        param.put("positionSet", positionSet);
      }
    if (!StringUtils.isEmpty(forceShow)) {
        param.put("forceShow", forceShow);
      }
    if (!StringUtils.isEmpty(zombie)) {
        param.put("zombie", zombie);
      }
    if (!StringUtils.isEmpty(lockBasicInfo)) {
        param.put("lockBasicInfo", lockBasicInfo);
      }
    if (!StringUtils.isEmpty(lockMap)) {
        param.put("lockMap", lockMap);
      }
    if (!StringUtils.isEmpty(lockPic)) {
        param.put("lockPic", lockPic);
      }    
    if (!StringUtils.isEmpty(hasPic)) {
        param.put("hasPic", hasPic);
      }
    if (!StringUtils.isEmpty(picApprove)) {
        param.put("picApprove", picApprove);
      }

    Integer totalCount = residenceDao.count("countResidenceList", param);
    
    // for pagination query
    if (page == null) page = 0;
    if (pageSize == null) pageSize = 100;

    list = residenceDao.paginate("findResidenceList", page, pageSize, param).getResult();

    if(!CollectionUtils.isEmpty(list)){
    	newList = new ArrayList<ResidenceEntity>();
    	Iterator<Object> iterator = list.iterator();
    	while(iterator.hasNext()){
    		newList.add((ResidenceEntity)iterator.next());
    		iterator.remove();
    	}
    }
    
    if (!CollectionUtils.isEmpty(newList)) {
      for (ResidenceEntity item : newList) {
    	  
        List<GooglePlaceBaseEntity> tempList = googlePlaceDao.select(
            "findSetPositionResidenceById",
            new UniqueIdObject(item.getResidenceId()));
        if (tempList != null && tempList.size() >= 1) {
          GooglePlaceBaseEntity target = tempList.get(0);
          if (!StringUtils.isEmpty(target.getLat())
              && !StringUtils.isEmpty(target.getLng())) {
        	  item.setPositionSet(true);
          }
        }
        
        searchService.populateResidenceData(item);
       
      }
      CollectionUtils.filter(list, new Predicate(){

		@Override
		public boolean evaluate(Object item) {
			ResidenceEntity obj = (ResidenceEntity)item;
	      	if (!StringUtils.isEmpty(positionSet) && StringUtils.equals(positionSet, "1") && !obj.isPositionSet()) {return false;}
	      	if (!StringUtils.isEmpty(positionSet) && StringUtils.equals(positionSet, "0")&&obj.isPositionSet()) {return false;}
	      	
	      	//if (!StringUtils.isEmpty(hasPic) && StringUtils.equals(hasPic, "0") && obj.getPicCount() > 0) {return false;}
	      	//else if (!StringUtils.isEmpty(hasPic) && StringUtils.equals(hasPic, "1") && obj.getPicCount() == 0) {return false;}

	      	//if (!StringUtils.isEmpty(picApprove) && StringUtils.equals(picApprove, "0") && obj.getPicApprove() > 0) {return false;}
	      	//else if (!StringUtils.isEmpty(picApprove) && StringUtils.equals(picApprove, "1") && obj.getPicApprove() == 0) {return false;}

	      	return true;
		}

      	});
 
    }
    
    model.addAttribute("positionSet", positionSet);
    model.addAttribute("forceShow", forceShow);
    model.addAttribute("zombie", zombie);
    model.addAttribute("lockBasicInfo", lockBasicInfo);
    model.addAttribute("lockMap", lockMap);
    model.addAttribute("lockPic", lockPic);
    model.addAttribute("list", newList);
    model.addAttribute("regionId", regionId);
    model.addAttribute("plateId", plateId);
    model.addAttribute("residenceName", decodedResidenceName);
    model.addAttribute("hasPic", hasPic);
    model.addAttribute("picApprove", picApprove);
    model.addAttribute("page", page);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("totalCount", totalCount);
    model.addAttribute("pageCount", newList == null ? 0 : newList.size());
    return "residence/residenceList";
  }
  
  /**
   * 小区详情
   * 
   * @param model
   * @param id
   * @return
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonGenerationException
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "residenceDetail.controller", method = RequestMethod.GET)
  public String residecenDetail(Model model, @RequestParam("id") int id)
      throws JsonGenerationException, JsonMappingException, IOException {
    ResidenceEntity entity = (ResidenceEntity) residenceDao.load(
        "loadResidenceById", id);
    if (entity != null) {
      model.addAttribute("entity", entity);
    }
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("residenceId", id);
    List<GooglePlaceBaseEntity> list = googlePlaceDao.select(
        "findRawDatByResidenceId", map);
    if (!CollectionUtils.isEmpty(list)) {
      GooglePlaceBaseEntity avgPoint = null;
      for (GooglePlaceBaseEntity item : list) {
        if (item.getIsMain() == null) {
          item.setIsMain(false);
        } else if (item.getIsMain() == true) {
          avgPoint = item;
          model.addAttribute("avgPoint", item);
        }
      }
      if (avgPoint == null) {
        avgPoint = new GooglePlaceBaseEntity();
        double avgLat = 0;
        double avgLng = 0;
        for (GooglePlaceBaseEntity item : list) {
          avgLat += Double.parseDouble(item.getLat());
          avgLng += Double.parseDouble(item.getLng());
        }
        avgPoint.setLat((avgLat / list.size() + ""));
        avgPoint.setLng((avgLng / list.size() + ""));
        model.addAttribute("avgPoint", avgPoint);
      }
    }
    
    if (!CollectionUtils.isEmpty(list)) {
      model.addAttribute("points", JsonUtils.writeValue(list));
    } else {
      model.addAttribute("points", "[]");
    }
    
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("residenceId", id);
    List<ResidenceMonthDataEntity> monthDatas = residenceMonthDataDao.select(
        "findByResidence", param);
    ResidenceMonthDataEntity monthData = null;
    if (monthDatas != null && monthDatas.size() > 0) {
      monthData = monthDatas.get(0);
    }
    model.addAttribute("monthData", monthData);
    return "residence/residenceDetail";
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "residenceMap.controller", method = RequestMethod.GET)
  public String residenceMap(Model model, @RequestParam("id") int id)
      throws JsonGenerationException, JsonMappingException, IOException {
    ResidenceEntity entity = (ResidenceEntity) residenceDao.load(
        "loadResidenceById", id);
    if (entity != null) {
      model.addAttribute("entity", entity);
    }
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("residenceId", id);
    List<GooglePlaceBaseEntity> list = googlePlaceDao.select(
        "findRawDatByResidenceId", map);
    if (!CollectionUtils.isEmpty(list)) {
      GooglePlaceBaseEntity avgPoint = null;
      for (GooglePlaceBaseEntity item : list) {
        if (item.getIsMain() == null) {
          item.setIsMain(false);
        } else if (item.getIsMain() == true) {
          avgPoint = item;
          model.addAttribute("avgPoint", item);
        }
      }
      if (avgPoint == null) {
        avgPoint = new GooglePlaceBaseEntity();
        double avgLat = 0;
        double avgLng = 0;
        for (GooglePlaceBaseEntity item : list) {
          avgLat += Double.parseDouble(item.getLat());
          avgLng += Double.parseDouble(item.getLng());
        }
        avgPoint.setLat((avgLat / list.size() + ""));
        avgPoint.setLng((avgLng / list.size() + ""));
        model.addAttribute("avgPoint", avgPoint);
      }
    }
    
    if (!CollectionUtils.isEmpty(list)) {
      model.addAttribute("points", JsonUtils.writeValue(list));
    } else {
      model.addAttribute("points", "[]");
    }
    
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("residenceId", id);
    List<ResidenceMonthDataEntity> monthDatas = residenceMonthDataDao.select(
        "findByResidence", param);
    ResidenceMonthDataEntity monthData = null;
    if (monthDatas != null && monthDatas.size() > 0) {
      monthData = monthDatas.get(0);
    }
    model.addAttribute("monthData", monthData);
    return "residence/mapEdit";
  }
  
  @RequestMapping(value = "addResidenceBuilding.controller")
  public String addResidenceBuilding(Model model,
      @RequestParam("residenceId") int residenceId) {
    
    ResidenceEntity entity = (ResidenceEntity) residenceDao.load(
        "loadResidence", residenceId);
    if (entity != null) {
      model.addAttribute("entity", entity);
    }
    return "residence/building/addBuilding";
  }
  
  @RequestMapping(value = "ajax/confirmResidencePosition.controller")
  public ModelAndView confirmResidencePosition(
      @RequestParam("residenceId") int residenceId,
      @RequestParam("mapPlaceId") int mapPlaceId) {
    
    AjaxResultBean result = new AjaxResultBean();
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("residenceId", residenceId);
    map.put("id", mapPlaceId);
    googlePlaceDao.update("resetResidencePosition", map);
    googlePlaceDao.update("updateResidencePosition", map);
    return new ModelAndView("jsonView", "json", result);
    
  }
  
  @RequestMapping(value = "ajax/confirmResidencePositionMannally.controller")
  public ModelAndView confirmResidencePositionMannally(
      @RequestParam("residenceId") int residenceId,
      @RequestParam("lat") String lat, @RequestParam("lng") String lng) {
    
    AjaxResultBean result = new AjaxResultBean();
    synchronized (googlePlaceDao) {
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("residenceId", residenceId);
      googlePlaceDao.update("resetResidencePosition", map);
      
      GooglePlaceBaseEntity entity = new GooglePlaceBaseEntity();
      entity.setResidenceId(residenceId);
      entity.setIsMain(true);
      entity.setKeyword(null);
      entity.setName("手动添加经纬度(无名称)");
      entity.setAddress(null);
      entity.setLat(lat);
      entity.setLng(lng);
      googlePlaceDao.add("addResidencePosition", entity);
    }
    return new ModelAndView("jsonView", "json", result);
    
  }

  @RequestMapping(value = "ajax/getNameHistory.controller")
  public ModelAndView getNameHistory(
      @RequestParam("residenceId") int residenceId,
      @RequestParam("nameType") int nameType) {
	Map<String,Object> param = new HashMap<String,Object>();
	param.put("residenceId", residenceId);
	param.put("type", nameType);
	    
	@SuppressWarnings("unchecked")
	List<ResidenceNameHistoryEntity> list = residenceNameHistoryDao.select("findResidenceNameHistoryList", param);
    //for (ResidenceNameHistoryEntity item : list) {
    //	item.datetime
    //}

	AjaxResultBean result = new AjaxResultBean();
	result.setBizData(list);
    return new ModelAndView("jsonView", "json", result);
    
  }

  
  /**
   * 
   * @param residenceId
   * @param prefix
   * @param buildingType
   * @param begin
   * @param end
   * @param suffix
   * @return
   */
  @RequestMapping(value = "ajax/addResidenceBuilding.controller")
  public ModelAndView addResidenceBuilding(
      @RequestParam("residenceId") int residenceId,
      @RequestParam("prefix") String prefix,
      @RequestParam("codeType") int codeType,
      @RequestParam("begin") String begin, @RequestParam("end") String end,
      @RequestParam("suffix") String suffix,
      @RequestParam("codeLimit") int codeLimit,
      @RequestParam("period") String period,
      @RequestParam("stair") String stair,
      @RequestParam("houseHold") String houseHold,
      @RequestParam("buildingType") String buildingType) {
    
    if (residenceId == 0 || (codeType != 1 && codeType != 2) || begin == null
        || end == null) {
      return new ModelAndView("jsonView", "json", null);
    }
    if (codeType == 1 && Integer.parseInt(begin) > Integer.parseInt(end)) {
      return new ModelAndView("jsonView", "json", null);
    }
    if (codeType == 2
        && (begin.length() > 1 || end.length() > 1 || begin.toString()
            .compareToIgnoreCase(end) > 0)) {
      return new ModelAndView("jsonView", "json", null);
    }
    List<Integer> resultIdList = new ArrayList<Integer>();
    // 数字
    if (codeType == 1) {
      int beginIndex = Integer.parseInt(begin);
      int endIndex = Integer.parseInt(end);
      while (endIndex >= beginIndex) {
        if (codeLimit == 1 && endIndex % 2 != 0 || codeLimit == 2
            && endIndex % 2 == 0) {
          endIndex--;
          continue;
        }
        ResidenceBuildingEntity entity = new ResidenceBuildingEntity();
        entity.setResidenceId(residenceId);
        entity.setPrefix(prefix);
        entity.setSuffix(suffix);
        entity.setCodeType(codeType);
        entity.setCodeBegin(String.valueOf(endIndex));
        entity.setCodeEnd(String.valueOf(endIndex));
        entity.setPeriod(period);
        entity.setStair(StringUtils.isEmpty(stair) ? 0 : Integer
            .parseInt(stair));
        entity.setHouseHold(StringUtils.isEmpty(houseHold) ? 0 : Integer
            .parseInt(houseHold));
        entity.setBuildingType(buildingType);
        try {
          resultIdList.add(residenceDao.add("addResidenceBuilding", entity));
        } catch (Exception e) {
          logger.error("addBuilding-number error", e);
        }
        endIndex--;
      }
    }
    
    // 字母
    if (codeType == 2) {
      char cBegin = begin.toUpperCase().charAt(0);
      char cEnd = end.toUpperCase().charAt(0);
      while (cBegin <= 'Z' && cBegin >= 'A' && cEnd <= 'Z' && cEnd >= 'A'
          && cEnd >= cBegin) {
        ResidenceBuildingEntity entity = new ResidenceBuildingEntity();
        entity.setResidenceId(residenceId);
        entity.setPrefix(prefix);
        entity.setSuffix(suffix);
        entity.setCodeType(codeType);
        entity.setCodeBegin(String.valueOf(cEnd));
        entity.setCodeEnd(String.valueOf(cEnd));
        entity.setPeriod(period);
        entity.setStair(StringUtils.isEmpty(stair) ? 0 : Integer
            .parseInt(stair));
        entity.setHouseHold(StringUtils.isEmpty(houseHold) ? 0 : Integer
            .parseInt(houseHold));
        entity.setBuildingType(buildingType);
        try {
          resultIdList.add(residenceDao.add("addResidenceBuilding", entity));
        } catch (Exception e) {
          logger.error("addBuilding-alpha error", e);
        }
        cEnd = (char) ((int) cEnd - 1);
      }
    }
    AjaxResultBean result = new AjaxResultBean();
    result.setBizData(StringUtils.join(resultIdList, ","));
    return new ModelAndView("jsonView", "json", result);
  }
  
  /**
   * Ajax栋座列表
   * 
   * @param id
   * @return
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "ajax/residenceBuildingList.controller")
  public ModelAndView residenceBuildingList(@RequestParam("id") int id,
      final @RequestParam("prefix") String prefix,
      final @RequestParam("period") String period) {
    
    List<ResidenceBuildingEntity> buildingList = residenceBuildingDao.select(
        "findResidenceBuildingListByResidenceId", new UniqueIdObject(id));
    if (!CollectionUtils.isEmpty(buildingList)) {
      CollectionUtils.filter(buildingList, new Predicate() {
        public boolean evaluate(Object object) {
          
          ResidenceBuildingEntity obj = (ResidenceBuildingEntity) object;
          
          if (!(obj.getCodeBegin() != null && obj.getCodeBegin().equals(
              obj.getCodeEnd()))) {
            return false;
          }
          
          if (!StringUtils.isEmpty(prefix)) {
            if (!prefix.trim().equalsIgnoreCase(obj.getPrefix())) {
              return false;
            }
          }
          
          if (!StringUtils.isEmpty(period)) {
            if (!period.trim().equalsIgnoreCase(obj.getPeriod())) {
              return false;
            }
          }
          
          return true;
        }
      });
      Collections.sort(buildingList, new Comparator<ResidenceBuildingEntity>() {
        
        public int compare(ResidenceBuildingEntity o1,
            ResidenceBuildingEntity o2) {
          logger.debug(o1.getPrefix() + " " + o2.getPrefix());
          if (StringUtils.isEmpty(o1.getPrefix().trim())) {
            if (StringUtils.isEmpty(o2.getPrefix().trim())) {
              
              if (o1.getCodeType() == 1 && o2.getCodeType() == 1) {
                return Integer.parseInt(o1.getCodeBegin())
                    - Integer.parseInt(o2.getCodeBegin());
              }
              
              if (o1.getCodeType() == 2 && o2.getCodeType() == 2) {
                return o1.getCodeBegin().compareToIgnoreCase(o2.getCodeBegin());
              }
              return 0;
              
            } else {
              return -1;
            }
          }
          
          if (o1.getPrefix().toLowerCase().trim()
              .equals(o2.getPrefix().toLowerCase().trim())) {
            if (o1.getCodeType() == 1 && o2.getCodeType() == 1) {
              return Integer.parseInt(o1.getCodeBegin())
                  - Integer.parseInt(o2.getCodeBegin());
            }
            
            if (o1.getCodeType() == 2 && o2.getCodeType() == 2) {
              return o1.getCodeBegin().compareToIgnoreCase(o2.getCodeBegin());
            }
            return 0;
          } else {
            return o1.getPrefix().toLowerCase().trim()
                .compareToIgnoreCase(o2.getPrefix().trim());
          }
          
        }
      });
      
    }
    
    return new ModelAndView("jsonView", "json", buildingList);
    
  }
  
  @RequestMapping(value = "ajax/residenceBuildingDelete.controller")
  public ModelAndView residenceBuildingDelete(@RequestParam("id") String ids) {
    
    if (!StringUtils.isEmpty(ids)) {
      String[] arr = ids.split(",");
      for (String item : arr) {
        int id = Integer.parseInt(item);
        try {
          residenceBuildingDao.delete("deleteResidencBulding", id);
        } catch (Exception e) {
          logger.error("delete building error", e);
          if (e.toString().toLowerCase().contains("duplicate")) {
            residenceBuildingDao.deletePhysical(
                "deleteResidencBuldingPhysical", id);
          }
        }
      }
    }
    return new ModelAndView("jsonView", "json", new AjaxResultBean());
  }
  
  @RequestMapping(value = "residenceBuildingCell.controller")
  public String residenceBuildingCellAdd(Model model,
      @RequestParam("buildingId") int id) {
    
    ResidenceBuildingEntity buildingEntity = (ResidenceBuildingEntity) residenceBuildingDao
        .load("loadResidenceBuildingById", id);
    model.addAttribute("buildingEntity", buildingEntity);
    return "residenceBuildingCell";
    
  }
  
  /**
   * 
   * @param buildingId
   * @param floorBegin
   * @param floorEnd
   * @param codeType
   * @param begin
   * @param end
   * @param codeLimit
   * @param floor
   * @param stair
   * @param houseHold
   * @return
   */
  @RequestMapping(value = "ajax/residenceCellAdd.controller")
  public ModelAndView residenceBuildingCellAdd(
      @RequestParam("buildingId") int buildingId,
      @RequestParam("floorBegin") int floorBegin,
      @RequestParam("floorEnd") int floorEnd,
      @RequestParam("codeType") int codeType,
      @RequestParam("begin") String begin, @RequestParam("end") String end,
      @RequestParam("codeLimit") int codeLimit) {
    
    if (buildingId <= 0 || (codeType != 1 && codeType != 2) || floorBegin <= 0
        || floorEnd <= 0) {
      new ModelAndView("jsonView", "json",
          new AjaxResultBean("填写不完整，请仔细检查输入项!"));
    }
    
    if (floorBegin > floorEnd) {
      new ModelAndView("jsonView", "json", new AjaxResultBean(
          "单元前缀，开始项不能大于结束项!"));
    }
    
    if (!checkCodeRange(codeType, begin, end)) {
      new ModelAndView("jsonView", "json",
          new AjaxResultBean("单元号，开始项不能大于结束项!"));
    }
    
    List<Integer> resultIdList = new ArrayList<Integer>();
    // 数字
    if (codeType == 1) {
      
      int beginIndex = Integer.parseInt(begin);
      int endIndex = Integer.parseInt(end);
      
      for (int i = floorBegin; i <= floorEnd; i++) {
        for (int j = beginIndex; j <= endIndex; j++) {
          if (codeLimit == 1 && j % 2 != 0 || codeLimit == 2 && j % 2 == 0) {
            continue;
          }
          ResidenceCellEntity entity = new ResidenceCellEntity();
          entity.setBuildingId(buildingId);
          entity.setCodeType(codeType);
          entity.setCodeBegin(j < 10 ? "0" + String.valueOf(j) : String
              .valueOf(j));
          entity.setCodeEnd(j < 10 ? "0" + String.valueOf(j) : String
              .valueOf(j));
          entity.setFloorBegin(i);
          entity.setFloorEnd(i);
          try {
            resultIdList.add(residenceCellDao.add("addResidenceCell", entity));
          } catch (Exception e) {
            logger.error("addCell-number error", e);
          }
        }
      }
      
    }
    
    // 字母
    if (codeType == 2) {
      
      for (int i = floorBegin; i <= floorEnd; i++) {
        char cBegin = begin.toUpperCase().charAt(0);
        char cEnd = end.toUpperCase().charAt(0);
        
        while (cEnd >= 'A' && cEnd <= 'Z' && cEnd >= cBegin) {
          ResidenceCellEntity entity = new ResidenceCellEntity();
          entity.setBuildingId(buildingId);
          entity.setCodeType(codeType);
          entity.setFloorBegin(i);
          entity.setFloorEnd(i);
          entity.setCodeBegin(String.valueOf(cEnd));
          entity.setCodeEnd(String.valueOf(cEnd));
          try {
            resultIdList.add(residenceCellDao.add("addResidenceCell", entity));
          } catch (Exception e) {
            logger.error("addCell-alpha error", e);
          }
          cEnd = (char) ((int) cEnd - 1);
        }
      }
    }
    AjaxResultBean result = new AjaxResultBean("添加成功");
    result.setBizData(StringUtils.join(resultIdList, ","));
    return new ModelAndView("jsonView", "json", result);
  }
  
  @RequestMapping(value = "residenceBuildingEdit.controller")
  public String residenceBuildingEdit(Model model,
      @RequestParam("residenceId") int residenceId,
      @RequestParam("buildingId") int buildingId) {
    
    ResidenceEntity entity = (ResidenceEntity) residenceDao.load(
        "loadResidence", residenceId);
    
    ResidenceBuildingEntity buildingEntity = (ResidenceBuildingEntity) residenceBuildingDao
        .load("loadResidenceBuildingById", buildingId);
    if (entity != null) {
      model.addAttribute("entity", entity);
    }
    
    if (buildingEntity != null) {
      model.addAttribute("building", buildingEntity);
    }
    
    return "/residence/building/residenceBuildingEdit";
  }
  
  @RequestMapping(value = "residenceBuildingUpdateSubmit.controller")
  public String residenceBuildingUpdateSubmit(Model model,
      @RequestParam("residenceId") int residenceId,
      @RequestParam("buildingId") int buildingId,
      @RequestParam("period") String period,
      @RequestParam("stair") String stair,
      @RequestParam("houseHold") String houseHold,
      @RequestParam("buildingType") String buildingType) {
    
    ResidenceBuildingEntity entity = new ResidenceBuildingEntity();
    entity.setId(buildingId);
    entity.setResidenceId(residenceId);
    entity.setBuildingType(buildingType);
    entity
        .setPeriod((period != null && period.equalsIgnoreCase("请选择")) ? StringUtils.EMPTY
            : period);
    entity.setStair(stair == null ? 0 : Integer.parseInt(stair));
    entity.setHouseHold(houseHold == null ? 0 : Integer.parseInt(houseHold));
    
    residenceBuildingDao.update("updateResidenceBuildingBaseInfo", entity);
    return "redirect:/residence/" + residenceId;
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "addBuildingResult.controller")
  public String addBuildingResultList(Model model,
      @RequestParam("residenceId") int residenceId,
      @RequestParam("idList") String idList) {
    
    ResidenceEntity entity = (ResidenceEntity) residenceDao.load(
        "loadResidence", residenceId);
    
    List<ResidenceBuildingEntity> buildingList = null;
    if (!StringUtils.isEmpty(idList)) {
      Map<String,String> map = new HashMap<String,String>();
      map.put("ids", idList);
      buildingList = residenceBuildingDao.select(
          "findResidenceBuildingListByIds", map);
    }
    
    if (entity != null) {
      model.addAttribute("entity", entity);
    }
    if (!CollectionUtils.isEmpty(buildingList)) {
      model.addAttribute("list", buildingList);
    }
    
    return "/residence/building/addBuildingResult";
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "residenceCellList.controller")
  public String cellList(Model model,
      @RequestParam("residenceId") int residenceId,
      @RequestParam("buildingId") int buildingId) {
    
    ResidenceEntity entity = (ResidenceEntity) residenceDao.load(
        "loadResidence", residenceId);
    ResidenceBuildingEntity buildingEntity = (ResidenceBuildingEntity) residenceBuildingDao
        .load("loadResidenceBuildingById", buildingId);
    List<ResidenceCellEntity> list = residenceCellDao.select(
        "findResidenceCellListByBuildingId", new UniqueIdObject(buildingId));
    
    if (entity != null) {
      model.addAttribute("entity", entity);
    }
    
    if (buildingEntity != null) {
      model.addAttribute("building", buildingEntity);
    }
    
    if (!CollectionUtils.isEmpty(list)) {
      model.addAttribute("list", list);
    }
    
    return "/residence/cell/cellList";
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "addResidenceCell.controller")
  public String residenceCellListView(Model model,
      @RequestParam("residenceId") int residenceId,
      @RequestParam("buildingId") int buildingId) {
    
    ResidenceEntity entity = (ResidenceEntity) residenceDao.load(
        "loadResidence", residenceId);
    ResidenceBuildingEntity buildingEntity = (ResidenceBuildingEntity) residenceBuildingDao
        .load("loadResidenceBuildingById", buildingId);
    List<ResidenceCellEntity> list = residenceCellDao.select(
        "findResidenceCellListByBuildingId", new UniqueIdObject(buildingId));
    
    if (entity != null) {
      model.addAttribute("entity", entity);
    }
    
    if (buildingEntity != null) {
      model.addAttribute("building", buildingEntity);
    }
    
    if (!CollectionUtils.isEmpty(list)) {
      model.addAttribute("list", list);
    }
    
    return "/residence/cell/addCell";
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "ajax/residenceCellList.controller")
  public ModelAndView residenceCellList(@RequestParam("id") int id) {
    
    List<ResidenceCellEntity> list = residenceCellDao.select(
        "findResidenceCellListByBuildingId", new UniqueIdObject(id));
    if (!CollectionUtils.isEmpty(list)) {
      CollectionUtils.filter(list, new Predicate() {
        public boolean evaluate(Object object) {
          ResidenceCellEntity obj = (ResidenceCellEntity) object;
          if (obj.getCodeBegin() != null
              && obj.getCodeBegin().equals(obj.getCodeEnd())) {
            return true;
          }
          return false;
        }
      });
    }
    
    return new ModelAndView("jsonView", "json", list);
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/addResidenceCellResult.controller")
  public String addCellResult(Model model,
      @RequestParam("residenceId") int residenceId,
      @RequestParam("buildingId") int buildingId,
      @RequestParam("idList") String idList) {
    
    ResidenceEntity entity = (ResidenceEntity) residenceDao.load(
        "loadResidence", residenceId);
    
    ResidenceBuildingEntity buildingEntity = (ResidenceBuildingEntity) residenceBuildingDao
        .load("loadResidenceBuildingById", buildingId);
    
    if (entity != null) {
      model.addAttribute("entity", entity);
    }
    
    if (buildingEntity != null) {
      model.addAttribute("building", buildingEntity);
    }
    
    List<ResidenceCellEntity> cellList = null;
    if (!StringUtils.isEmpty(idList)) {
      Map<String,String> map = new HashMap<String,String>();
      map.put("ids", idList);
      cellList = residenceCellDao.select("findResidenceCellListByIds", map);
    }
    
    if (!CollectionUtils.isEmpty(cellList)) {
      model.addAttribute("list", cellList);
    }
    
    return "/residence/cell/addCellResult";
  }
  
  @RequestMapping(value = "/ajax/residenceCellDelete.controller")
  public ModelAndView residenceCellDelete(@RequestParam("id") String idList) {
    String[] ids = idList.split(",");
    if (!ArrayUtils.isEmpty(ids)) {
      for (String id : ids) {
        try {
          residenceCellDao.delete("deleteResidenceCell", Integer.parseInt(id));
        } catch (Exception e) {
          logger.error("delete building error", e);
          if (e.toString().toLowerCase().contains("duplicate")) {
            residenceCellDao.deletePhysical("deleteResidenceCellPhysical",
                Integer.parseInt(id));
          }
        }
      }
    }
    
    return new ModelAndView("jsonView", "json", new AjaxResultBean());
  }
  
  /**
   * 
   * @param resourceProvider
   */
  public void setResourceProvider(ResourceProvider resourceProvider) {
    this.resourceProvider = resourceProvider;
  }
  
  /**
   * 
   * @param codeType
   * @param begin
   * @param end
   * @return
   */
  private boolean checkCodeRange(int codeType, String begin, String end) {
    if (codeType == 1 && Integer.parseInt(begin) > Integer.parseInt(end)) {
      return true;
    }
    if (codeType == 2
        && ((begin != null && begin.length() == 1) && (end != null && end
            .length() == 1)) && begin.toString().compareToIgnoreCase(end) <= 0) {
      return true;
    }
    return false;
  }
  
  @RequestMapping(value = "residencePinYin.controller")
  public String residencePinYin(Model model) {
    return "/residence/residencePinYin";
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "generatePinyinName.controller")
  public ModelAndView residencePinyin(Model model) {
    
    List<ResidenceEntity> list = residenceDao.select("findResidenceList", null);
    
    if (CollectionUtils.isNotEmpty(list)) {
      PinyinTranslator pyTr = new PinyinTranslator();
      for (ResidenceEntity r : list) {
        if (StringUtils.isNotBlank(r.getResidenceName())) {
          Map<String,Object> para = new HashMap<String,Object>();
          para.put("pinyinName", pyTr.toPinyin(r.getResidenceName()));
          para.put("firstPinyin", pyTr.toFirstPinyin(r.getResidenceName()));
          para.put("id", r.getResidenceId());
          residenceDao.update("updatePinyin", para);
        }
      }
    }
    
    return new ModelAndView(new RedirectView("residencePinyin.controller"));
    
  }
  
  @SuppressWarnings({"unchecked", "rawtypes"})
  @RequestMapping(value = "addOrUpdateResidence.controller")
  public ModelAndView addOrUpdateResidence(Model model,
      ResidenceExtEntity residence, String plateId) {
    
    if (residence == null) {
      residence = new ResidenceExtEntity();
    }
    String regionId = residence.getRegionId();
    residence.setRegionId(plateId);
    
    int residenceId = 0;
    if (residence.getId() > 0) {
      // update
      residenceId = residence.getId();
      
      int hasPower = residencePrivilegeService.hasPower(
          HouseMartContext.getCurrentUserId(), ActionType.Update,
          Integer.valueOf(regionId), Integer.valueOf(residence.getRegionId()));
      
      ResidenceEntity residencePre = (ResidenceEntity) residenceDao.load(
          "loadResidenceById", residenceId);
      Map<String,Object[]> map = new HashMap<String,Object[]>();
      
      if (residence.getResidenceName() != null
              && !StringUtils.equals(residencePre.getResidenceName(),
                  residence.getResidenceName())) {
    	  
          // check exist
          Map<String,Object> param = new HashMap<String,Object>();
          param.put("residenceNameEqual", residence.getResidenceName().trim());
          List<ResidenceEntity> exists = residenceDao.select("findResidenceList",
              param);
          if (CollectionUtils.isNotEmpty(exists)) {

            Map<String,Object> param2 = new HashMap<String,Object>();
            param2.put("residenceId", residenceId);
            List<ResidenceMonthDataEntity> monthDatas2 = residenceMonthDataDao.select(
                "findByResidence", param2);
            ResidenceMonthDataEntity monthData2 = new ResidenceMonthDataEntity();
            if (monthDatas2 != null && monthDatas2.size() > 0) {
              monthData2 = monthDatas2.get(0);
            }
            
            model.addAttribute("entity", residence);
            model.addAttribute("plateId", plateId);
            model.addAttribute("regionId", regionId);
            model.addAttribute("monthData", monthData2);

            
            HouseMartContext.setSysMsg("小区已存在");
            return new ModelAndView("/residence/residenceDetailForEdit");
          }
    	  
          map.put(ResidenceAuditContentKeys.RESIDENCENAME,
                new Object[] {residencePre.getResidenceName(), residence.getResidenceName()});
      }

      if (residence.getAliasName() != null) {
    	  String[] names = StringUtils.split(StringUtils.replace(residence.getAliasName(),"，", ","), ',');
    	  for (int i = 0; i < names.length; i++) {
    		  names[i] = StringUtils.trim(names[i]);
    	  }
    	  residence.setAliasName(StringUtils.join(names, ','));
    	  
          if (!StringUtils.equals(residencePre.getAliasName(),
                      residence.getAliasName())) {
                map.put(ResidenceAuditContentKeys.ALIASNAME,
                    new Object[] {residencePre.getAliasName(), residence.getAliasName()});
              }
      }

      if (residence.getRegionId() != null
              && !StringUtils.equals(residencePre.getRegionId(),
                  residence.getRegionId())) {
            map.put(ResidenceAuditContentKeys.REGIONID,
                new Object[] {residencePre.getRegionId(), residence.getRegionId()});
          }

      if (residence.getAddress() != null
          && !StringUtils.equals(residencePre.getAddress(),
              residence.getAddress())) {
        map.put(ResidenceAuditContentKeys.ADDRESS,
            new Object[] {residencePre.getAddress(), residence.getAddress()});
      }
      
      if (residence.getGreenRate() != null
          && !StringUtils.equals(residencePre.getGreenRate(),
              residence.getGreenRate())) {
        map.put(ResidenceAuditContentKeys.GREEN_RATE, new Object[] {
            residencePre.getGreenRate(), residence.getGreenRate()});
      }
      
      if (residence.getHeadCount() != null
          && !StringUtils.equals(residencePre.getHeadCount(),
              residence.getHeadCount())) {
        map.put(ResidenceAuditContentKeys.HEAD_COUNT, new Object[] {
            residencePre.getHeadCount(), residence.getHeadCount()});
      }
      
      if (residence.getDeveloper() != null
          && !StringUtils.equals(residencePre.getDeveloper(),
              residence.getDeveloper())) {
        map.put(
            ResidenceAuditContentKeys.DEVELOPER,
            new Object[] {residencePre.getDeveloper(), residence.getDeveloper()});
      }
      
      if (residence.getFinishedTime() != null
          && !StringUtils.equals(residencePre.getFinishedTime(),
              residence.getFinishedTime())) {
        map.put(ResidenceAuditContentKeys.FINISHED_TIME, new Object[] {
            residencePre.getFinishedTime(), residence.getFinishedTime()});
      }
      
      if (residence.getParking() != null
          && !StringUtils.equals(residencePre.getParking(),
              residence.getParking())) {
        map.put(ResidenceAuditContentKeys.PARKING,
            new Object[] {residencePre.getParking(), residence.getParking()});
      }
      
      if (residence.getPropertyFee() != null
          && !StringUtils.equals(residencePre.getPropertyFee(),
              residence.getPropertyFee())) {
        map.put(ResidenceAuditContentKeys.PROPERTY_FEE, new Object[] {
            residencePre.getPropertyFee(), residence.getPropertyFee()});
      }
      
      if (residence.getPropertyType() != null
          && !StringUtils.equals(residencePre.getPropertyType(),
              residence.getPropertyType())) {
        map.put(ResidenceAuditContentKeys.PROPERTY_TYPE, new Object[] {
            residencePre.getPropertyType(), residence.getPropertyType()});
      }
      
      if (residence.getVolumeRate() != null
          && !StringUtils.equals(residencePre.getVolumeRate(),
              residence.getVolumeRate())) {
        map.put(ResidenceAuditContentKeys.VOLUME_RATE, new Object[] {
            residencePre.getVolumeRate(), residence.getVolumeRate()});
      }
      
      Map<String,Object> param = new HashMap<String,Object>();
      param.put("residenceId", residenceId);
      List<ResidenceMonthDataEntity> monthDatas = residenceMonthDataDao.select(
          "findByResidence", param);
      ResidenceMonthDataEntity monthDataPre = null;
      if (monthDatas != null && monthDatas.size() > 0) {
        monthDataPre = monthDatas.get(0);
      }
      
      if (monthDataPre == null
          || (residence.getAnnualPriceIncrement() != monthDataPre
              .getAnnualPriceIncrement())) {
        map.put(ResidenceAuditContentKeys.ANNUAL_PRICE_INCREMENT, new Object[] {
            monthDataPre == null ? 0 : monthDataPre.getAnnualPriceIncrement(),
            residence.getAnnualPriceIncrement()});
      }
      
      if (monthDataPre == null
          || (residence.getAnnualTurnoverPercent() != monthDataPre
              .getAnnualTurnoverPercent())) {
        map.put(
            ResidenceAuditContentKeys.ANNUAL_TURNOVER_PERCENT,
            new Object[] {
                monthDataPre == null ? 0 : monthDataPre
                    .getAnnualTurnoverPercent(),
                residence.getAnnualTurnoverPercent()});
      }
      
      if (monthDataPre == null
          || (residence.getRentRevenue() != monthDataPre.getRentRevenue())) {
        map.put(
            ResidenceAuditContentKeys.RENT_REVENUE,
            new Object[] {
                monthDataPre == null ? 0 : monthDataPre.getRentRevenue(),
                residence.getRentRevenue()});
      }
      
      if (map.size() > 0) {
        int id = auditService.requestResidenceStatusAndContentUpdate(
            residenceId, map);
        
        if (hasPower == PriviledgeResultType.Direct.getValue()) {
          
           // Add name change history
          if (residence.getResidenceName() != null
                  && !StringUtils.equals(residencePre.getResidenceName(),
                      residence.getResidenceName())) {
        	  ResidenceNameHistoryEntity nameHistoryEntity=new ResidenceNameHistoryEntity();
        	  nameHistoryEntity.setResidenceId(residenceId);
        	  
        	  String decodedResidenceName = null;
        	  try {
        		  decodedResidenceName = URLDecoder.decode(residencePre.getResidenceName().trim(), "UTF-8");
        	      } catch (UnsupportedEncodingException e) {
        	        logger.error(e.getMessage(), e);
        	      }
 
        	  nameHistoryEntity.setOldName(decodedResidenceName);
        	  nameHistoryEntity.setAddTime(new Date());
        	  nameHistoryEntity.setAccountId(HouseMartContext.getCurrentUserId());
        	  //type=1 means residencenamehistory
        	  nameHistoryEntity.setType(1);
              residenceNameHistoryDao.add("addResidenceNameHistory", nameHistoryEntity);
          }

          // Add aliasName change history
         if (residence.getAliasName() != null && residencePre.getAliasName() != null
                 && !StringUtils.equals(residencePre.getAliasName(),
                     residence.getAliasName())) {
       	  ResidenceNameHistoryEntity nameHistoryEntity=new ResidenceNameHistoryEntity();
       	  nameHistoryEntity.setResidenceId(residenceId);
       	  
       	  String decodedAliasName = null;
       	  try {
       		decodedAliasName = URLDecoder.decode(residencePre.getAliasName().trim(), "UTF-8");
       	      } catch (UnsupportedEncodingException e) {
       	        logger.error(e.getMessage(), e);
       	      }

	       	  nameHistoryEntity.setOldName(decodedAliasName);
	       	  nameHistoryEntity.setAddTime(new Date());
	       	  nameHistoryEntity.setAccountId(HouseMartContext.getCurrentUserId());
        	  //type=2 means aliasnamehistory
	       	  nameHistoryEntity.setType(2);
	       	  residenceNameHistoryDao.add("addResidenceNameHistory", nameHistoryEntity);
         }

         auditService.approveResidenceStatusAndContent(residenceId, id);
       }
      }
      HouseMartContext.setSysMsg("保存成功，等待审核");
    } else {
      // add
      
      // check exist
      Map<String,Object> param = new HashMap<String,Object>();
      param.put("residenceNameEqual", residence.getResidenceName().trim());
      List<ResidenceEntity> exists = residenceDao.select("findResidenceList",
          param);
      if (CollectionUtils.isNotEmpty(exists)) {
        Map map = new HashMap();
        map.put("entity", residence);
        map.put("regionId", regionId);
        map.put("plateId", plateId);
        HouseMartContext.setSysMsg("小区已存在");
        return new ModelAndView("/residence/residenceDetailForNew", map);
      }
      
      PinyinTranslator pyTr = new PinyinTranslator();
      residence.setPinyinName(pyTr.toPinyin(residence.getResidenceName()));
      residence
          .setFirstPinyin(pyTr.toFirstPinyin(residence.getResidenceName()));
      residence.setCreator(HouseMartContext.getCurrentUserId());
      residenceId = residenceDao.add("addResidence", residence);
      
      Calendar c = Calendar.getInstance();
      ResidenceMonthDataEntity monthData = new ResidenceMonthDataEntity();
      monthData.setResidenceId(residenceId);
      monthData.setResidenceName(residence.getResidenceName());
      monthData.setAnnualPriceIncrement(residence.getAnnualPriceIncrement());
      monthData.setAnnualTurnoverPercent(residence.getAnnualTurnoverPercent());
      monthData.setRentRevenue(residence.getRentRevenue());
      monthData.setMonth(c.get(Calendar.MONTH));
      monthData.setYear(c.get(Calendar.YEAR));
      monthData.setAddTime(new Date());
      monthData.setUpdateTime(new Date());
      residenceMonthDataDao.add("addResidenceMonthData", monthData);
      
      GooglePlaceBaseEntity entity = new GooglePlaceBaseEntity();
      entity.setResidenceId(residenceId);
      entity.setIsMain(false);
      entity.setKeyword("");
      entity.setName("手动添加经纬度(无名称)");
      entity.setAddress("");
      entity.setLat("31.1877830");
      entity.setLng("121.5355610");
      googlePlaceDao.add("addResidencePosition", entity);
      
      int id = auditService.requestAddNewResidence(residenceId);
      
      int hasPower = residencePrivilegeService.hasPower(
          HouseMartContext.getCurrentUserId(), ActionType.Add,
          Integer.valueOf(regionId), Integer.valueOf(residence.getRegionId()));
      if (hasPower == PriviledgeResultType.Direct.getValue()) {
        auditService.approveNewResidence(id);
      }
    }
    
    HouseMartContext.setSysMsg("保存成功，等待审核");
    
    if (HouseMartContext.isManager()
        || HouseMartContext.getCurrentUserId() == 0) {
      return new ModelAndView(new RedirectView("/residence/" + residenceId));
    } else {
      return new ModelAndView(new RedirectView("/residenceMap.controller?id="
          + residenceId));
    }
    
  }
  
  @RequestMapping(value = "updateResidenceShowStatus.controller")
  public ModelAndView updateShowStatus(Model model, int id, int forceShow,
      int zombie) {
    
    if (id <= 0 || forceShow < 0 || zombie < 0) {
      HouseMartContext.setSysMsg("参数有误");
      return new ModelAndView(new RedirectView(
          "/editResidence.controller?residenceId=" + id));
    }
    
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("id", id);
    param.put("forceShow", forceShow);
    param.put("zombie", zombie);
    residenceDao.update("updateShowStatus", param);
    
    HouseMartContext.setSysMsg("保存成功");
    return new ModelAndView(new RedirectView(
        "/editResidence.controller?residenceId=" + id));
  }
  
  @RequestMapping(value = "updateResidenceLockStatus.controller")
  public ModelAndView updateLockStatus(Model model, int id, int lockBasicInfo,
      int lockMap, int lockPic) {
    
    if (id <= 0 || lockBasicInfo < 0 || lockMap < 0 || lockPic < 0) {
      HouseMartContext.setSysMsg("参数有误");
      return new ModelAndView(new RedirectView(
          "/editResidence.controller?residenceId=" + id));
    }
    
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("id", id);
    param.put("lockBasicInfo", lockBasicInfo);
    param.put("lockMap", lockMap);
    param.put("lockPic", lockPic);
    residenceDao.update("updateLockStatus", param);
    
    HouseMartContext.setSysMsg("保存成功");
    return new ModelAndView(new RedirectView(
        "/editResidence.controller?residenceId=" + id));
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "editResidence.controller")
  public String editResidence(Model model, Integer residenceId, String plateId,
      String regionId) {
    
    ResidenceEntity residence = new ResidenceEntity();
    if (residenceId != null) {
      // update
      residence = (ResidenceEntity) residenceDao.load("loadResidenceById",
          residenceId);
      plateId = residence.getRegionId();
      regionId = residence.getParentRegionId();
      
      Map<String,Object> param = new HashMap<String,Object>();
      param.put("residenceId", residenceId);
      List<ResidenceMonthDataEntity> monthDatas = residenceMonthDataDao.select(
          "findByResidence", param);
      ResidenceMonthDataEntity monthData = new ResidenceMonthDataEntity();
      if (monthDatas != null && monthDatas.size() > 0) {
        monthData = monthDatas.get(0);
      }
      
      model.addAttribute("entity", residence);
      model.addAttribute("plateId", plateId);
      model.addAttribute("regionId", regionId);
      model.addAttribute("monthData", monthData);
      
      return "/residence/residenceDetailForEdit";
      
    } else {
      // add
      model.addAttribute("regionId", regionId);
      model.addAttribute("plateId", plateId);
      return "/residence/residenceDetailForNew";
    }
    
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "myResidence.controller")
  public String myResidence(Model model) {
    
    List<ResidenceEntity> list = null;
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("creator", String.valueOf(HouseMartContext.getCurrentUserId()));
    list = residenceDao.select("findResidenceList", param);
    
    if (!CollectionUtils.isEmpty(list)) {
      for (ResidenceEntity item : list) {
        List<GooglePlaceBaseEntity> tempList = googlePlaceDao.select(
            "findSetPositionResidenceById",
            new UniqueIdObject(item.getResidenceId()));
        if (tempList != null && tempList.size() >= 1) {
          GooglePlaceBaseEntity target = tempList.get(0);
          if (!StringUtils.isEmpty(target.getLat())
              && !StringUtils.isEmpty(target.getLng())) {
            item.setPositionSet(true);
          }
        }
      }
    }
    
    model.addAttribute("list", list);
    
    return "/residence/myResidenceList";
  }
  
}
