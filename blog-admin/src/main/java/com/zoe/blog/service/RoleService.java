package com.zoe.blog.service;

import com.github.pagehelper.PageHelper;
import com.zoe.blog.dao.RoleDao;
import com.zoe.blog.dao.UserRoleRefDao;
import com.zoe.blog.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 02:32
 */
@Service
public class RoleService {

    @Autowired
    RoleDao roleDao;

    @Autowired
    UserRoleRefDao userRoleRefDao;

    public List<Role> list(String roleName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return roleDao.getList(roleName);
    }

    public List<Role> search(String roleName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return roleDao.search(roleName);
    }

    public Role findByRoleId(Integer id) {
        return roleDao.findByRoleId(id);
    }

    public int insert(Role role) {
        return roleDao.insert(role);
    }

    public int update(Role role) {
        return roleDao.update(role);
    }

    @Transactional
    public int delete(Integer id) {
        // 删除用户角色关联表里面的角色
        userRoleRefDao.deleteByRoleId(id);

        return roleDao.delete(id);
    }
}
