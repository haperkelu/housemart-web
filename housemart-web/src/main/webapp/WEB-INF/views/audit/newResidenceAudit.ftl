<html>
<head><title>小区审核</title></head>
<body>

<#include "/audit/auditNav.ftl">

<table>
<thead>
<th>板块</th><th>楼盘</th><th>操作</th><th>申请时间</th>
</thead>
<#if list?exists>
<tbody>
<#list list as item> 
<tr>
<td>${(item.plateName)!}</td>
<td><a href="/residence/${(item.residenceId)!}">${(item.residenceName)!}</a></td>
<td style="width:100px;">
	<a href="/audit/approveNewResidence.controller?id=${item.id}&residenceId=${item.residenceId}">同意</a>&nbsp
	<a href="/audit/rejectNewResidence.controller?id=${item.id}&residenceId=${item.residenceId}">拒绝</a></td>
<td>${(item.addTime)!?string("yyyy-MM-dd HH:mm:ss")}</td>
</tr>
</#list>
</tbody>
</#if>
</table>

</body>
</html>