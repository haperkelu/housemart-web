/**
 * Created on 2014-2-27
 * 
 */
package org.housemart.crawl.ziprealty.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.crawl.common.crawler.HouseMartScraper;
import org.housemart.crawl.common.crawler._ACrawler;
import org.housemart.crawl.ziprealty._IZrConstants;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.variables.NodeVariable;

public class PicUrlCrawler extends _ACrawler {

    public PicUrlCrawler() {
	super(_IZrConstants.CRAWLER_CIG_PIC_URL, "");
    }

    public List<String> crawlPicUrls(String mls, String source) {
	List<String> urls = new ArrayList<String>();
	final List<NodeVariable> pic = new ArrayList<NodeVariable>();
	Scraper scraper = new HouseMartScraper(config, workDir);
	scraper.getContext().put(
		"url",
		"http://www.ziprealty.com/xhr/photoserver?listing_num=" + mls
			+ "&source=" + source);
	scraper.getContext().put("pic", pic);
	// scraper.getHttpClientManager().setHttpProxy("localhost", 8888);
	scraper.setDebug(false);
	scraper.execute();

	if (!CollectionUtils.isEmpty(pic) && pic.get(0).toString().length() > 0) {
	    if (!CollectionUtils.isEmpty(pic)) {
		String[] purls0 = pic.get(0).toString().split("\t\n");
		if (purls0 != null) {
		    for (String url : purls0)
			if (StringUtils.isNotBlank(url))
			    urls.add(url);
		}
	    }
	}
	return urls;
    }
}
