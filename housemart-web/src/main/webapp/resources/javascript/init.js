var timer = null;

$(document).ready(function(){
	renderLoginToolbar();
	showSysMsg("");
});

function renderLoginToolbar()
{
	$.ajax({
		type: "get",
		url: "/ajax/getCurrentAccount.controller",
		data: {},
		dataType: "json",
		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success: function (data) {
			if (data != null)
			{
				var html = "当前登录: " + data.name + ", " +
					"职位: " + data.positionType + 
					(data.id > 0 ? (
						" | " + '<a href="/myAccount.controller">[修改我的信息]</a>' +
						" | " + '<a href="/changeMyPwd.controller">[修改密码]</a>'
						) : "") +
					" | " + '<a href="/logout.controller">退出</a>';
				$("#login-toolbar").html(html);
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
		}
  	});
	
}

function showSysMsg(msg)
{
	if (msg != "")
	{
		$("#sys-msg span").html(msg);
	}
	
	if ($("#sys-msg span").html() != "")
	{
		$("#sys-msg").attr("tabindex", -1);
		$("#sys-msg").focus();
		$("#sys-msg").show();
	}
	
	setTimeout(function(){
		$("#sys-msg").blur();
		$("#sys-msg span").html("");
		$("#sys-msg").hide();
	}, 5000);
}