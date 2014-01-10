<title>栋座生成结果</title>
<body>
<#if entity?exists>
<h3><a href="/residence/${entity.residenceId}">${entity.residenceName}栋座信息</a>>><a href="/addResidenceBuilding?residenceId=${entity.residenceId}">生成栋座</a>>>生成栋座结果</h3>
<br/>
<div id="buidingList">
<table>
<thead><tr><th></th><th>栋座名称</th><th>几期</th><th>单元数</th></tr></thead>
<tbody id="buidingListBody">
<#if list?exists>
	<#list list as item> 
	<tr id="${item.id}">
	<td><input name="checkList" type="checkbox" value=${item.id} />
	</td>
	<td>${item.prefix}${item.codeBegin}${item.suffix}</td>
	<td>${item.period}</td>
	<td>${item.cellCount}</td>
	</tr>
	</#list>
</#if>
</tbody>
</table>
<br/>
<button id="batchDelete">删除选中</button>&nbsp<button id="batchCheckRevert">清除选中</button>
</div>
<br/>
</#if>

<script>
var residenceId = ${entity.residenceId};
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
			url: "/ajax/residenceBuildingDelete.controller",
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
</body>