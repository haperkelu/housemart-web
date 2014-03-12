/**
 * Created on 2014-3-10
 * 
 */
package org.housemart.crawl.ziprealty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.crawl.ziprealty.model.ZrHouse;
import org.housemart.crawl.ziprealty.model.ZrHouseBean;

public class ZrHouseConverter {
  ObjectMapper om = new ObjectMapper();
  
  public ZrHouseBean toBean(ZrHouse zrHouse) throws JsonParseException, JsonMappingException, IOException {
    ZrHouseBean zrHouseBean = new ZrHouseBean();
    zrHouseBean.setAddTime(zrHouse.getAddTime());
    zrHouseBean.setBlock(zrHouse.getBlock());
    zrHouseBean.setHouseDetail(zrHouse.getHouseDetail());
    zrHouseBean.setId(zrHouse.getId());
    zrHouseBean.setLink(zrHouse.getLink());
    zrHouseBean.setListed(zrHouse.getListed());
    zrHouseBean.setMls(zrHouse.getMls());
    zrHouseBean.setNeighborhood(zrHouse.getNeighborhood());
    zrHouseBean.setPrice(zrHouse.getPrice());
    if (zrHouse.getQnPics() != null) {
      zrHouseBean.setQnPics(om.readValue(zrHouse.getQnPics(), List.class));
    }
    zrHouseBean.setStatus(zrHouse.getStatus());
    zrHouseBean.setTitle(zrHouse.getTitle());
    zrHouseBean.setUpdateTime(zrHouse.getUpdateTime());
    zrHouseBean.setZrPics(zrHouse.getZrPics());
    return zrHouseBean;
  }
  
  public List<ZrHouseBean> toBean(List<ZrHouse> zrHouses) throws JsonParseException, JsonMappingException, IOException {
    List<ZrHouseBean> result = new ArrayList<ZrHouseBean>();
    if (zrHouses != null) {
      for (ZrHouse zrHouse : zrHouses) {
        ZrHouseBean zrHouseBean = toBean(zrHouse);
        result.add(zrHouseBean);
      }
    }
    return result;
  }
}
