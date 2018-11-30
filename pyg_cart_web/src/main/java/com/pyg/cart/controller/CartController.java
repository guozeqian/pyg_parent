package com.pyg.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pyg.cart.service.CartService;
import com.pyg.order.service.OrderService;
import com.pyg.pojo.TbOrder;
import com.pyg.pojogroup.Cart;
import entity.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import com.pyg.utils.CookieUtil;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference
    private CartService cartService;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;


    @RequestMapping("findCartList")
    public List<Cart> findCartList() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        if (username.equals("anonymousUser")) {//如果未登录
            String cartListString = CookieUtil.getCookieValue(request,
                    "cartList", "UTF-8");
            if (cartListString == null || cartListString.equals("")) {
                cartListString = "[]";
            }
            List<Cart> cartlist_cookie = JSON.parseArray(cartListString, Cart
                    .class);
            return cartlist_cookie;
        } else {
            List<Cart> list = cartService.findCartListFromRedis(username);
            return list;
        }
    }

    @RequestMapping("addGoodsToCartList")
    @CrossOrigin(origins = "http://localhost:9004")
    public Result addGoodsToCartList(Long itemId, Integer num) {
        try {
            //处理js跨域调用问题
//            response.setHeader("Access-Control-Allow-Origin",
//                    "http://localhost:9004");//允许谁来跨域访问
//            response.setHeader("Access-Control-Allow-Credentials","true");
// 允许访问的时候,带过来cookie

            String username = SecurityContextHolder.getContext()
                    .getAuthentication().getName();
            List<Cart> cartList = findCartList();
            cartList = cartService.addGoodsToCartList(cartList, itemId,
                    num);
            if ("anonymousUser".equals(username)) {
                CookieUtil.setCookie(request, response, "cartList", JSON
                        .toJSONString(cartList), 3600 * 24, "UTF-8");

            } else {
                cartService.saveCartListToRedis(username, cartList);
            }
            return new Result(true, "添加成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "添加失败");
    }



}
