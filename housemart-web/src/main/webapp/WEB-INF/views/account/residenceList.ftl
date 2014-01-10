<title>楼盘管理 </title>
<body>
<br/>
<div>
	<#include "/common/regionSelect.ftl"><button id="submit">筛选</button>
</div>
<br/>
<table>
	<thead>
		<tr><th>区域</th><th>板块</th><th>楼盘</th><th>经纪人</th><th>区域经理</th><th>操作</th></tr>
	</thead>
	<tbody>
		<#list list as item> 
		<tr>
			<td>${item.regionName}</td>
			<td>${item.plateName}</td>
			<td>${item.residenceName}</td>
			<td>
				<#if item.agents??>
					<#list item.agents as account>
				${account.name}
					</#list>
				</#if> 
			</td>
			<td>
				<#if item.managers??>
					<#list item.managers as account>
				${account.name}
					</#list>
				</#if> 
			</td>
			<td>
				<a href="setResidenceManager.controller?id=${item.residenceId}">变更区域经理</a>
				<a href="setResidenceAgent.controller?id=${item.residenceId}">变更经纪人</a>
			</td>
		</tr>
		</#list>
	</tbody>
	</table>
	<script>
		
		var currentRegion = ${regionId};
		var currentPlate = ${plateId};
	
		$(document).ready(function(){		
			$('#region').change(function(){ 
				refreshPlateList();
			});
			
			$('#submit').click(function(){
				location.href = location.pathname + '?regionId=' + $("#region").find("option:selected").val()  +'&plateId=' + $("#plate").find("option:selected").val();
			});
		});
	
		$.ajax({
			type: "post",
			url: "/ajax/getRegionList.controller",
			data: {},
			dataType: "json",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (data) {		
				var total = '';
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
	  	
		  	$.ajax({
				type: "post",
				url: "/ajax/getPlateList.controller",
				data: {parentId: $("#region").find("option:selected").val()},
				dataType: "json",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (data) {		
					var total = '';
					for(var i in data.bizData){
						var selected = ' ';
						if(data.bizData[i].id == currentPlate){
							selected += 'selected';
						}
						var option = '<option value=' + data.bizData[i].id + selected + '>' + data.bizData[i].name + '</option>';
						total += option;
					}
					$('#plate').html(total);		
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
				}
		  	}); 
	  	
	  	}	
	
	</script>
</body>