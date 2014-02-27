/**
 * Created on 2013-1-22
 * 
 */
package org.housemart.crawl.common;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

public abstract class Connection {

	protected String cookies;
	protected String url;
	protected HttpURLConnection httpConnection;

	public Connection(String url, String cookies) throws Exception {
		this.cookies = cookies;
		this.url = url;

		URL urlObject = null;
		urlObject = new URL(url);
		httpConnection = (HttpURLConnection) urlObject.openConnection();
		httpConnection
				.setRequestProperty(
						"User-Agent",
						" Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1 AlexaToolbar/alxg-3.1");
		httpConnection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
		httpConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
		httpConnection.setRequestProperty("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
		httpConnection.setRequestProperty("Connection", "keep-alive");

		if (StringUtils.isNotEmpty(cookies))
			httpConnection.setRequestProperty("cookie", cookies);
	}

	public String getCookies() {
		return cookies;
	}

	public String getUrl() {
		return url;
	}

	public HttpURLConnection getHttpConnection() {
		return httpConnection;
	}

	public void setHttpConnection(HttpURLConnection httpConnection) {
		this.httpConnection = httpConnection;
	}

}
