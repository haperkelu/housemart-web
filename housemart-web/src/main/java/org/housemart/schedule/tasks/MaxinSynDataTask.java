/** 
 * @Title: MaxinSynDataTask.java
 * @Package org.housemart.schedule.tasks
 * @Description: TODO
 * @author Pie.Li
 * @date 2013-5-3 下午4:26:02
 * @version V1.0 
 */
package org.housemart.schedule.tasks;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import org.apache.commons.collections.CollectionUtils;
import org.housemart.dao.entities.HouseContactEntity;
import org.housemart.dao.entities.HouseEntity.StatusEnum;
import org.housemart.dao.entities.HouseExtEntity;
import org.housemart.dao.entities.HouseProcessEntity;
import org.housemart.dao.entities.HouseRentEntity;
import org.housemart.dao.entities.HouseSaleEntity;
import org.housemart.dao.entities.MaxinRawEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.web.context.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: MaxinSynDataTask
 * @Description: TODO
 * @date 2013-5-3 下午4:26:02
 * 
 */
public class MaxinSynDataTask extends TimerTask {

	private static final Logger logger = LoggerFactory
			.getLogger("CommonLogger");

	GenericDao houseDao = SpringContextHolder.getBean("houseDao");

	GenericDao houseContactDao = SpringContextHolder.getBean("houseContactDao");

	GenericDao maxinRawDao = SpringContextHolder.getBean("maxinRawDao");

	GenericDao houseSaleDao = SpringContextHolder.getBean("houseSaleDao");

	GenericDao houseRentDao = SpringContextHolder.getBean("houseRentDao");

	GenericDao houseProcessDao = SpringContextHolder.getBean("houseProcessDao");

	@SuppressWarnings("unchecked")
	@Override
	public void run() {		
		
		List<MaxinRawEntity> list = maxinRawDao
				.select("findLatestRecord", null);
		if (CollectionUtils.isNotEmpty(list)) {
			for (MaxinRawEntity item : list) {
				HouseExtEntity entity = new HouseExtEntity();

				try {
					// residence id
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", item.getResidence());
					List<Integer> ids = maxinRawDao.select(
							"findResidenceIdByNameDirect", map);
					int residenceId = 0;
					if (CollectionUtils.isNotEmpty(ids)) {
						residenceId = ids.get(0);
					} else {
						ids = maxinRawDao.select("findResidenceIdByName", map);
						if (CollectionUtils.isNotEmpty(ids)) {
							residenceId = ids.get(0);
						}
					}
					if (residenceId == 0) {
						logger.info("could not match residence:" + item.getId());
						continue;
					}
					entity.setResidenceId(residenceId);
					entity.setStatus(StatusEnum.Valid.value);
					entity.setAddTime(Calendar.getInstance().getTime());
					entity.setUpdateTime(Calendar.getInstance().getTime());
					entity.setSourceType(2); //客服团队 
					entity.setCreator(-1); // dump

					// area
					try {
						entity.setPropertyArea(Float.parseFloat(item.getHouseArea()));
					} catch (Exception e1) {
						logger.error(e1.getMessage(), e1);
					}
					houseDao.add("addHouse", entity);

					// contact
					HouseContactEntity contact = entity.generateHouseContactEntity();
					contact.setCommitter(-1);
					contact.setHouseId(entity.getId());
					contact.setAddTime(Calendar.getInstance().getTime());
					contact.setUpdateTime(Calendar.getInstance().getTime());
					contact.setName(item.getClientName());
					contact.setMobile(item.getPhone1());
					contact.setMobile2(item.getPhone2());
					contact.setMobile3(item.getPhone3());
					contact.setMemo(item.getContactMemo());
					contact.setStatus(1);
					try {
						houseContactDao.add("addHouseContactNew", contact);
					} catch (Exception e1) {
						logger.error(e1.getMessage(), e1);
					}

					// sale
					HouseSaleEntity sale = entity.generateHouseSaleEntity();
					sale.setAddTime(Calendar.getInstance().getTime());
					sale.setUpdateTime(Calendar.getInstance().getTime());
					// sale.setAvgPrice(item.getAvg() == null? null:
					// Float.parseFloat(item.getAvg()));
					try {
						if (item.getSalePrice() != null
								&& item.getSalePrice().contains("万")) {
							sale.setPrice(10000 * Float.parseFloat(item
									.getSalePrice().substring(0,
											item.getSalePrice().length() - 1)));
						}
						sale.setHouseId(entity.getId());
						houseSaleDao.add("addHouseSale", sale);
						
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}


					// rent
					HouseRentEntity rent = entity.generateHouseRentEntity();
					rent.setHouseId(entity.getId());
					rent.setAddTime(Calendar.getInstance().getTime());
					rent.setUpdateTime(Calendar.getInstance().getTime());
					rent.setMemo(item.getRentPrice());
					try {
						houseRentDao.add("addHouseRent", rent);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}

					// process
					HouseProcessEntity process = new HouseProcessEntity();
					process.setHouseId(entity.getId());
					process.setMemo(item.getMemo());
					process.setAddTime(Calendar.getInstance().getTime());
					try {
						houseProcessDao.add("addHouseProcess", process);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}

					// call back
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("id", item.getId());
					map2.put("houseId", entity.getId());
					maxinRawDao.update("updateRawDataByHouseId", map2);
				} catch (Exception e) {
					logger.error(item.getId() + ":" + e.getMessage(), e);
				}

			}

		}
	}

}
