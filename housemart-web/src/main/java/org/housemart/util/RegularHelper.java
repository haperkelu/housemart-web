package org.housemart.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pqin
 */
public class RegularHelper {
  public static String REG_MOBILE = "[1][3,5,8]+\\d{9}";
  public static String REG_PHONE = "\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d(3)-\\d(8)";
  public static String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
  public static String REG_AGE = "[1,2,3,4,5][1-9]";
  public static String REG_EXPYEAR = "[1-9]+年";
  public static String REG_MD5_32 = "\\w{32}";
  public static String REG_HEX_16 = "[0-9a-fA-F]{16}";
  public static String REG_HEX = "[0-9a-fA-F]+";
  public static String REG_NUMBER = "[0-9]+";
  
  public static String getFirstValue(String source, String regularStr) {
    String result = null;
    List<String> vals = getValues(source, regularStr);
    if (vals != null && vals.size() > 0) {
      result = vals.get(0);
    }
    return result;
  }
  
  public static List<String> getValues(String source, String regularStr) {
    Pattern p = Pattern.compile(regularStr);
    Matcher m = p.matcher(source);
    List<String> values = new ArrayList<String>();
    
    while (m.find()) {
      values.add(m.group());
    }
    
    return values;
  }
  
  public static boolean match(String source, String regularStr) {
    Pattern p = Pattern.compile(regularStr);
    Matcher matcher = p.matcher(source);
    return matcher.matches();
  }
  
  public static void main(String[] args) {
    String REG_MOBILE = "[1][3,5]+\\d{8}";
    String REG_PHONE = "\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d(3)-\\d(8)";
    String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    
    String source1 = "电话:1358574879111111";
    String source2 = "电话:0331-13585748791";
    String source3 = "qinpeng@pinwoo.com,asdfsf@dsfsdf.com";
    String source4 = "年龄:29";
    String source5 = "工作经验:5年";
    String source6 = "sdfasdfsdfsfsdf 68EEE882C1BDF882C779FCE5F3493E37 xxx";
    String source7 = "6B4A970AA5D9A7EACB71CD5660BD4356 1A9D337E84A8A3BC0343478188635A11";
    String source8 = "6B4A970AA5D9A7EACB71CD5660BD4356";
    System.out.println(RegularHelper.getValues(source1, REG_MOBILE));
    System.out.println(RegularHelper.getValues(source2, REG_PHONE));
    System.out.println(RegularHelper.getValues(source3, REG_EMAIL));
    System.out.println(RegularHelper.getValues(source4, REG_AGE));
    System.out.println(RegularHelper.getValues(source5, REG_EXPYEAR));
    System.out.println(RegularHelper.getValues(source6, REG_MD5_32));
    System.out.println(RegularHelper.getValues(source7, REG_HEX_16));
    System.out.println(RegularHelper.match(source8, REG_HEX_16));
    System.out.println(RegularHelper.match(source7, REG_HEX_16));
  }
}
