<html>
	<head></head>	
	<title>房源照片</title>
	<body>
		<div>
			<#include "/house/houseNav.ftl">
		</div>
		<br>
		<hr>
		<a href="/repository/getHousePicList.controller?residenceId=${(house.residenceId)!}&houseId=${(house.id)!}">>>>进入图片库>>></a>
		<br>
		<hr>
		<div style="width:800px;height:500px;">
			<div class="view" style="width:450px;" >
			</div>	
			<div class="update" style="width:450px;">
				<form id="uploadForm" action="/housePicUpload.controller" name="uploadForm" method="POST" enctype="multipart/form-data">  
					<input id="houseId" name="houseId" type="hidden" value="${(house.id)!}"/>
					<input id="houseType" name="houseType" type="hidden" value="3"/>
					<input name="imageFile" type="file"/>  
					<input type="submit" value="submit"/>
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
							pic =  '<p><img src="' + data.bizData[i].cloudUrl 
								+ '" width="400" height="300" /><br><a href="/removePic.controller?id=' 
								+ data.bizData[i].id 
								+ '" onclick="return confirm(\'是否将此图片删除?\')">删除此图片</a></p>';
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