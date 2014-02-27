/**
 * Created on 2013-4-19
 * 
 */
package org.housemart.crawl.pic.service.anjuke.store;

import java.text.MessageFormat;

public class ResidenceStoreProfile {

	private String rootPath;
	private String subPathRegular;

	public String generateStorePath(String city, String residence) {
		return rootPath + "/" + MessageFormat.format(subPathRegular, city, residence);
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getSubPathRegular() {
		return subPathRegular;
	}

	public void setSubPathRegular(String subPathRegular) {
		this.subPathRegular = subPathRegular;
	}

}
