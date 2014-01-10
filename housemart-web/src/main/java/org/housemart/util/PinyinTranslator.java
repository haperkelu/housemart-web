/**
 * Created on 2013-8-18
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinTranslator {
  
  public String toFirstPinyin(String chinese) {
    
    StringBuffer pybf = new StringBuffer();
    char[] arr = chinese.toCharArray();
    HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
    defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] > 128) {
        try {
          String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i],
              defaultFormat);
          if (_t != null) {
            pybf.append(_t[0].charAt(0));
          }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
          e.printStackTrace();
        }
      } else {
        pybf.append(arr[i]);
      }
    }
    return pybf.toString().replaceAll("\\W", "").trim();
    
  }
  
  public String toPinyin(String chinese) {
    
    StringBuffer pybf = new StringBuffer();
    char[] arr = chinese.toCharArray();
    HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
    defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] > 128) {
        try {
          String[] array = PinyinHelper.toHanyuPinyinStringArray(arr[i],
              defaultFormat);
          if (array != null) {
            pybf.append(array[0]);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        pybf.append(arr[i]);
      }
    }
    return pybf.toString();
    
  }
  
  public static void main(String[] args) {
    PinyinTranslator tr = new PinyinTranslator();
    System.out.println(tr.toFirstPinyin("拼音测试首字母,hi pinyin!"));
    System.out.println(tr.toPinyin("拼音测试首字母,hi pinyin!"));
  }
}
