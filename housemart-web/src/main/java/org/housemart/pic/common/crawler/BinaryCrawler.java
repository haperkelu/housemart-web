/**
 * Created on 2013-4-26
 * 
 */
package org.housemart.pic.common.crawler;

public class BinaryCrawler extends _ACrawler {
  
  public BinaryCrawler(String workDir) {
    super("org/housemart/pic/common/crawler/BinaryData.xml", workDir);
  }
  
  public String crawl(String url, String filePath) throws Exception {
    
    String finalPath = workDir + "/" + filePath;
    getScraper().getContext().put("url", url);
    getScraper().getContext().put("filePath", finalPath);
    getScraper().setDebug(true);
    getScraper().execute();
    return finalPath;
    
  }
}
