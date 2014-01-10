<html>
	<head></head>	
	<title>已售房源${house.id!}</title>
	<body>
		<div>
			<a href="soldHouseDetail.controller?houseId=${(house.id)!}">基本信息</a>
			<a href="soldHousePicConsole.controller?houseId=${(house.id)!}">房屋照片</a>
		</div>
		<hr/>
		<div style="width:768px;">
			<form action="/houseUpdate.controller" method="post">
				<input type="hidden" name="id" value="${(house.id)!}" id="houseId"/>
				<input type="hidden" name="contactId" value="${(house.contactId)!}"/>
				<input type="hidden" name="propertyId" value="${(house.propertyId)!}"/>
				<hr/>
				<div>
					地址：${(house.regionCityId)!} - ${(house.regionName)!} - ${(house.residenceName)!}
					- ${(house.residenceAddress)!} - - ${(house.buildingPrefix)!}${(house.buildingCodeBegin)!}${(house.buildingSuffix)!}
					- ${(house.cellFloorBegin)!}${(house.cellCodeBegin)!}
				</div>
				<div>
					楼层：第${(house.cellFloorBegin)!}层  共 ?? 层				
				</div>
				<div>
					产证面积：${(house.propertyArea)!}平米		
				</div>
				<div>
					占地面积：${(house.occupiedArea)!}平米				
				</div>
				<div>
					房型： ${(roomType.shi)!}室
					${(roomType.ting)!}厅
					${(roomType.wei)!}卫
					${(roomType.yangtai)!}阳台				
				</div>
				<div>
					建房时间：
					<select name="buildTimeString" <#if house.buildTime??>value='${house.buildTime?string("yyyy")}'</#if>>（日期：1980-1-1）
					  	<option value="">选择</option>
						<#list 1950..2013 as i>
							<option value="${i}" <#if house.buildTime?? && house.buildTime?string("yyyy") == i?string>selected= "selected"</#if>>${i}</option>
						</#list>
					</select>				
				</div>
				<div>
					装修：
					<#include "/common/decoratingSelect.ftl">
				</div>
				<div>
					朝向：
					<#include "/common/directionSelect.ftl">				
				</div>
				<div>
					类型：
					<select name="houseType" value="${(house.houseType)!}" disabled="disabled">
					  <option value="">选择</option>
					  <option value="1" <#if house.houseType?? && house.houseType == '1'>selected= "selected"</#if>>公寓</option>
					  <option value="2"	<#if house.houseType?? && house.houseType == '2'>selected= "selected"</#if>>小高层</option>
					  <option value="3"	<#if house.houseType?? && house.houseType == '3'>selected= "selected"</#if>>老公房</option>
					  <option value="4"	<#if house.houseType?? && house.houseType == '4'>selected= "selected"</#if>>其它</option>
					</select>	
				</div>
				<hr/>
					<div>房源状态：
						租
						<select name="rentRentStatus" value="${(house.rentRentStatus)!}" id="rentStatus" disabled="disabled">
						  <option value="" >选择</option>
						  <option value="1"	<#if house.rentRentStatus?? && house.rentRentStatus == 1>selected= "selected"</#if>>在租</option>
						  <option value="2"	<#if house.rentRentStatus?? && house.rentRentStatus == 2>selected= "selected"</#if>>不租</option>
						  <option value="3"	<#if house.rentRentStatus?? && house.rentRentStatus == 3>selected= "selected"</#if>>已租</option>
						</select>
						售
						<select name="saleSaleStatus" value="${(house.saleSaleStatus)!}" id="saleStatus" disabled="disabled">
						  <option value="" >选择</option>
						  <option value="1"	<#if house.saleSaleStatus?? && house.saleSaleStatus == 1>selected= "selected"</#if>>在售</option>
						  <option value="2"	<#if house.saleSaleStatus?? && house.saleSaleStatus == 2>selected= "selected"</#if>>不售</option>
						</select>	
						看房方式
						<select name="viewHouseType" value="${(house.viewHouseType)!}" id="saleStatus" disabled="disabled">
						  <option value="" >选择</option>
						  <option value="1"	<#if house.viewHouseType?? && house.viewHouseType == 1>selected= "selected"</#if>>预约看房</option>
						  <option value="2"	<#if house.viewHouseType?? && house.viewHouseType == 2>selected= "selected"</#if>>立即看房</option>
						</select>
					</div>
					<div>
						挂牌价：${(house.salePrice)!}万
						底价：${(house.saleBasePrice)!}万
						<div>
							交易类型
							<select name="saleDealType" value="${(house.saleDealType)!}" id="saleDealType" disabled="disabled">
							  <option value="" >选择</option>
							  <option value="1"	<#if house.saleDealType?? && house.saleDealType == '1'>selected= "selected"</#if>>税费各付</option>
							  <option value="2"	<#if house.saleDealType?? && house.saleDealType == '2'>selected= "selected"</#if>>买房全付</option>
							</select>
						</div>
						<div>
							出售状态
							<input type="radio" name="saleSaleRec" value="0" <#if house.saleSaleRec?? && house.saleSaleRec == 0>checked= "checked"</#if>/>无 
							<input type="radio" name="saleSaleRec" value="1" <#if house.saleSaleRec?? && house.saleSaleRec == 1>checked= "checked"</#if>/>急售
							<input type="radio" name="saleSaleRec" value="2" <#if house.saleSaleRec?? && house.saleSaleRec == 2>checked= "checked"</#if>/>推荐
						</div>
						<div>
							售房特色
							<input type="text" value="" id="saleTagListView" disabled="disabled"/>
							<input type="hidden" name="saleTagList" id="saleTagList" value="${(house.saleTagList)!}"/>
							<div id="saleOptions" style="display:none;">
								<#if saleTagOptions??>
									<#list saleTagOptions as saleTag> 
									<div class="saleTag" _tId="${(saleTag.id)!}" _tName="${(saleTag.name)!}" _tCategoryName="${(saleTag.categoryName)!}">
										<a href="javascript:;" class="tagOption">${(saleTag.name)!}<input type="checkbox" class="status" disabled="disabled"/></a>
									</div>
									</#list>
								</#if>
							</div>
						</div>
						<div>
							售房特殊说明 ：${(house.saleMemo)!}
						</div>
					</div>

					<div>
						租金 ：${(house.rentPrice)!}
						底价 ：${(house.rentBasePrice)!}
						<div>
							租房类型
							<select name="rentRentType" value="${(house.rentRentType)!}" id="rentRentType" disabled="disabled">
							  <option value="" >选择</option>
							  <option value="1"	<#if house.rentRentType?? && house.rentRentType == 1>selected= "selected"</#if>>整租</option>
							  <option value="2"	<#if house.rentRentType?? && house.rentRentType == 2>selected= "selected"</#if>>合租</option>
							</select>
						</div>
						<div>
							出租状态：
							<input type="radio" name="rentRentRec" value="0" <#if house.rentRentRec?? && house.rentRentRec == 0>checked= "checked"</#if>/>无 
							<input type="radio" name="rentRentRec" value="1" <#if house.rentRentRec?? && house.rentRentRec == 1>checked= "checked"</#if>/>急租
							<input type="radio" name="rentRentRec" value="2" <#if house.rentRentRec?? && house.rentRentRec == 2>checked= "checked"</#if>/>推荐
						</div>
						<div>
							租房特色：
							<input type="text" value="" id="rentTagListView" disabled="disabled"/>
							<input type="hidden" name="rentTagList" id="rentTagList" value="${(house.rentTagList)!}"/>
							<div id="rentOptions" style="display:none;">
								<#if rentTagOptions??>
									<#list rentTagOptions as rentTag> 
									<div class="rentTag" _tId="${(rentTag.id)!}" _tName="${(rentTag.name)!}" _tCategoryName="${(rentTag.categoryName)!}">
										<a href="javascript:;" class="tagOption">${(rentTag.name)!}<input type="checkbox" class="status" disabled="disabled"/></a>
									</div>
									</#list>
								</#if>
							</div>
						</div>
						<div>
							租房特殊说明:${(house.rentMemo)!}
						</div>
					</div>
					<hr/>
					<div>
						钥匙：
						<input type="radio" name="hasKey" value="1"	<#if house.hasKey?? && house.hasKey == 1>checked= "checked"</#if>/>有 
						<input type="radio" name="hasKey" value="0" <#if house.hasKey?? && house.hasKey == 0>checked= "checked"</#if>/>无 
						<input type="text" name="hasKeyMemo" value="${(house.hasKeyMemo)!}"/>
					</div>
					<div>
						业主姓名：${(house.contactName)!}
						业主电话：${(house.contactMobile)!}
						联系方式备注：${(house.contactMemo)!}
					</div>
					<div>
						产权编号：${(house.propertyPropertyCode)!}
						产权人：${(house.propertyPropertyOwner)!}
						贷款情况：${(house.propertyLoan)!}
					</div>
			</form>
		</div>
		<div id="more">
		</div>

		
		<script>
			$(document).ready(function(){
				init();
				bind();
				exec();
			});
			
			function init(){
				$("select[name='direction']").attr("disabled", 'disabled');
				$("select[name='decorating']").attr("disabled", 'disabled');
			
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
			
			function bind(){
				$("#markInvalid").click(function(){
					markHouseInvalid();
				});
				$("#loggingUpdate").click(function(){
					$("#update").show();
				});
				$("#update .close").click(function(){
					clearUpdateForm();
					$("#update").hide();
				});
				$("#update .submit").click(function(){
					submitUpdateForm();
				});
				$("#viewAuditUpdates").click(function(){
					refreshAuditUpdates();
				});
				$("#viewContactUpdates").click(function(){
					refreshContactUpdates();
				});
				$("#selectSaleOption").click(function(){
					$("#saleOptions").toggle();
				});
				$("#selectRentOption").click(function(){
					$("#rentOptions").toggle();
				});
				$(".tagOption").click(function(){
					clickTagOption($(this));
				})
			}
			
			function exec(){
			}
			
			function refreshAuditUpdates(){
				$.ajax({
					type: "post",
					url: "/ajax/getContentUpdateHistoryByHouseId.controller",
					data: {houseId: $("#houseId").val()},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {		
						var total = '<ul>';
						for(var i in data.bizData){
							var item = '<li>' 
							+ '日期：' + new Date(data.bizData[i].updateTime) + '--' 
							+ '修改人：' +  data.bizData[i].committerId + '--' 
							+ '修改前：' + data.bizData[i].preContent + '--' 
							+ '修改后：' + data.bizData[i].postContent
							+ '</li>';
							total += item;
						}
						total += '</ul>';
						$('#history').html(total);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
			}
			
			function refreshContactUpdates(){
				$.ajax({
					type: "post",
					url: "/ajax/getContactListByHouseId.controller",
					data: {houseId: $("#houseId").val()},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {		
						var total = '<ul>';
						for(var i in data.bizData){
							var item = '<li>' 
							+ '日期：' + new Date(data.bizData[i].updateTime) + '--'
							+ '修改人：' + data.bizData[i].committer + '--' 
							+ '联系人：' + data.bizData[i].name + '--' 
							+ '联系方式：' + data.bizData[i].mobile
							+ '</li>';
							total += item;
						}
						total += '</ul>';
						$('#history').html(total);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
			}
			
			function markHouseInvalid(){
				$.ajax({
					type: "post",
					url: "/ajax/markHouseInvalid.controller",
					data: {houseId: $("#houseId").val()},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						var val = parseInt($("#invalidNumer").attr('_val')) + 1;
						$("#invalidNumer").html('(' + val + ')');
						alert("success");
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
			}
			
			function clearUpdateForm(){
				$("#update input").val("");
			}
			
			function submitUpdateForm(){
				$.ajax({
					type: "post",
					url: "/ajax/committerUpdateAudit.controller",
					data: {
						houseId: $("#houseId").val(),
						contactName: $("#updateContactName").val(),
						contactMobile: $("#updateContactMobile").val(),
						housePropertyArea: $("#updateHousePropertyArea").val()
					},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						alert("success");
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
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
		</script>
	</body>
</html>