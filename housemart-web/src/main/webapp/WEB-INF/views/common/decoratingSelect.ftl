<select id="J_decorating" name="decorating" class="form-control">
	<option value="">选择</option>
	<option value="5" <#if house.decorating?? && house.decorating == 5>selected= "selected"</#if>>豪装</option>
	<option value="1" <#if house.decorating?? && house.decorating == 1>selected= "selected"</#if>>精装</option>
	<option value="2"	<#if house.decorating?? && house.decorating == 2>selected= "selected"</#if>>简装</option>
	<option value="3"	<#if house.decorating?? && house.decorating == 3>selected= "selected"</#if>>毛坯</option>
</select>