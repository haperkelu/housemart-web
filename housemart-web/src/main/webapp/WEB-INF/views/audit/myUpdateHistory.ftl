<html>
	<head>
	</head>	
	<title>我的更新</title>
	<body>
		<div style="width:1024px;">
			<table>
			<tr>
				<th>日期</th><th>修改前</th><th>修改后</th>
			</tr>
			<#if list??>
				<#list list as auditHistory>
					<tr>
						<td><#if auditHistory.updateTime??>${auditHistory.updateTime?string("yyyy-MM-dd")}</#if></td>
						<td>${auditHistory.preContent!}</td>
						<td>${auditHistory.postContent!}</td>
					</tr>
				</#list>
			</#if>
			</table>
		</div>
	</body>
</html>
