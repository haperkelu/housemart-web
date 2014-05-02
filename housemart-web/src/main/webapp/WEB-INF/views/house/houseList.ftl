<html>
	<head>
	</head>	
	<title>我的盘源</title>
	<body>
		<div style="width:900px;">
			<form action="/houseList.controller" method="get" id="houseListForm" enctype="x-www-form-urlencoded" onsubmit="$('#residenceName').val(encodeURI($('#maskName').val()));$('#creatorName').val(encodeURI($('#creatorMaskName').val()));">
				<input type="hidden" value="${(param.totalCount)!0}" id="totalCount"/>
				<input type="hidden" name="page" value="${(param.page)!}" id="page"/>
				<input type="hidden" name="pageSize" value="${(param.pageSize)!}" id="pageSize"/>
				<div>
						<div>房源类型
						 	<label for="sourceTypeInternal">内部房源</label><input type="radio" name="sourceTypeIn" value="1,2" id="sourceTypeInternal" <#if param.sourceTypeIn?? && param.sourceTypeIn == '1,2'>checked= "checked"</#if>/>
						 	<label for="sourceTypeExternal">合作房源</label><input type="radio" name="sourceTypeIn" value="3" id="sourceTypeExternal" <#if param.sourceTypeIn?? && param.sourceTypeIn == '3'>checked= "checked"</#if>/>
					 	</div>
						<div>
							城市
							<select name="regionCityId" value="${(param.regionCityId)!}" id="city">
								<option value="" selected="selected">选择</option>
								<option value="1" <#if param.regionCityId?? && param.regionCityId == 1>selected= "selected"</#if>>上海</option>
								<option value="2" <#if param.regionCityId?? && param.regionCityId == 2>selected= "selected"</#if>>南加州</option>
								<option value="3" <#if param.regionCityId?? && param.regionCityId == 3>selected= "selected"</#if>>北加州</option>
							</select>
							区域
							<select name="regionParentId" _value="${(param.regionParentId)!}" id="region">
								
							</select>
							板块
							<select name="regionRegionId" _value="${(param.regionRegionId)!}" id="plate">
								
							</select>
						</div>
						<div>
							楼盘<input type="text" value="${(param.residenceName)!}" id="maskName"/><span style="color:red;">（楼板名称：静安新城 或者 静安）</span>
							<input type="hidden" name="residenceName" id="residenceName"/>
						</div>
						<div>栋（号）<input type="text" name="buildingInfo" value="${(param.buildingInfo)!}"/>
							  单元（室）<input type="text" name="cellInfo" value="${(param.cellInfo)!}"/>
						</div>
						<div>
							总价<input type="text" name="salePriceGT" value="${(param.salePriceGT)!}"/>-<input type="text" name="salePriceLE" value="${(param.salePriceLE)!}"/>
							<span style="color:red;">（总价范围：1000 - 2000 代表大于1000，小于等于2000）</span>
						</div>
						<div>
							面积<input type="text" name="propertyAreaGT" value="${(param.propertyAreaGT)!}"/>-<input type="text" name="propertyAreaLE" value="${(param.propertyAreaLE)!}"/>
							<span style="color:red;">（范围：1000 - 2000 代表大于1000，小于等于2000）</span>	
						</div>
						<div>房型<input type="text" name="shi" value="${(param.shi)!}"/>室<input type="text" name="ting" value="${(param.ting)!}"/>厅<input type="text" name="wei" value="${(param.wei)!}"/>卫</div>
						<div>房源状态
						 	<label for="validStatus">有效</label><input type="radio" name="status" value="1" id="validStatus" <#if param.status?? && param.status == 1>checked= "checked"</#if>/>
						 	<label for="invalidStatus">无效</label><input type="radio" name="status" value="0" id="invalidStatus" <#if param.status?? && param.status == 0>checked= "checked"</#if>/>（无登盘人）
						 	<label for="rejectStatus">拒绝</label><input type="radio" name="status" value="2" id="rejectStatus" <#if param.status?? && param.status == 2>checked= "checked"</#if>/>
					 	</div>
						<div>
							登盘人<input type="text" value="${(param.creatorName)!}" id="creatorMaskName"/><span style="color:red;">（经纪人姓名：小王）</span>
							<input type="hidden" name="creatorName" id="creatorName"/>
						</div>
						<div>租
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
							出售意向
							<select name="saleIntention" value="${(param.saleIntention)!}" id="saleIntention" class="toEncode">
							  <option value="" >选择</option>
							  <option value="" <#if param.saleIntention?? && param.saleIntention == '无'>selected= "selected"</#if>>无</option>
							  <option value="" <#if param.saleIntention?? && param.saleIntention == '不卖'>selected= "selected"</#if>>不卖</option>
							  <option value="" <#if param.saleIntention?? && param.saleIntention == '非业主/错号'>selected= "selected"</#if>>非业主/错号</option>
							  <option value="" <#if param.saleIntention?? && param.saleIntention == '要卖，价格清楚'>selected= "selected"</#if>>要卖，价格清楚</option>
							  <option value="" <#if param.saleIntention?? && param.saleIntention == '要卖，价格不清楚'>selected= "selected"</#if>>要卖，价格不清楚</option>
							  <option value="" <#if param.saleIntention?? && param.saleIntention == '听不懂'>selected= "selected"</#if>>听不懂</option>
							</select>	
							出租意向
							<select name="rentIntention" value="${(param.rentIntention)!}" id="rentIntention" class="toEncode">
							    <option value="" >选择</option>
							    <option value="" <#if param.rentIntention?? && param.rentIntention == '无'>selected= "selected"</#if>>无</option>
								<option value="" <#if param.rentIntention?? && param.rentIntention == '不租'>selected= "selected"</#if>>不租</option>
								<option value="" <#if param.rentIntention?? && param.rentIntention == '要租'>selected= "selected"</#if>>要租</option>
								<option value="" <#if param.rentIntention?? && param.rentIntention == '已租'>selected= "selected"</#if>>已租</option>
							</select>	
							外拨结果
							<select name="dialResult" value="${(param.dialResult)!}" id="dialResult" class="toEncode">
							  <option value="" >选择</option>
							  <option value="" <#if param.dialResult?? && param.dialResult == '未知'>selected= "selected"</#if>>未知</option>
							  <option value="" <#if param.dialResult?? && param.dialResult == '挂断'>selected= "selected"</#if>>挂断</option>
							  <option value="" <#if param.dialResult?? && param.dialResult == '空号'>selected= "selected"</#if>>空号</option>
							  <option value="" <#if param.dialResult?? && param.dialResult == '无人应答'>selected= "selected"</#if>>无人应答</option>
							  <option value="" <#if param.dialResult?? && param.dialResult == '接通并跟进'>selected= "selected"</#if>>接通并跟进</option>
							</select>
						</div>
						<div>联系电话<input type="text" name="phone" value="${(param.phone)!}" id="phone"/><span style="color:red;">（电话号码：1358888 或者 888）</span></div>
						<br/>
						<div>
							<input type="submit" value="搜索" />
							<input type="button" value="我要登盘" onclick="window.open('/houseEdit.controller')" />
						</div>
						<br/>
						<div>
							共搜索到<span style="color:red;">${(param.totalCount)!0}</span>个房源，本页显示<span style="color:red;">${(param.pageCount)!0}</span>个房源
						</div>
						<br/>
				</div>
				<div style="width:900px;">
					<table>
						<tr>
							<th></th><th></th><th>操作</th><th>序号</th><th>登盘人</th><th>状态</th><th>楼盘</th><th>栋座</th><th>房号</th><th>楼层</th><th>迈信栋座</th><th>迈信房号</th><th>外网栋座</th><th>外网房号</th><th>朝向</th><th>房型</th><th>面积</th><th>售价（万）</th><th>租价（元）</th><th>单价（万）</th><th>钥匙</th><th>更新内容</th><th>审核时间</th>
						</tr>
						<#if houseList??>
							<#list houseList as house> 
							<tr _hId="${(house.id)!}">
								<td>
									<#if house.sourceType?? && house.sourceType == 3>
										<span style="color:red;">【合】</span>
									<#else>
										<span style="color:red;">【内】</span>
									</#if>
								</td>
								<td>
									<#if house.favoriteId??>
										<a href="javascript:;" class="delFav">取消</a>
									<#else>
										<a href="javascript:;" class="addFav">收藏</a>		
									</#if>
								</td>
								<td><a href="houseEdit.controller?houseId=${(house.id)!}" target="_blank">查看</a></td>
								<td>${(house.id)!}</td>
								<td>${(house.creatorName)!}</td>
								<td>
									<#if house.saleSaleStatus??><@spring.message "house.sale.saleStatus.${house.saleSaleStatus}"/><#else>无</#if>/<#if house.rentRentStatus??><@spring.message "house.rent.rentStatus.${house.rentRentStatus}"/><#else>无</#if>
								</td>
								<td>${(house.residenceName)!}</td>
								<td>${(house.buildingPrefix)!}${(house.buildingCodeBegin)!}${(house.buildingSuffix)!}</td>
								<td>${(house.cellFloorBegin)!}${(house.cellCodeBegin)!}</td>
								<td>${(house.cellFloorBegin)!}</td>
								<td>${(house.maxinBuildingNo)!}</td>
								<td>${(house.maxinCellNo)!}</td>
								<td>${(house.buildingNo)!}</td>
								<td>${(house.cellNo)!}</td>
								<td><#if house.direction??><@spring.message "house.house.direction.${house.direction}"/></#if></td>
								<td>${(house.roomType)!}</td>
								<td>${(house.propertyArea)!}</td>
								<td>${(house.salePrice?number/10000)!}</td>
								<td>${(house.rentPrice)!}</td>
								<td><#if house.propertyArea??>${(house.salePrice?number/house.propertyArea?number/10000)!}</#if></td>
								<td><#if house.hasKey??><@spring.message "house.house.hasKey.${house.hasKey}"/></#if></td>
								<td></td>
								<td><#if house.auditTime??>${house.auditTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
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
				
				//console.debug($("#region").attr("_value") + ' ' + $("#plate").attr("_value"));
				refreshRegionList([$("#region").attr("_value"), $("#plate").attr("_value")]);
				
				if($('#rejectStatus').attr('checked') ||   $('#invalidStatus').attr('checked')){
					$('#saleStatus').val('');
					$('#rentStatus').val('');
					$('#saleStatus').attr('disabled', 'disabled');
					$('#rentStatus').attr('disabled', 'disabled');
				}
				
				$(".toEncode option").each(function(){
					if($(this).text() != '选择')
						$(this).val(encodeURI($(this).text()));
				});
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
				$('#rejectStatus, #invalidStatus').click(function(){
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
					var cityId = $("#city").find("option:selected").val();
					$.ajax({
						type: "post",
						url: "ajax/getRegionListByCityId.controller",
						data: {cityId:cityId},
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
