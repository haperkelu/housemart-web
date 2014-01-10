<html>
<head><title>盘源状态与内容变更</title></head>
<body>

<#include "/audit/auditNav.ftl">

<table>
<thead>
<th>板块</th><th>楼盘</th><th>变更内容</th><th>操作</th><th>申请时间</th>
</thead>
<#if list?exists>
<tbody>
<#list list as item> 
<tr>
<td><#if item.plateName?exists>${item.plateName}</#if></td>
<td><#if item.residenceName?exists><a href="/residence/${(item.residenceId)!}" target="_blank">${(item.residenceName)!}</a></#if></td>
<td><#if item.content?exists>${item.content}</#if></td>
<td style="width:100px;">
	<a href="/audit/approveResidenceStatusAndContent.controller?id=${item.id}&residenceId=${item.residenceId}">同意</a>&nbsp
	<a href="/audit/rejectResidenceStatusAndContent.controller?id=${item.id}&residenceId=${item.residenceId}">拒绝</a></td>
<td><#if item.addTime?exists>${item.addTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
</tr>
</#list>
</tbody>
</#if>
</table>

<script>
	

</script>
</body>
</html>