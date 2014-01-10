<select name="direction" id="J_direction" class="form-control">
	<option value="0">选择</option>
	<option value="1000" <#if house.direction?? && house.direction == 1000>selected= "selected"</#if>>东</option>
	<option value="100" <#if house.direction?? && house.direction == 100>selected= "selected"</#if>>南</option>
	<option value="10" <#if house.direction?? && house.direction == 10>selected= "selected"</#if>>西</option>
	<option value="1"	<#if house.direction?? && house.direction == 1>selected= "selected"</#if>>北</option>
	<option value="1100"	<#if house.direction?? && house.direction == 1100>selected= "selected"</#if>>东南</option>
	<option value="1001"	<#if house.direction?? && house.direction == 1001>selected= "selected"</#if>>东北</option>
	<option value="110"	<#if house.direction?? && house.direction == 110>selected= "selected"</#if>>西南</option>
	<option value="11"	<#if house.direction?? && house.direction == 11>selected= "selected"</#if>>西北</option>
	<option value="101"	<#if house.direction?? && house.direction == 101>selected= "selected"</#if>>南北</option>
	<option value="1010"	<#if house.direction?? && house.direction == 1010>selected= "selected"</#if>>东西</option>
</select>