/**
 * Created on 2014-3-7
 * 
 */
package org.housemart.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.housemart.crawl.ziprealty.model.ZrHouse;
import org.housemart.crawl.ziprealty.service.DetailPageCrawler;
import org.housemart.crawl.ziprealty.service.ListPageCrawler;
import org.housemart.service.ZrHouseService;
import org.housemart.web.context.SpringContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ZrHouseController extends BaseController {
  
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
}
