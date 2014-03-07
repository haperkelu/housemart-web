/**
 * Created on 2014-2-28
 * 
 */
package org.housemart.crawl.ziprealty;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/beans/spring*.xml"})
public class ListPageCrawlerTest {
  
  @Test
  public void houseUrlsTest() throws Exception {
    ZrHouseService zrHouseService = SpringContextHolder.getBean("zrHouseService");
    String url = "http://www.ziprealty.com/homes-for-sale/list/oc/by-city/Irvine,CA/detailed";
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
  }
  
  @Test
  public void houseDetailTest() throws JsonGenerationException, JsonMappingException, IOException {
    DetailPageCrawler detailCrawler = new DetailPageCrawler();
    ZrHouse h = detailCrawler
        .crawlDetailInfo("http://www.ziprealty.com/property/2705-LADRILLO-AISLE-IRVINE-CA-92606/8014022/detail");
    System.out.println(h.getBlock());
    System.out.println(h.getTitle());
    System.out.println(h.getQnPics());
  }
  
}
