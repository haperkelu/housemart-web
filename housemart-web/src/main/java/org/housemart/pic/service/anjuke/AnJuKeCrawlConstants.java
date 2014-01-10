/**
 * Created on 2013-4-19
 * 
 */
package org.housemart.pic.service.anjuke;

public interface AnJuKeCrawlConstants {

	static final int CITY_SHANGHAI = 11;
	static final int PAGE_SIZE = 15;

	static final String SEARCH_URL_PATTERN_COMMUNITY = "http://api.anjuke.com/mobile/1.3/community.get?i=357853047832754&m=Android-GT-I9001&o=GT-I9001-user+2.3.3+GINGERBREAD+ZSKI1+release-keys&v=2.3.3&cv=3.2.1&app=a-anjuke&pm=b23&cid=11&api_key=eb8cd4ef60fde7580260cf9cf4250a24&maptype=baidu&id={0}";
	static final String SEARCH_URL_PATTERN_HOUSE = "http://api.anjuke.com/mobile/1.3/property.searchV3?i=357853047832754&m=Android-GT-I9001&o=GT-I9001-user+2.3.3+GINGERBREAD+ZSKI1+release-keys&v=2.3.3&cv=3.2.1&app=a-anjuke&pm=b23&cid=11&api_key=eb8cd4ef60fde7580260cf9cf4250a24&map_type=baidu&uuid=357853047832754&city_id={0}&q={1}&page_size={2}&page={3}";
}
