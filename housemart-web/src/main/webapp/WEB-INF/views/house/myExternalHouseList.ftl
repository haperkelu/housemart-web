<html>
	<head>
	</head>	
	<title>我的外网盘源</title>
	<body>
		<div style="margin-bottom:10px;line-height:20px;">
			租售 :
			<select name="saleRent" id="saleRent">
				<option ${(saleRent == 0) ? string('selected="selected"', '')} value="0">全部</option>
				<option ${(saleRent == 1) ? string('selected="selected"', '')} value="1">出售</option>
				<option ${(saleRent == 2) ? string('selected="selected"', '')} value="2">出租</option>
			</select>
			状态:
			<select name="status" id="status">
				<option ${((status!100) == 100) ? string('selected="selected"', '')} value="">全部</option>
				<option ${((status!100) == 1) ? string('selected="selected"', '')} value="1">上架</option>
				<option ${((status!100) == 0) ? string('selected="selected"', '')} value="0">审核中/草稿</option>
				<option ${((status!100) == 2) ? string('selected="selected"', '')} value="2">拒绝</option>
				<option ${((status!100) == -2) ? string('selected="selected"', '')} value="-2">下架</option>
				<option ${((status!100) == -1) ? string('selected="selected"', '')} value="-1">删除</option>
			</select>
			<br/>
			<#if accountType == 'manager'>
			区域:
			<select name="regionId" value="" id="regionId">
				<option value="">选择区域</option>
				<#if regions??>
					<#list regions as accountResidence>
				<option value="${accountResidence.residenceID}">
					${accountResidence.residencePinyinName?substring(0,1)?upper_case}
					${accountResidence.residenceName}
				</option>
					</#list>
				</#if>
			</select>
			板块
			<select name="plateId" value="" id="plateId">
				<option value="">选择板块</option>
				<#if plates??>
					<#list plates as accountResidence>
				<option value="${accountResidence.residenceID}">
					${accountResidence.residencePinyinName?substring(0,1)?upper_case}
					${accountResidence.residenceName}
				</option>
					</#list>
				</#if>
			</select>
			</#if>
			小区名称：<input id="residenceName" type="text" name="residenceName" value="${(param.residenceName)!}" />
			<#if accountType != 'broker'>
			登盘人姓名：
			<input id="creatorName" type="text" name="creatorName" value="${(param.creatorName)!}" />
			<a href="javascript:void(0)" onclick="filterHouses(0)">
				[搜索]
			</a>
			
			<a href="javascript:void(0)" onclick="assignHouse()">
				[分配]
			</a>
			
			<a href="javascript:void(0)" onclick="deactiveHouse()">
				[下架]
			</a>
			</#if>
			
		</div>
		<div>
			<input type="hidden" value="${(param.totalCount)!0}" id="totalCount"/>
			<input type="hidden" name="page" value="${(param.page)!}" id="page"/>
			<input type="hidden" name="pageSize" value="${(param.pageSize)!}" id="pageSize"/>
	
			<div>
				<table>
					<tr>
						<th style="width:30px;"><input id="selectAll" type="checkbox" /></th>
						<th>操作</th>
						<th>编号</th>
						<th>有效</th>
						<th>状态</th>
						<th>楼盘</th>
						<th>栋座</th>
						<th>房号</th>
						<th>楼层</th>
						<th>朝向</th><th>房型</th><th>面积</th><th>售价</th><th>租价</th><th>单价</th>
						<th>钥匙</th><th>归属人</th>
					</tr>
					<#if houseList??>
						<#list houseList as house> 
						<tr _hId="${(house.id)!}">
							<td><input class="checkHouseId" type="checkbox" value="${(house.id)!}" />
							<td><a href="houseView.controller?houseId=${(house.id)!}" target="_blank">查看</a></td>
							<td>${(house.id)!}</td>
							<td>
								<#if ((house.status!0) == 0) >
								审核中/草稿
								</#if>
								<#if ((house.status!0) == 1) >
								上架
								</#if>
								<#if ((house.status!0) == 2) >
								拒绝
								</#if>
								<#if ((house.status!0) == -1) >
								删除
								</#if>
								<#if ((house.status!0) == -2) >
								下架
								</#if>
							</td>
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
							<td>${(house.creatorName)!}</td>
						</tr>
						</#list>
					</#if>	
				</table>
			</div>
			<div id="pagination" class="pagination">
			</div>
		</div>
		
		<div id="dlg-assign-house" title="分配房源">
		    <div class="dlg-content">
		    	将所选房源分配给经纪人
		    	<select id="dlg-assign-broker">
		    		<option value="">选择经纪人</option>
		    		<#if accountList??>
		    		<#list accountList as account>
		    		<option value="${(account.id)!}">${(account.id)} - ${(account.name)}</option>
		    		</#list>
		    		</#if>
		    	</select>
		    </div>
		    <div class="dlg-content" style="text-align:right;">
		    	<input type="checkbox" id="dlg-assign-history" checked="checked" />
		    	迁移会话
		    </div>
		    <div class="dlg-buttons">
		    	<input type="button" value="确定" onclick='doAssignHouse()' />
		    	<input type="button" value="取消" onclick='$("#dlg-assign-house").dialog("close")'/>
		    </div>
		</div>
		
		<div id="dlg-deactive-house" title="下架房源">
		    <div class="dlg-content">
		    	将所选房源下架，下架理由
		    	<input id="dlg-deactive-comments" type="text" value="" />
		    </div>
		    <div class="dlg-buttons">
		    	<input type="button" value="确定" onclick='doDeactiveHouse()' />
		    	<input type="button" value="取消" onclick='$("#dlg-deactive-house").dialog("close")'/>
		    </div>
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
				
				$("#dlg-assign-house").dialog(
					{
						autoOpen: false,
						width: 500,
						modal: true,
						resizable: false,
					});
				$("#dlg-deactive-house").dialog(
					{
						autoOpen: false,
						width: 500,
						modal: true,
						resizable: false,
					});
			}
			
			// bind
			function bind(){
				$("#selectAll").click(function(){
					if (!$("#selectAll").is(":checked"))
					{
						$(".checkHouseId").attr("checked", false);
					}
					else
					{
						$(".checkHouseId").attr("checked", true);
					}
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
					filterHouses(page_index);
				}
				callbackCounter++;
			}
			
			function assignHouse()
			{
				$("#dlg-assign-house").dialog("open");
			}
			
			function doAssignHouse()
			{
				var houseIds = "";
				
				var assign_history = $("#dlg-assign-history").is(":checked") ? 1 : 0;
				
				$(".checkHouseId:checked").each(function(){
					houseIds += (houseIds == "" ? "" : ",") + $(this).val();
				});
				
				var broker_id = $("#dlg-assign-broker").val();
				$.ajax({
					type: "post",
					url: "/ajax/assignHouse.controller",
					data: {ids: houseIds, brokerId: broker_id, assignHistory: assign_history},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						location.href = "/myExternalHouseList.controller?sysMsg=房源已分配.";
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
			}
			
			function deactiveHouse()
			{
				$("#dlg-deactive-house").dialog("open");
			}
			
			function doDeactiveHouse()
			{
				var houseIds = "";
				
				$(".checkHouseId:checked").each(function(){
					houseIds += (houseIds == "" ? "" : ",") + $(this).val();
				});
				
				var deactive_comments = $("#dlg-deactive-comments").val();
				$.ajax({
					type: "post",
					url: "/ajax/deactiveHouse.controller",
					data: {ids: houseIds, comments: deactive_comments},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						location.href = "/myExternalHouseList.controller?sysMsg=房源已下架.";
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
			}
			
			function filterHouses(page)
			{
				var residenceName = $("#residenceName").val();
				var creatorName = $("#creatorName").val();
				var saleRent = $("#saleRent").val();
				var status = $("#status").val();
				
				location.href = "/myExternalHouseList.controller?residenceName=" + residenceName +
					"&creatorName=" + creatorName + "&page=" + page + "&saleRent=" + saleRent + "&status=" + status;
			}
		</script>
	</body>
</html>
