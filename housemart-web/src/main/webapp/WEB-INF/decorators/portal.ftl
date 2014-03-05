<#ftl strip_whitespace=true>


    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <!-- dns预解析 -->
        <meta http-equiv="x-dns-prefetch-control" content="on" />
        <title>好识买HouseMart</title>
        <link rel="dns-prefetch" href="xxx" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- Bootstrap core CSS -->
        <link href="/webresources/css/lib/bootstrap.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="/webresources/css/lib/base.css" rel="stylesheet">
        <link href="/webresources/css/index.css" rel="stylesheet">
        <link href="/webresources/css/newsell.css" rel="stylesheet">
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="/webresources/js/lib/html5shiv.js"></script>
        <script src="/webresources/js/lib/respond.min.js"></script>
        <![endif]-->
        <!--[if lte IE 7]>
	    <link rel="stylesheet" type="text/css" href="/webresources/css/lib/fix-ie.min.css">
	    <![endif]-->
          <!--[if lte IE 6]>
	    <link rel="stylesheet" type="text/css" href="/webresources/css/lib/fix-ie6.min.css">
	    <![endif]-->
	     
    </head>
    <body class="no-padding-bottom">
	    <!--[if lt IE 8]>
		<script>
			if(location.href.indexOf("broker.housemart") >= 0)
			{
				window.onload = function(){
					document.body.innerHTML = '<div style="text-align:center;margin:50px;color:red;">' + 
						'由于您的浏览器版本太低，我们暂时无法为您提供服务，请升级至最新版本。建议使用<a href="http://www.google.com/intl/zh-CN/chrome/" target="_blank">现代浏览器Chrome</a>.' +
					'</div>';
						
				}
			}
		</script>
		<![endif]-->
        <!-- nav start -->
        <div class="navbar navbar-default navbar-static-top">
        	<div id="J_systemInfo" class="container hm-sys-info" <#if sysMsg?exists && (sysMsg == '' || sysMsg == '用户名或者密码错误')>style="display:none"</#if>>
		        <div class= "alert alert-success">
		            <button type="button" class="close" id="J_closeSysInfo">×</button>
		            <strong>${sysMsg!}</strong>
		        </div>
		    </div>       			    
            <div class="container">
                <div class="navbar-header">
                    <a href="/" class="navbar-brand logo">HouseMart</a>
                </div>
                <ul class="nav navbar-nav navbar-right">
               		<li><a href="/html/index.html">首页</a></li>
               		<li><a href="/page/brokerApply.controller">经纪人服务</a></li>
               		<li><a href="/html/about.html">关于</a></li>				    
                </ul>
            </div>
        </div>
        ${body}

	    <div style="display:none;">
		    <script type="text/javascript">
			var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
			document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F493a93e6a9ac53ff6bb17cdf23509ef4' type='text/javascript'%3E%3C/script%3E"));
			</script>
	    </div>
    
        <div id="footer" class="hm-footer">
			<div class="container">
				<p class="text-center text-muted">www.housemart.cn 2012-2013 &copy; All Right Reserved</p> 
				<p class="text-center text-muted">沪ICP备：13014224号-1</p> 
			</div>
			<div style="display:none;">
			    <script type="text/javascript">
				var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
				document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F493a93e6a9ac53ff6bb17cdf23509ef4' type='text/javascript'%3E%3C/script%3E"));
				</script>
		    </div> 
        </div>
					
    </body>
    </html>
    



    
