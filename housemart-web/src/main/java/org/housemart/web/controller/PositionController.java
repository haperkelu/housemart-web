package org.housemart.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.housemart.dao.entities.AreaPositionEntity;
import org.housemart.dao.entities.RegionEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.util.JsonUtils;
import org.housemart.web.beans.AjaxResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class PositionController {

	@Autowired
	GenericDao areaPositionDao;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao regionDao;

	@RequestMapping(value = "citySet.controller")
	public String citySet(Model model, @RequestParam("cityId") int cityId) throws JsonGenerationException, JsonMappingException, IOException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityId", cityId);
		map.put("type", 1);
		map.put("positionId", cityId);
		
		List<AreaPositionEntity> list = areaPositionDao.select("findPositionList", map);
		
		if(list != null && list.size() > 0){
			AreaPositionEntity entity = (AreaPositionEntity)list.get(0);
			model.addAttribute("points", JsonUtils.writeValue(list));
		} else {
			AreaPositionEntity entity = new AreaPositionEntity();
			entity.setCityId(1);
			entity.setType(1);
			entity.setPositionId(cityId);
			entity.setLat("31.197162");
			entity.setLng("121.440599");		
			areaPositionDao.add("addPosition", entity);
			list.add(entity);
		}
		model.addAttribute("points", JsonUtils.writeValue(list));
		model.addAttribute("cityid", cityId);
		
		
		return "position/city"; 
	}
	
	@RequestMapping(value = "regionSet.controller")
	public String  getList(Model model, @RequestParam("cityId") int cityId, @RequestParam("regionId")  int regionId) throws JsonGenerationException, JsonMappingException, IOException{
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityId", cityId);
		map.put("type", 2);
		map.put("positionId", regionId);

		List<AreaPositionEntity> list = areaPositionDao.select("findPositionList", map);
		if(list != null && list.size() > 0){
			AreaPositionEntity entity = (AreaPositionEntity)list.get(0);
			model.addAttribute("points", JsonUtils.writeValue(list));
		} else {
			AreaPositionEntity entity = new AreaPositionEntity();
			entity.setCityId(1);
			entity.setType(2);
			entity.setPositionId(regionId);
			entity.setLat("31.197162");
			entity.setLng("121.440599");		
			areaPositionDao.add("addPosition", entity);
			list.add(entity);
		}
		model.addAttribute("points", JsonUtils.writeValue(list));
		
		//model.addAttribute("list", list);
		model.addAttribute("regionid", regionId);
		model.addAttribute("cityid", cityId);
		
		return "position/region"; 
	}
	
	@RequestMapping(value = "plateSet.controller")
	public String  getPlateList(Model model, @RequestParam("cityId") int cityId, @RequestParam("regionId")  int regionId, @RequestParam("plateId")  int plateId) throws JsonGenerationException, JsonMappingException, IOException{
		
		int type = 3;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityId", cityId);
		map.put("type", type);
		if(plateId == 0){
			Map<Object, Object> mapParent = new HashMap<Object, Object>();
			mapParent.put("parentId", regionId);
			List<RegionEntity> plateList = regionDao.select("findPlateListByRegionId", mapParent);
			if(CollectionUtils.isNotEmpty(plateList)){
				map.put("positionId", plateList.get(0).getId());
			}
		} else {
			map.put("positionId", plateId);
		}
		
		List<AreaPositionEntity> list = areaPositionDao.select("findPositionList", map);
		if(list != null && list.size() > 0){
			AreaPositionEntity entity = (AreaPositionEntity)list.get(0);
			model.addAttribute("points", JsonUtils.writeValue(list));
		} else {
			AreaPositionEntity entity = new AreaPositionEntity();
			entity.setCityId(1);
			entity.setType(type);
			entity.setPositionId(plateId);
			entity.setLat("31.197162");
			entity.setLng("121.440599");		
			areaPositionDao.add("addPosition", entity);
			list.add(entity);
		}
		model.addAttribute("points", JsonUtils.writeValue(list));
		
		//model.addAttribute("list", list);
		model.addAttribute("cityId", cityId);
		model.addAttribute("regionId", regionId);
		model.addAttribute("plateId", plateId);
		
		return "position/plate"; 
	}
	
	@RequestMapping(value = "regionSet/confirmPositionMannally.controller")
	public ModelAndView  getList(@RequestParam("id")  int id, 
			@RequestParam("lat")  String lat, @RequestParam("lng")  String lng,
			@RequestParam("cityId")  int cityId, @RequestParam("type")  int type,
			@RequestParam("positionId")  int positionId
			) {
		
		AjaxResultBean result = new AjaxResultBean();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityId", cityId);
		map.put("type", type);
		map.put("positionId", positionId);
		
		
		AreaPositionEntity entity = new AreaPositionEntity();
		entity.setCityId(cityId);
		entity.setType(type);
		entity.setPositionId(positionId);
		entity.setLat(lat);
		entity.setLng(lng);		
		areaPositionDao.deletePhysical("deletePosition", entity);
		areaPositionDao.add("addPosition", entity);

		return new ModelAndView("jsonView", "json", result);
		
	}
}
