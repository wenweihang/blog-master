package com.zoe.blog.service;

import com.github.pagehelper.PageHelper;
import com.zoe.blog.dao.UserDao;
import com.zoe.blog.dao.UserRoleRefDao;
import com.zoe.blog.model.User;
import com.zoe.blog.utils.RestBCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 11:23
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    UserRoleRefDao userRoleRefDao;

    @Autowired
    RestBCryptPasswordEncoder encoder;

    public List<User> list(String username, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userDao.getList(username);
    }

    public List<User> search(String username, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userDao.search(username);
    }

    public User findByUserId(Integer id) {
        return userDao.findByUserId(id);
    }

    public int insert(User user) {
        // 加密
        user.setPassword(encoder.passwrodEncoder(user.getPassword()));
        return userDao.insert(user);
    }

    public int update(User user) {
        return userDao.update(user);
    }

    public int updatePassword(Integer id, String rawPassword, String newPassword) {
        User user = userDao.findByUserId(id);
        if (!encoder.passwordMatch(rawPassword, user.getPassword())) {
            return -1;
        }
        return userDao.modifyPassword(id, encoder.passwrodEncoder(newPassword));
    }

    @Transactional
    public int delete(Integer id) {
        // 删除用户角色关联表
        userRoleRefDao.deleteByUserId(id);

        return userDao.delete(id);
    }
}
