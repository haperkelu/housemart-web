<html>
<head><title>房源审核</title></head>
<body>

<#include "/audit/auditNav.ftl">
<h3>
<a href="/houseAudit.controller?sourceType=1">内部盘源(${(internalCount)!})</a>
&nbsp
<a href="/houseAudit.controller?sourceType=3">外部盘源(${(externalCount)!})</a>
&nbsp
<a href="/rejectedHouseList.controller?sourceType=3&auditType=1">外部房源拒绝列表</a>
</h3>

<table>
<thead>
<th>编号</th><th>板块</th><th>楼盘</th><th>房号</th><th>房东信息</th><th>申请时间</th><th>拒绝理由</th><th>登盘人姓名</th><th>房源详情</th>
</thead>
<#if list?exists>
<tbody>
<#list list as item> 
<tr>
<td>${item.id}</td>
<td>${item.plateName}</td>
<td>${item.residenceName}</td>
<td>${item.roomNo}</td>
<td>${item.contactInfo}</td>
<td>${item.addTime?string("yyyy-MM-dd HH:mm:ss")}</td>
<td>${(item.comments)!}</td>
<td>${(item.creatorName)!}</td>
<td><a href="/external/houseView.controller?houseId=${item.houseId}" target="_blank">查看</a></td>
</tr>
</#list>
</tbody>
</#if>
</table>

</body>
</html>