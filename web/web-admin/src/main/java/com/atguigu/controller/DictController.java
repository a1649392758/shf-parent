package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DictController
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/7/2 11:21
 * @Version 1.0
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

    @Reference
    private DictService dictService;

    private final static String PAGE_INDEX = "dict/index";

    @GetMapping(value = "/findZNodes")
    @ResponseBody
    public Result findByParentId(@RequestParam(value = "id", defaultValue = "0") Long id) {
        List<Map<String,Object>> zNodes = dictService.findZNodes(id);
        return Result.ok(zNodes);
    }

    @GetMapping
    public String index(ModelMap model) {
        return PAGE_INDEX;
    }

    /**
     * 根据上级id获取子节点数据列表
     * @param parentId
     * @return
     */
    @GetMapping(value = "findListByParentId/{parentId}")
    @ResponseBody
    public Result<List<Dict>> findListByParentId(@PathVariable Long parentId) {
        List<Dict> list = dictService.findListByParentId(parentId);
        return Result.ok(list);
    }

    /**
     * 根据编码获取子节点数据列表
     * @param dictCode
     * @return
     */
    @RequestMapping("/findListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode) { //beijing
        List<Dict> dictList = this.dictService.findListByDictCode(dictCode);
        return Result.ok(dictList);
    }

}
