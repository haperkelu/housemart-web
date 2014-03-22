/**
 * Created on 2014-3-7
 * 
 */
package org.housemart.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.housemart.crawl.ziprealty.ZrHouseConverter;
import org.housemart.crawl.ziprealty.model.ZrHouse;
import org.housemart.crawl.ziprealty.model.ZrHouseBean;
import org.housemart.crawl.ziprealty.service.DetailPageCrawler;
import org.housemart.crawl.ziprealty.service.ListPageCrawler;
import org.housemart.service.ZrHouseService;
import org.housemart.web.context.SpringContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ZrHouseController extends BaseController {
  
  private ZrHouseConverter converter = new ZrHouseConverter();
  
  @RequestMapping(value = "zr/crawlerPage.controller")
  public String externalHousePicConsole() {
    return "zr/crawlerPage";
  }
  
  @RequestMapping(value = "zr/crawlTask.controller")
  public String crawlTask() {
    List<String> urls = SpringContextHolder.getBean("zipRealtyHouseList");
    for (String url : urls) {
      try {
        crawlUrl(url);
      } catch (Exception e) {
        logger.error("Crawl Task Error!", e);
      }
    }
    return "zr/crawlerPage";
  }
  
  @RequestMapping(value = "zr/crawlHouse.controller")
  public String crawlUrl(String url) throws JsonGenerationException, JsonMappingException, IOException {
    ZrHouseService zrHouseService = SpringContextHolder.getBean("zrHouseService");
    int maxPage = 1;
    int currentPage = 1;
    while (currentPage <= maxPage) {
      ListPageCrawler listCrawler = new ListPageCrawler();
      Map<String,Object> result = listCrawler.crawlDetailUrls(url + "?pageNum=" + currentPage);
      List<String> urls = (List<String>) result.get("urls");
      if (currentPage == 1) {
        maxPage = Integer.valueOf(result.get("maxPage").toString());
      }
      
      if (urls != null) {
        DetailPageCrawler detailCrawler = new DetailPageCrawler();
        for (String u : urls) {
          ZrHouse h = detailCrawler.crawlDetailInfo(u);
          zrHouseService.addZrHouse(h);
        }
      }
      currentPage++;
    }
    
    return "zr/crawlerPage";
  }
  
  @RequestMapping(value = "zr/findHouse.controller")
  public String find(Model model, Integer page, String mlsLike, String neighborhoodLike, String blockLike)
      throws JsonParseException, JsonMappingException, IOException {
    ZrHouseService zrHouseService = SpringContextHolder.getBean("zrHouseService");
    if (page == null || page < 0) {
      page = 0;
    }
    int pageSize = 20;
    Map<String,Object> para = new HashMap<String,Object>();
    if (StringUtils.isNotBlank(mlsLike)) {
      para.put("mlsLike", mlsLike);
    }
    if (StringUtils.isNotBlank(neighborhoodLike)) {
      para.put("neighborhoodLike", neighborhoodLike);
    }
    if (StringUtils.isNotBlank(blockLike)) {
      para.put("blockLike", blockLike);
    }
    para.put("skip", page * pageSize);
    para.put("count", pageSize);
    List<ZrHouse> houses = zrHouseService.findHouse(para);
    int total = zrHouseService.countHouse(para);
    List<ZrHouseBean> result = converter.toBean(houses);
    if (result != null) {
      for (ZrHouseBean bean : result) {
        if (bean.getQnPics() != null && bean.getQnPics().size() > 5) {
          bean.setQnPics(bean.getQnPics().subList(0, 4));
        }
      }
    }
    model.addAttribute("houses", result);
    model.addAttribute("total", total);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("page", page);
    model.addAllAttributes(para);
    
    return "zr/houseList";
  }
}
