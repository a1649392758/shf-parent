package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @ClassName UserDetailsServiceImpl
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/7/ssssss11 11:53
 * @Version 1.0
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username--->" + username);
        Admin admin = adminService.getByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<GregorianCalendar> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(""));
        return new User(username,admin.getPassword(), authorities);
    }
}
