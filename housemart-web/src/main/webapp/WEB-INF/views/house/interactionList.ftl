<title>交互管理</title>
<body>
<link rel="stylesheet" href="resources/css/account.css" />
<script src="resources/javascript/account.js" type="text/javascript"></script>

<div>
	将所选对象会话迁移给
	<input id="search-broker-name" type="text" value="" onkeyup="filterAccount(this.value, 'search-brokers')" onblur="filterAccount(this.value, 'search-brokers')" />
	备注
	<input id="interaction-transfer-notes" type="text" value="" />
	<input id="btn-interaction-transfer" type="button" value="确定" onclick="interactionTransfer()"/>
	<input type="hidden" value="" id="to-broker-id" />
	<input type="hidden" value="" id="current-search" />
	<ul id="search-brokers">
	</ul>
</div>

<div style="height:40px;">
	账号:
	<select id="filter_agent_id" onchange="filterInteractions()">
		<option value="">全部</option>
		<#if list??>
			<#list agents as interaction>
		<option value="${(interaction.agentID)!}" <#if interaction.agentID == filter["agent_id"]>selected="selected"</#if>>${(interaction.agentName)!}</option>
			</#list>
		</#if>	
	</select>
	<input type="checkbox" id="filter_valid" value="1" onclick="filterInteractions()" <#if !filter["show_all"]>checked="checked"</#if> />
	只显示有效
</div>

<div class="table-list-div">
	<table class="table">
		<tr>
			<th>&nbsp;</th>
			<th>房源ID</th>
			<th>房源</th>
			<th>交互人</th>
			<th>交互状态</th>
			<th>开始日期</th>
			<th>结束日期</th>
			<th>操作</th>
		</tr>
		<#if list??>
			<#list list as interaction> 
		<tr id="item-${(interaction.id)!}">
			<td><input name="interaction[]" type="checkbox" value="${(interaction.id)!}" /></td>
			<td>
				<a href="houseEdit.controller?houseId=${(interaction.houseID)!}" title="查看房源" target="_blank">
					${(interaction.houseID)!}
				</a>
			</td>
			<td>
				<a href="houseEdit.controller?houseId=${(interaction.houseID)!}" title="查看房源" target="_blank">
					${(interaction.houseInfo)!}
				</a>
			</td>
			<td>
				<a href="accountEdit.controller?id=${(interaction.agentID)!}" title="编辑账号" target="_blank">
					${(interaction.agentName)!}
				</a>
			</td>
			<td>
				<#if interaction.status == 1>
				普通
				<#elseif interaction.status == 2>
				独占
				<#elseif interaction.status == 3>
				托管
				</#if>
			</td>
			<td><#if interaction.startDate??>${interaction.startDate?string("yyyy-MM-dd HH:mm")}</#if></td>
			<td><#if interaction.endDate??>${interaction.endDate?string("yyyy-MM-dd HH:mm")}</#if></td>
			<td>
				<a href="javascript:void()" onclick="cancelInteraction(${(interaction.id)!})">[取消]</a>
			</td>
		</tr>
			</#list>
		</#if>	
	</table>
</div>

</body>