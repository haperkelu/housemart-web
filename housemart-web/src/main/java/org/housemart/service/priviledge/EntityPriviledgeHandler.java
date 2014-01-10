package org.housemart.service.priviledge;

import java.util.List;

import org.housemart.service.enums.ResourceDescription;

public class EntityPriviledgeHandler implements PriviledgeHandler {

	@Override
  public int getPriviledgeValue(ResourceDescription desc, int bizId,
      List<String> fieldsKeyList) {
    
    // orgRule 类别的校验
    
    int orgRuleValue = 0;
    int orgRuleValueTmp = 0;
    // 自己的资源
    orgRuleValue = OrgRuleEngine.parseOwn(desc.getResource(),
        desc.getOrgRule(), bizId);
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
