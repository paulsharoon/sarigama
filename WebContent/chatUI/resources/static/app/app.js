var app =angular.module('sariGaMa,'['ui-router','ngsanitize']);


app.constant('urlRoutePrefix',contextPath);
app.config(['$stateProvider', '$urlRouterProvider', 
    function($stateProvider, $urlRouterProvider) {

//$urlRouterProvider.otherwise('/signin');

$stateProvider
	.state('demoPage', {
		cache:false,
		url: '/demopage',
		templateUrl:'pages/demo/demo.html',
		controller:'demoCtrl'
	})


}]);

