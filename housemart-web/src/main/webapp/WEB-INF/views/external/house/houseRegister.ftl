<html>
	<head></head>	
	<title>
		发布房源 - 基本信息
	</title>
	<body>
		<link href="/webresources/css/widget/calendar.css" rel="stylesheet"/>
		<link href="/webresources/css/newsell.css" rel="stylesheet"/>
		<div class="container hm-container">
			<h1 class="hm-page-title">
				<#if (param.viewType!) == "sale">
				<#if ((house.id!0)>0)>编辑<#else>发布</#if>出售房
				</#if>
				<#if (param.viewType!) == "rent">
				<#if ((house.id!0)>0)>编辑<#else>发布</#if>出租房
				</#if>
			</h1>
			<div class="row hm-step-wrap hm-bordered">
		        <div class="col-sm-4 hm-step active"><span></span><i>1、基本信息</i></div>
		        
		        <div class="col-sm-4 hm-step after-active"><span></span>
			        <i>
					2. 上传图片
			        </i>
		        </div>
		        <div class="col-sm-4 hm-step"><span></span>
		        	<i>
					3. 完成
			        </i>
		        </div>
		    
		    </div>	
			<div class="container hm-inputs-wrap hm-bordered">
		        <p class="text-muted hm-form-tip">“<span class="hm-required">*</span>”为必填内容</p>
		        <form class="form-horizontal" role="form" id="J_releaseForm" action="/external/houseUpdate.controller" method="post">
		            <input type="hidden" name="id" value="${(house.id)!}"/>
					<input type="hidden" name="viewType" value="${(param.viewType)!}"/>
					<#if ((create!'false') == 'true')>
						<input type="hidden" name="create" value="true"/>
					</#if>
					
		            <div class="form-group">
		                <label class="col-sm-3 control-label" for="J_residence">选择小区<span class="hm-required">*</span></label>
		                <div class="col-sm-3">
		                	<#if ((house.status!0) == 1)>
		                    <select name="residenceId" value="${(house.residenceId)!}" id="J_residence" class="form-control">
		                    <#if ((house.id!0) == 0)>
							<option value="">选择小区</option>
							</#if>
							<#if accountResidences??>
								<#list accountResidences as accountResidence>
								<#if ((house.residenceId!0) == accountResidence.residenceID) >
								<option value="${accountResidence.residenceID}">${accountResidence.residenceName}</option>
								</#if>
								</#list>
							</#if>
		                    </select>
		                    <#else>
		                    <select name="residenceId" value="${(house.residenceId)!}" id="J_residence" class="form-control">
								<#if ((house.id!0) == 0)>
								<option value="">选择小区</option>
								</#if>
							<#if accountResidences??>
								<#list accountResidences as accountResidence>
								<#if ((house.residenceId!0) == accountResidence.residenceID) >
								<option selected="selected" value="${accountResidence.residenceID}">
									${accountResidence.residencePinyinName?substring(0,1)?upper_case}
									${accountResidence.residenceName}
								</option>
								<#else>
								<option value="${accountResidence.residenceID}">
									${accountResidence.residencePinyinName?substring(0,1)?upper_case}
									${accountResidence.residenceName}
								</option>
								</#if>
								</#list>
							</#if>
							</select>
							</#if>
		                </div>
		            </div>
		            
		            <div class="form-group">
		                <label  class="col-sm-3 control-label" for="J_residenceName">小区别名<span class="hm-required">*</span></label>
		                <div class="col-sm-3">
		                    <#if ((house.status!0) == 1)>
							${(house.detailName)!}
							<input type="hidden" id="J_residenceName" name="detailName" value=<#if house.detailName??>${(house.detailName)!}</#if>">
							<#else>
							<input type="text" data-original-title="请正确输入具体名称" placeholder="具体名称" class="form-control" id="J_residenceName" name="detailName" value="<#if house.detailName??>${(house.detailName)!}</#if>"/>
							</#if>
		                </div>
		            </div>
		            <div class="form-group">
		                <label  class="col-sm-3 control-label" for="J_houseType">类型<span class="hm-required">*</span></label>
		                <div class="col-sm-3">
		                	<#if ((house.status!0) == 1)>
							<span>${(house.houseType)!}</span>
							<input type="hidden" name="houseType" value="${(house.houseType)!}"/>
							<#else>
							<#include "/common/houseTypeSelect.ftl">
							</#if>
		                </div>
		            </div>
		          
		            <div class="form-group form-inline">
		                <label  class="col-sm-3 control-label" for="J_blockNo">栋座<span class="hm-required" style='display: none;'>*</span></label>
		                <div class="col-sm-9">
		                    <#if ((house.status!0) == 1)>
							${(house.buildingNo)!'xxx'} 栋（号）
							<input type="hidden" id="J_blockNo" name="buildingNo" value="${(house.buildingNo)!}" />
		                	
							<#else>
		                    <input type="text" id="J_blockNo" name="buildingNo" value="${(house.buildingNo)!}" class="form-control wid-200"/><select class="form-control wid-60" name="blockUnit" data-original-title="请正确输入栋（号）"><option value="栋">栋</option><option value="号">号</option></select>
		                	</#if>
		                </div>
		            </div>
		            <!--
		            <div class="form-group form-inline">
		                <label  class="col-sm-3 control-label" for="J_blockNo">单元</label>
		                <div class="col-sm-3 input-group">
		                	<#if ((house.status!0) == 1)>
							${(house.cellNo)!'xxx'} 单元（室）
							<input type="hidden" id="J_cellNo" name="cellNo" value="${(house.cellNo)!}"/>
							<#else>
		                    <input type="text" id="J_cellNo" name="cellNo" value="${(house.cellNo)!}" class="form-control wid-60"/> <i class="input-group-addon" data-original-title="请正确输入单元（室）">单元（室）</i>
		                	</#if>
		                </div>
		            </div>
		            -->
		            <div class="form-group">
		                <label  class="col-sm-3 control-label" for="J_area">产证面积<span class="hm-required">*</span></label>
		                <div class="col-sm-3 input-group">
						<#if ((house.status!0) == 1)>
						${(house.propertyArea)!}平米
						<input type="hidden" name="propertyArea" value="${(house.propertyArea)!}"  id="J_area">
						<#else>
		                    <input type="input" name="propertyArea" value="${(house.propertyArea)!}" class="form-control" id="J_area" placeholder="产证面积">
		                    <i class="input-group-addon" data-original-title="面积必须为数字">平米</i>	
						</#if>
		                </div>
		            </div>
		            <div class="form-group">
		                <label  class="col-sm-3 control-label" for="J_cover">占地面积</label>
		                <div class="col-sm-3 input-group">
		                  	<#if ((house.status!0) == 1)>
							${(house.occupiedArea)!}平米
							<input type="hidden" name="occupiedArea" value="${(house.occupiedArea)!}" id="J_cover">
							<#else>
							<input type="input" name="occupiedArea" value="${(house.occupiedArea)!}" class="form-control" id="J_cover" placeholder="占地面积">
		                    <i data-original-title="面积必须为数字" class="input-group-addon">平米</i>
							</#if>  
		                </div>	
		            </div>
		            <#if (param.viewType!) == "sale">
		            <div class="form-group">
		                <label  class="col-sm-3 control-label" for="J_price">价格<span class="hm-required">*</span></label>
		                <div class="col-sm-3 input-group">
		                	<#if ((house.status!0) == 1)>
							${((house.salePrice!0)/10000)!} 万元
							<input type="hidden" name="salePrice" value="<#if house.salePrice??>${((house.salePrice!0)/10000)!}</#if>" id="J_price">
							<#else>
							<input type="input" name="salePrice" value="<#if house.salePrice??>${((house.salePrice!0)/10000)!}</#if>" class="form-control" placeholder="价格" id="J_price">
		                    <i class="input-group-addon" data-original-title="价格必须为数字">万元</i>
							</#if>    
		                </div>	
		            </div>
		            </#if> 
		            <#if (param.viewType!) == "rent">
		            <div class="form-group">
		                <label  class="col-sm-3 control-label" for="J_price">租金<span class="hm-required">*</span></label>
		                <div class="col-sm-3 input-group">
		                	<#if ((house.status!0) == 1)>
							${(house.rentPrice)!} 元/月
							<input type="hidden" name="rentPrice" value="${(house.rentPrice)!}"  id="J_price">
							<#else>
							<input type="input" name="rentPrice" value="${(house.rentPrice)!}" class="form-control" placeholder="价格" id="J_price">
		                    <i class="input-group-addon" data-original-title="租金必须为数字">元/月</i>
							</#if>    
		                </div>	
		            </div>
					</#if>
		            <div class="form-group form-inline">
		                <label  class="col-sm-3 control-label">房型<span class="hm-required">*</span></label>
		                <div class="col-sm-7">
		                	<#if ((house.status!0) == 1)>
							<span id="room-type-txt">${(house.roomType)!}</span>
							<input type="hidden"  id="J_rooms" value="1"/>
							<input type="hidden"  id="J_parlor" value="1"/>
							<input type="hidden"  id="J_washroom" value="1"/>
							<input type="hidden"  id="J_veranda" value="1"/>
							<#else>
							<#include "/common/roomTypeSelect.ftl">
							</#if>
		                    </div>
		            </div>
		            <div class="form-group">
		                <label  class="col-sm-3 control-label" for="J_floor">楼层<span class="hm-required">*</span></label>
		                <div class="col-sm-3">
		                    <#if ((house.status!0) == 1)>
							<span id="floor-txt">${(house.floor)!}</span>
							<input type="hidden"  id="J_floor" value="${(house.floor)!}"/>
							<#else>
							<#include "/common/floorSelect.ftl">	
							</#if>
		                </div>
		            </div>
		            <#if (param.viewType!) == "rent">
					<div class="form-group">
						<label  class="col-sm-3 control-label" for="J_time">入住时间<span class="hm-required">*</span></label>
						<div class="col-sm-3">
							<#if ((house.status!0) == 1)>
							${house.rentDealTime?string("yyyy-MM-dd")}
							<input type="hidden" class="form-control" name="rentDealTime" id="J_time" <#if house.rentDealTime??>value='${house.rentDealTime?string("yyyy-MM-dd")}'</#if>/>
							<#else>
							<input data-original-title="请正确选择日期，格式为yyyy-mm-dd" placeholer="yyyy-mm-dd 例如(2013-01-01)" type="text" class="form-control" name="rentDealTime" id="J_time" <#if house.rentDealTime??>value='${house.rentDealTime?string("yyyy-MM-dd")}'</#if>/>
							</#if>
						</div>
					</div>
					</#if>					
		            <div class="form-group">
		                <label  class="col-sm-3 control-label" for="J_decorating">装修<span class="hm-required">*</span></label>
		                <div class="col-sm-3">
		                	<#include "/common/decoratingSelect.ftl">
		                </div>       
		            </div>
		            <div class="form-group">
		                <label  class="col-sm-3 control-label" for="J_direction">朝向<span class="hm-required">*</span></label>
		                <div class="col-sm-3">
		                	<select data-original-title="请正确选择朝向" id="J_direction" class="form-control" name="direction" value="${(house.direction)!}">
							  <option value="">选择</option>
							  <option value="1000" <#if house.direction?? && house.direction == 1000>selected= "selected"</#if>>东</option>
							  <option value="100" <#if house.direction?? && house.direction == 100>selected= "selected"</#if>>南</option>
							  <option value="10" <#if house.direction?? && house.direction == 10>selected= "selected"</#if>>西</option>
							  <option value="1"	<#if house.direction?? && house.direction == 1>selected= "selected"</#if>>北</option>
							  <option value="1100"	<#if house.direction?? && house.direction == 1100>selected= "selected"</#if>>东南</option>
							  <option value="1001"	<#if house.direction?? && house.direction == 1001>selected= "selected"</#if>>东北</option>
							  <option value="110"	<#if house.direction?? && house.direction == 110>selected= "selected"</#if>>西南</option>
							  <option value="11"	<#if house.direction?? && house.direction == 11>selected= "selected"</#if>>西北</option>
							  <option value="101"	<#if house.direction?? && house.direction == 101>selected= "selected"</#if>>南北</option>
							  <option value="1010"	<#if house.direction?? && house.direction == 1010>selected= "selected"</#if>>东西</option>
							</select>
		                </div>       
		            </div>
		            <div class="form-group">
		                <label  class="col-sm-3 control-label" for="J_history">建房时间<span class="hm-required">*</span></label>
		                <div class="col-sm-3">
		                    <select id="J_history" name="buildTimeString" class="form-control" <#if house.buildTime??>value='${house.buildTime?string("yyyy")}'</#if>>（日期：1980-1-1）
							  	<option value="">选择</option>
								<#list 2013..1900 as i>
									<option value="${i}" <#if house.buildTime?? && house.buildTime?string("yyyy") == i?string>selected= "selected"</#if>>${i}</option>
								</#list>
							</select>
		                </div>
		            </div>
		            <#if (param.viewType!) == "rent">
		            <div class="form-group">
		                <label  class="col-sm-3 control-label">租房配置<span class="hm-required">*</span></label>
		                <div class="col-sm-7" id="J_equipments" data-original-title="请选择租房配置">
		                    <label><input type="checkbox" class="equipment" name="water" <#if rentEquipments.water??>checked="checked"</#if>/>水</label>
							<label><input type="checkbox" class="equipment" name="power" <#if rentEquipments.power??>checked="checked"</#if>/>电</label>
							<label><input type="checkbox" class="equipment" name="gas" <#if rentEquipments.gas??>checked="checked"</#if>/>煤气</label>
							<label><input type="checkbox" class="equipment" name="heat" <#if rentEquipments.heat??>checked="checked"</#if>/>暖气</label>
							<label><input type="checkbox" class="equipment" name="cable" <#if rentEquipments.cable??>checked="checked"</#if>/>有线电视</label>
							<label><input type="checkbox" class="equipment" name="network" <#if rentEquipments.network??>checked="checked"</#if>/>宽带</label>
							<label><input type="checkbox" class="equipment" name="tv" <#if rentEquipments.tv??>checked="checked"</#if>/>电视</label>
							<label><input type="checkbox" class="equipment" name="refrigerator" <#if rentEquipments.refrigerator??>checked="checked"</#if>/>冰箱</label>
							<label><input type="checkbox" class="equipment" name="airCondition" <#if rentEquipments.airCondition??>checked="checked"</#if>/>空调</label>
							<label><input type="checkbox" class="equipment" name="washer" <#if rentEquipments.washer??>checked="checked"</#if>/>洗衣机</label>
							<label><input type="checkbox" class="equipment" name="waterHeater" <#if rentEquipments.waterHeater??>checked="checked"</#if>/>热水机</label>
							<label><input type="checkbox" class="equipment" name="microwave" <#if rentEquipments.microwave??>checked="checked"</#if>/>微波炉</label>
							<label><input type="checkbox" class="equipment" name="telephone" <#if rentEquipments.telephone??>checked="checked"</#if>/>电话</label>			
							<a class="btn btn-default btn-sm" href="javascript:;" id="J_selectAll">全选</a>
							<a href="javascript:;" class="btn btn-default btn-sm margin-left" id="J_unselectAll">清空</a>	
		                </div>
		            </div>
					</#if>
		            <div class="form-group form-inline">
		                <label  class="col-sm-3 control-label" for="J_special">特殊说明</label>
		                <div class="col-sm-3">
		                    <#if (param.viewType!) == "rent">
								付
								<select class="form-control wid-60" id="J_pay">
								  <option <#if house.memo?? && house.memo?length gte 2 && house.memo?substring(1,2) == '0'> selected= "selected"</#if> value="0">0</option>
								  <option <#if house.memo?? && house.memo?length gte 2 && house.memo?substring(1,2) == '1'> selected= "selected"</#if> value="1">1</option>
								  <option <#if house.memo?? && house.memo?length gte 2 && house.memo?substring(1,2) == '2'> selected= "selected"</#if> value="2">2</option>
								  <option <#if house.memo?? && house.memo?length gte 2 && house.memo?substring(1,2) == '3'> selected= "selected"</#if> value="3">3</option>
								  <option <#if house.memo?? && house.memo?length gte 2 && house.memo?substring(1,2) == '4'> selected= "selected"</#if> value="4">4</option>
								  <option <#if house.memo?? && house.memo?length gte 2 && house.memo?substring(1,2) == '5'> selected= "selected"</#if> value="5">5</option>
								</select>
								押
								<select class="form-control wid-60" id="J_mortgage">
								  <option <#if house.memo?? && house.memo?length gte 4 && house.memo?substring(3,4) == '0'> selected= "selected"</#if> value="0">0</option>
								  <option <#if house.memo?? && house.memo?length gte 4 && house.memo?substring(3,4) == '1'> selected= "selected"</#if> value="1">1</option>
								  <option <#if house.memo?? && house.memo?length gte 4 && house.memo?substring(3,4) == '2'> selected= "selected"</#if> value="2">2</option>
								  <option <#if house.memo?? && house.memo?length gte 4 && house.memo?substring(3,4) == '3'> selected= "selected"</#if> value="3">3</option>
								  <option <#if house.memo?? && house.memo?length gte 4 && house.memo?substring(3,4) == '4'> selected= "selected"</#if> value="4">4</option>
								  <option <#if house.memo?? && house.memo?length gte 4 && house.memo?substring(3,4) == '5'> selected= "selected"</#if> value="5">5</option>
								</select>
								<input type="hidden" name="memo" value="${(house.memo)!}" id="J_payment"/>	
							</#if>
							<#if (param.viewType!) == "sale">
								<select class="form-control" value="${(house.memo)!}" id="J_salePay">
								  <option value="0">选择</option>
								  <option <#if house.memo?? && house.memo == '税费各付价'>selected= "selected"</#if> value="1">税费各付价</option>
								  <option <#if house.memo?? && house.memo == '房东到手价'>selected= "selected"</#if> value="2">房东到手价</option>
								</select>
								<input type="hidden" name="memo" value="${(house.memo)!}" readonly="readonly" id="J_payment"/>		
							</#if>
		                </div>
		            </div>
		            <#if (param.viewType!) == "sale">
		            <div class="form-group">
		                <label  class="col-sm-3 control-label">房源标签</label>
		                <div class="col-sm-5 input-group">
		                	<input type="hidden" name="saleTagList" id="J_tagsIds" value="${(house.saleTagList)!}"/>
		                    <input type="input" class="form-control" readonly id="J_selectedTags" autocomplete="off"/>
		                	<div class="input-group-btn">
		                        <button class="btn btn-default" type="button" id="J_ctrlTags" data-toggled='0'>
		                            选择标签 <span class="caret"></span>
		                        </button>（最多选择三个）
		                    </div>		                
		                </div>
		            </div>           
		            <div class="form-group" id="J_tagsCtrlWrap" style='display:none'>
		                <label class="col-sm-3 control-label"></label>
		                <#if saleTagOptions??>
		                	<div class="col-sm-6 hm-tag-link collapse in" id="J_tagsWrap">
							<#list saleTagOptions as saleTag> 
							<a href="###" data-id="${(saleTag.id)!}" data-name="${(saleTag.name)!}" date-category="${(saleTag.categoryName)!}" class="btn btn-default">${(saleTag.name)!}</a>
							</#list>
							</div>
						</#if>
		            </div>
		             </#if>
		             <#if (param.viewType!) == "rent">
		            <div class="form-group">
		                <label  class="col-sm-3 control-label">房源标签</label>
		                <div class="col-sm-5 input-group">
		                	<input type="hidden" id="J_tagsIds" name="rentTagList" value="${(house.rentTagList)!}"/>
		                    <input type="input" class="form-control" readonly id="J_selectedTags" autocomplete="off"/>
		                	<div class="input-group-btn">
		                        <button class="btn btn-default" type="button" id="J_ctrlTags" data-toggle='0'>
		                            选择标签 <span class="caret"></span>
		                        </button>
		                    </div>
		                </div>
		            </div>           
		            <div class="form-group" id="J_tagsCtrlWrap" style='display:none'>
		                <label class="col-sm-3 control-label"></label>
		                <#if rentTagOptions??>
		                	<div class="col-sm-6 hm-tag-link collapse in" id="J_tagsWrap">
							<#list rentTagOptions as rentTag> 
							<a href="###" data-id="${(rentTag.id)!}" data-name="${(rentTag.name)!}" date-category="${(rentTag.categoryName)!}" class="btn btn-default">${(rentTag.name)!}</a>
							</#list>
							</div>
						</#if>
		            </div>
		             </#if>
		            <div class="form-group">
		                <div class="col-sm-12 hm-center">
		                    <button class="btn btn-primary btn-lg">下一步&nbsp;&raquo;</button>
		                	<input id="house-status" name="house-status" type="hidden" value="${house.status!}" />
		                </div>
		            </div>
		        </form>
		    </div>		    		
		</div>
		<script src="/webresources/js/lib/sea.js"></script>
		<script type="text/javascript">
		    seajs.config({
		        base: "/webresources/js/",
		        alias: {
		            "jquery": "lib/jquery.js"
		        }
		    });
		    <#if (param.viewType!) == "sale">
			seajs.use(['jquery','header', 'newsell']);
			</#if>
			<#if (param.viewType!) == "rent">
			seajs.use(['jquery','header', 'newrent']);
			</#if>
		    
		</script>
	</body>
</html>