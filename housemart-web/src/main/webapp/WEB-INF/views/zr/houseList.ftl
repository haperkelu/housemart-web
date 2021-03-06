<html>
	<head></head>	
	<title>加州房源</title>
	<body>
		<br>
		<hr>
		<div>
			<div class="view" >
			</div>	
			<div class="update">
				<form id="search" action="/zr/findHouse.controller" name="search" method="POST">  
					mls编号<input name="mlsLike" type="text" value="${(mlsLike)!}"/>
					&nbsp;&nbsp;&nbsp;
					板块<input name="neighborhoodLike" type="text" value="${(neighborhoodLike)!}"/>  
					&nbsp;&nbsp;&nbsp;
					邮编或者区域<input name="blockLike" type="text" value="${(blockLike)!}"/>
					<input type="submit" value="搜索"/>
					
					<input type="hidden" value="${(total)!0}" id="total"/>
					<input type="hidden" name="page" value="${(page)!}" id="page"/>
					<input type="hidden" name="pageSize" value="${(pageSize)!}" id="pageSize"/>
					
					<div id="pagination" class="pagination">
					</div>
				</form>
			</div>
			<br>					
			<hr>
			<div class="update">
				<table>
						<tr>
							<th><a id="batchCreate" href="javascript:void;">批量创建</a></th>
							<th>ID</th><th>预览</th><th>标题</th><th>操作</th><th>已创建房源</th><th>街区</th><th>MLS</th><th>区域</th><th>详情</th><th>价格</th><th>状态</th><th>发布时间</th><th>抓取日期</th>
						</tr>
				<#if houses??>
					<#list houses as house>
						<tr>
							<td><input type="checkbox" class="houseIds" value="${(house.id)!}" /></td>
							<td>${(house.id)!}</td>
							<td>
							<#list house.qnPics as pic>
							<img src="${(pic)!}" class="img-thumbnail" width="70" height="53">
							</#list>
							</td>
							<td>${(house.title)!}</td>
							<td>
								<a href="${(house.link)!}" target="_blank">原链接</a>
								<br/>
								<br/>
								<a href="/zr/accountList.controller?zrHouseId=${(house.id)!}" target="_blank">创建房源</a>
							</td>
							<td>
								<#list house.hmIds as hmId>
									<a href="/external/houseView.controller?houseId=${(hmId)!}" target="_blank">${(hmId)!}</a><br/>
								</#list>
							</td>
							<td>${(house.block)!}</td>
							<td>${(house.mls)!}</td>
							<td>${(house.neighborhood)!}</td>
							<td>${(house.houseDetail)!}</td>
							<td>${(house.price)!}</td>
							<td>${(house.status)!}</td>
							<td>${(house.listed)!}</td>
							<td><#if house.updateTime??>${house.updateTime?string("yyyy-MM-dd")}</#if></td>
						</tr>
					</#list>
				</#if>
				</table>		
			</div>				
		</div>
		<script>
			var callbackCounter = 0;
			
			function pageselectCallback(page_index, jq){
				if(callbackCounter>0){
					$("#page").val(page_index);
					$("#search").submit();
				}
				callbackCounter++;
			}
			
			$(document).ready(function(){
				$("#pagination").pagination(parseInt($("#total").val()), {
				    num_edge_entries: 2,
				    num_display_entries: 4,
				    callback: pageselectCallback,
				    items_per_page:$("#pageSize").val(),
				    current_page:parseInt($("#page").val())
				});
				
				$("#batchCreate").click(function(){
					if(!$("input:checkbox[class='houseIds']:checked").size() > 0){
						alert("请先勾选关联房源");
						return false;
					}
					var ids = "";
					$("input:checkbox[class='houseIds']:checked").each(function(){
						ids=ids+$(this).val()+",";	
					});
					ids=ids.substring(0,ids.length-1);
					if(confirm("关联房源" + ids)){
						location.href = "/zr/accountList.controller?zrHouseId=" + ids;
					}
				});
				
				
			});
			
			
		</script>
	</body>
</html>