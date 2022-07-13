package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.HouseUserDao;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName HouseUserServiceImpl
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/7/4 22:22
 * @Version 1.0
 */
@Service(interfaceClass = HouseUserService.class)
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {

    @Autowired
    private HouseUserDao houseUserDao;

    @Override
    protected BaseDao<HouseUser> getEntityDao() {
        return houseUserDao;
    }

    @Override
    public List<HouseUser> findListByHouseId(Long houseId) {
        return houseUserDao.findListByHouseId(houseId);
    }
}