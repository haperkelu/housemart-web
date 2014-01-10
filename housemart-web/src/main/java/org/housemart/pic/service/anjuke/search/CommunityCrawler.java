/**
 * Created on 2013-4-20
 * 
 */
package org.housemart.pic.service.anjuke.search;

import org.apache.commons.lang.StringUtils;
import org.housemart.pic.common.crawler.JsonCrawler;
import org.housemart.pic.model.anjuke.CommunityData;
import org.housemart.pic.model.anjuke.CommunityGet;

public class CommunityCrawler extends JsonCrawler implements
    _ICrawlable<CommunityGet> {
  
  public CommunityCrawler(String workDir) {
    super(workDir);
  }
  
  @Override
  public CommunityGet crawl(String url) throws Exception {
    
    CommunityGet community = null;
    String jsonResult = null;
    
    jsonResult = crawlReturnJson(url);
    
    if (StringUtils.isNotBlank(jsonResult)) {
      jsonResult = jsonResult.replaceAll("\r", "").replaceAll("\n", "");
      log.debug(jsonResult);
      CommunityData data = om.readValue(jsonResult, CommunityData.class);
      if (data != null) community = data.getCommunity();
    }
    
    return community;
  }
  
}
