<html>
	<head></head>	
	<title>登盘${(house.id)!}</title>
	<body>
		<div style="width:500px;float:left;">
			<form action="/soldHouseSubmit.controller" method="post">
				<input type="hidden" name="id" value="${(house.id)!}" id="houseId"/>
				<input type="hidden" name="contactId" value="${(house.contactId)!}"/>
				<input type="hidden" name="propertyId" value="${(house.propertyId)!}"/>
				<input type="hidden" name="saleSaleStatus" value="3"/>
				<div>*城市：
					<select name="regionCityId" value="${(house.regionCityId)!}">
						<option value="1" selected="selected">上海</option>
					</select>
				</div>
				<div>*区域：
					<select name="regionParentId" _value="${(house.regionParentId)!}" id="region">
						
					</select>
				</div>
				<div>*板块：
					<select name="regionRegionId" _value="${(house.regionId)!}" id="plate">
						
					</select>
				</div>
				<div>*楼盘
					<select name="residenceId" _value="${(house.residenceId)!}" id="residence">
						
					</select>
				</div>
				<!--
				<div>*栋（号）
					<select name="buildingId" _value="${(house.buildingId)!}" id="building">
						
					</select>
				</div>
				<div>*单元（室）
					<select name="cellId" _value="${(house.cellId)!}" id="cell">
						
					</select>
				</div>
				-->
				<div>*栋座信息<input type="text" name="soldBuildingInfo" value="${(house.soldBuildingInfo)!}"/><span>20个字以内</span></div>
				<div>总价<input type="text" name="salePrice" value="${(house.salePrice)!}"/><span>单位：元</span></div>
				<div>面积<input type="text" name="propertyArea" value="${(house.propertyArea)!}"/><span>单位：平方米</span></div>
				<div>单价<input type="text" name="saleAvgPrice" value="${(house.saleAvgPrice)!}"/><span>单位：元/平方米</span></div>
				<div>签约时间<input type="text" name="saleDealTime" id="saleDealTime" <#if house.saleDealTime??>value='${house.saleDealTime?string("yyyy-MM-dd")}'</#if>/></div>
				<div>
					交易类型
					<select name="saleDealType" value="${(house.saleDealType)!}" id="saleDealType">
					  <option value="" >选择</option>
					  <option value="1"	<#if house.saleDealType?? && house.saleDealType == '1'>selected= "selected"</#if>>税费各付</option>
					  <option value="2"	<#if house.saleDealType?? && house.saleDealType == '2'>selected= "selected"</#if>>买方全付</option>
					</select>
				</div>
				<div>数据来源
					<select name="saleSource" id="saleSource">
						<option value="1">交易中心</option>
						<option value="2">ERP录入</option>
					</select>
				</div>
				<input type="submit" text="提交"/>
			</form>
		</div>
		
		<script>
			$(document).ready(function(){
				init();
				bind();
				exec();
			});
			
			function init(){
				$("#saleDealTime").datepicker();
			}
			
			function bind(){
				$('#city').change(function(){
					refreshRegionList();
				});
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
			}
			
			function exec(){
				$("#saleSource").val("${(house.saleSource)!}");
				 
				refreshRegionList({
					'region' : $("#region").attr("_value"), 
					'plate' : $("#plate").attr("_value"),
					'residence' : $("#residence").attr("_value"),
					'building' : $("#building").attr("_value"),
					'cell' : $("#cell").attr("_value"),
				});
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
//							console.debug(selectedOpts['region'] + '---' + data.bizData[i].id);
//							console.debug(selectedOpts['region'] == data.bizData[i].id);
							if(typeof(selectedOpts) !== 'undefined' && typeof(selectedOpts['region']) !== 'undefined'  && selectedOpts['region'] == data.bizData[i].id )
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
			
			function refreshPlateList(selectedOpts){
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
//								console.debug(selectedOpts['plate'] + '---' + data.bizData[i].id);
//								console.debug(selectedOpts['plate'] == data.bizData[i].id);
								if(typeof(selectedOpts) !== 'undefined' && typeof(selectedOpts['plate']) !== 'undefined'  && selectedOpts['plate'] == data.bizData[i].id )
									option = '<option value=' + data.bizData[i].id + ' selected="selected">' + data.bizData[i].name + '</option>';
								else
									option = '<option value=' + data.bizData[i].id + '>' + data.bizData[i].name + '</option>';
								total += option;
							}
							$('#plate').html(total);
							refreshResidenceList(selectedOpts);		
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	}); 
			  	}
		  	}	
		  	
			function refreshResidenceList(selectedOpts){
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
								if(typeof(selectedOpts) !== 'undefined' && typeof(selectedOpts['residence']) !== 'undefined'  && selectedOpts['residence'] == data.bizData[i].residenceId )
									option = '<option value=' + data.bizData[i].residenceId + ' selected="selected">' + data.bizData[i].residenceName + '</option>';
								else
									option = '<option value=' + data.bizData[i].residenceId + '>' + data.bizData[i].residenceName + '</option>';
								total += option;
							}
							$('#residence').html(total);
							refreshBuildingList(selectedOpts);		
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	});
			  	} 
		  	}
		  	
		  	function refreshBuildingList(selectedOpts){
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
								if(typeof(selectedOpts) !== 'undefined' && typeof(selectedOpts['building']) !== 'undefined'  && selectedOpts['building'] == data.bizData[i].id )
									option = '<option value=' + data.bizData[i].id + ' selected="selected">' + data.bizData[i].prefix + data.bizData[i].codeBegin +data.bizData[i].suffix  + '</option>';
								else
									option = '<option value=' + data.bizData[i].id + '>' + data.bizData[i].prefix + data.bizData[i].codeBegin +data.bizData[i].suffix + '</option>';
								total += option;
							}
							$('#building').html(total);
							refreshCellList(selectedOpts);		
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	}); 
			  	}
			 }
			 
			function refreshCellList(selectedOpts){
				if($("#building").find("option:selected").val() > 0){
				  	$.ajax({
						type: "post",
						url: "/ajax/getCellListByBuildingId.controller",
						data: {buildingId: $("#building").find("option:selected").val()},
						dataType: "json",
						contentType:'application/x-www-form-urlencoded; charset=UTF-8',
						success: function (data) {		
							var total = '<option value="">选择</option>';
							for(var i in data.bizData){
								var option; 
//								console.debug(selectedOpts['cell'] == data.bizData[i].id )
								if(typeof(selectedOpts) !== 'undefined' && typeof(selectedOpts['cell']) !== 'undefined'  && selectedOpts['cell'] == data.bizData[i].id ){
									option = '<option value=' + data.bizData[i].id + ' selected="selected">' + data.bizData[i].floorBegin + data.bizData[i].codeBegin + '</option>';
									$('#floorBegin').attr('value', data.bizData[i].floorBegin);
								}
								else
									option = '<option value=' + data.bizData[i].id + '>' + data.bizData[i].floorBegin + data.bizData[i].codeBegin + '</option>';
								total += option;
							}
							$('#cell').html(total);		
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	});
			  	} 
			 }	

		</script>
	</body>
</html>