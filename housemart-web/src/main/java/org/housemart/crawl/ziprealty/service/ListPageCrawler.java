/**
 * Created on 2014-2-27
 * 
 */
package org.housemart.crawl.ziprealty.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.housemart.crawl.common.crawler.HouseMartScraper;
import org.housemart.crawl.common.crawler._ACrawler;
import org.housemart.crawl.ziprealty._IZrConstants;
import org.springframework.util.CollectionUtils;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.variables.NodeVariable;

public class ListPageCrawler extends _ACrawler {
  
  public ListPageCrawler() {
    super(_IZrConstants.CRAWLER_CIG_HOUSE_LIST, "");
  }
  
  public Map<String,Object> crawlDetailUrls(String listUrl) {
    Map<String,Object> result = new HashMap<String,Object>();
    
    List<String> detUrls = new ArrayList<String>();
    int maxSize = 0;
    final List<NodeVariable> houseUrls = new ArrayList<NodeVariable>();
    final List<NodeVariable> maxSizeUrls = new ArrayList<NodeVariable>();
    
    Scraper scraper = new HouseMartScraper(config, workDir);
    scraper.getContext().put("url", listUrl);
    scraper.getContext().put("houseUrls", houseUrls);
    scraper.getContext().put("maxSizeUrls", maxSizeUrls);
    // scraper.getHttpClientManager().setHttpProxy("localhost", 8888);
    scraper.setDebug(false);
    scraper.execute();
    
    if (!CollectionUtils.isEmpty(houseUrls)) {
      for (NodeVariable lv : houseUrls) {
        detUrls.add(_IZrConstants.HOST_ZIP_REALTY + lv.toString());
      }
    }
    if (!CollectionUtils.isEmpty(maxSizeUrls)) {
      try {
        maxSize = Integer.valueOf(StringUtils.substringAfterLast(maxSizeUrls.get(0).toString(), "of").trim());
      } catch (Exception e) {
        log.error("Parse max page error!");
      }
    }
    result.put("urls", detUrls);
    result.put("maxPage", maxSize);
    return result;
  }
}
