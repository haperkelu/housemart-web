/**
 * Created on 2014-3-5
 * 
 */
package org.housemart.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.crawl.ziprealty.model.ZrHouse;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.web.context.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZrHouseService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @SuppressWarnings("rawtypes")
  private GenericDao zrHouseDao = SpringContextHolder.getBean("zrHouseDao");
  
  public int addZrHouse(ZrHouse zrHouse) {
    try {
      if (zrHouse != null) {
        if (StringUtils.isNotBlank(zrHouse.getMls().trim())) {
          Map<String,Object> para = new HashMap<String,Object>();
          para.put("mls", zrHouse.getMls().trim());
          List<ZrHouse> zrHouses = (List<ZrHouse>) zrHouseDao.select("queryByMls", para);
          if (CollectionUtils.isNotEmpty(zrHouses)) {
            zrHouseDao.update("updateZrHouse", zrHouse);
            return zrHouses.get(0).getId();
          } else {
            return zrHouseDao.add("addZrHouse", zrHouse);
          }
        }
      }
    } catch (Exception e) {
      logger.error("Crawl zrhouse error.", e);
    }
    return -1;
  }
}
