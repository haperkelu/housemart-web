<html>
	<head></head>	
	<title>登盘${house.id!}</title>
	<body>
		<div>
			<#include "/house/houseNav.ftl">
		</div>
		<br>
		<div>
			<a href="javascript:;" id="markInvalid">标记为无效盘<span id="invalidNumer" _val="${(invalidLoggingNumber)!'0'}">(${(invalidLoggingNumber)!'0'})</span></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:;" id="loggingUpdate">登盘人变更申请</a>
		</div>
		<div style="width:900px;height:1450px;">
		<hr/>
		
		<div style="float:left;width:250px;">
			<table>
				<tr>
					<td>
						迈信ID
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.id)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						区域
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.region)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						板块
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.plate)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						小区名称
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.residence)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						地址
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.address)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						栋座
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.buildingNo)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						单元
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.cellNo)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						均价
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.avg)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						面积
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.houseArea)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						总价估值
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.totalValue)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						租金估值
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.rentValue)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						出售意向
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.saleIntention)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						出售价格
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.salePrice)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						出租意向
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.rentIntention)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						出租价格
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.rentPrice)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						外拨结果
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.dialResult)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						任务人
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.taskPerson)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						外拨时间
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.dialTime)!}"/>
					</td>
				</tr>
				<tr>
					<td>
						分配时间
					</td>
					<td><input type="text" readonly="readonly" value="${(maxinData.assignTime)!}"/>
					</td>
				</tr>
				<tr>
					<td>
					</td>
					<td>
						<a href="javascript:;" id="viewHouseContact">查看房东联系方式</a>
					</td>
				</tr>
			</table>
		</div>
		
		<div style="float:left;width:620px;margin:0 0 0 20px;">
			<form action="/houseUpdate.controller" method="post">
				<input type="hidden" name="id" value="${(house.id)!}" id="houseId"/>
				<input type="hidden" name="contactId" value="${(house.contactId)!}"/>
				<input type="hidden" name="propertyId" value="${(house.propertyId)!}"/>
				<table>
				
					<#if isAdmin?exists && isAdmin == true>
					<tr>
						<td>
							迁移
						</td>
						<td>
							小区名或拼音：
							<input type="text" name="residenceName" value="" />
							<input type="button" value="搜索" onclick="refreshResidenceList(false)"/>
							
							<p id="residences">
				
							</p>										
			
						</td>
					</tr>				
					</#if>
					
					<tr>
						<td>
							地址
						</td>
						<td>
							${(house.regionCityId)!} - ${(house.regionName)!} - ${(house.residenceName)!}
							- ${(house.residenceAddress)!} - - ${(house.buildingPrefix)!}${(house.buildingCodeBegin)!}${(house.buildingSuffix)!}
							- ${(house.cellFloorBegin)!}${(house.cellCodeBegin)!}
						</td>
					</tr>
					<tr>
						<td>
							具体名称
						</td>
						<td>
							<input type="text" name="detailName" value="${(house.detailName)!}" />
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:red;">楼层</span>
						</td>
						<td>
							第<input type="text" readonly="readonly" value="${(house.cellFloorBegin)!}"/>层 共
							<input type="text" readonly="readonly" value=""/>层
							<#include "/common/floorSelect.ftl">				
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:red;">产证面积</span>
						</td>
						<td>
							<#if isAdmin?exists && isAdmin == true>
								<input type="text" name="propertyArea" value="${(house.propertyArea)!}" />平米
							<#else>
								<input type="text" name="propertyArea" value="${(house.propertyArea)!}" readonly="readonly" />平米
							</#if>		
						</td>
					</tr>
					<tr>
						<td>
							占地面积
						</td>
						<td>
							<input type="text" name="occupiedArea" value="${(house.occupiedArea)!}"/>平米				
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:red;">房型</span>
						</td>
						<td>
							<#include "/common/roomTypeSelect.ftl">
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:blue;">建房时间</span>
						</td>
						<td>
							<select name="buildTimeString" <#if house.buildTime??>value='${house.buildTime?string("yyyy")}'</#if>>（日期：1980-1-1）
							  	<option value="">选择</option>
								<#list 1950..2013 as i>
									<option value="${i}" <#if house.buildTime?? && house.buildTime?string("yyyy") == i?string>selected= "selected"</#if>>${i}</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:blue;">装修</span>
						</td>
						<td>
							<#include "/common/decoratingSelect.ftl">
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:blue;">朝向</span>
						</td>
						<td>
							<select name="direction" value="${(house.direction)!}">
							  <option value="0">选择</option>
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
						</td>
					</tr>
					<tr>
						<td>
							类型
						</td>
						<td>
							<#include "/common/houseTypeSelect.ftl">
						</td>
					</tr>
					<tr>
						<td></td><td></td>
					</tr>
					<tr>
						<td>
							<span style="color:red;">房源出售状态</span>
						</td>
						<td>
							<select name="saleSaleStatus" value="${(house.saleSaleStatus)!}" id="saleStatus">
							  <option value="" >选择</option>
							  <option value="1"	<#if house.saleSaleStatus?? && house.saleSaleStatus == 1>selected= "selected"</#if>>在售</option>
							  <option value="2"	<#if house.saleSaleStatus?? && house.saleSaleStatus == 2>selected= "selected"</#if>>不售</option>
							</select>
						</td>
					</tr>
					<tr>	
						<td>
							看房方式
						</td>
						<td>
							<select name="viewHouseType" value="${(house.viewHouseType)!}" id="saleStatus">
							  <option value="" >选择</option>
							  <option value="1"	<#if house.viewHouseType?? && house.viewHouseType == 1>selected= "selected"</#if>>预约看房</option>
							  <option value="2"	<#if house.viewHouseType?? && house.viewHouseType == 2>selected= "selected"</#if>>立即看房</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:red;">售价</span>
						</td>
						<td>
							挂牌价<input type="text" name="salePrice" value="<#if house.salePrice??>${(house.salePrice/10000)!}</#if>"/>万
							&nbsp;&nbsp;
							底价<input type="text" name="saleBasePrice" value="<#if house.saleBasePrice??>${(house.saleBasePrice/10000)!}</#if>"/>万
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:blue;">出售标签</span>
						</td>
						<td>
							<input type="radio" name="saleSaleRec" value="0" <#if house.saleSaleRec?? && house.saleSaleRec == 0>checked= "checked"</#if>/>无 
							<input type="radio" name="saleSaleRec" value="1" <#if house.saleSaleRec?? && house.saleSaleRec == 1>checked= "checked"</#if>/>急售
							<input type="radio" name="saleSaleRec" value="2" <#if house.saleSaleRec?? && house.saleSaleRec == 2>checked= "checked"</#if>/>推荐
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:blue;">售房特色</span>
						</td>
						<td>
							<input type="text" value="" id="saleTagListView" readonly="readonly"/>
							<input type="hidden" name="saleTagList" id="saleTagList" value="${(house.saleTagList)!}"/><a href="javascript:;" id="selectSaleOption">选择标签</a> 至少3个标签，最多5个
							<div id="saleOptions" style="display:none;">
								<#if saleTagOptions??>
									<#list saleTagOptions as saleTag> 
									<div class="saleTag" _tId="${(saleTag.id)!}" _tName="${(saleTag.name)!}" _tCategoryName="${(saleTag.categoryName)!}">
										<a href="javascript:;" class="tagOption">${(saleTag.name)!}<input type="checkbox" class="status" readonly="readonly"/></a>
									</div>
									</#list>
								</#if>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<span>交易类型</span>
						</td>
						<td>
							<select name="saleDealType" value="${(house.saleDealType)!}" id="saleDealType">
							  <option value="" >选择</option>
							  <option value="1"	<#if house.saleDealType?? && house.saleDealType == '1'>selected= "selected"</#if>>税费各付</option>
							  <option value="2"	<#if house.saleDealType?? && house.saleDealType == '2'>selected= "selected"</#if>>买方全付</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:blue;">售房特殊说明</span>
						</td>
						<td>
							<input type="text" name="saleMemo" value="${(house.saleMemo)!}"/>
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:red;">房源出租状态</span>
						</td>
						<td>
							<select name="rentRentStatus" value="${(house.rentRentStatus)!}" id="rentStatus">
							  <option value="" >选择</option>
							  <option value="1"	<#if house.rentRentStatus?? && house.rentRentStatus == 1>selected= "selected"</#if>>在租</option>
							  <option value="2"	<#if house.rentRentStatus?? && house.rentRentStatus == 2>selected= "selected"</#if>>不租</option>
							  <option value="3"	<#if house.rentRentStatus?? && house.rentRentStatus == 3>selected= "selected"</#if>>已租</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><span style="color:red;">租价</span></td>
						<td>
						租金<input type="text" name="rentPrice" value="${(house.rentPrice)!}"/>元
						底价<input type="text" name="rentBasePrice" value="${(house.rentBasePrice)!}"/>元
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:blue;">租房类型</span>
						</td>
						<td>
							<select name="rentRentType" value="${(house.rentRentType)!}" id="rentRentType">
							  <option value="" >选择</option>
							  <option value="1"	<#if house.rentRentType?? && house.rentRentType == 1>selected= "selected"</#if>>整租</option>
							  <option value="2"	<#if house.rentRentType?? && house.rentRentType == 2>selected= "selected"</#if>>合租</option>
							</select>
						</td>
					</tr>
					<tr>	
						<td>
							<span style="color:blue;">出租标签</span>
						</td>
						<td>
							<input type="radio" name="rentRentRec" value="0" <#if house.rentRentRec?? && house.rentRentRec == 0>checked= "checked"</#if>/>无 
							<input type="radio" name="rentRentRec" value="1" <#if house.rentRentRec?? && house.rentRentRec == 1>checked= "checked"</#if>/>急租
							<input type="radio" name="rentRentRec" value="2" <#if house.rentRentRec?? && house.rentRentRec == 2>checked= "checked"</#if>/>推荐
						</td>
					</tr>
						<tr>
						<td>
							<span style="color:blue;">租房配置</span>
						</td>
						<td>
							<input type="checkbox" class="equipment" name="water" <#if rentEquipments.water??>checked="checked"</#if>/>水
							<input type="checkbox" class="equipment" name="power" <#if rentEquipments.power??>checked="checked"</#if>/>电
							<input type="checkbox" class="equipment" name="gas" <#if rentEquipments.gas??>checked="checked"</#if>/>煤气
							<input type="checkbox" class="equipment" name="heat" <#if rentEquipments.heat??>checked="checked"</#if>/>暖气
							<input type="checkbox" class="equipment" name="cable" <#if rentEquipments.cable??>checked="checked"</#if>/>有线电视
							<input type="checkbox" class="equipment" name="network" <#if rentEquipments.network??>checked="checked"</#if>/>宽带
							<input type="checkbox" class="equipment" name="tv" <#if rentEquipments.tv??>checked="checked"</#if>/>电视
							<input type="checkbox" class="equipment" name="refrigerator" <#if rentEquipments.refrigerator??>checked="checked"</#if>/>冰箱
							<input type="checkbox" class="equipment" name="airCondition" <#if rentEquipments.airCondition??>checked="checked"</#if>/>空调
							<input type="checkbox" class="equipment" name="washer" <#if rentEquipments.washer??>checked="checked"</#if>/>洗衣机
							<input type="checkbox" class="equipment" name="waterHeater" <#if rentEquipments.waterHeater??>checked="checked"</#if>/>热水机
							<input type="checkbox" class="equipment" name="microwave" <#if rentEquipments.microwave??>checked="checked"</#if>/>微波炉
							<input type="checkbox" class="equipment" name="telephone" <#if rentEquipments.telephone??>checked="checked"</#if>/>电话	
							<br>			
							<a href="javascript:;" id="selectAllEquipments">全选</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" id="deselectAllEquipments">清空</a>
						</td>
					</tr>
					<tr>	
						<td>
							<span style="color:blue;">租房特色</span>
						</td>
						<td>
							<input type="text" value="" id="rentTagListView" readonly="readonly"/>
							<input type="hidden" name="rentTagList" id="rentTagList" value="${(house.rentTagList)!}"/><a href="javascript:;" id="selectRentOption">选择标签</a> 至少3个标签，最多5个
							<div id="rentOptions" style="display:none;">
								<#if rentTagOptions??>
									<#list rentTagOptions as rentTag> 
									<div class="rentTag" _tId="${(rentTag.id)!}" _tName="${(rentTag.name)!}" _tCategoryName="${(rentTag.categoryName)!}">
										<a href="javascript:;" class="tagOption">${(rentTag.name)!}<input type="checkbox" class="status" readonly="readonly"/></a>
									</div>
									</#list>
								</#if>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:blue;">租房入住时间</span>
						</td>
						<td>
							<input type="text" name="rentDealTime" id="rentDealTime" <#if house.rentDealTime??>value='${house.rentDealTime?string("yyyy-MM-dd")}'</#if>/>（日期：1980-1-1）
						</td>
					</tr>
					<tr>
						<td>
							<span style="color:blue;">租房特殊说明</span>
						</td>
						<td>
							<input type="text" name="rentMemo" value="${(house.rentMemo)!}"/>
						</td>
					</tr>
					<tr>
						<td></td><td></td>
					</tr>
					<tr>
						<td>
							钥匙		
						</td>
						<td>
							<input type="radio" name="hasKey" value="1"	<#if house.hasKey?? && house.hasKey == 1>checked= "checked"</#if>/>有 
							<input type="radio" name="hasKey" value="0" <#if house.hasKey?? && house.hasKey == 0>checked= "checked"</#if>/>无 
							<input type="text" name="hasKeyMemo" value="${(house.hasKeyMemo)!}"/>
						</td>
					</tr>
					<tr>
						<td>
							产权编号
						</td>
						<td>
							<input type="text" name="propertyPropertyCode" value="${(house.propertyPropertyCode)!}"/>
						</td>
					</tr>
					<tr>
						<td>
							产权人
						</td>
						<td>
							<input type="text" name="propertyPropertyOwner" value="${(house.propertyPropertyOwner)!}"/>
						</td>
					</tr>
					<tr>
						<td>
							贷款情况
						</td>
						<td><input type="text" name="propertyLoan" value="${(house.propertyLoan)!}"/>
						</td>
					</tr>
				</table>
				<br>
				<input type="submit" value="提交更新"/>
			</form>
		</div>
		
		</div>
		<br>
		<hr/>
		<br>
		<div>
			房源跟进
			<form>
				<textarea id="processMemo"></textarea>
				<br/>
				<input type="button" id="addProcess" value="提交跟进" />
				<div id="houseProcess">
					
				</div>
				<hr/>
			</form>
		</div>
		<div id="more">
			<div>
				<a href="javascript:;" id="viewAuditUpdates">更新记录</a>
			</div>
			<br>
			<div id="opHistory" style="width:800px;">
			</div>
			<br>
			<hr/>
			<br>
			<div>
				<a href="javascript:;" id="viewContactUpdates">业主电话更新记录</a>
			</div>
			<br>
			<div id="updateHistory" style="width:800px;">
			</div>
		</div>
		
		
		<div id="update" class="detailLayer">
			<div class="detailLayerHeader">
			    <h3>登盘人变更申请</h3>
		  	</div>
	  		<div class="detailLayerContent">
				<div>地址：${(house.regionCityId)!} - ${(house.regionName)!} - ${(house.residenceName)!}
					- ${(house.residenceAddress)!} - - ${(house.buildingPrefix)!}${(house.buildingCodeBegin)!}${(house.buildingSuffix)!}
					- ${(house.cellFloorBegin)!}${(house.cellCodeBegin)!}</div>
				<div>当前登盘人：姓名： 电话： 负责板块：</div>
				<div>业主姓名：<input type="text" id="updateContactName"/></div>
				<div>业主电话：<input type="text" id="updateContactMobile"/></div>
				<div>产证面积：<input type="text" id="updateHousePropertyArea"/>平米</div>
				<a href="javascript:;" class="submit">提交</a> <a href="javascript:;" class="close">取消</a>
		  	</div>
		</div>
		
		
		
		<div id="dlg-contact" title="房东联系方式 ">
		    <div class="dlg-content">
		    	<table>
			    	<tr>
						<td>
							(mx)业主姓名
						</td>
						<td><input type="text" readonly="readonly" value="" id="maxinData_clientName"/>
						</td>
					</tr>
					<tr>
						<td>
							(mx)电话1
						</td>
						<td><input type="text" readonly="readonly" value="" id="maxinData_phone1"/>
						</td>
					</tr>
					<tr>
						<td>
							(mx)电话2
						</td>
						<td><input type="text" readonly="readonly" value="" id="maxinData_phone2"/>
						</td>
					</tr>
					<tr>
						<td>
							(mx)电话3
						</td>
						<td><input type="text" readonly="readonly" value="" id="maxinData_phone3"/>
						</td>
					</tr>
					<tr>
						<td>
							(mx)联系方式备注
						</td>
						<td><input type="text" readonly="readonly" value="" id="maxinData_contactMemo"/>
						</td>
					</tr>
					<tr>
						<td>
							业主姓名
						</td>
						<td>
							<input type="text" name="contactName" value="" readonly="readonly" id="house_contactName"/>
						</td>
					</tr>
					<tr>
						<td>
							业主电话
						</td>
						<td>
							<input type="text" name="contactMobile" value="" readonly="readonly" id="house_contactMobile"/>
						</td>
					</tr>
					<tr>
						<td>
							业主备注
						</td>
						<td>
							<input type="text" name="memo" value="" id="house_memo"/>
						</td>
					</tr>
		    	</table>
		    </div>
		    <div class="dlg-buttons">
		    	<input type="button" value="确定" onclick='$("#dlg-contact").dialog("close")'/>
		    </div>
		</div>
		
		<div id="dlg-contact-no-power" title="无法查看房东联系方式">
		    <div class="dlg-content">
		    	今天已经达到查看的上限，请明天继续。
		    </div>
		    <div class="dlg-buttons">
		    	<input type="button" value="确定" onclick='$("#dlg-contact-no-power").dialog("close")'/>
		    </div>
		</div>
		
		<div id="dlg-move-house" title="迁移到其它小区">
			<input type="hidden" id="destResidenceId"/>
		    <div class="dlg-content">
		    	确定要把当前房源迁移到<span id="destResidenceName" style="color:red;"></span>？
		    </div>
		    <div class="dlg-buttons">
		    	<input type="button" value="确定" onclick='moveToResidence();'/>
		    	<input type="button" value="取消" onclick='$("#dlg-move-house").dialog("close");'/>
		    </div>
		</div>
		
		
		<style>
			.detailLayer {
				display:none;
				padding-bottom:20px;
				position:fixed;
				_position:absolute;
			
				width:650px;
				left:50%;
				margin-left:-325px!important;
				right:10%;
				top:2%;
				background:#f5f5f5;
				border:3px solid #999;   
			    -moz-box-shadow:0px 0px 8px #ccc;   
			    -webkit-box-shadow:0px 0px 8px #ccc;   
			    box-shadow:0px 0px 8px #ccc;   
				z-index:1000;
			}
			.detailLayer.big{
				width:800px;
				margin-left:-400px!important;
			}
			.detailLayer.small{
				height:200px;
				width:400px;
				top:30%;
				margin-left:-200px!important;
				min-width:400px;
				min-height:200px;
			}
			.detailLayer .layerCtrl {
				position:absolute;
				top:5px;
				right:5px;
			}
			.detailLayer .layerCtrl .btnClose {
				background:url(../images/btn.png) -112px 0px no-repeat;
				width:32px;
				height:32px;
				border:none;
				cursor:pointer;
			}
			.detailLayer .layerCtrl .btnClose:hover {
				background:url(../images/btn.png) -112px -32px no-repeat;
			}
			.detailLayer .layerCtrl .btnClose:active {
				background:url(../images/btn.png) -112px -64px no-repeat;
			}
			.detailLayer .detailLayerHeader{
				position:relative;
				margin:20px 20px 0 20px;
			}
			.detailLayer .detailLayerHeader h3 {
				width:60%;
				padding:0 0 15px 0;
				margin:0 0 15px 0;
				font-size:1.4em;
				overflow:hidden;
			}
			.detailLayer .detailLayerHeader h3 span {
				border-left:1px solid #ccc;
				padding:0 0 0 20px;
				margin:0 0 0 20px;
			}
			.detailLayer .detailLayerHeader .menu{
				position:absolute;
				right:20px;
				top:25px;
				overflow:hidden;
			}
			.detailLayer .detailLayerHeader .menuList li {
				float:left;
				margin: 0 0 0 10px;
				padding:0;
			}
			
			.detailLayer .detailLayerContent {
				margin:0 0 10px 20px;
			}

			#residences span {float:left;margin:5px 20px 5px 0;cursor:pointer;
				border:1px dashed #aaa;padding:2px 5px;}
			#residences span a, #selectedResidences span a {text-decoration:none;color:#000;}
			#residences .selected a {color:red;}
			
			#selectedResidences span {float:left;margin:5px 20px 5px 0;cursor:pointer;
				border:1px solid #aaa;padding:2px 5px;}
			#selectedResidences span input {display:hidden;}
			
			#residences .zombie a, #selectedResidences .zombie a {color:#888;}

		</style>
		
		<script>
		
			$(document).ready(function(){
				init();
				bind();
				exec();
			});
			
			function init(){
				var saleTagsStr = $("#saleTagList").val();
				var saleTags;
				var saleTagsView = "";
				if(saleTagsStr.length > 0){
					saleTags = saleTagsStr.split(",");
					for(i=0;i < saleTags.length ;i++){
						if(i>0)
							saleTagsView += ",";
						saleTagsView += $("div[_tId=" + saleTags[i] + "]").attr("_tName");
						$("div[_tId='" + saleTags[i] + "'][_tCategoryName='sale']").find(".status").attr("checked","true");
					}
					$("#saleTagListView").val(saleTagsView);
				}
				
				var rentTagsStr = $("#rentTagList").val();
				var rentTags;
				var rentTagsView = "";
				if(rentTagsStr.length > 0){
					rentTags = rentTagsStr.split(",");
					for(i=0;i < rentTags.length ;i++){
						if(i>0)
							rentTagsView += ",";
						rentTagsView += $("div[_tId=" + rentTags[i] + "]").attr("_tName");
						$("div[_tId='" + rentTags[i] + "'][_tCategoryName='rent']").find(".status").attr("checked","true");
					}
					$("#rentTagListView").val(rentTagsView);
				}
				
				$("#buildTime").datepicker();
				$("#rentDealTime").datepicker();
				
				
				$("#dlg-contact").dialog(
					{
						autoOpen: false,
						width: 500,
						modal: true,
						resizable: false
					});
				$("#dlg-contact-no-power").dialog(
					{
						autoOpen: false,
						width: 500,
						modal: true,
						resizable: false
					});
				$("#dlg-move-house").dialog(
					{
						autoOpen: false,
						width: 500,
						modal: true,
						resizable: false
					});
			}
			
			function bind(){
				$("#markInvalid").click(function(){
					markHouseInvalid();
				});
				$("#loggingUpdate").click(function(){
					$("#update").show();
				});
				$("#update .close").click(function(){
					clearUpdateForm();
					$("#update").hide();
				});
				$("#update .submit").click(function(){
					submitUpdateForm();
				});
				$("#viewAuditUpdates").click(function(){
					refreshAuditUpdates();
				});
				$("#viewContactUpdates").click(function(){
					refreshContactUpdates();
				});
				$("#selectSaleOption").click(function(){
					$("#saleOptions").toggle();
				});
				$("#selectRentOption").click(function(){
					$("#rentOptions").toggle();
				});
				$(".tagOption").click(function(){
					clickTagOption($(this));
				});
				$("#addProcess").click(function(){
					$.ajax({
						type: "post",
						url: "/ajax/addHouseProcess.controller",
						data: {houseId: $("#houseId").val(), memo:$.trim($("#processMemo").val())},
						dataType: "json",
						contentType:'application/x-www-form-urlencoded; charset=UTF-8',
						success: function (data) {		
							refreshHouseProcess();
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	});
				});
				$("#viewHouseContact").click(function(){
					$.ajax({
						type: "get",
						url: "/ajax/ViewHouseContact.controller",
						data: {houseId: $("#houseId").val()},
						dataType: "json",
						contentType:'application/x-www-form-urlencoded; charset=UTF-8',
						success: function (data) {		
							houseContactInfoDialog(data);
						},
						error: function (XMLHttpRequest, textStatus, errorThrown) {
						}
				  	});
				});
				
				$("#selectAllEquipments").click(function(){
					$(".equipment").attr("checked", "true");
				});
				
				$("#deselectAllEquipments").click(function(){
					$(".equipment").removeAttr("checked");
				});
			}
			
			function exec(){
				refreshHouseProcess();
				refreshAuditUpdates();
				refreshContactUpdates();
			}
			
			function refreshAuditUpdates(){
				$.ajax({
					type: "post",
					url: "/ajax/getContentUpdateHistoryByHouseId.controller",
					data: {houseId: $("#houseId").val(),type:5},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {		
						var total = '<table><th>日期</th><th>修改人</th><th>修改前</th><th>修改后</th>';
						for(var i in data.bizData){
							var item = '<tr><td>' 
							+ formatDateTime(data.bizData[i].updateTime) + '</td><td>' 
							+ data.bizData[i].committerId + '</td><td>' 
							+ data.bizData[i].preContent + '</td><td>' 
							+ data.bizData[i].postContent + '</td></tr>';
							total += item;
						}
						total += '</table>';
						$('#opHistory').html(total);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
			}
			
			function refreshContactUpdates(){
				$.ajax({
					type: "post",
					url: "/ajax/getContentUpdateHistoryByHouseId.controller",
					data: {houseId: $("#houseId").val(),type:2},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {		
						var total = '<table><th>日期</th><th>修改人</th><th>修改前</th><th>修改后</th>';
						for(var i in data.bizData){
							var item = '<tr><td>' 
							+ formatDateTime(data.bizData[i].updateTime) + '</td><td>' 
							+ data.bizData[i].committerId + '</td><td>' 
							+ data.bizData[i].preContent + '</td><td>' 
							+ data.bizData[i].postContent + '</td></tr>';
							total += item;
						}
						total += '</table>';
						$('#updateHistory').html(total);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
			}
			
			function refreshHouseProcess(){
				$.ajax({
					type: "post",
					url: "/ajax/getProcessListByHouseId.controller",
					data: {houseId: $("#houseId").val()},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {		
						var total = '<table><th>日期</th><th>内容</th>';
						for(var i in data.bizData){
							var item = '<tr><td>' 
							+  formatDateTime(data.bizData[i].addTime) + '</td><td>'
							+  data.bizData[i].memo
							+ '</td></tr>';
							total += item;
						}
						total += '</table>';
						$('#houseProcess').html(total);
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
			}
			
			function markHouseInvalid(){
				$.ajax({
					type: "post",
					url: "/ajax/markHouseInvalid.controller",
					data: {houseId: $("#houseId").val()},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						var val = parseInt($("#invalidNumer").attr('_val')) + 1;
						$("#invalidNumer").html('(' + val + ')');
						alert("标记为无效盘成功!");
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
			}
			
			function clearUpdateForm(){
				$("#update input").val("");
			}
			
			function submitUpdateForm(){
				$.ajax({
					type: "post",
					url: "/ajax/committerUpdateAudit.controller",
					data: {
						houseId: $("#houseId").val(),
						contactName: $("#updateContactName").val(),
						contactMobile: $("#updateContactMobile").val(),
						housePropertyArea: $("#updateHousePropertyArea").val()
					},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						$('#update').fadeOut();						
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});
			}
			
			function clickTagOption(thisDom){
				var tagType= thisDom.parent().attr("_tCategoryName");
				var clickedOptionId = thisDom.parent().attr("_tId");
				var tagList ="";
				var tagListView = "";
				if(thisDom.find(".status").attr("checked") == true){
					thisDom.find(".status").removeAttr("checked");
				}
				else{
					thisDom.find(".status").attr("checked", "true");
				}
				
				var tagsView = "";
				var tags = "";
				$("#" + tagType + "Options").find(".status[checked]").each(function(i){
					if(i>0){
						tagsView += ",";
						tags += ",";
					}
					tagsView += $(this).closest("." + tagType + "Tag").attr("_tName");
					tags += $(this).closest("." + tagType + "Tag").attr("_tId");
				});
				$("#" + tagType + "TagListView").val(tagsView);
				$("#" + tagType + "TagList").val(tags);
			}
			
			var formatDateTime = function (obj) {   
		        var myDate = new Date(obj);   
		        var year = myDate.getFullYear();  
		        var month = ("0" + (myDate.getMonth() + 1)).slice(-2);  
		        var day = ("0" + myDate.getDate()).slice(-2);  
		        var h = ("0" + myDate.getHours()).slice(-2);  
		        var m = ("0" + myDate.getMinutes()).slice(-2);  
		        var s = ("0" + myDate.getSeconds()).slice(-2);   
		        var mi = ("00" + myDate.getMilliseconds()).slice(-3);  
	            return year + "-" + month + "-" + day + " " + h + ":" + m + ":" + s;   
		    }
		    
		    var houseContactInfoDialog = function(data) {
		    	if(data.code == 1){
					var maxinData;
		    		if((maxinData = data.bizData.maxinData) !=null ){
						$("#maxinData_clientName").val(maxinData.clientName);
						$("#maxinData_phone1").val(maxinData.phone1);
						$("#maxinData_phone2").val(maxinData.phone2);
						$("#maxinData_phone3").val(maxinData.phone3);
						$("#maxinData_contactMemo").val(maxinData.contactMemo);
		    		}
		    		var house;
					if((house = data.bizData.house) !=null ){
						$("#house_contactName").val(house.contactName);
						$("#house_contactMobile").val(house.contactMobile);
						$("#house_memo").val(house.contactMemo);
		    		}
					
		    		$("#dlg-contact").dialog("open");
		       	}else{
		    		// no power
		    		$("#dlg-contact-no-power").dialog("open");
		    	}
			}
			
			function refreshResidenceList(selectedOpt){
	
				$('#residences').html("数据加载中...");
				if (selectedOpt)
				{
					if($("#plate").find("option:selected").val() > 0 ||
							$("#region").find("option:selected").val() > 0)
					{
						$("input[name='residenceName']").val("");
					  	$.ajax({
							type: "post",
							url: "/ajax/getResidenceListByPlateId.controller",
							data: {plateId: $("#plate").find("option:selected").val(), regionId: $("#region").find("option:selected").val()},
							dataType: "json",
							contentType:'application/x-www-form-urlencoded; charset=UTF-8',
							success: function (data) {
								showResidenceList(data);
							},
							error: function (XMLHttpRequest, textStatus, errorThrown) {
							}
					  	});
				  	}
				}
				else
				{
					var key = $.trim($("input[name='residenceName']").val());
					if (key != "")
					{
						$('#region').val("");
						$('#plate').val("");
						 
						$.ajax({
							type: "post",
							url: "/ajax/getResidenceListByResidenceName.controller",
							data: {residenceName: key},
							dataType: "json",
							contentType:'application/x-www-form-urlencoded; charset=UTF-8',
							success: function (data) {
								showResidenceList(data);
							},
							error: function (XMLHttpRequest, textStatus, errorThrown) {
							}
					  	});
					}
				}
			}
					
			function showResidenceList(data)
			{
				var total = '';
				var index = '';
				var indexList = new Array();
				for(var i in data.bizData){
					var alpha = data.bizData[i].pinyinName.toUpperCase().charAt(0).toUpperCase();
					
					if (alpha.charCodeAt(0) < 65 || alpha.charCodeAt(0) > 90)
					{
						alpha = "";
					}
					
					var option = '<span class="region-residence index-' + alpha + (data.bizData[i].zombie == 1 ? ' zombie' : "")  + '" id="res-' + data.bizData[i].residenceId + '" onclick="addAccountResidence(' + data.bizData[i].residenceId + ',\'' + data.bizData[i].residenceName + '\')">' + 
					'<a href="javascript:void(0)">' +
					(data.bizData[i].pinyinName != null ? alpha + " " : "") + 
					data.bizData[i].residenceName + '(' + data.bizData[i].brokerCount + ')' + '</a></span>';
					total += option;
					
					if ($.inArray(alpha, indexList) < 0)
					{
						indexList.push(alpha);
					}
					indexList.sort();
				}
				index += '<div class="residence-index">';
				for (var i in indexList)
				{
					var alpha = indexList[i];
					if (alpha == "")
					{
						alpha = "其它";
					}
					
					index += '<a href="javascript:void(0)" onclick="showResidenceAlphaList(this)">' + alpha + '</a> ';
				}
				index += '</div>';
				$('#residences').html(index + total);
				$(".region-residence").hide();
				if (indexList.length > 0)
				{
					$(".index-" + indexList[0]).show();
				}
				
				markSelectedResidences();
			}
			
			function showResidenceAlphaList(obj)
			{
				$(".region-residence").hide();
				var alpha = $(obj).html();
				if (alpha == "其它")
				{
					alpha = "";
				}
				$(".index-" + alpha).show();;
			}
			
			function markSelectedResidences()
			{
				$(".region-residence").each(function(){
					var id = $(this).attr("id");
					id = id.split("-");
					id = id[1];
					if ($("#select-res-" + id).length > 0)
					{
						$(this).css("color", "red");
					}
					else
					{
						$(this).css("color", "black");
					}
				});
			}
			
			function addAccountResidence(id, name)
			{
				$("#destResidenceId").val(id);
				$("#destResidenceName").html(name);
				$("#dlg-move-house").dialog("open");		
			}
		
			function moveToResidence(){
				var destResidenceId = $("#destResidenceId").val();
				var houseId = $("#houseId").val(); 
				$.ajax({
					type: "post",
					url: "/ajax/changeResidence.controller",
					data: {residenceId: destResidenceId, houseId: houseId},
					dataType: "json",
					contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					success: function (data) {
						alert("迁移成功");
						location.reload();
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
					}
			  	});		
			}	
		</script>
		
	</body>
</html>