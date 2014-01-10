<#if type?exists && (type == 1 || type == -1)>

<title>新建小区</title>
<body>
<link href="/webresources/css/widget/calendar.css" rel="stylesheet"/>
<link href="/webresources/css/newsell.css" rel="stylesheet"/>
<div class="container hm-container">
	<h1 class="hm-page-title">
		新建小区
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
	<div class="container hm-inputs-wrap hm-bordered">
		<p class="text-muted hm-form-tip">“<span class="hm-required">*</span>”为必填内容</p>
		<form class="form-horizontal" role="form" id="J_releaseForm" action="/addOrUpdateResidence.controller" method="post">
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_region">选择区域<span class="hm-required">*</span></label>
				<div class="col-sm-3">
					<select id="J_region" name="regionId" class="form-control" data-original-title="请选择区域"></select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_plate">选择板块<span class="hm-required">*</span></label>
				<div class="col-sm-3">
					<select id="J_plate" name="plateId" class="form-control" data-original-title="请选择板块"></select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_residence">小区名称<span class="hm-required">*</span></label>
				<div class="col-sm-4">
					<input type="text"  name="residenceName" id="J_residence" data-original-title="请输入小区名称" class="form-control" value="${(entity.residenceName)!}" />
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
					  	<option  value="公寓">公寓</option>
					  	<option  value="老公房">老公房</option>
					  	<option  value="新里洋房">新里洋房</option>
					  	<option  value="别墅">别墅</option>
					  	<option  value="其它">其它</option>
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
					<input type="text" name="annualPriceIncrement" id="J_annualPriceIncrement" data-original-title="输入涨幅" class="form-control" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_annualTurnoverPercent">年换手率<span class="hm-required">*</span></label>
				<div class="col-sm-3">
					<input type="text" name="annualTurnoverPercent" id="J_annualTurnoverPercent" data-original-title="年换手率必须为数字" class="form-control" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="J_rentRevenue">租金回报率<span class="hm-required">*</span></label>
				<div class="col-sm-3">
					<input type="text" name="rentRevenue" id="J_rentRevenue" data-original-title="租金回报率必须为数字" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
                <div class="col-sm-12 hm-center">
                    <button class="btn btn-primary btn-lg">下一步&nbsp;&raquo;</button>
                </div>
            </div>
		</form>
	</div>
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
    
</script>
</body>

<#else>

<title>新建小区</title>
<body>
<h3>新建小区</h3>
<ul class="page-nav">
	<li class="focus">1. 新建小区</li>
	<li>
		2. 设定坐标
	</li>
	<li>
		3. 上传图片
	</li>
</ul>
<div>
			<form action="/addOrUpdateResidence.controller" method="post" id="residenceForm" class="form">
				
				<table class="table">
					<tr>
						<td>
						</td>
						<td>
						区域:<select style="width:50px" id="region" name="regionId"></select>
						板块:<select style="width:50px" id="plate" name="plateId"></select>
						</td>
					</tr>
					<tr>
						<td style="width:120px;">
							<span style="color:red;">*</span>小区名称：
						</td>
						<td>
							<input type="text"  name="residenceName" class="text validate" _vType="residenceName" value="${(entity.residenceName)!}" />	
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
							  	<option  value="公寓">公寓</option>
							  	<option  value="老公房">老公房</option>
							  	<option  value="新里洋房">新里洋房</option>
							  	<option  value="别墅">别墅</option>
							  	<option  value="其它">其它</option>
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
					<#if isAdmin?exists && isAdmin == true>
					<tr>
						<td>
							<span style="color:red;">*</span>年度涨幅：
						</td>
						<td>
							<input type="text" name="annualPriceIncrement" class="text validate" _vType="number" />（跌幅-0.2234 涨幅0.2234）
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>年换手率：
						</td>
						<td>
							<input type="text" name="annualTurnoverPercent" class="text validate" _vType="number" />（0.2234）
						</td>			
					</tr>
					<tr>
						<td>
							<span style="color:red;">*</span>租金回报率：
						</td>
						<td>
							<input type="text" name="rentRevenue" class="text validate" _vType="number" />（0.2234）
						</td>			
					</tr>
					</#if>
					<tr>
						<td>
							<input  type ="submit"  value="提交"/>
						</td>
						<td></td>
					</tr>
				</table>
			</form>
</div>

<script>
	var currentRegion = ${regionId};
	var currentPlate = ${plateId};
	
	$(document).ready(function(){	
		
		$("#finishedTime").datepicker();
		
		$('#region').change(function(){ 
			refreshPlateList();
		});
		
		$.ajax({
			type: "post",
			url: "/ajax/getRegionList.controller",
			data: {},
			dataType: "json",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (data) {		
				var total = '';
				for(var i in data.bizData){
					var selected = ' ';
					if(data.bizData[i].id == currentRegion){
						selected += 'selected';
					}
					var option = '<option value=' + data.bizData[i].id + selected + '>' + data.bizData[i].name + '</option>';
					total += option;
				}
				$('#region').html(total);
				refreshPlateList();		
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			}
		}); 
		
		
		$('#residenceForm').validate({
			focusInvalid : false,
			onkeyup : false,
			errorPlacement : function(error, element) {
				error.appendTo(element.parent());
			}
		});
		
		$.validator.draw();
	});

	
  	
  	var refreshPlateList = function(){
  	
	  	$.ajax({
			type: "post",
			url: "/ajax/getPlateList.controller",
			data: {parentId: $("#region").find("option:selected").val()},
			dataType: "json",
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (data) {		
				var total = '';
				for(var i in data.bizData){
					var selected = ' ';
					if(data.bizData[i].id == currentPlate){
						selected += 'selected';
					}
					var option = '<option value=' + data.bizData[i].id + selected  + '>' + data.bizData[i].name + '</option>';
					total += option;
				}
				$('#plate').html(total);		
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			}
	  	}); 
  	
  	}	

</script>

</body>


</#if>