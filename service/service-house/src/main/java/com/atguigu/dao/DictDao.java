package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Dict;

import java.util.List;

/**
 * @ClassName DictDao
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/7/2 11:06
 * @Version 1.0
 */
public interface DictDao extends BaseDao<Dict> {

    List<Dict> findListByParentId(Long parentId);

    Integer countIsParent(Long id);

    String getNameById(Long id);

    Dict getByDictCode(String dictCode);
}
