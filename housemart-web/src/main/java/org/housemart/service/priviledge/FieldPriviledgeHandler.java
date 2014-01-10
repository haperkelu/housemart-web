package org.housemart.service.priviledge;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.housemart.service.enums.ResourceDescription;
import org.housemart.web.context.HouseMartContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldPriviledgeHandler implements PriviledgeHandler {

	private static final Logger logger = LoggerFactory
			.getLogger("CommonLogger");
	
	@Override
	public int getPriviledgeValue(ResourceDescription desc, int bizId,
			List<String> fieldsKeyList) {
		
	  //TODO::times priviledge
	  
		if (desc.getWhiteListField() != null
				&& !CollectionUtils.isEmpty(fieldsKeyList)) {

			boolean isInWhiteList = true;
			for (String targetKey : fieldsKeyList) {
				if (!ArrayUtils.contains(desc.getWhiteListField(), targetKey)) {
					isInWhiteList = false;
				}
			}
			if (isInWhiteList == true && desc.getWhiteListOrgRule() != null) {

				int orgRuleValue = 0;
				int orgRuleValueTmp = 0;
				orgRuleValue = OrgRuleEngine.parseOwn(desc.getResource(),
						desc.getOrgRule(), bizId);
				if (orgRuleValue == 1) {
					return orgRuleValue;
				}

				// 跨职能架构的资源
				orgRuleValue = (orgRuleValueTmp = OrgRuleEngine.parseCross(
						desc.getResource(), desc.getOrgRule(), bizId)) > orgRuleValue ? orgRuleValueTmp
						: orgRuleValue;
				if (orgRuleValue == 1) {
					return orgRuleValue;
				}

				// 组织架构之下的资源
				orgRuleValue = (orgRuleValueTmp = OrgRuleEngine.parseAbove(
						desc.getResource(), desc.getOrgRule(), bizId)) > orgRuleValue ? orgRuleValueTmp
						: orgRuleValue;
				if (orgRuleValue == 1) {
					return orgRuleValue;
				}

				return 3;

			}
			
			//不在白名单里面
			if(isInWhiteList == false && desc.getOrgRule() != null) {
				
				int orgRuleValue = 0;
				int orgRuleValueTmp = 0;
				// 自己的资源
				orgRuleValue = OrgRuleEngine.parseOwn(desc.getResource(),
						desc.getOrgRule(), bizId);
				logger.info("parse own value:" + HouseMartContext.getCurrentUserId() + " " + orgRuleValue);
				if (orgRuleValue == 3) {
					return orgRuleValue;
				}
			
				// 跨职能架构的资源
				orgRuleValue = (orgRuleValueTmp = OrgRuleEngine.parseCross(
						desc.getResource(), desc.getOrgRule(), bizId)) > orgRuleValue ? orgRuleValueTmp
						: orgRuleValue;
				if (orgRuleValue == 3) {
					return orgRuleValue;
				}

				// 组织架构之下的资源
				orgRuleValue = (orgRuleValueTmp = OrgRuleEngine.parseAbove(
						desc.getResource(), desc.getOrgRule(), bizId)) > orgRuleValue ? orgRuleValueTmp
						: orgRuleValue;
				if (orgRuleValue == 3) {
					return orgRuleValue;
				}

				return orgRuleValue;
				
				
			}
			
			
			
		}
		return 0;
	}
}