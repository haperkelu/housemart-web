/**
 * Created on 2014-3-7
 * 
 */
package org.housemart.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.crawl.ziprealty.ZrHouseConverter;
import org.housemart.crawl.ziprealty.model.ZrHouse;
import org.housemart.crawl.ziprealty.model.ZrHouseBean;
import org.housemart.crawl.ziprealty.model.ZrHouseHmHouse;
import org.housemart.crawl.ziprealty.service.DetailPageCrawler;
import org.housemart.crawl.ziprealty.service.ListPageCrawler;
import org.housemart.dao.entities.AccountExtEntity;
import org.housemart.dao.entities.AccountResidenceEntity;
import org.housemart.dao.entities.HouseEntity;
import org.housemart.dao.entities.HouseEntity.StatusEnum;
import org.housemart.dao.entities.HouseExtEntity;
import org.housemart.dao.entities.HousePicEntity;
import org.housemart.dao.entities.HouseRentEntity;
import org.housemart.dao.entities.HouseSaleEntity;
import org.housemart.dao.entities.ResidenceExtEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.dao.generic.UniqueIdObject;
import org.housemart.service.AuditService;
import org.housemart.service.ZrHouseService;
import org.housemart.util.ZrHouseHelper;
import org.housemart.web.context.HouseMartContext;
import org.housemart.web.context.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ZrHouseController extends BaseController {

    private ZrHouseConverter converter = new ZrHouseConverter();
    @Autowired
    private AuditService auditService;
    @SuppressWarnings("rawtypes")
    @Autowired
    private GenericDao houseDao;
    @SuppressWarnings("rawtypes")
    @Autowired
    private GenericDao houseSaleDao;
    @SuppressWarnings("rawtypes")
    @Autowired
    private GenericDao housePicDao;
    @SuppressWarnings("rawtypes")
    @Autowired
    private GenericDao houseRentDao;
    @SuppressWarnings("rawtypes")
    @Autowired
    private GenericDao accountDao;
    @SuppressWarnings("rawtypes")
    @Autowired
    private GenericDao zrHouseHmHouseDao;
    @SuppressWarnings("rawtypes")
    private GenericDao accountResidenceDao = SpringContextHolder
	    .getBean("accountResidenceDao");
    @SuppressWarnings("rawtypes")
    @Autowired
    private GenericDao residenceDao;

    private ObjectMapper om = new ObjectMapper();

    private static final Set<Task> runningTasks = new HashSet<Task>();
    private static final List<Task> finishedTasks = new ArrayList<Task>();

    @RequestMapping(value = "zr/crawlerPage.controller")
    public String externalHousePicConsole(Model model) {

	model.addAttribute("runningTasks", runningTasks);
	model.addAttribute("finishedTasks", finishedTasks);
	return "zr/crawlerPage";
    }

    @RequestMapping(value = "zr/crawlTask.controller")
    public String crawlTask(Model model) {
	List<String> urls = SpringContextHolder.getBean("zipRealtyHouseList");
	for (String url : urls) {
	    try {
		crawlUrl(model, url);
	    } catch (Exception e) {
		logger.error("Crawl Task Error!", e);
	    }
	}
	model.addAttribute("runningTasks", runningTasks);
	model.addAttribute("finishedTasks", finishedTasks);
	return "zr/crawlerPage";
    }

    @RequestMapping(value = "zr/crawlHouse.controller")
    public String crawlUrl(Model model, final String url)
	    throws JsonGenerationException, JsonMappingException, IOException {
	final ZrHouseService zrHouseService = SpringContextHolder
		.getBean("zrHouseService");

	final Task task = new Task();
	task.setStart(new Date());
	task.setName(url);
	runningTasks.add(task);

	Thread thread = new Thread(new Runnable() {

	    @Override
	    public void run() {
		try {
		    int maxPage = 1;
		    int currentPage = 1;
		    while (currentPage <= maxPage) {
			ListPageCrawler listCrawler = new ListPageCrawler();
			Map<String, Object> result = listCrawler
				.crawlDetailUrls(url + "?pageNum="
					+ currentPage);
			List<String> urls = (List<String>) result.get("urls");
			if (currentPage == 1) {
			    maxPage = Integer.valueOf(result.get("maxPage")
				    .toString());
			}

			if (urls != null) {
			    DetailPageCrawler detailCrawler = new DetailPageCrawler();
			    for (String u : urls) {
				ZrHouse h = detailCrawler.crawlDetailInfo(u);
				zrHouseService.addZrHouse(h);
			    }
			}
			currentPage++;
		    }
		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
		} finally {
		    runningTasks.remove(task);
		    task.setEnd(new Date());
		    finishedTasks.add(task);
		}

	    }
	});
	thread.start();

	model.addAttribute("runningTasks", runningTasks);
	model.addAttribute("finishedTasks", finishedTasks);

	return "zr/crawlerPage";
    }

    @RequestMapping(value = "zr/findHouse.controller")
    public String find(Model model, Integer page, String mlsLike,
	    String neighborhoodLike, String blockLike)
	    throws JsonParseException, JsonMappingException, IOException {
	ZrHouseService zrHouseService = SpringContextHolder
		.getBean("zrHouseService");
	if (page == null || page < 0) {
	    page = 0;
	}
	int pageSize = 20;
	Map<String, Object> para = new HashMap<String, Object>();
	if (StringUtils.isNotBlank(mlsLike)) {
	    para.put("mlsLike", mlsLike);
	}
	if (StringUtils.isNotBlank(neighborhoodLike)) {
	    para.put("neighborhoodLike", neighborhoodLike);
	}
	if (StringUtils.isNotBlank(blockLike)) {
	    para.put("blockLike", blockLike);
	}
	para.put("skip", page * pageSize);
	para.put("count", pageSize);
	List<ZrHouse> houses = zrHouseService.findHouse(para);
	int total = zrHouseService.countHouse(para);
	List<ZrHouseBean> result = converter.toBean(houses);
	if (result != null) {
	    for (ZrHouseBean bean : result) {
		if (bean.getQnPics() != null && bean.getQnPics().size() > 5) {
		    bean.setQnPics(bean.getQnPics().subList(0, 4));
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("zrId", bean.getId());
		List<ZrHouseHmHouse> zrHouseHmHouses = zrHouseHmHouseDao
			.select("queryByZrId", params);
		List<String> hmHouseIds = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(zrHouseHmHouses)) {
		    for (ZrHouseHmHouse zhhh : zrHouseHmHouses) {
			hmHouseIds.add(String.valueOf(zhhh.getHmId()));
		    }
		}
		bean.setHmIds(hmHouseIds);
	    }
	}
	model.addAttribute("houses", result);
	model.addAttribute("total", total);
	model.addAttribute("pageSize", pageSize);
	model.addAttribute("page", page);
	model.addAllAttributes(para);

	return "zr/houseList";
    }

    @RequestMapping(value = "zr/createHouse.controller")
    public ModelAndView createHouse(int accountId, int residenceId,
	    String zrHouseId) {

	// TODO: account & residence validate
	boolean hasPower = false;
	Set<Integer> residenceList = new HashSet<Integer>();
	Set<Integer> regionList = new HashSet<Integer>();
	Set<Integer> plateList = new HashSet<Integer>();
	// 小区
	List<AccountResidenceEntity> list = (List<AccountResidenceEntity>) accountResidenceDao
		.select("findAccountResidenceList", new UniqueIdObject(
			accountId));
	if (!CollectionUtils.isEmpty(list)) {

	    List<Integer> tempResidenceList = (List<Integer>) CollectionUtils
		    .collect(list, new Transformer() {
			@Override
			public Object transform(Object input) {
			    return ((AccountResidenceEntity) input)
				    .getResidenceID();
			}
		    });

	    if (tempResidenceList != null) {
		residenceList = new HashSet<Integer>(tempResidenceList);
	    }

	}

	list = (List<AccountResidenceEntity>) accountResidenceDao.select(
		"findAccountRegionList", new UniqueIdObject(accountId));
	if (!list.isEmpty()) {
	    for (int i = 0; i < list.size(); i++) {
		AccountResidenceEntity entity = list.get(i);
		if (entity
			.getResidenceIDType()
			.equals(AccountResidenceEntity.ResidenceIDTypeEnum.Region.value)) {
		    regionList.add(entity.getResidenceID());
		} else {
		    plateList.add(entity.getResidenceID());
		}
	    }
	}

	ResidenceExtEntity residence = (ResidenceExtEntity) residenceDao.load(
		"loadResidence", residenceId);
	if (residence != null
		&& (residenceList.contains(residenceId)
			|| plateList.contains(Integer.valueOf(residence
				.getRegionId())) || regionList.contains(Integer
			.valueOf(residence.getParentRegionId())))) {
	    hasPower = true;
	}
	if (!hasPower) {
	    HouseMartContext.setSysMsg("经纪人没有小区权限");
	    return new ModelAndView(new RedirectView(
		    "/zr/accountList.controller?zrHouseId=" + zrHouseId));
	}

	ZrHouseService zrHouseService = SpringContextHolder
		.getBean("zrHouseService");

	String[] zrIds = zrHouseId.split(",");
	for (String zrId : zrIds) {

	    int hId = Integer.valueOf(zrId);
	    ZrHouse zrHouse = zrHouseService.findHouseById(hId);

	    HouseSaleEntity sale = null;
	    HouseRentEntity rent = null;

	    // 登盘
	    HouseExtEntity house = ZrHouseHelper.zrHouse2HMHouse(zrHouse);
	    house.setSourceType(HouseEntity.SourceTypeEnum.external.value);
	    house.setStatus(StatusEnum.Default.value);
	    house.setAddTime(Calendar.getInstance().getTime());
	    house.setUpdateTime(Calendar.getInstance().getTime());
	    house.setCreator(accountId);
	    house.setResidenceId(residenceId);
	    house.setHouseType("7"); // 独栋
	    house.setDirection(101); // 南北朝向
	    house.setDecorating(HouseEntity.DecoratingEnum.Well.value);
	    house.setFloor(6); // 独栋
	    houseDao.add("addHouse", house);

	    sale = house.generateHouseSaleEntity();
	    rent = house.generateHouseRentEntity();

	    sale.setPrice(house.getSalePrice());
	    sale.setAvgPrice(sale.getPrice() / house.getPropertyArea());
	    sale.setSaleStatus(HouseSaleEntity.SaleStatusEnum.Saling.saleStatus);
	    sale.setHouseId(house.getId());
	    sale.setSource("2");
	    sale.setAddTime(Calendar.getInstance().getTime());
	    sale.setUpdateTime(Calendar.getInstance().getTime());
	    rent.setHouseId(house.getId());
	    rent.setAddTime(Calendar.getInstance().getTime());
	    rent.setUpdateTime(Calendar.getInstance().getTime());

	    houseSaleDao.add("addHouseSale", sale);
	    houseRentDao.add("addHouseRent", rent);

	    String qnpics = zrHouse.getQnPics();
	    if (StringUtils.isNotBlank(qnpics)) {
		try {
		    List<String> pics = om.readValue(qnpics, List.class);
		    if (pics != null) {
			for (String pic : pics) {
			    HousePicEntity housePic = new HousePicEntity();
			    housePic.setHouseId(house.getId());
			    housePic.setResidenceId(0);
			    housePic.setType(HousePicEntity.Type.HousePic
				    .getValue());
			    housePic.setCloudUrl(pic);
			    housePic.setStatus(1);
			    housePic.setAddTime(Calendar.getInstance()
				    .getTime());
			    housePic.setUpdateTime(Calendar.getInstance()
				    .getTime());
			    housePic.setShowStatus(1);
			    housePicDao.add("addHousePic", housePic);
			}
		    }
		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
		}
	    }
	    // 自动审核通过
	    int auditId = auditService.requestAddNewHouse(house.getId());
	    auditService.approveNewHouse(house.getId(), auditId);
	    HouseMartContext.setSysMsg("已添加新房源 ,请审核");

	    ZrHouseHmHouse zrhmHouse = new ZrHouseHmHouse();
	    zrhmHouse.setZrId(hId);
	    zrhmHouse.setHmId(house.getId());
	    zrhmHouse.setAccountId(accountId);
	    zrhmHouse.setAddTime(new Date());
	    zrhmHouse.setUpdateTime(new Date());
	    zrHouseHmHouseDao.add("addZrHouseHmHouse", zrhmHouse);
	}
	return new ModelAndView(new RedirectView("/zr/findHouse.controller"));
    }

    @RequestMapping(value = "zr/accountList.controller", method = RequestMethod.GET)
    public String accountList(
	    Model model,
	    @RequestParam(value = "zrHouseId", required = true) String zrHouseId,
	    @RequestParam(value = "keyword", required = false) String keyword,
	    @RequestParam(value = "page", required = false) Integer page,
	    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
	keyword = (keyword == null ? "" : keyword.trim());
	page = (page == null ? 0 : page);
	pageSize = (pageSize == null ? 50 : pageSize);

	List<AccountExtEntity> list = null;

	Map<Object, Object> map = new HashMap<Object, Object>();
	map.put("skip", page * pageSize);
	map.put("count", pageSize);
	map.put("orderByClause", "acc.ID DESC");

	if (keyword.length() > 0) {
	    map.put("searchKeyword", keyword);
	}

	list = accountDao.select("findAccountList", map);
	Integer totalCount = accountDao.count("countAccountList", map);

	model.addAttribute("list", list);

	Map<Object, Object> pager = new HashMap<Object, Object>();
	pager.put("totalCount", totalCount);
	pager.put("page", page);
	pager.put("pageSize", pageSize);

	ZrHouseService zrHouseService = SpringContextHolder
		.getBean("zrHouseService");

	String titles = "";
	String[] zrIds = zrHouseId.split(",");
	for (String zrId : zrIds) {
	    ZrHouse zrHouse = zrHouseService.findHouseById(Integer
		    .valueOf(zrId));
	    titles = titles + "," + zrHouse.getTitle();
	}
	titles = titles.substring(1);

	model.addAttribute("pager", pager);
	model.addAttribute("keyword", keyword);
	model.addAttribute("zrHouseId", zrHouseId);
	model.addAttribute("zrHouseTitle", titles);

	return "zr/accountList";
    }

    public static class Task {
	private Date start;
	private Date end;
	private String name;

	public Date getStart() {
	    return start;
	}

	public void setStart(Date start) {
	    this.start = start;
	}

	public Date getEnd() {
	    return end;
	}

	public void setEnd(Date end) {
	    this.end = end;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	@Override
	public int hashCode() {
	    return name.hashCode() + start.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
	    Task o = (Task) obj;
	    return o.getName().equals(this.name)
		    && o.getStart().getTime() == this.start.getTime();
	}
    }

}
