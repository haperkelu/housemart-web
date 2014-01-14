﻿<title>小区列表 </title>
<body>
<br/>
	<div class="col-sm-3">
	<label class="col-sm-3 control-label" for="J_positionSet">设置坐标</label>
	<select id="J_positionSet" name="positionSet" data-original-title="全部" class="form-control">
		<option <#if positionSet?? && positionSet == ''>selected= "selected"</#if> value="">全部</option>
		<option <#if positionSet?? && positionSet == '1'>selected= "selected"</#if> value="1">是</option>
		<option <#if positionSet?? && positionSet == '0'>selected= "selected"</#if> value="0">否</option>
	</select>
	<label class="col-sm-3 control-label" for="J_forceShow">强制显示</label>
	<select id="J_forceShow" name="forceShow" data-original-title="全部" class="form-control">
		<option <#if forceShow?? && forceShow == ''>selected= "selected"</#if> value="">全部</option>
		<option <#if forceShow?? && forceShow == '1'>selected= "selected"</#if> value="1">是</option>
		<option <#if forceShow?? && forceShow == '0'>selected= "selected"</#if> value="0">否</option>
	</select>
	<label class="col-sm-3 control-label" for="J_zombie">暗小区</label>
	<select id="J_zombie" name="zombie" data-original-title="全部" class="form-control">
		<option <#if zombie?? && zombie == ''>selected= "selected"</#if> value="">全部</option>
		<option <#if zombie?? && zombie == '1'>selected= "selected"</#if> value="1">是</option>
		<option <#if zombie?? && zombie == '0'>selected= "selected"</#if> value="0">否</option>
	</select>
	<label class="col-sm-3 control-label" for="J_lockBasicInfo">锁定基本信息</label>
	<select id="J_lockBasicInfo" name="lockBasicInfo" data-original-title="全部" class="form-control">
		<option <#if lockBasicInfo?? && lockBasicInfo == ''>selected= "selected"</#if> value="">全部</option>
		<option <#if lockBasicInfo?? && lockBasicInfo == '1'>selected= "selected"</#if> value="1">是</option>
		<option <#if lockBasicInfo?? && lockBasicInfo == '0'>selected= "selected"</#if> value="0">否</option>
	</select>

	<label class="col-sm-3 control-label" for="J_lockMap">锁定坐标</label>
	<select id="J_lockMap" name="lockMap" data-original-title="全部" class="form-control">
		<option <#if lockMap?? && lockMap == ''>selected= "selected"</#if> value="">全部</option>
		<option <#if lockMap?? && lockMap == '1'>selected= "selected"</#if> value="1">是</option>
		<option <#if lockMap?? && lockMap == '0'>selected= "selected"</#if> value="0">否</option>
	</select>

	<label class="col-sm-3 control-label" for="J_lockPic">锁定图片</label>
	<select id="J_lockPic" name="lockPic" data-original-title="全部" class="form-control">
		<option <#if lockPic?? && lockPic == ''>selected= "selected"</#if> value="">全部</option>
		<option <#if lockPic?? && lockPic == '1'>selected= "selected"</#if> value="1">是</option>
		<option <#if lockPic?? && lockPic == '0'>selected= "selected"</#if> value="0">否</option>
	</select>
	<label class="col-sm-3 control-label" for="J_hasPic">是否有有效图片</label>
	<select id="J_hasPic" name="hasPic" data-original-title="全部" class="form-control">
		<option <#if hasPic?? && hasPic == ''>selected= "selected"</#if> value="">全部</option>
		<option <#if hasPic?? && hasPic == '1'>selected= "selected"</#if> value="1">是</option>
		<option <#if hasPic?? && hasPic == '0'>selected= "selected"</#if> value="0">否</option>
	</select>

	<label class="col-sm-3 control-label" for="J_picApprove">图片待审核</label>
	<select id="J_picApprove" name="picApprove" data-original-title="全部" class="form-control">
		<option <#if picApprove?? && picApprove == ''>selected= "selected"</#if> value="">全部</option>
		<option <#if picApprove?? && picApprove == '1'>selected= "selected"</#if> value="1">是</option>
		<option <#if picApprove?? && picApprove == '0'>selected= "selected"</#if> value="0">否</option>
	</select>
	</div>

<div>
<#include "/common/regionSelect.ftl">
楼盘<input type="text" value="${(residenceName)!}" id="maskName"/><span style="color:red;">（楼板名称：静安新城 或者 静安）</span>
<input type="hidden" name="residenceName" id="residenceName"/>
<button id="submit">筛选</button>
<button id="export">导出此行政区或者版块的小区</button>
</div>
<br/>
<div>
<a href="/editResidence.controller?regionId=${regionId}&plateId=${(plateId)!}" target="_blank">新建此版块的小区</a>
</div>
<br/>
<table>
<thead><tr><th>区域</th><th>板块</th><th>小区名称</th><th>栋座数</th><th>单元数</th><th>是否设置地标</th><th>有效图片数量</th><th>是否强制显示</th><th>是否暗小区</th><th>是否锁定基本信息</th><th>是否暗锁定地图</th><th>是否锁定图片</th></tr></thead>
<tbody>
<#list list as item> 
	<tr>
		<td>${item.regionName}</td>
		<td>${item.plateName}</td>
		<td><a href="/residence/${item.residenceId}">${item.residenceName}</a></td>
		<td>${item.buildingCount}</td>
		<td>${item.cellCount}</td>
		<td> <#if item.positionSet?exists & item.positionSet == true><span style="color:blue;">是</span><#else><span style="color:red">否</span></#if></td>
		<td>${item.picCount}</td>
		<td> <#if item.forceShow?exists & item.forceShow == 1><span style="color:blue;">是</span><#else><span style="color:red;">否</span></#if></td>
		<td> <#if item.zombie?exists & item.zombie == 1><span style="color:blue;">是</span><#else><span style="color:red;">否</span></#if></td>
		<td> <#if item.lockBasicInfo?exists & item.lockBasicInfo == 1><span style="color:blue;">是</span><#else><span style="color:red;">否</span></#if></td>
		<td> <#if item.lockMap?exists & item.lockMap == 1><span style="color:blue;">是</span><#else><span style="color:red;">否</span></#if></td>
		<td> <#if item.lockPic?exists & item.lockPic == 1><span style="color:blue;">是</span><#else><span style="color:red;">否</span></#if></td>
	</tr>
</#list>
</tbody>
</table>
<script>
	var currentRegion = <#if regionId?? && regionId != ''>${(regionId)!}<#else>''</#if>;
	var currentPlate = <#if plateId?? && plateId != ''>${(plateId)!}<#else>''</#if>;
	$(document).ready(function(){		
		$('#region').change(function(){ 
			refreshPlateList();
		});
		
		$('#submit').click(function(){
			$('#residenceName').val(encodeURI(encodeURI($('#maskName').val())));
			location.href = location.pathname + '?positionSet='  + $("#J_positionSet").find("option:selected").val()+'&forceShow='+ $("#J_forceShow").find("option:selected").val()  +'&zombie=' + $("#J_zombie").find("option:selected").val()  +'&lockBasicInfo='  + $("#J_lockBasicInfo").find("option:selected").val()  +'&lockMap='  + $("#J_lockMap").find("option:selected").val()+'&lockPic='  + $("#J_lockPic").find("option:selected").val()  +'&hasPic='  + $("#J_hasPic").find("option:selected").val()+'&picApprove='  + $("#J_picApprove").find("option:selected").val() +'&regionId='  + $("#region").find("option:selected").val()  +'&plateId=' + $("#plate").find("option:selected").val() + '&residenceName=' + $("#residenceName").val();
		});
		
		$('#export').click(function(){
			location.href = '/residenceExport.controller?regionId='  + $("#region").find("option:selected").val()  +'&plateId=' + $("#plate").find("option:selected").val();
		});
	});

	$.ajax({
		type: "post",
		url: "/ajax/getRegionList.controller",
		data: {},
		dataType: "json",
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success: function (data) {		
			var total = '<option value="">选择</option>';
			for(var i in data.bizData){
				var selected = ' ';
				if(data.bizData[i].id == currentRegion){
					selected += 'selected';
				}
				var option = '<option value=' + data.bizData[i].id + selected + '>' + data.bizData[i].name + '</option>';
				total += option;
			}
			$('#region').html(total);
			refreshPlateList();		
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		}
  	}); 
  	
  	var refreshPlateList = function(){
  		//console.debug(currentRegion != '' && $("#region").find("option:selected").val() != '');
  		if(currentRegion != '' && $("#region").find("option:selected").val() != ''){
		  	$.ajax({
				type: "post",
				url: "/ajax/getPlateList.controller",
				data: {parentId: $("#region").find("option:selected").val()},
				dataType: "json",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (data) {		
					var total = '<option value="">选择</option>';
					for(var i in data.bizData){
						var selected = ' ';
						if(data.bizData[i].id == currentPlate){
							selected += 'selected';
						}
						var option = '<option value=' + data.bizData[i].id + selected  + '>' + data.bizData[i].name + '</option>';
						total += option;
					}
					$('#plate').html(total);		
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
				}
		  	}); 
  		}else{
  			var total = '<option value="">选择</option>';
  			$('#plate').html(total);	
  		}
  	}	

</script>
</body>