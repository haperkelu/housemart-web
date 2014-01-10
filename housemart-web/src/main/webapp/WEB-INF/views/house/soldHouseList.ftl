<html>
	<head>
	</head>	
	<title>我的盘源</title>
	<body>
		<div>
			<form action="/soldHouseList.controller" method="get" id="houseListForm" enctype="x-www-form-urlencoded" onsubmit="$('#residenceName').val(encodeURI($('#residenceName').val()))">
				<input type="hidden" value="${(param.totalCount)!0}" id="totalCount"/>
				<input type="hidden" name="page" value="${(param.page)!}" id="page"/>
				<input type="hidden" name="pageSize" value="${(param.pageSize)!}" id="pageSize"/>
				<div>
						<div>
							城市：
							<select name="regionCityId" value="${(param.regionCityId)!}" id="city">
								<option value="" selected="selected">选择</option>
								<option value="1" <#if param.regionCityId?? && param.regionCityId == 1>selected= "selected"</#if>>上海</option>
							</select>
						</div>
						<div>区域：
							<select name="regionParentId" _value="${(param.regionParentId)!}" id="region">
								
							</select>
						</div>
						<div>板块：
							<select name="regionRegionId" _value="${(param.regionRegionId)!}" id="plate">
								
							</select>
						</div>
						<div>楼盘<input type="text" name="residenceName" value="${(param.residenceName)!}" id="residenceName"/><span style="color:red;">（楼板名称：静安新城 或者 静安）</span></div>
						<div>栋（号）<input type="text" name="buildingInfo" value="${(param.buildingInfo)!}"/></div>
						<div>单元（室）<input type="text" name="cellInfo" value="${(param.cellInfo)!}"/></div>
						<div>
							总价：<input type="text" name="salePriceGT" value="${(param.salePriceGT)!}"/>-<input type="text" name="salePriceLE" value="${(param.salePriceLE)!}"/>
							<span style="color:red;">（总价范围：1000 - 2000 代表大于1000，小于等于2000）</span>
						</div>
						<div>
							面积：<input type="text" name="propertyAreaGT" value="${(param.propertyAreaGT)!}"/>-<input type="text" name="propertyAreaLE" value="${(param.propertyAreaLE)!}"/>
							<span style="color:red;">（范围：1000 - 2000 代表大于1000，小于等于2000）</span>	
						</div>
						<div style="display:none;">房型：<input type="text" name="shi" value="${(param.shi)!}"/>室<input type="text" name="ting" value="${(param.ting)!}"/>厅<input type="text" name="wei" value="${(param.wei)!}"/>卫</div>
						<div style="display:none;">房源状态
						 	<label for="validStatus">有效</label><input type="radio" name="status" value="1" id="validStatus" <#if param.status?? && param.status == 1>checked= "checked"</#if>/>
						 	<label for="invalidStatus">无效</label><input type="radio" name="status" value="0" id="invalidStatus" <#if param.status?? && param.status == 0>checked= "checked"</#if>/>（无登盘人）
					 	</div>
						<div style="display:none;">租
							<select name="rentStatus" value="${(param.rentStatus)!}" id="rentStatus">
							  <option value="" >选择</option>
							  <option value="1"	<#if param.rentStatus?? && param.rentStatus == 1>selected= "selected"</#if>>在租</option>
							  <option value="2"	<#if param.rentStatus?? && param.rentStatus == 2>selected= "selected"</#if>>不租</option>
							  <option value="3"	<#if param.rentStatus?? && param.rentStatus == 3>selected= "selected"</#if>>已租</option>
							</select>
							售
							<select name="saleStatus" value="${(param.saleStatus)!}" id="saleStatus">
							  <option value="" >选择</option>
							  <option value="1"	<#if param.saleStatus?? && param.saleStatus == 1>selected= "selected"</#if>>在售</option>
							  <option value="2"	<#if param.saleStatus?? && param.saleStatus == 2>selected= "selected"</#if>>不售</option>
							</select>	
						</div>
						<br/>
						<div>
							<input type="submit" value="搜索"/>
							<input type="button" value="添加出售房源" onclick="window.open('/soldHouseEdit.controller')" />
						</div>
				</div>
				<div>
					<table>
						<tr>
							<th style="display:none;"></th><th>操作</th><th>状态</th><th>楼盘</th>
							<!--<th>栋座</th><th>房号</th><th>楼层</th>--><th>栋座</th>
							<th>朝向</th><th>房型</th><th>面积</th><th>售价</th><th>租价</th><th>单价</th><th>钥匙</th><th>更新内容</th>
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
								<td>
									<a href="soldHouseDetail.controller?houseId=${(house.id)!}" target="_blank">查看</a>
									<a href="soldHouseEdit.controller?houseId=${(house.id)!}" target="_blank">编辑</a>
								</td>
								<td>
									<#if house.saleSaleStatus??><@spring.message "house.sale.saleStatus.${house.saleSaleStatus}"/></#if>/<#if house.rentRentStatus??><@spring.message "house.rent.rentStatus.${house.rentRentStatus}"/></#if>
								</td>
								<td>${(house.residenceName)!}</td>
								<!--
								<td>${(house.buildingPrefix)!}${(house.buildingCodeBegin)!}${(house.buildingSuffix)!}</td>
								<td>${(house.cellFloorBegin)!}${(house.cellCodeBegin)!}</td>
								<td>${(house.cellFloorBegin)!}</td>
								-->
								<td>${(house.soldBuildingInfo)!}</td>
								<td><#if house.direction??><@spring.message "house.house.direction.${house.direction}"/></#if></td>
								<td>${(house.roomType)!}</td>
								<td>${(house.propertyArea)!}</td>
								<td>${(house.salePrice)!}</td>
								<td>${(house.rentPrice)!}</td>
								<td>${(house.saleAvgPrice)!}</td>
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
				
				refreshRegionList([$("#region").attr("_value"), $("#plate").attr("_value")]);
				
				if($('#invalidStatus').attr('checked')){
					$('#saleStatus').val('');
					$('#rentStatus').val('');
					$('#saleStatus').attr('disabled', 'disabled');
					$('#rentStatus').attr('disabled', 'disabled');
				}
			}
			
			// bind
			function bind(){
				$('#city').change(function(){
					refreshRegionList();
				});
				$('#region').change(function(){
					refreshPlateList();
				});
				$('#validStatus').click(function(){
					$('#saleStatus').removeAttr('disabled');
					$('#rentStatus').removeAttr('disabled');
				});
				$('#invalidStatus').click(function(){
					$('#saleStatus').val('');
					$('#rentStatus').val('');
					$('#saleStatus').attr('disabled', 'disabled');
					$('#rentStatus').attr('disabled', 'disabled');
				});
				$('.addFav').bind("click", function(){
					var houseId = $(this).closest('tr').attr('_hId');
					addFav(houseId, $(this));
				});
				$('.delFav').bind("click", function(){
					var houseId = $(this).closest('tr').attr('_hId');
					delFav(houseId, $(this));
				});
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
			
			function refreshRegionList(selectedOpts){
				if($("#city").find("option:selected").val() > 0){
					$.ajax({
						type: "post",
						url: "ajax/getRegionList.controller",
						data: {},
						dataType: "json",
						contentType:'application/x-www-form-urlencoded; charset=UTF-8',
						success: function (data) {		
							var total = '<option value="">选择</option>';
							for(var i in data.bizData){
								var option;
								if(typeof(selectedOpts) !== 'undefined' && typeof(selectedOpts[0]) !== 'undefined'  && selectedOpts[0] == data.bizData[i].id )
									option = '<option value=' + data.bizData[i].id + ' selected="selected">' + data.bizData[i].name + '</option>';
								else
									option = '<option value=' + data.bizData[i].id + '>' + data.bizData[i].name + '</option>';
								total += option;
							}
							$('#region').html(total);
							//console.debug(selectedOpts);
							refreshPlateList(selectedOpts);
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	});
			  	}
			}
			
			function refreshPlateList(selectedOpts){
				if($("#region").find("option:selected").val() > 0){
				  	$.ajax({
						type: "post",
						url: "ajax/getPlateList.controller",
						data: {parentId: $("#region").find("option:selected").val()},
						dataType: "json",
						contentType:'application/x-www-form-urlencoded; charset=UTF-8',
						success: function (data) {		
							var total = '<option value="">选择</option>';
							for(var i in data.bizData){
								var option; 
								if(typeof(selectedOpts) !== 'undefined' && typeof(selectedOpts[1]) !== 'undefined'  && selectedOpts[1] == data.bizData[i].id )
									option = '<option value=' + data.bizData[i].id + ' selected="selected">' + data.bizData[i].name + '</option>';
								else
									option = '<option value=' + data.bizData[i].id + '>' + data.bizData[i].name + '</option>';
								total += option;
							}
							$('#plate').html(total);		
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	}); 
			  	}
		  	}	
		  	function addFav(houseId, dom){
		  		if(houseId > 0){
				  	$.ajax({
						type: "post",
						url: "ajax/addToFavorite.controller",
						data: {houseId: houseId},
						dataType: "json",
						contentType:'application/x-www-form-urlencoded; charset=UTF-8',
						success: function (data) {	
							dom.unbind("click");
							dom.click(function(){
								var houseId = $(this).closest('tr').attr('_hId');
								delFav(houseId, $(this));
							});
							dom.html("取消");
							dom.attr("class", "delFav");
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	}); 
			  	}
		  	}
		  	function delFav(houseId, dom){
		  		if(houseId > 0){
				  	$.ajax({
						type: "post",
						url: "ajax/removeFavorite.controller",
						data: {houseId: houseId},
						dataType: "json",
						contentType:'application/x-www-form-urlencoded; charset=UTF-8',
						success: function (data) {		
							dom.unbind("click");
							dom.click(function(){
								var houseId = $(this).closest('tr').attr('_hId');
								addFav(houseId, $(this));
							});
							dom.html("收藏");
							dom.attr("class", "addFav");
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	}); 
			  	}
		  	}
		</script>
	</body>
</html>
