<select id="J_floor" name="floor" class="form-control">
  <option value="">选择</option>
  <option value="1" <#if house.floor?? && house.floor == 1>selected= "selected"</#if>>低层</option>
  <option value="2"	<#if house.floor?? && house.floor == 2>selected= "selected"</#if>>中低层</option>
  <option value="3"	<#if house.floor?? && house.floor == 3>selected= "selected"</#if>>中层</option>
  <option value="4"	<#if house.floor?? && house.floor == 4>selected= "selected"</#if>>中高层</option>
  <option value="5"	<#if house.floor?? && house.floor == 5>selected= "selected"</#if>>高层</option>
  
  <option value="6"	<#if house.floor?? && house.floor == 6>selected= "selected"</#if>>独栋</option>
  <option value="7"	<#if house.floor?? && house.floor == 7>selected= "selected"</#if>>双拼</option>
  <option value="8"	<#if house.floor?? && house.floor == 8>selected= "selected"</#if>>联排</option>
  <option value="9"	<#if house.floor?? && house.floor == 9>selected= "selected"</#if>>叠加</option>
  <option value="10"	<#if house.floor?? && house.floor == 10>selected= "selected"</#if>>新里</option>
  <option value="11"	<#if house.floor?? && house.floor == 11>selected= "selected"</#if>>老洋房</option>
</select>