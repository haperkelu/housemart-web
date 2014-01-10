<title>小区详情</title>
<body>
<link href="/webresources/css/newsell.css" rel="stylesheet"/>
<#if entity?exists>
<div class="container hm-container">
	<h1 class="hm-page-title">
		设定坐标
	</h1>
	<div class="row hm-step-wrap hm-bordered">
	    <div class="col-sm-4 hm-step"><span></span><i>1、基本信息</i></div>
	    
	    <div class="col-sm-4 hm-step active"><span></span>
	        <i>
			2. 设定坐标
	        </i>
	    </div>
	    <div class="col-sm-4 hm-step after-active"><span></span>
	    	<i>
			3. 上传图片
	        </i>
	    </div>		
	</div>
	<#if entity.lockMap?? && entity.lockMap == 1>
		<p class="alert alert-danger" style="margin: 10px 3px;">地图信息被锁定不能编辑</p>
	</#if>	
	<div class="row hm-inputs-wrap hm-no-bg">
		<div class="col-sm-12 hm-half-panel">
			<div class="hm-bordered">
				<div id="J_map" class="map-container hm-bordered"></div>	
			</div>
		</div>
	</div>
	<div class="row hm-inputs-wrap hm-no-bg" style="text-align:center">
		<a class="btn btn-default btn-lg" href="/editResidence.controller?residenceId=${entity.residenceId}">上一步</a>
		<a class="btn btn-primary btn-lg" href="/external/residencePicConsole.controller?residenceId=${entity.residenceId}">下一步</a>
		<a class="btn" href="###" data-toggle="modal" data-target="#J_help">如何插入坐标</a>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="J_help" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">如何插入地图坐标</h4>
      </div>
      <div class="modal-body hm-help-step">
		<h5><code>第一步</code>：在地图上，找到小区位置，然后直接用鼠标点击，就会出现一个坐标</h5>
		<img src="/resources/images/flag_01.jpg" class="img-thumbnail">
		<h5><code>第二步</code>：点击小红图标，将该坐标设定为主经纬度</h5>
		<img src="/resources/images/flag_02.png" class="img-thumbnail">
		<br>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<#if isAdmin?exists && isAdmin == true || isManager?exists && isManager == true>
	<div style="float:left;padding-left:40px;"><a target = "_blank" href="/addResidenceBuilding?residenceId=${entity.residenceId}">生成栋座</a></div>
</#if>
</#if>
<script type="text/tpl" id="J_infoWinTpl">
<div style="width: 310px; padding-right: 10px"> 
	<p><small class="text-left text-muted">标记当前经纬度为主要经纬度:</small></p>
	<p class="text-center"><b>(<%=x%>, <%=y%>)</b><p>
	<%if (isAuto){%>
	<p class="text-center"><%=name + (isMain == true ?'<span style="color:red">(主经纬度)</span>' : '(非主经纬度)')%></p>
	<%}%>
	<p class="text-center"><a onclick="__saveMarker(this)" class="btn btn-primary btn-sm" data-action='save' data-is-auto="<%if(isAuto){%>1<%}else{%>0<%}%>" data-id="<%=id%>" data-lng="<%=x%>" data-lat="<%=y%>" href="###">保存小区坐标位置</a></p>
</div>
</script>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?v=3&language=zh-CN&key=AIzaSyBItBTVpc3EwAxES2U6x6NNrALw1tlPxRA&sensor=false"></script>
<script src="/webresources/js/lib/sea.js"></script>
<script type="text/javascript">
__appConfig = {
	<#if entity.lockMap?? && entity.lockMap == 1>isLocked: true,</#if>
	enableMap: true,
	points: ${points},
	residenceId: '${entity.residenceId}',
	center: {lat: '${(avgPoint.lat)!31.23}' ,lng: '${(avgPoint.lng)!121.47}'}
};
seajs.config({
    base: "/webresources/js/",
    alias: {
        "jquery": "lib/jquery.js"
    }
});
</script>
<script>
	seajs.use(['jquery', 'header', 'residence/map']);
</script>  


</body>