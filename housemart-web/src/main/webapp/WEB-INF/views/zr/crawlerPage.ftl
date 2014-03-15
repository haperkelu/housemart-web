<html>
	<head></head>	
	<title>抓取加州房源</title>
	<body>
		<br>
		<hr>
		<div>
			<div class="view">
			</div>	
			<div class="update">
				<form id="crawlForm" action="/zr/crawlHouse.controller" name="crawlForm" method="POST">  
					链接：<input name="url" type="text"/> <span>例如：http://www.ziprealty.com/homes-for-sale/list/oc/by-city/Irvine,CA/detailed</span>  
					<br/>
					<input type="submit" value="抓取链接"/>
				</form>
			</div>
			<br>					
			<hr>
			<div class="update">
				<form id="crawlForm" action="/zr/crawlTask.controller" name="crawlForm" method="POST">  
					<input type="submit" value="重新执行后台抓取任务"/>
				</form>
			</div>				
		</div>
	</body>
</html>