/**
 * Created on 2013-4-5
 * 
 */
package org.housemart.pic.service.anjuke.search;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.housemart.pic.model.anjuke.CommunityGet;
import org.housemart.pic.service.anjuke.AnJuKeCrawlConstants;
import org.housemart.pic.service.anjuke.crack.DecryptSig;
import org.housemart.util.RequestUtils;
import org.housemart.web.context.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CommunitySearcher implements _IEntitySearchable<CommunityGet> {
  Log log = LogFactory.getLog(this.getClass());
  
  @Override
  public CommunityGet search(String communityId) throws Exception {
    
    _ICrawlable<CommunityGet> communityCrawler = SpringContextHolder
        .getBean("communityCrawler");
    String url = generateURL(communityId);
    return communityCrawler.crawl(url);
    
  }
  
  @Override
  public String searchJsonResult(String communityId) throws Exception {
    
    _ICrawlable<CommunityGet> communityCrawler = SpringContextHolder
        .getBean("communityCrawler");
    String url = generateURL(communityId);
    return communityCrawler.crawlReturnJson(url);
    
  }
  
  public String generateURL(String communityId) throws Exception {
    
    String ret = null;
    String url = MessageFormat.format(
        AnJuKeCrawlConstants.SEARCH_URL_PATTERN_COMMUNITY, communityId);
    log.info("AnJuKeResidence::url - " + url);
    Map<String,String> param = RequestUtils.URLRequest(url);
    Map<String,String> significativeParam = DecryptSig
        .obtainSignificativeParam(param);
    ret = url + "&sig=" + DecryptSig.generateSig(significativeParam);
    
    return ret;
  }
  
}
