<title>生成单元</title>
<body>
<#if entity?exists>
<#if building?exists>
<h3><a href="/residence/${entity.residenceId}">${entity.residenceName}栋座信息</a>>><a href="/residenceCellList?residenceId=${entity.residenceId}&buildingId=${building.id}">单元信息</a>>>生成单元 </h3>
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
<div id="cellPanel" style="line-height:200%;">
	单元编号:<br/>
	前缀(数字):<input id="cellPrefixBegin" class="inputBox" type="text"/>-<input id="cellPrefixEnd" class="inputBox" type="text"/><span class="hint">必须是数字，后面数字大于前面数字</span><br/>
	单元号: <br/>
	<input type="radio" name="cellType" value="1" checked>数字<input type="text" class="inputBox" id="dCodeBegin" />-<input type="text" class="inputBox" id="dCodeEnd" />
	限制条件<select id="cellLimit"><option value="0">无</option><option value="1">无单数</option><option value="2">无双数</option></select><span class="hint">必须是数字，后面数字大于前面数字</span><br/>
	<input type="radio" name="cellType" value="2">字母<input type="text" class="inputBox" id="lCodeBegin" />-<input type="text" class="inputBox" id="lCodeEnd" /><span class="hint">必须是字母，后面字母大于前面字母</span><br/>
	后缀:室
	<button id="addCellBtn">添加</button>
</div>
<br/>
<div style="border:1px solid black">
<image src="/resources/images/cell_hint.png"></image>
</div>
<script>
var buildingId = ${building.id};
$(document).ready(function(){

	//add cell
 	$("#addCellBtn").bind("click", function(){
 		var floorBegin = $('#cellPrefixBegin').val().trim();
 		var floorEnd = $('#cellPrefixEnd').val().trim();
 		
 		if(isEmpty(floorBegin) || isEmpty(floorEnd)){
 			alert('请选择必填项：单元编号前缀!');
 			return;
 		}
 		
 		var cellType = $('input:radio[name="cellType"]:checked').val();
 		var limit = $("#cellLimit").find("option:selected").val();
 		var begin,end;
  		if(cellType == '1'){
  			begin = $('#dCodeBegin').val();
  			end = $('#dCodeEnd').val();
  		}else{
  			begin = $('#lCodeBegin').val();
  			end = $('#lCodeEnd').val();
  		}
 		if(cellType == null || isEmpty(begin) || isEmpty(end)){
 			alert('请选择必填项：单元编号');
 			return;
 		}
 		
 		$.ajax({
			type: "post",
			url: "/ajax/residenceCellAdd.controller",
			data: {buildingId: buildingId, codeType:cellType, 
				floorBegin:floorBegin, floorEnd:floorEnd,
				begin:begin, end:end, codeLimit:limit
			},
			dataType: "json",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (data) {	
				location.href = '/addResidenceCellResult?residenceId=${entity.residenceId}&buildingId=${building.id}&idList=' + data.bizData;
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			}
  		});  
 		
 	});

});

var deleteCell = function(id) {
	var answer = confirm("确定删除?");
	if(answer){
		$.ajax({
			type: "post",
			url: "/ajax/residenceCellDelete.controller",
			data: {id: id},
			dataType: "json",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (data) {		
				refreshCellList();
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			}
		});		
	}
}

</script>


</#if>
</#if>
</body>