package com.zoe.blog.service;

import com.zoe.blog.dao.UserDao;
import com.zoe.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuxuelian on 2020/07/29 16:51
 */
@Service
public class WebUserDetailService {
    @Autowired
    UserDao userDao;

    /**
     * 获取展示在博客首页的博主信息
     */
    public User getUserDetail() {
        return userDao.getList("nico").get(0);
    }
}
