<title>栋座生成结果</title>
<body>
<#if entity?exists>
<h3>${entity.residenceName}栋座信息</h3>
<div>
<table style="float:left">
<tr>
	<td>行政区：${entity.regionName}
	</td>
</tr>
<tr>
	<td>
		板块：${entity.plateName}
	</td>
</tr>
<tr>
	<td>
		小区名称：${entity.residenceName}
	</td>
</tr>
<tr>
	<td>
		${entity.address}
	</td>
</tr>
</table>
<div style="clear:both"></div>
</div>
<br/>
<h3>${building.prefix}${building.codeBegin}${building.suffix}</h3>
<br/>
<div>
<form id="form" name="form" action="/residenceBuildingUpdateSubmit" method="post">
梯户数: 
<input  name="residenceId" value=${entity.residenceId} type="hidden"/>
<input  name="buildingId" value=${building.id}  type="hidden" />
<input id="stairs" name="stair" class="inputBox" type="text" value=${building.stair} />梯<input id="houseHold" name="houseHold" class="inputBox" type="text" value=${building.houseHold} />户
类型:<select id="buildingType" name="buildingType">
<option selected>请选择</option><option>小高层</option><option>高层</option>
<option>多层</option><option>公寓</option><option>老公房</option>
<option>叠加别墅</option><option>独栋别墅</option><option>双拼别墅</option><option>联排别墅</option>	
</select>
<br/>
<br/>
几期:
<select id="period" name="period">
<option selected>请选择</option><option>一期</option><option>二期</option><option>三期</option><option>四期</option>
<option>五期</option><option>六期</option><option>七期</option><option>八期</option><option>九期</option><option>十期</option>
</select>
<button onclick="$('#form').submit();">提交</button>
</form>
</div>
</#if>

<script>
var buildingType = '${building.buildingType}';
var period = '${building.period}';

$('#buildingType option').each(function(){

	if($(this).text() == buildingType){
		$(this).attr("selected","true");
	}
	
});

$('#period option').each(function(){

	if($(this).text() == period){
		$(this).attr("selected", "true");
	}
	
});

</script>


</body>