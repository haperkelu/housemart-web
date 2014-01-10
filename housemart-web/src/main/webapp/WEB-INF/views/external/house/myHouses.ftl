<link href="/webresources/css/list.css" rel="stylesheet">
<form action="/external/myHouseList.controller" method="get" id="J_listForm">
    <div class="container hm-container">
        <ul class="nav nav-tabs">
            <li class="${((tabIndex!1) == 1) ? string('active', '')}">
                <a href="/external/myHouseList.controller?tabIndex=1">上架房源(${onboardCount})</a>
            </li>
            <li class="${((tabIndex!1) == 2) ? string('active', '')}">
                <a href="/external/myHouseList.controller?tabIndex=2">审核中(${auditCount})</a>
            </li>
            <li class="${((tabIndex!1) == 3) ? string('active', '')}">
                <a href="/external/myHouseList.controller?tabIndex=3">拒绝(${rejectCount})</a>
            </li>
            <li class="${((tabIndex!1) == 4) ? string('active', '')}">
                <a href="/external/myHouseList.controller?tabIndex=4">下架房源(${offboardCount})</a>
            </li>
            <li class="${((tabIndex!1) == 5) ? string('active', '')}">
                <a href="/external/myHouseList.controller?tabIndex=5">草稿(${notRequestAuditCount})</a>
            </li>
        </ul>
        <div class="hm-wrap hm-no-top">
            <div class="hm-filter" id="J_filterWrap">
                <input name="tabIndex" type="hidden" value="${(tabIndex!1)}" />
                <label class="radio">
                    <input type="radio" name='saleRent' value="0" ${(saleRent == "0") ? string('checked="checked"', '')}/> 全部
                </label>
                <label class="radio">
                    <input type="radio" name='saleRent' value="1" ${(saleRent == "1") ? string('checked="checked"', '')}/> 租
                </label>
                <label class="radio">
                    <input type="radio" name='saleRent' value="2" ${(saleRent == "2") ? string('checked="checked"', '')}/> 售
                </label>
                <label class="select">
                    <select class="form-control" name="residenceId" value="${residenceId}">
                        <option value="0">所有小区</option>
                        <#if accountResidences??>
                            <#list accountResidences as accountResidence>
                                <#if ((residenceId?number) == accountResidence.residenceID) >
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
                </label>
            </div>
            <div class="btn-group hm-action-btn">
                <a href="/houseEdit.controller?viewType=sale" target="_blank" class="btn btn-primary btn-md">发布售房</a>
                <a href="/houseEdit.controller?viewType=rent" target="_blank" class="btn btn-primary btn-md">发布租房</a>
            </div>
            <div class="hm-room-list">
                <table class="table table-bordered table-striped" id="J_listWrap">
                    <colgroup>
                    <!--
                        <col class="col-md-1">
                        <col class="col-md-5">
                        <#if ((tabIndex!1) == 3) >
                        <col class="col-md-2">
                        </#if>
                        <#if ((tabIndex!1) == 1) >
                        <col class="col-md-2">
                        </#if>
                        <#if ((tabIndex!1) == 4) >
                            <col class="col-md-2">
                            <col class="col-md-2">
                        </#if>
                        <col class="col-md-2">
                    -->
                    	<col style="width:100px;">
			            <col>
			            <#if ((tabIndex!1) == 3) >
			                <col style="width:200px;">
			            </#if>
			            <col style="width:200px;text-align:center;">
			            <#if ((tabIndex!1) == 1) >
			                <col style="width:150px;text-align:center;">
			            </#if>
			            <#if ((tabIndex!1) == 4) >
			                <col style="width:130px;">
			                <col style="width:200px;">
			            </#if>
			            <col style="width:150px;text-align:center;">    
                    </colgroup>
                    <thead>
                    <tr>
                        <th>房源编号</th>
                        <th>房源基本信息</th>
                        <#if ((tabIndex!1) == 3) >
                        <th>拒绝理由</th>
                        </#if>
                        <th>房源管理</th>
                        <#if ((tabIndex!1) == 1) >
                        <th>审核时间</th>
                        </#if>
                        <#if ((tabIndex!1) == 4) >
                        <th>下架人</th>
                        <th>下架理由</th>
                        </#if>
                        <#if ((tabIndex!1) != 5) >
                        <th>申请时间</th>
                        </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <#if houseList??>
                    <#list houseList as house>
                    <tr>
                        <td>
                            ${(house.ID)!}
                        </td>
                        <td>
                            <div class="row">
                                <div class="col-md-3">
                                    <a href="${(house.picURL)!}" title="" class="hm-thumb">
                                        <img class="img-thumbnail" src="${(house.picURL)!}" alt=""/>
                                    </a>
                                </div>
                                <div class="col-md-9 hm-thumb-desc">
                                    <p>
                                    	<#if house.RoomType?exists>
                                        ${(house.RoomType/1000)?int}房${((house.RoomType%1000)/100)?int}厅${((house.RoomType%100)/10)?int}卫
                                        </#if>
                                        <#if ((house.rentRentStatus!0) == 1)>
                                        租金：${(house.rentPrice!0)}元/月
                                        </#if>
                                        <#if ((house.saleSaleStatus!0) == 1)>
                                            总价：${(house.salePrice!0)/10000}万元
                                        </#if>
                                    </p>
                                    <a href="/external/houseView.controller?houseId=${house.ID!}" target="_blank">
                                        ${(house.residenceName)!}
                                        <#if house.BuildingNo?exists && house.BuildingNo != "">${(house.BuildingNo)!}栋（号）</#if> <#if house.CellNo?exists && house.CellNo != "">${(house.CellNo)!}${(house.CellNo)!}单元（室）</#if>
                                    </a>
                                </div>
                            </div>
                        </td>
                        <#if ((tabIndex!1) == 3) >
                            <td>
                                ${(house.auditComments)!}
                            </td>
                        </#if>
                        <td class="center">
                            <#if ((tabIndex!1) == 1) >
                                <!--<a class="btn btn-default btn-sm" href="/houseEdit.controller?houseId=${(house.ID)!}" data-action='modify' target="_blank">修改</a>-->
                                <a class="btn btn-default btn-sm" href="javascript:void(0)" data-action='deactive' data-house-id="${(house.ID)!}">下架</a>
                                <#else>
                                <a class="btn btn-default btn-sm" href="/houseEdit.controller?houseId=${(house.ID)!}" target="_blank">修改</a>
                            </#if>
                            <#if ((tabIndex!1) != 1) >
                                <a class="btn btn-default btn-sm" href="javascript:void(0)" data-action='delete' data-house-id="${(house.ID)!}">删除</a>
                            </#if>

                            <#if ((tabIndex!1) > 2 && (tabIndex!1) != 5) >
                                <a class="btn btn-default btn-sm" href="javascript:void(0)" data-action='active' data-house-id="${(house.ID)!}">提交审核</a>
                            </#if>
                            
                            <#if ((tabIndex!1) ==5) >
                                <a class="btn btn-default btn-sm" href="javascript:void(0)" data-action='active' data-house-id="${(house.ID)!}">提交审核</a>
                            </#if>
                        </td>
                        <#if ((tabIndex!1) == 1) >
                            <td class="center">
                                ${(house.onboardTime?string("yyyy-MM-dd hh:mm"))!}
                            </td>
                        </#if>
                        <#if ((tabIndex!1) == 4) >
                            <td>${(house.auditor)!}</td>
                            <td>${(house.rentMemo)!}${(house.saleMemo)!}</td>
                        </#if>
                        <#if ((tabIndex!1) != 5) >
                        <td class="center">
                            ${(house.applyTime?string("yyyy-MM-dd hh:mm"))!}
                        </td>
                        </#if>
                    </tr>
                    </#list>
                    </#if>
                    </tbody>
                </table>
                
                <input type="hidden" name="page" value="${(param.page)!}" id="J_currentPage"/>               
                <ul class="pagination hm-list-page" id="J_pagebarWrap">
                </ul>
            </div>
        </div>
    </div>
</form>
<script src="/webresources/js/lib/sea.js"></script>
<script type="text/javascript">
	__appConfig = {
        totalPage: Math.ceil(${(param.totalCount)!0}/${(param.pageSize)!}),
        currentPage: ${(param.page)!}		
	};
    seajs.config({
        base: "/webresources/js/",
        alias: {
            "jquery": "lib/jquery.js"
        }
    });

    seajs.use(['lib/bootstrap', 'jquery', 'header', 'list']);
</script>