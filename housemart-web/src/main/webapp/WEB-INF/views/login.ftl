<html>
	<head>
		
	</head>	
	<body>	
	<link href="/webresources/css/login.css" rel="stylesheet">
	<div class="container hm-container">
	    <form class="form-signin" id="J_loginForm" action="/loginSubmit.controller" method="post" style='width:330px;'>
	        <h3 class="form-signin-heading">欢迎登录HouseMart</h3>
	        <div class="form-group">
	            <input id="J_username" type="text" name="userName" class="form-control wid-auto" placeholder="Username" autofocus data-original-title="账号为6-18位字符"/>
	        </div>
	        <div class="form-group">
	            <input id="J_password" type="password" name="password" class="form-control wid-auto" placeholder="Password" data-original-title="密码为6-18位字符"/>
	        </div>
	        
	        <#if sysMsg?exists && sysMsg != ''>
	        	<p class="alert alert-danger hm-alert" id="J_errorMsg">用户名或者密码错误</p>
	        </#if>
	        <button class="btn btn-lg btn-primary wid-auto" type="submit" id="J_submit">登&nbsp;&nbsp;录</button>
	    </form>
	
	</div> <!-- /container -->	
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
		seajs.use(['lib/bootstrap', 'jquery', 'account/login']);
	</script>
	<!--[if lt IE 8]>
		<script>

				window.onload = function(){
					document.body.innerHTML = '<div style="text-align:center;margin:50px;color:red;">' + 
						'由于您的浏览器版本太低，我们暂时无法为您提供服务，请升级至最新版本。建议使用<a href="http://www.google.com/intl/zh-CN/chrome/" target="_blank">现代浏览器Chrome</a>.' +
					'</div>';
						
				}
		
		</script>
		<![endif]-->
	</body>
</html>