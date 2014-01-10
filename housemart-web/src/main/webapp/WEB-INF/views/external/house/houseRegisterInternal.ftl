<html>
	<head></head>	
	<title>
		发布房源 - 基本信息
	</title>
	<body>
		<h2>
			<#if (param.viewType!) == "sale">
			发布出售房
			</#if>
			<#if (param.viewType!) == "rent">
			发布出租房
			</#if>
		</h2>
		<ul class="page-nav">
			<li class="focus">1. 基本信息</li>
			<#if ((house.status!0) != 1)>
			<li>
				<#if ((house.id!0) == 0)>
				2. 上传图片
				<#else>
				<a href="/external/housePicConsole.controller?houseId=${(house.id)!}&picType=3">2.上传图片</a> 
				</#if>
			</li>
			<li>
				<#if ((house.id!0) == 0)>
				3. 完成
				<#else>
				<a href="/external/myHouseList.controller?tabIndex=2&sysMsg=房源信息已保存">
				3. 完成
				</a>
				</#if>
			</li>
			</#if>
		</ul>
		<div>
			<form action="/external/houseUpdate.controller" method="post" onsubmit="return checkExtHouseUpdateForm()">
				<input type="hidden" name="id" value="${(house.id)!}"/>
				<input type="hidden" name="viewType" value="${(param.viewType)!}"/>
				<table class="table">
					<tr>
						<td style="width:120px;">
							<span style="color:red;">*</span> 小区
						</td>
						<td>
							<#if ((house.status!0) == 1)>
							<select name="residenceId" value="${(house.residenceId)!}" id="residence">
								<#if ((house.id!0) == 0)>
								<option value="">选择小区</option>
								</#if>
							<#if accountResidences??>
								<#list accountResidences as accountResidence>
								<#if ((house.residenceId!0) == accountResidence.residenceID) >
								<option value="${accountResidence.residenceID}">${accountResidence.residenceName}</option>
								</#if>
								</#list>
							</#if>
							</select>
							<span id="msg-residenceId" class="msg"></span>
							<#else>
							<select name="residenceId" value="${(house.residenceId)!}" id="residence">
								<#if ((house.id!0) == 0)>
								<option value="">选择小区</option>
								</#if>
							<#if accountResidences??>
								<#list accountResidences as accountResidence>
								<#if ((house.residenceId!0) == accountResidence.residenceID) >
								<option selected="selected" value="${accountResidence.residenceID}">
									${accountResidence.residencePinyinName?substring(0,1)?upper_case}
									${accountResidence.residenceName}
								</option>
								<#else>
								<option value="${accountResidence.residenceID}">
									${accountResidence.residencePinyinName?substring(0,1)?upper_case}
									${accountResidence.residenceName}
								</option>
								</#if>
								</#list>
							</#if>
							</select>
							<span id="msg-residenceId" class="msg"></span>
							</#if>
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span> 具体名称
						</td>
						<td>
							<#if ((house.status!0) == 1)>
							${(house.detailName)!}
							<#else>
							<input type="text" name="detailName" value="<#if house.detailName??>${(house.detailName)!}</#if>"/>
							<span id="msg-detailName" class="msg"></span>
							</#if>
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span> 类型
						</td>
						<td>
							<#if ((house.status!0) == 1)>
							<span id="house-type-txt">${(house.houseType)!}</span>
							<#else>
							<#include "/common/houseTypeSelect.ftl">
							<span id="msg-houseType" class="msg"></span>
							</#if>
						</td>
					</tr>
					<tr id="building-info">
						<td>
							<span id="building-required" style="color:red;">*</span> 栋座
						</td>
						<td>
							<#if ((house.status!0) == 1)>
							${(house.buildingNo)!'xxx'} 栋（号）
							<#else>
							<input type="text" name="buildingNo" value="${(house.buildingNo)!}" style="width:50px;"/>		
						 	栋（号）
							<span id="msg-building" class="msg"></span>
							</#if>
						</td>
					</tr>
					<tr id="cell-info">
						<td>
							单元
						</td>
						<td>
							<#if ((house.status!0) == 1)>
							${(house.cellNo)!'xxx'} 单元（室）
							<#else>
							<input type="text" name="cellNo" value="${(house.cellNo)!}" style="width:50px;"/>
							单元（室）
							<span id="msg-cell" class="msg"></span>
							</#if>
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span> 产证面积
						</td>
						<td>
							<#if ((house.status!0) == 1)>
							${(house.propertyArea)!}平米
							<#else>
							<input class="number-field" type="text" name="propertyArea" value="${(house.propertyArea)!}"/>平米
							<span id="msg-propertyArea" class="msg"></span>	
							</#if>
						</td>
					</tr>
					<tr>
						<td>
							占地面积
						</td>
						<td>
							<#if ((house.status!0) == 1)>
							${(house.occupiedArea)!}平米
							<#else>
							<input class="number-field" type="text" name="occupiedArea" value="${(house.occupiedArea)!}"/>平米
							<span id="msg-occupiedArea" class="msg"></span>	
							</#if>			
						</td>
					</tr>
					<#if (param.viewType!) == "sale">
					<tr>
						<td>
							<span style="color:red;">*</span> 价格
						</td>
						<td>
							<#if ((house.status!0) == 1)>
							${((house.salePrice!0)/10000)!} 万元
							<#else>
							<input class="number-field" type="text" name="salePrice" value="<#if house.salePrice??>${((house.salePrice!0)/10000)!}</#if>"/>万元
							<span id="msg-salePrice" class="msg"></span>
							</#if>
						</td>
					</tr>
					</#if>
					<#if (param.viewType!) == "rent">
					<tr>
						<td>
							<span style="color:red;">*</span> 租金
						</td>
						<td>
							<#if ((house.status!0) == 1)>
							${(house.rentPrice)!} 元/月
							<#else>
							<input class="number-field" type="text" name="rentPrice" value="${(house.rentPrice)!}"/>元/月
							<span id="msg-rentPrice" class="msg"></span>
							</#if>
						</td>
					</tr>
					</#if>
					
					<tr>
						<td>
							<span style="color:red;">*</span> 房型
						</td>
						<td>
							<#if ((house.status!0) == 1)>
							<span id="room-type-txt">${(house.roomType)!}</span>
							<#else>
							<#include "/common/roomTypeSelect.ftl">
							<span id="msg-roomType" class="msg"></span>
							</#if>
						</td>
					</tr>
					
					<tr>
						<td>
							<span style="color:red;">*</span> 楼层
						</td>
						<td>
							<#if ((house.status!0) == 1)>
							<span id="floor-txt">${(house.floor)!}</span>
							<#else>
							<#include "/common/floorSelect.ftl">
							<span id="msg-floor" class="msg"></span>	
							</#if>
						</td>
					</tr>
					
					<#if (param.viewType!) == "rent">
					<tr>
						<td>
							<span style="color:red;">*</span> 入住时间
						</td>
						<td>
							<#if ((house.status!0) == 1)>
							${house.rentDealTime?string("yyyy-MM-dd")}
							<#else>
							<input type="text" name="rentDealTime" id="rentDealTime" <#if house.rentDealTime??>value='${house.rentDealTime?string("yyyy-MM-dd")}'</#if>/>（日期：1980-1-1）
							<span id="msg-rentDealTime" class="msg"></span>	
							</#if>
						</td>
					</tr>
					</#if>
					
					<tr>
						<td>
							<span style="color:red;">*</span> 装修
						</td>
						<td>
							<#include "/common/decoratingSelect.ftl">
							<span id="msg-decorating" class="msg"></span>	
						</td>
					</tr>
					
					<tr>
						<td>
							<span style="color:red;">*</span> 朝向
						</td>
						<td>
							<select id="direction" name="direction" value="${(house.direction)!}">
							  <option value="0">选择</option>
							  <option value="1000" <#if house.direction?? && house.direction == 1000>selected= "selected"</#if>>东</option>
							  <option value="100" <#if house.direction?? && house.direction == 100>selected= "selected"</#if>>南</option>
							  <option value="10" <#if house.direction?? && house.direction == 10>selected= "selected"</#if>>西</option>
							  <option value="1"	<#if house.direction?? && house.direction == 1>selected= "selected"</#if>>北</option>
							  <option value="1100"	<#if house.direction?? && house.direction == 1100>selected= "selected"</#if>>东南</option>
							  <option value="1001"	<#if house.direction?? && house.direction == 1001>selected= "selected"</#if>>东北</option>
							  <option value="110"	<#if house.direction?? && house.direction == 110>selected= "selected"</#if>>西南</option>
							  <option value="11"	<#if house.direction?? && house.direction == 11>selected= "selected"</#if>>西北</option>
							  <option value="101"	<#if house.direction?? && house.direction == 101>selected= "selected"</#if>>南北</option>
							  <option value="1010"	<#if house.direction?? && house.direction == 1010>selected= "selected"</#if>>东西</option>
							</select>
							<span id="msg-direction" class="msg"></span>		
						</td>
					</tr>
					
					<tr>
						<td>
							<span style="color:red;">*</span> 建房时间
						</td>
						<td>
							<select id="buildTimeString" name="buildTimeString" <#if house.buildTime??>value='${house.buildTime?string("yyyy")}'</#if>>（日期：1980-1-1）
							  	<option value="">选择</option>
								<#list 1900..2013 as i>
									<option value="${i}" <#if house.buildTime?? && house.buildTime?string("yyyy") == i?string>selected= "selected"</#if>>${i}</option>
								</#list>
							</select>
							<span id="msg-buildTime" class="msg"></span>
						</td>
					</tr>
					
					<#if (param.viewType!) == "rent">
					<tr>
						<td>
							<span style="color:red;">*</span> 租房配置
						</td>
						<td>
							<input type="checkbox" class="equipment" name="water" <#if rentEquipments.water??>checked="checked"</#if>/>水
							<input type="checkbox" class="equipment" name="power" <#if rentEquipments.power??>checked="checked"</#if>/>电
							<input type="checkbox" class="equipment" name="gas" <#if rentEquipments.gas??>checked="checked"</#if>/>煤气
							<input type="checkbox" class="equipment" name="heat" <#if rentEquipments.heat??>checked="checked"</#if>/>暖气
							<input type="checkbox" class="equipment" name="cable" <#if rentEquipments.cable??>checked="checked"</#if>/>有线电视
							<input type="checkbox" class="equipment" name="network" <#if rentEquipments.network??>checked="checked"</#if>/>宽带
							<input type="checkbox" class="equipment" name="tv" <#if rentEquipments.tv??>checked="checked"</#if>/>电视
							<input type="checkbox" class="equipment" name="refrigerator" <#if rentEquipments.refrigerator??>checked="checked"</#if>/>冰箱
							<input type="checkbox" class="equipment" name="airCondition" <#if rentEquipments.airCondition??>checked="checked"</#if>/>空调
							<input type="checkbox" class="equipment" name="washer" <#if rentEquipments.washer??>checked="checked"</#if>/>洗衣机
							<input type="checkbox" class="equipment" name="waterHeater" <#if rentEquipments.waterHeater??>checked="checked"</#if>/>热水机
							<input type="checkbox" class="equipment" name="microwave" <#if rentEquipments.microwave??>checked="checked"</#if>/>微波炉
							<input type="checkbox" class="equipment" name="telephone" <#if rentEquipments.telephone??>checked="checked"</#if>/>电话	
							<br>			
							<a href="javascript:;" id="selectAllEquipments">全选</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" id="deselectAllEquipments">清空</a>
							<span id="msg-equipment" class="msg"></span>		
						</td>
					</tr>
					</#if>
					
					<tr>
						<td>
							特殊说明
						</td>
						<td>
							<#if (param.viewType!) == "rent">
								付
								<select class="rentMemo" id="pay">
								  <option <#if house.memo?? && house.memo?length gte 2 && house.memo?substring(1,2) == '0'> selected= "selected"</#if> value="0">0</option>
								  <option <#if house.memo?? && house.memo?length gte 2 && house.memo?substring(1,2) == '1'> selected= "selected"</#if> value="1">1</option>
								  <option <#if house.memo?? && house.memo?length gte 2 && house.memo?substring(1,2) == '2'> selected= "selected"</#if> value="2">2</option>
								  <option <#if house.memo?? && house.memo?length gte 2 && house.memo?substring(1,2) == '3'> selected= "selected"</#if> value="3">3</option>
								  <option <#if house.memo?? && house.memo?length gte 2 && house.memo?substring(1,2) == '4'> selected= "selected"</#if> value="4">4</option>
								  <option <#if house.memo?? && house.memo?length gte 2 && house.memo?substring(1,2) == '5'> selected= "selected"</#if> value="5">5</option>
								</select>
								押
								<select class="rentMemo" id="mortgage">
								  <option <#if house.memo?? && house.memo?length gte 4 && house.memo?substring(3,4) == '0'> selected= "selected"</#if> value="0">0</option>
								  <option <#if house.memo?? && house.memo?length gte 4 && house.memo?substring(3,4) == '1'> selected= "selected"</#if> value="1">1</option>
								  <option <#if house.memo?? && house.memo?length gte 4 && house.memo?substring(3,4) == '2'> selected= "selected"</#if> value="2">2</option>
								  <option <#if house.memo?? && house.memo?length gte 4 && house.memo?substring(3,4) == '3'> selected= "selected"</#if> value="3">3</option>
								  <option <#if house.memo?? && house.memo?length gte 4 && house.memo?substring(3,4) == '4'> selected= "selected"</#if> value="4">4</option>
								  <option <#if house.memo?? && house.memo?length gte 4 && house.memo?substring(3,4) == '5'> selected= "selected"</#if> value="5">5</option>
								</select>	
							</#if>
							<#if (param.viewType!) == "sale">
								<select class="saleMemo">
								  <option value="0"></option>
								  <option <#if house.memo?? && house.memo == '税费各付价'>selected= "selected"</#if> value="1">税费各付价</option>
								  <option <#if house.memo?? && house.memo == '房东到手价'>selected= "selected"</#if> value="2">房东到手价</option>
								</select>	
							</#if>
							
							<input type="text" name="memo" value="${(house.memo)!}" readonly="readonly" id="memo"/>	
								
						</td>
					</tr>
					
					<#if (param.viewType!) == "sale">
					<tr>
						<td>
							房源标签
						</td>
						<td>
							<input type="text" value="" id="saleTagListView" readonly="readonly" style="width:400px;"/>
							<input type="hidden" name="saleTagList" id="saleTagList" value="${(house.saleTagList)!}"/><a href="javascript:;" id="selectSaleOption">选择标签</a> （不超过三个）
							<span id="msg-taglist" class="msg"></span>
							<div id="saleOptions" style="display:none;width:500px;">
								<#if saleTagOptions??>
									<#list saleTagOptions as saleTag> 
									<div class="saleTag" _tId="${(saleTag.id)!}" _tName="${(saleTag.name)!}" _tCategoryName="${(saleTag.categoryName)!}" style="float: left;margin: 5px 20px 5px 0;cursor: pointer;border: 1px solid #aaa;padding: 2px 5px;">
										<a href="javascript:;" class="tagOption">${(saleTag.name)!}<input type="checkbox" class="status" readonly="readonly"/></a>
									</div>
									</#list>
								</#if>
							</div>
						</td>
					</tr>
					</#if>
					
					<#if (param.viewType!) == "rent">
					<tr>	
						<td>
							房源标签
						</td>
						<td>
							<input type="text" value="" id="rentTagListView" readonly="readonly" style="width:400px;"/>
							<input type="hidden" name="rentTagList" id="rentTagList" value="${(house.rentTagList)!}"/><a href="javascript:;" id="selectRentOption">选择标签</a> （不超过3个）
							<span id="msg-taglist" class="msg"></span>
							<div id="rentOptions" style="display:none;width:500px;">
								<#if rentTagOptions??>
									<#list rentTagOptions as rentTag> 
									<div class="rentTag" _tId="${(rentTag.id)!}" _tName="${(rentTag.name)!}" _tCategoryName="${(rentTag.categoryName)!}" style="float: left;margin: 5px 20px 5px 0;cursor: pointer;border: 1px solid #aaa;padding: 2px 5px;">
										<a href="javascript:;" class="tagOption">${(rentTag.name)!}<input type="checkbox" class="status" readonly="readonly"/></a>
									</div>
									</#list>
								</#if>
							</div>
						</td>
					</tr>
					</#if>
					
					<tr>
						<td>
						</td>
						<td>
							<input id="submitBtn" type="submit" value="${((house.id!0) > 0) ? string('保 存', '下一步')}"/>
							<input id="house-status" name="house-status" type="hidden" value="${house.status!}" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<script>
			var submitLock = false;
			
			$(document).ready(function(){
				init();
				bind();
				exec();
			});
			
			// init
			function init(){
				
				$("#rentDealTime").datepicker();
				
				if ($("#saleTagList").length > 0)
				{
					var saleTagsStr = $("#saleTagList").val();
					var saleTags;
					var saleTagsView = "";
					if(saleTagsStr.length > 0){
						saleTags = saleTagsStr.split(",");
						for(i=0;i < saleTags.length ;i++){
							if(i>0)
								saleTagsView += ",";
							saleTagsView += $("div[_tId=" + saleTags[i] + "]").attr("_tName");
							$("div[_tId='" + saleTags[i] + "'][_tCategoryName='sale']").find(".status").attr("checked","true");
						}
						$("#saleTagListView").val(saleTagsView);
					}
				}
				
				if ($("#rentTagList").length > 0)
				{
					var rentTagsStr = $("#rentTagList").val();
					var rentTags;
					var rentTagsView = "";
					if(rentTagsStr.length > 0){
						rentTags = rentTagsStr.split(",");
						for(i=0;i < rentTags.length ;i++){
							if(i>0)
								rentTagsView += ",";
							rentTagsView += $("div[_tId=" + rentTags[i] + "]").attr("_tName");
							$("div[_tId='" + rentTags[i] + "'][_tCategoryName='rent']").find(".status").attr("checked","true");
						}
						$("#rentTagListView").val(rentTagsView);
					}
				}
				
				checkHouseType();
				showHouseType();
				showRoomType();
				showFloor();
				
			}
			
			// bind
			function bind(){
				if ($("#selectSaleOption").length > 0)
				{
					$("#selectSaleOption").click(function(){
						$("#saleOptions").toggle();
					});
				}
				
				if ($("#selectRentOption").length > 0)
				{
					$("#selectRentOption").click(function(){
						$("#rentOptions").toggle();
					});
				}
				
				$(".tagOption").click(function(){
					clickTagOption($(this));
				});
				
				$("#selectAllEquipments").click(function(){
					$(".equipment").attr("checked", "true");
				});
				
				$("#deselectAllEquipments").click(function(){
					$(".equipment").removeAttr("checked");
				});
				
				$('.rentMemo').change(function(){
					var pay = $("#pay").find("option:selected").html();
					var mortgage = $("#mortgage").find("option:selected").html();
					var memo = "付" + pay + "押" + mortgage;
					
					$("#memo").val(memo);
				});
				
				$('.saleMemo').change(function(){
					var memo = $(this).find("option:selected").html();
					$("#memo").val(memo);
				});
				
				$(".number-field").change(function(){
					$(this).val($.trim($(this).val()));
					if ($(this).val() != "")
					{
						if (parseFloat($(this).val()) != $(this).val())
						{
							$(this).val("");
						}
					}
				});
				
				$("#residence").change(function(){
					if ($(this).val() > 0)
					{
						var name = $.trim($.trim($("#residence").find("option:selected").text()).substr(1));
						$("input[name='detailName']").val(name);
					}
				});
				
				$("input").click(function(){
					$(".msg").html("");
				});
			
				$("#houseType").change(function(){
					checkHouseType();
				});	
			}
			
			function exec(){
				//TODO:exec
			}
			
			function checkHouseType()
			{
				var houseType = $("#houseType").val();
				
				if (houseType == 9 || houseType == 4 || houseType == 5) 
				{
					$("#building-required").show();
				}
				else
				{
					$("#building-required").hide();
				}
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
			
			function clickTagOption(thisDom){
				var tagType= thisDom.parent().attr("_tCategoryName");
				var clickedOptionId = thisDom.parent().attr("_tId");
				var tagList ="";
				var tagListView = "";
				if(thisDom.find(".status").attr("checked") == true){
					thisDom.find(".status").removeAttr("checked");
				}
				else{
					thisDom.find(".status").attr("checked", "true");
				}
				
				var tagsView = "";
				var tags = "";
				$("#" + tagType + "Options").find(".status[checked]").each(function(i){
					if(i>0){
						tagsView += ",";
						tags += ",";
					}
					tagsView += $(this).closest("." + tagType + "Tag").attr("_tName");
					tags += $(this).closest("." + tagType + "Tag").attr("_tId");
				});
				$("#" + tagType + "TagListView").val(tagsView);
				$("#" + tagType + "TagList").val(tags);
			}
			
			function checkExtHouseUpdateForm()
			{
				if(submitLock === true){
					console.debug("请勿重复提交");
					return false;
				}
				submitLock = true;
				
				var result = true;
				
				try
				{
					var house_status = $("#house-status").val();
					var viewType = $("input[name='viewType']").val();
					
					if (house_status != 1)
					{
						var residenceId = $("#residence").val();
						var detailName = $.trim($("input[name='detailName']").val());
						var houseType = $("#houseType").val(); 
						var buildingNo = $.trim($("input[name='buildingNo']").val());
						var cellNo = $.trim($("input[name='cellNo']").val());
						var propertyArea = $.trim($("input[name='propertyArea']").val());
						var occupiedArea = $.trim($("input[name='occupiedArea']").val());
						
						var shi = $.trim($("#shi").val());
						var ting = $.trim($("#ting").val());
						var wei = $.trim($("#wei").val());
						var yangtai = $.trim($("#yangtai").val());
						
						var floor = $.trim($("#floor").val());
					}
					
					var decorating = $.trim($("#J_decorating").val());
					var direction = $.trim($("#direction").val());
					var buildTimeString = $.trim($("#buildTimeString").val());
					
					if (house_status != 1)
					{
						if (!(residenceId > 0))
						{
							result = false;
							$("#msg-residenceId").html("请选择小区");
						}
						
						if (detailName == "")
						{
							result = false;
							$("#msg-detailName").html("请填写具体名称");
						}
						
						if (houseType == "")
						{
							houseType
							result = false;
							$("#msg-houseType").html("请选择类型");
						}
						
						if (houseType == 9 || houseType == 4 || houseType == 5) 
						{
							if (buildingNo == "")
							{
								result = false;
								$("#msg-building").html("请填写栋座");
							}
							
							
							if (floor == "")
							{
								result = false;
								$("#msg-floor").html("请选择楼层");
							}
						}
						
						if (buildingNo != "" && buildingNo.match(/[^A-Za-z0-9]/g) != null)
						{
							result = false;
							$("#msg-building").html("只能填写数字和字母");
						}
						
						if (cellNo != "" && cellNo.match(/[^A-Za-z0-9]/g) != null)
						{
							result = false;
							$("#msg-cell").html("只能填写数字和字母");
						}
						
						if (propertyArea == "" || !(parseFloat(propertyArea) > 0))
						{
							result = false;
							$("#msg-propertyArea").html("面积必须大于0");
						}
						else if (parseFloat(propertyArea) > 2000)
						{
							result = false;
							$("#msg-propertyArea").html("数字过大");
						}
						
						if (occupiedArea != "" && !(parseFloat(occupiedArea) > 0))
						{
							result = false;
							$("#msg-occupiedArea").html("必须为数字");
						}
						else if (parseFloat(occupiedArea) > 1000000)
						{
							result = false;
							$("#msg-occupiedArea").html("数字过大");
						}
						
						if (shi == "" || ting == "" || wei == "" || yangtai == "")
						{
							result = false;
							$("#msg-roomType").html("请选择房型");
						}
					}
					
					if (decorating == "")
					{
						result = false;
						$("#msg-decorating").html("请选择装修");
					}
					
					if (direction == "" || direction == 0)
					{
						result = false;
						$("#msg-direction").html("请选择朝向");
					}
					
					if (buildTimeString == "")
					{
						result = false;
						$("#msg-buildTime").html("请选择建房年代");
					}
					
					if (viewType == "sale")
					{
						if (house_status != 1)
						{
							var salePrice = $.trim($("input[name='salePrice']").val());
							if (salePrice == "" || !(parseFloat(salePrice) > 0))
							{
								result = false;
								$("#msg-salePrice").html("售价必须大于0");
							}
							else if (salePrice > 100000)
							{
								result = false;
								$("#msg-salePrice").html("数字过大");
							}
						}
					}
					
					if (viewType == "rent")
					{
						if (house_status != 1)
						{
							var rentPrice = $.trim($("input[name='rentPrice']").val());
							if (rentPrice == "" || !(parseFloat(rentPrice) > 0))
							{
								result = false;
								$("#msg-rentPrice").html("租金必须大于0");
							}
							else if (rentPrice > 100000)
							{
								result = false;
								$("#msg-rentPrice").html("数字过大");
							}
							
							var rentDealTime = $.trim($("input[name='rentDealTime']").val());
							if (rentDealTime == "")
							{
								result = false;
								$("#msg-rentDealTime").html("请填写入住时间");
							}
						}
						
						var hasEquipment = false;
						$(".equipment").each(
							function()
							{
								hasEquipment |= $(this).is(':checked'); 
							}
						);
						
						if (!hasEquipment)
						{
							result = false
							$("#msg-equipment").html("请选择配置");
						}
					}
					
					var tagCount = 0;
					tagCount = $(".tagOption input[type='checkbox']:checked").length;
					if (tagCount > 3)
					{
						$("#msg-taglist").html("最多选择三个标签");
					}
				
				}
				catch (e)
				{
        		}
        		finally
        		{
        			if(submitLock === true)
                		submitLock = false;
        		}
        		
        		
        		if(result === true) {
        			$("#submitBtn").hide();
        		}
				
				return result;
				
			}
		</script>
	</body>
</html>