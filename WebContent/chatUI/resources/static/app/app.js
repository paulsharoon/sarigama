var app =angular.module('sariGaMa',["ui.router"]);

 app.constant('protocal','http');
 app.constant('protocalport','localhost:8090');
//app.constant('urlRoutePrefix',contextPath);
app.config(['$stateProvider', '$urlRouterProvider', 
    function($stateProvider, $urlRouterProvider) {

$urlRouterProvider.otherwise('/demoPage');

$stateProvider
	.state('demoPage', {
		cache:false,
		url: '/demoPage',
		templateUrl:'chatUI/resources/static/pages/demo/demo.html',
		controller:'demoCtrl'
	})


}]);

