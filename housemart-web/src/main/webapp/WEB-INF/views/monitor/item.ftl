<title></title>
<head>
<script src="/webresources/js/third_party_js/angular.min.js"></script>
</head>
<body>
<style>
</style>
<div>request context data: </div>
<div ng-app="content_module" ng-controller="main_controller">
	{{content}}
</div>
<script>

	var app = angular.module('content_module',[]);
	app.controller("main_controller", function($scope){
		$scope.content = '${record.accessText}'
	});
</script>
</body>