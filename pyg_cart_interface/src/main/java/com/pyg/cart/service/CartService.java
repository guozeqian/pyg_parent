package com.pyg.cart.service;

import com.pyg.pojogroup.Cart;

import java.util.List;


public interface CartService {


    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId,
                                         Integer num);

    public List<Cart> findCartListFromRedis(String username);

    public void saveCartListToRedis(String username,List<Cart> cartList);
}
