<body>
	<h1 >小区图片列表(${(residenceName)!})</h1>
	<ul class="page-nav">
		<li><a href="/editResidence.controller?residenceId=${residenceId}">1. 基本信息</a></li>
		<li>
			<a href="/residence/${residenceId}">2. 设定坐标</a>
		</li>
		<li class="focus">
			3. 上传图片
		</li>
	</ul>
	
	<#if isAdmin?exists && isAdmin == true || isManager?exists && isManager == true>
		<a href="/repository/getResidencePicListById.controller?residenceId=${residenceId}">>>>进入图片库>>></a>
	</#if>
	<br>
	<hr>
		<div class="update">
			<form id="uploadForm" action="/housePicUpload.controller" name="uploadForm" method="POST" enctype="multipart/form-data">  
					
				<input id="residenceId" name="residenceId" type="hidden" value="${(residenceId)!}"/>
				<input id="picType" name="picType" type="hidden" value="1"/>
				
				<div class="flashuploader">
					<span class="title"><em>*</em>上传图片：</span><span class="uploadContent required">
						<input type="file" id="attach" name="imageFile"/>
					</span>
					<div id="fileQueue" style="margin:0 0 0 150px;"></div>
		          	<p class="error" style="display:none;">请选择图片</p>
	          	</div>
	          	
	          	<div class="basicuploader">
	          		<input name="imageFile" type="file"/>  
					<input type="submit" value="上传"/>
	          	</div>
	          	
	          	<div class="hint" style="margin:10px 0;">
	          	* 请至少上传两张房源照片，否则无法通过审核
	          	</div>
	          	
			</form>
		</div>
	
	<h2>小区图片</h2>
	
	<div>
		<ul class="hm-img-list">
		
		<!--
		<#if list?exists>
			<#list list as item> 
				<li>
					<image src="${(item.cloudUrl)!}" data-pic-id="${(item.id)!}" class="img-thumbnail" width="280" height="210"></image>
					<br>
					<a href="javascript:move('${(item.id)!}', 'up');">上移</a>
					<a href="javascript:move('${(item.id)!}', 'down');">下移</a>
					<a href="javascript:removePic('${(item.id)!}');" onclick="return confirm('是否将此图片删除?');" data-show-status="${(item.showStatus)!}" class="delPic">删除此图片</a>
					
					<#if item.showStatus?exists && item.showStatus == 0>
						<div data-show-status="${(item.showStatus)!}" class="toAudit" data-pic-id="${(item.id)!}">
						<span style="color:red;">图片待审核</span>
						<a href="javascript:showPic(${(item.id)!});">通过</a>
						<a href="/removePic.controller?id=${(item.id)!}" onclick="return confirm('是否拒绝此图片?');">拒绝</a>
						</div>
					</#if>
				</li>
			</#list>
		</#if>
		
		-->
		</ul>		
		
		<div id="shade"></div>
		
		<style>
			#shade{display:none;width:100%;height:100%;background:#ccc;filter:alpha(opacity=80);-moz-opacity:0.8;opacity:0.8;position:fixed;z-index:2000;top:0;left:0}
			.hm-img-list li .img-thumbnail {
			height: 90%;
			width: 100%;
			}
			
			.hm-img-list{padding: 0; margin: 10px 0 0 0;width: 1200px;}
			.hm-img-list li{margin: 0 2px 18px 0; display: inline-block; vertical-align: middle; height: 210px; width: 280px; position: relative;}
		</style>
					
		<script>

		    function removePic(picId){
				$("#shade").show();
				
				$.ajax({
					type: "get",
					url: "/ajax/removePic.controller",
					data: {id: picId},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						$("img[data-pic-id='"+ picId +"']").parent().remove();
						$("#shade").hide();
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
						$("#shade").hide();
						alert("删除图片失败");
					}
			  	}); 
			  	
			}

			function move(picId, type){
				$("#shade").show();
							
				var cur = $("img[data-pic-id='"+ picId +"']").parent();
				var pre = cur.prev();
				var next = cur.next();
				
				if(type == 'up'){
					$(pre).before(cur);
				}else{
					$(cur).before(next);
				}
				
				var sort = "";
				var picElements = cur.parent().find(".img-thumbnail");
				picElements.each(function(i){
					var pId = $(this).attr("data-pic-id");
					sort = sort + pId;
					if(i < picElements.size() - 1){
						sort = sort + ","
					}
				})
					
				$.ajax({
					type: "get",
					url: "/addOrUpdateSort.controller",
					data: {picId: picId, residenceId: ${residenceId}, sort: sort},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						$("#shade").hide();
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
						$("#shade").hide();
						alert("移动图片失败");
					}
			  	});
			}
			
			function showPic(picId){
				$("#shade").show();
				
				$.ajax({
					type: "get",
					url: "/ajax/showPic.controller",
					data: {id: picId},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						$(".toAudit[data-pic-id='"+ picId +"']").remove();
						$("#shade").hide();
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
						$("#shade").hide();
						alert("删除图片失败");
					}
			  	}); 
			  	
			}
			
			function isFlashEnabled() {
			    var hasFlash = false;
			    try {
			        var fo = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
			        if(fo) hasFlash = true;
			    }
			    catch(e) {
			        if(navigator.mimeTypes ["application/x-shockwave-flash"] != undefined) hasFlash = true;
			    }
			    return hasFlash;
			}
			
			function refreshHousePicList(){
			  	$.ajax({
					type: "post",
					url: "/ajax/getPicListByResidenceId.controller",
					data: {type: $('#picType').val(), residenceId: $('#residenceId').val()},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {	
						var html = '';
						for(var i in data.bizData){
							var item = data.bizData[i];
							var pic; 
							pic =  	'<li>' +
									'<image src="' + item.cloudUrl + '" data-pic-id="' + item.id + '" class="img-thumbnail" width="280" height="210"></image>' +
									'<br>' +
									'<a href="javascript:move(\'' + item.id + '\', \'up\');">上移</a>&nbsp;&nbsp;' + 
									'<a href="javascript:move(\'' + item.id + '\', \'down\');">下移</a>&nbsp;&nbsp;' +
									'<a href="javascript:removePic(\'' + item.id + '\');" onclick="return confirm(\'是否将此图片删除?\');" data-show-status="' + item.showStatus + '" class="delPic">删除此图片</a>';
							if(item.showStatus == 0){
								pic += '<div data-show-status="' + item.showStatus + '" class="toAudit" data-pic-id="' + item.id + '">';
								pic += '<span style="color:red;">图片待审核</span>';
								pic += '<a href="javascript:showPic(' + item.id + ');">通过</a>';
								pic += '<a href="/removePic.controller?id=' + item.id + '" onclick="return confirm(\'是否拒绝此图片?\');">拒绝</a>';
								pic += '</div>';
							}
							pic += '</li>';
							html += pic;
						}
						html += '<div style="clear:both"></div>';
						$('.hm-img-list').html(html);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	}); 
			}
			
			var ext_pic_execute = function() {
			    var residenceId = $("#residenceId").val();
			    var picType = $("#picType").val();
			    
				var baseUploadifyOpts = {
			    	swf : '/resources/javascript/uploadify/uploadify.swf',
			        uploader : "/multiPicUpload.controller",
			        postData : {'residenceId' : residenceId, 'picType' : picType},
			        cancelImage : '/resources/javascript/uploadify/cancel.png',
			        checkExisting : false,
			        fileObjName:'imageFile', //和以下input的name属性一致   
			        auto:true, //是否自动开始  
			        buttonText:'浏览...', //按钮上的文字   
			    	buttonImage:'',
			    	width:75,
					height:28, 
					fileTypeDesc:'支持的格式：',
			        //允许上传的文件后缀
			        fileTypeExts:'*.jpg;*.jpge;*.gif;*.png',
			        fileSizeLimit : 15*1024, //15M 
			        successTimeout : 300,//上传时的timeout
			        removeCompleted : true,//上传成功后的文件，是否在队列中自动删除
			        removeTimeout:1,
			        transparent:true,
					queueSizeLimit : 10,
				    simUploadLimit : 10	,
				    skipDefault:['onSelectError', 'onUploadError', 'onClearQueue', 'onDialogOpen', 'onDialogClose'],
			        onSelectError : function(file,errorCode,errorMsg) {//当文件选定发生错误时触发
			        	if(errorCode == -110){
			        		alert("文件大小超过系统限制15M")
						 }
			    	},
			    	onUploadError : function(file,errorCode,errorMsg,errorString,swfuploadifyQueue) {   
			    		alert("文件:" + file.name + "上传失败");
					},
					multi: true,
					onFallback:function(){
			            alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
			        }
			    };
			    
				var attachOpts = $.extend({}, baseUploadifyOpts,{
				   	 'queueID'  : 'fileQueue',
					 onUploadSuccess : function(file,data,response) { 
				    	 var res = jQuery.parseJSON(data);
				    	 if(res.code == 1){
				    		 refreshHousePicList();
				    	 }else{
				    	 	alert(res.msg);
				    	 }
				    }
				});
				$('#attach').uploadify(attachOpts);
				
				refreshHousePicList();
			};
		
			var ext_pic_init = function() {
				if(isFlashEnabled()){
					$(".basicuploader").remove()
				}else{
					$(".flashuploader").remove()
				}
			}
			
			var ext_pic_bind = function() {
			}
			
			$(document).ready(function(){
				ext_pic_init();
				ext_pic_bind();
				ext_pic_execute();
			});	
		</script>
	</div>
</body>