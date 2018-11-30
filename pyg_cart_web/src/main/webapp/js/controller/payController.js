//控制层
app.controller('payController', function ($scope, payService) {

    $scope.getNative = function () {
        payService.getNative().success(
            function (response) {
                $scope.money = (response.total_fee / 100).toFixed(2);
                $scope.out_trade_no = response.out_trade_no;
                //二维码
                var qr = new QRious({
                    element: document.getElementById("qrious"),
                    size: 250,
                    level: 'H',
                    value: response.code_url
                });
                queryPayStatus(response.out_trade_no);
            }
        );
    }
    queryPayStatus = function (out_trade_no) {
        payService.queryPayStatus(out_trade_no).success(
            function (response) {
                if (response.success) {
                    $scope.money = response.money;
                    location.href = "paysuccess.html#?money=" + $scope.money;
                } else {
                    alert(response.message);
                    location.href = "payfail.html"
                }
            }
        )
    }
    $scope.getMoney = function () {
        return $location.search()['money'];
    }
});