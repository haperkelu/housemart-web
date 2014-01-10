/**
 * Created on 2013-10-20
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.web.beans.HousePicOrderByTypeParameterBean;
import org.housemart.web.beans.HousePicOrderByTypeParameterListBean;
import org.housemart.web.beans.HousePicOrderByTypeParameterBean.HousePicOrderByTypeItem;
import org.junit.Test;

public class HousePicSortTest {
  
  @Test
  public void paramTest() throws JsonGenerationException, JsonMappingException,
      IOException {
    HousePicOrderByTypeParameterBean para1 = new HousePicOrderByTypeParameterBean();
    para1.setType(0);
    para1
        .setItems(new ArrayList<HousePicOrderByTypeParameterBean.HousePicOrderByTypeItem>() {
          private static final long serialVersionUID = 1L;
          {
            add(new HousePicOrderByTypeItem() {
              {
                setId(2081);
                setOrder(1);
              }
            });
            add(new HousePicOrderByTypeItem() {
              {
                setId(2080);
                setOrder(2);
              }
            });
            add(new HousePicOrderByTypeItem() {
              {
                setId(2079);
                setOrder(3);
              }
            });
          }
        });
    
    List<HousePicOrderByTypeParameterBean> params = new ArrayList<HousePicOrderByTypeParameterBean>();
    params.add(para1);
    
    HousePicOrderByTypeParameterListBean bean = new HousePicOrderByTypeParameterListBean();
    bean.setPics(params);
    
    ObjectMapper mapper = new ObjectMapper();
    System.out.println(mapper.writeValueAsString(bean));
  }
}
