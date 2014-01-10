/**
 * Created on 2012-9-19
 * 
 * Copyright (c) Comprice, Inc. 2010. All rights reserved.
 */
package org.housemart.dao;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections.map.HashedMap;
import org.housemart.dao.entities.HouseEntity;
import org.housemart.dao.entities.HouseProcessEntity;
import org.housemart.dao.entities.HouseRentEntity;
import org.housemart.dao.entities.HouseSaleEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/beans/spring*.xml"})
public class HouseModuleDaoTester {
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao houseDao;
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao houseRentDao;
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao houseSaleDao;
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao houseProcessDao;

	@Test
	public void addHouseTest() {
		HouseEntity house = new HouseEntity();
		house.setName("test");
		System.out.println("Insert Test. House id : " + houseDao.add("addHouse", house));
	}

	@Test
	public void loadHouseTest() {
		Object house = houseDao.load("loadHouse", 1);
		System.out.println("Load House Test. House name " + ((HouseEntity) house).getName());;

		house = houseDao.load("loadHouseExt", 1);
		System.out.println("Load House Ext Test. House name " + ((HouseEntity) house).getName());;
	}

	@Test
	public void updateHouseTest() {
		HouseEntity house = new HouseEntity();
		house.setId(1);
		house.setName("abc");
		houseDao.update("updateHouse", house);
	}

	@Test
	public void addHouseRentTest() {
		HouseRentEntity rent = new HouseRentEntity();
		rent.setMemo("abc");
		System.out.println("Insert HouseRent Test. HouseRent id : " + houseRentDao.add("addHouseRent", rent));
	}

	@Test
	public void addHouseSaleTest() {
		HouseSaleEntity sale = new HouseSaleEntity();
		sale.setMemo("abc");
		System.out.println("Insert HouseSale Test. HouseSale id : " + houseSaleDao.add("addHouseSale", sale));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void findLatestHouseProcessListTest() {
		@SuppressWarnings("rawtypes")
		Map para = new HashedMap();
		para.put("houseIds", "1,2,3,4,5,6,7");
		List<HouseProcessEntity> houseProcessList = (List<HouseProcessEntity>) houseProcessDao.select(
				"findLatestHouseProcessList", para);
		System.out.println("Find Latest House ProcessList Test. Size : " + houseProcessList.size());
	}
}
