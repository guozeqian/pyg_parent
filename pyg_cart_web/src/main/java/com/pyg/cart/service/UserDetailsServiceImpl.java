package com.pyg.cart.service;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        List<GrantedAuthority> authorityList = new ArrayList();
        authorityList.add(new SimpleGrantedAuthority
                ("ROLE_SELLER"));
        authorityList.add(new SimpleGrantedAuthority("ROLE_AAAA"));
        return new User(username, "", authorityList);


    }




}
