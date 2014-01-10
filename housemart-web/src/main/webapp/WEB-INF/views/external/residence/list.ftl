<html>
	<head>
	</head>	
	<title>我的小区</title>
	<body>
		<link href="/webresources/css/list.css" rel="stylesheet">
		<div class="container hm-container no-padding-top">
			<h1 class="hm-page-title">我的小区列表</h1>
			<div class="hm-wrap hm-bordered">
				<!--
				<div class="hm-action-btn hm-right-btn"><a href="/external/myResidenceCheckList.controller" class="btn btn-default">进入关联小区流程</a></div>-->
				<table class="table table-bordered table-striped hm-residence-list" id="J_listWrap">
					<thead><th>名称</th><th>操作</th></thead>
					<#if list??>
					<#list list as item>
					<tr>
						<td>${item.residenceName}<#if item.updateAuditPendingCount gt 0 ><span style="color:red;float:right;">修改信息审核中</span></#if></td>
						<td><a href="/residence/${item.residenceId}" class="btn btn-default btn-sm">查看</a></td>
					</tr>
					</#list>
					</#if>
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
			seajs.use(['jquery','header', 'residence/list']);
		    
		</script>	
	</body>
</html>
