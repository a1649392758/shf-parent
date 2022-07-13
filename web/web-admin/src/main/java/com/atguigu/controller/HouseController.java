package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HouseController
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/7/4 9:39
 * @Version 1.0
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseService houseService;

    private final static String LIST_ACTION = "redirect:/house";

    private final static String PAGE_INDEX = "house/index";
    private final static String PAGE_SHOW = "house/show";
    private final static String PAGE_CREATE = "house/create";
    private final static String PAGE_EDIT = "house/edit";
    private final static String PAGE_SUCCESS = "common/successPage";

    private String getString(ModelMap model, String pageCreate) {
        model.addAttribute("communityList",communityService.findAll());
        model.addAttribute("houseTypeList",dictService.findListByDictCode("houseType"));
        model.addAttribute("floorList",dictService.findListByDictCode("floor"));
        model.addAttribute("buildStructureList",dictService.findListByDictCode("buildStructure"));
        model.addAttribute("directionList",dictService.findListByDictCode("direction"));
        model.addAttribute("decorationList",dictService.findListByDictCode("decoration"));
        model.addAttribute("houseUseList",dictService.findListByDictCode("houseUse"));
        return pageCreate;
    }

    /**
     * 列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String index(ModelMap model, HttpServletRequest request){
        Map<String, Object> filters = getFilters(request);
        PageInfo<House> page = houseService.findPage(filters);
        model.addAttribute("page",page);
        model.addAttribute("filters",filters);
        return getString(model, PAGE_INDEX);
    }

    @GetMapping("/create")
    public String insert(ModelMap model){
        return getString(model, PAGE_CREATE);
    }

    @PostMapping("/save")
    public String save(ModelMap model, House house){
        model.addAttribute("messagePage","添加成功");
        houseService.insert(house);
        return PAGE_SUCCESS;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap model){
        House house = houseService.getById(id);
        model.addAttribute("house",house);
        return getString(model, PAGE_EDIT);
    }

    @PostMapping("/update")
    public String update(ModelMap model, House house){
        model.addAttribute("messagePage","修改成功");
        houseService.update(house);
        return PAGE_SUCCESS;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        houseService.delete(id);
        return LIST_ACTION;
    }

@GetMapping("/publish/{id}/{status}")
    public String publish(@PathVariable Long id,@PathVariable Integer status){
        houseService.publish(id,status);
        return LIST_ACTION;
    }

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseUserService houseUserService;

    /**
     * 详情
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public String show(ModelMap model,@PathVariable Long id) {
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(id);
        List<HouseUser> houseUserList = houseUserService.findListByHouseId(id);
        List<HouseImage> houseImage1List = houseImageService.findList(id, 1);
        List<HouseImage> houseImage2List = houseImageService.findList(id, 2);

        model.addAttribute("house",house);
        model.addAttribute("community",community);
        model.addAttribute("houseBrokerList",houseBrokerList);
        model.addAttribute("houseUserList",houseUserList);
        model.addAttribute("houseImage1List",houseImage1List);
        model.addAttribute("houseImage2List",houseImage2List);
        return PAGE_SHOW;
    }

 }

