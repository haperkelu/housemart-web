<title>账号管理</title>
<body>
<link rel="stylesheet" href="resources/css/account.css" />
<script src="resources/javascript/account.js" type="text/javascript"></script>
<div class="action">
	<select id="account-apply-op">
		<option value="">操作</option>
		<option value="3">标记为保留</option>
		<option value="4">标记为无效</option>
	</select>
	
	<a id="tab-1" href="accountApplyList.controller?tabIndex=1">未处理</a>
	<a id="tab-2" href="accountApplyList.controller?tabIndex=2">已开通</a>
	<a id="tab-3" href="accountApplyList.controller?tabIndex=3">保留</a>
	<a id="tab-4" href="accountApplyList.controller?tabIndex=4">无效</a>
</div>

<div class="action">
	<form action="accountApplyList.controller" method="GET">
	姓名:
	<input type="textbox" name="keyword" value="${keyword}" />
	<input type="submit" value="搜索" />
	<input type="hidden" id="tabIndex" name="tabIndex" value="${tabIndex}" />
	<input type="button" value="全部" onclick="location.href='accountApplyList.controller?tabIndex=${tabIndex}'" />
	</form>
</div>

<div class="table-list-div">
	<table class="table">
		<tr>
			<th>&nbsp;</th>
			<th>姓名</th>
			<th>性别</th>
			<th>联系电话</th>
			<th>身份证</th>
			<th>公司名称/分行</th>
			<th>公司地址</th>
			<th>工作小区</th>
			<th>微信</th>
			<th>已加好友</th>
			<th>提交时间</th>
			<#if (tabIndex != 4)>
			<th>操作</th>
			</#if>
		</tr>
		<#if list??>
			<#list list as account> 
		<tr id="account-${(account.id)!}">
			<td><input type="checkbox" name="acc_id" value="${(account.id)!}" /></td>
			<td>${(account.name)}</td>
			<td>${((account.gender!-1) == 1) ? string('男', '女')}</td>
			<td>${(account.contactInfo1)!} ${(account.contactInfo2)!}</td>
			<td>${(account.identityID)!}</td>
			<td>${(account.company!)}</td>
			<td>${(account.companyAddress!)}</td>
			<td>${(account.note!)}</td>
			<td>${(account.weixin!)}</td>
			<td>${((account.weixinJoined!0) == 1) ? string('<span style="color:blue">是</span>', '<span style="color:red">否</span>')}</td>
			<td>${(account.addTime?string("yyyy-MM-dd hh:mm"))!}</td>
			<#if (tabIndex != 4)>
			<td>
				<a href="accountEdit.controller?id=${(account.id)!}">编辑开通</a>
			</td>
			</#if>
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

<div id="dlg-account-mark" title="标记用户">
    <div class="dlg-content">
    	你确定要将<span id="dlg-account-mark-name"></span>的标记为<span id="dlg-account-mark-status"></span>吗？
    </div>
    <div class="dlg-buttons">
    	<input type="button" value="确定" onclick='doMarkAccount()' />
    	<input type="button" value="取消" onclick='$("#dlg-account-mark").dialog("close")'/>
    	<input type="hidden" value="" id="dlg-account-mark-ids" />
    </div>
</div>

<script type="text/javascript">
	$("#dlg-account-mark").dialog(
		{
			autoOpen: false,
			width: 500,
			modal: true,
			resizable: false
		});
		
	var callbackCounter = 0;
			
	$(document).ready(function(){
		init();
		bind();
	});
	
	function init(){
		$("#pagination").pagination(parseInt($("#totalCount").val()), {
		    num_edge_entries: 2,
		    num_display_entries: 4,
		    callback: pageselectCallback,
		    items_per_page:$("#pageSize").val(),
		    current_page:parseInt($("#page").val())
		});
		
		$("#tab-" + $("#tabIndex").val()).css("fontWeight", "bold");
	}
	
	function bind()
	{
		$("#account-apply-op").bind("change", function(){
			var op = $(this).val();
			var accountIds = "";
			
			$("input[name='acc_id']:checked").each(function(){
				accountIds += (accountIds == "" ? "" : ",") + $(this).val();
			});
			
			if (op != "" && accountIds != "")
			{
				location.href = "accountApplyOp.controller?op=" + op + "&accountIds=" + accountIds + "&fromTab=" + $("#tabIndex").val();
			}
		});
	}
	
	function pageselectCallback(page_index, jq){
		if(callbackCounter > 0) {
			var tabIndex = $("#tabIndex").val();
			location.href = "/accountApplyList.controller?page=" + page_index + "&tabIndex=" + tabIndex;
		}
		callbackCounter++;
	}
	
</script>
</body>