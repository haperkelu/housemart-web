<title>
	修改我的信息
</title>
<body>
	<link href="/webresources/css/login.css" rel="stylesheet">
	<div class="container hm-container">
		<h1 class="hm-page-title">修改我的信息</h1>
		<div class="container hm-inputs-wrap hm-bordered">
			<form action="myAccountSave.controller" method="POST" enctype="multipart/form-data">
			<table id="table-user" class="table table-bordered" style="margin-top:20px;">
				<tr>
					<th style="width:100px;">姓名</th>
					<td>
						${(account.name!"")}
					</td>
					<th>性别</th>
					<td>
						<select name="gender">
							<option value="1" <#if ((account.gender!-1) == 1)>selected="selected"</#if>>男</option>
							<option value="0" <#if ((account.gender!-1) == 0)>selected="selected"</#if>>女</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>身份证号</th>
					<td colspan="3">
						${account.identityID!""}
					</td>
				</tr>
				<tr>
					<th>联系方式1</th>
					<td>
						<input name="contactInfo1" class="form-control" type="text" value="${account.contactInfo1!""}" />
					</td>
					<th>联系方式2</th>
					<td>
						<input name="contactInfo2" class="form-control" type="text" value="${account.contactInfo2!""}" />
					</td>
				</tr>
				<tr>
					<th>微信号</th>
					<td colspan="3">
						<input name="weixin" class="form-control" type="text" value="${account.weixin!""}" />
					</td>
				</tr>
				<tr>
					<th>工作区域</th>
					<td colspan="3" id="selectedResidences">
						<#if accountResidences??>
							<#list accountResidences as accountResidence>
						<span class="btn btn-info">
							${accountResidence.residenceName}
						</span>
							</#list>
						</#if>
					</td>
				</tr>
				<tr id="J_accountImgs">
					<th>图片</th>
					<td>
						<img style="width:100px;" src="${account.picURL!""}" title="点击删除头像图片" data-type="1"/>
					</td>
					<td>
						<img style="width:100px;" src="${account.idURL!""}" title="点击删除身份证图片" data-type="2"/>
					</td>
					<td>
						<img style="width:100px;" src="${account.cardURL!""}" title="点击删除名片图片" data-type="3"/>
					</td>
				</tr>
				<tr>
					<th>图片类型</th>
					<td>
						头像
					</td>
					<td>
						身份证
					</td>
					<td>
						名片
					</td>
				</tr>
				<tr>
					<th>上传</th>
					<td>
						<#if ((account.picLocked!0) == 1) >
						已认证
						<#else>
						<input name="picFile" type="file" id="J_uploadHead" class="btn btn-default col-sm-3" value="上传"/>
						<input type="submit" class="btn btn-default col-sm-8" value="上传头像" style="margin-left: 10px;visibility:hidden">
						</#if>
					</td>
					<td>
						<#if ((account.idLocked!0) == 1) >
						已认证
						<#else>
						<input name="idFile" type="file" id="J_uploadID" class="btn btn-default col-sm-3" value="上传"/>
						<input type="submit" class="btn btn-default col-sm-8" value="上传身份证" style="margin-left: 10px;visibility:hidden">
						</#if>
					</td>
					<td>
						<#if ((account.cardLocked!0) == 1) >
						已认证
						<#else>
						<input name="cardFile" id="J_uploadCard" type="file" class="btn btn-default col-sm-3" value="上传"/>
						<input type="submit" class="btn btn-default col-sm-8" value="上传名片" style="margin-left: 10px;visibility:hidden">
						</#if>
					</td>
				</tr>
				<tr>
					<th>&nbsp;</th>
					<td colspan="3">
						<input type="submit" class="btn btn-primary btn-lg" value="保 存" />
					</td>
				</tr>
			</table>
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
    </script>
	<script>
		seajs.use(['jquery', 'header', 'account/modify-info']);
	</script>	
</body>