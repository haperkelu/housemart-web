<body>
<div id="map_canvas" style="width: 500px; height: 300px"></div>  

<script src="http://ditu.google.cn/maps?file=api&v=2.x&hl=zh-CN&key=AIzaSyBItBTVpc3EwAxES2U6x6NNrALw1tlPxRA"
type="text/javascript"></script>  
<script>

	if (GBrowserIsCompatible()) {  
     map = new GMap2(document.getElementById("map_canvas"));  
     map.setCenter(new GLatLng(31.197162,121.440599), 13);  
     map.setUIToDefault();
     
     var point = new GLatLng(31.197162,121.440599);
     map.addOverlay(new GMarker(point));
     
   }  

</script>
</body>