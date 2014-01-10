/**
 * Created on 2013-7-7
 * 
 */
package org.housemart.pic.common.crawler;

import org.apache.commons.lang.StringUtils;

public class CrawlUtils {
  
  public static String removeC2A0Space(String str) {
    if (str == null) return null;
    else return str.replace(' ', ' ');
  }
  
  public static String normalizeString(String text) {
    String normalized = null;
    if (StringUtils.isNotBlank(text)) {
      normalized = text.replaceAll("\n", "").replaceAll("\\s*", "").replaceAll("―", "").replaceAll("—", "").trim();
    }
    return normalized;
  }
}
