app.controller('seckillController', function ($scope, $location, $interval, seckillService) {

    $scope.findList = function () {
        seckillService.findList().success(
            function (response) {
                $scope.list = response;
            }
        )
    }
    $scope.goItem = function (id) {
        location.href = 'seckill-item.html#?id=' + id;
    }

    $scope.findOne = function () {
        var id = $location.search()['id'];
        seckillService.findOne(id).success(
            function (response) {
                $scope.entity = response;
                var allsecond = Math.floor((new Date($scope.entity.endTime).getTime() - (new Date().getTime())) / 1000);

                //秒杀倒计时
                time = $interval(function () {
                    if (allsecond > 0) {
                        allsecond = allsecond - 1;
                        $scope.timeString = convertTimeString(allsecond);

                    } else {
                        $interval.cancel(time);
                        alert("秒杀结束")
                    }
                }, 1000);
            }
        )
    }
    convertTimeString = function (allSecond) {
        var days = Math.floor(allSecond / (60 * 60 * 24));
        var hours= Math.floor( (allSecond-days*60*60*24)/(60*60) );//小时数
        var minutes= Math.floor(  (allSecond -days*60*60*24 - hours*60*60)/60    );//分钟数
        var seconds= allSecond -days*60*60*24 - hours*60*60 -minutes*60; //秒数
        var timeString="";
        if (days > 0) {
            timeString = days + "天 ";
        }
        //alert(hours)
        return timeString + hours + ":" + minutes + ":" + seconds;
    }
    $scope.go = function (id) {
        var url = 'seckill-item.html#?id=' + id;

        // url =encodeURI(url);
        // alert(url);
        location.href = url;
    }
    //提交订单
    $scope.submitOrder=function(){
        seckillService.submitOrder($scope.entity.id).success(
            function(response){
                if(response.success){
                    alert("下单成功，请在1分钟内完成支付");
                    location.href="pay.html";
                }else{
                    alert(response.message);
                }
            }
        );
    }

});