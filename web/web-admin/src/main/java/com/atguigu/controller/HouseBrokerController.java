package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName HouseBrokerController
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/7/4 22:41
 * @Version 1.0
 */
@Controller
@RequestMapping(value="/houseBroker")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseBrokerController extends BaseController {

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private AdminService adminService;

    private final static String LIST_ACTION = "redirect:/house/";
    private final static String PAGE_CREATE = "houseBroker/create";
    private final static String PAGE_EDIT = "houseBroker/edit";
    private final static String PAGE_SUCCESS = "common/successPage";


    /**
     * 进入新增
     * @param model
     * @param houseBroker
     * @return
     */
    @GetMapping("/create")
    public String create(ModelMap model, @RequestParam("houseId") Long houseId) {
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList",adminList);
        model.addAttribute("houseId",houseId);
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param model
     * @param houseBroker
     * @param request
     * @return
     */
    @PostMapping("/save")
    public String save(HouseBroker houseBroker,ModelMap model) {
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        model.addAttribute("messagePage", "添加成功");
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.insert(houseBroker);
        return PAGE_SUCCESS;
    }

    /**
     * 编辑
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(ModelMap model,@PathVariable Long id) {
        HouseBroker houseBroker = houseBrokerService.getById(id);
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList",adminList);
        model.addAttribute("houseBroker",houseBroker);
        return PAGE_EDIT;
    }

    /**
     * 保存更新
     * @param model
     * @param id
     * @param houseBroker
     * @param request
     * @return
     */
    @PostMapping(value="/update")
    public String update(HouseBroker houseBroker,ModelMap model) {
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        model.addAttribute("messagePage", "修改成功");
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.update(houseBroker);

        return PAGE_SUCCESS;
    }

    /**
     * 删除
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId, @PathVariable Long id) {
        houseBrokerService.delete(id);
        return LIST_ACTION + houseId;
    }

}