<html>
	<head></head>	
	<title>房源照片</title>
	<body>
		<div>
			<a href="soldHouseDetail.controller?houseId=${(house.id)!}">基本信息</a>
			<a href="soldHousePicConsole.controller?houseId=${(house.id)!}">房屋照片</a>
		</div>
		<hr/>

		<div style="width:800px;height:500px;">
			<div class="view" style="width:450px;" >
			</div>	
			<div class="update" style="width:450px;">
				<form id="uploadForm" action="/housePicUpload.controller" name="uploadForm" method="POST" enctype="multipart/form-data">  
					<input id="houseId" name="houseId" type="hidden" value="${(house.id)!}"/>
					<input id="houseType" name="houseType" type="hidden" value="3"/>
				</form>
			</div>				
		</div>
		<script>
			$(document).ready(function(){
				refreshRoomPicList();
			});
			
			function refreshRoomPicList(){
			  	$.ajax({
					type: "post",
					url: "ajax/getPicListByHouseId.controller",
					data: {houseId: $('#houseId').val(),type: 3},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {	
						var html = '';
						for(var i in data.bizData){
							var pic; 
							pic = '<div><img src="' + data.bizData[i].url + '" width="400" height="300" /></div>';
							html += pic;
						}
						$('.view').html(html);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	}); 
		  	}
		</script>
	</body>
</html>