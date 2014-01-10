<title>
	修改密码
</title>
<body>
	<link href="/webresources/css/login.css" rel="stylesheet">
	<script src="resources/javascript/account.js" type="text/javascript"></script>
	<div class="container hm-container">	
		<form class="form-modify-pwd form-horizontal" action="changeMyPwdSave.controller" method="POST" onsubmit="return checkChangeMyPwd()">
			<h3 class="form-signin-heading">修改密码</h3>
			<div class="form-group">
				<label for="old-pwd" class="col-sm-3 control-label">旧密码</label>
				<div class="col-sm-7">
	            	<input id="J_originPwd" type="password" name="oldPwd" class="form-control" placeholder="输入旧密码" autofocus data-original-title="请正确输入密码"/>
	            </div>
	            <div class="col-sm-2"></div>
	        </div>
	        <div class="form-group">
	        	<label for="new-pwd" class="col-sm-3 control-label">新密码</label>
	        	<div class="col-sm-7">
	            	<input id="J_newPwd" type="password" name="newPwd" class="form-control" placeholder="输入新密码" data-original-title="请正确输入新密码"/>
	            </div>
	            <div class="col-sm-2"></div>
	        </div>
	        <div class="form-group">
	        	<label for="repeat-pwd" class="col-sm-3 control-label">重复密码</label>
	        	<div class="col-sm-7">
	            	<input id="J_repeatPwd" type="password" name="repeatPwd" class="form-control" placeholder="重复新密码" data-original-title="两次密码不一致"/>
	            </div>
	            <div class="col-sm-2"></div>
	        </div>
	        
	        <#if sysMsg?exists && sysMsg != ''>
	        	<p class="alert alert-danger hm-alert" id="J_errorMsg">用户名或者密码错误</p>
	        </#if>
			<div class="form-group">
		        <div class="col-sm-offset-3 col-sm-7">
		        	<button class="btn btn-lg btn-primary wid-auto" type="submit" id="J_submit">保&nbsp;&nbsp;存</button>
		        </div>
	        </div>
		</form>
	</div>
	<script src="/webresources/js/lib/sea.js"></script>
	<script type="text/javascript">
    seajs.config({
        base: "/webresources/js/",
        alias: {
            "jquery": "lib/jquery.js"
        }
    });
    </script>
	<script>
		seajs.use(['jquery', 'header', 'account/modify-pwd']);
	</script>
</body>