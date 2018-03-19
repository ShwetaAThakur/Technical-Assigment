'use strict';

angular.module('myApp.view1', ['ngRoute','ngCookies'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {
    templateUrl: 'view1/view1.html',
    controller: 'homeCtrl'
  });

 

}])

.controller('homeCtrl', ['$scope', '$http', '$cookies', function ($scope, $http, $cookies) {
	
	$scope.content = "{}";
	$scope.searchresults = {}
  $scope.selectedStockDetails = {}
	$scope.isErrorPresent = false;

	$scope.getAllStocks = function() {
    var requestedUrl = "http://localhost:8081/api/stocks";
     $http({
                   method: "get",
                   url: requestedUrl,
               }).then(
         function success(response) {
          $scope.searchresults = response.data;
            
        },function error (response) {
            isErrorPresent = true;
        });
	$('[data-toggle="tooltip"]').tooltip();
}

$scope.selectStock = function (selectedStockDetails) {
  var linkArray = selectedStockDetails.links;
  var requestUrlForRead;
  for(var index in linkArray) {
    if(linkArray[index].rel == "self"){
      requestUrlForRead = linkArray[index].href;
      break;
     }
   }
   if(requestUrlForRead != null) {
      $http({
        method: "get",
        url: requestUrlForRead,
        }).then(
         function success(response) {
          $scope.selectedStockDetails = response.data.stock;
           console.log($scope.selectedStockDetails);
        },function error (response) {
            isErrorPresent = true;
        });
         $("#singleStockDetailsModel").modal("show");
    }
           
}
	
	

}])
