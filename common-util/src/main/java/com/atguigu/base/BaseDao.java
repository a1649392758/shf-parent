package com.atguigu.base;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName BaseDao
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/6/29 21:04
 * @Version 1.0
 */
public interface BaseDao<T> {

    /**
     * 保存一个实体
     *
     * @param t
     */
    Integer insert(T t);


    /**
     * 删除
     *
     * @param id 标识ID 可以是自增长ID，也可以是唯一标识。
     */
    void delete(Serializable id);

    /**
     * 更新一个实体
     *
     * @param t
     */
    Integer update(T t);

    /**
     * 通过一个标识ID 获取一个唯一实体
     *
     * @param id 标识ID 可以是自增长ID，也可以是唯一标识。
     * @return
     */
    T getById(Serializable id);

    Page<T> findPage(Map<String, Object> filters);
}