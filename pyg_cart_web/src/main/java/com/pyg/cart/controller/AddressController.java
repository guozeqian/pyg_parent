package com.pyg.cart.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbAddress;
import com.pyg.user.service.AddressService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {

    @Reference
    private AddressService addressService;

    @RequestMapping("findListByLoginUser")
    public List<TbAddress> findListByLoginUser() {
        String username = SecurityContextHolder.getContext().getAuthentication
                ().getName();
        System.out.println(username);
        return addressService.findAddressByUser(username);
    }


}
