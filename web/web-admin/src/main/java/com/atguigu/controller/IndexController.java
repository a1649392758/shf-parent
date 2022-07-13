package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/6/28 14:22
 * @Version 1.0
 */

@Controller
public class IndexController {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    private final static String PAGE_INDEX = "frame/index";
    private final static String PAGE_LOGIN = "frame/login";
    private final static String PAGE_MAIN = "frame/main";

    /**
     * 框架首页
     *
     * @return
     */
    /**
     * 框架首页
     *
     * @return
     */
    @GetMapping("/")
    public String index(ModelMap model) {
        //后续替换为当前登录用户id
        //Long adminId = 11L;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        //Admin admin = adminService.getById(adminId);
        Admin admin = adminService.getByUsername(username);
        //List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(adminId);
        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(admin.getId());
        model.addAttribute("admin", admin);
        model.addAttribute("permissionList",permissionList);
        return PAGE_INDEX;
    }

    /**
     * 框架主页
     *
     * @return
     */
    @GetMapping("/main")
    public String main() {

        return PAGE_MAIN;
    }

    @RequestMapping("/loginPage")
    public String loginPage() {
        return PAGE_LOGIN;
    }

    /**
     * 获取当前登录信息
     * @return
     */
    @GetMapping("getInfo")
    @ResponseBody
    public Object getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getPrincipal();
    }
}