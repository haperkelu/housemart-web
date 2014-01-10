<body>
	<h1>小区图片库(${(residenceName)!})</h1>
	<a href="/residencePicList.controller?residenceId=${(residenceId)!}"><<<返回小区图片列表<<<</a>
	<br>
	<hr>
	<div>
		<h3>抓取小区图片</h3>
		<form id="crawl" action="/repository/crawlResidencePics.controller" name="crawl" method="GET">
			<input type="hidden" name="residenceId" value="${(residenceId)!}" />
			安居客小区Id<input id="anjukeId" type="text" name="anjukeId"/>
			<input type="submit" value="立即抓取"/>说明：抓取过程可能持续几分钟，视网络状况而定，可重复抓取
		</form>
	</div>
	<hr>
	<div>
	共搜索到<span style="color:red;">${(size)!}</span>张图片
	<form id="upload" action="/repository/bindResidencePics.controller" name="uploadForm" method="POST">  
		<#include "/repository/bindPicForm.ftl">
		<input type="submit" onclick="uploadForm.action='/repository/deleteResidencePics.controller';uploadForm.submit();" value="删除图片"/>
	</form>
	</div>
	
</body>