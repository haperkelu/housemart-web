/**
 * Created on 2013-1-22
 * 
 */
package org.housemart.crawl.common;

public class GetConnection extends Connection {

	public GetConnection(String url, String cookies) throws Exception {
		super(url, cookies);

		httpConnection.setRequestMethod("GET");
	}

}
