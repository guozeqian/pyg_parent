<!-- 头部栏位 -->
<!--页面顶部-->
<div id="nav-bottom">
    <!--顶部-->
    <div class="nav-top">
        <div class="top">
            <div class="py-container">
                <div class="shortcut">
                    <ul class="fl">
                        <li class="f-item">品优购欢迎您！</li>
                        <li class="f-item">请<a href="login.html"
                                               target="_blank">登录</a>　<span><a
                                href="register.html"
                                target="_blank">免费注册</a></span></li>
                    </ul>
                    <ul class="fr">
                        <li class="f-item">我的订单</li>
                        <li class="f-item space"></li>
                        <li class="f-item"><a href="home.html" target="_blank">我的品优购</a>
                        </li>
                        <li class="f-item space"></li>
                        <li class="f-item">品优购会员</li>
                        <li class="f-item space"></li>
                        <li class="f-item">企业采购</li>
                        <li class="f-item space"></li>
                        <li class="f-item">关注品优购</li>
                        <li class="f-item space"></li>
                        <li class="f-item" id="service">
                            <span>客户服务</span>
                            <ul class="service">
                                <li><a href="cooperation.html" target="_blank">合作招商</a>
                                </li>
                                <li><a href="shoplogin.html" target="_blank">商家后台</a>
                                </li>
                                <li><a href="cooperation.html" target="_blank">合作招商</a>
                                </li>
                                <li><a href="#">商家后台</a></li>
                            </ul>
                        </li>
                        <li class="f-item space"></li>
                        <li class="f-item">网站导航</li>
                    </ul>
                </div>
            </div>
        </div>

        <!--头部-->
        <div class="header">
            <div class="py-container">
                <div class="yui3-g Logo">
                    <div class="yui3-u Left logoArea">
                        <a class="logo-bd" title="品优购" href="JD-index.html"
                           target="_blank"></a>
                    </div>
                    <div class="yui3-u Center searchArea">
                        <div class="search">
                            <form action="" class="sui-form form-inline">
                                <!--searchAutoComplete-->
                                <div class="input-append">
                                    <input type="text" id="autocomplete"
                                           type="text"
                                           class="input-error input-xxlarge"/>
                                    <button class="sui-btn btn-xlarge btn-danger"
                                            type="button">搜索
                                    </button>
                                </div>
                            </form>
                        </div>
                        <div class="hotwords">
                            <ul>
                                <li class="f-item">品优购首发</li>
                                <li class="f-item">亿元优惠</li>
                                <li class="f-item">9.9元团购</li>
                                <li class="f-item">每满99减30</li>
                                <li class="f-item">亿元优惠</li>
                                <li class="f-item">9.9元团购</li>
                                <li class="f-item">办公用品</li>

                            </ul>
                        </div>
                    </div>
                    <div class="yui3-u Right shopArea">
                        <div class="fr shopcar">
                            <div class="show-shopcar" id="shopcar">
                                <span class="car"></span>
                                <a class="sui-btn btn-default btn-xlarge"
                                   href="cart.html" target="_blank">
                                    <span>我的购物车</span>
                                    <i class="shopnum">0</i>
                                </a>
                                <div class="clearfix shopcarlist"
                                     id="shopcarlist" style="display:none">
                                    <p>"啊哦，你的购物车还没有商品哦！"</p>
                                    <p>"啊哦，你的购物车还没有商品哦！"</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="yui3-g NavList">
                    <div class="yui3-u Left all-sort">
                        <h4>全部商品分类</h4>
                    </div>
                    <div class="yui3-u Center navArea">
                        <ul class="nav">
                            <li class="f-item">服装城</li>
                            <li class="f-item">美妆馆</li>
                            <li class="f-item">品优超市</li>
                            <li class="f-item">全球购</li>
                            <li class="f-item">闪购</li>
                            <li class="f-item">团购</li>
                            <li class="f-item">有趣</li>
                            <li class="f-item"><a href="seckill-index.html"
                                                  target="_blank">秒杀</a></li>
                        </ul>
                    </div>
                    <div class="yui3-u Right"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="py-container">
    <div id="item">
        <div class="crumb-wrap">
            <ul class="sui-breadcrumb">
                <li>
                    <a href="#">${itemCat1}</a>
                </li>
                <li>
                    <a href="#">${itemCat1}</a>
                </li>
                <li>
                    <a href="#">${itemCat1}</a>
                </li>
                <li class="active">${goods.goodsName}</li>
            </ul>
        </div>

        <!--product-info-->
        <div class="product-info">
            <div class="fl preview-wrap">
                <!--放大镜效果-->
                <div class="zoom">
                    <!--默认第一个预览-->
                    <div id="preview" class="spec-preview">
                        <span class="jqzoom">
                        <#if (imageList?size>0)>
                            <img jqimg="${imageList[0].url}"
                                 src="${imageList[0].url}"/>
                        </#if>
                        </span>
                    </div>
                    <!--下方的缩略图-->
                    <div class="spec-scroll">
                        <a class="prev">&lt;</a>
                        <!--左右按钮-->
                        <div class="items">
                            <ul>
                            <#list imageList as item>
                                <li><img src="${item.url}" bimg="img/_/b3.png"
                                         onmousemove="preview(this)"/></li>
                            </#list>
                            </ul>
                        </div>
                        <a class="next">&gt;</a>
                    </div>
                </div>
            </div>
            <div class="fr itemInfo-wrap">
                <div class="sku-name">
                    <h4>{{sku.title}}</h4>
                </div>
                <div class="news">
                    <span>${goods.caption}</span></div>
                <div class="summary">
                    <div class="summary-wrap">
                        <div class="fl title">
                            <i>价　　格</i>
                        </div>
                        <div class="fl price">
                            <i>¥</i>
                            <em>{{sku.price}}</em>
                            <span>降价通知</span>
                        </div>
                        <div class="fr remark">
                            <i>累计评价</i><em>612188</em>
                        </div>
                    </div>
                    <div class="summary-wrap">
                        <div class="fl title">
                            <i>促　　销</i>
                        </div>
                        <div class="fl fix-width">
                            <i class="red-bg">加价购</i>
                            <em class="t-gray">满999.00另加20.00元，或满1999.00另加30.00元，或满2999.00另加40.00元，即可在购物车换
                                购热销商品</em>
                        </div>
                    </div>
                </div>
                <div class="support">
                    <div class="summary-wrap">
                        <div class="fl title">
                            <i>支　　持</i>
                        </div>
                        <div class="fl fix-width">
                            <em class="t-gray">以旧换新，闲置手机回收 4G套餐超值抢 礼品购</em>
                        </div>
                    </div>
                    <div class="summary-wrap">
                        <div class="fl title">
                            <i>配 送 至</i>
                        </div>
                        <div class="fl fix-width">
                            <em class="t-gray">满999.00另加20.00元，或满1999.00另加30.00元，或满2999.00另加40.00元，即可在购物车换购热销商品</em>
                        </div>
                    </div>
                </div>
                <div class="clearfix choose">
                    <div id="specification" class="summary-wrap clearfix">
                    <#list  specificationList as specification>
                        <dl>
                            <dt>
                                <div class="fl title">
                                    <i>${specification.attributeName}</i>
                                </div>
                            </dt>
                            <#list specification.attributeValue as value>

                                    <dd><a href="javascript:;"
                                           class="{{isSelected('${specification.attributeName}','${value}')?'selected':''}}"
                                           ng-click="selectSpecification('${specification.attributeName}','${value}')">${value}
                                        <span
                                            title="点击取消选择">&nbsp;</span>
                                    </a></dd>




                            </#list>
                        </dl>
                    </#list>


                    </div>


                    <div class="summary-wrap">
                        <div class="fl title">
                            <div class="control-group">
                                <div class="controls">
                                    <input autocomplete="off" type="text"
                                           value="{{num}}" minnum="1"
                                           class="itxt"/>
                                    <a href="javascript:void(0)" class="increment plus" ng-click="addNum(1)" >+</a>
                                    <a href="javascript:void(0)" class="increment mins" ng-click="addNum(-1)">-</a>
                                </div>
                            </div>
                        </div>
                        <div class="fl">
                            <ul class="btn-choose unstyled">
                                <li>
                                    <a href="cart.html" target="_blank"
                                       ng-click="addToCart()"
                                       class="sui-btn  btn-danger addshopcar">加入购物车</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--product-detail-->
        <div class="clearfix product-detail">
            <div class="fl aside">
                <ul class="sui-nav nav-tabs tab-wraped">
                    <li class="active">
                        <a href="#index" data-toggle="tab">
                            <span>相关分类</span>
                        </a>
                    </li>
                    <li>
                        <a href="#profile" data-toggle="tab">
                            <span>推荐品牌</span>
                        </a>
                    </li>
                </ul>
                <div class="tab-content tab-wraped">
                    <div id="index" class="tab-pane active">
                        <ul class="part-list unstyled">
                            <li>手机</li>
                            <li>手机壳</li>
                            <li>内存卡</li>
                            <li>Iphone配件</li>
                            <li>贴膜</li>
                            <li>手机耳机</li>
                            <li>移动电源</li>
                            <li>平板电脑</li>
                        </ul>
                        <ul class="goods-list unstyled">
                            <li>
                                <div class="list-wrap">
                                    <div class="p-img">
                                        <img src="img/_/part01.png"/>
                                    </div>
                                    <div class="attr">
                                        <em>Apple苹果iPhone 6s (A1699)</em>
                                    </div>
                                    <div class="price">
                                        <strong>
                                            <em>¥</em>
                                            <i>6088.00</i>
                                        </strong>
                                    </div>
                                    <div class="operate">
                                        <a href="javascript:void(0);"
                                           class="sui-btn btn-bordered">加入购物车</a>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="list-wrap">
                                    <div class="p-img">
                                        <img src="img/_/part02.png"/>
                                    </div>
                                    <div class="attr">
                                        <em>Apple苹果iPhone 6s (A1699)</em>
                                    </div>
                                    <div class="price">
                                        <strong>
                                            <em>¥</em>
                                            <i>6088.00</i>
                                        </strong>
                                    </div>
                                    <div class="operate">
                                        <a href="javascript:void(0);"
                                           class="sui-btn btn-bordered">加入购物车</a>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="list-wrap">
                                    <div class="p-img">
                                        <img src="img/_/part03.png"/>
                                    </div>
                                    <div class="attr">
                                        <em>Apple苹果iPhone 6s (A1699)</em>
                                    </div>
                                    <div class="price">
                                        <strong>
                                            <em>¥</em>
                                            <i>6088.00</i>
                                        </strong>
                                    </div>
                                    <div class="operate">
                                        <a href="javascript:void(0);"
                                           class="sui-btn btn-bordered">加入购物车</a>
                                    </div>
                                </div>
                                <div class="list-wrap">
                                    <div class="p-img">
                                        <img src="img/_/part02.png"/>
                                    </div>
                                    <div class="attr">
                                        <em>Apple苹果iPhone 6s (A1699)</em>
                                    </div>
                                    <div class="price">
                                        <strong>
                                            <em>¥</em>
                                            <i>6088.00</i>
                                        </strong>
                                    </div>
                                    <div class="operate">
                                        <a href="javascript:void(0);"
                                           class="sui-btn btn-bordered">加入购物车</a>
                                    </div>
                                </div>
                                <div class="list-wrap">
                                    <div class="p-img">
                                        <img src="img/_/part03.png"/>
                                    </div>
                                    <div class="attr">
                                        <em>Apple苹果iPhone 6s (A1699)</em>
                                    </div>
                                    <div class="price">
                                        <strong>
                                            <em>¥</em>
                                            <i>6088.00</i>
                                        </strong>
                                    </div>
                                    <div class="operate">
                                        <a href="javascript:void(0);"
                                           class="sui-btn btn-bordered">加入购物车</a>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div id="profile" class="tab-pane">
                        <p>推荐品牌</p>
                    </div>
                </div>
            </div>
            <div class="fr detail">
                <div class="clearfix fitting">
                    <h4 class="kt">选择搭配</h4>
                    <div class="good-suits">
                        <div class="fl master">
                            <div class="list-wrap">
                                <div class="p-img">
                                    <img src="img/_/l-m01.png"/>
                                </div>
                                <em>￥5299</em>
                                <i>+</i>
                            </div>
                        </div>
                        <div class="fl suits">
                            <ul class="suit-list">
                                <li class="">
                                    <div id="">
                                        <img src="img/_/dp01.png"/>
                                    </div>
                                    <i>Feless费勒斯VR</i>
                                    <label data-toggle="checkbox"
                                           class="checkbox-pretty">
                                        <input type="checkbox"><span>39</span>
                                    </label>
                                </li>
                                <li class="">
                                    <div id=""><img src="img/_/dp02.png"/></div>
                                    <i>Feless费勒斯VR</i>
                                    <label data-toggle="checkbox"
                                           class="checkbox-pretty">
                                        <input type="checkbox"><span>50</span>
                                    </label>
                                </li>
                                <li class="">
                                    <div id=""><img src="img/_/dp03.png"/></div>
                                    <i>Feless费勒斯VR</i>
                                    <label data-toggle="checkbox"
                                           class="checkbox-pretty">
                                        <input type="checkbox"><span>59</span>
                                    </label>
                                </li>
                                <li class="">
                                    <div id=""><img src="img/_/dp04.png"/></div>
                                    <i>Feless费勒斯VR</i>
                                    <label data-toggle="checkbox"
                                           class="checkbox-pretty">
                                        <input type="checkbox"><span>99</span>
                                    </label>
                                </li>
                            </ul>
                        </div>
                        <div class="fr result">
                            <div class="num">已选购0件商品</div>
                            <div class="price-tit"><strong>套餐价</strong></div>
                            <div class="price">￥5299</div>
                            <button class="sui-btn  btn-danger addshopcar">
                                加入购物车
                            </button>
                        </div>
                    </div>
                </div>
                <div class="tab-main intro">
                    <ul class="sui-nav nav-tabs tab-wraped">
                        <li class="active">
                            <a href="#one" data-toggle="tab">
                                <span>商品介绍</span>
                            </a>
                        </li>
                        <li>
                            <a href="#two" data-toggle="tab">
                                <span>规格与包装</span>
                            </a>
                        </li>
                        <li>
                            <a href="#three" data-toggle="tab">
                                <span>售后保障</span>
                            </a>
                        </li>
                        <li>
                            <a href="#four" data-toggle="tab">
                                <span>商品评价</span>
                            </a>
                        </li>
                        <li>
                            <a href="#five" data-toggle="tab">
                                <span>手机社区</span>
                            </a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                    <div class="tab-content tab-wraped">
                        <div id="one" class="tab-pane active">
                            <ul class="goods-intro unstyled">
                                <li>分辨率：1920*1080(FHD)</li>
                                <li>后置摄像头：1200万像素</li>
                                <li>前置摄像头：500万像素</li>
                                <li>核 数：其他</li>
                                <li>频 率：以官网信息为准</li>
                                <li>品牌： Apple</li>
                                <li>商品名称：APPLEiPhone 6s Plus</li>
                                <li>商品编号：1861098</li>
                                <li>商品毛重：0.51kg</li>
                                <li>商品产地：中国大陆</li>
                                <li>热点：指纹识别，Apple Pay，金属机身，拍照神器</li>
                                <li>系统：苹果（IOS）</li>
                                <li>像素：1000-1600万</li>
                                <li>机身内存：64GB</li>
                                <li>${goodsDesc.introduction}</li>
                            </ul>
                            <div class="intro-detail">

                                <img src="img/_/intro01.png"/>
                                <img src="img/_/intro02.png"/>
                                <img src="img/_/intro03.png"/>
                            </div>
                        </div>
                        <div id="two" class="tab-pane">
                            <p>${goodsDesc.packageList}</p>
                        </div>
                        <div id="three" class="tab-pane">
                            <p>${goodsDesc.saleService}</p>
                        </div>
                    <#--<div id="four" class="tab-pane">-->
                    <#--<p>商品评价</p>-->
                    <#--</div>-->
                    <#--<div id="five" class="tab-pane">-->
                    <#--<p>手机社区</p>-->
                    <#--</div>-->
                    </div>
                </div>
            </div>
        </div>
        <!--like-->
        <div class="clearfix"></div>
        <div class="like">
            <h4 class="kt">猜你喜欢</h4>
            <div class="like-list">
                <ul class="yui3-g">
                    <li class="yui3-u-1-6">
                        <div class="list-wrap">
                            <div class="p-img">
                                <img src="img/_/itemlike01.png"/>
                            </div>
                            <div class="attr">
                                <em>DELL戴尔Ins 15MR-7528SS 15英寸 银色 笔记本</em>
                            </div>
                            <div class="price">
                                <strong>
                                    <em>¥</em>
                                    <i>3699.00</i>
                                </strong>
                            </div>
                            <div class="commit">
                                <i class="command">已有6人评价</i>
                            </div>
                        </div>
                    </li>
                    <li class="yui3-u-1-6">
                        <div class="list-wrap">
                            <div class="p-img">
                                <img src="img/_/itemlike02.png"/>
                            </div>
                            <div class="attr">
                                <em>Apple苹果iPhone 6s/6s Plus 16G 64G 128G</em>
                            </div>
                            <div class="price">
                                <strong>
                                    <em>¥</em>
                                    <i>4388.00</i>
                                </strong>
                            </div>
                            <div class="commit">
                                <i class="command">已有700人评价</i>
                            </div>
                        </div>
                    </li>
                    <li class="yui3-u-1-6">
                        <div class="list-wrap">
                            <div class="p-img">
                                <img src="img/_/itemlike03.png"/>
                            </div>
                            <div class="attr">
                                <em>DELL戴尔Ins 15MR-7528SS 15英寸 银色 笔记本</em>
                            </div>
                            <div class="price">
                                <strong>
                                    <em>¥</em>
                                    <i>4088.00</i>
                                </strong>
                            </div>
                            <div class="commit">
                                <i class="command">已有700人评价</i>
                            </div>
                        </div>
                    </li>
                    <li class="yui3-u-1-6">
                        <div class="list-wrap">
                            <div class="p-img">
                                <img src="img/_/itemlike04.png"/>
                            </div>
                            <div class="attr">
                                <em>DELL戴尔Ins 15MR-7528SS 15英寸 银色 笔记本</em>
                            </div>
                            <div class="price">
                                <strong>
                                    <em>¥</em>
                                    <i>4088.00</i>
                                </strong>
                            </div>
                            <div class="commit">
                                <i class="command">已有700人评价</i>
                            </div>
                        </div>
                    </li>
                    <li class="yui3-u-1-6">
                        <div class="list-wrap">
                            <div class="p-img">
                                <img src="img/_/itemlike05.png"/>
                            </div>
                            <div class="attr">
                                <em>DELL戴尔Ins 15MR-7528SS 15英寸 银色 笔记本</em>
                            </div>
                            <div class="price">
                                <strong>
                                    <em>¥</em>
                                    <i>4088.00</i>
                                </strong>
                            </div>
                            <div class="commit">
                                <i class="command">已有700人评价</i>
                            </div>
                        </div>
                    </li>
                    <li class="yui3-u-1-6">
                        <div class="list-wrap">
                            <div class="p-img">
                                <img src="img/_/itemlike06.png"/>
                            </div>
                            <div class="attr">
                                <em>DELL戴尔Ins 15MR-7528SS 15英寸 银色 笔记本</em>
                            </div>
                            <div class="price">
                                <strong>
                                    <em>¥</em>
                                    <i>4088.00</i>
                                </strong>
                            </div>
                            <div class="commit">
                                <i class="command">已有700人评价</i>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>