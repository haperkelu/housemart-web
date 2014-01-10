<title>账号管理</title>
<body>
<link rel="stylesheet" href="resources/css/account.css" />
<script src="resources/javascript/account.js" type="text/javascript"></script>

<div class="table-list-div">
	<table class="table">
		<tr>
			<th>申请类型序</th>
			<th>区域经理名</th>
			<th>负责楼盘</th>
			<th>经理电话</th>
			<th>经纪人</th>
			<th>操作</th>
			<th>申请时间</th>
		</tr>
		<#if list??>
			<#list list as item> 
		<tr>
			<td>注销申请</td>
			<td>${(item.managerName)!}</td>
			<td></td>
			<td>${(item.managerPhone)!}</td>
			<td id="account-name-${item.id}">${(item.accountName)!} ${(item.accountLoginName)!}</td>
			<td>
				<#if item.status == 0>
				<a href="javascript:void(0)" onclick="revokeAccount(${item.id}, 1)">注销</a>
				<a href="javascript:void(0)" onclick="revokeAccount(${item.id}, 2)">拒绝</a>
				<#elseif item.status == 1>
				已注销
				<#elseif item.status == 2>
				已拒绝
				</#if>
			</td>
			<td>
				<#if item.addTime??>${item.addTime?string("yyyy-MM-dd HH:mm")}</#if>
			</td>
		</tr>
			</#list>
		</#if>	
	</table>
</div>

<div id="dlg-revoke-account" title="注销申请">
    <div class="dlg-content">
    	<span id="dlg-revoke-msg"></span>
    </div>
    <div class="dlg-buttons">
    	<input type="button" value="确定" onclick='doRevokeAccount()' />
    	<input type="button" value="取消" onclick='$("#dlg-revoke-account").dialog("close")'/>
    	<input type="hidden" value="" id="dlg-revoke-id" />
    	<input type="hidden" value="" id="dlg-revoke-status" />
    </div>
</div>

<script type="text/javascript">
	$("#dlg-revoke-account").dialog(
		{
			autoOpen: false,
			width: 500,
			modal: true,
			resizable: false
		});
</script>
</body>