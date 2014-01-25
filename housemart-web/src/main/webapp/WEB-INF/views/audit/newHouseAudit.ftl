<html>
<head><title>房源审核</title></head>
<body>

<#include "/audit/auditNav.ftl">
<h3>
<a href="/houseAudit.controller?sourceType=1">内部盘源(${(internalCount)!})</a>
&nbsp
<a href="/houseAudit.controller?sourceType=3">外部盘源(${(externalCount)!})</a>
&nbsp
<a href="/rejectedHouseList.controller?sourceType=3&auditType=1">外部房源拒绝列表</a>
</h3>
<p>
<div>
<form action="/houseAudit.controller" method="post">
	小区名称：<input type="text" name="residenceName" value="${(residenceName)!}" />
	登盘人姓名：<input type="text" name="creatorName" value="${(creatorName)!}" />

	<input type="hidden" value="${(sourceType)!}" name="sourceType"/>
	<input type="submit" value="搜索"/>
</form>
</div>
</p>
&nbsp;&nbsp;<a href="javascript:;" class="approveAll" style="display:none;">通过</a>&nbsp;&nbsp;<a href="javascript:;" class="rejectAll" style="display:none;">拒绝</a>
<table>
<thead>
<th><input type="checkbox" id="allSelection"/></th>
<th>板块</th><th>楼盘</th><th>房号</th><th>房东信息</th><th>操作</th><th>申请时间</th><th>登盘人姓名</th><th>房源详情</th>
</thead>
<#if list?exists>
<tbody>
<#list list as item> 
<tr>
<td><input type="checkbox" class="auditItem" readonly="readonly" _houseId='${item.houseId}' _id='${item.id}'/></td>
<td>${(item.plateName)!}</td>
<td>${(item.residenceName)!}</td>
<td>${(item.roomNo)!}</td>
<td>${(item.contactInfo)!}</td>
<td>
	<a href="/audit/approveNewHouse.controller?id=${item.id}&houseId=${item.houseId}">同意</a>&nbsp
	<a href="javascript:;" onclick="rejectCfm(${item.id}, ${item.houseId})">拒绝</a></td>
<td>${item.addTime?string("yyyy-MM-dd HH:mm:ss")}</td>
<td>${item.creatorName}</td>
<td><a href="/external/houseView.controller?auditId=${item.id}&houseId=${item.houseId}" target="_blank">查看</a></td>
</tr>
</#list>
</tbody>
</#if>
</table>

<div id="reject-dialog" title="拒绝申请" style="display:none;">
  	<p>
  		<div>
	  	<span>拒绝理由：</span><input id="comments" value=""/>
	  	</div>
	</p>
</div>

<script>
function rejectCfm(id, hosueId){
	$( "#reject-dialog" ).dialog({
      height:300,
      width:400,
      modal: true,
      buttons: {
        "拒绝": function() {
			window.location = "/audit/rejectNewHouse.controller?id=" + id + "&houseId=" + hosueId + "&comments=" + $("#comments").val();
        },
        "取消": function() {
          $(this).dialog( "close" );
        }
      }
	});
}
	
$(document).ready(function(){

	$('#allSelection').click(function(){

		if($(this).attr('checked')){
			// 全选
			$('.approveAll').show();
			$('.rejectAll').show();

			$('.auditItem').attr("checked", "checked");
			
		}else{
			// 取消全选
			$('.approveAll').hide();
			$('.rejectAll').hide();
			
			$('.auditItem').removeAttr("checked");
		}
	
	});
	
	$('.approveAll').click(function(){
		var houseIds = '';
		var ids = '';
		$('.auditItem:checked').each(function(index){
			houseIds += $(this).attr("_houseId");
			ids += $(this).attr("_id");
			if(index +1 < $('.auditItem:checked').size()){
				houseIds += ",";
				ids += ",";
			}
		});
		
		window.location = "/audit/approveNewHouses.controller?ids=" + ids + "&houseIds=" + houseIds; 
	});
	
	$('.rejectAll').click(function(){
		$( "#reject-dialog" ).dialog({
	      height:300,
	      width:400,
	      modal: true,
	      buttons: {
	        "拒绝": function() {
	        	var houseIds = '';
				var ids = '';
				$('.auditItem:checked').each(function(index){
					houseIds += $(this).attr("_houseId");
					ids += $(this).attr("_id");
					if(index +1 < $('.auditItem:checked').size()){
						houseIds += ",";
						ids += ",";
					}
				});
				
				window.location = "/audit/rejectNewHouses.controller?ids=" + ids + "&houseIds=" + houseIds + "&comments=" + $("#comments").val(); 
	        },
	        "取消": function() {
	          $( this ).dialog( "close" );
	        }
	      }
    	});
	
	});
	
});	

</script>
</body>
</html>