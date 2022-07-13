package com.atguigu.base;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

/**
  *@ClassName BaseService
  *@Description TODO
  *@Author 郑帅
  *@DATE 2022/6/29 21:11
  *@Version 1.0
*/
public interface BaseService<T> {

    Integer insert(T t);

    void delete(Serializable id);

    Integer update(T t);

    T getById(Serializable id);

    PageInfo<T> findPage(Map<String, Object> filters);
}