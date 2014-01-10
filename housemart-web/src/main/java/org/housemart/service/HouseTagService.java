/**
 * Created on 2012-11-10
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.housemart.framework.dao.generic.GenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("houseTagService")
public class HouseTagService {
	protected static final Logger logger = LoggerFactory.getLogger(HouseTagService.class);
	@SuppressWarnings({"rawtypes"})
	@Autowired
	private GenericDao houseDao;
	@SuppressWarnings("rawtypes")
	@Autowired
	private GenericDao houseTagDao;

	@SuppressWarnings({"rawtypes", "unchecked"})
	public List getTagOptionsByCategory(String categoryName) {
		List tagOptions = null;
		Map para = new HashMap();
		if (!StringUtils.isEmpty(categoryName))
			para.put("categoryName", categoryName);
		tagOptions = houseTagDao.select("findHouseTagList", para);
		return tagOptions;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public List getHouseTagsIn(String tagsList) {
		List houseTags = null;
		Map para = new HashMap();
		if (!StringUtils.isEmpty(tagsList)) {
			para.put("ids", tagsList);
			houseTags = houseTagDao.select("findHouseTagListByIds", para);
		}
		return houseTags;
	}

}
