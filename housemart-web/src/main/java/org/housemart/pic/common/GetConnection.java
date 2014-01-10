/**
 * Created on 2013-1-22
 * 
 */
package org.housemart.pic.common;

public class GetConnection extends Connection {

	public GetConnection(String url, String cookies) throws Exception {
		super(url, cookies);

		httpConnection.setRequestMethod("GET");
	}

}
