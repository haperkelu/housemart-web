<html>
	<head>
	</head>	
	<title>新建小区</title>
	<body>
		
		<form action="/external/myResidenceCheckList.controller" method="get">
	    <div class="container hm-container no-padding-top">
			<h1 class="hm-page-title">请确定负责小区: <input type="text" name="keyword"></input> &nbsp &nbsp <input type="submit" text="搜索"/></h1>
		
			<#if list?exists>
				
				<table>
				<th>名称</th><th>板块</th><th>区域</th><th>操作</th>
				<#list list as item>
				<tr>
				<td>${item.residenceName}</td>
				<td>${item.regionName}</td>
				<td>${item.plateName}</td>
				<td><a href="">申请负责该小区</a></td></tr>
				</#list>
				</table>
			
			</#if>
			
			<#if list?exists>
			<br/><br/>
			<div id="hint" style="">搜索结果么有？新建一个？<a href="/editResidence.controller?plateId=0&regionId=0">新建小区</a></div>
			</#if>
		</div>		
		
		</form>
		
	</body>
</html>