<title>设置城市经纬度</title>
<body>


<div>城市: <select id="city" name="city" style="width:50px"><option value="1">上海</option><option value="2">南加州</option><option value="3">北加州</option></select>
<br/>
<div id="map_canvas" style="float:left;margin-left:20px;width: 500px; height: 300px"></div>
<div style="clear:both"></div>

<script>
	var currentCity = ${cityid};
	$('#city option').each(function(){
		if($(this).val() == currentCity){
			$(this).attr('selected', true);
		}
	});
  	
  	$(document).ready(function(){
	  	$('#city').change(function(){
	  		location.href = location.pathname + '?cityId=' + $("#city").find("option:selected").val();
	  	});  					
	});

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
		data: {id: obj.id, lat: obj.getAttribute('lat'), lng: obj.getAttribute('lng'), type:1, cityId:currentCity, positionId: currentCity},
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