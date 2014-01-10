<title>客户端版本</title>
<body>
<br/>
<div>
	<h1>客户端版本</h1>
	<div style="width:250px;">
		<h2>大众版-IOS</h2>
		<form action="/client/version/update.controller" method="post">
			<input type="hidden" name="osType" value="i"/>
			<input type="hidden" name="clientType" value="p"/>
			<table>
				<tr>
					<td>
						最新版本
					</td>
					<td><input type="text" name="currentVersion" value="${(client_version_p_i.currentVersion)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						最新版本更新内容
					</td>
					<td><textarea name="currentVersionInfo">${(client_version_p_i.currentVersionInfo)!}</textarea>
					</td>
				</tr>
				<tr>
					<td>
						最低版本
					</td>
					<td><input type="text" name="requiredVersion" value="${(client_version_p_i.requiredVersion)!}"/>
					</td>
				</tr>
			</table>
			
			<br>
			
			<input type="submit" value="提交更新"/>
		</form>
	</div>
	<div style="width:250px;">
		<h2>大众版-Android</h2>
		<form action="/client/version/update.controller" method="post">
			<input type="hidden" name="osType" value="a"/>
			<input type="hidden" name="clientType" value="p"/>
			<table>
				<tr>
					<td>
						最新版本
					</td>
					<td><input type="text" name="currentVersion" value="${(client_version_p_a.currentVersion)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						最新版本更新内容
					</td>
					<td><textarea name="currentVersionInfo">${(client_version_p_a.currentVersionInfo)!}</textarea>
					</td>
				</tr>
				<tr>
					<td>
						最低版本
					</td>
					<td><input type="text" name="requiredVersion" value="${(client_version_p_a.requiredVersion)!}"/>
					</td>
				</tr>
			</table>
			
			<br>
			<input type="submit" value="提交更新"/>
		</form>
	</div>
	<div style="width:250px;">
		<h2>经纪人版-IOS</h2>
		<form action="/client/version/update.controller" method="post">
			<input type="hidden" name="osType" value="i"/>
			<input type="hidden" name="clientType" value="b"/>
			<table>
				<tr>
					<td>
						最新版本
					</td>
					<td><input type="text" name="currentVersion" value="${(client_version_b_i.currentVersion)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						最新版本更新内容
					</td>
					<td><textarea name="currentVersionInfo">${(client_version_b_i.currentVersionInfo)!}</textarea>
					</td>
				</tr>
				<tr>
					<td>
						最低版本
					</td>
					<td><input type="text" name="requiredVersion" value="${(client_version_b_i.requiredVersion)!}"/>
					</td>
				</tr>
			</table>
			
			<br>
			<input type="submit" value="提交更新"/>
		</form>
	</div>
	<div style="width:250px;">
		<h2>经纪人版-Android</h2>
		<form action="/client/version/update.controller" method="post">
			<input type="hidden" name="osType" value="a"/>
			<input type="hidden" name="clientType" value="b"/>
			<table>
				<tr>
					<td>
						最新版本
					</td>
					<td><input type="text" name="currentVersion" value="${(client_version_b_a.currentVersion)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						最新版本更新内容
					</td>
					<td><textarea name="currentVersionInfo">${(client_version_b_a.currentVersionInfo)!}</textarea>
					</td>
				</tr>
				<tr>
					<td>
						最低版本
					</td>
					<td><input type="text" name="requiredVersion" value="${(client_version_b_a.requiredVersion)!}"/>
					</td>
				</tr>
			</table>
			
			<br>
			<input type="submit" value="提交更新"/>
		</form>
	</div>
</div>
<br/>
</body>
	