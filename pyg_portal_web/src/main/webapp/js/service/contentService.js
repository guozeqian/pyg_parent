//服务层
app.service('contentService',function($http){

    //查询某类广告列表
    this.findListByCategoryId=function (categoryId) {
        //localhost:9103/content/findListByCategoryId.do
        return $http.get('content/findListByCategoryId.do?categoryId='+categoryId);
    }

});
