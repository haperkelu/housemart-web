/**
 * Created on 2013-4-5
 * 
 */
package org.housemart.pic.common.crawler;

import java.net.HttpURLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.housemart.pic.common.Connection;
import org.housemart.pic.common.GetConnection;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.exception.HttpException;
import org.webharvest.exception.ScraperXQueryException;
import org.webharvest.runtime.Scraper;

public class HouseMartScraper extends Scraper {
  private Log log = LogFactory.getLog(this.getClass());
  private int coolDownUnit = 1000;
  private int tryTimes = 3;
  
  public HouseMartScraper(ScraperConfiguration configuration, String workingDir) {
    super(configuration, workingDir);
  }
  
  public HouseMartScraper(ScraperConfiguration configuration,
      String workingDir, int tryTimes) {
    this(configuration, workingDir);
    if (tryTimes > 0) this.tryTimes = tryTimes;
  }
  
  @Override
  public void execute() {
    int cd = tryTimes;
    boolean exception = true;
    String url = getContext().containsKey("url") ? (String) getContext().get(
        "url") : null;
    
    while (cd > 0 && exception) {
      try {
        log.debug("第 " + (tryTimes + 1 - cd) + " 次抓取 " + url);
        cd--;
        super.execute();
        exception = false;
      } catch (Exception e) {
        log.error("url " + url);
        
        if (e.getMessage() != null && (e instanceof HttpException)) {
          log.error(e.getMessage());
          log.error("网络不够畅通，即将启动探测程序...");
          
          detect(url);
        } else if (e.getMessage() == null) {
          log.error(e.getMessage());
          log.error("网络不够畅通，即将尝试下一次...");
        } else if (e instanceof ScraperXQueryException) {
          log.error(e.getMessage(), e);
          return;
        } else {
          throw new RuntimeException("抓取异常", e);
        }
      }
    }
  }
  
  public boolean detect(String url) {
    boolean goodTraffic = false;
    
    try {
      int status = -1;
      Connection hmConnection = null;
      HttpURLConnection hm = null;
      
      final int t = 10;
      int cd = t;
      while (cd > 0 && status != 200) {
        int coolDown = coolDownUnit * 3;
        Thread.sleep(coolDown);
        log.info("<探测中>第 " + (t + 1 - cd) + " 次尝试访问， 还剩下" + cd + "次尝试，频率是"
            + coolDown / 1000 + "秒间隔/次");
        
        try {
          hmConnection = new GetConnection(url, null);
          hm = hmConnection.getHttpConnection();
          hm.connect();
          status = hm.getResponseCode();
        } catch (Exception e) {}
        
        log.debug("<探测中>返回代码" + status);
        
        cd--;
      }
      
      if (status == 200) goodTraffic = true;
      
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    
    return goodTraffic;
  }
}
