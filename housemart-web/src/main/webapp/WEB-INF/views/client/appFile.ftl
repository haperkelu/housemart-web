<title>客户端上传</title>
<body>
<br/>
<div>
	<h1>客户端上传</h1>
	<div style="width:700px;">
		<div>${(sysMsg)!}</div>
		<h2>大众版-Android</h2>
		<form action="/client/appFile/add.controller" method="post" enctype="multipart/form-data">
			<input type="hidden" name="clientType" value="p"/>
			<table>
				<tr>
					<td>
						最新版本
					</td>
					<td>
						<input type="text" name="version"/> (*必填)
					</td>
				</tr>
				<tr>
					<td>
						安装文件
					</td>
					<td>
						<input name="appFile" type="file"/> (*必填)
					</td>
				</tr>
				<tr>
					<td>
						上传记录
					</td>
					<td>
						<#if popItems??>
							<#list popItems as item>
								ID：${item.id} - 版本号：${item.version} - 下载地址：<a href="${item.cloudURL}">下载</a> - 上传日期：<#if item.addTime??>${item.addTime?string("yyyy-MM-dd:hh")}</#if>    
								<br/>
							</#list>
						</#if>
					</td>
				</tr>
			</table>
			
			<br>
			<input type="submit" value="提交"/>
		</form>
		
		
	</div>
	<div style="width:700px;">
		<h2>经纪人版-Android</h2>
		<form action="/client/appFile/add.controller" method="post" enctype="multipart/form-data">
			<input type="hidden" name="clientType" value="b"/>
			<table>
				<tr>
					<td>
						最新版本
					</td>
					<td>
						<input type="text" name="version"/> (*必填)
					</td>
				</tr>
				<tr>
					<td>
						安装文件
					</td>
					<td>
						<input name="appFile" type="file"/>  (*必填)
					</td>
				</tr>
				<tr>
					<td>
						上传记录
					</td>
					<td>
						<#if brokerItems??>
							<#list brokerItems as item>
								ID：${item.id} - 版本号：${item.version} - 下载地址：<a href="${item.cloudURL}">下载</a> - 上传日期：<#if item.addTime??>${item.addTime?string("yyyy-MM-dd:hh")}</#if>    
								<br/>
							</#list>
						</#if>
					</td>
				</tr>
			</table>
			
			<br>
			<input type="submit" value="提交"/>
		</form>
	</div>
</div>
<br/>
</body>
	