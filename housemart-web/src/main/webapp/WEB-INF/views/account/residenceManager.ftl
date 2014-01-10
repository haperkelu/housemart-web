<title>变更区域经理</title>
<body>
<h2>
	变更区域经理
</h2>

<div>
	${residence.residenceName}
</div>
<div class="table-list-div">
	<form action="saveResidenceAccounts.controller" method="POST">
	<table class="table">
		<tr>
			<th>&nbsp;</th>
			<th>邮箱</th>
			<th>姓名</th>
			<th>负责楼盘份</th>
		</tr>
		<#assign manager_map = "">   
		<#if residence.managers??>
			<#list residence.managers as account> 
			<#assign manager_map = manager_map + "_" + account.id + "_" >
		<tr>
			<td><input name="accountID" type="checkbox" value="${(account.id)!}" checked="checked" /></td>
			<td>${(account.loginName)}</td>
			<td>${(account.name)}</td>
			<td></td>
		</tr>
			</#list>
		</#if>
		<#if accountList??>
			<#list accountList as account>
			<#if !manager_map?contains("_" + account.id + "_")>
		<tr>
			<td><input name="accountID" type="checkbox" value="${(account.id)!}" /></td>
			<td>${(account.loginName)}</td>
			<td>${(account.name)}</td>
			<td></td>
		</tr>
			</#if>
			</#list>
		</#if>
	</table>
	<div>
		<input type="submit" value="确 定" />
		<input type="hidden" name="residenceID" value="${residence.residenceId}"/>
		<input type="hidden" name="positionType" value="区域经理"/>
	</div>
	</form>
</div>
</body>