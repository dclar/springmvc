//angular.module("myAPP", ['cgBusy']);
// angular.module("myAPP", ['angular-clipboard']);

var myapp = angular.module("myAPP",[]);


// myapp.value('cgBusyDefaults', {
//     message: '',
//     backdrop: true,
//     templateUrl: '/static/common/css/angular-busy.html',
//     //delay: 300,
//     //minDuration: 700,
//     //wrapperClass: 'my-class my-class2'
// });

//myApp.config(['$compileProvider', function($compileProvider) { $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|fil‌​e|ftp|blob):|data:im‌​age\//); } ]);

var baseUrl = '${pageContext.request.contextPath}';


myapp.directive("fileModel", ["$parse", function ($parse) {
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind("change", function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                    // console.log( scope );
                })
            })
        }
    }
}]);




myapp.service("fileUpload", ["$http", function ($http) {

    this.uploadFileToUrl = function ($scope, file, uploadUrl) {
        var fd = new FormData();
        fd.append("file", file)
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {"Content-Type": undefined}
        }).then(function successCallback(response) {
            // this callback will be called asynchronouslyZ
            // when the response is available
            $scope.uploadVo = response.data;


        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.

        });
    }
}]);



myapp.controller("myUploadController", ["$scope", "fileUpload", function ($scope, fileUpload) {
    $scope.sendFile = function () {
        var url = "../upload/excel",
            file = $scope.fileToUpload;
        if (!file) return;
        fileUpload.uploadFileToUrl($scope, file, url);
    }

    // $scope.supported = false;
    //
    // $scope.textToCopy = 'I can copy by clicking!';
    //
    // $scope.success = function () {
    //     console.log('Copied!');
    // };
    //
    // $scope.fail = function (err) {
    //     console.error('Error!', err);
    // };




}]);





myapp.controller("myController", function ($scope, $http) {

    // //$scope.delay = 1000;
    // $scope.minDuration = 0;
    // //$scope.message = 'Please Wait...';
    // $scope.backdrop = true;
    // $scope.promise = null;
    //
    // $scope.demo = function () {
    //
    //     $scope.promise = $http.get('http://httpbin.org/delay/3');
    //
    // };


    $scope.initUrl = function () {


        $scope.loading = true;

        //alert(document.cookie);
        var sentUrl = "../download/excel";

        var sendModels = $scope.downloadVo;

        $http({
            method: 'GET'
            , url: sentUrl
            , params: sendModels

        }).then(function successCallback(response) {
            // this callback will be called asynchronouslyZ
            // when the response is available



            $scope.loading = false;
            $scope.downloadVo = response.data;

            window.open(response.data.filePath_);

        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.

            alert(response);
            alert("failed");
        });


    };

    $scope.init = function () {


        $scope.loading = true;

        //alert(document.cookie);
        var sentUrl = "../download/excel/init";

        var sendModels = $scope.downloadVo;

        $http({
            method: 'GET'
            , url: sentUrl
            , params: sendModels

        }).then(function successCallback(response) {
            // this callback will be called asynchronouslyZ
            // when the response is available

            //alert("over");
            $scope.loading = false;
            //alert(response.data.cookieValue);
            $scope.downloadVo = response.data;


        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.

            $scope.loading = false;
            alert(response);
            alert("failed");
        });


    };


    $scope.SendData = function () {


        var sentUrl = "../download/excel/check";

        var sendModels = $scope.testVo;
        /**
         * Delete the key which has no data accordingly
         * Params of Angular $http receive the object instead of json, and
         * acvitivi-rest API can not check null data, so we should delete
         * the unnecessary key whose val is null.
         *
         * */
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // if (typeof $scope.inputModels !== "undefined") {
        //
        //     inputModels = $scope.inputModels;
        //     Object.keys(inputModels).forEach(function (key) {
        //         if (!inputModels[key]) {
        //             // delete inputModels[key];
        //         } else {
        //
        //             sendModels[key] = inputModels[key];
        //             if (key.endsWith("Like")) {
        //                 sendModels[key] = sendModels[key] + "%";
        //             }
        //         }
        //     });
        // }


        $http({
            method: 'POST'
            , url: sentUrl
            , params: sendModels

        }).then(function successCallback(response) {
            // this callback will be called asynchronouslyZ
            // when the response is available

            // Json response data sample:

            //$scope.models = response.data.cookie;
            // alert(response.data.status);
            alert(response.data.statusValue);


        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            // $scope.data = response.data || 'Request failed';
            // $scope.status = response.status;
            //
            // alert($scope.data);
            // alert($scope.status);
            alert(response);
            alert("failed");
        });

    };

    $scope.getFile = function () {

        $scope.loading = true;

        //alert(document.cookie);
        var sentUrl = "../download/excel";

        var sendModels = $scope.downloadVo;

        $http({
            method: 'GET'
            , url: sentUrl
            , params: sendModels

        }).then(function successCallback(response) {
            // this callback will be called asynchronouslyZ
            // when the response is available

            //alert(response.data.filePath_);

            $scope.loading = false;
            $scope.downloadVo = response.data;
            alert(response.data.filePath_);
            window.open(response.data.filePath_);

        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.

            alert(response);
            alert("failed");
        });

    };


    $scope.testLogin = function () {

        //$scope.loading = true;

        //alert(document.cookie);
        var sentUrl = "http://10.0.47.66/users/sign_in?utf8=%E2%9C%93&authenticity_token=7AnLkTnoW59iUVHsUg%2Fkvu2Nuhq%2F3qiowzRn8fIQIHlbaGgAhAye3KK1%2BGP5wmnuE6j3L%2FjsBtRI6%2BAuyGgkvQ%3D%3D&user%5Blogin%5D=darcula&user%5Bpassword%5D=13579246810&user%5Bremember_me%5D=0"


        var sendModels = {};
        $http({
            method: 'POST'
            , url: sentUrl
            //, params: sendModels

        }).then(function successCallback(response) {
            // this callback will be called asynchronouslyZ
            // when the response is available

            //alert(response.data.filePath_);

            //$scope.loading = false;
            //window.open(response.data.filePath_);

        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.

            alert(response);
            alert("failed");
        });


    };

});