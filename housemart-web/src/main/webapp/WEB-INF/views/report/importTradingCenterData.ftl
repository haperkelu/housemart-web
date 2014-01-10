<title>数据导入</title>
<body>
<h1>数据导入</h1>
<h2>${(message)!}</h2>
<h2>总共${(total)!}条数据，插入${(affectedCount)!}条</h2>

				<form id="uploadForm" action="/importTradingCenterDataSubmit.controller" name="uploadForm" method="POST" enctype="multipart/form-data">  
					<input name="file" type="file"/>  
					<input type="submit" value="submit"/>
				</form>

</body>