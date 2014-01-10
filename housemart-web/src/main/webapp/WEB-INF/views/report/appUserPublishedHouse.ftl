<title>客户端发布房源</title>
<body>

<form action="/appUserPublishedHouse.controller" method="get" id="houseListForm" enctype="x-www-form-urlencoded" >
				<input type="hidden" value="${(paginateObject.count)!0}" id="totalCount"/>
				<input type="hidden" name="page" value="${(paginateObject.pageNo)!}" id="page"/>
				<input type="hidden" name="pageSize" value="${(paginateObject.pageSize)!}" id="pageSize"/>
</form>
<div>
	<table>
		<thead>
		<tr>
		<th>ID</th><th>联系人</th><th>手机</th><th>小区</th><th>地址</th><th>面积</th><th>添加时间</th>
		</tr>
		</thead>
		<tbody>
		<#if paginateObject?exists>
			
			<#list paginateObject.result as item>	
				<tr><td>${item.id}</td><td>${item.contactName}</td><td>${item.mobile}</td><td>${item.residenceName}</td><td>${item.address}</td><td>${item.area}</td><td>${item.addTime?string('yyyy-MM-dd HH:mm:ss')}</td></tr>	
			</#list>
			
		</#if>
		</tbody>
	</table>

	<div id="pagination" class="pagination">
	</div>
</div>

<script>
			
			var callbackCounter = 0;
			
			$(document).ready(function(){
				init();
			});
			
			function init(){
				$("#pagination").pagination(parseInt($("#totalCount").val()), {
				    num_edge_entries: 2,
				    num_display_entries: 4,
				    callback: pageselectCallback,
				    items_per_page:$("#pageSize").val(),
				    current_page:parseInt($("#page").val())
				});
			}
			function pageselectCallback(page_index, jq){
				if(callbackCounter > 0){
					$("#page").val(page_index);
					$("#houseListForm").submit();
				}
				callbackCounter++;
			}

</script>



</body>