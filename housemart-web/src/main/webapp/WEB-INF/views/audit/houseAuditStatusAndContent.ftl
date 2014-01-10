<html>
<head><title>盘源状态与内容变更</title></head>
<body>

<#include "/audit/auditNav.ftl">

<table>
<thead>
<th>板块</th><th>楼盘</th><th>房号</th><th>变更内容</th><th>操作</th><th>申请时间</th><th>房源详情</th>
</thead>
<#if list?exists>
<tbody>
<#list list as item> 
<tr>
<td><#if item.plateName?exists>${item.plateName}</#if></td>
<td><#if item.residenceName?exists>${item.residenceName}</#if></td>
<td><#if item.roomNo?exists>${item.roomNo}</#if></td>
<td><#if item.content?exists>${item.content}</#if></td>
<td>
	<a href="/audit/approveStatusAndContent.controller?id=${item.id}&houseId=${item.houseId}">同意</a>&nbsp
	<a href="/audit/RejectStatusAndContent.controller?id=${item.id}&houseId=${item.houseId}">拒绝</a></td>
<td><#if item.addTime?exists>${item.addTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
<td><a href="/external/houseView.controller?houseId=${item.houseId}" target="_blank">查看</a></td>
</tr>
</#list>
</tbody>
</#if>
</table>

<script>
	

</script>
</body>
</html>