app.controller("searchController", function ($scope,$location, searchService) {

    $scope.loadkeywords=function () {
        $scope.searchMap.key_words=  $location.search()['keywords'];
        $scope.search();
    }
    
    $scope.search = function () {
        searchService.search($scope.searchMap).success(
            function (response) {
                $scope.resultMap = response;
                bulildPageLabel();
            }
        )
    }


    $scope.searchMap = {
        'key_words': '',
        'category': '',
        'brand': '',
        'spec': {},
        'price': '',
        'pageNo': 1,
        'pageSize': 40,
        'sort': '',
        'sortField': ''
    };//搜索对象
    $scope.addSearchItem = function (key, value) {
        if (key == 'category' || key == 'brand' || key == 'price') {
            $scope.searchMap[key] = value;
        } else {
            $scope.searchMap.spec[key] = value;
        }
        $scope.search();//执行搜索
    }
    $scope.removeSearchItem = function (key) {
        if (key == 'category' || key == 'brand' || key == 'price') {
            $scope.searchMap[key] = '';
        } else {
            delete $scope.searchMap.spec[key];
        }
        $scope.search();//执行搜索
    }


    bulildPageLabel = function () {
        $scope.pageLabel = [];
        var maxPageNo = $scope.resultMap.totalPages;
        var firstPage = 1;
        var lastPage = maxPageNo;
        if ($scope.resultMap.totalPages > 5) {
            if ($scope.searchMap.pageNo <= 3) {
                lastPage = 5;
                $scope.lastDot = true;
            } else if ($scope.searchMap.pageNo >= lastPage - 2) {
                firstPage = maxPageNo - 4;
                $scope.startDot = true;
            } else {
                firstPage = $scope.searchMap.pageNo - 2;
                lastPage = $scope.searchMap.pageNo + 2;
                $scope.startDot = true;
                $scope.lastDot = true;
            }
        }
        for (var i = firstPage; i <= lastPage; i++) {
            $scope.pageLabel.push(i);
        }
        // console.log($scope.searchMap.pageNo);
        // console.log(firstPage);
        // console.log(lastPage);
        // console.log($scope.pageLabel);
    }
    $scope.isStartPage = function () {
        return $scope.searchMap.pageNo == 1;
    }
    $scope.isEndPage = function () {
        return $scope.searchMap.pageNo == $scoperesultMap.totalPages;
    }

    $scope.queryPage = function (page) {
        $scope.searchMap.pageNo = page;
        $scope.search();
    }
    $scope.querySort = function (sort, sortField) {
        $scope.searchMap.sort = sort;
        $scope.searchMap.sortField = sortField;
        $scope.search();
    }
    $scope.queryTime = function (sort, sortField) {

    }

    $scope.kewordsIsBrand = function () {
        for (var i = 0; i < $scope.resultMap.brandList.length; i++) {
            if ($scope.searchMap.key_words.indexOf($scope.resultMap.brandList[i].text) >= 0) {
                return true;
            }
        }
        return false;
    }


})