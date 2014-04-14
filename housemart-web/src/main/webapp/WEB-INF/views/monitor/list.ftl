<title>系统请求Profiling</title>
<body>


<#if paginateObject?exists>
<form name="mainForm" id="mainForm" action="/monitor/list.controller" method="get">
	<input type="hidden" value="${(paginateObject.count)!0}" id="totalCount"/>
	<input type="hidden" name="page" value="${(paginateObject.pageNo)!}" id="page"/>
	<input type="hidden" name="pageSize" value="${(paginateObject.pageSize)!}" id="pageSize"/>
</form>	
<table>
	<thead>
		<th>ID</th><th>标签</th><th>URL</th><th>内容</th><th>时间</th>		
	</thead>
	<tbody>
	<#list paginateObject.result as item>	
		<tr><td>${item.id}</td><td>${item.bizTag}</td><td>${item.url}</td><td>${item.accessText}</td><td>${item.addTime?string('yyyy-MM-dd HH:mm:ss')}</td></tr>	
	</#list>		
	</tbody>
</table>

<div id="pagination" class="pagination">
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
				$("#mainForm").submit();
			}
			callbackCounter++;
		}

</script>

</#if>

				
				
				

</body>