package org.housemart.service.priviledge;

import java.util.List;

import org.housemart.service.enums.ResourceDescription;

public interface PriviledgeHandler {

	 int getPriviledgeValue(ResourceDescription des, int bizId, List<String> fieldsKeyList);
	
}
