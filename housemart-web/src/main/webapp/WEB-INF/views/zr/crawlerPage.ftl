<html>
	<head></head>	
	<title>抓取加州房源</title>
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
				<form id="crawlForm" action="/zr/crawlHouse.controller" name="crawlForm" method="POST">  
					<input name="url" type="text"/>  
					<input type="submit" value="submit"/>
				</form>
			</div>				
		</div>
	</body>
</html>