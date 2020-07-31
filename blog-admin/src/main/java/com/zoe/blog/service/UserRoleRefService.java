package com.zoe.blog.service;

import com.zoe.blog.dao.UserRoleRefDao;
import com.zoe.blog.model.Role;
import com.zoe.blog.model.UserRoleRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 11:51
 */
@Service
public class UserRoleRefService {
    @Autowired
    UserRoleRefDao userRoleRefDao;

    public Role getRoleByUserId(Integer userId) {
        return userRoleRefDao.getRoleByUserId(userId);
    }

    /**
     * 批量插入用户拥有的角色
     * @param roleRef
     */
    @Transactional
    public int insert(UserRoleRef roleRef) {

        // 添加数据前删除用户之前拥有的权限
        userRoleRefDao.deleteByUserId(roleRef.getUserId());

        return userRoleRefDao.insert(roleRef);
    }

}
