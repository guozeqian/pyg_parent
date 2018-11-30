app.service('brandService', function ($http) {
    //查询品牌列表
    this.findAll = function () {
        return $http.get('../brand/findAll.do');
    }
    //分页查询
    this.findPage = function (page, rows) {
        return $http.get('../brand/findPage.do?&page=' + page + '&rows=' + rows);
    }
    //保存
    this.save = function (methodStr, entity) {
        return $http.post("../brand/" + methodStr, entity);
    }
    //删除
    this.del = function (selectIds) {
        return $http.get("../brand/del.do?ids=" + selectIds);
    }
    //根据id查询一个
    this.findOne = function (id) {
        return $http.get("/brand/findOne.do?id=" + id);
    }
    //查询
    this.search = function (page, rows, searchEntity) {
        return $http.post("../brand/search.do?page=" + page + "&rows=" +
            rows, searchEntity);
    }
    //品牌下拉列表
    this.selectOptionList=function () {
        return $http.get('../brand/selectOptionList.do');
    }

})