<html>
	<head>
		
	</head>	
	<title>
		发布房源 - 上传图片
	</title>
	<body>
		<link href="/webresources/css/newsell.css" rel="stylesheet"/>
		<form id="uploadForm" action="/housePicUpload.controller" name="uploadForm" method="POST" enctype="multipart/form-data"> 
			<input id="residenceId" name="residenceId" type="hidden" value="${(residenceId)!}"/>
			<input id="houseType" name="houseType" type="hidden" value="3"/>
			<input id="sourceType" name="sourceType" type="hidden" value="3"/>
			<input id="sort" name="sort" type="hidden" value="${(sort)!}"/>
			<input id="picType" name="picType" type="hidden" value="${(picType)!}"/>
			<div class="container hm-container">
				<h1 class="hm-page-title">
					上传图片
				</h1>
				<div class="row hm-step-wrap hm-bordered">
				    <div class="col-sm-4 hm-step"><span></span><i>1、基本信息</i></div>
				    <div class="col-sm-4 hm-step"><span></span>
				        <i>
						2. 设定坐标
				        </i>
				    </div>
				    <div class="col-sm-4 hm-step active"><span></span>
				    	<i>
						3. 上传图片
				        </i>
				    </div>		
				</div>
				<#if entity.lockPic?? && entity.lockPic == 1>
					<p class="alert alert-danger" style="margin: 10px 3px;">图片信息被锁定不能编辑</p>
				</#if>
				<div class="container hm-inputs-wrap hm-bordered hm-imgs-wrap" id="J_imgsWrap">
			        <ul class="hm-img-list" id="J_imgsWrap-1"></ul>
			    </div> 
				<#if entity.lockPic?? && entity.lockPic == 1>
				<#else>
			    <div class="hm-bordered hm-wrap row hm-upload-wrap">
			        <div class="col-md-2">
			            <button class="btn btn-warning btn-lg" id="J_upload">&nbsp;&nbsp;上传图片&nbsp;&nbsp;</button>
			        </div>
			        <p class="col-md-10">按住Ctrl或Shift可以多选图片,请至少上传两张房源照片，否则无法通过审核</br>尺寸不小于300*300px，支持jpg、png和bmp</p>
			    </div>
			    </#if>
			    <div class="hm-center">
			    	<a href="/residenceMap.controller?id=${(residenceId)!}" class="btn btn-default btn-lg">上一步</a>
			        <a href="/external/myResidenceList.controller" class="btn btn-primary btn-lg J_complete">完成，去小区列表</a>		        
			        <!--<a href="/myResidence.controller" class="btn btn-primary btn-lg J_complete">完成，去创建小区列表</a>-->
		    	</div>
			</div>
		</form>
		<script type="text/tpl" id="J_imgItemTpl">
		    <li id="{fileID}">
		        <!-- progress bar -->
		        <div class="progress progress-striped active">
		            <div class="progress-bar"  role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
		            </div>
		        </div>
		        <!-- image -->
		        <img class="img-thumbnail" width="280" height="210" style="display:none"/>
		        
		    </li>
		</script>
		<script type="text/tpl" id="J_picsTpl">
		
				<%for(i=0;i<list.length; i++){ %>
				<li>
			        <img class="img-thumbnail" src="<%= list[i].cloudUrl%>" data-img-id="<%=list[i].id%>" width="280" height="210"/>
			        <small class="img-status <%if (list[i].showStatus){%>img-passed<%}%>"><%if (list[i].showStatus){%>已认证<%}else{%>待审核<%}%></small>
				</li>
				<%} %>	
			
		</script>
		<script>
			__appConfig = {
				<#if entity.lockPic?? && entity.lockPic == 1>isLocked: true,</#if>
				sort: '${(sort)!}',
				picType: '${(picType)!}',
				postData: {'residenceId' : ${(residenceId)!}, 'picType' : ${(picType)!}, 'houseType': 3, 'sourceType': 3}	
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
		    seajs.use(['jquery','header', 'residence/upload']);
		</script>
	</body>
</html>