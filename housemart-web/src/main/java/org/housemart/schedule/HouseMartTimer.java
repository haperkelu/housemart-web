package org.housemart.schedule;

import org.housemart.schedule.tasks.HouseInteractionNoticeDeletionTask;
import org.housemart.schedule.tasks.HouseInteractionTask;
import org.housemart.schedule.tasks.HouseOnboardDateTask;
import org.housemart.schedule.tasks.MaxinSynDataTask;
import org.housemart.schedule.tasks.NotificationTask;
import org.housemart.schedule.tasks.ResidenceCellCountTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HouseMartTimer {

  /**5分钟**/
  private final static int INTERVAL = 1000 * 60 * 5;
  private static final Logger logger = LoggerFactory
			.getLogger("CommonLogger");
  
  public HouseMartTimer(){
    
//     Timer timer = new Timer();
//     timer.schedule(new ResidenceCellCountTask(), 0, INTERVAL);
//     timer.schedule(new HouseInteractionTask(), 0, INTERVAL);
     
     //脉信
     final MaxinSynDataTask task = new MaxinSynDataTask();
     Thread t1 = new Thread(new Runnable() {
		
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			
			while(true){
			
				try {
					logger.info("MaxinSynDataTask begin to execute");
					task.run();
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}
				try {
					Thread.currentThread().sleep(INTERVAL);
				} catch (InterruptedException e) {
				}
				
			}			
		}		
	});  
        
    //房源交互权 
    final  HouseInteractionTask task2 = new HouseInteractionTask();
    Thread t2 = new Thread(new Runnable() {
		
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			
			while(true){
			
				try {
					logger.info("HouseInteractionTask begin to execute");
					task2.run();
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}
				try {
					Thread.currentThread().sleep(INTERVAL);
				} catch (InterruptedException e) {
				}
				
			}			
		}		
	});  
    
    //单元计算 
    final ResidenceCellCountTask task3 = new ResidenceCellCountTask();
    Thread t3 = new Thread(new Runnable() {
		
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			
			while(true){
			
				try {
					logger.info("ResidenceCellCountTask begin to execute");
					task3.run();
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}
				try {
					Thread.currentThread().sleep(INTERVAL);
				} catch (InterruptedException e) {
				}
				
			}			
		}		
	}); 
    
  //删除过期的交互通知 
    final HouseInteractionNoticeDeletionTask task4 = new HouseInteractionNoticeDeletionTask();
    Thread t4 = new Thread(new Runnable() {
      
      @SuppressWarnings("static-access")
      @Override
      public void run() {
        
        while (true) {
          
          try {
            logger.info("HouseInteractionNoticeDeletionTask begin to execute");
            task4.run();
          } catch (Exception e) {
            logger.error(e.getMessage(), e);
          }
          try {
            Thread.currentThread().sleep(INTERVAL* 12 *24);
          } catch (InterruptedException e) {}
          
        }
      }
    }); 
    
    //群推消息 
    final  NotificationTask task5 = new NotificationTask();
    Thread t5 = new Thread(new Runnable() {
		
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			
			while(true){
			
				try {
					logger.info("NotificationTask begin to execute");
					task5.run();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				try {
					Thread.currentThread().sleep(INTERVAL * 2);
				} catch (InterruptedException e) {
				}
				
			}			
		}		
	});  
    
    //刷新登盘日期
    final  HouseOnboardDateTask task6 = new HouseOnboardDateTask();
    Thread t6 = new Thread(new Runnable() {
      
      @SuppressWarnings("static-access")
      @Override
      public void run() {
        
        while (true) {
          
          try {
            logger.info("RefreshOnboardTimeTask begin to execute");
            task6.run();
            logger.info("RefreshOnboardTimeTask finish");
          } catch (Exception e) {
            logger.error(e.getMessage(), e);
          }
          try {
            Thread.currentThread().sleep(INTERVAL * 12 * 24);
          } catch (InterruptedException e) {}
          
        }
      }
    });  
    t1.start();   
    t2.start();  
    t3.start();
    t4.start();
    t5.start();
    t6.start();
  }
  
  
}
