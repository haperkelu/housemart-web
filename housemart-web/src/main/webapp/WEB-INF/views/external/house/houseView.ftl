<html>
	<head>
	</head>	
	<title>
		查看房源信息
	</title>
	<body>
		<link href="/webresources/css/detail.css" rel="stylesheet">
		<div class="container hm-container">
			<h1 class="hm-page-title">房源信息</h1>
			<div class="hm-bordered hm-fields">
				<table class="table">
					<thead>
			            <tr>
			                <th>&nbsp;</th>
			                <th>&nbsp;</th>
			            </tr>
		            </thead>
		            <tbody>
		            	<tr>
			                <td><b>小区</b></td>
			                <td>${(house.residenceName)!}</td>
			            </tr>
			            <tr>
			                <td><b>小区别名</b></td>
			                <td>${(house.detailName)!}</td>
			            </tr>
			            <tr>
			                <td><b>类型</b></td>
			                <td id="J_houseType" data-origin="${(house.houseType)!}">Loading…</td>
			            </tr>
			            <tr>
			                <td><b>栋座单元</b></td>
			                <td>
			                	${(house.buildingNo)!'xxx'} 栋（号）
								${(house.cellNo)!'xxx'} 单元（室）
			                </td>
			            </tr>
			            <tr>
			                <td><b>产证面积</b></td>
			                <td>${(house.propertyArea)!}平米</td>
			            </tr>
						<#if ((house.occupiedArea!0) > 0)>
			            <tr>
			                <td><b>占地面积</b></td>
			                <td>${(house.occupiedArea)!}平米</td>
			            </tr>
						</#if>
			            <#if (house.saleSaleStatus!0) == 1>
			            <tr>
			                <td><b>价格</b></td>
			                <td>${((house.salePrice!0)/10000)!}万元</td>
			            </tr>
			            </#if>
			            <#if (house.rentRentStatus!0) == 1>
						<tr>
							<td><b>租金</b></td>
							<td>
								${(house.rentPrice)!}元/月
							</td>
						</tr>
						</#if>
						<tr>
							<td><b>房型</b></td>
							<td id="J_roomType" data-origin="${(house.roomType)!}">
								Loading…	
							</td>
						</tr>
						<tr>
							<td><b>楼层</b></td>
							<td id="J_floor" data-origin="${(house.floor)!}">
								Loading…
							</td>
						</tr>
						
						<#if (house.rentRentStatus!0) == 1>
						<tr>
							<td><b>入住时间</b></td>
							<td>
								<#if house.rentDealTime??>
								${house.rentDealTime?string("yyyy-MM-dd")}
								</#if>
							</td>
						</tr>
						</#if>
						
						<tr>
							<td><b>装修</b></td>
							<td>
								<#if house.decorating?? && house.decorating == 5>豪装</#if>
								<#if house.decorating?? && house.decorating == 1>精装</#if>
								<#if house.decorating?? && house.decorating == 2>简装</#if>
								<#if house.decorating?? && house.decorating == 3>毛坯</#if>
								<#if house.decorating?? && house.decorating == 4>清水</#if>
							</td>
						</tr>
						
						<tr>
							<td><b>朝向</b></td>
							<td>
								<#if house.direction?? && house.direction == 1000>东</#if>
								<#if house.direction?? && house.direction == 100>南</#if>
								<#if house.direction?? && house.direction == 10>西</#if>
								<#if house.direction?? && house.direction == 1>北</#if>
								<#if house.direction?? && house.direction == 1100>东南</#if>
								<#if house.direction?? && house.direction == 1001>东北</#if>
								<#if house.direction?? && house.direction == 110>西南</#if>
								<#if house.direction?? && house.direction == 11>西北</#if>
								<#if house.direction?? && house.direction == 101>南北</#if>
								<#if house.direction?? && house.direction == 1010>东西</#if>
							</td>
						</tr>
						
						<tr>
							<td><b>建房时间</b></td>
							<td>
								<#if house.buildTime??>
								${house.buildTime?string("yyyy")}
								</#if>
							</td>
						</tr>
						
						<#if (house.rentRentStatus!0) == 1>
						<tr>
							<td><b>租房配置</b></td>
							<td>
								<#if rentEquipments.water??>水</#if>
								<#if rentEquipments.power??>电</#if>
								<#if rentEquipments.gas??>煤气</#if>
								<#if rentEquipments.heat??>暖气</#if>
								<#if rentEquipments.cable??>有线电视</#if>
								<#if rentEquipments.network??>宽带</#if>
								<#if rentEquipments.tv??>电视</#if>
								<#if rentEquipments.refrigerator??>冰箱</#if>
								<#if rentEquipments.airCondition??>空调</#if>
								<#if rentEquipments.washer??>洗衣机</#if>
								<#if rentEquipments.waterHeater??>热水机</#if>
								<#if rentEquipments.microwave??>微波炉</#if>
								<#if rentEquipments.telephone??>电话</#if>
							</td>
						</tr>
						</#if>
						
						<tr>
							<td><b>特殊说明</b></td>
							<td>
								<#if (house.rentRentStatus!0) == 1>
									付
									<#if house.memo?? && house.memo?length gte 2>
									${house.memo?substring(1,2)}
									</#if>
									押
									<#if house.memo?? && house.memo?length gte 4>
									${house.memo?substring(3,4)}
									</#if>
								</#if>
								<#if (house.saleSaleStatus!0) == 1>
									${house.memo!}
								</#if>
							</td>
						</tr>
						
						<#if (house.saleSaleStatus!0) == 1>
						<tr>
							<td><b>房源标签</b></td>
							<td>
								<div id="saleTagListView">
								</div>
								<input type="hidden" name="saleTagList" id="saleTagList" value="${(house.saleTagList)!}"/>
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
						</#if>
						
						<#if (house.rentRentStatus!0) == 1>
						<tr>	
							<td><b>房源标签</b></td>
							<td>
								<div id="rentTagListView">
								</div>
								<input type="hidden" name="rentTagList" id="rentTagList" value="${(house.rentTagList)!}"/>
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
						</#if>
									            
		            </tbody>	
				</table>
			</div>
			
			<h3>室内图片</h3>
			<div id="J_picsHouse" class="picCategory"></div>
			<h3>户型图片</h3>
			<div id="J_picsRoom" class="picCategory"></div>
			<h3>小区图片</h3>
			<div id="J_picsCommunity" class="picCategory"></div>


			<#if type?exists && !(type == 1 || type == -1)>
				</br>
				</br>
				</br>
				<div>
					<h3>客户端类型</h3>
					<#if house.clientType != 2 && house.clientType != 3>网页</#if>
					<#if house.clientType == 2>IOS</#if>
					<#if house.clientType == 3>Android</#if>
				</div>
				<div>
					<h3>房源审核</h3>
					<a href="/audit/approveNewHouseInDetailPage.controller?id=${(auditId)!}&houseId=${(house.id)!}">同意</a>&nbsp
					<a href="javascript:;" onclick="$('#reject-dialog').show()">拒绝</a>
					<div id="reject-dialog" style="display:none;">
						拒绝理由<input type="text" id="comments"/>
						<a href="javascript:;" onclick="doReject()">确定</a>
					</div>
					</br>
					</br>
					</br>
				</div>
				
				<style>
					#shade{display:none;width:100%;height:100%;background:#ccc;filter:alpha(opacity=80);-moz-opacity:0.8;opacity:0.8;position:fixed;z-index:2000;top:0;left:0}
					.hm-img-list li .img-thumbnail {
					height: 85%;
					width: 100%;
					}
				</style>
			</#if>
			

			<div id="shade"></div>
			
			
		</div>
		
		<script>
			function doReject(){
				window.location = "/audit/rejectNewHouseInDetailPage.controller?id=" + ${(auditId)!} + "&houseId=" + ${(house.id)!} + "&comments=" + $("#comments").val();
			}
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
				var houseId = ${(house.id)!};
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
					data: {picId: picId, houseId: houseId, sort: sort},
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
	
		<script type="text/tpl" id="J_picItemTpl">
			<ul class="hm-img-list">
				<%for(i=0;i<list.length; i++){ %>
				<li><img src="<%= list[i].cloudUrl%>" data-pic-id="<%=list[i].id%>" class="img-thumbnail" width="280" height="210">
				<%if (list[i].type==0||list[i].type==1){%><small class="img-status <%if (list[i].showStatus){%>img-passed<%}%>"><%if (list[i].showStatus){%>已认证<%}else{%>待审核<%}%></small><%}%>
					<#if type?exists && !(type == 1 || type == -1)>
					<a href="javascript:move('<%= list[i].id%>', 'up');">上移</a>
					<a href="javascript:move('<%= list[i].id%>', 'down');">下移</a>
					<a href="javascript:removePic('<%= list[i].id%>');" onclick="return confirm('是否将此图片删除?');" data-show-status="<%= list[i].showStatus%>" class="delPic">删除此图片</a>
					</#if>
					
					<div data-show-status="<%= list[i].showStatus%>" style="display:none;" class="toAudit" data-pic-id="<%= list[i].id%>">
						<!--
						<span style="color:red;">图片待审核</span>
						-->
						<#if type?exists && !(type == 1 || type == -1)>
						<a href="javascript:showPic('<%= list[i].id%>');">通过</a>
						<a href="javascript:removePic('<%= list[i].id%>');" onclick="return confirm('是否拒绝此图片?');">拒绝</a>
						</#if>
					</div>
					
				</li>
				<%} %>	
			</ul>
		</script>
		<script type="text/tpl" id="J_noPicTpl">
			<p class="alert-warning">暂无图片,请上传！ <!--<a href="/external/housePicConsole.controller?houseId=<%=houseId%>&picType=<%=picType%>" class="btn btn-sm btn-warning">上传图片</a>--> </p>
		</script>
		<script src="/webresources/js/lib/sea.js"></script>
		<script>
			__appConfig = {
				houseId: ${(house.id)!},
				sort: '${sort!}'
			};
			
			seajs.config({
		        base: "/webresources/js/",
		        alias: {
		            "jquery": "lib/jquery.js"
		        }
		    });
		
		    seajs.use(['jquery','header', 'detail']);
		</script>
	</body>
</html>