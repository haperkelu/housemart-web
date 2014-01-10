	<div style="width:900px;">
	<#if list?exists>
		<#list list as item> 
			<div style="width:300px;height:170px;float:left;margin:0 15px 0 0">
				<image src="${(item.cloudURL)!}" width="300" height="150"></image>
				<input class="pics" type="checkbox" id="pic_check_${(item.id)!}" _pId="${(item.id)!}" class="choosePic"/><label for="pic_check_${(item.id)!}">选中</label>
			</div>
		</#list>
	</#if>
	<div>
	<div style="display:none;" id="selectedPics">
		<input type="hidden" id="maxNumber" value="0">
	</div>

	<input id="residenceId" name="residenceId" type="hidden" value="${(residenceId)!}"/>
	<input id="houseId" name="houseId" type="hidden" value="${(houseId)!}"/>
	<input type="submit" value="关联图片"/>
	
	<script>
	$(".pics").click(function(){
		var picId = $(this).attr("_pId");
		if($(this).attr("checked")==true){
			var index = parseInt($(maxNumber).val()) + 1;
			$(maxNumber).val(index);
			$("#selectedPics").append("<input name='seletedPics' value='" + picId + "' id='pic_" + picId + "' type='hidden'>");
		}else{
			$("#pic_" + picId).remove();
		}
	});
	</script>