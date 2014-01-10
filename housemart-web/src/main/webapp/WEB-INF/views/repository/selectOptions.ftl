	<input type="hidden" name="houseId" value="${(houseId)!}"/>
	<input type="hidden" name="residenceId" value="${(residenceId)!}"/>
	<div>
		<div>
			房型：
			<a href="javascript:;" class="roomType <#if (!roomType.shi?? || roomType.shi == 0) && (!shiGE??)>on</#if>" _value="0">全部</a>
			<a href="javascript:;" class="roomType <#if roomType.shi?? && roomType.shi == 1>on</#if>" _value="1">一室</a>
			<a href="javascript:;" class="roomType <#if roomType.shi?? && roomType.shi == 2>on</#if>" _value="2">两室</a>
			<a href="javascript:;" class="roomType <#if roomType.shi?? && roomType.shi == 3>on</#if>" _value="3">三室</a>
			<a href="javascript:;" class="roomType <#if shiGE?? && shiGE == 4>on</#if>" _value="4">四室及以上</a>
			<input type="hidden" name="shi" id="shi" <#if roomType.shi??>value="${roomType.shi}"</#if>/>
			<input type="hidden" name="shiGE" id="shiGE" <#if shiGE??>value="${shiGE}"</#if>/>
		</div>
		<div>
			朝向：
			<a href="javascript:;" class="direction <#if (!direction.east?? || direction.east != '1') && (!direction.south?? || direction.south != '1')>on</#if>" _value="0">全部</a>
			<a href="javascript:;" class="direction <#if direction.east?? && direction.east == '1'>on</#if>" _value="1">东西</a>
			<a href="javascript:;" class="direction <#if direction.south?? && direction.south == '1'>on</#if>" _value="2">南北</a>
			<input type="hidden" name="east" id="east" <#if direction.east?? && direction.east == '1'>value="1"</#if>/>
			<input type="hidden" name="south" id="south" <#if direction.south??  && direction.south == '1'>value="1"</#if>/>
			<input type="hidden" name="west" id="west" <#if direction.west??  && direction.west == '1'>value="1"</#if> />
			<input type="hidden" name="north" id="north" <#if direction.north??  && direction.north == '1'>value="1"</#if>/>
		</div>
		<div>
			面积：
			<a href="javascript:;" class="area <#if !area?? || area == 0>on</#if>" _value="0">全部</a>
			<a href="javascript:;" class="area <#if area?? && area == 1>on</#if>" _value="1">50以下</a>
			<a href="javascript:;" class="area <#if area?? && area == 2>on</#if>" _value="2">50-70</a>
			<a href="javascript:;" class="area <#if area?? && area == 3>on</#if>" _value="3">70-90</a>
			<a href="javascript:;" class="area <#if area?? && area == 4>on</#if>" _value="4">90-120</a>
			<a href="javascript:;" class="area <#if area?? && area == 5>on</#if>" _value="5">120-150</a>
			<a href="javascript:;" class="area <#if area?? && area == 6>on</#if>" _value="6">150-200</a>
			<a href="javascript:;" class="area <#if area?? && area == 7>on</#if>" _value="7">200以上</a>
			<input type="hidden" name="area" id="area" <#if area??>value="${area}"</#if>/>
		</div>
	</div>
	
	<style>
			.on{
				background:#ccc;
			}
		</style>
		<script>
			$(".roomType").click(function(){
				$(".roomType").removeClass("on");
				$(this).addClass("on");
				var shi = parseInt($(this).attr("_value"));
				if(shi== 0){
					$("#shi").removeAttr('value');
					$("#shiGE").removeAttr('value');
				}else if(shi <= 3){
					$("#shi").val($(this).attr("_value"));
					$("#shiGE").removeAttr('value');
				}else{
					$("#shiGE").val($(this).attr("_value"));
					$("#shi").removeAttr('value');
				}
				
				$("#housePicList").submit();
			});
		
			$(".direction").click(function(){
				$(".direction").removeClass("on");
				$(this).addClass("on");
				var direc = $(this).attr("_value");
				if(direc == 0){
					$("#east").removeAttr('value');
					$("#south").removeAttr('value');
					$("#west").removeAttr('value');
					$("#north").removeAttr('value');
				}else if(direc == 1){
					$("#east").val('1');
					$("#south").removeAttr('value');
					$("#west").val('1');
					$("#north").removeAttr('value');
				}else{
					$("#east").removeAttr('value');
					$("#south").val('1');
					$("#west").removeAttr('value');
					$("#north").val('1');
				}
				
				$("#housePicList").submit();
			});
			
			$(".area").click(function(){
				$(".area").removeClass("on");
				$(this).addClass("on");
				var area = $(this).attr("_value");
				if(area == 0){
					$("#area").removeAttr('value');
				}else{
					$("#area").val(area);
				}
				
				$("#housePicList").submit();
			});
		</script>