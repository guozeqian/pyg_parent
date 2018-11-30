//控制层
app.controller('cartController', function ($scope, $controller, cartService) {

    $scope.findCartList = function () {
        cartService.findCartList().success(
            function (response) {
                $scope.cartList = response;
                $scope.totalValue = cartService.sum($scope.cartList);
            }
        )
    }

    $scope.addGoodsToCartList = function (itemId, num) {
        cartService.addGoodsToCartList(itemId, num).success(
            function (response) {
                $scope.findCartList();
                //alert(response.message);
            }
        )
    }

    $scope.findAddressList = function () {
        cartService.findAddressList().success(
            function (response) {
                $scope.addressList = response;
                if ($scope.addressList != null) {
                    for (var i = 0; i < $scope.addressList.length; i++) {
                        if ($scope.addressList[i].isDefault == '1') {
                            $scope.selectedAddress = $scope.addressList[i];
                            break;
                        }
                    }
                }
            }
        )
    }
    $scope.selectAddress = function (address) {
        $scope.selectedAddress = address;
    }
    $scope.isSelectedAddress = function (address) {
        if ($scope.selectedAddress == address) {
            return true;
        }
        return false;
    }

    $scope.order = {paymentType: 1};
    $scope.selectByPayType = function (type) {
        $scope.order.paymentType = type;
    }
    $scope.submitOrder=function () {
        $scope.order.receiverAreaName=$scope.selectedAddress.address;
        $scope.order.receiverMobile=$scope.selectedAddress.mobile;
        $scope.order.receiver=$scope.selectedAddress.contact;
        cartService.submitOrder($scope.order).success(
            function (response) {
              if(response.success){
                  if($scope.order.paymentType=='1'){
                      location.href='pay.html';

                  }else {
                      location.href='paysuccess.html';
                  }
              }else {
                  alert(response.message);
              }
            }
        );
    }

});
