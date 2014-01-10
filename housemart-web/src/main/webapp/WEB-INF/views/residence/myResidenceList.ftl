<#if type?exists && (type == 1 || type == -1)>
<title>小区列表</title>
<body>
<link href="/webresources/css/list.css" rel="stylesheet">
<div class="container hm-container no-padding-top">
	<h1 class="hm-page-title">房源信息</h1>
	<div class="hm-wrap hm-bordered">
		<!--
		<div class="hm-action-btn hm-right-btn"><a href="/external/myResidenceCheckList.controller" class="btn btn-default">进入关联小区流程</a></div>-->
		<table class="table table-bordered table-striped hm-residence-list" id="J_listWrap">
			<thead><tr><th>区域</th><th>板块</th><th>小区名称</th><th>是否设置地标</th><th>有效图片数量</th><th>状态</th></tr></thead>
			<#list list as item> 
				<tr>
					<td>${item.regionName}</td>
					<td>${item.plateName}</td>
					<td><a href="editResidence.controller?residenceId=${item.residenceId}" target="_blank">${item.residenceName}</a></td>
					<td> <#if item.positionSet?exists & item.positionSet == true>是<#else><span style="color:red">否<span></#if></td>
					<td>${item.picCount}</td>
					<td>
						 <#if item.status == 1><span style="color:blue;">有效</#if>
						 <#if item.status == 2><span style="color:red;">拒绝<span></#if>
					 	 <#if item.status == 0>审核中</#if>
					</td>
				</tr>
			</#list>
		</table>
	</div>	
</div>
<script src="/webresources/js/lib/sea.js"></script>
<script type="text/javascript">			
    seajs.config({
        base: "/webresources/js/",
        alias: {
            "jquery": "lib/jquery.js"
        }
    });
	seajs.use(['jquery','header']);
    
</script>		
</body>
<#else>

	<title>小区列表</title>
	<body>
	<br/>
	<div class="tabs">
		<span class="tab-focus">
			小区列表
		</span>
		<span class="tab">
			<a href="/editResidence.controller?regionId=359&plateId=380" target="_blank">新建小区</a>
		</span>
	</div>
	<div class="tab-content">
	<table>
	<thead><tr><th>区域</th><th>板块</th><th>小区名称</th><th>是否设置地标</th><th>有效图片数量</th><th>状态</th></tr></thead>
	<tbody>
	<#list list as item> 
		<tr>
			<td>${item.regionName}</td>
			<td>${item.plateName}</td>
			<td><a href="editResidence.controller?residenceId=${item.residenceId}" target="_blank">${item.residenceName}</a></td>
			<td> <#if item.positionSet?exists & item.positionSet == true>是<#else><span style="color:red">否<span></#if></td>
			<td>${item.picCount}</td>
			<td>
				 <#if item.status == 1><span style="color:blue;">有效</#if>
				 <#if item.status == 2><span style="color:red;">拒绝<span></#if>
			 	 <#if item.status == 0>审核中</#if>
			</td>
		</tr>
	</#list>
	</tbody>
	</table>
	</div>
	
	<style>
		.clear {clear:both;}
		.tabs {overflow:hidden;height:27px;}
		.tabs span a {text-decoration:none;color:#000;}
		.tabs span {float:left;margin:0 10px 0;display:block;line-height:25px;padding:0 5px;}
		.tab {background:#ccc;border:1px solid #888;}
		.tabs .tab-right {float:right;background:#333;border:1px solid #000;line-height:22px;}
		.tabs .tab-right a {color:#fff;}
		.tab-focus {border:1px solid #888;border-bottom:1px solid #fff;}
		.tab-content {border:1px solid #888;padding:20px;margin-top:-1px;}
		.tab-content table {width:100%;}
		.house-pic {width:80px; height:60px;display:none;}
	</style>
	</body>

</#if>
