<title>生成单元结果</title>
<body>
<#if entity?exists>
<#if building?exists>
<h3><a href="/residence/${entity.residenceId}">${entity.residenceName}栋座信息</a>>><a href="/residenceCellList?residenceId=${entity.residenceId}&buildingId=${building.id}">单元信息</a>>>
<a href="/addResidenceCell?residenceId=${entity.residenceId}&buildingId=${building.id}">生成单元 </a>>>生成单元结果</h3>
<div>
<table style="float:left">
<tr>
	<td>${entity.residenceName}
	</td>
</tr>
<tr>
	<td>
		${building.prefix}${building.codeBegin}${building.suffix}
	</td>
</tr>
</table>
<div style="float:left;padding-left:40px;"></div>
<div style="clear:both"></div>
</div>
<br/>
<div id="buidingList">
	<table>
	<thead><tr><th></th><th>单元名称</th></tr></thead>
	<tbody id="cellListBody">	
		<#if list?exists>
		<#list list as item> 
		<tr id="${item.id}">
		<td><input name="checkList" type="checkbox" value=${item.id} />
		</td>
		<td>${item.floorBegin}${item.codeBegin}室</td>
		</tr>
		</#list>
		</#if>	
	</tbody>
	</table>
	<br/>
	<button id="batchDelete">删除选中</button>&nbsp<button id="batchCheckRevert">清除选中</button>
</div>

<script>
var buildingId = ${building.id};

$(document).ready(function(){

	$('#batchCheckRevert').bind('click', function(){
   		$("input[name='checkList']").removeAttr("checked"); 
   });
   
   $('#batchDelete').bind('click', function(){
   		var arrChk = $("input[name='checkList']:checked");
   		var arrChkIds = '';
   		$(arrChk).each(function(){
   			if(arrChkIds == ''){
   				arrChkIds += this.value;
   			}else{
   				arrChkIds += ',' + this.value;
   			}			
   		});
		var answer = confirm("确定删除?");
		if(answer){
			$.ajax({
			type: "post",
			url: "/ajax/residenceCellDelete.controller",
			data: {id: arrChkIds},
			dataType: "json",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (data) {		
				$(arrChk).each(function(){
					$('#' + this.value).remove();
				});
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			}
		    });			
		}		
   });
   

});

</script>


</#if>
</#if>
</body>