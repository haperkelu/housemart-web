<#ftl strip_whitespace=true>

<#if type?exists && (type == 1 || type == -1)>
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
        
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="/webresources/js/lib/html5shiv.js"></script>
        <script src="/webresources/js/lib/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
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
        <div class="navbar navbar-default navbar-fixed-top">        	
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
                
                <#if type?exists && type != -1>
                <ul class="nav navbar-nav" id="J_navBar">
                    <li data-category='house'><a href="/external/myHouseList.controller">我的盘源</a></li>
                    <li data-category='residence'><a href="/external/myResidenceList.controller">我的小区</a></li>
                    <!--
                    <li><a href="/myResidence.controller">新建小区</a></li>
                    -->
                </ul>
                <ul class="nav navbar-nav navbar-right" id="J_userInfo">
                   
                </ul>
                </#if>
            </div>
        </div>
        <!-- nav end -->
    ${body}
    
    <div style="display:none;">
	    <script type="text/javascript">
		var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
		document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F493a93e6a9ac53ff6bb17cdf23509ef4' type='text/javascript'%3E%3C/script%3E"));
		</script>
    </div>
    
    </body>
    </html>
<#else>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <title>${title}</title>
	    <link rel="stylesheet" href="/resources/css/jquery.treeview.css" />
	    <link rel="stylesheet" href="/resources/css/jquery.pagination.css" />
		<link rel="stylesheet" href="/resources/javascript/jquery.ui.1.8.13/themes/base/jquery.ui.all.css">
		<link rel="stylesheet" href="/resources/javascript/jquery.ui.1.8.13/themes/demos.css">
	    <link rel="stylesheet" href="/resources/css/common.css" />
	    <link rel="stylesheet" href="/resources/javascript/uploadify/uploadify.css"/>
		
			
		<script type="text/javascript" src="/resources/javascript/jquery1.4.min.js"></script>
		<script type="text/javascript" src="/resources/javascript/jquery.treeview.js"></script>
		<script type="text/javascript" src="/resources/javascript/jquery.pagination.js"></script>
		<script type="text/javascript" src="/resources/javascript/jquery.ui.1.8.13/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="/resources/javascript/jquery.ui.1.8.13/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="/resources/javascript/jquery.ui.1.8.13/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="/resources/javascript/jquery.ui.1.8.13/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
		<script type="text/javascript" src="/resources/javascript/jquery.ui.1.8.13/ui/jquery.ui.dialog.js"></script>
		<script type="text/javascript" src="/resources/javascript/init.js"></script>
		<!-- uploader -->
		<script type="text/javascript" src="/resources/javascript/uploadify/swfobject.js"></script>
		<script type="text/javascript" src="/resources/javascript/uploadify/jquery.uploadify.js"></script>
		<script type="text/javascript" src="/resources/javascript/jquery.metadata.js"></script>
		<script type="text/javascript" src="/resources/javascript/jquery.validate.js"></script>
		<script type="text/javascript" src="/resources/javascript/jquery.validate.functions.js"></script>
		<script type="text/javascript" src="/resources/javascript/jquery.cookie.js"></script>
		
		<script>
			var isEmpty = function(str){
				if(str == null || str == ''){
					return true;
				}
				return false;
			}
		</script>
		<style>
			table {border-collapse:collapse;}
			table th {border:1px solid;padding:5px 10px; text-align:left;}
			table td {border:1px solid;padding:5px 10px; }
			.inputBox {width: 50px;}
			.hint {font-size:11px;color:red;}
			
			.page-nav {height:25px;margin:20px 0;padding:0;}
			.page-nav li {float:left;line-height:25px;margin:0;width:160px;padding:0 10px;border:1px solid #666;list-style:none;}
			.page-nav .focus {background:#888;color:#fff;}
			
			.form input.text.error, .form input.error {
				border:1px dashed #c00;
			}
			.form label.error {
				padding:0 0 5px 10px;
				color:#C00;
				vertical-align:top;
			}
		</style><script type="text/javascript">
	
	  var _gaq = _gaq || [];
	  _gaq.push(['_setAccount', 'UA-45059885-1']);
	  _gaq.push(['_setDomainName', 'housemart.cn']);
	  _gaq.push(['_setAllowLinker', true]);
	  _gaq.push(['_trackPageview']);
	
	  (function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	  })();
	</script></head>
	<body>
	<div style="height:40px;">
		<div class="logo">
			HouseMart后台
		</div>
		<div id="login-toolbar">
		</div>
	</div>
	<div id="nav">
	
		<#if type?exists && type != 1>
			<ul id="menu">
				<li id="cat-1" class="cat">
					<span>小区管理</span>
				</li>
				<li id="cat-2" class="cat">
					<span>房源库</span>
				</li>
				<li id="cat-3" class="cat">
					<span>我的盘源</span>
				</li>
				<#if isAdmin?exists && isAdmin == true>
				<li id="cat-4" class="cat">
					<span>交互盘源</span>
				</li>
				</#if>					
				<#if isAdmin?exists && isAdmin == true>
				<li id="cat-6" class="cat">
					<span>公司管理</span>
				</li>
				<li id="cat-7" class="cat">
					<span>报表数据</span>
				</li>
				<li id="cat-8" class="cat">
					<span>客户端管理</span>
				</li>		
				</#if>
			</ul>
			<ul id="cat-1-sub" class="sub-cat">
				<li><span><a href="/residenceList?regionId=359&plateId=380">小区列表</a></span></li>		
				<li><span><a href="/residencePinYin.controller">小区拼音</a></span></li>
			</ul>
			<ul id="cat-2-sub" class="sub-cat">
				<li><span><a href="/houseEdit.controller">登盘</a></span></li>
				<li><span><a href="/houseList.controller?regionCityId=1&regionParentId=359&regionRegionId=380&status=1">房源列表</a></span></li>
				<li><span><a href="/soldHouseList.controller">已售房源</a></span></li>
				<#if isAdmin?exists && isAdmin == true || isManager?exists && isManager == true>
					<li><span><a href="/houseAudit.controller?sourceType=1">房源审核</a></span></li>
					<li><span><a href="/zr/findHouse.controller">加州房源</a></span></li>
					<li><span><a href="/zr/crawlerPage.controller">加州房源抓取</a></span></li>
				</#if>
			</ul>
			<ul id="cat-3-sub" class="sub-cat">
				<li><span><a href="/myHouseList.controller">我的盘源</a></span></li>
				<li><span><a href="/myExternalHouseList.controller">我的外网盘源</a></span></li>
				<li><span><a href="/getMyUpdateHistory.controller">我的更新</a></span></li>
				<li><span><a href="/myFavoriteHouseList.controller">我的收藏</a></span></li>
			</ul>
			<ul id="cat-4-sub" class="sub-cat">
				<li><span><a href="/interactionList.controller">交互管理</a></span></li>
				<li><span><a href="/interactionTransferList.controller">会话迁移历史</a></span></li>
			</ul>
			
			<ul id="cat-6-sub" class="sub-cat">
				<li><span><a href="/accountList.controller">账号管理</a></span></li>
				<li><span><a href="/accountApplyList.controller">账号审核</a></span></li>
				<li><span><a href="/accountResidenceList.controller">楼盘管理</a></span></li>
				<li><span><a href="/accountRevokeList.controller">行政审核</a></span></li>
				<li><span><a href="/citySet.controller?cityId=1">设置城市经纬度</a></span></li>
				<li><span><a href="/regionSet.controller?cityId=1&regionId=0">设置行政区经纬度</a></span></li>
				<li><span><a href="/plateSet.controller?cityId=1&regionId=359&plateId=0">设置板块经纬度</a></span></li>		
				<li><span><a href="/importData.controller">导入脉信数据</a></span></li>	
			</ul>
			<ul id="cat-7-sub" class="sub-cat">
				<li><span><a href="/appUserPublishedHouse.controller?page=0&pageSize=20">客户端发布房源列表</a></span></li>
				<li><span><a href="/appUserFeedback.controller?page=0&pageSize=20">客户端反馈</a></span></li>
				<li><span><a href="/monitor/list.controller?page=0&pageSize=20">手机客户端请求Profiling Dashaboard</a></span></li>			
			</ul>
			<ul id="cat-8-sub" class="sub-cat">
				<li><span><a href="/client/version/view.controller">客户端版本信息</a></span></li>
				<li><span><a href="/client/appFile/list.controller">上传客户端</a></span></li>
				<li><span><a href="/client/notification/notify.controller">消息推送</a></span></li>
			</ul>
		</#if>
		
		<#if type?exists && type == 1>
			<ul id="menu">
				<li id="cat-5" class="cat">
					<span>合作经纪人</span>
				</li>
			</ul>
			<ul id="cat-5-sub" class="sub-cat">
				<li><span><a href="/external/myHouseList.controller">我的盘源</a></span></li>
				<li><span><a href="/external/myResidenceList.controller">我的小区</a></span></li>
				<li><span><a href="/myResidence.controller">新建小区</a></span></li>
				<li>
					<span><a href="/logout.controller">退出</a></span>
				</li>
			</ul>
		</#if>
	</div>
	<div style="position:relative;">
		<div id="sys-msg">
			<span>${sysMsg!}</span>
		</div>
		${body}
	</div>
	
	<script>
		$(".cat").bind("mouseover", function()
			{
				$(".cat-focus").removeClass("cat-focus");
				$(".sub-cat-focus").removeClass("sub-cat-focus");
				$(this).addClass("cat-focus");
				$("#" + $(this).attr("id") + "-sub").addClass("sub-cat-focus");
			}
		);
	</script>
	</body>
	</html>    
</#if>
    
