<title>会话迁移历史</title>
<body>
<link rel="stylesheet" href="resources/css/account.css" />
<script src="resources/javascript/account.js" type="text/javascript"></script>

<div class="table-list-div">
	<table class="table">
		<tr>
			<th>房源</th>
			<th>原交互人</th>
			<th>迁移交互人</th>
			<th>最终交互人</th>
			<th>迁移日期</th>
			<th>备注</th>
		</tr>
		<#if list??>
			<#list list as transfer> 
		<tr>
			<td>${(transfer.houseInfo)!}</td>
			<td>${(transfer.fromBrokerName)!}</td>
			<td>${(transfer.toBrokerName)!}</td>
			<td>${(transfer.finalBrokerName)!}</td>
			<td><#if transfer.addTime??>${transfer.addTime?string("yyyy-MM-dd")}</#if></td>
			<td>${(transfer.notes)!}</td>
		</tr>
			</#list>
		</#if>	
	</table>
</div>

</body>