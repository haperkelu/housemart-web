function initAccountEdit() {
	var type = $("input[name=positionType]:checked").val();
	
	if (type != "区域经理") {
		$("#manager-row").show();
	}
	else {
		$("#manager-row").hide();
	}
	
	$("input[name=positionType]").bind("click", function() {
		if ($(this).val() != "区域经理") {
			$("#manager-row").show();
		}
		else {
			$("#manager-row").hide();
		}
		
	});
	
	$('#city').change(function() {
		refreshRegionList();
	});
	$('#region').change(function() {
		refreshPlateList();
		refreshResidenceList(true);
	});
	$('#plate').change(function() {
		refreshResidenceList(true);
	});
}

function checkAccountFrom()
{
	var loginName = $.trim($("input[name='loginName']").val());
	var name = $.trim($("input[name='name']").val());
	var gender = $("input[name='gender']:checked").val();
	var identityID = $.trim($("input[name='identityID']").val());
	var contactInfo1 = $.trim($("input[name='contactInfo1']").val());
	var positionType = $.trim($("input[name='positionType']:checked").val());
	
	var managerID = $("#managerID").val();
	
	if (loginName == "")
	{
		$("input[name='loginName']").focus();
		return false;
	}
	if (name == "")
	{
		$("input[name='name']").focus();
		return false;
	}
	if (gender != 0 && gender != 1)
	{
		$("input[name='gender']").focus();
		return false;
	}
	if (identityID == "")
	{
		$("input[name='identityID']").focus();
		return false;
	}
	if (contactInfo1 == "")
	{
		$("input[name='contactInfo1']").focus();
		return false;
	}
	if (positionType == "")
	{
		$("input[name='positionType']").focus();
		return false;
	}
	if (managerID == "")
	{
		$("input[name='managerID']").focus();
		return false;
	}
	return true;
	
	
}

function refreshRegionList(selectedOpts){
	if($("#city").find("option:selected").val() > 0){
		$.ajax({
			type: "post",
			url: "ajax/getRegionList.controller",
			data: {},
			dataType: "json",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (data) {		
				var total = '<option value="">选择</option>';
				for(var i in data.bizData){
					var option;
					if(typeof(selectedOpts) !== 'undefined' && typeof(selectedOpts[0]) !== 'undefined'  && selectedOpts[0] == data.bizData[i].id )
						option = '<option value=' + data.bizData[i].id + ' selected="selected">' + data.bizData[i].name + '</option>';
					else
						option = '<option value=' + data.bizData[i].id + '>' + data.bizData[i].name + '</option>';
					total += option;
				}
				$('#region').html(total);
				//console.debug(selectedOpts);
				refreshPlateList(selectedOpts);
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			}
	  	});
  	}
}

function refreshPlateList(selectedOpts){
	if($("#region").find("option:selected").val() > 0){
	  	$.ajax({
			type: "post",
			url: "ajax/getPlateList.controller",
			data: {parentId: $("#region").find("option:selected").val()},
			dataType: "json",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (data) {
				var total = '<option value="">选择</option>';
				for(var i in data.bizData){
					var option; 
					if(typeof(selectedOpts) !== 'undefined' && typeof(selectedOpts[1]) !== 'undefined'  && selectedOpts[1] == data.bizData[i].id )
						option = '<option value=' + data.bizData[i].id + ' selected="selected">' + data.bizData[i].name + '</option>';
					else
						option = '<option value=' + data.bizData[i].id + '>' + data.bizData[i].name + '</option>';
					total += option;
				}
				$('#plate').html(total);			
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			}
	  	}); 
  	}
}

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
				data: {plateId: $("#plate").find("option:selected").val(), regionId: $("#region").find("option:selected").val(), countBroker: 1},
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
				data: {residenceName: key, countBroker: 1},
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
		
		var option = '<span class="region-residence index-' + alpha + 
			(data.bizData[i].zombie == 1 ? ' zombie' : "")  + 
			'" id="res-' + data.bizData[i].residenceId + '" onclick="addAccountResidence(' + 
			data.bizData[i].residenceId + ')">' +
			'<a href="javascript:void(0)" title="' + data.bizData[i].brokerList + '">' +
			(data.bizData[i].pinyinName != null ? alpha + " " : "") + 
			data.bizData[i].residenceName + '(' + data.bizData[i].brokerCount + ')' + '</a></span>';
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

function addAccountResidence(id)
{
	if ($('#select-res-' + id).length == 0)
	{
		var res = '<span id="select-res-' + id + '" onclick="deleteAccountResidence(' + id + ')">' +
			'<input type="checkbox" name="residenceID" value="' + id + '" checked="checked" />' +
			$("#res-" + id).html() + "</span>";
		$('#selectedResidences').append(res);
		markSelectedResidences();
	}
}

function addAccountRegion()
{
	var id = $('#region').val();
	if (id > 0 && $('#select-reg-' + id).length == 0)
	{
		var name = $("#region").find("option:selected").text();
		var res = '<span id="select-reg-' + id + '" onclick="deleteAccountResidence(' + id + ')">' +
			'<input type="checkbox" name="regionID" value="' + id + '" checked="checked" />' +
			name + "</span>";
		$('#selectedRegions').append(res);
	}
}

function addAccountPlate()
{
	var id = $('#plate').val();
	if (id > 0 && $('#select-plate-' + id).length == 0)
	{
		var name = $("#plate").find("option:selected").text();
		var res = '<span id="select-plate-' + id + '" onclick="deleteAccountResidence(' + id + ')">' +
			'<input type="checkbox" name="plateID" value="' + id + '" checked="checked" />' +
			name + "</span>";
		$('#selectedPlates').append(res);
	}
}

function markSelectedResidences()
{
	$(".region-residence").each(function(){
		var id = $(this).attr("id");
		id = id.split("-");
		id = id[1];
		$(this).removeClass("selected");
		
		if ($("#select-res-" + id).length > 0)
		{
			$(this).addClass("selected");
		}
	});
}

function selectAllResidences()
{
	$(".region-residence").click();
	markSelectedResidences();
}

function clearAllResidences()
{
	$("#selectedResidences").html("");
	markSelectedResidences();
}


function deleteAccountResidence(id)
{
	$('#select-res-' + id).remove();
	markSelectedResidences();
}

function resetPassword(id)
{
	$("#dlg-reset-account-name").html($("#account-name-" + id).text());
	$("#dlg-reset-pwd").dialog("open");
	$("#dlg-reset-account-id").val(id);
}

function doResetPassword()
{
	var account_id = $("#dlg-reset-account-id").val();
	$.ajax({
		type: "post",
		url: "/ajax/resetAccountPassword.controller",
		data: {id: account_id},
		dataType: "json",
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success: function (data) {
			$("#dlg-reset-pwd").dialog("close");
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		}
  	});
}

function closeAccount(id)
{
	$("#dlg-close-account-name").html($("#account-name-" + id).text());
	$("#dlg-close-account").dialog("open");
	$("#dlg-close-account-id").val(id);
}

function doCloseAccount()
{
	var account_id = $("#dlg-close-account-id").val();
	$.ajax({
		type: "post",
		url: "/ajax/closeAccount.controller",
		data: {id: account_id},
		dataType: "json",
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success: function (data) {
			$("#dlg-close-account").dialog("close");
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		}
  	});
}

function revokeAccount(id, status)
{
	$("#dlg-revoke-account").dialog("open");
	$("#dlg-revoke-id").val(id);
	$("#dlg-revoke-status").val(status);
	
	$("#dlg-revoke-msg").html(status == 1 ? "确定要注销用户" + $("#account-name-" + id).text() + "?" :
		"确定要拒绝注销用户" + $("#account-name-" + id).text() + "?");
}

function doRevokeAccount()
{
	var revoke_id = $("#dlg-revoke-id").val();
	var revoke_status = $("#dlg-revoke-status").val();
	$.ajax({
		type: "post",
		url: "/ajax/revokeAccount.controller",
		data: {id: revoke_id, status: revoke_status},
		dataType: "json",
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success: function (data) {
			$("#dlg-revoke-account").dialog("close");
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		}
  	});
}

function checkChangeMyPwd()
{
	if ($("#old-pwd").val() == "")
	{
		return false;
	}
	
	if ($("#new-pwd").val() == "" || $("#new-pwd").val() != $("#repeat-pwd").val())
	{
		return false;
	}
	
	return true;
}

function interactionTransfer()
{
	var ids = "";
	var brokerId = $("#to-broker-id").val();
	var transfer_notes = $("#interaction-transfer-notes").val();
	
	$("input[name='interaction[]']:checked").each(function(){
		ids += (ids == "") ? $(this).val() : ("," + $(this).val());
	});
	
	if (ids == "")
	{
		return;
	}
	
	if (brokerId == "")
	{
		return;
	}
	
	if (confirm("确定要迁移会话？"))
	{		
		$.ajax({
			type: "get",
			url: "/ajax/interactionTransfer.controller",
			data: {fromIds: ids, toBrokerId: brokerId, notes: transfer_notes},
			dataType: "json",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (data) {
				$("input[name='interaction[]']:checked").attr("checked", false);
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			}
	  	});
	}
}

function filterAccount(keyword, target_id)
{
	if (timer != null)
	{
		clearTimeout(timer);
	}

	keyword = $.trim(keyword);
	
	if (keyword == "")
	{
		fillAccountResult(array(), target_id);
		return;
	}
	
	if ($("#current-search").val() == keyword)
	{
		return;
	}
	
	timer = setTimeout(function(){
		$("#current-search").val(keyword);
		$.ajax({
			type: "get",
			url: "/ajax/searchAccount.controller",
			data: {searchKeyword: keyword},
			dataType: "json",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (data) {
				fillAccountResult(data, target_id);
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			}
	  	});
	}, 300);
}

function fillAccountResult(data, target_id)
{
	var html = "";
	for(var i = 0; i < data.length; i++)
	{
		html += "<span id='account-" + data[i].id + "' onclick='setBrokerID(" + data[i].id + ")'>" + data[i].name + "</span>";
	}
	
	if (html != "")
	{
		$("#" + target_id).html(html);
		$("#btn-interaction-transfer").show();
	}
	else
	{
		$("#" + target_id).html("");
		$("#btn-interaction-transfer").hide();
	}
}

function setBrokerID(id)
{
	$("#to-broker-id").val(id);
	$("#search-brokers .focus").removeClass("focus");
	$("#account-" + id).addClass("focus");
}

function filterInteractions()
{
	var filter_valid = $("#filter_valid").attr("checked");
	var filter_agent_id = $("#filter_agent_id").val();
	
	location.href = "interactionList.controller" + (filter_valid ? "" : "?filter_all=1") +
	(filter_agent_id > -1 ? ((filter_valid ? "?" : "&") + "filter_agent_id=" + filter_agent_id) : "");
}

function cancelInteraction(item_id)
{
	$.ajax({
		type: "get",
		url: "/ajax/interactionCancel.controller",
		data: {id: item_id},
		dataType: "json",
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success: function (data) {
			$("#item-" + item_id).remove();
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		}
  	});
	
}