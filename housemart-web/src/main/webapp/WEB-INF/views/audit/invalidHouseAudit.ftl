<html>
<head><title>房源审核</title></head>
<body>

<#include "/audit/auditNav.ftl">

<table>
<thead>
<th>板块</th><th>楼盘</th><th>房号</th><th>标记人</th><th>操作</th><th>申请时间</th>
</thead>
<#if list?exists>
<tbody>
<#list list as item> 
<tr>
<td>${item.plateName}</td>
<td>${item.residenceName}</td>
<td>${item.roomNo}</td>
<td>${item.committerName}</td>
<td>
	<a href="/audit/approveInvalidHouse.controller?id=${item.id}&houseId=${item.houseId}">同意</a>&nbsp
	<a href="/audit/rejectInvalidHouse.controller?id=${item.id}&houseId=${item.houseId}">拒绝</a></td>
<td>${item.addTime?string("yyyy-MM-dd HH:mm:ss")}</td>
</tr>
</#list>
</tbody>
</#if>
</table>

<script>
	

</script>
</body>
</html>