<html>
	<head></head>	
	<title>抓取加州房源</title>
	<body>
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
			<br>					
			<hr>
			<div class="update" style="width:450px;">
				<form id="crawlForm" action="/zr/crawlTask.controller" name="crawlForm" method="POST">  
					<input type="submit" value="启动后台任务"/>
				</form>
			</div>				
		</div>
	</body>
</html>