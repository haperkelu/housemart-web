<select id="J_houseType" name="houseType" class="form-control" value="${(house.houseType)!}">
  <option value="">选择</option>
  	<option value="4"	<#if house.houseType?? && house.houseType == '4'>selected= "selected"</#if>>公寓</option>
  	<option value="5"	<#if house.houseType?? && house.houseType == '5'>selected= "selected"</#if>>老公房</option>
	<option value="7"	<#if house.houseType?? && house.houseType == '7'>selected= "selected"</#if>>独栋</option>
	<option value="8"	<#if house.houseType?? && house.houseType == '8'>selected= "selected"</#if>>双拼</option>
	<option value="2" <#if house.houseType?? && house.houseType == '2'>selected= "selected"</#if>>联排</option>
	<option value="9" <#if house.houseType?? && house.houseType == '9'>selected= "selected"</#if>>叠加</option>
	<option value="3" <#if house.houseType?? && house.houseType == '3'>selected= "selected"</#if>>新里</option>
	<option value="10" <#if house.houseType?? && house.houseType == '10'>selected= "selected"</#if>>洋房</option>
</select>