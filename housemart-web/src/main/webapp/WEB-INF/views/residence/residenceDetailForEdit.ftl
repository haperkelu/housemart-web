<#if type?exists && (type == 1 || type == -1)>

<title>编辑小区</title>
<body>
<link href="/webresources/css/widget/calendar.css" rel="stylesheet"/>
<link href="/webresources/css/newsell.css" rel="stylesheet"/>
<div class="container hm-container">
	<h1 class="hm-page-title">
		编辑小区
	</h1>
	<div class="row hm-step-wrap hm-bordered">
	    <div class="col-sm-4 hm-step active"><span></span><i>1、基本信息</i></div>
	    
	    <div class="col-sm-4 hm-step after-active"><span></span>
	        <i>
			2. 设定坐标
	        </i>
	    </div>
	    <div class="col-sm-4 hm-step"><span></span>
	    	<i>
			3. 上传图片
	        </i>
	    </div>		
	</div>
	<#if entity.lockBasicInfo?? && entity.lockBasicInfo == 1>	
		<p class="alert alert-danger" style="margin: 10px 3px;">基本信息被锁定不能编辑</p>
	</#if>
	<div class="container hm-inputs-wrap hm-bordered">
		<p class="text-muted hm-form-tip">“<span class="hm-required">*</span>”为必填内容</p>
		<form class="form-horizontal" role="form" id="J_releaseForm" action="/addOrUpdateResidence.controller" method="post">
				<input type="hidden" name="regionId" value="${(regionId)!}"/>
				<input type="hidden" name="plateId" value="${(plateId)!}"/>
				<input type="hidden" name="id" value="${(entity.id)!}"/>
							
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_residence">小区名称<span class="hm-required">*</span></label>
				<div class="col-sm-4">
					<input type="text"  name="residenceName" id="J_residence" data-original-title="请输入小区名称" class="form-control" value="${(entity.residenceName)!}" readonly="true"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_address">小区地址<span class="hm-required">*</span></label>
				<div class="col-sm-4">
					<input type="text" id="J_address" name="address" class="form-control" data-original-title="请输入小区地址" value="${(entity.address)!}"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_headCount">总户数<span class="hm-required">*</span></label>
				<div class="col-sm-3 input-group">
					<input type="text" name="headCount" id="J_headCount" class="form-control" value="${(entity.headCount)!}" />
					<i class="input-group-addon" data-original-title="请输入总户数，户数为数字">户</i>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_parking">停车位数<span class="hm-required">*</span></label>
				<div class="col-sm-3 input-group">
					<input type="text" name="parking" id="J_parking" class="form-control" value="${(entity.parking)!}" />
					<i class="input-group-addon" data-original-title="请输入停车位数，停车位数为整数">个</i>
				</div>
			</div>				
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_propertyType">类型<span class="hm-required">*</span></label>
				<div class="col-sm-3">
					<select id="J_propertyType" name="propertyType" data-original-title="请选择类型" class="form-control">
					  	<option <#if entity.propertyType?? && entity.propertyType == '公寓'>selected= "selected"</#if> value="公寓">公寓</option>
							  	<option <#if entity.propertyType?? && entity.propertyType == '老公房'>selected= "selected"</#if> value="老公房">老公房</option>
							  	<option <#if entity.propertyType?? && entity.propertyType == '新里洋房'>selected= "selected"</#if> value="新里洋房">新里洋房</option>
							  	<option <#if entity.propertyType?? && entity.propertyType == '别墅'>selected= "selected"</#if> value="别墅">别墅</option>
							  	<option <#if entity.propertyType?? && entity.propertyType == '其它'>selected= "selected"</#if> value="其它">其它</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_greenRate">绿化率<span class="hm-required">*</span></label>
				<div class="col-sm-3 input-group">
					<input type="text" name="greenRate" id="J_greenRate" class="form-control" value="${(entity.greenRate)!}" />
					<i class="input-group-addon" data-original-title="绿化率必须为数字">%</i>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_volumeRate">容积率</label>
				<div class="col-sm-3">
					<input type="text" name="volumeRate" id="J_volumeRate" data-original-title="容积率必须为数字" class="form-control" value="${(entity.volumeRate)!}" />     				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_propertyFee">物业费<span class="hm-required">*</span></label>
				<div class="col-sm-3 input-group">
					<input type="text" name="propertyFee" id="J_propertyFee" class="form-control" value="${(entity.propertyFee)!}" />
					<i class="input-group-addon" data-original-title="物业费必须为数字">元/月/平</i>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_developer">开发商<span class="hm-required">*</span></label>
				<div class="col-sm-3">
					<input type="text" name="developer" id="J_developer" class="form-control" data-original-title="请输入开发商" value="${(entity.developer)!}"/>					
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_finishedTime">竣工时间<span class="hm-required">*</span></label>
				<div class="col-sm-3">
					<input type="text" name="finishedTime" id="J_finishedTime" class="form-control" data-original-title="请选择竣工时间，yyyy-mm-dd" value="${(entity.finishedTime)!}"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_annualPriceIncrement">年度涨幅<span class="hm-required">*</span></label>
				<div class="col-sm-3">
					<input type="text" name="annualPriceIncrement" id="J_annualPriceIncrement" data-original-title="输入涨幅" class="form-control" <#if monthData??> value="${monthData.annualPriceIncrement?string('#.####')}" </#if> <#if isAdmin?exists && isAdmin == false>readonly='readonly'</#if>/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_annualTurnoverPercent">年换手率<span class="hm-required">*</span></label>
				<div class="col-sm-3">
					<input type="text" name="annualTurnoverPercent" id="J_annualTurnoverPercent" data-original-title="年换手率必须为数字" class="form-control" <#if monthData??> value="${monthData.annualTurnoverPercent?string('#.####')}" </#if> <#if isAdmin?exists && isAdmin == false>readonly='readonly'</#if>/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_rentRevenue">租金回报率<span class="hm-required">*</span></label>
				<div class="col-sm-3">
					<input type="text" name="rentRevenue" id="J_rentRevenue" data-original-title="租金回报率必须为数字" class="form-control" <#if monthData??> value="${monthData.rentRevenue?string('#.####')}" </#if> <#if isAdmin?exists && isAdmin == false>readonly='readonly'</#if>/>
				</div>
			</div>
			<div class="form-group">
                <div class="col-sm-12 hm-center">
                	<#if entity.lockBasicInfo?? && entity.lockBasicInfo == 1>	
					<a class="btn btn-primary btn-lg" target="_self" href="residenceMap.controller?id=${(entity.id)!}">保存并下一步&nbsp;&raquo;</a>
					<#else>
					<button class="btn btn-primary btn-lg">保存并下一步&nbsp;&raquo;</button>
					</#if>
                </div>
            </div>
		</form>
	</div>
</div>
<script src="/webresources/js/lib/sea.js"></script>
<script type="text/javascript">
	__appConfig = {
		<#if entity.lockBasicInfo?? && entity.lockBasicInfo == 1>isLocked: true,</#if>
		isEdit: true,
		region: '${regionId}',
		plate: '${plateId}',
		regionAjax: '/ajax/getRegionList.controller',
		plateAjax: '/ajax/getPlateList.controller'	
	};
	
    seajs.config({
        base: "/webresources/js/",
        alias: {
            "jquery": "lib/jquery.js"
        }
    });
	seajs.use(['jquery','header', 'residence/newresidence']);
    
</script>
</body>

<#else>

<title>小区详情</title>
<body>
<h3>基本信息</h3>

<ul class="page-nav">
	<li class="focus">1. 基本信息</li>
	<li>
		<a href="/residence/${entity.id}">2. 设定坐标</a>
	</li>
	<li>
		<#if isAdmin?exists && isAdmin == true || isManager?exists && isManager == true>
			<a href="/residencePicList.controller?residenceId=${entity.residenceId}">3. 上传图片</a>
		<#else>
			<a href="/external/residencePicConsole.controller?residenceId=${entity.residenceId}">3. 上传图片</a>
		</#if>
	</li>
</ul>

<div>
			<table class="table">
			
				<form action="/addOrUpdateResidence.controller" method="post" id="residenceForm" class="form">
				<input type="hidden" name="id" value="${(entity.id)!}"/>
					<#if isAdmin?exists && isAdmin == true>
					<tr>
						<td style="width:120px;">
							区域/板块：
						</td>
						<td>
							<select id="J_region" name="regionId" class="form-control" data-original-title="请选择区域"></select>
							<select id="J_plate" name="plateId" class="form-control" data-original-title="请选择板块"></select>	
						</td>			
					</tr>
					<#else>
						<input name="regionId" value="${(regionId)!}" type="hidden"/>
						<input name="plateId" value="${(plateId)!}" type="hidden"/>
					</#if>
					<tr>
						<td style="width:120px;">
							小区名称：
						</td>
						<td>
						<#if isAdmin?exists && isAdmin == true>
							<input type="text" name="residenceName" value="${(entity.residenceName)!}"/>	<a id="a_nameHistory" href="#" onclick="showNameHistory(); return false;">名称修改历史</a> <div id='d_nameHistory'></div>
						<#else>
							<input type="text" name="residenceName" value="${(entity.residenceName)!}" readonly="true"/>	
						</#if>
						</td>			
					</tr>
					<tr>
						<td style="width:120px;">
							小区别名：
						</td>
						<td>
						<#if isAdmin?exists && isAdmin == true>
							<input type="text" name="aliasName" value="${(entity.aliasName)!}"/> <a id="a_aliasnameHistory" href="#" onclick="showAliasNameHistory(); return false;">别名修改历史</a> 多个别名用逗号隔开，例如：上海花园，上海春天     <div id='d_aliasnameHistory'></div>
						<#else>
							<input type="text" name="aliasName" value="${(entity.aliasName)!}" readonly="true"/> 多个别名用逗号隔开，例如：上海花园，上海春天
						</#if>
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>小区地址：
						</td>
						<td>
							<input type="text" name="address" class="text validate" _vType="address" value="${(entity.address)!}"/>
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>总户数：
						</td>
						<td>
							<input type="text" name="headCount" class="text validate" _vType="headCount" value="${(entity.headCount)!}" />户
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>停车位：
						</td>
						<td>
							<input type="text" name="parking" class="text validate" _vType="parking" value="${(entity.parking)!}" />个
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>类型：
						</td>
						<td>
							<select class="propertyType" name="propertyType" class="text validate" _vType="propertyType">
							  	<option <#if entity.propertyType?? && entity.propertyType == '公寓'>selected= "selected"</#if> value="公寓">公寓</option>
							  	<option <#if entity.propertyType?? && entity.propertyType == '老公房'>selected= "selected"</#if> value="老公房">老公房</option>
							  	<option <#if entity.propertyType?? && entity.propertyType == '新里洋房'>selected= "selected"</#if> value="新里洋房">新里洋房</option>
							  	<option <#if entity.propertyType?? && entity.propertyType == '别墅'>selected= "selected"</#if> value="别墅">别墅</option>
							  	<option <#if entity.propertyType?? && entity.propertyType == '其它'>selected= "selected"</#if> value="其它">其它</option>
							</select>
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>绿化率：
						</td>
						<td>
							<input type="text" name="greenRate" class="text validate" _vType="greenRate"  value="${(entity.greenRate)!}" />%
						</td>			
					</tr>
					<tr>
						<td>
							容积率：
						</td>
						<td>
							<input type="text" name="volumeRate" class="text validate" _vType="volumeRate" value="${(entity.volumeRate)!}" />（0.2234）
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>物业费：
						</td>
						<td>
							<input type="text" name="propertyFee" class="text validate" _vType="propertyFee"  value="${(entity.propertyFee)!}" />（1.2元/月/平） 
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>开发商：
						</td>
						<td>
							<input type="text" name="developer" class="text validate" _vType="developer" value="${(entity.developer)!}"/>
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>竣工时间：
						</td>
						<td>
							<input type="text" name="finishedTime" class="text validate" _vType="finishedTime"  id="finishedTime" value="${(entity.finishedTime)!}"/>
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>年度涨幅：
						</td>
						<td>
							<input type="text" name="annualPriceIncrement" class="text validate" _vType="number" <#if monthData??> value="${monthData.annualPriceIncrement?string('#.####')}" </#if> <#if isAdmin?exists && isAdmin == false>readonly='readonly'</#if>/>（跌幅-0.2234 涨幅0.2234）
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>年换手率：
						</td>
						<td>
							<input type="text" name="annualTurnoverPercent" class="text validate" _vType="number" <#if monthData??> value="${monthData.annualTurnoverPercent?string('#.####')}" </#if> <#if isAdmin?exists && isAdmin == false>readonly='readonly'</#if>/>（0.2234）
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>租金回报率：
						</td>
						<td>
							<input type="text" name="rentRevenue" class="text validate" _vType="number" <#if monthData??> value="${monthData.rentRevenue?string('#.####')}" </#if> <#if isAdmin?exists && isAdmin == false>readonly='readonly'</#if>/>（0.2234）
						</td>			
					</tr>
					<tr>
						<td>
							<input  type ="submit"  value="保存基本信息"/>
						</td>
						<td></td>
					</tr>
				</form>


				
					
					
					<#if isAdmin?exists && isAdmin == true>
					<form action="/updateResidenceShowStatus.controller" method="post" id="residenceStatusForm" class="form">
					<input type="hidden" name="id" value="${(entity.id)!}"/>
					<tr>
						<td>
							是否强制显示该小区：
						</td>
						<td>
							<label for="forceShowNo">否</label><input type="radio" name="forceShow" value="0" id="forceShowNo" <#if entity.forceShow?? && entity.forceShow == 0>checked= "checked"</#if>/>
						 	<label for="forceShowYes">是</label><input type="radio" name="forceShow" value="1" id="forceShowYes" <#if entity.forceShow?? && entity.forceShow == 1>checked= "checked"</#if>/>
						</td>
					</tr>
					<tr>
						<td>
							是否暗小区：
						</td>
						<td>
							<label for="zombieNo">否</label><input type="radio" name="zombie" value="0" id="zombieNo" <#if entity.zombie?? && entity.zombie == 0>checked= "checked"</#if>/>
						 	<label for="zombieYes">是</label><input type="radio" name="zombie" value="1" id="zombieYes" <#if entity.zombie?? && entity.zombie == 1>checked= "checked"</#if>/>
						</td>
					</tr>
					<tr>
						<td>
							<input  type ="submit"  value="保存显示状态"/>
						</td>
						<td></td>
					</tr>							
					</form>
					
					<form action="/updateResidenceLockStatus.controller" method="post" id="residenceLockStatusForm" class="form">
					<input type="hidden" name="id" value="${(entity.id)!}"/>
					<tr>
						<td>
							是否锁定基本信息：
						</td>
						<td>
							<label for="lockBasicInfoNo">否</label><input type="radio" name="lockBasicInfo" value="0" id="lockBasicInfoNo" <#if entity.lockBasicInfo?? && entity.lockBasicInfo == 0>checked= "checked"</#if>/>
						 	<label for="lockBasicInfoYes">是</label><input type="radio" name="lockBasicInfo" value="1" id="lockBasicInfoYes" <#if entity.lockBasicInfo?? && entity.lockBasicInfo == 1>checked= "checked"</#if>/>
						</td>
					</tr>
					<tr>
						<td>
							是否锁定地图：
						</td>
						<td>
							<label for="lockMapNo">否</label><input type="radio" name="lockMap" value="0" id="lockMapNo" <#if entity.lockMap?? && entity.lockMap == 0>checked= "checked"</#if>/>
						 	<label for="lockMapYes">是</label><input type="radio" name="lockMap" value="1" id="lockMapYes" <#if entity.lockMap?? && entity.lockMap == 1>checked= "checked"</#if>/>
						</td>
					</tr>
					<tr>
						<td>
							是否锁定图片：
						</td>
						<td>
							<label for="lockPicNo">否</label><input type="radio" name="lockPic" value="0" id="lockPicpNo" <#if entity.lockPic?? && entity.lockPic == 0>checked= "checked"</#if>/>
						 	<label for="lockPicYes">是</label><input type="radio" name="lockPic" value="1" id="lockPicYes" <#if entity.lockPic?? && entity.lockPic == 1>checked= "checked"</#if>/>
						</td>
					</tr>
					<tr>
						<td>
							<input  type ="submit"  value="保存锁定状态"/>
						</td>
						<td></td>
					</tr>							
					</form>
					
					</#if>
				
			</table>
</div>
<script src="/webresources/js/lib/sea.js"></script>
<script type="text/javascript">
	__appConfig = {
		region: '${regionId}',
		plate: '${plateId}',
		regionAjax: '/ajax/getRegionList.controller',
		plateAjax: '/ajax/getPlateList.controller'	
	};
	
    seajs.config({
        base: "/webresources/js/",
        alias: {
            "jquery": "lib/jquery.js"
        }
    });
	seajs.use(['jquery','header', 'residence/newresidence']);
    
    var hasShowNameHistory = false;
    var showNameHistory=function(){
    	if (hasShowNameHistory == false) {
	    	$.ajax({
				type: "post",
				url: "/ajax/getNameHistory.controller",
				data: {residenceId: ${entity.residenceId}, nameType: 1},
				dataType: "json",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (data) {		
					var total = '<table><thead><tr><th>修改时间</th><th>旧名称</th><th>修改人</th></thead><tbody>';
					for(var i in data.bizData){
						var tr = '<tr><td>' + data.bizData[i].addTime + '</td>'  + '<td>' + data.bizData[i].oldName + '</td>'  + '<td>' + data.bizData[i].accountName + '</td></tr>';
						total += tr;
					}
					total += '</tbody></table>';
					$('#d_nameHistory').html(total);
					
					hasShowNameHistory = true;
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
				}
		  	}); 
	  	}
	  	else {
	  		hasShowNameHistory = false;
	  		$('#d_nameHistory').html('');
	  	}
    }
    
    var hasShowAliasNameHistory = false;
    var showAliasNameHistory=function(){
    	if (hasShowAliasNameHistory == false) {
	    	$.ajax({
				type: "post",
				url: "/ajax/getNameHistory.controller",
				data: {residenceId: ${entity.residenceId}, nameType: 2},
				dataType: "json",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				success: function (data) {		
					var total = '<table><thead><tr><th>修改时间</th><th>旧名称</th><th>修改人</th></thead><tbody>';
					for(var i in data.bizData){
						var tr = '<tr><td>' + data.bizData[i].addTime + '</td>'  + '<td>' + data.bizData[i].oldName + '</td>'  + '<td>' + data.bizData[i].accountName + '</td></tr>';
						total += tr;
					}
					total += '</tbody></table>';
					$('#d_aliasnameHistory').html(total);
					
					hasShowAliasNameHistory = true;
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
				}
		  	}); 
	  	}
	  	else {
	  		hasShowAliasNameHistory = false;
	  		$('#d_aliasnameHistory').html('');
	  	}
    }
    
    
			$(document).ready(function(){
				$("#finishedTime").datepicker();
				
				$('#residenceForm').validate({
				focusInvalid : false,
					onkeyup : false,
					errorPlacement : function(error, element) {
						error.appendTo(element.parent());
					}
				});
				
				$.validator.draw();
			});
</script>
</body>

</#if>