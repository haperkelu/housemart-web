/**
 * Created on 2013-8-25
 * 
 */
package org.housemart.message;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.housemart.dao.entities.HouseInteractionNoticeEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.web.context.SpringContextHolder;

public class HouseInteractionNotifier {
  
  private GenericDao<HouseInteractionNoticeEntity> houseInteractionNoticeDao = SpringContextHolder
      .getBean("houseInteractionNoticeDao");
  
  public void changeToWithoutInteraction(List<Integer> houseIds) {
    // todo::
    if (CollectionUtils.isNotEmpty(houseIds)) {
      
      for (Integer id : houseIds) {
        if (id == null) {
          continue;
        }
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("houseId", id);
        houseInteractionNoticeDao.update("updateToRead", param);
        
        HouseInteractionNoticeEntity entity = new HouseInteractionNoticeEntity();
        entity.setHouseId(id);
        entity.setStatus(HouseInteractionNoticeEntity.StatusEnum.Unread.value);
        entity
            .setType(HouseInteractionNoticeEntity.TypeEnum.ToWithoutInteraction.value);
        entity.setAddTime(new Date());
        entity.setUpdateTime(new Date());
        houseInteractionNoticeDao.add("addHouseInteractionNotice", entity);
      }
    }
  }
  
  public void changeToWithInteraction(List<Integer> houseIds) {
    // todo::
    if (CollectionUtils.isNotEmpty(houseIds)) {
      
      for (Integer id : houseIds) {
        if (id == null) {
          continue;
        }
        
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("houseId", id);
        houseInteractionNoticeDao.update("updateToRead", param);
        
        HouseInteractionNoticeEntity entity = new HouseInteractionNoticeEntity();
        entity.setHouseId(id);
        entity.setStatus(HouseInteractionNoticeEntity.StatusEnum.Unread.value);
        entity
            .setType(HouseInteractionNoticeEntity.TypeEnum.ToWithInteraction.value);
        entity.setAddTime(new Date());
        entity.setUpdateTime(new Date());
        houseInteractionNoticeDao.add("addHouseInteractionNotice", entity);
      }
    }
  }
  
}
