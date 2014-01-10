<html>
	<head></head>	
	<title>登盘${house.id!}</title>
	<body>
		<div>
			<form action="/houseUpdate.controller" method="post">
				<input type="hidden" name="id" value="${(house.id)!}"/>
				<input type="hidden" name="contactId" value="${(house.contactId)!}"/>
				<input type="hidden" name="propertyId" value="${(house.propertyId)!}"/>
				<table>
					<tr>
						<td>
							<span style="color:red;">*</span>板块
						</td>
						<td>					
							城市
							<select name="regionCityId" value="${(house.regionCityId)!}">
								<option value="1" selected="selected">上海</option>
							</select>
							区域
							<select name="regionParentId" _value="${(house.regionParentId)!}" id="region">
								
							</select>
							板块
							<select name="regionRegionId" _value="${(house.regionRegionId)!}" id="plate">
								
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>楼盘
						</td>
						<td>楼盘
							<select name="residenceId" _value="${(house.residenceId)!}" id="residence">
								
							</select>
						 	 栋（号）
							<select name="buildingId" _value="${(house.buildingId)!}" id="building">
								
							</select>
							 单元（室）
							<select name="cellId" _value="${(house.cellId)!}" id="cell">
								
							</select>
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>楼层
						</td>
						<td>
							第<input type="text" id="floorBegin" disabled="disabled"/>层 共
							<input type="text" id="floorTotal" disabled="disabled"/>层				
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>产证面积
						</td>
						<td>
							<input type="text" name="propertyArea" value="${(house.propertyArea)!}"/>平米		
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>占地面积
						</td>
						<td>
							<input type="text" name="occupiedArea" value="${(house.occupiedArea)!}"/>平米	<span style="color:red;">（有些别墅会有占地面积）</span>			
						</td>
					</tr>
					<tr>
						<td>
							房型
						</td>
						<td>
							<#include "/common/roomTypeSelect.ftl">		
						</td>
					</tr>
					<tr>
						<td>
							建房时间
						</td>
						<td>
							<select name="buildTimeString" <#if house.buildTime??>value='${house.buildTime?string("yyyy")}'</#if>>（日期：1980-1-1）
							  	<option value="">选择</option>
								<#list 1950..2013 as i>
									<option value="${i}" <#if house.buildTime?? && house.buildTime?string("yyyy") == i?string>selected= "selected"</#if>>${i}</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							装修
						</td>
						<td>
							<#include "/common/decoratingSelect.ftl">
						</td>
					</tr>
					<tr>
						<td>
							朝向
						</td>
						<td>
							<select name="direction" value="${(house.direction)!}">
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
						</td>
					</tr>
					<tr>
						<td>
							类型
						</td>
						<td>
							<#include "/common/houseTypeSelect.ftl">		
						</td>
					</tr>
					<tr>
						<td>
							钥匙
						</td>
						<td>
							<input type="radio" id="keyTrue" name="hasKey" value="1"	<#if house.hasKey?? && house.hasKey == 1>checked= "checked"</#if>/><label for="keyTrue">有</label> 
							<input type="radio" id="keyFalse" name="hasKey" value="0" <#if house.hasKey?? && house.hasKey == 0>checked= "checked"</#if>/><label for="keyFalse">无</label> 
							<input type="text" name="hasKeyMemo" value="${(house.hasKeyMemo)!}"/><span style="color:red;">（此处为钥匙相关说明）</span>
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>业主
						</td>
						<td>
							业主姓名：<input type="text" name="contactName" value="${(house.contactName)!}"/>
							业主电话：<input type="text" name="contactMobile" value="${(house.contactMobile)!}"/>
						</td>
					</tr>
					<tr>
						<td>
							联系方式备注
						</td>
						<td>
							<input type="text" name="contactMemo" value="${(house.contactMemo)!}"/>
						</td>
					</tr>
					<tr>
						<td>
							产权
						</td>
						<td>
							产权编号：<input type="text" name="propertyPropertyCode" value="${(house.propertyPropertyCode)!}"/>
							产权人：<input type="text" name="propertyPropertyOwner" value="${(house.propertyPropertyOwner)!}"/>
							贷款情况：<input type="text" name="propertyLoan" value="${(house.propertyLoan)!}"/>
						</td>
					</tr>
					<tr>
						<td>
						</td>
						<td>
							<input type="submit" text="提交"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<script>
			$(document).ready(function(){
				init();
				bind();
				exec();
			});
			
			// init
			function init(){
				refreshRegionList({
					'region' : $("#region").attr("_value"), 
					'plate' : $("#plate").attr("_value"),
					'residence' : $("#residence").attr("_value"),
					'building' : $("#building").attr("_value"),
					'cell' : $("#cell").attr("_value")
				});
				
			}
			
			// bind
			function bind(){
				$('#region').change(function(){
					refreshPlateList();
				});
				$('#plate').change(function(){
					refreshResidenceList();
				});
				$('#residence').change(function(){
					refreshBuildingList();
				});
				$('#building').change(function(){
					refreshCellList();
				});
				$('#cell').change(function(){
					$('#floorBegin').attr('value', $("#cell").find("option:selected").attr('_floor'));
					$('#floorTotal').attr('value', $("#cell").attr('_maxFloor'));
				})
			}
			
			function exec(){
				//TODO:exec
			}
			
			function refreshRegionList(selectedOpts){
				$.ajax({
					type: "post",
					url: "/ajax/getRegionList.controller",
					data: {},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {		
						var total = '<option value="">选择</option>';
						for(var i in data.bizData){
							var option;
							//console.debug(selectedOpts[0] + '---' + data.bizData[i].id);
							if(typeof(selectedOpt) !== 'undefined' && typeof(selectedOpts['region']) !== 'undefined'  && selectedOpts['region'] == data.bizData[i].id )
								option = '<option value=' + data.bizData[i].id + ' selected="selected">' + data.bizData[i].name + '</option>';
							else
								option = '<option value=' + data.bizData[i].id + '>' + data.bizData[i].name + '</option>';
							total += option;
						}
						$('#region').html(total);
						refreshPlateList(selectedOpts);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
			}
			
			function refreshPlateList(selectedOpt){
				if($("#region").find("option:selected").val() > 0){
				  	$.ajax({
						type: "post",
						url: "/ajax/getPlateList.controller",
						data: {parentId: $("#region").find("option:selected").val()},
						dataType: "json",
						contentType:'application/x-www-form-urlencoded; charset=UTF-8',
						success: function (data) {		
							var total = '<option value="">选择</option>';
							for(var i in data.bizData){
								var option; 
								if(typeof(selectedOpt) !== 'undefined' && typeof(selectedOpt['plate']) !== 'undefined'  && selectedOpt['plate'] == data.bizData[i].id )
									option = '<option value=' + data.bizData[i].id + ' selected="selected">' + data.bizData[i].name + '</option>';
								else
									option = '<option value=' + data.bizData[i].id + '>' + data.bizData[i].name + '</option>';
								total += option;
							}
							$('#plate').html(total);
							refreshResidenceList(selectedOpt);		
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	}); 
			  	}
		  	}	
		  	
			function refreshResidenceList(selectedOpt){
				if($("#plate").find("option:selected").val() > 0){
				  	$.ajax({
						type: "post",
						url: "/ajax/getResidenceListByPlateId.controller",
						data: {plateId: $("#plate").find("option:selected").val()},
						dataType: "json",
						contentType:'application/x-www-form-urlencoded; charset=UTF-8',
						success: function (data) {		
							var total = '<option value="">选择</option>';
							for(var i in data.bizData){
								var option; 
								if(typeof(selectedOpt) !== 'undefined' && typeof(selectedOpt['residence']) !== 'undefined'  && selectedOpt['residence'] == data.bizData[i].residenceId )
									option = '<option value=' + data.bizData[i].residenceId + ' selected="selected">' + data.bizData[i].residenceName + '</option>';
								else
									option = '<option value=' + data.bizData[i].residenceId + '>' + data.bizData[i].residenceName + '</option>';
								total += option;
							}
							$('#residence').html(total);
							refreshBuildingList(selectedOpt);		
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	});
			  	} 
		  	}
		  	
		  	function refreshBuildingList(selectedOpt){
				if($("#residence").find("option:selected").val() > 0){
				  	$.ajax({
						type: "post",
						url: "ajax/getBuildingListByResienceId.controller",
						data: {residenceId: $("#residence").find("option:selected").val()},
						dataType: "json",
						contentType:'application/x-www-form-urlencoded; charset=UTF-8',
						success: function (data) {		
							var total = '<option value="">选择</option>';
							for(var i in data.bizData){
								var option; 
								if(typeof(selectedOpt) !== 'undefined' && typeof(selectedOpt['building']) !== 'undefined'  && selectedOpt['building'] == data.bizData[i].id )
									option = '<option value=' + data.bizData[i].id + ' selected="selected">' + data.bizData[i].prefix + data.bizData[i].codeBegin +data.bizData[i].suffix  + '</option>';
								else
									option = '<option value=' + data.bizData[i].id + '>' + data.bizData[i].prefix + data.bizData[i].codeBegin +data.bizData[i].suffix + '</option>';
								total += option;
							}
							$('#building').html(total);
							refreshCellList();		
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	}); 
			  	}
			 }
			 
			function refreshCellList(selectedOpt){
				if($("#building").find("option:selected").val() > 0){
				  	$.ajax({
						type: "post",
						url: "/ajax/getCellListByBuildingId.controller",
						data: {buildingId: $("#building").find("option:selected").val()},
						dataType: "json",
						contentType:'application/x-www-form-urlencoded; charset=UTF-8',
						success: function (data) {		
							var total = '<option value="">选择</option>';
							var maxFloor = 0;
							for(var i in data.bizData){
								var option; 
								if(typeof(selectedOpt) !== 'undefined' && typeof(selectedOpt['cell']) !== 'undefined'  && selectedOpt['cell'] == data.bizData[i].id ){
									option = '<option value=' + data.bizData[i].id + ' selected="selected">' + data.bizData[i].floorBegin + data.bizData[i].codeBegin + '</option>';
									$('#floorBegin').attr('value', data.bizData[i].floorBegin);
								}
								else
									option = '<option value=' + data.bizData[i].id + ' _floor=' + data.bizData[i].floorBegin + '>' + data.bizData[i].floorBegin + data.bizData[i].codeBegin + '</option>';
								total += option;
								maxFloor = parseInt(data.bizData[i].floorBegin) > maxFloor ? parseInt(data.bizData[i].floorBegin) : maxFloor;
							}
							$('#cell').html(total);
							$('#cell').attr('_maxFloor', maxFloor);		
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	});
			  	} 
			 } 
		</script>
	</body>
</html>