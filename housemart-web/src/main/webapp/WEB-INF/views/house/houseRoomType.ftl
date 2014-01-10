<html>
	<head></head>	
	<title>房型图</title>
	<body>
		<div>
			<#include "/house/houseNav.ftl">
		</div>
		<br>
		<hr>
		<a href="/repository/getRoomTypePicList.controller?residenceId=${(house.residenceId)!}&houseId=${(house.id)!}">>>>进入图片库>>></a>&nbsp;&nbsp;&nbsp;&nbsp;
		<br>
		<hr>
		<div style="width:800px;height:500px;">
			<div class="left" style="width:450px;height:400px;display:inline;margin:0 50px 0 0;float:left;">
				<form id="uploadForm" action="/housePicUpload.controller" name="uploadForm" method="POST" enctype="multipart/form-data">  
					<input id="houseId" name="houseId" type="hidden" value="${(house.id)!}"/>
					<input id="houseType" name="houseType" type="hidden" value="2"/>
					<input name="imageFile" type="file"/>  
					<input type="submit" value="submit"/>
				</form>
			</div>
			<div class="right" style="width:250px;height:400px;overflow-y:scroll;display:inline ;margin:0 50px 0 0;float:left;" >
			</div>					
		</div>
		<script>
			$(document).ready(function(){
				refreshRoomTypeList();
			});
			
			function refreshRoomTypeList(){
			  	$.ajax({
					type: "post",
					url: "ajax/getPicListByHouseId.controller",
					data: {houseId: $('#houseId').val(),type: 2},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {	
						var html = '';
						for(var i in data.bizData){
							var pic; 
							pic = '<p><img src="' + data.bizData[i].cloudUrl 
								+ '" width="200" height="150" /><br><a href="/removePic.controller?id=' 
								+ data.bizData[i].id 
								+ '" onclick="return confirm(\'是否将此图片删除?\')">删除此图片</a></p>';
							html += pic;
						}
						$('.right').html(html);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	}); 
		  	}
		</script>
	</body>
</html>