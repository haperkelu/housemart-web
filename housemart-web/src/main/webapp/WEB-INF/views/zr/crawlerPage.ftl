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
			<h3>任务状态</h3>
			<div class="update">
				<h3>执行中的任务</h3>
				<table>
						<tr>
							<th>开始时间</th><th>任务</th>
						</tr>
				<#if runningTasks??>
					<#list runningTasks as task>
						<tr>
							<td><#if task.start??>${task.start?string("yyyy-MM-dd:mm:ss")}</#if></td>
							<td>${(task.name)!}</td>
						</tr>
					</#list>
				</#if>
				</table>
			</div>	
			<div class="update">
				<h3>完成的任务</h3>
				<table>
						<tr>
							<th>开始时间</th><th>完成时间</th><th>任务</th>
						</tr>
				<#if finishedTasks??>
					<#list finishedTasks as task>
						<tr>
							<td><#if task.start??>${task.start?string("yyyy-MM-dd:mm:ss")}</#if></td>
							<td><#if task.end??>${task.end?string("yyyy-MM-dd:mm:ss")}</#if></td>
							<td>${(task.name)!}</td>
						</tr>
					</#list>
				</#if>
				</table>
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