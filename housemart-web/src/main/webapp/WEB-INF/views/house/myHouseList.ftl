<html>
	<head>
	</head>	
	<title>我的盘源</title>
	<body>
		<div>
			<form action="/myHouseList.controller" method="get" id="houseListForm" enctype="x-www-form-urlencoded" onsubmit="$('#residenceName').val(encodeURI($('#residenceName').val()))">
				<input type="hidden" value="${(param.totalCount)!0}" id="totalCount"/>
				<input type="hidden" name="page" value="${(param.page)!}" id="page"/>
				<input type="hidden" name="pageSize" value="${(param.pageSize)!}" id="pageSize"/>
				<div>
					<input type="button" value="我要登盘" onclick="window.open('/houseEdit.controller')" />
				</div>
				<br>
				<div style="width:1024px;">
					<table>
						<tr>
							<th style="display:none;"></th><th>操作</th><th>有效</th><th>状态</th><th>楼盘</th><th>栋座</th><th>房号</th><th>楼层</th><th>朝向</th><th>房型</th><th>面积</th><th>售价</th><th>租价</th><th>单价</th><th>钥匙</th><th>更新内容</th>
						</tr>
						<#if houseList??>
							<#list houseList as house> 
							<tr _hId="${(house.id)!}">
								<td style="display:none;">
									<#if house.favoriteId??>
										<a href="javascript:;" class="delFav">取消</a>
									<#else>
										<a href="javascript:;" class="addFav">收藏</a>		
									</#if>
								</td>
								<td><a href="houseEdit.controller?houseId=${(house.id)!}" target="_blank">查看</a></td>
								<td>${(house.status)!}</td>
								<td>
									<#if house.saleSaleStatus??><@spring.message "house.sale.saleStatus.${house.saleSaleStatus}"/></#if>/<#if house.rentRentStatus??><@spring.message "house.rent.rentStatus.${house.rentRentStatus}"/></#if>
								</td>
								<td>${(house.residenceName)!}</td>
								<td>${(house.buildingPrefix)!}${(house.buildingCodeBegin)!}${(house.buildingSuffix)!}</td>
								<td>${(house.cellFloorBegin)!}${(house.cellCodeBegin)!}</td>
								<td>${(house.cellFloorBegin)!}</td>
								<td><#if house.direction??><@spring.message "house.house.direction.${house.direction}"/></#if></td>
								<td>${(house.roomType)!}</td>
								<td>${(house.propertyArea)!}</td>
								<td>${(house.salePrice)!}</td>
								<td>${(house.rentPrice)!}</td>
								<td>${(house.salePrice)!}</td>
								<td><#if house.hasKey??><@spring.message "house.house.hasKey.${house.hasKey}"/></#if></td>
								<td></td>
							</tr>
							</#list>
						</#if>	
					</table>
				</div>
				<div id="pagination" class="pagination">
				</div>	
			</form>
		</div>
		<script>
			var callbackCounter = 0;
			
			$(document).ready(function(){
				init();
				bind();
				exec();
			});
			
			// init
			function init(){
				$("#pagination").pagination(parseInt($("#totalCount").val()), {
				    num_edge_entries: 2,
				    num_display_entries: 4,
				    callback: pageselectCallback,
				    items_per_page:$("#pageSize").val(),
				    current_page:parseInt($("#page").val())
				});
			}
			
			// bind
			function bind(){
			}
			
			// exec
			function exec(){
				//TODO:exec
			}
			
			// funcs
			function pageselectCallback(page_index, jq){
				if(callbackCounter>0){
					$("#page").val(page_index);
					$("#houseListForm").submit();
				}
				callbackCounter++;
			}
		</script>
	</body>
</html>
