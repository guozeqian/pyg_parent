app.controller('contentController', function ($scope, contentService) {

    $scope.categoryList = [];

    //查询某类广告列表
    $scope.findListByCategoryId = function (categoryId) {
        contentService.findListByCategoryId(categoryId).success(
            function (response) {//某类型的广告列表
                $scope.categoryList[categoryId] = response;
            }
        );
    }
    $scope.keywords = '';
    $scope.search = function () {
        location.href = "http://localhost:9007/search.html#?keywords=" + $scope.keywords;
    }
})