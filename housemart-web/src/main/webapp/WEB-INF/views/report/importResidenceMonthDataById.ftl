<title>数据导入</title>
<body>
<h1>数据导入</h1>
<h2>${(message)!}</h2>
<h2>总共${(total)!}条数据，插入${(affectedCount)!}条</h2>

				<form id="uploadForm" action="/importResidenceMonthDataByIdSubmit.controller" name="uploadForm" method="POST" enctype="multipart/form-data">  
					选择要导入的数据：
					<select name="colName" value="全部">
						<option value="全部">全部</option>
						<option value="年涨幅">年涨幅</option>
						<option value="年换手百分比">年换手百分比</option>
						<option value="年租金回报">年租金回报</option>
						<option value="小区均价">小区均价</option>
						<option value="最低租金">最低租金</option>
						<option value="最高租金">最高租金</option>
					</select>
					年份：
					<select name="year" value="${(year)!}">
						<option value="2013" <#if year?? && year == 2013>selected= "selected"</#if>>2013</option>
						<option value="2014" <#if year?? && year == 2014>selected= "selected"</#if>>2014</option>
						<option value="2015" <#if year?? && year == 2015>selected= "selected"</#if>>2015</option>
						<option value="2016" <#if year?? && year == 2016>selected= "selected"</#if>>2016</option>
						<option value="2017" <#if year?? && year == 2017>selected= "selected"</#if>>2017</option>
					</select>
					月份：
					<select name="month" value="${(month)!}">
						<option value="1" <#if month?? && month == 1>selected= "selected"</#if>>1</option>
						<option value="2" <#if month?? && month == 2>selected= "selected"</#if>>2</option>
						<option value="3" <#if month?? && month == 3>selected= "selected"</#if>>3</option>
						<option value="4" <#if month?? && month == 4>selected= "selected"</#if>>4</option>
						<option value="5" <#if month?? && month == 5>selected= "selected"</#if>>5</option>
						<option value="6" <#if month?? && month == 6>selected= "selected"</#if>>6</option>
						<option value="7" <#if month?? && month == 7>selected= "selected"</#if>>7</option>
						<option value="8" <#if month?? && month == 8>selected= "selected"</#if>>8</option>
						<option value="9" <#if month?? && month == 9>selected= "selected"</#if>>9</option>
						<option value="10" <#if month?? && month == 10>selected= "selected"</#if>>10</option>
						<option value="11" <#if month?? && month == 11>selected= "selected"</#if>>11</option>
						<option value="12" <#if month?? && month == 12>selected= "selected"</#if>>12</option>
					</select>
					
					<input name="file" type="file"/>  
					<input type="submit" value="submit"/>
				</form>

</body>