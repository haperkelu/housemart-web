/**
 * Created on 2013-3-29
 * 
 */
package org.housemart.crawl.pic.service.anjuke.crack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.housemart.util.EncryptUtils;

public class DecryptSig {
	private static String apiKey = "eb8cd4ef60fde7580260cf9cf4250a24";
	private static String privateKey = "5d41a9e970273bca";

	/**
	 * 获取用于加密的参数
	 * 
	 * @param param
	 * @return
	 */
	public static Map<String, String> obtainSignificativeParam(Map<String, String> param) {
		Map<String, String> ret = new HashMap<String, String>();
		// for house list
		if (param.get("city_id") != null)
			ret.put("city_id", param.get("city_id"));
		if (param.get("uuid") != null)
			ret.put("uuid", param.get("uuid"));
		if (param.get("q") != null)
			ret.put("q", param.get("q"));
		if (param.get("page") != null)
			ret.put("page", param.get("page"));
		if (param.get("page_size") != null)
			ret.put("page_size", param.get("page_size"));
		if (param.get("map_type") != null)
			ret.put("map_type", param.get("map_type"));

		// for resicence detail
		if (param.get("maptype") != null)
			ret.put("maptype", param.get("maptype"));
		if (param.get("id") != null)
			ret.put("id", param.get("id"));
		return ret;
	}

	public static String generateSig(Map<String, String> param) throws Exception {
		return EncryptUtils.md5(encryptParameterList(param) + privateKey).toLowerCase();
	}

	/**
	 * 安居客的一系列加密方法
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	private static String encryptParameterList(Map<String, String> param) {
		ArrayList localArrayList = new ArrayList();
		Iterator localIterator1 = param.entrySet().iterator();
		StringBuffer localStringBuffer;
		Iterator localIterator2;

		localArrayList.add("api_key=" + apiKey);
		localStringBuffer = new StringBuffer();

		while (localIterator1.hasNext()) {
			Map.Entry localEntry = (Map.Entry) localIterator1.next();
			Object localObject1 = localEntry.getKey();
			Object localObject2 = localEntry.getValue();
			localArrayList.add(localObject1 + "=" + localObject2);
		}
		Collections.sort(localArrayList);

		localIterator2 = localArrayList.iterator();
		while (localIterator2.hasNext()) {
			String str = (String) localIterator2.next();
			if (localStringBuffer.toString().length() == 0)
				localStringBuffer.append(str);
			else
				localStringBuffer.append("&" + str);
		}

		return localStringBuffer.toString();
	}

}
