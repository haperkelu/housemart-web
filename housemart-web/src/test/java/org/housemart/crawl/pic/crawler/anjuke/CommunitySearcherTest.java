/**
 * Created on 2013-4-20
 * 
 */
package org.housemart.crawl.pic.crawler.anjuke;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.crawl.pic.model.anjuke.CommunityData;
import org.housemart.crawl.pic.service.ResidencePicCrawlerService;
import org.housemart.web.context.SpringContextHolder;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/beans/spring*.xml"})
public class CommunitySearcherTest {
  
//  @Test
  public void searchTest() throws Exception {
    ResidencePicCrawlerService residencePicCrawlerService = SpringContextHolder.getBean("residencePicCrawlerService");
    //243947
    System.out.println(residencePicCrawlerService.crawlAndSaveResidencePic(16130,243947));
    
  }
  
//  @Test
  public void jsonTest() throws JsonParseException, JsonMappingException, IOException{
    String str = "{\"community\":{\"block\":\"静安新城\",\"id\":588273,\"name\":\"大上海国际花园\",\"address\":\"闵行区漕宝路1555号\",\"property_number\":1374,\"rent_number\":0,\"lat\":\"31.165364456899\",\"lng\":\"121.38369289618\",\"cityid\":\"11\",\"introduction\":\"大上海国际花园位于漕宝路1555号。是闵行区第一批商品房，与万科城市花园是同一期的，绿色清晰，交通方便，业余生活丰富多彩，是理想的休闲居住地。居住环境安静，交通方便快捷，生活配套一应俱全，方便居民的日常生活。项目以实用、舒适、分区合理为整体规划原则，户型选择丰富，居住实用率高。\",\"supporting_facilities\":\"羽毛球场 餐厅 美容美发 洗衣店 网球场 歌舞厅 儿童游乐场 停车场 健身房\",\"type\":\"公寓\",\"developer\":\"深圳金田实业集团\",\"completion_time\":\"2014-02\",\"property_company\":\"上海鸿浩物业管理有限公司\",\"sale_price\":\"31743\",\"sale_price_change\":\"0.00922011\",\"anjuke_trend\":\"http://static.anjuke.com/chart/pricetrendcomm/W0QQtZ1QQcodeZ588273QQwZ690QQhZ250QQdateZ20140228QQticksZ1QQextZ.pngQQacZ000100110113QQmthZ5??radius=0\",\"default_photo\":\"http://pic1.ajkimg.com/m/d01412584c9a5a0ce01017b70d4f3bce/420x420.jpg\",\"photos\":[\"http://pic1.ajkimg.com/m/d7f29d8caea570d345c088321f32d37b/420x420.jpg\",\"http://pic1.ajkimg.com/m/c1eb5250f5485b2eac0afaaea2cd94c5/420x420.jpg\",\"http://pic1.ajkimg.com/m/40fd832748f9a947f211df738d5c3c1d/420x420.jpg\",\"http://pic1.ajkimg.com/m/d01412584c9a5a0ce01017b70d4f3bce/420x420.jpg\",\"http://pic1.ajkimg.com/m/ec21dc2806ca755ae5de8b071f4f2935/420x420.jpg\",\"http://pic1.ajkimg.com/m/aae00ab168ccc576fe29392e83c7447c/420x420.jpg\",\"http://pic1.ajkimg.com/m/2d4be9c80f20965005a996c250631d3e/420x420.jpg\",\"http://pic1.ajkimg.com/m/62412f8a83b1804874052fad554ba08c/420x420.jpg\",\"http://pic1.ajkimg.com/m/c7bc304f650de0b569465756df7b12cb/420x420.jpg\",\"http://pic1.ajkimg.com/m/4a1128354c797cb7a84af360fdf15416/420x420.jpg\"],\"today_price\":\"32201\",\"last_week_price\":\"31811\",\"current_month_price\":\"31626\",\"price\":\"32201\"},\"status\":\"ok\",\"requestTime\":0.066834926605225}";
    ObjectMapper om = new ObjectMapper();
    om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false); 
    CommunityData data = om.readValue(str, CommunityData.class);
    System.out.println(data.toString());
  }
}
