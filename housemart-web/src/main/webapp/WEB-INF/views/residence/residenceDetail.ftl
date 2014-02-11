<#if type?exists && (type == 1 || type == -1)>

<title>小区详情</title>
<body>
<link href="/webresources/css/newsell.css" rel="stylesheet"/>
<#if entity?exists>
<div class="container hm-container">
	<h1 class="hm-page-title">
		小区详情
	</h1>
	<div class="row hm-inputs-wrap hm-no-bg">
		<div class="col-sm-5 hm-half-panel" style="padding-right:5px;">
			<div class="hm-bordered">
	           <table class="table table-striped table-hover">
	           		<col class="col-sm-3"/>
	           		<col/>
					<thead>
			            <tr>
			                <th>&nbsp;</th>
			                <th>&nbsp;</th>
			            </tr>
		            </thead>
		            <tbody>
		            	<tr>
			                <td><b>行政区</b></td>
			                <td>${entity.regionName}</td>
			            </tr>
			            <tr>
			                <td><b>板块</b></td>
			                <td>${entity.plateName}</td>
			            </tr>
			            <tr>
			                <td><b>小区名称</b></td>
			                <td>${entity.residenceName}</td>
			            </tr>
			            <tr>
			                <td><b>小区别名</b></td>
			                <td>${entity.aliasName!""}</td>
			            </tr>
			            <tr>
			                <td><b>图片个数</b></td>
			                <td>
			                	 ${(entity.picCount)!}
			                </td>
			            </tr>
			            <tr>
			                <td><b>总户数</b></td>
			                <td>${(entity.headCount)!}</td>
			            </tr>
			            <tr>
			                <td><b>停车位</b></td>
			                <td>${(entity.parking)!}</td>
			            </tr>
			            <tr>
			                <td><b>类型</b></td>
			                <td>${(entity.propertyType)!}</td>
			            </tr>
						<tr>
							<td><b>绿化率</b></td>
							<td>${(entity.greenRate)!}</td>
						</tr>
						<tr>
							<td><b>容积率</b></td>
							<td>${(entity.volumeRate)!}</td>
						</tr>
						
						
						<tr>
							<td><b>物业费</b></td>
							<td>
								${(entity.propertyFee)!}
							</td>
						</tr>
						
						<tr>
							<td><b>开发商</b></td>
							<td>${(entity.developer)!}</td>
						</tr>
						
						<tr>
							<td><b>竣工时间</b></td>
							<td>
								${(entity.finishedTime)!}
							</td>
						</tr>
						
						
						<tr>
							<td>&nbsp;</td>
							<td>
								<a class="btn btn-primary btn-lg" href="/editResidence.controller?residenceId=${entity.residenceId}">编辑基本信息</a>
							</td>
						</tr>			            
		            </tbody>	
				</table>
			</div>
		</div>
		<div class="col-sm-7 hm-half-panel" style="padding-left:5px;">
			<div class="hm-bordered">
				<div id="J_map" class="map-container hm-bordered"></div>	
			</div>
		</div>
	</div>
	<div class="container hm-inputs-wrap hm-bordered hm-imgs-wrap" id="J_imgsWrap">
        
    </div> 
</div>

<#if isAdmin?exists && isAdmin == true || isManager?exists && isManager == true>
	<div style="float:left;padding-left:40px;"><a target = "_blank" href="/addResidenceBuilding?residenceId=${entity.residenceId}">生成栋座</a></div>
</#if>
</#if>
<script type="text/tpl" id="J_infoWinTpl">
<div style="width: 310px; padding-right: 10px"> 
	<p><small class="text-left text-muted">标记当前经纬度为主要经纬度:</small></p>
	<p class="text-center"><b>(<%=x%>, <%=y%>)</b><p>
	<%if (isAuto){%>
	<p class="text-center"><%=name + (isMain == true ?'<span style="color:red">(主经纬度)</span>' : '(非主经纬度)')%></p>
	<%}%>
	<p class="text-right"><a data-action='save' data-is-auto="<%if(isAuto){%>1<%}else{%>0<%}%>" data-id="<%=id%>" data-lng="<%=y%>" data-lat="<%=x%>" href="###">点击设置主经纬度</a></p>
</div>
</script>
<script type="text/tpl" id="J_picsTpl">
	<ul class="hm-img-list">	
	<%for(i=0;i<list.length; i++){ %>
	<li>
		<!-- image -->
        <img class="img-thumbnail" src="<%= list[i].cloudUrl%>" data-img-id="<%=list[i].id%>" width="280" height="210"/>
        <small class="img-status <%if (list[i].showStatus){%>img-passed<%}%>"><%if (list[i].showStatus){%>已认证<%}else{%>待审核<%}%></small>
	</li>
	<%} %>	
	</ul>
</script>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?v=3&language=zh-CN&key=AIzaSyBItBTVpc3EwAxES2U6x6NNrALw1tlPxRA&sensor=false"></script>
<script src="/webresources/js/lib/sea.js"></script>
<script type="text/javascript">
__appConfig = {
	points: ${points},
	residenceId: '${entity.residenceId}',
	center: {lat: '${(avgPoint.lat)!31.23}' ,lng: '${(avgPoint.lng)!121.47}'}
};
seajs.config({
    base: "/webresources/js/",
    alias: {
        "jquery": "lib/jquery.js"
    }
});
</script>
<script>
	seajs.use(['jquery', 'header', 'residence/map']);
</script>  


</body>

<#else>

<title>小区详情</title>
<body>
<#if entity?exists>
<h3>${entity.residenceName}栋座信息>编辑信息</h3>

<ul class="page-nav">
	<li><a href="/editResidence.controller?residenceId=${entity.id}">1. 基本信息</a></li>
	<li class="focus">
		2. 设定坐标
	</li>
	<li>
	<#if isAdmin?exists && isAdmin == true || isManager?exists && isManager == true>
		<a href="/residencePicList.controller?residenceId=${entity.id}">3. 上传图片</a>
	<#else>
		<a href="/external/residencePicConsole.controller?residenceId=${entity.id}">3. 上传图片</a>
	</#if>
	</li>
</ul>

<div>
<table style="float:left">
<tr>
	<td>行政区：${entity.regionName}
	</td>
</tr>
<tr>
	<td>
		板块：${entity.plateName}
	</td>
</tr>
<tr>
	<td>
		小区名称：${entity.residenceName}
	</td>
</tr>
<tr>
	<td>
		小区别名：${entity.aliasName!""}
	</td>
</tr>
<tr>
	<td>
		${entity.address}
	</td>
</tr>
<tr>
	<td style="display:none;">
		小区图片个数: <a href="/residencePicList.controller?residenceId=${entity.residenceId}">添加图片</a>
	</td>
</tr>
<tr>
	<td>
		总户数：${(entity.headCount)!}
	</td>			
</tr>
<tr>
	<td>
		停车位：${(entity.parking)!}
	</td>			
</tr>
<tr>
	<td>
		类型：${(entity.propertyType)!}
	</td>			
</tr>
<tr>
	<td>
		绿化率：${(entity.greenRate)!}
	</td>			
</tr>
<tr>
	<td>
		容积率：${(entity.volumeRate)!}
	</td>			
</tr>
<tr>
	<td>
		物业费：${(entity.propertyFee)!}
	</td>			
</tr>
<tr>
	<td>
		开发商：${(entity.developer)!}
	</td>			
</tr>
<tr>
	<td>
		竣工时间：${(entity.finishedTime)!}
	</td>			
</tr>
	
<tr>
	<td style="display:none;">
		<a href="/editResidence.controller?residenceId=${entity.residenceId}">编辑基本信息项</a>
	</td>
</tr>
</table>
<#if isAdmin?exists && isAdmin == true || isManager?exists && isManager == true>
	<div style="float:left;padding-left:40px;"><a target = "_blank" href="/addResidenceBuilding?residenceId=${entity.residenceId}">生成栋座</a></div>
</#if>
<div id="map_canvas" style="float:left;margin-left:20px;width: 900px; height: 500px"></div>
<div style="clear:both"></div>
</div>

<#if !(isAdmin?exists && isAdmin == true || isManager?exists && isManager == true)>
<div>
	<h3>如何插坐标</h3>
	<p>
		<span>第一步：在地图上，找到小区位置，然后直接用鼠标点击，就会出现一个坐标</span>
		</br>
		<img src="/resources/images/flag_01.jpg">
		</br>
		<span>第二步：点击小红图标，将该坐标设定为主经纬度</span>
		</br>
		<img src="/resources/images/flag_02.jpg">
		</br>
	</p>
</div>
</#if>

<br/>

<#if isAdmin?exists && isAdmin == true || isManager?exists && isManager == true>
	<div id="buidingList">
	前缀:<input class="inputBox" type="text" id="filterPrefix"></input>
	几期:<select id="filterStage">
	<option selected>请选择</option><option>一期</option><option>二期</option><option>三期</option><option>四期</option>
	<option>五期</option><option>六期</option><option>七期</option><option>八期</option><option>九期</option><option>十期</option>
	</select>
	<button id="filter">筛选</button>
	<br /><br />
	<table>
	<thead><tr><th></th><th>栋座名称</th><th>几期</th><th>单元数</th><th>梯户数</th><th>类型</th><th>操作</th></tr></thead>
	<tbody id="buidingListBody">
	</tbody>
	</table>
	<br/>
	<button id="batchDelete">删除选中</button>&nbsp<button id="batchCheckRevert">清除选中</button>
	&nbsp<button id="batchSelect">选中全部</button>
	</div>
<#else>
	<button onclick="window.location='/external/residencePicConsole.controller?residenceId=${entity.residenceId}'">提交</button>
</#if>

<br/>
</#if>

<script>
var residenceId = ${entity.residenceId};
$(document).ready(function(){
   
   $('#batchCheckRevert').bind('click', function(){
   		$("input[name='checkList']").removeAttr("checked"); 
   });
 
    $('#batchSelect').bind('click', function(){
   		$("input[name='checkList']").attr({checked: "true"}); 
   });  
     
   $('#filter').bind('click', function(){
   		refreshBuildingList();
   });
   
   $('#batchDelete').bind('click', function(){
   		var arrChk = $("input[name='checkList']:checked");
   		var arrChkIds = '';
   		$(arrChk).each(function(){
   			if(arrChkIds == ''){
   				arrChkIds += this.value;
   			}else{
   				arrChkIds += ',' + this.value;
   			}			
   		});
		var answer = confirm("确定删除?");
		if(answer){
			$.ajax({
			type: "post",
			url: "/ajax/residenceBuildingDelete.controller",
			data: {id: arrChkIds},
			dataType: "json",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (data) {		
				refreshBuildingList();
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			}
		    });			
		}		
   });

});

var refreshBuildingList = function(){

		var prefix = $('#filterPrefix').val();
		var period = $("#filterStage").find("option:selected").text();
		if(period == '请选择'){
			period = '';
		}
		
		
		<#if isAdmin?exists && isAdmin == true || isManager?exists && isManager == true>
	  	$.ajax({
		type: "post",
		url: "/ajax/residenceBuildingList.controller",
		data: {id: residenceId,prefix:prefix, period:period},
		dataType: "json",
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success: function (data) {		
			$('#buidingListBody').html('');	
			var totalStr = ''
			for(i in data){
				
				var editQs = '/residenceBuildingEdit?residenceId=' + residenceId + '&buildingId=' + data[i].id;				
				totalStr += '<tr><td>' + '<input name="checkList" type="checkbox"' + ' value=' + data[i].id + ' />'
				 + '</td><td>';
				 totalStr += '<a href="' + '/residenceCellList?residenceId=' + residenceId + '&buildingId=' + data[i].id + '">';
				 var content = data[i].prefix == null ? data[i].codeBegin + data[i].suffix: data[i].prefix + data[i].codeBegin + data[i].suffix;
				 totalStr += content + '</a>';
				 totalStr += '</td><td>' + (data[i].period == null? '':data[i].period)
				 + '</td><td>' + data[i].cellCount
				 + '</td><td>' + data[i].stair + '梯' + data[i].houseHold + '户'
				 + '</td><td>' + data[i].buildingType
				 + '</td><td>' + '<a href="' + editQs + '">编辑</a>'
				 + '</td></tr>';		 
			}

			$('#buidingListBody').html(totalStr);
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		}
  	}); 
  	</#if>

};
refreshBuildingList();

</script>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?v=3&language=zh-CN&key=AIzaSyBItBTVpc3EwAxES2U6x6NNrALw1tlPxRA&sensor=false"></script>
<script>

	var initialize = function(){
		var points = ${points};
		var center;
	 
		//中心点
		var avgLat = 0;
	    var avgLng = 0;
	    for(var index in points){
	        avgLat += parseFloat(points[index].lat);
	        avgLng += parseFloat(points[index].lng);       
	    }
		if(points.length == 0){
			center = new google.maps.LatLng(31.197162,121.440599);
	    } else {
	     	center = new google.maps.LatLng(avgLat * 1.0/points.length ,avgLng * 1.0/points.length);
	    }
	    var map = new google.maps.Map(document.getElementById("map_canvas"),{
	    	center: center,
	        zoom: 13,
	        mapTypeId: google.maps.MapTypeId.ROADMAP
	     });  
	         
	     for(var index in points){
	        
	        var marker = new google.maps.Marker({
	            position: new google.maps.LatLng(parseFloat(points[index].lat), parseFloat(points[index].lng)),
	            map: map
	        });
	        
			var label = points[index].isMain == true ?'<span style="color:red">(主经纬度)</span>' : '(非主经纬度)';
	        var WINDOW_HTML = '<div style="width: 220px; padding-right: 10px">' 
	        + points[index].name + label
	        + '<br/><a id="' + points[index].id +'" href="#" onclick="confirmPosition(this);return false;">点击此按钮设置主主经纬度</a>' + '</div>';
	        marker.html = WINDOW_HTML;
	        google.maps.event.addListener(marker, "click", function() { 
	        var infowindow = new google.maps.InfoWindow({
	      		content: this.html
	  		});
			infowindow.open(map,this);   
			});  
	     
	     }
	     
	     //手动添加地标
	     google.maps.event.addListener(map, 'click', function(event) {         
	         var marker = new google.maps.Marker({
	      position: event.latLng,
	      map: map
	  	 });
		 var WINDOW_HTML = '标记当前经纬度为主要经纬度:(' + event.latLng.lat() + ',' + event.latLng.lng() + ')' +
		 '<br/><a id="' + points[index].id + '"' +' lat=' + event.latLng.lat() + ' lng=' + event.latLng.lng() +    
		 ' href="#" onclick="confirmPositionMannual(this);return false;">点击此按钮设置主主经纬度</a>';
		 marker.html = WINDOW_HTML;	  
	 	  
	 	 google.maps.event.addListener(marker, "click", function(event) { 
			 var infowindow = new google.maps.InfoWindow({
		       content: this.html
		  	 });
		 	 infowindow.open(map,this);     
		     });  
	     });
	}
	google.maps.event.addDomListener(window, 'load', initialize);

	var confirmPosition = function(obj){
		$.ajax({
		type: "post",
		url: "/ajax/confirmResidencePosition.controller",
		data: {residenceId: residenceId,mapPlaceId: obj.id},
		dataType: "json",
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success: function (data) {	
		<#if isAdmin?exists && isAdmin == true || isManager?exists && isManager == true>
			window.location = "/residencePicList.controller?residenceId=" + residenceId;
		<#else>
			window.location = "/external/residencePicConsole.controller?residenceId=" + residenceId;
		</#if>
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		}
  		}); 
	}

	var confirmPositionMannual = function(obj){
		$.ajax({
		type: "post",
		url: "/ajax/confirmResidencePositionMannally.controller",
		data: {residenceId: residenceId, lat: obj.getAttribute('lat'), lng: obj.getAttribute('lng')},
		dataType: "json",
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success: function (data) {	
			<#if isAdmin?exists && isAdmin == true || isManager?exists && isManager == true>
				window.location = "/residencePicList.controller?residenceId=" + residenceId;
			<#else>
				window.location = "/external/residencePicConsole.controller?residenceId=" + residenceId;
			</#if>
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		}
  		}); 
	}	

</script>

</body>

</#if>
