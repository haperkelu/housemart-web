<html>
	<head></head>	
	<title>
		查看房源信息
	</title>
	<body>
		<h2>
			查看房源信息
		</h2>
		<div>
			<input type="hidden" name="houseId" id="houseId" value="${(house.id)!}"/>
			<input type="hidden" name="sort" id="sort" value="${sort!}"/>
			<table class="table">
				<tr>
					<td style="width:120px;">
						编号
					</td>
					<td>
						${(house.id)!}
					</td>			
				</tr>
				<#if isAdmin?exists && isAdmin == true>
					<tr>
						<td>
							迁移
						</td>
						<td>
							小区名或拼音：
							<input type="text" name="residenceName" value="" />
							<input type="button" value="搜索" onclick="refreshResidenceList(false)"/>
							
							<p id="residences">
				
							</p>										
			
						</td>
					</tr>				
				</#if>
				<tr>
					<td>
						<span style="color:red;">*</span> 小区
					</td>
					<td>
						${(house.residenceName)!}
					</td>			
				</tr>
				<tr>
					<td>
						<span style="color:red;">*</span> 具体名称
					</td>
					<td>
						${(house.detailName)!}
					</td>			
				</tr>
				<tr>
					<td>
						<span style="color:red;">*</span> 类型
					</td>
					<td>
						<span id="house-type-txt">${(house.houseType)!}</span>
					</td>
				</tr>
				<tr id="building-cell-info">
					<td>
						<span style="color:red;">*</span> 栋座单元
					</td>
					<td>
						${(house.buildingNo)!'xxx'} 栋（号）
						${(house.cellNo)!'xxx'} 单元（室）
					</td>			
				</tr>
				<tr>
					<td>
						<span style="color:red;">*</span> 产证面积
					</td>
					<td>
						${(house.propertyArea)!}平米
					</td>
				</tr>
				<tr>
					<td>
						占地面积
					</td>
					<td>
						${(house.occupiedArea)!}平米
					</td>
				</tr>
				<#if (house.saleSaleStatus!0) == 1>
				<tr>
					<td>
						<span style="color:red;">*</span> 价格
					</td>
					<td>
						${((house.salePrice!0)/10000)!}万元
					</td>
				</tr>
				</#if>
				<#if (house.rentRentStatus!0) == 1>
				<tr>
					<td>
						<span style="color:red;">*</span> 租金
					</td>
					<td>
						${(house.rentPrice)!}元/月
					</td>
				</tr>
				</#if>
				
				<tr>
					<td>
						<span style="color:red;">*</span> 房型
					</td>
					<td>
						<span id="room-type-txt">${(house.roomType)!}</span>
					</td>
				</tr>
				
				<tr>
					<td>
						<span style="color:red;">*</span> 楼层
					</td>
					<td>
						<!--
						<span id="floor-txt">${(house.floor)!}</span>
						-->
						<#include "/common/floorSelect.ftl">
					</td>
				</tr>
				
				<#if (house.rentRentStatus!0) == 1>
				<tr>
					<td>
						<span style="color:red;">*</span> 入住时间
					</td>
					<td>
						<#if house.rentDealTime??>
						${house.rentDealTime?string("yyyy-MM-dd")}
						</#if>
					</td>
				</tr>
				</#if>
				
				<tr>
					<td>
						<span style="color:red;">*</span> 装修
					</td>
					<td>
						<#if house.decorating?? && house.decorating == 5>豪装</#if>
						<#if house.decorating?? && house.decorating == 1>精装</#if>
						<#if house.decorating?? && house.decorating == 2>简装</#if>
						<#if house.decorating?? && house.decorating == 3>毛坯</#if>
						<#if house.decorating?? && house.decorating == 4>清水</#if>
					</td>
				</tr>
				
				<tr>
					<td>
						<span style="color:red;">*</span> 朝向
					</td>
					<td>
						<#if house.direction?? && house.direction == 1000>东</#if>
						<#if house.direction?? && house.direction == 100>南</#if>
						<#if house.direction?? && house.direction == 10>西</#if>
						<#if house.direction?? && house.direction == 1>北</#if>
						<#if house.direction?? && house.direction == 1100>东南</#if>
						<#if house.direction?? && house.direction == 1001>东北</#if>
						<#if house.direction?? && house.direction == 110>西南</#if>
						<#if house.direction?? && house.direction == 11>西北</#if>
						<#if house.direction?? && house.direction == 101>南北</#if>
						<#if house.direction?? && house.direction == 1010>东西</#if>
					</td>
				</tr>
				
				<tr>
					<td>
						<span style="color:red;">*</span> 建房时间
					</td>
					<td>
						<#if house.buildTime??>
						${house.buildTime?string("yyyy")}
						</#if>
					</td>
				</tr>
				
				<#if (house.rentRentStatus!0) == 1>
				<tr>
					<td>
						<span style="color:red;">*</span> 租房配置
					</td>
					<td>
						<#if rentEquipments.water??>水</#if>
						<#if rentEquipments.power??>电</#if>
						<#if rentEquipments.gas??>煤气</#if>
						<#if rentEquipments.heat??>暖气</#if>
						<#if rentEquipments.cable??>有线电视</#if>
						<#if rentEquipments.network??>宽带</#if>
						<#if rentEquipments.tv??>电视</#if>
						<#if rentEquipments.refrigerator??>冰箱</#if>
						<#if rentEquipments.airCondition??>空调</#if>
						<#if rentEquipments.washer??>洗衣机</#if>
						<#if rentEquipments.waterHeater??>热水机</#if>
						<#if rentEquipments.microwave??>微波炉</#if>
						<#if rentEquipments.telephone??>电话</#if>
					</td>
				</tr>
				</#if>
				
				<tr>
					<td>
						特殊说明
					</td>
					<td>
						<#if (house.rentRentStatus!0) == 1>
							付
							<#if house.memo?? && house.memo?length gte 2>
							${house.memo?substring(1,2)}
							</#if>
							押
							<#if house.memo?? && house.memo?length gte 4>
							${house.memo?substring(3,4)}
							</#if>
						</#if>
						<#if (house.saleSaleStatus!0) == 1>
							${house.memo!}
						</#if>
					</td>
				</tr>
				
				<#if (house.saleSaleStatus!0) == 1>
				<tr>
					<td>
						房源标签
					</td>
					<td>
						${(house.saleTagList)!}
					</td>
				</tr>
				</#if>
				
				<#if (house.rentRentStatus!0) == 1>
				<tr>	
					<td>
						房源标签
					</td>
					<td>
						${(house.rentTagList)!}
					</td>
				</tr>
				</#if>
				
				<tr>
					<td>
					</td>
					<td>
						<a href="javascript:void(0)" onclick="assignHouse(${(house.id)!})">
							[分配]
						</a>
					</td>
				</tr>
			</table>
			<div>房源图片</div>
			<div class="view-3">
			</div>
			<div>房型图片</div>
			<div class="view-2">
			</div>
			<div>小区图片</div>
			<div class="view-0">
			</div>
			<div class="view-1" style="display:none;">
			</div>
			
			<div id="dlg-move-house" title="迁移到其它小区">
				<input type="hidden" id="destResidenceId"/>
			    <div class="dlg-content">
			    	确定要把当前房源迁移到<span id="destResidenceName" style="color:red;"></span>？
			    </div>
			    <div class="dlg-buttons">
			    	<input type="button" value="确定" onclick='moveToResidence();'/>
			    	<input type="button" value="取消" onclick='$("#dlg-move-house").dialog("close");'/>
			    </div>
			</div>
		</div>
		
		<div id="dlg-assign-house" title="分配房源">
		    <div class="dlg-content">
		    	为房源#<span id="dlg-assign-house-no"></span>分配经纪人
		    	<select id="dlg-assign-broker">
		    		<option value="">选择经纪人</option>
		    		<#list accountList as account>
		    		<option value="${(account.id)!}">${(account.name)}</option>
		    		</#list>
		    	</select>
		    </div>
		    <div class="dlg-buttons">
		    	<input type="button" value="确定" onclick='doAssignHouse()' />
		    	<input type="button" value="取消" onclick='$("#dlg-assign-house").dialog("close")'/>
		    	<input type="hidden" value="" id="dlg-assign-house-id" />
		    </div>
		</div>
		
		<style>
			#residences span {float:left;margin:5px 20px 5px 0;cursor:pointer;
				border:1px dashed #aaa;padding:2px 5px;}
				
			#selectedResidences span {float:left;margin:5px 20px 5px 0;cursor:pointer;
				border:1px solid #aaa;padding:2px 5px;}
				
			#selectedResidences span input {display:hidden;}
		</style>
		<script>
			$(document).ready(function(){
				init();
				bind();
				exec();
			});
			
			// init
			function init(){
			
				showHouseType();
				showRoomType();
				showFloor();
				
				$("#dlg-assign-house").dialog(
					{
						autoOpen: false,
						width: 500,
						modal: true,
						resizable: false,
					});
				
				refreshHousePicList(3);
				refreshHousePicList(2);
				refreshHousePicList(0);
				refreshHousePicList(1);
				
				$("#floor").attr("disabled", "disabled");
				
				$("#dlg-move-house").dialog(
					{
						autoOpen: false,
						width: 500,
						modal: true,
						resizable: false
					});
			}
			
			// bind
			function bind(){
				
			}
			
			function exec(){
				//TODO:exec
			}
			
			function refreshHousePicList(picType){
			  	$.ajax({
					type: "post",
					url: "/ajax/getPicListByHouseId.controller",
					data: {houseId: $('#houseId').val(),type: picType, sort: $('#sort').val()},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {	
						var html =  data.bizData.length > 0 ? '' : '暂无图片';
						for(var i in data.bizData){
							var pic; 
							pic =  '<p class="pic" _pId="' + data.bizData[i].id + '" style="float:left;margin:10px 20px 10px 0;">' 
								+ '<img src="' + data.bizData[i].cloudUrl + '" width="280" height="210" /></br>'
								/*
								+  '<a href="javascript:move(\''  + data.bizData[i].id + '\', \'up\');">上移</a>&nbsp;&nbsp;'
								+  '<a href="javascript:move(\''  + data.bizData[i].id + '\', \'down\');">下移</a>&nbsp;&nbsp;'
								*/
								+  '<a href="javascript:removePic(' + data.bizData[i].id + ')" onclick="return confirm(\'是否将此图片删除?\')">删除此图片</a>'
								+ '</p>';
							html += pic;
						}
						html += '<div style="clear:both"></div>';
						$('.view-' + picType).html(html);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	}); 
		  	}
			
			function removePic(picId){
				
				$.ajax({
					type: "get",
					url: "/ajax/removePic.controller",
					data: {id: picId},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						refreshHousePicList(3);
						refreshHousePicList(2);
						refreshHousePicList(0);
						refreshHousePicList(1);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
						alert("删除失败");
					}
			  	}); 
			  	
			}
			
			function checkHouseType()
			{
			}
			
			function showHouseType()
			{
				var index = parseInt($("#house-type-txt").html(), 10);
				if (index > 0)
				{
					var type = "";

					switch (index)
					{
						case 2:
							type = "联排";
							break;
						case 3:
							type = "新里";
							break;
						case 4:
							type = "公寓";
							break;
						case 5:
							type = "老公房";
							break;
						case 7:
							type = "独栋";
							break;
						case 8:
							type = "双拼";
							break;
						case 9:
							type = "叠加";
							break;
						case 10:
							type = "洋房";
							break;
						default:
							break;
					}
					$("#house-type-txt").html(type);
				}
			}
			
			function showRoomType()
			{
				var raw_type = $("#room-type-txt").html();
				if (raw_type != null && raw_type.length == 4)
				{
					var type = raw_type.charAt(0) + "室" +
						raw_type.charAt(1) + "厅" +
						raw_type.charAt(2) + "卫" +
						raw_type.charAt(3) + "阳台";
					 $("#room-type-txt").html(type);
				}
			}
			
			function showFloor()
			{
				var index = parseInt($("#floor-txt").html(), 10);
				if (index > 0)
				{
					var floor = "";
					switch (index)
					{
						case 1:
							floor = "低层";
							break;
						case 2:
							floor = "中低层";
							break;
						case 3:
							floor = "中层";
							break;
						case 4:
							floor = "中高层";
							break;
						case 5:
							floor = "高层";
							break;
						default:
							break;
					}
					$("#floor-txt").html(floor);
				}
			}
			
			function assignHouse(id)
			{
				$("#dlg-assign-house-no").html(id);
				$("#dlg-assign-house").dialog("open");
				$("#dlg-assign-house-id").val(id);
			}
			
			function doAssignHouse()
			{
				var house_id = $("#dlg-assign-house-id").val();
				var broker_id = $("#dlg-assign-broker").val();
				$.ajax({
					type: "post",
					url: "/ajax/assignHouse.controller",
					data: {id: house_id, brokerId: broker_id},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						location.href = "/myExternalHouseList.controller?sysMsg=房源已分配.";
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
			}
		
			function refreshResidenceList(selectedOpt){
	
				$('#residences').html("数据加载中...");
				if (selectedOpt)
				{
					if($("#plate").find("option:selected").val() > 0 ||
							$("#region").find("option:selected").val() > 0)
					{
						$("input[name='residenceName']").val("");
					  	$.ajax({
							type: "post",
							url: "/ajax/getResidenceListByPlateId.controller",
							data: {plateId: $("#plate").find("option:selected").val(), regionId: $("#region").find("option:selected").val()},
							dataType: "json",
							contentType:'application/x-www-form-urlencoded; charset=UTF-8',
							success: function (data) {
								showResidenceList(data);
							},
							error: function (XMLHttpRequest, textStatus, errorThrown) {
							}
					  	});
				  	}
				}
				else
				{
					var key = $.trim($("input[name='residenceName']").val());
					if (key != "")
					{
						$('#region').val("");
						$('#plate').val("");
						 
						$.ajax({
							type: "post",
							url: "/ajax/getResidenceListByResidenceName.controller",
							data: {residenceName: key},
							dataType: "json",
							contentType:'application/x-www-form-urlencoded; charset=UTF-8',
							success: function (data) {
								showResidenceList(data);
							},
							error: function (XMLHttpRequest, textStatus, errorThrown) {
							}
					  	});
					}
				}
			}
			
			function showResidenceList(data)
			{
				var total = '';
				var index = '';
				var indexList = new Array();
				for(var i in data.bizData){
					var alpha = data.bizData[i].pinyinName.toUpperCase().charAt(0).toUpperCase();
					
					if (alpha.charCodeAt(0) < 65 || alpha.charCodeAt(0) > 90)
					{
						alpha = "";
					}
					
					var option = '<span class="region-residence index-' + alpha + '" id="res-' + data.bizData[i].residenceId + '" onclick="addAccountResidence(' + data.bizData[i].residenceId + ',\'' + data.bizData[i].residenceName + '\')">' + 
					(data.bizData[i].pinyinName != null ? alpha + " " : "") + 
					data.bizData[i].residenceName + '</span>';
					total += option;
					
					if ($.inArray(alpha, indexList) < 0)
					{
						indexList.push(alpha);
					}
					indexList.sort();
				}
				index += '<div class="residence-index">';
				for (var i in indexList)
				{
					var alpha = indexList[i];
					if (alpha == "")
					{
						alpha = "其它";
					}
					
					index += '<a href="javascript:void(0)" onclick="showResidenceAlphaList(this)">' + alpha + '</a> ';
				}
				index += '</div>';
				$('#residences').html(index + total);
				$(".region-residence").hide();
				if (indexList.length > 0)
				{
					$(".index-" + indexList[0]).show();
				}
				
				markSelectedResidences();
			}
			
			function showResidenceAlphaList(obj)
			{
				$(".region-residence").hide();
				var alpha = $(obj).html();
				if (alpha == "其它")
				{
					alpha = "";
				}
				$(".index-" + alpha).show();;
			}
			
			function markSelectedResidences()
			{
				$(".region-residence").each(function(){
					var id = $(this).attr("id");
					id = id.split("-");
					id = id[1];
					if ($("#select-res-" + id).length > 0)
					{
						$(this).css("color", "red");
					}
					else
					{
						$(this).css("color", "black");
					}
				});
			}
			
			function addAccountResidence(id, name)
			{
				$("#destResidenceId").val(id);
				$("#destResidenceName").html(name);
				$("#dlg-move-house").dialog("open");		
			}
		
			function moveToResidence(){
				var destResidenceId = $("#destResidenceId").val();
				var houseId = $("#houseId").val(); 
				$.ajax({
					type: "post",
					url: "/ajax/changeResidence.controller",
					data: {residenceId: destResidenceId, houseId: houseId},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						alert("迁移成功");
						location.reload();
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});		
			}	
		</script>
	</body>
</html>