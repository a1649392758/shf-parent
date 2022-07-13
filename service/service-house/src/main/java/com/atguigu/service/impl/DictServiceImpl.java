package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DictServiceImpl
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/7/2 9:53
 * @Version 1.0
 */
@SuppressWarnings("unchecked")
@Transactional
@Service(interfaceClass = DictService.class)
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {

    @Autowired
    private DictDao dictDao;

    protected BaseDao getEntityDao() {
        return dictDao;
    }

    @Override
    public List<Map<String, Object>> findZNodes(Long id) {
        List<Dict> dictList = dictDao.findListByParentId(id);
        List<Map<String, Object>> zNodes = new ArrayList<>();
        for (Dict dict : dictList) {
            Integer count = dictDao.countIsParent(dict.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("id", dict.getId());
            map.put("isParent", count > 0 ? true : false);
            map.put("name", dict.getName());
            zNodes.add(map);
        }
        ;
        return zNodes;
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        //根据dictCode找到该字典信息（其中包含该字典信息的id）
        Dict dict = this.dictDao.getByDictCode(dictCode);
        //根据字典的id获取它的下级字典列表信息
        return this.dictDao.findListByParentId(dict.getId());
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        return this.dictDao.findListByParentId(parentId);
    }

    @Override
    public String getNameById(Long id) {
        return this.dictDao.getNameById(id);
    }

}

