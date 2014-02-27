/**
 * Created on 2013-4-20
 * 
 */
package org.housemart.crawl.pic.crawler.anjuke;

import org.housemart.crawl.pic.service.ResidencePicCrawlerService;
import org.housemart.web.context.SpringContextHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/beans/spring*.xml"})
public class CommunitySearcherTest {
  
//  @Test
  public void searchTest() throws Exception {
    ResidencePicCrawlerService residencePicCrawlerService = SpringContextHolder.getBean("residencePicCrawlerService");
    //243947
    System.out.println(residencePicCrawlerService.crawlAndSaveResidencePic(16130,243947));
    
  }
}
