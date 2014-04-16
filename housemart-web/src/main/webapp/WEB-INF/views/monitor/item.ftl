<title></title>
<body>
<style>
</style>
<div>request context data: </div>
<div id="content" ng-app="content_module" ng-controller="main_controller">
	{{content}}
</div>

<script src="/webresources/js/third_party_js/angular.min.js"></script>
<script>

	var app = angular.module('content_module',[]);
	app.controller("main_controller", function($scope){
		$scope.content = ${record.accessText}
	});
</script>
</body>