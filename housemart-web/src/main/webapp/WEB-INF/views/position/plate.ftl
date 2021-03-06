<title>设置行政区经纬度</title>
<body>


<div>
城市: <select id="city" name="city" style="width:50px"><option value="1">上海</option><option value="2">南加州</option><option value="3">北加州</option></select>
&nbsp<#include "/common/regionSelect.ftl"></div>

<br/>
<div id="map_canvas" style="float:left;margin-left:20px;width: 500px; height: 300px"></div>
<div style="clear:both"></div>

<script>
	var currentCity = ${cityId};
	var currentRegion = ${regionId};
	var currentPlate = ${plateId};
	$('#city option').each(function(){
		if($(this).val() == currentCity){
			$(this).attr('selected', true);
		}
	});	
	$(document).ready(function(){	


		$('#city').change(function(){ 
			location.href = location.pathname + '?cityId=' + $("#city").find("option:selected").val() + '&regionId=0&plateId=0';
		});			
		$('#region').change(function(){ 
			location.href = location.pathname + '?cityId=' + $("#city").find("option:selected").val() + '&regionId='  + $("#region").find("option:selected").val()  +'&plateId=0';
		});
		$('#plate').change(function(){
			location.href = location.pathname + '?cityId=' + $("#city").find("option:selected").val() + '&regionId='  + $("#region").find("option:selected").val()  +'&plateId=' + $("#plate").find("option:selected").val();
		 });
		
	});
	$.ajax({
		type: "post",
		url: "/ajax/getRegionListByCityId.controller",
		data: {cityId:currentCity},
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
				var isSet = false;
				for(var i in data.bizData){
					if(data.bizData[i].id == currentPlate){
						isSet = true;
					}
				}
				for(var i in data.bizData){
					var selected = ' ';
					if(isSet == false && i == 0){
						selected += 'selected';
						currentPlate = data.bizData[i].id;
					}
					if(isSet == true && data.bizData[i].id == currentPlate){
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
  	
  	}	
	

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
        
		var label = '<span style="color:red">(主经纬度)</span>';
        var WINDOW_HTML = label;
        marker.html = WINDOW_HTML;	
        google.maps.event.addListener(marker, "click", function() { 
	        var infowindow = new google.maps.InfoWindow({
	      		content: marker.html
	  		});
			infowindow.open(map,marker);   
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
 		 google.maps.event.addListener(marker, "click", function() { 
			var infowindow = new google.maps.InfoWindow({
	      		content: marker.html
	  		});
			infowindow.open(map,marker);     
		 }); 		 
     });
	
	
	}  
   
   	var confirmPositionMannual = function(obj){
		$.ajax({
		type: "post",
		url: "/regionSet/confirmPositionMannally.controller",
		data: {id: obj.id, lat: obj.getAttribute('lat'), lng: obj.getAttribute('lng'), type:3, cityId:currentCity, positionId: currentPlate},
		dataType: "json",
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success: function (data) {	
			window.location.reload();	
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		}
  		}); 
	}
	google.maps.event.addDomListener(window, 'load', initialize);

</script>

</body>