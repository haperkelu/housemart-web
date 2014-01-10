<style>
	    	#J_region.placeholder{background:url('/webresources/img/textarea-placeholder.jpg') no-repeat 5px top; height: 145px;}
	    </style>
	    <!--[if lte IE 7]>
	    <style>
			#J_form .col-sm-3{width: 200px; display: block; clear: both;}
			.form-group{ display: block;}
		</style>
		<![endif]-->
	<div class="container hm-container">
        	<h1 class="hm-page-title">
				加入我们
			</h1>
			<div class="row hm-step-wrap hm-bordered">
			    <div class="col-sm-3 hm-step"><span></span><i>1、在线提交个人资料</i></div>
			    
			    <div class="col-sm-3 hm-step"><span></span>
			        <i>
					2. 工作人员核实
			        </i>
			    </div>
			    <div class="col-sm-3 hm-step"><span></span>
			    	<i>
					3. 开通经纪人账号
			        </i>
			    </div>	
			    <div class="col-sm-3 hm-step"><span></span>
			    	<i>
					4. <a href="/myResidence.controller" class="btn btn-primary" style="color:white">去登盘&nbsp;&raquo;</a>
			        </i>
			    </div>	
			</div>
			<div class="container hm-inputs-wrap hm-bordered row">
				<div class="col-sm-9" style="border-right: 1px solid #ddd">
					<p class="text-muted hm-form-tip">“<span class="hm-required">*</span>”为必填内容</p>
			    	<form action="/brokerApplySave.controller" id="J_releaseForm" method="POST" class="form-horizontal">
						<div class="form-group">
			                <label class="col-sm-3 control-label" for="J_userName">姓名<span class="hm-required">*</span></label>
			                <div class="col-sm-5">
								<input type="text" data-original-title="请正确输入姓名" class="form-control" id="J_userName" name="name" value="">
			                </div>
			            </div>
			            <div class="form-group">
			                <label class="col-sm-3 control-label">性别<span class="hm-required">*</span></label>
			                <div class="col-sm-5">
								<label class="radio-inline"><input checked="checked" name="gender" type="radio" value="1"/>男</label>
								<label class="radio-inline"><input name="gender" type="radio" value="0"/>女</label>
			                </div>
			            </div>
			            <div class="form-group">
			                <label class="col-sm-3 control-label" for="J_cardID">身份证号<span class="hm-required">*</span></label>
			                <div class="col-sm-5">
								<input name="identityID" data-original-title="请正确输入身份证号" type="text" value="" id="J_cardID" class="form-control">
			                </div>
			            </div>
			            <div class="form-group">
			                <label class="col-sm-3 control-label" for="J_phone">手机号<span class="hm-required">*</span></label>
			                <div class="col-sm-5">
								<input name="contactInfo1" data-original-title="请正确输入手机号" type="text" value="" id="J_phone" class="form-control">
			                </div>
			            </div>
			            <div class="form-group">
			                <label class="col-sm-3 control-label" for="J_company">公司名称/分行<span class="hm-required">*</span></label>
			                <div class="col-sm-5">
								<input name="company" data-original-title="请正确输入公司名称/分行" placeholder="例如：XXX地产花木分行" id="J_company" type="text" value="" class="form-control">
			                </div>
			            </div>
			            <div class="form-group">
			                <label class="col-sm-3 control-label" for="J_address">公司地址<span class="hm-required">*</span></label>
			                <div class="col-sm-5">
								<input name="companyAddress" data-original-title="请正确输入公司地址" id="J_address" placeholder="例如：龙漕路xxx号" type="text" value="" class="form-control">
			                </div>
			            </div>
			            <div class="form-group">
			                <label class="col-sm-3 control-label" for="J_region">工作小区<span class="hm-required">*</span></label>
			                <div class="col-sm-5">
								<textarea name="residences" placeholder=' ' type="text" data-original-title="请正确输入工作小区" id="J_region" value="" class="form-control"></textarea>
			                	<span class="text-muted">最多5个小区,逗号分割</span>
			                </div>
			            </div>
			            <div class="form-group">
			                <label class="col-sm-3 control-label" for="J_weixin">微信号<span class="hm-required">*</span></label>
			                <div class="col-sm-5">
								<input name="weixin" type="text" data-original-title="请正确输入微信账号" value="" id="J_weixin" class="form-control">
			                </div>
			            </div>
			            <div class="form-group">
			                <label class="col-sm-3 control-label"></label>
			                <div class="col-sm-5">
								<input type="submit" value="提交申请" class="btn btn-primary btn-lg">
			                </div>
			            </div>
					    
					</form>
				</div>
				<div class="col-sm-3">
					<dl class="dl-vertical">
						<dt>使用与帮助</dt>
						<dd><a href="/html/demo/index.htm" target="_blank">如何登盘</a></dd>
						<dd><a href="/html/pic-desc/index.htm" target="_blank">房源图片规格说明</a></dd>
					</dl>	
				</div>	
			</div>
			<div class="container hm-download">
	    	<div class="row">
		    	<div class="col-sm-6">
		    		<h5 class="phone-logo iphone">IPhone 版</h5>
		    		<div class="hm-icon-wrap">
		         	<p><img class="img" src="/webresources/img/index_ios.png"></p>
		          	<p><a class="btn btn-default btn-lg" href="###" role="button">扫描二维码下载</a></p>
		        	</div>
		        	<img class="img-thumbnail" src="/webresources/img/iphone_code_agent.png">
		        </div>
		        <div class="col-sm-6">
		          <h5 class="phone-logo android">Andriod 版</h5>
		          <div class="hm-icon-wrap">
		          <p><img class="img" src="/webresources/img/index_android.png"></p>
		          <p><a class="btn btn-default btn-lg" href="###" role="button">扫描二维码下载</a></p>
		          </div>
		          <img class="img-thumbnail" src="/webresources/img/android_code_agent.png">
		        </div>
	    	</div>		
    	</div>
    	<div class="container">
	    	<div class="row app-store-list">
		    	<div class="col-sm-6">
		    		<h4>更多苹果市场，搜索“好识买经纪版”</h4>
			    	<div class="row">
				    	<div class="col-sm-6">
					    	<img src="/webresources/img/apple-store-icon.png" class="app-store"/>
							<span>苹果App Store</span>
				    	</div>
			    	</div>
			    	<div class="row">
				    	<div class="col-sm-6">
					    	<img src="/webresources/img/91.png" class="app-store"/>
							<span>91手机助手</span>
				    	</div>
			    	</div>
		    	</div>
		    	<div class="col-sm-6">
			    	<h4>更多安卓市场，搜索“好识买经纪版”</h4>	
			    	<div class="row">
				    	<div class="col-sm-6">
					    	<img src="/webresources/img/360-icon.png" class="app-store"/>
							<span>360手机助手</span>
				    	</div>
				    	<div class="col-sm-6">
					    	<img src="/webresources/img/baidu-icon.png" class="app-store"/>
							<span>百度手机助手</span>
				    	</div>
			    	</div>
			    	<div class="row">
				    	<div class="col-sm-6">
					    	<img src="/webresources/img/bean-icon.png" class="app-store"/>
							<span>豌豆荚</span>
				    	</div>
				    	<div class="col-sm-6">
					    	<img src="/webresources/img/xiaomi-icon.png" class="app-store"/>
							<span>小米商店</span>
				    	</div>
			    	</div>
			    	<div class="row">
				    	<div class="col-sm-6">
					    	<img src="/webresources/img/91-icon.png" class="app-store"/>
							<span>91手机助手</span>
				    	</div>
			    	</div>
			    	<div class="row">
				    	<div class="col-sm-6">
					    	<img src="/webresources/img/yingyongbao-icon.png" class="app-store"/>
							<span>应用宝</span>
				    	</div>
			    	</div>
		    	</div>
	    	</div>
	    		
    	</div>
	   
        </div>
<script src="/webresources/js/lib/sea.js"></script>
<script type="text/javascript">
__appConfig = {
	sysMsg: <#if sysMsg?exists && (sysMsg == '' || sysMsg == '用户名或者密码错误')>""<#else>"${sysMsg!}"</#if>
};
seajs.config({
    base: "/webresources/js/",
    alias: {
        "jquery": "lib/jquery.js"
    }
});
</script>
<script>
	seajs.use(['jquery', 'header', 'account/register']);
</script>