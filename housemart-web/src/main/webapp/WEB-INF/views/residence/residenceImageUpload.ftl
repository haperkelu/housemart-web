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
	<div>
	<form id="uploadForm" action="/residencePicUpload.controller" name="uploadForm" method="POST" enctype="multipart/form-data">  
		<input id="residenceId" name="residenceId" type="hidden" value="${residenceId}"/>
		<input name="imageFile" type="file"/>  
		<input type="submit" value="上传"/>
	</form>
		<button onclick="window.location='/myResidence.controller'">完成</button>
	</div>
	
	<h2>小区图片</h2>
	
	<div>
		<#if list?exists>
			<ul class="hm-img-list">
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
			</ul>
		</#if>
		
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
		</script>
	</div>
</body>