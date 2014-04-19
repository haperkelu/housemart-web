<title>创建房源</title>
<body>
<link rel="stylesheet" href="/resources/css/account.css" />
<script src="/resources/javascript/account.js" type="text/javascript"></script>

<div class="action">
	房源:ID[${zrHouseId}]&nbsp;标题[${zrHouseTitle}]
	&nbsp;&nbsp;&nbsp;
	关联至经纪人:<span id="accountName"></span>
	&nbsp;&nbsp;&nbsp;
	关联至小区:<span id="residenceName"></span>
</div>

<div id="accountArea">
	
	<div class="action">
		<h3>第一步:选择经纪人</h3>
		<form action="/zr/accountList.controller" method="GET">
		搜索用户名/姓名:
		<input type="textbox" name="keyword" value="${keyword}" />
		<input type="hidden" name="zrHouseId" value="${zrHouseId}" />
		<input type="submit" value="搜索" /> 
		<input type="button" value="全部" onclick="location.href='/zr/accountList.controller?zrHouseId=${zrHouseId}'" />
		</form>
	</div>
	
	<div class="table-list-div">
		<table class="table">
			<tr>
				<th>编号</th>
				<th>姓名</th>
				<th>操作</th>
				<th>联系电话</th>
				<th>身份</th>
				<th>类型</th>
				<th>头像</th>
				<th>身份证</th>
				<th>名片</th>
				<th>已加好友</th>
				<th>注册时间</th>
				
			</tr>
			<#if list??>
				<#list list as account> 
			<tr>
				<td>${(account.id)!}</td>
				<td id="account-name-${(account.id)!}"><a href="/accountEdit.controller?id=${(account.id)!}">${(account.name)}</a></td>
				<td>
					<a href="javascript:;" onclick="setAccount(${(account.id)!}, '${(account.name)!}');$('#accountArea').hide();">关联此经纪人</a>
				</td>
				<td>${(account.contactInfo1)!} ${(account.contactInfo2)!}</td>
				<td>${(account.positionType)!}</td>
				<td>
					<#if (account.type!0) == 0 >
						内部
					</#if>
					<#if (account.type!0) == 1 >
						合作
					</#if>
					<#if (account.type!0) == 2 >
						内部+合作
					</#if>
				</td>
				<td>${((account.picLocked!0) == 1) ? string('<span style="color:blue">是</span>', '<span style="color:red">否</span>')}</td>
				<td>${((account.idLocked!0) == 1) ? string('<span style="color:blue">是</span>', '<span style="color:red">否</span>')}</td>
				<td>${((account.cardLocked!0) == 1) ? string('<span style="color:blue">是</span>', '<span style="color:red">否</span>')}</td>
				<td>${((account.weixinJoined!0) == 1) ? string('<span style="color:blue">是</span>', '<span style="color:red">否</span>')}</td>
				<td>${(account.addTime?string("yyyy-MM-dd hh:mm"))!}</td>
			</tr>
				</#list>
			</#if>	
		</table>
		
		<input type="hidden" value="${(pager.totalCount)!0}" id="totalCount"/>
		<input type="hidden" name="page" value="${(pager.page)!}" id="page"/>
		<input type="hidden" name="pageSize" value="${(pager.pageSize)!}" id="pageSize"/>
		<div id="pagination" class="pagination">
		</div>
	</div>
</div>


<div id="residenceArea">
	<h3>第二步:选择小区</h3>
	小区名或拼音：
	<input type="text" name="residenceName" value="" />
	<input type="button" value="搜索" onclick="refreshResidenceList(false)"/>
	
	<p id="residences">

	</p>
</div>

<div id="submitArea" style="display:none;">
	<h3>第三步:确认关联房源到经纪人和小区</h3>
	<form action="/zr/createHouse.controller" method="GET">
		<input type="hidden" name="zrHouseId" value="${zrHouseId}" />
		<input type="hidden" name="accountId" id="accountId" value=""/>
		<input type="hidden" name="residenceId" id="residenceId" value=""/>
		<input type="submit" value="关联经纪人和小区" />
		<input type="button" value="重新选择" onclick="javascript:window.location.href='/zr/accountList.controller?zrHouseId=${zrHouseId}'"/>  
	</form>
</div>	
<style>
	#residences span {float:left;margin:5px 20px 5px 0;cursor:pointer;
					border:1px dashed #aaa;padding:2px 5px;}
	#residences span a, #selectedResidences span a {text-decoration:none;color:#000;}
	#residences .selected a {color:red;}
	#selectedResidences span {float:left;margin:5px 20px 5px 0;cursor:pointer;
		border:1px solid #aaa;padding:2px 5px;}
	#selectedResidences span input {display:hidden;}
	#residences .zombie a, #selectedResidences .zombie a {color:#999;}
</style>

<script type="text/javascript">
	function refreshResidenceList(selectedOpt){

		$('#residences').html("数据加载中...");
		if (selectedOpt)
		{
			if($("#plate").find("option:selected").val() > 0 ||
					$("#region").find("option:selected").val() > 0)
			{
				$("input[name='residenceName']").val("");
			  	$.ajax({
					type: "post",
					url: "/ajax/getResidenceListByPlateId.controller",
					data: {plateId: $("#plate").find("option:selected").val(), regionId: $("#region").find("option:selected").val()},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						showResidenceList(data);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
		  	}
		}
		else
		{
			var key = $.trim($("input[name='residenceName']").val());
			if (key != "")
			{
				$('#region').val("");
				$('#plate').val("");
				 
				$.ajax({
					type: "post",
					url: "/ajax/getResidenceListByResidenceName.controller",
					data: {residenceName: key},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						showResidenceList(data);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
			}
		}
	}
			
	function showResidenceList(data)
	{
		var total = '';
		var index = '';
		var indexList = new Array();
		for(var i in data.bizData){
			var alpha = data.bizData[i].pinyinName.toUpperCase().charAt(0).toUpperCase();
			
			if (alpha.charCodeAt(0) < 65 || alpha.charCodeAt(0) > 90)
			{
				alpha = "";
			}
			
			var option = '<span class="region-residence index-' + alpha + (data.bizData[i].zombie == 1 ? ' zombie' : "")  + '" id="res-' + data.bizData[i].residenceId + '" onclick="addAccountResidence(' + data.bizData[i].residenceId + ',\'' + data.bizData[i].residenceName + '\')">' + 
			'<a href="javascript:void(0)">' +
			(data.bizData[i].pinyinName != null ? alpha + " " : "") + 
			data.bizData[i].residenceName + '</a></span>';
			total += option;
			
			if ($.inArray(alpha, indexList) < 0)
			{
				indexList.push(alpha);
			}
			indexList.sort();
		}
		index += '<div class="residence-index">';
		for (var i in indexList)
		{
			var alpha = indexList[i];
			if (alpha == "")
			{
				alpha = "其它";
			}
			
			index += '<a href="javascript:void(0)" onclick="showResidenceAlphaList(this)">' + alpha + '</a> ';
		}
		index += '</div>';
		$('#residences').html(index + total);
		$(".region-residence").hide();
		if (indexList.length > 0)
		{
			$(".index-" + indexList[0]).show();
		}
		
		markSelectedResidences();
	}
	
	function showResidenceAlphaList(obj)
	{
		$(".region-residence").hide();
		var alpha = $(obj).html();
		if (alpha == "其它")
		{
			alpha = "";
		}
		$(".index-" + alpha).show();;
	}
	
	function markSelectedResidences()
	{
		$(".region-residence").each(function(){
			var id = $(this).attr("id");
			id = id.split("-");
			id = id[1];
			if ($("#select-res-" + id).length > 0)
			{
				$(this).css("color", "red");
			}
			else
			{
				$(this).css("color", "black");
			}
		});
		
	}
	
	function addAccountResidence(id, name)
	{
		$("#residenceName").html(name);
		$("#residenceId").val(id);
		$("#residenceArea").hide();
		$("#submitArea").show();
	}

	function setAccount(accountId, accountName){
		$("#accountId").val(accountId);
		$("#accountName").html(accountName);
	}	
</script>
</body>