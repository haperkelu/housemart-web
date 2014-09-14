/**
 * Created on 2014-2-27
 * 
 */
package org.housemart.crawl.ziprealty.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.crawl.common.crawler.HouseMartScraper;
import org.housemart.crawl.common.crawler.PicCrawler;
import org.housemart.crawl.common.crawler._ACrawler;
import org.housemart.crawl.ziprealty._IZrConstants;
import org.housemart.crawl.ziprealty.model.ZrHouse;
import org.housemart.pic.api.HessianPicServiceInterface;
import org.housemart.pic.api.PicSaveResult;
import org.housemart.resource.ResourceProvider;
import org.housemart.web.context.SpringContextHolder;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.variables.NodeVariable;

import com.caucho.hessian.client.HessianProxyFactory;

public class DetailPageCrawler extends _ACrawler {

    private PicUrlCrawler puc = new PicUrlCrawler();
    private ObjectMapper om = new ObjectMapper();

    public DetailPageCrawler() {
	super(_IZrConstants.CRAWLER_CIG_HOUSE_DETAIL, "");
    }

    public ZrHouse crawlDetailInfo(String detailUrl)
	    throws JsonGenerationException, JsonMappingException, IOException {
	ZrHouse zrHouse = new ZrHouse();

	final List<NodeVariable> title = new ArrayList<NodeVariable>();
	final List<NodeVariable> block = new ArrayList<NodeVariable>();
	final List<NodeVariable> mls = new ArrayList<NodeVariable>();
	final List<NodeVariable> price = new ArrayList<NodeVariable>();
	final List<NodeVariable> listed = new ArrayList<NodeVariable>();
	final List<NodeVariable> status = new ArrayList<NodeVariable>();
	final List<NodeVariable> detail = new ArrayList<NodeVariable>();
	final List<NodeVariable> neighborhood = new ArrayList<NodeVariable>();
	final List<NodeVariable> source = new ArrayList<NodeVariable>();

	Scraper scraper = new HouseMartScraper(config, workDir);
	scraper.getContext().put("url", detailUrl);
	scraper.getContext().put("title", title);
	scraper.getContext().put("block", block);
	scraper.getContext().put("mls", mls);
	scraper.getContext().put("price", price);
	scraper.getContext().put("listed", listed);
	scraper.getContext().put("status", status);
	scraper.getContext().put("detail", detail);
	scraper.getContext().put("neighborhood", neighborhood);
	scraper.getContext().put("source", source);
	// scraper.getHttpClientManager().setHttpProxy("localhost", 8888);
	scraper.setDebug(false);
	scraper.execute();

	if (!CollectionUtils.isEmpty(title)) {
	    if (title.size() > 0)
		zrHouse.setTitle(title.get(0).toString().replaceAll("\n", ","));
	    if (block.size() > 0)
		zrHouse.setBlock(block.get(0).toString());
	    if (mls.size() > 0)
		zrHouse.setMls(StringUtils.substringAfter(
			StringUtils.substringBefore(mls.get(0).toString(), ","),
			"#"));
	    if (price.size() > 0)
		zrHouse.setPrice(price.get(0).toString());
	    if (listed.size() > 0)
		zrHouse.setListed(listed.get(0).toString());
	    if (status.size() > 0)
		zrHouse.setStatus(status.size() > 0 ? status.get(0).toString()
			: "");
	    if (detail.size() > 0)
		zrHouse.setHouseDetail(stripHtml(detail.get(0).toString()));
	    zrHouse.setNeighborhood(neighborhood.size() > 0 ? neighborhood.get(
		    0).toString() : "");
	    zrHouse.setLink(detailUrl);
	    zrHouse.setAddTime(new Date());
	    zrHouse.setUpdateTime(new Date());
	    String src = source.size() == 0 ? "CARETS_OC" : source.get(0)
		    .toString().trim();

	    if (zrHouse.getMls() != null) {
		List<String> picUrlsOnZr = puc.crawlPicUrls(zrHouse.getMls(),
			src);
		List<String> picUrlsOnQn = new ArrayList<String>();
		if (picUrlsOnZr != null) {
		    PicCrawler pc = SpringContextHolder.getBean("picCrawler");
		    ResourceProvider resourceProvider = SpringContextHolder
			    .getBean("resourceProvider");

		    for (String puOnZr : picUrlsOnZr) {
			if (StringUtils.isBlank(puOnZr.trim())) {
			    continue;
			}
			String photoName = StringUtils.substringAfterLast(
				puOnZr, "/");
			String fileSavePath = new SimpleDateFormat("yyyyMMdd")
				.format(new Date())
				+ _IZrConstants.FILE_SUBPATH_RESIDENCE
				+ "/"
				+ photoName;
			try {
			    String path = pc.crawl(
				    _IZrConstants.HOST_ZIP_REALTY + puOnZr,
				    fileSavePath);
			    // TODO:uncomments
			    String URL = resourceProvider
				    .getValue("housemart.pic.service.url");
			    HessianProxyFactory factory = new HessianProxyFactory();
			    HessianPicServiceInterface service = (HessianPicServiceInterface) factory
				    .create(HessianPicServiceInterface.class,
					    URL);
			    PicSaveResult remoteResult = service
				    .savePicToCloud(-1, photoName, "image/jpg",
					    path);
			    if (remoteResult != null
				    && remoteResult.getCode() == 200) {
				picUrlsOnQn.add(remoteResult.getUrl());
			    }
			} catch (Exception e) {
			    log.error(e.getMessage(), e);
			}
		    }
		}
		zrHouse.setQnPics(om.writeValueAsString(picUrlsOnQn));
		zrHouse.setZrPics(om.writeValueAsString(picUrlsOnZr));
	    }
	}
	return zrHouse;
    }

    private String stripHtml(String content) {
	// <p>段落替换为换行
	content = content.replaceAll("<p .*?>", "\r\n");
	// <br><br/>替换为换行
	content = content.replaceAll("<br\\s*/?>", "\r\n");
	// 去掉其它的<>之间的东西
	content = content.replaceAll("\\<.*?>", "");
	// 还原HTML
	// content = HTMLDecoder.decode(content);
	return content;
    }
}
