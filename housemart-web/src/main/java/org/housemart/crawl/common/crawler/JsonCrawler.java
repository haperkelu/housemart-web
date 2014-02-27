/**
 * Created on 2013-4-20
 * 
 */
package org.housemart.crawl.common.crawler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.util.CollectionUtils;
import org.webharvest.runtime.variables.ListVariable;

public class JsonCrawler extends _ACrawler {

	public JsonCrawler(String workDir) {
		super("org/housemart/crawl/common/crawler/JsonData.xml", workDir);
	}

	public String crawlReturnJson(String url) throws Exception {

		String jsonResult = null;

		List<ListVariable> jsonData = new ArrayList<ListVariable>();
		getScraper().getContext().put("url", url);
		getScraper().getContext().put("jsonData", jsonData);
		getScraper().setDebug(true);
		getScraper().execute();

		if (!CollectionUtils.isEmpty(jsonData)) {
			String raw = jsonData.get(0).toString().trim().replaceAll("\\\"", "\\\\\"");
			jsonResult = StringEscapeUtils.unescapeJava(raw);
		}

		return jsonResult;
	}
}
