package org.housemart.message;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/beans/spring*.xml"})
public class HouseInteractionNotiferTester {
  @Autowired
  HouseInteractionNotifier notifier;
  
  @Test
  public void interactionNotice() {
    
    List<Integer> toWithData = new ArrayList<Integer>();
    toWithData.add(1);
    toWithData.add(2);
    toWithData.add(3);
    toWithData.add(4);
    
    notifier.changeToWithInteraction(toWithData);
    
    List<Integer> toWithpitData = new ArrayList<Integer>();
    toWithpitData.add(5);
    toWithpitData.add(6);
    toWithpitData.add(7);
    toWithpitData.add(8);
    
    notifier.changeToWithInteraction(toWithpitData);
  }
}
