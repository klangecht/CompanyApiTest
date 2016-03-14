var app = angular.module('myApp', ['ngRoute']);
// configure our routes
app.config(function($routeProvider) {
    $routeProvider
    // route for the main page
        .when('/', {
            templateUrl: 'resources/pages/main.html',
            controller: 'mainController'
        })
        // route for the details page
        .when('/details', {
            templateUrl: 'resources/pages/companyDetails.html',
            controller: 'detailsController'
        })
        // route for the new Company page
        .when('/newCompany', {
            templateUrl: 'resources/pages/newCompany.html',
            controller: 'newCompanyController'
        })
        // route for the modify Company page
        .when('/modifyCompany', {
            templateUrl: 'resources/pages/modifyCompany.html',
            controller: 'modifyCompanyController'
        });
});
//store data and use in controllers
app.service('userService', function() {
    this.currentCompany = "";
    this.temp = "";
});
app.controller('mainController', function($scope, $http, $location, userService) {
    $http.get("http://company-api-test.cfapps.io/company").then(function(response) {
        $scope.companies = response.data;
        userService.allCompanies = $scope.companies;
    });
    $scope.showDetails = function(selectedCompany) {
        userService.currentCompany = selectedCompany;
        $location.path('/details');
    };
    $scope.createNewCompany = function() {
        $location.path('/newCompany');
    };
    $scope.modifyCompany = function(selectedCompany) {
        $location.path('/modifyCompany');
        userService.currentCompany = selectedCompany;
    };
});
app.controller('detailsController', function($scope, $http, $location, userService) {
    $scope.currentCompany = userService.currentCompany;
    $scope.currentOwners = "";
    var counter = 0;
    var temp = "";

    userService.currentCompany.owners = getStringFromArray(userService.currentCompany.owners);
    $scope.showOverview = function() {
        $location.path('/');
    };
});
app.controller('newCompanyController', function($scope, $http, $location, userService) {
    $scope.currentCompany = userService.currentCompany;
    $scope.showOverview = function() {
        $location.path('/');
    };
    $scope.submitCompany = function(company) {
        var config = {
            params: {
                company: company
            }
        };
        $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=utf-8";
        var payload = $.param(company);
        $http.post("http://company-api-test.cfapps.io/company/new", payload).success(function(data, status, headers, config) {
            $location.path('/');
        }).error(function(data, status, headers, config) {
            console.log("no success");
        });
    };
});
app.controller('modifyCompanyController', function($scope, $http, $location, userService) {
    $scope.currentCompany = userService.currentCompany;
    $scope.companyName = $scope.currentCompany.name;
    var stringOwners = "";
    var counter = 0;

    userService.currentCompany.owners = getStringFromArray(userService.currentCompany.owners);

    $scope.showOverview = function() {
        $location.path('/');
    };
    $scope.submitChanges = function(company) {
        var config = {
            params: {
                company: company
            }
        };
        $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=utf-8";
        //special API behaviour 
        company.newName = company.name;
        company.name = $scope.companyName;
        var payload = $.param(company);
        //update all except owners
        $http.post("http://company-api-test.cfapps.io/company/update", payload).success(function(data, status, headers, config) {
            $location.path('/');
        }).error(function(data, status, headers, config) {
            //error management
            console.log("no success");
        });
        //update owners with second api call
        payload = "companyName=" + company.name + "&owners=" + company.owners;
        
        $http.post("http://company-api-test.cfapps.io/company/owners/add", payload).success(function(data, status, headers, config) {
            $location.path('/');
            console.log("owners update success");
        }).error(function(data, status, headers, config) {
            //error management
            console.log("owners update no success");
        });
    };
});

//prepares owner values for display
function getStringFromArray(array) {
    var counter = 0;
    var stringOwners = "";
    angular.forEach(array, function(value, key) {
        counter++;
        stringOwners += value.name;
        if (counter < array.length) stringOwners += ", ";
    });
    return stringOwners;
}