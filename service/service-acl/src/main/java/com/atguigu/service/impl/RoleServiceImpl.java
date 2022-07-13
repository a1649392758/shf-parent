package com.atguigu.service.impl;


import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/6/28 11:45
 * @Version 1.0
 */
@Transactional
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    protected BaseDao<Role> getEntityDao() {
        return roleDao;
    }

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    /**
     * 根据用户获取角色数据
     *
     * @param adminId
     * @return
     */
    @Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {
        //查询所有的角色
        List<Role> allRolesList = roleDao.findAll();

        //拥有的角色id
        List<Long> existRoleIdList = adminRoleDao.findRoleIdByAdminId(adminId);

        //对角色进行分类
        List<Role> noAssignRoleList = new ArrayList<>();
        List<Role> assignRoleList = new ArrayList<>();
        for (Role role : allRolesList) {
            //已分配
            if (existRoleIdList.contains(role.getId())) {
                assignRoleList.add(role);
            } else {
                noAssignRoleList.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("noAssignRoleList", noAssignRoleList);
        roleMap.put("assignRoleList", assignRoleList);
        return roleMap;
    }

    /**
     * 分配角色
     *
     * @param adminId
     * @param roleIds
     */
    @Override
    public void saveUserRoleRealtionShip(Long adminId, Long[] roleIds) {
        // 先删除该用户已经分配的所有的角色
        this.adminRoleDao.deleteByAdminId(adminId);
        //给该用户添加现在分配的各个角色
        for(Long roleId :roleIds){
            if(!StringUtils.isEmpty(roleId)){
                AdminRole adminRole = new AdminRole();
                adminRole.setRoleId(roleId);
                adminRole.setAdminId(adminId);
                this.adminRoleDao.insert(adminRole);
            }
        }
    }

}