app.controller('demoCtrl',function($scope,$http,demoSer){
$scope.demo="sariGaMa";
console.log($scope.demo);


$scope.loginCall=function (Uname,passWord){
	
	$scope.demoParam={
			"email":Uname,
			"passKey":passWord,
	}
	demoSer.getdemoResp($scope.demoParam,function (resp){
		
		
		
		
		
	})
	}


});