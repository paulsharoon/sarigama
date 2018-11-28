app.factory('demoSer',function($http,protocal,protocalport){
	
    var getdemoResp = function (demologinData,callback) {
        var req = {
            method: 'GET',
            url:protocal+"://"+protocalport+'/sarigama/api/authentication/register/',
            headers:{
                "content-type":"application/json"
            }
        }
        $http(req).then(function(data){  
        	callback(data);
        }, function(data){
            callback(data);
        });   
     };
     
    
      
     return{
    	 getdemoResp:getdemoResp,
    	 
          };
});
