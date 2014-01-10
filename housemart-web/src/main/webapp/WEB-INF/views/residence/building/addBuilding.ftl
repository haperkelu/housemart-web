<title>添加栋座</title>
<body>
<#if entity?exists>
<h3><a href="/residence/${entity.residenceId}">${entity.residenceName}栋座信息</a>>>生成栋座</h3>
<br/>
<div id="buildingPanel" style="line-height:200%;">
	梯户数: <input id="stairs" class="inputBox" type="text"/>梯<input id="houseHold" class="inputBox" type="text"/>户
	类型:<select id="buildingType">
	<option selected>请选择</option><option>小高层</option><option>高层</option>
	<option>多层</option><option>公寓</option><option>老公房</option>
	<option>叠加别墅</option><option>独栋别墅</option><option>双拼别墅</option><option>联排别墅</option>	
	</select>
	&nbsp几期:
	<select id="filterStage">
	<option selected>请选择</option><option>一期</option><option>二期</option><option>三期</option><option>四期</option>
	<option>五期</option><option>六期</option><option>七期</option><option>八期</option><option>九期</option><option>十期</option>
	</select>
	<br/>
	前缀:<input id="buildingPrefix" class="inputBox" type="text"/><span class="hint">这里可以是任意字符</span><br/>
	栋座编号: <br/>
	<input type="radio" name="codeType" value="1" checked>数字<input type="text" class="inputBox" id="dCodeBegin" />-<input type="text" class="inputBox" id="dCodeEnd" />
	限制条件<select id="buidingLimit"><option value="0">无</option><option value="1">无单数</option><option value="2">无双数</option></select><span class="hint">必须是数字，后面数字大于前面数字</span><br/>
	<input type="radio" name="codeType" value="2">字母<input type="text" class="inputBox" id="lCodeBegin" />-<input type="text" class="inputBox" id="lCodeEnd" /><span class="hint">必须是字母，后面字母大于前面字母</span><br/>
	后缀:<select id="buildingSuffix"><option>座</option><option selected>栋</option><option>号</option><option>幢</option></select>
	<br/>
	<button id="addBuildingBtn">添加</button>
</div>
<br/>
<br/>
<div style="border:1px solid black">
<image src="/resources/images/building_hint.png"></image>
</div>
<div style="padding-left:100px">栋座名生成方法，以汤城高尔夫为例</div>
</#if>

<script>
var residenceId = ${entity.residenceId};
$(document).ready(function(){
  
  //add building
  $("#addBuildingBtn").bind("click", function(){

 	var stair =  $('#stairs').val();
 	var houseHold =  $('#houseHold').val();

 	var buildingType =  $("#buildingType").find("option:selected").text();
 	if(buildingType == '请选择'){
		buildingType = '';
	}

  	var prefix = $('#buildingPrefix').val();
  	var codeType = $('input:radio[name="codeType"]:checked').val();
  	
  	var period = $("#filterStage").find("option:selected").text();
	if(period == '请选择'){
		period = '';
	}
  	
  	if(codeType == null){
  		alert('请选择必填项：栋座编号');
  		return;
  	} 
  	
  	var begin,end;
  	if(codeType == '1'){
  		begin = $('#dCodeBegin').val();
  		end = $('#dCodeEnd').val();
  	}else{
  		begin = $('#lCodeBegin').val();
  		end = $('#lCodeEnd').val();
  	}
  	var suffix = $('#buildingSuffix').val(); 
  	var limit = $("#buidingLimit").find("option:selected").val();
  	$.ajax({
		type: "post",
		url: "/ajax/addResidenceBuilding.controller",
		data: {residenceId: residenceId, prefix:prefix, codeType:codeType, 
		begin:begin, end:end, suffix:suffix, codeLimit:limit, period: period,
		stair:stair, houseHold:houseHold, buildingType:buildingType
		},
		dataType: "json",
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success: function (data) {	
			location.href = '/addBuildingResult?residenceId=${entity.residenceId}&idList=' + data.bizData;
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		}
  	});  //ajax call
   });  //click binding
   
   
   $('#filter').bind('click', function(){
   		refreshBuildingList();
   });
   
});

</script>




</body>