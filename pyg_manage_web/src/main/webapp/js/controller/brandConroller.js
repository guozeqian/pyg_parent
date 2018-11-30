//定义控制器
app.controller('brandController', function ($scope, $controller, brandService) {
    //继承 1 被继承的控制器名称
    $controller('baseController', {$scope: $scope});

    //获取品牌列表，查询出所有内容
    $scope.findAll = function () {
        brandService.findAll().success(function (response) {
            $scope.list = response;
        })
    }

    //分页查询
    $scope.findPage = function (page, rows) {
        brandService.findPage(page, rows).success(function
            (response) {

            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    }


    //保存
    $scope.save = function () {
        if ($scope.entity.firstChar.length > 1) {
            alert("首字母输入有误，保存失败！");
            return false;
        }
        var methodStr = "add.do";
        if ($scope.entity.id != null) {
            methodStr = "edit.do";
        }
        brandService.save(methodStr, $scope.entity).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();
                } else {
                    alert(response.message);
                }
            })
    }


    //删除
    $scope.del = function () {
        if ($scope.selectIds.length < 1) {
            alert("请先选中需要删除的行！");
            return;
        }
        brandService.del($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    alert(response.message);
                    $scope.reloadList();
                } else {
                    alert(response.message);
                }
            })
    }
    $scope.findOne = function (id) {
        brandService.findOne(id).success(function (response) {
            $scope.entity = response;

        })
    }

    $scope.searchEntity = {}
    //查询
    $scope.search = function (page, rows) {
        brandService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;
            })
    }


})