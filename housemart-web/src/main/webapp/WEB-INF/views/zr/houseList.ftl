<html>
	<head></head>	
	<title>加州房源</title>
	<body>
		<br>
		<hr>
		<div>
			<div class="view" >
			</div>	
			<div class="update">
				<form id="search" action="/zr/findHouse.controller" name="search" method="POST">  
					mls编号<input name="mlsLike" type="text" value="${(mlsLike)!}"/>
					&nbsp;&nbsp;&nbsp;
					板块<input name="neighborhoodLike" type="text" value="${(neighborhoodLike)!}"/>  
					&nbsp;&nbsp;&nbsp;
					邮编或者区域<input name="blockLike" type="text" value="${(blockLike)!}"/>
					<input type="submit" value="搜索"/>
				</form>
			</div>
			<br>					
			<hr>
			<div class="update">
				<table>
						<tr>
							<th>ID</th><th>预览</th><th>标题</th><th>街区</th><th>MLS</th><th>区域</th><th>详情</th><th>价格</th><th>状态</th><th>发布时间</th><th>链接</th><th>抓取日期</th>
						</tr>
				<#if houses??>
					<#list houses as house>
						<tr>
						
							<td>${(house.id)!}</td>
							<td>
							<#list house.qnPics as pic>
							<img src="${(pic)!}" class="img-thumbnail" width="70" height="53">
							</#list>
							</td>
							<td>${(house.title)!}</td>
							<td>${(house.block)!}</td>
							<td>${(house.mls)!}</td>
							<td>${(house.neighborhood)!}</td>
							<td>${(house.houseDetail)!}</td>
							<td>${(house.price)!}</td>
							<td>${(house.status)!}</td>
							<td>${(house.listed)!}</td>
							<td><a href="${(house.link)!}" target="_blank">点击</a></td>
							<td><#if house.updateTime??>${house.updateTime?string("yyyy-mm-dd")}</#if></td>
						</tr>
					</#list>
				</#if>
				</table>		
			</div>				
		</div>
	</body>
</html>