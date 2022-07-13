package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.QiniuUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/6/29 15:43
 * @Version 1.0
 */
@Controller
@RequestMapping(value="/admin")
@SuppressWarnings({"unchecked", "rawtypes"})
public class AdminController extends BaseController {

    @Reference
    private AdminService adminService;

    @Reference
    private RoleService roleService;

    private final static String LIST_ACTION = "redirect:/admin";
    private final static String PAGE_INDEX = "admin/index";
    private final static String PAGE_CREATE = "admin/create";
    private final static String PAGE_EDIT = "admin/edit";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_UPLOAD_SHOW = "admin/upload";
    private final static String PAGE_ASSIGN_SHOW = "admin/assignShow";

    /**
     * 列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String index(ModelMap model, HttpServletRequest request) {
        Map<String,Object> filters = getFilters(request);
        PageInfo<Admin> page = adminService.findPage(filters);

        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

    /**
     * 进入新增页面
     * @param model
     * @param admin
     * @return
     */
    @GetMapping("/create")
    public String create() {
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param admin
     * @param request
     * @return
     */
    @PostMapping("/save")
    public String save(Admin admin) {
        //设置默认头像
        admin.setHeadUrl("http://47.93.148.192:8080/group1/M00/03/F0/rBHu8mHqbpSAU0jVAAAgiJmKg0o148.jpg");
        adminService.insert(admin);

        return PAGE_SUCCESS;
    }

    /**
     * 进入编辑页面
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable Long id) {
        Admin admin = adminService.getById(id);
        model.addAttribute("admin",admin);
        return PAGE_EDIT;
    }

    /**
     * 保存更新
     * @param id
     * @param admin
     * @param request
     * @return
     */
    @PostMapping(value="/update")
    public String update(Admin admin,ModelMap model) {
        model.addAttribute("messagePage", "修改成功");
        adminService.update(admin);

        return PAGE_SUCCESS;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        adminService.delete(id);
        return LIST_ACTION;
    }

    @GetMapping("/uploadShow/{id}")
    public String uploadShow(ModelMap model,@PathVariable Long id) {
        model.addAttribute("id", id);
        return PAGE_UPLOAD_SHOW;
    }

    @PostMapping("/upload/{id}")
    public String upload(@PathVariable Long id, @RequestParam(value = "file") MultipartFile file, HttpServletRequest request,ModelMap model) throws IOException {
        String newFileName =  UUID.randomUUID().toString() ;
        // 上传图片
        QiniuUtil.upload2Qiniu(file.getBytes(),newFileName);
        String url= "http://remni17at.hb-bkt.clouddn.com/"+ newFileName;
        Admin admin = new Admin();
        admin.setId(id);
        admin.setHeadUrl(url);
        adminService.update(admin);
        model.addAttribute("messagePage","头像上传成功!!!");
        return PAGE_SUCCESS;
    }

    /**
     * 进入分配角色页面
     * @param adminId
     * @return
     */
    @GetMapping("/assignShow/{adminId}")
    public String assignShow(ModelMap model,@PathVariable Long adminId) {
        Map<String, Object> roleMap = roleService.findRoleByAdminId(adminId);
        model.addAllAttributes(roleMap);
        model.addAttribute("adminId", adminId);
        return PAGE_ASSIGN_SHOW;
    }

    /**
     * 根据用户分配角色
     * @param adminId
     * @param roleIds
     * @return
     */
    @PostMapping("/assignRole")
    public String assignRole(Long adminId, Long[] roleIds,ModelMap model) {
        model.addAttribute("messagePage", "分配成功");
        roleService.saveUserRoleRealtionShip(adminId,roleIds);
        return PAGE_SUCCESS;
    }


}
