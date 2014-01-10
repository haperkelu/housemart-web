package com.pieli.web.test;


import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.schedule.tasks.MaxinSynDataTask;
import org.housemart.service.AuthenticationService;
import org.housemart.service.priviledge.PrivilegeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/beans/spring*.xml" })
public class IndexerTester {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private PrivilegeService privilegeService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	GenericDao residenceDao;
	
	@Test
	public void testRights(){
		
		 /**
		 List<PrivilegeEntity> list = privilegeService.getPriviledgeList(1);
		 System.out.println(list.size());
		 for(PrivilegeEntity item: list){
			 System.out.println(item.getName() + " " + item.getResource());
		 }
		 **/
//		List<String> listKeys = new ArrayList<String>();
//		listKeys.add("house::HasKey");
//		privilegeService.hasPower(2, ActionType.Update, ResourceType.House, GranularityType.Field, 5555, listKeys);
	}
	
	@Test
	public void sendEmail() {
		new MaxinSynDataTask().run();
		//TestService service = new TestService();
		//service.testTransaction();
//		List<ResidenceEntity> list = residenceDao.selectByType("findResidenceList", null);
//		for(ResidenceEntity item: list){
//			System.out.println(item.getRegionName());
//		}
		/**
		List<ResidenceEntity> list = residenceDao.select("findResidenceList");
		Assert.assertTrue(list.size() >= 1);
		for(ResidenceEntity item: list){
			System.out.println(item.getResidenceName());
		}
		**/
		/**
		ResidenceBuildingEntity entity = new ResidenceBuildingEntity();
		entity.setResidenceId(2103);
		entity.setPrefix("G");
		entity.setSuffix("dong");
		entity.setCodeBegin("1");
		entity.setCodeEnd("50");
		residenceDao.add("addResidenceBuilding", entity);
		**/
		/**
		HouseMartToken token = authenticationService.login("admin", "!@qwaszx");
		System.out.println(authenticationService.isLoggin(token.getTokenStr()));
		**/
	}
	
	
}
