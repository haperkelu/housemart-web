<html>
	<head></head>	
	<title>房屋图片库(${(residenceName)!})</title>
	<body>
		<h3>房屋图片库(${(residenceName)!})</h3>
		<a href="/housePicConsole.controller?houseId=${(houseId)!}"><<<返回房屋图片列表<<<</a>
		
		<br>
		<hr>
		<br>
		<div>
		<form id="housePicList" action="/repository/getHousePicList.controller" method="GET">
			<#include "/repository/selectOptions.ftl">
		</form>
		
		<br>
		<hr>
		<br>
		共搜索到<span style="color:red;">${(size)!}</span>张图片
		<form id="uploadForm" action="/repository/bindHousePics.controller" name="uploadForm" method="POST">  
			<#include "/repository/bindPicForm.ftl">
			<input type="submit" onclick="uploadForm.action='/repository/deleteHousePics.controller';uploadForm.submit();" value="删除图片"/>
		</form>
		</div>
		
	</body>
</html>