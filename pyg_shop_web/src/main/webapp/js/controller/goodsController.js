//控制层
app.controller('goodsController', function ($scope, $controller, $location, goodsService, uploadService, itemCatService, typeTemplateService) {

        $controller('baseController', {$scope: $scope});//继承


        $scope.selectItemCatList = function (parentId) {
            itemCatService.findByParentId(parentId).success(function (response) {
                if (parentId == 0) {
                    $scope.itemCat1List = response;
                }
                else if (parentId == 1) {
                    $scope.itemCat2List = response;
                }
                else if (parentId == 2) {
                    $scope.itemCat3List = response;
                }
            })
        }

        $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
            if (newValue == undefined) {
                return;
            }
            itemCatService.findByParentId(newValue).success(function (response) {
                $scope.itemCat2List = response;
                $scope.itemCat3List = {};
            })
        })
        $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {
            if (newValue == undefined) {
                return;
            }
            itemCatService.findByParentId(newValue).success(function (response) {
                $scope.itemCat3List = response;
            })
        })
        $scope.$watch('entity.goods.category3Id', function (newValue, oldValue) {
            if (newValue == undefined) {
                return;
            }
            itemCatService.findOne(newValue).success(function (response) {
                //alert(response);
                $scope.entity.goods.typeTemplateId = response.typeId; //更新模板ID

            });

        })

        //监控模板id，如果发生变化，查询模板对象，模板对象中有品牌和规格的数据
        $scope.$watch('entity.goods.typeTemplateId', function (newValue, oldValue) {
            if (newValue == undefined) {
                return;
            }
            //alert(newValue);
            //可以通过findOne去找品牌列表
            typeTemplateService.findOne(newValue).success(
                function (response) {//模板对象
                    $scope.typeTemplate = response;
                    $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);

                    // $scope.brandList = JSON.parse(response.brandIds);//[{"id":29,"text":"耐克"},{"id":31,"text":"阿迪"},{"id":32,"text":"彪马"},{"id":4,"text":"小米"},{"id":34,"text":"李宁"},{"id":33,"text":"戴尔"},{"id":35,"text":"特步"}]
                });
            typeTemplateService.findSpecList(newValue).success(
                function (response) {
                    $scope.specList = response;
                }
            );
        });

        //创建sku列表
        $scope.createItemList = function () {
            $scope.entity.itemList = [{
                spec: {},
                price: 0,
                num: 9999,
                status: '1',
                isDefault: '0'
            }];
            var items = $scope.entity.goodsDesc.specificationItems;
            for (var i = 0; i < items.length; i++) {
                $scope.entity.itemList = addColumn($scope.entity.itemList, items[i].attributeName, items[i].attributeValue);
            }
        }

        addColumn = function (list, columnName, columnValues) {
            // alert(list.length);
            var newList = [];
            for (var i = 0; i < list.length; i++) {
                var oldRow = list[i];
                for (var j = 0; j < columnValues.length; j++) {
                    var newRow = JSON.parse(JSON.stringify(oldRow));//深刻隆
                    newRow.spec[columnName] = columnValues[j];
                    newList.push(newRow);
                }
            }
            return newList;
        }

        //判断要勾选的规格名称在勾选结果中是否存在，如果存在添加元素，如果不存在添加对象
        //list:$scope.entity.goodsDesc.specificationItems
        //name:规格名称 类似于网络制式
        selectObject = function (list, key, name) {
            if (list == null) {
                return null;
            }
            for (var i = 0; i < list.length; i++) {
                if (list[i][key] == name) {//说明要勾选的规格名称，在勾选结果中已经存在
                    return list[i];
                }
            }
            return null;//该规格没有被勾选过
        }
        $scope.entity = {
            goods: {},
            goodsDesc: {itemImages: [], specificationItems: []},
            itemList: []
        };
//记录勾选结果，该如何添加，name规则名称，value选项名称
        $scope.updateSpecAttribute = function ($event, name, value) {
            var specItems = $scope.entity.goodsDesc.specificationItems;//勾选结果
            //alert(specItems);
            var object = selectObject(specItems, 'attributeName', name);
            if (object != null) {
                if ($event.target.checked) {
                    alert(object.attributeValue.indexOf(value));
                    if (object.attributeValue.indexOf(value) < 0) {
                        object.attributeValue.push(value);
                    }
                } else {
                    object.attributeValue.splice(object.attributeValue.indexOf(value), 1);
                    if (object.attributeValue.length == 0) {
                        specItems.splice(specItems.indexOf(object), 1);
                    }
                }
            } else {
                specItems.push({
                    "attributeName": name,
                    "attributeValue": [value]
                });
            }
        }


        $scope.uploadFile = function () {
            uploadService.uploadFile().success(function (response) {
                if (response.success) {
                    $scope.image_entity.url = response.message;
                } else {
                    alert(response.message);
                }
            }).error(function () {
                alert("上传发生错误")
            })
        }


        //读取列表数据绑定到表单中
        $scope.findAll = function () {
            goodsService.findAll().success(
                function (response) {
                    $scope.list = response;
                }
            );
        }

        //分页
        $scope.findPage = function (page, rows) {
            goodsService.findPage(page, rows).success(
                function (response) {
                    $scope.list = response.rows;
                    $scope.paginationConf.totalItems = response.total;//更新总记录数
                }
            );
        }

        //查询实体
        $scope.findOne = function () {
            var id = $location.search()['id'];
            if (id == null) {
                return;
            }
            goodsService.findOne(id).success(
                function (response) {
                    $scope.entity = response;
                    editor.html($scope.entity.goodsDesc.introduction);
                    $scope.entity.goodsDesc.itemImages = JSON.parse($scope.entity.goodsDesc.itemImages);
                    $scope.entity.goodsDesc.specificationItems = JSON.parse($scope.entity.goodsDesc.specificationItems);
                    for (var i = 0; i < $scope.entity.itemList.length; i++) {
                        $scope.entity.itemList[i].spec = JSON.parse($scope.entity.itemList[i].spec);
                    }
                }
            );
        }

        $scope.image_entity = {};
        $scope.add_image_entity = function () {
            //alert($scope.image_entity.color);
            $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
        }
        $scope.delete_Image = function (id) {
            $scope.entity.goodsDesc.itemImages.splice(id);
        }
        //保存
        $scope.save = function () {

            $scope.entity.goodsDesc.introduction = editor.html();
            var serviceObject;//服务层对象
            if ($scope.entity.goods.id != null) {//如果有ID
                serviceObject = goodsService.update($scope.entity); //修改
            } else {
                serviceObject = goodsService.add($scope.entity);//增加
            }
            serviceObject.success(
                function (response) {
                    if (response.success) {
                        location.href = "goods.html";
                        //重新查询
                        // $scope.reloadList();//重新加载
                    } else {
                        alert(response.message);
                    }
                }
            );
        }


        //批量删除
        $scope.dele = function () {
            //获取选中的复选框
            goodsService.dele($scope.selectIds).success(
                function (response) {
                    if (response.success) {
                        $scope.reloadList();//刷新列表
                    }
                }
            );
        }

        $scope.searchEntity = {};//定义搜索对象

        //搜索
        $scope.search = function (page, rows) {
            goodsService.search(page, rows, $scope.searchEntity).success(
                function (response) {
                    $scope.list = response.rows;
                    $scope.paginationConf.totalItems = response.total;//更新总记录数
                }
            );
        }
        $scope.status = ['未审核', '已审核', '审核未通过', '关闭'];//商品状态
        $scope.itemCatList = [];
        $scope.findItemCatList = function () {
            itemCatService.findAll().success(function (response) {
                // for (var i = 0; i < response.length; i++) {
                //     $scope.itemCatList[response[i].id] = response[i].name;
                // }
                for (var i = 0; i < response.length; i++) {
                    $scope.itemCatList[response[i].id] = response[i].name;
                }
            })
        }

        $scope.checkboxResult = function (Items, attributeName, attributeValue) {
            for (var i = 0; i < Items.length; i++) {
                if (Items[i].attributeName == attributeName) {
                    for (var j = 0; j < Items[i].attributeValue.length; j++) {
                        if (Items[i].attributeValue[j] == attributeValue) {
                            return true;
                        }
                    }
                }
            }
            false;
        }

    }
);
