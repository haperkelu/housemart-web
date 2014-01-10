<title>消息推送</title>
<body>
<div>
	<h1>消息推送</h1>
	
	<form action="/client/notification/notify.controller" method="post">
		<input type="radio" name='target' value="0" checked="checked" /> 所有
		<input type="radio" name='target' value="1" /> 仅客户
 		<input type="radio" name='target' value="2" /> 仅经纪人
 		
 		指定客户端ID:
 		<input type="text" name="clientUIds" value="" style="width:500px;" />
 		(多个ID用英文,分隔)
		<table>
			<tr>
				<td style="width:120px;">
					发送时间
				</td>
				<td>
					<input type="text" name="sendTime" id="sendTime" value="${defaultTime}" style="width:160px;" />
				</td>
			</tr>
			<tr>
				<td style="width:120px;">
					消息内容
				</td>
				<td>
					<input type="text" name="content" id="content" value="" style="width:600px;" />
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					<input type="submit" value="发送" />
				</td>
			</tr>
		</table>
	</form>
	<hr/>
	<div class="table-list-div">
		<table class="table">
			<tr>
				<th>编号</th>
				<th>发送时间</th>
				<th>内容</th>
				<th>发送对象</th>
				<th>创建人</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
			<#if notificationList??>
				<#list notificationList as notification> 
			<tr>
				<td>${(notification.id)!}</td>
				<td>${(notification.sendTime?string("yyyy-MM-dd hh:mm"))!}</td>
				<td>${(notification.content)!}</td>
				<td>
					<#if (notification.target!0) == 0 >
						所有
					</#if>
					<#if (notification.target!0) == 1 >
						仅大众版
					</#if>
					<#if (notification.target!0) == 2 >
						仅经纪人版
					</#if>
					<#if (notification.target!0) == 3 >
						自定义
					</#if>
				</td>
				<td>${(notification.accountName)!}</td>
				<td>${((notification.status!0) == 1) ? string('已处理', '未处理')}</td>
				<td>
					<#if (notification.status!0) == 0 >
					<a href="/client/notification/process.controller?id=${(notification.id)!}&send=1" >手工发送</a>
					</#if>
				</td>
			</tr>
				</#list>
			</#if>	
		</table>
		<input type="hidden" name="page" value="${(pager.page)!}" id="page"/>
		<input type="hidden" name="pageSize" value="${(pager.pageSize)!}" id="pageSize"/>
		<div id="pagination" class="pagination">
		</div>
	</div>
</div>
</body>
	