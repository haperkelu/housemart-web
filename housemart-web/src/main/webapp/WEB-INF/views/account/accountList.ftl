<title>账号管理</title>
<body>
<link rel="stylesheet" href="resources/css/account.css" />
<script src="resources/javascript/account.js" type="text/javascript"></script>
<div class="action">
	<a href="/accountEdit.controller">
		+分配账号
	</a>
	
	<a href="/accountExport.controller">
		+导出Excel
	</a>
	<a>|</a>
	<a href="javascript:void(0)" onclick="exportAccountHouses('sale')">
		+导出合作经纪人登盘统计 (售)
	</a>
	
	<a href="javascript:void(0)" onclick="exportAccountHouses('rent')">
		+导出合作经纪人登盘统计 (租)
	</a>
	
	<input type="checkbox" id="notShowAll" checked="checked" /> 不导出注销用户
</div>

<div class="action">
	<form action="accountList.controller" method="GET">
	用户名/姓名:
	<input type="textbox" name="keyword" value="${keyword}" />
	<input type="submit" value="搜索" /> 
	<input type="button" value="全部" onclick="location.href='accountList.controller'" />
	</form>
</div>

<div class="table-list-div">
	<table class="table">
		<tr>
			<th>编号</th>
			<th>姓名</th>
			<th>联系电话</th>
			<th>身份</th>
			<th>类型</th>
			<th>头像</th>
			<th>身份证</th>
			<th>名片</th>
			<th>已加好友</th>
			<th>注册时间</th>
			<th>操作</th>
		</tr>
		<#if list??>
			<#list list as account> 
		<tr>
			<td>${(account.id)!}</td>
			<td id="account-name-${(account.id)!}"><a href="accountEdit.controller?id=${(account.id)!}">${(account.name)}</a></td>
			<td>${(account.contactInfo1)!} ${(account.contactInfo2)!}</td>
			<td>${(account.positionType)!}</td>
			<td>
				<#if (account.type!0) == 0 >
					内部
				</#if>
				<#if (account.type!0) == 1 >
					合作
				</#if>
				<#if (account.type!0) == 2 >
					内部+合作
				</#if>
			</td>
			<td>${((account.picLocked!0) == 1) ? string('<span style="color:blue">是</span>', '<span style="color:red">否</span>')}</td>
			<td>${((account.idLocked!0) == 1) ? string('<span style="color:blue">是</span>', '<span style="color:red">否</span>')}</td>
			<td>${((account.cardLocked!0) == 1) ? string('<span style="color:blue">是</span>', '<span style="color:red">否</span>')}</td>
			<td>${((account.weixinJoined!0) == 1) ? string('<span style="color:blue">是</span>', '<span style="color:red">否</span>')}</td>
			<td>${(account.addTime?string("yyyy-MM-dd hh:mm"))!}</td>
			<td>
				<a href="javascript:void(0)" onclick="resetPassword(${(account.id)!})">重置密码</a>
				<a href="javascript:void(0)" onclick="closeAccount(${(account.id)!})">注销</a>
			</td>
		</tr>
			</#list>
		</#if>	
	</table>
	<input type="hidden" value="${(pager.totalCount)!0}" id="totalCount"/>
	<input type="hidden" name="page" value="${(pager.page)!}" id="page"/>
	<input type="hidden" name="pageSize" value="${(pager.pageSize)!}" id="pageSize"/>
	<div id="pagination" class="pagination">
	</div>
</div>

<div id="dlg-reset-pwd" title="重置用户密码">
    <div class="dlg-content">
    	你确定要重置<span id="dlg-reset-account-name"></span>的密码吗？
    </div>
    <div class="dlg-buttons">
    	<input type="button" value="确定" onclick='doResetPassword()' />
    	<input type="button" value="取消" onclick='$("#dlg-reset-pwd").dialog("close")'/>
    	<input type="hidden" value="" id="dlg-reset-account-id" />
    </div>
</div>

<div id="dlg-close-account" title="注销用户">
    <div class="dlg-content">
    	你确定要注销<span id="dlg-close-account-name"></span>的账户吗？
    </div>
    <div class="dlg-buttons">
    	<input type="button" value="确定" onclick='doCloseAccount()' />
    	<input type="button" value="取消" onclick='$("#dlg-close-account").dialog("close")'/>
    	<input type="hidden" value="" id="dlg-close-account-id" />
    </div>
</div>

<script type="text/javascript">
	$("#dlg-reset-pwd").dialog(
		{
			autoOpen: false,
			width: 500,
			modal: true,
			resizable: false
		});
	$("#dlg-close-account").dialog(
		{
			autoOpen: false,
			width: 500,
			modal: true,
			resizable: false
		});
		
	var callbackCounter = 0;
			
	$(document).ready(function(){
		init();
	});
	
	function init(){
		$("#pagination").pagination(parseInt($("#totalCount").val()), {
		    num_edge_entries: 2,
		    num_display_entries: 4,
		    callback: pageselectCallback,
		    items_per_page:$("#pageSize").val(),
		    current_page:parseInt($("#page").val())
		});
	}
	
	function pageselectCallback(page_index, jq){
		if(callbackCounter > 0) {
			location.href = "/accountList.controller?page=" + page_index;
		}
		callbackCounter++;
	}
	
	function exportAccountHouses(type)
	{
		var showAll = $("#notShowAll").is(":checked") ? 0 : 1
		location.href = "/accountHouseExport.controller?houseType=" + type + "&showAll=" + showAll;
	}
</script>
</body>