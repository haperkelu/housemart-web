<title>
	${account.id?? ? string('账号编辑', '账号分配')}
</title>
<body>
	<link rel="stylesheet" href="resources/css/account.css" />
	<script src="resources/javascript/account.js" type="text/javascript"></script>
	
	<h2>账号分配</h2>
	<form action="accountSave.controller" method="POST" enctype="multipart/form-data" onsubmit="return checkAccountFrom()">
	<table id="table-user" class="table">
		<tr>
			<th><span>*</span>用户名</th>
			<td style="width:200px;">
				<input name="loginName" type="text" value="${account.loginName!""}" />
			</td>
			<th>类型</th>
			<td>
				<input name="type" type="radio" value="0" ${((account.type!1) == 0) ? string('checked="checked"', '')}  />
				内部经纪人
				<input name="type" type="radio" value="1" ${((account.type!1) == 1) ? string('checked="checked"', '')}  />
				合作经纪人
				<input name="type" type="radio" value="2" ${((account.type!1) == 2) ? string('checked="checked"', '')}  />
				内部+合作经纪人
			</td>
		</tr>
		<tr>
			<th><span>*</span>姓名</th>
			<td>
				<input name="name" type="text" value="${account.name!""}" />
			</td>
			<th><span>*</span>性别</th>
			<td>
				<input name="gender" type="radio" value="1" ${((account.gender!-1) == 1) ? string('checked="checked"', '')} />
				男
				<input name="gender" type="radio" value="0" ${((account.gender!-1) == 0) ? string('checked="checked"', '')} />
				女
			</td>
		</tr>
		<tr>
			<th><span>*</span>身份证号</th>
			<td colspan="3">
				<input name="identityID" type="text" value="${account.identityID!""}" />
			</td>
		</tr>
		<tr>
			<th><span>*</span>联系方式1</th>
			<td>
				<input name="contactInfo1" type="text" value="${account.contactInfo1!""}" />
			</td>
			<th>联系方式2</th>
			<td>
				<input name="contactInfo2" type="text" value="${account.contactInfo2!""}" />
			</td>
		</tr>
		<tr>
			<th>微信号</th>
			<td colspan="3">
				<input name="weixin" type="text" value="${account.weixin!""}" />
				<input name="weixinJoined" type="checkbox" value="1" ${((account.weixinJoined!0) == 1) ? string('checked="checked"', '')} />
				已加好友
			</td>
		</tr>
		<tr>
			<th><span>*</span>身份</th>
			<td colspan="3">
				<input name="positionType" type="radio" value="初级经纪人" ${((account.positionType!"") == "初级经纪人") ? string('checked="checked"', '')} />
				初级经纪人（无交互权）
				<input name="positionType" type="radio" value="经纪人"  ${((account.positionType!"") == "经纪人") ? string('checked="checked"', '')} />
				经纪人
				<input name="positionType" type="radio" value="区域经理"  ${((account.positionType!"") == "区域经理") ? string('checked="checked"', '')} />
				区域经理
			</td>
		</tr>
		<tr>
			<th>所属公司</th>
			<td colspan="3">
				<input name="company" type="text" value="${account.company!""}" style="width:600px;"/>
			</td>
		</tr>
		<tr>
			<th>公司地址</th>
			<td colspan="3">
				<input name="companyAddress" type="text" value="${account.companyAddress!""}" style="width:600px;"/>
			</td>
		</tr>
		<tr>
			<th>备注</th>
			<td colspan="3">
				<input name="note" type="text" value="${account.note!""}" style="width:600px;"/>
			</td>
		</tr>
		<tr>
			<th>图片</th>
			<td>
				<img style="width:100px;" src="${account.picURL!""}" style="display:none;" onload="$(this).show()" />
			</td>
			<td>
				<img style="width:100px;" src="${account.idURL!""}" style="display:none;" onload="$(this).show()" />
			</td>
			<td>
				<img style="width:100px;" src="${account.cardURL!""}" style="display:none;" onload="$(this).show()" />
			</td>
		</tr>
		<tr>
			<th>上传</th>
			<td>
				头像<br/>
				<input name="picFile" type="file" /><br/>
				<input name="picLocked" type="checkbox" ${((account.picLocked!0) == 1) ? string('checked="checked"', '')} value="1" /> 认证通过
			</td>
			<td>
				身份证<br/>
				<input name="idFile" type="file" /><br/>
				<input name="idLocked" type="checkbox" ${((account.idLocked!0) == 1) ? string('checked="checked"', '')} value="1" /> 认证通过
			</td>
			<td>
				名片<br/>
				<input name="cardFile" type="file" /><br/>
				<input name="cardLocked" type="checkbox" ${((account.cardLocked!0) == 1) ? string('checked="checked"', '')} value="1" /> 认证通过
			</td>
		</tr>
		<tr id="manager-row">
			<th><span>*</span>行政经理</th>
			<td colspan="3">
				<select id="managerID" name="managerID">
					<option value="-1">请选择</option>
				<#if managers??>
					<#list managers as manager>
						<#if manager.id != account.id!0 >
					<option value="${manager.id}" ${((account.managerID!0) == manager.id) ? string('selected="selected"', '')}>${manager.name}</option>
						</#if>
					</#list>
				</#if>
				</select>
			</td>
		</tr>
		<tr>
			<th><span>*</span>工作小区</th>
			<td colspan="3">
				<select name="regionCityId" value="" id="city">
					<option value="" selected="selected">选择城市</option>
					<option value="1">上海</option>
				</select>
				<select name="regionParentId" value="" id="region">
					<option value="" selected="selected">选择区</option>
				</select>
				<select name="regionRegionId" id="plate">
							
				</select>
				小区名或拼音：
				<input type="text" name="residenceName" value="" />
				<input type="button" value="搜索" onclick="refreshResidenceList(false)"/>
				<br/>
				<a href="javascript:void(0);" id="select-all-residences" onclick="selectAllResidences()">全选</a>
				<a href="javascript:void(0);" id="clear-all-residences" onclick="clearAllResidences()">清空</a>
				<a href="javascript:void(0);" onclick="addAccountRegion()">添加区域</a>
				<a href="javascript:void(0);" onclick="addAccountPlate()">添加板块</a>
			</td>
		</tr>
		<tr>
			<th>板块楼盘</th>
			<td colspan="3" id="residences">
				
			</td>
		</tr>
		<tr>
			<th>已选区域</th>
			<td colspan="3" id="selectedRegions">
				<#if accountRegions??>
					<#list accountRegions as accountResidence>
				<span id="select-res-${accountResidence.residenceID}" onclick="deleteAccountResidence(${accountResidence.residenceID})">
					<input type="checkbox" name="regionID" value="${accountResidence.residenceID}" checked="checked" />
					${accountResidence.residenceName}
				</span>
					</#list>
				</#if>
			</td>
		</tr>
		<tr>
			<th>已选板块</th>
			<td colspan="3" id="selectedPlates">
				<#if accountPlates??>
					<#list accountPlates as accountResidence>
				<span id="select-res-${accountResidence.residenceID}" onclick="deleteAccountResidence(${accountResidence.residenceID})">
					<input type="checkbox" name="plateID" value="${accountResidence.residenceID}" checked="checked" />
					${accountResidence.residenceName}
				</span>
					</#list>
				</#if>
			</td>
		</tr>
		<tr>
			<th>已选楼盘</th>
			<td colspan="3" id="selectedResidences">
				<#if accountResidences??>
					<#list accountResidences as accountResidence>
				<span ${((account.residenceZombie!0) == 1) ? string('class="zombie"', '')} id="select-res-${accountResidence.residenceID}" onclick="deleteAccountResidence(${accountResidence.residenceID})">
					<a href="javascript:void(0)" title="${accountResidence.brokerList}">
						<input type="checkbox" name="residenceID" value="${accountResidence.residenceID}" checked="checked" />
						${accountResidence.residencePinyinName?substring(0,1)?upper_case} ${accountResidence.residenceName}
						(${accountResidence.brokerCount})
					</a>
				</span>
					</#list>
				</#if>
			</td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td colspan="3">
				<input type="submit" value="保 存" />
				<input type="hidden" name="id" value="${account.id!-1}" />
			</td>
		</tr>
	</table>
	</form>
	<script type="text/javascript">
		initAccountEdit();
	</script>
</body>