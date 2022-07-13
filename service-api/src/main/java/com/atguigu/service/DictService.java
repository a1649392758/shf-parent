package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DictService
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/7/2 9:50
 * @Version 1.0
 */
public interface DictService extends BaseService<Dict> {

    List<Map<String,Object>> findZNodes(Long id);

    /**
     * 根据编码获取子节点数据列表
     * @param dictCode
     * @return
     */
    List<Dict> findListByDictCode(String dictCode);


    List<Dict> findListByParentId(Long parentId);


    String getNameById(Long id);
}
