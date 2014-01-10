<html>
	<head>
		
	</head>	
	<title>
		发布房源 - 上传图片
	</title>
	<body>
		<link href="/webresources/css/newsell.css" rel="stylesheet"/>
		<form id="uploadForm" action="/housePicUpload.controller" name="uploadForm" method="POST" enctype="multipart/form-data">
			<input id="houseId" name="houseId" type="hidden" value="${(house.id)!}"/>
			<input id="residenceId" name="residenceId" type="hidden" value="${(house.residenceId)!}"/>
			<input id="houseType" name="houseType" type="hidden" value="3"/>
			<input id="sourceType" name="sourceType" type="hidden" value="3"/>
			<input id="sort" name="sort" type="hidden" value="${(sort)!}"/>
			<input id="picType" name="picType" type="hidden" value="${(picType)!}"/>
			<div class="container hm-container">
				<h1 class="hm-page-title">
					<#if (house.saleSaleStatus!0) == 1>
					发布出售房
					</#if>
					<#if (house.rentRentStatus!0) == 1>
					发布出租房
					</#if>
				</h1>
				<div class="row hm-step-wrap hm-bordered">
			        <div class="col-md-4 hm-step"><span></span><i>1、基本信息</i></div>
			        <#if ((house.status!0) != 1)>
			        <div class="col-md-4 hm-step active"><span></span>
				        <i>
				        2. 上传图片
				        </i>
			        </div>
			        <div class="col-md-4 hm-step after-active"><span></span>
			        	<i>
						3. 完成
				        </i>
			        </div>
			        </#if>
			    </div>
			    
			    <#if residence.lockPic?? && residence.lockPic == 1>	
					<p class="alert alert-danger" style="margin: 10px 3px;">小区图片信息锁定不能编辑</p>
				</#if>
	
			    <ul class="nav nav-tabs hm-img-tab" id="J_imgCategory">
			        <li data-pic-type='3' <#if (picType!0) == 3>class="active"</#if>><a href="#J_imgsWrap-1" data-pic-type='3'  data-category='J_imgsWrap-1' data-toggle="tab">房源图片(<i>0</i>)</a></li>
			        <li data-pic-type='2' <#if (picType!0) == 2>class="active"</#if>><a href="#J_imgsWrap-2" data-pic-type='2'  data-toggle="tab" data-category='J_imgsWrap-2'>房型图片(<i>0</i>)</a></li>
			        <li data-pic-type='0' <#if (picType!0) == 0>class="active"</#if>><a href="#J_imgsWrap-3" data-pic-type='0'  data-toggle="tab" data-category='J_imgsWrap-3'>小区图片(<i>0</i>)</a></li>
			    </ul>
				<div class="tab-content container hm-inputs-wrap hm-bordered hm-img-tab-view" id="J_imgsWrap">
			        <ul class="hm-img-list tab-pane <#if (picType!0) == 3>fade in active</#if>" id="J_imgsWrap-1"></ul>
			        <ul class="hm-img-list tab-pane <#if (picType!0) == 2>fade in active</#if>" id="J_imgsWrap-2"></ul>
			        <ul class="hm-img-list tab-pane <#if (picType!0) == 0>fade in active</#if>" id="J_imgsWrap-3"></ul>
			    </div> 
			     <#if residence.lockPic?? && residence.lockPic == 1>	
					<p class="alert alert-danger" id="J_lockedMsg" style="margin: 10px 3px;display:none;">小区图片信息被锁定，不能编辑</p>
				</#if>
			    <div class="hm-bordered hm-wrap row hm-upload-wrap" id="J_uploadWrap">
			        <div class="col-md-2">
			            <a class="btn btn-warning btn-lg" id="J_upload">&nbsp;&nbsp;上传图片&nbsp;&nbsp;</a>
			        </div>
			        <p class="col-md-10">按住Ctrl或Shift可以多选图片,单张图片不超过10MB</br>尺寸不小于300*300px，支持jpg、png和bmp</p>
			
			    </div>
			    <div class="hm-center">
			  		<#if ((create!'false') == 'true')>
			  			<a href="/houseEdit.controller?houseId=${(house.id)!}&create=true" class="btn btn-default btn-lg">上一步</a>
						<a href="/external/myHouseList.controller?tabIndex=2&sysMsg=房源信息已保存" id="J_complete" class="btn btn-primary btn-lg">保存为草稿</a>
				        <a href="/external/myHouseList.controller?tabIndex=2&sysMsg=房源信息已保存并提交审核" id="J_complete_request" class="btn btn-primary btn-lg">保存并提交审核</a>
			       		<a href="###" class="btn" data-toggle="modal" data-target="#J_rules">图片规格说明</a>
			        <#else>
			        	<a href="/houseEdit.controller?houseId=${(house.id)!}" class="btn btn-default btn-lg">上一步</a>
			        	<a href="/external/myHouseList.controller?tabIndex=2&sysMsg=房源信息已保存" id="J_complete" class="btn btn-primary btn-lg">完成</a>
			        	<a href="###" class="btn" data-toggle="modal" data-target="#J_rules">图片规格说明</a>
			        </#if>
		    	</div> 
			</form>	      			
		</div>
		<!-- Modal -->
		<div class="modal fade" id="J_rules" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		        <h4 class="modal-title" id="myModalLabel">图片规格说明</h4>
		      </div>
		      <div class="modal-body hm-help-step">
				<h5><code>1</code>、照片不能有水印。</h5>
				<h5><code>2</code>、照片如果有日期，不能超过三个月。</h5>
				<h5><code>3</code>、照片清晰。在真实范围内尽量美观。</h5>
				<h5><code>4</code>、小区照片3张。</h5>
				<h5><code>5</code>、公寓3张，一张客厅、一张主卧、一张厨房或卫生间。</h5>
				<h5><code>6</code>、房别墅6张，一张大门（有无花园均可）、一张客厅、一张主卧、一张客卧、一张厨房、一张卫生间。</h5>
				<h5><code>7</code>、房型图1张。</h5>
				<br>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
		      </div>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		<script type="text/tpl" id="J_imgItemTpl">
		    <li id="{fileID}">
		        <!-- progress bar -->
		        <div class="progress progress-striped active">
		            <div class="progress-bar"  role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
		            </div>
		        </div>
		        <!-- image -->
		        <img class="img-thumbnail" width="280" height="210" style="display:none"/>
		        <div class="btn-group hm-img-controls" style="display:none;">
		            <a class="btn btn-default btn-sm hm-up" href="###" data-action='up'>&nbsp;</a>
		            <a class="btn btn-default btn-sm hm-down" href="###" data-action='down'>&nbsp;</a>
		            <a class="btn btn-default btn-sm hm-remove" href="###" data-action='remove'>&nbsp;</a>
		        </div>
		    </li>
		</script>
		<script type="text/tpl" id="J_picsTpl">
		
				<%for(i=0;i<list.length; i++){ %>
				<li>
					<!-- image -->
			        <img class="img-thumbnail" src="<%= list[i].cloudUrl%>" data-img-id="<%=list[i].id%>" width="280" height="210"/>
			        <%if (list[i].type!=0&&list[i].type!=1){%>
			        <div class="btn-group hm-img-controls">
			            <a class="btn btn-default btn-sm hm-up" href="###" data-action='up'>&nbsp;</a>
			            <a class="btn btn-default btn-sm hm-down" href="###" data-action='down'>&nbsp;</a>
			            <a class="btn btn-default btn-sm hm-remove" href="###" data-action='remove'>&nbsp;</a>
			        </div>
			        <%}%>
			        <%if (list[i].type==0||list[i].type==1){%><small class="img-status <%if (list[i].showStatus){%>img-passed<%}%>"><%if (list[i].showStatus){%>已认证<%}else{%>待审核<%}%></small><%}%>
				</li>
				<%} %>	
			
		</script>
		<script>
			__appConfig = {
				<#if residence.lockPic?? && residence.lockPic == 1>isLocked: true,</#if>
				picType: '${(picType)!}',
				postData: {'houseId' : ${(house.id)!}, 'residenceId' : ${(house.residenceId)!}, 'picType' : ${(picType)!}}	
			};
		</script>
		<script src="/webresources/js/lib/sea.js"></script>
		<script type="text/javascript">
		    seajs.config({
		        base: "/webresources/js/",
		        alias: {
		            "jquery": "lib/jquery.js"
		        }
		    });
		    seajs.use(['jquery','header', 'upload']);
		</script>
	</body>
</html>