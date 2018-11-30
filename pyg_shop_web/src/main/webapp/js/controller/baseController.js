app.controller('baseController', function ($scope) {

    //分页控件配置  onChange 上面4个属性发生变化的时候就会执行
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 0,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            $scope.select_all = false;
            $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
        }
    };

    //刷新页面
    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }

    //记录被选中待操作的id数组
    $scope.selectIds = [];
    $scope.updateSelection = function ($event, id) {
        if ($event.target.checked) {
            $scope.selectIds.push(id);
        } else {//取消勾选  1 元素在数组中的下标 2 移除的个数
            var index = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(index, 1);
            $scope.select_all = false;
        }
    }


    //全选
    $scope.selectAll = function ($event) {
        if ($event.target.checked) {
            $scope.selectIds = [];
            angular.forEach($scope.list, function (i, index) {
                $scope.selectIds.push(i.id);
                i.checked = true;
            })
        } else {
            $scope.selectIds = [];
            angular.forEach($scope.list, function (i, index) {
                i.checked = false;
            })
        }
    }

    $scope.jsonToString = function (jsonString, key) {
        var value = "";
        if (jsonString) {
            var json = JSON.parse(jsonString);
            if (json != null) {
                for (var i = 0; i < json.length; i++) {
                    if (i > 0) {
                        value += ",";
                    }
                    value += json[i][key];
                }
            }
        }
        return value;
    }


})