<title>单元信息</title>
<body>
<#if entity?exists>
<h3><a href="/residence/${entity.residenceId}">${entity.residenceName}栋座信息</a>>>生成单元 </h3>

<#if building?exists>
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
<div style="float:left;padding-left:40px;"><a target = "_blank" href="/addResidenceCell?residenceId=${entity.residenceId}&buildingId=${building.id}">生成单元</a></div>
<div style="clear:both"></div>
</div>
<br/>

<div id="buidingList">
	<table>
	<thead><tr><th></th><th>单元名称</th></tr></thead>
	<tbody id="cellListBody">
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
				refreshCellList();
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			}
		    });			
		}		
   });

});

var refreshCellList = function(){

	  	$.ajax({
		type: "post",
		url: "/ajax/residenceCellList.controller",
		data: {id: buildingId},
		dataType: "json",
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success: function (data) {		
			$('#cellListBody').html('');	
			var totalStr = ''
			for(i in data){
			
				totalStr += '<tr><td>' + '<input name="checkList" type="checkbox"' + ' value=' + data[i].id + ' />'
				 + '</td>';
				totalStr += '<td>' + data[i].floorBegin + data[i].codeBegin + '室'
				 + '</tr>';
			}
			$('#cellListBody').html(totalStr);
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		}
  	}); 

};

refreshCellList();

</script>

</#if>
</#if>
</body>